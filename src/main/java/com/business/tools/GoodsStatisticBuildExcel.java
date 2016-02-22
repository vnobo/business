package com.business.tools;

import com.business.domain.QueryPurStatistic;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by billb on 2015/6/8.
 */
public class GoodsStatisticBuildExcel extends AbstractExcelView {
    private List<QueryPurStatistic> animalList;

    public GoodsStatisticBuildExcel(List<QueryPurStatistic> queryPurItems) {
        this.animalList = queryPurItems;
    }

    protected void buildExcelDocument(Map<String, Object> model,
                                      HSSFWorkbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        HSSFSheet excelSheet = workbook.createSheet("商品销售统计");
        excelSheet.setDefaultColumnWidth(10);
        HSSFRow excelHeader = excelSheet.createRow(0);
        excelHeader.createCell(0).setCellValue("商品ID");
        excelHeader.createCell(1).setCellValue("商品名称");
        excelHeader.createCell(2).setCellValue("申请数量");
        excelHeader.createCell(3).setCellValue("退货数量");
        excelHeader.createCell(4).setCellValue("实收数量");
        excelHeader.createCell(5).setCellValue("单价");
        excelHeader.createCell(6).setCellValue("申请金额");
        excelHeader.createCell(7).setCellValue("退货金额");
        excelHeader.createCell(8).setCellValue("实收金额");

        int i = 1;
        double qty = 0, retqty = 0, valqty = 0, qtyPriceSum = 0, retPriceSum = 0, valqtyPriceSum = 0;
        for (QueryPurStatistic p : animalList) {
            HSSFRow excelRow = excelSheet.createRow(i++);
            excelRow.createCell(0).setCellValue(p.getGoodsId());
            excelRow.createCell(1).setCellValue(p.getName());
            excelRow.createCell(2).setCellValue(p.getCountQty());
            excelRow.createCell(3).setCellValue(p.getCountRetQty());
            excelRow.createCell(4).setCellValue(p.getCountValQty() - p.getCountRetQty());
            excelRow.createCell(5).setCellValue(p.getPrice());
            excelRow.createCell(6).setCellValue(p.getCountQty() * p.getPrice());
            excelRow.createCell(7).setCellValue(p.getCountRetQty() * p.getPrice());
            excelRow.createCell(8).setCellValue((p.getCountValQty() - p.getCountRetQty()) * p.getPrice());
            qty = qty + p.getCountQty();
            retqty = retqty + p.getCountRetQty();
            valqty = valqty + p.getCountValQty();
            qtyPriceSum += p.getCountQty() * p.getPrice();
            retPriceSum += p.getCountRetQty() * p.getPrice();
            valqtyPriceSum += p.getCountValQty() * p.getPrice() - retPriceSum;
        }

        HSSFRow excelRow = excelSheet.createRow(i);
        excelRow.createCell(0).setCellValue("合计");
        excelRow.createCell(2).setCellValue(qty);
        excelRow.createCell(3).setCellValue(retqty);
        excelRow.createCell(4).setCellValue(valqty);
        excelRow.createCell(6).setCellValue(qtyPriceSum);
        excelRow.createCell(7).setCellValue(retPriceSum);
        excelRow.createCell(8).setCellValue(valqtyPriceSum);

    }
}
