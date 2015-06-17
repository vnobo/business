package com.business.tools;

import com.business.domain.QueryPurItem;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by billb on 2015-05-08.
 */

public class OrderBuildExcel extends AbstractExcelView {

    private List<QueryPurItem> animalList;

    public OrderBuildExcel(List<QueryPurItem> queryPurItems) {
        this.animalList = queryPurItems;
    }

    protected void buildExcelDocument(Map<String, Object> model,
                                      HSSFWorkbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        HSSFSheet excelSheet = workbook.createSheet("Order List");
        excelSheet.setDefaultColumnWidth(20);
        HSSFRow excelHeader = excelSheet.createRow(0);
        excelHeader.createCell(0).setCellValue("订单号");
        excelHeader.createCell(1).setCellValue("用户");
        excelHeader.createCell(2).setCellValue("提交日期");
        excelHeader.createCell(3).setCellValue("完成日期");
        excelHeader.createCell(4).setCellValue("商品ID");
        excelHeader.createCell(5).setCellValue("商品名称");
        excelHeader.createCell(6).setCellValue("申请数量");
        excelHeader.createCell(7).setCellValue("实收数量");
        excelHeader.createCell(8).setCellValue("售价");
        excelHeader.createCell(9).setCellValue("申请金额");
        excelHeader.createCell(10).setCellValue("实收金额");

        int i = 1;
        double qty = 0, valqty = 0, qtyPriceSum = 0, valqtyPriceSum = 0;
        for (QueryPurItem p : animalList) {
            HSSFRow excelRow = excelSheet.createRow(i++);
            excelRow.createCell(0).setCellValue(p.getSheet().getSheetid());
            excelRow.createCell(1).setCellValue(p.getSheet().getUsername());
            excelRow.createCell(2).setCellValue(p.getSheet().getEditdate().toLocalDateTime().toString());
            excelRow.createCell(3).setCellValue(p.getSheet().getCheckdate().toLocalDateTime().toString());
            excelRow.createCell(4).setCellValue(p.getGoodsid());
            excelRow.createCell(5).setCellValue(p.getName());
            excelRow.createCell(6).setCellValue(p.getQty());
            excelRow.createCell(7).setCellValue(p.getValqty());
            excelRow.createCell(8).setCellValue(p.getPrice());
            excelRow.createCell(9).setCellValue(p.getQty() * p.getPrice());
            excelRow.createCell(10).setCellValue(p.getValqty() * p.getPrice());
            qty = qty + p.getQty();
            valqty = valqty + p.getValqty();
            qtyPriceSum = qtyPriceSum + p.getQty() * p.getPrice();
            valqtyPriceSum = valqtyPriceSum + p.getValqty() * p.getPrice();
        }

        HSSFRow excelRow = excelSheet.createRow(i);
        excelRow.createCell(0).setCellValue("合计");
        excelRow.createCell(6).setCellValue(qty);
        excelRow.createCell(7).setCellValue(valqty);
        excelRow.createCell(9).setCellValue(qtyPriceSum);
        excelRow.createCell(10).setCellValue(valqtyPriceSum);

    }

}
