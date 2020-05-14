package com.tom.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tom.domain.vo.ContractProductVo;
import com.tom.service.cargo.ContractService;
import com.tom.utils.DownloadUtils;
import com.tom.web.controller.BaseController;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping(path = "/cargo/contract", name = "出货表打印")
public class OutContractController extends BaseController {
    @Reference
    private ContractService contractService;

    @RequestMapping(path = "/print", name = "跳转到打印的搜索页面")
    public String print() {

        return "cargo/print/contract-print";
    }

    @RequestMapping(path = "/printExcel", name = "出货表打印")
    public void printExcel(String inputDate) throws IOException {
        List<ContractProductVo> productVoList = contractService.findByShipTime(inputDate, getCompanyId());

        Workbook wb = new XSSFWorkbook();

        Sheet sheet = wb.createSheet("Sheet1");

        Row row = sheet.createRow(0);

        Cell cell = row.createCell(1);

        String headTitle = inputDate.replace("-", "年") + "月份出货表";

        cell.setCellValue(headTitle);

        row = sheet.createRow(1);

        cell.setCellStyle(bigTitle(wb));

        String[] titles = new String[] {"", "客户", "合同号", "货号", "数量", "工厂", "工厂交期", "船期", "贸易条款"};

        for (int i = 1; i < titles.length; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(title(wb));
            cell.setCellValue(titles[i]);
        }

        int index = 2;
        for (ContractProductVo vo : productVoList) {
            row = sheet.createRow(index++);//从第三行开始创建
            row.setHeightInPoints(24); // 行高
            // 客户名称单元格
            cell = row.createCell(1);
            // cell.setCellStyle(text(wb)); // 数据样式
            cell.setCellValue(vo.getCustomName());
            // 合同号单元格
            cell = row.createCell(2);
            // cell.setCellStyle(text(wb)); // 数据样式
            cell.setCellValue(vo.getContractNo());
            // 货号单元格
            cell = row.createCell(3);
            // cell.setCellStyle(text(wb)); // 数据样式
            cell.setCellValue(vo.getProductNo());
            // 数量单元格
            cell = row.createCell(4);
            // cell.setCellStyle(text(wb)); // 数据样式
            cell.setCellValue(vo.getCnumber());
            // 工厂单元格
            cell = row.createCell(5);
            // cell.setCellStyle(text(wb)); // 数据样式
            cell.setCellValue(vo.getFactoryName());
            // 工厂交期单元格
            cell = row.createCell(6);
            // cell.setCellStyle(text(wb)); // 数据样式
            // 将日期对象转换为字符串
            cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(vo.getDeliveryPeriod()));
            // 船期单元格
            cell = row.createCell(7);
            // cell.setCellStyle(text(wb)); // 数据样式
            // 将日期对象转换为字符串
            cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(vo.getShipTime()));
            // 贸易条款单元格
            cell = row.createCell(8);
            // cell.setCellStyle(text(wb)); // 数据样式
            cell.setCellValue(vo.getTradeTerms());
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        wb.write(byteArrayOutputStream);
        DownloadUtils.download(byteArrayOutputStream,response, "出货表.xlsx");
    }

    @RequestMapping(path = "/printExcelTemplate", name = "出货表模版打印")
    public void printExcelTemplate(String inputDate) throws IOException {
        List<ContractProductVo> list = contractService.findByShipTime(inputDate, getCompanyId());

        String realPath = session.getServletContext().getRealPath("/make/xlsprint/tOUTPRODUCT.xlsx");

        Workbook wb = new XSSFWorkbook(realPath);

        Sheet sheet = wb.getSheetAt(0);

        Row row = sheet.getRow(0);

        Cell cell = row.getCell(1);

        cell.setCellValue(inputDate.replace("-", "年") + "月份出货表");

        row = sheet.getRow(2);

        CellStyle[] css = new CellStyle[9];

        for (int i = 1; i < css.length; i++) {
            cell = row.getCell(i);
            css[i] = cell.getCellStyle();
        }

        int index = 2;
        for (ContractProductVo vo : list) {
            row = sheet.createRow(index);// 从第三行开始创建
            row.setHeightInPoints(24); // 行高
            // 客户名称单元格
            cell = row.createCell(1);
            cell.setCellStyle(css[1]); // 数据样式
            cell.setCellValue(vo.getCustomName());
            // 合同号单元格
            cell = row.createCell(2);
            cell.setCellStyle(css[2]); // 数据样式
            cell.setCellValue(vo.getContractNo());
            // 货号单元格
            cell = row.createCell(3);
            cell.setCellStyle(css[3]); // 数据样式
            cell.setCellValue(vo.getProductNo());
            // 数量单元格
            cell = row.createCell(4);
            cell.setCellStyle(css[4]); // 数据样式
            cell.setCellValue(vo.getCnumber());
            // 工厂单元格
            cell = row.createCell(5);
            cell.setCellStyle(css[5]); // 数据样式
            cell.setCellValue(vo.getFactoryName());
            // 工厂交期单元格
            cell = row.createCell(6);
            cell.setCellStyle(css[6]); // 数据样式
            // 将日期对象转换为字符串
            cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(vo.getDeliveryPeriod()));
            // 船期单元格
            cell = row.createCell(7);
            cell.setCellStyle(css[7]); // 数据样式
            // 将日期对象转换为字符串
            cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(vo.getShipTime()));
            // 贸易条款单元格
            cell = row.createCell(8);
            cell.setCellStyle(css[8]); // 数据样式
            cell.setCellValue(vo.getTradeTerms());

            index++;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // 将workbook数据设置到字节数组输出流
        wb.write(byteArrayOutputStream);
        DownloadUtils.download(byteArrayOutputStream, response, "出货表.xlsx");
    }

    //大标题的样式
    public CellStyle bigTitle(Workbook wb){
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)16);
        font.setBold(true);//字体加粗
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);				//横向居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);		//纵向居中
        return style;
    }

    //小标题的样式
    public CellStyle title(Workbook wb){
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short)12);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);				//横向居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);		//纵向居中
        style.setBorderTop(BorderStyle.THIN);						//上细线
        style.setBorderBottom(BorderStyle.THIN);					//下细线
        style.setBorderLeft(BorderStyle.THIN);						//左细线
        style.setBorderRight(BorderStyle.THIN);						//右细线
        return style;
    }

    //文字样式
    public CellStyle text(Workbook wb){
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short)10);

        style.setFont(font);

        style.setAlignment(HorizontalAlignment.LEFT);				//横向居左
        style.setVerticalAlignment(VerticalAlignment.CENTER);		//纵向居中
        style.setBorderTop(BorderStyle.THIN);						//上细线
        style.setBorderBottom(BorderStyle.THIN);					//下细线
        style.setBorderLeft(BorderStyle.THIN);						//左细线
        style.setBorderRight(BorderStyle.THIN);						//右细线

        return style;
    }
}
