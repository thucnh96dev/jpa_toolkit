package com.thucnh96.jpa.service.doc;

import com.thucnh96.jpa.modal.Column;
import com.thucnh96.jpa.modal.Table;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

public class GenDocument {
    private  List<Table> tables;
    public GenDocument(List<Table> tables){
        this.tables=tables;
    }

    public  Workbook genDoc(){
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("DATABASE");
        try {
            int rowCount = 0;
            for (Table table : this.tables){
                    Row row = sheet.createRow(rowCount);
                    Cell index =  row.createCell(0);
                    Cell tableName =  row.createCell(1);
                    index.setCellValue(rowCount);
                    tableName.setCellValue(table.getName());
                    rowCount ++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        Sheet sheet1 = workbook.createSheet("DATABASE1");
        try {
            int rowCount = 0;
            for (Table table : this.tables){
                for (Column column : table.getColums()) {
                    Row row = sheet1.createRow(rowCount);
                    Cell index =  row.createCell(0);
                    Cell tableName =  row.createCell(1);
                    Cell columnName =  row.createCell(2);
                    Cell dataType =  row.createCell(3);
                    Cell length =  row.createCell(4);
                    Cell unique =  row.createCell(5);
                    Cell note =  row.createCell(6);
                    Cell defaultValue =  row.createCell(7);
                    index.setCellValue(rowCount);
                    columnName.setCellValue(column.getFieldName());
                    dataType.setCellValue(column.getDataType());
                    defaultValue.setCellValue(column.getDefaultValue());
                    length.setCellValue("");
                    unique.setCellValue(column.getKey());
                    note.setCellValue(column.getExtra());
                    tableName.setCellValue(table.getName());
                    rowCount ++;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return workbook;
    }
}
