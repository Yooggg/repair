package ru.rut.repair.controller;

import com.lowagie.text.*;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.rut.repair.model.Act;
import ru.rut.repair.model.Inventory;
import ru.rut.repair.model.Locomotive;
import ru.rut.repair.model.Works;
import ru.rut.repair.repository.ActRepository;
import ru.rut.repair.service.ActService;
import ru.rut.repair.service.InventoryService;
import ru.rut.repair.service.LocomotiveService;
import ru.rut.repair.service.WorksService;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class PdfController {
    private final ActService actService;
    private final ActRepository actRepository;
    private final LocomotiveService locomotiveService;
    private final WorksService worksService;
    private final InventoryService inventoryService;

    public PdfController(ActService actService, ActRepository actRepository, LocomotiveService locomotiveService, WorksService worksService, InventoryService inventoryService) {
        this.actService = actService;
        this.actRepository = actRepository;
        this.locomotiveService = locomotiveService;
        this.worksService = worksService;
        this.inventoryService = inventoryService;
    }

    @PostMapping("certificate/{act_id}/print")
    public Document createPDF(@PathVariable(value = "act_id") int id) throws FileNotFoundException {
        String FILE = "act" + id + ".pdf";
        Act act = actService.getById(id);

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(FILE));
        document.open();
        Table table = new Table(1);
        document.add(new Paragraph("Акт №" + act.getId()));
        document.add(new Paragraph(Chunk.NEWLINE));
        document.add(new Paragraph("приемки локомотива от ремотного предприятия(сервисной компании)"));
        document.add(new Paragraph(Chunk.NEWLINE));
        document.add(new Paragraph("Настоящий акт составлен о том, что локомотив"));
        document.add(new Paragraph(Chunk.NEWLINE));
        createTableLoco(document,act);
        document.add(new Paragraph(Chunk.NEWLINE));
        document.add(new Paragraph("принимается от ремонтного предприятия(сервисной компании)"));
        document.add(new Paragraph(Chunk.NEWLINE));
        document.add(new Paragraph(act.getCompany()));
        document.add(new Paragraph(Chunk.NEWLINE));
        document.add(new Paragraph("после выполнения ТО/ремонта/модернизации/МЛП/испытаний"));
        document.add(new Paragraph(Chunk.NEWLINE));
        document.add(new Paragraph("Перечень выполненных конструктивных изменений и модернизаци внесен в технический пасспорт локомотива"));
        document.add(new Paragraph(Chunk.NEWLINE));
        createTableWorkName(document,act);
        document.add(new Paragraph(Chunk.NEWLINE));
        document.add(new Paragraph("Комплектность инвентаря, деталей и узлов"));
        document.add(new Paragraph(Chunk.NEWLINE));
        createTableInventory(document,act);
        document.add(new Paragraph(Chunk.NEWLINE));
        document.add(new Paragraph("При приемке не выявлено нарушений действующей нормативной документации по ремонту(техническому обслуживанию, модернизации)(правил, инструкций и т.д.)."));
        document.add(new Paragraph(Chunk.NEWLINE));
        document.add(new Paragraph(Chunk.NEWLINE));
        Rectangle line = new Rectangle(30f, 30f, PageSize.A4.getRight(30f), PageSize.A4.getRelativeTop());
        line.setBorder(Rectangle.TOP);
        line.setBorderWidth(1f);
        line.setBorderColor(Color.BLACK);
        document.add(line);
        document.add(new Paragraph("ФИО"));
        document.add(new Paragraph(Chunk.NEWLINE));
        document.add(new Paragraph(Chunk.NEWLINE));
        line.setTop(220f);
        document.add(line);
        document.add(new Paragraph(Chunk.NEWLINE));
        document.add(new Paragraph("дата"));

        document.close();
        return document;
    }

    private void createTableLoco(Document document, Act act){
        Table table = new Table(5);
        ArrayList<String> headerTable = new ArrayList<>();
        headerTable.add("Серия");
        headerTable.add("Заводской номер");
        headerTable.add("Индекс секции");
        headerTable.add("Депо(предприятие) приписки");
        headerTable.add("Факт проведения работ");

        headerTable.forEach(e -> {
            Cell current = new Cell(new Phrase(e));
            current.setHeader(true);
            current.getVerticalAlignment();
            table.addCell(current);
        });

        LinkedHashMap<Integer, List<String>> listRows = new LinkedHashMap<>();
        Locomotive locomotive = act.getLocomotive();
        listRows.put(1, Arrays.asList(locomotive.getSeries(),locomotive.getFactoryNumber(), locomotive.getSectionIndex(), locomotive.getHomeDepot(),locomotive.getWorkFact()));
        listRows.forEach((index,userDetailRow) -> {
            String currentSeries = userDetailRow.get(0);
            String currentFactoryName = userDetailRow.get(1);
            String currentSectionIndex = userDetailRow.get(2);
            String currentHomeDepot = userDetailRow.get(3);
            String currentWorkFact = userDetailRow.get(4);

            table.addCell(new Cell(new Phrase(currentSeries)));
            table.addCell(new Cell(new Phrase(currentFactoryName)));
            table.addCell(new Cell(new Phrase(currentSectionIndex)));
            table.addCell(new Cell(new Phrase(currentHomeDepot)));
            table.addCell(new Cell(new Phrase(currentWorkFact)));
        });

        document.add(table);
    }

    private void createTableWorkName(Document document, Act act){
        Table table = new Table(2);
        ArrayList<String> headerTable = new ArrayList<>();
        headerTable.add("Наименование выполненных работ");
        headerTable.add("Количество");

        headerTable.forEach(e -> {
            Cell current = new Cell(new Phrase(e));
            current.setHeader(true);
            current.getVerticalAlignment();
            table.addCell(current);
        });

        LinkedHashMap<Integer, List<String>> listRows = new LinkedHashMap<>();
        List<Works> worksList = worksService.getListByActId(act.getId());

        worksList.forEach(e ->{
            for (int i = 1; i < worksList.size() + 1; i++) {
                listRows.put(i, Arrays.asList(e.getName(), e.getQuantity().toString()));
                listRows.forEach((index,userDetailRow) -> {
                    String currentName = userDetailRow.get(0);
                    String currentQuantity = userDetailRow.get(1);

                    table.addCell(new Cell(new Phrase(currentName)));
                    table.addCell(new Cell(new Phrase(currentQuantity)));

                });
            }
        });

        document.add(table);
    }
    private void createTableInventory(Document document, Act act){
        Table table = new Table(5);
        ArrayList<String> headerTable = new ArrayList<>();
        headerTable.add("N п/п");
        headerTable.add("Наименование инвентаря");
        headerTable.add("Ед.изм");
        headerTable.add("Количество по норме");
        headerTable.add("Количество по факту");

        headerTable.forEach(e -> {
            Cell current = new Cell(new Phrase(e));
            current.setHeader(true);
            current.getVerticalAlignment();
            table.addCell(current);
        });

        LinkedHashMap<Integer, List<String>> listRows = new LinkedHashMap<>();
        List<Inventory> inventoryList = inventoryService.getListByActId(act.getId());

        inventoryList.forEach(e -> {
            for (int i = 1; i < inventoryList.size() + 1; i++) {
                listRows.put(i, Arrays.asList(e.getNumber().toString(), e.getInventory_name(), e.getMeasure_unit(),e.getQuantity_norm().toString(),e.getQuantity_fact().toString()));
                listRows.forEach((index,userDetailRow) -> {
                    String currentNumber = userDetailRow.get(0);
                    String currentInventoryName = userDetailRow.get(1);
                    String currentMeasureUnit = userDetailRow.get(2);
                    String currentQuantityNorm = userDetailRow.get(3);
                    String currentQuantityFact = userDetailRow.get(4);

                    table.addCell(new Cell(new Phrase(currentNumber)));
                    table.addCell(new Cell(new Phrase(currentInventoryName)));
                    table.addCell(new Cell(new Phrase(currentMeasureUnit)));
                    table.addCell(new Cell(new Phrase(currentQuantityNorm)));
                    table.addCell(new Cell(new Phrase(currentQuantityFact)));
                });
            }
        });

        document.add(table);
    }
}
