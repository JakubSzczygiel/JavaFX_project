package sample;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;

public class Excel implements Writeable {

    static final String EXCEL_FILE_PATH = "C:\\Users\\Jakub\\IdeaProjects\\JavaFX_todolist\\Output.xlsx";

    @Override
    public void write(Collection<Task> tasks) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("ToDoList");

        String[] columnTitles = {"DATE", "STATUS", "PRIORITY", "DESCRIPTION"};
        addColumnTitles(columnTitles, sheet);
        addTaskEntries(sheet, tasks);
        adjustColumnWidth(columnTitles, sheet);
        try {
            FileOutputStream outputStream = new FileOutputStream(EXCEL_FILE_PATH);
            workbook.write(outputStream);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addColumnTitles(String[] columnTitles, XSSFSheet sheet) {
        int rowNum = 0;
        int colNum = 0;
        Row titleRow = sheet.createRow(rowNum);
        CellStyle cellStyle = titleRow.getSheet().getWorkbook().createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);

        for (String columnTitle : columnTitles
        ) {
            Cell cell = titleRow.createCell(colNum++);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(columnTitle);
        }
    }

    private void addTaskEntries(XSSFSheet sheet, Collection<Task> tasks) {
        int rowNum = 1;
        for (Task task : tasks) {
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            Object[] taskFieldsArray = task.getObjectArrayFromTaskFields();
            for (Object field : taskFieldsArray) {
                Cell cell = row.createCell(colNum++);
                cell.setCellValue(field.toString());
            }
        }
    }

    private void adjustColumnWidth(String[] columnTitles, XSSFSheet sheet) {
        for (int i = 0; i < columnTitles.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }
}
