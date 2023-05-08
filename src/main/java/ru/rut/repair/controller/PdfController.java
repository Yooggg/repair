package ru.rut.repair.controller;

import com.lowagie.text.*;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.rut.repair.model.Act;
import ru.rut.repair.model.Inventory;
import ru.rut.repair.model.Locomotive;
import ru.rut.repair.model.Works;
import ru.rut.repair.service.ActService;
import ru.rut.repair.service.InventoryService;
import ru.rut.repair.service.WorksService;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;
import java.util.List;


@Controller
public class PdfController {
    private final ActService actService;
    private final WorksService worksService;
    private final InventoryService inventoryService;

    public PdfController(ActService actService, WorksService worksService, InventoryService inventoryService) {
        this.actService = actService;
        this.worksService = worksService;
        this.inventoryService = inventoryService;
    }

    @PostMapping("certificate/{actId}/print")
    public Document createPDF(@PathVariable(value = "actId") int id) throws FileNotFoundException {
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
        createLine(document,"ФИО");
        document.add(new Paragraph(Chunk.NEWLINE));
        document.add(new Paragraph(Chunk.NEWLINE));
        createLine(document,"дата");

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
            listRows.put(worksList.indexOf(e), Arrays.asList(e.getName(), e.getQuantity().toString()));
        });

        listRows.forEach((index,userDetailRow) -> {
            String currentName = userDetailRow.get(0);
            String currentQuantity = userDetailRow.get(1);

            table.addCell(new Cell(new Phrase(currentName)));
            table.addCell(new Cell(new Phrase(currentQuantity)));

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

        inventoryList.forEach((e) -> {
            listRows.put(inventoryList.indexOf(e), Arrays.asList(e.getNumber().toString(), e.getInventoryName(), e.getMeasureUnit(), e.getQuantityNorm().toString(), e.getQuantityFact().toString()));
        });

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

        document.add(table);
    }
    private void createLine(Document document, String str){

        PdfPTable table = new PdfPTable(1);
        table.setTotalWidth(1000);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        table.getDefaultCell().setPaddingBottom(5);
        table.getDefaultCell().setBorder(Rectangle.TOP);
        table.addCell(new Paragraph(str));

        document.add(table);
    }


}
