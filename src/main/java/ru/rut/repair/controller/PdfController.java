package ru.rut.repair.controller;

import com.lowagie.text.*;
import com.lowagie.text.Rectangle;
import com.lowagie.text.alignment.HorizontalAlignment;
import com.lowagie.text.alignment.VerticalAlignment;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @PostMapping("certificate/print")
    public Document createPDF(@RequestParam int id) throws FileNotFoundException {
        String FILE = "act" + id + ".pdf";
        Act act = actService.getById(id);

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(FILE));
        Font font = new Font(Font.TIMES_ROMAN, 12);
        Font italic = new Font(Font.ITALIC,12);

        document.open();

        enterText(document, "Акт №" + act.getId(), font);
        enterText(document, "приемки локомотива от ремотного предприятия(сервисной компании)", font);
        enterText(document, "Настоящий акт составлен о том, что локомотив", font);
        enterText(document, "\n", font);
        emptyCell(document);

        createTableLoco(document,act);

        document.add(new Paragraph("принимается от ремонтного предприятия(сервисной компании)\n "));
        document.add(new Paragraph(act.getCompany()+"\n ", italic));
        document.add(new Paragraph("после выполнения ТО/ремонта/модернизации/МЛП/испытаний\n "));
        document.add(new Paragraph("Перечень выполненных конструктивных изменений и модернизаци внесен в технический пасспорт локомотива"));

        createTableWorkName(document,act);
        document.add(new Paragraph("\n "));
        document.add(new Paragraph("Комплектность инвентаря, деталей и узлов"));

        createTableInventory(document,act);

        document.add(new Paragraph("При приемке не выявлено нарушений действующей нормативной документации по ремонту(техническому обслуживанию, модернизации)(правил, инструкций и т.д.).\n "));
        document.add(new Paragraph(Chunk.NEWLINE));
        document.add(new Paragraph(Chunk.NEWLINE));
        createLine(document,"ФИО");
        document.add(new Paragraph(Chunk.NEWLINE));
        document.add(new Paragraph(Chunk.NEWLINE));
        createLine(document,"Дата");

        document.close();
        return document;
    }

    private void enterText(Document document, String str, Font font){

        Table table = new Table(1);
        table.setBorder(Rectangle.NO_BORDER);
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);
        table.setWidth(300);

        Cell cell = new Cell(new Paragraph(str, font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(HorizontalAlignment.CENTER);

        table.addCell(cell);

        document.add(table);
    }
    private void enterTextInTable(Table table, String str, Font font){

        Cell cell = new Cell(new Paragraph(str + "\n ", font));

        cell.setBorder(Rectangle.BOX);
        cell.setHorizontalAlignment(HorizontalAlignment.CENTER);
        cell.setVerticalAlignment(VerticalAlignment.BASELINE);

        table.addCell(cell);

    }
    private void emptyCell(Document document){

        Table table = new Table(1);
        table.setBorder(Rectangle.NO_BORDER);
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);
        table.setWidth(300);

        Cell cell = new Cell(new Paragraph(" "));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(HorizontalAlignment.CENTER);

        table.addCell(cell);

        document.add(table);

    }
    private void createTableLoco(Document document, Act act){
        Table table = new Table(5);
        table.setWidth(105);
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);
        table.getDefaultCell().setVerticalAlignment(VerticalAlignment.CENTER);
        table.getDefaultCell().setHorizontalAlignment(HorizontalAlignment.CENTER);
        table.getDefaultCell().setColspan(0);
        table.getDefaultCell().setRowspan(0);

        ArrayList<String> headerTable = new ArrayList<>();
        headerTable.add("Серия\n ");
        headerTable.add("Заводской номер\n ");
        headerTable.add("Индекс секции\n ");
        headerTable.add("Депо(предприятие) приписки\n ");
        headerTable.add("Факт проведения работ\n ");

        headerTable.forEach(e -> {
            Cell current = new Cell(new Phrase(e));
            current.setHorizontalAlignment(HorizontalAlignment.CENTER);
            current.setHeader(true);
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

            enterTextInTable(table, currentSeries, new Font(Font.TIMES_ROMAN, 12));
            enterTextInTable(table, currentFactoryName, new Font(Font.TIMES_ROMAN, 12));
            enterTextInTable(table, currentSectionIndex, new Font(Font.TIMES_ROMAN, 12));
            enterTextInTable(table, currentHomeDepot, new Font(Font.TIMES_ROMAN, 12));
            enterTextInTable(table, currentWorkFact, new Font(Font.TIMES_ROMAN, 12));
        });

        document.add(table);
    }
    private void createTableWorkName(Document document, Act act){
        Table table = new Table(2);
        table.setWidth(105);
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);
        table.getDefaultCell().setVerticalAlignment(VerticalAlignment.CENTER);
        table.getDefaultCell().setHorizontalAlignment(HorizontalAlignment.CENTER);
        table.getDefaultCell().setColspan(0);
        table.getDefaultCell().setRowspan(0);
        ArrayList<String> headerTable = new ArrayList<>();
        headerTable.add("Наименование выполненных работ\n ");
        headerTable.add("Количество\n ");

        headerTable.forEach(e -> {
            Cell current = new Cell(new Phrase(e));
            current.setHorizontalAlignment(HorizontalAlignment.CENTER);
            current.setHeader(true);
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

            enterTextInTable(table, currentName, new Font(Font.TIMES_ROMAN, 12));
            enterTextInTable(table, currentQuantity, new Font(Font.TIMES_ROMAN, 12));
        });

        document.add(table);
    }
    private void createTableInventory(Document document, Act act){
        Table table = new Table(5);
        table.setWidth(105);
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);
        table.getDefaultCell().setVerticalAlignment(VerticalAlignment.CENTER);
        table.getDefaultCell().setHorizontalAlignment(HorizontalAlignment.CENTER);
        table.getDefaultCell().setColspan(0);
        table.getDefaultCell().setRowspan(0);
        ArrayList<String> headerTable = new ArrayList<>();
        headerTable.add("N п/п\n ");
        headerTable.add("Наименование инвентаря\n ");
        headerTable.add("Ед.изм\n ");
        headerTable.add("Количество по норме\n ");
        headerTable.add("Количество по факту\n ");

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

            enterTextInTable(table, currentNumber, new Font(Font.TIMES_ROMAN, 12));
            enterTextInTable(table, currentInventoryName, new Font(Font.TIMES_ROMAN, 12));
            enterTextInTable(table, currentMeasureUnit, new Font(Font.TIMES_ROMAN, 12));
            enterTextInTable(table, currentQuantityNorm, new Font(Font.TIMES_ROMAN, 12));
            enterTextInTable(table, currentQuantityFact, new Font(Font.TIMES_ROMAN, 12));
        });

        document.add(table);
    }
    private void createLine(Document document, String str){

        Table table = new Table(1);
        table.setWidth(105);
        table.getDefaultCell().setHorizontalAlignment(HorizontalAlignment.LEFT);
        table.setBorder(Rectangle.TOP);
        Cell cell = new Cell(new Paragraph(str + "\n", new Font(Font.TIMES_ROMAN, 12)));
        cell.setHorizontalAlignment(HorizontalAlignment.LEFT);
        cell.setHeader(true);
        cell.setBorder(Rectangle.TOP);
        table.addCell(cell);

        document.add(table);
    }


}
