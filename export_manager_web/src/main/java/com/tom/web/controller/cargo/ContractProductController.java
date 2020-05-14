package com.tom.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.tom.domain.cargo.ContractProduct;
import com.tom.domain.cargo.ContractProductExample;
import com.tom.domain.cargo.Factory;
import com.tom.domain.cargo.FactoryExample;
import com.tom.service.cargo.ContractProductService;
import com.tom.service.cargo.FactoryService;
import com.tom.utils.ImageUploadUtils;
import com.tom.web.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/cargo/contractProduct", name = "货物管理")
public class ContractProductController extends BaseController {
    @Reference
    private ContractProductService contractProductService;

    @Reference
    private FactoryService factoryService;

    @RequestMapping(path = "/list", name = "货物查询")
    public String list(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize, String contractId) {
        ContractProductExample productExample = new ContractProductExample();
        ContractProductExample.Criteria exampleCriteria = productExample.createCriteria();
        exampleCriteria.andContractIdEqualTo(contractId);

        PageInfo page = contractProductService.findByPage(pageNum, pageSize, productExample);

        request.setAttribute("page", page);

        FactoryExample factoryExample = new FactoryExample();
        FactoryExample.Criteria factoryExampleCriteria = factoryExample.createCriteria();
        factoryExampleCriteria.andCtypeEqualTo("货物");
        List<Factory> factoryList = factoryService.findAll(factoryExample);

        request.setAttribute("factoryList", factoryList);

        request.setAttribute("contractId", contractId);
        return "cargo/product/product-list";
    }

    @RequestMapping(path = "/edit", name = "save or update")
    public String edit(ContractProduct cp, MultipartFile productPhoto) throws IOException {
        cp.setCompanyId(getCompanyId());
        cp.setCompanyName(getCompanyName());

        //image upload
        if (null != productPhoto && !productPhoto.isEmpty()) {
            //Upload to Qi niu cloud
            String imgUrl = ImageUploadUtils.upload(productPhoto.getInputStream());

            cp.setProductImage(imgUrl);
        }

        if (StringUtils.isEmpty(cp.getId())) {
            contractProductService.save(cp);
        } else {
            contractProductService.update(cp);
        }

        return "redirect:/cargo/contractProduct/list.do?contractId=" + cp.getContractId();
    }

    @RequestMapping(path = "/toUpdate", name = "update")
    public String update(String id) {
        ContractProduct product = contractProductService.findById(id);
        request.setAttribute("contractProduct", product);

        FactoryExample factoryExample = new FactoryExample();
        FactoryExample.Criteria factoryExampleCriteria = factoryExample.createCriteria();
        factoryExampleCriteria.andCtypeEqualTo("货物");
        List<Factory> factoryList = factoryService.findAll(factoryExample);

        return "cargo/product/product-update";
    }

    @RequestMapping(path = "/delete", name = "delete")
    public String delete(String id, String contractId) {
        contractProductService.delete(id);

        return "redirect:/cargo/contractProduct/list.do?contractId=" + contractId;
    }

    @RequestMapping(path = "/toImport", name = "进入货物上传页面")
    public String toImport(String contractId) {

        request.setAttribute("contractId", contractId);
        return "cargo/product/product-import";
    }

    @RequestMapping(path = "/import", name = "货物批量上传")
    public String importExcel(String contractId, MultipartFile file) throws IOException {
        List<ContractProduct> list = new ArrayList<>();

        Workbook wb = new XSSFWorkbook(file.getInputStream());

        Sheet sheet = wb.getSheetAt(0);

        int rowLength = sheet.getLastRowNum();

        for (int i = 1; i < rowLength + 1; i++) {
            Row row = sheet.getRow(i);

            Object[] objs = new Object[10];

            short cellNum = row.getLastCellNum();

            for (int j = 1; j < cellNum; j++) {
                Cell cell = row.getCell(j);
                objs[j] = getCellValue(cell);
            }

            ContractProduct cp = new ContractProduct(objs, getCompanyId(), getCompanyName());

            cp.setContractId(contractId);

            list.add(cp);

        }

        for (ContractProduct contractProduct : list) {
            contractProductService.save(contractProduct);
        }

        return "redirect:/cargo/contract/list.do";
    }

    //解析每个单元格的数据
    public static Object getCellValue(Cell cell) {
        Object obj = null;
        CellType cellType = cell.getCellType(); //获取单元格数据类型
        switch (cellType) {
            case STRING: {
                obj = cell.getStringCellValue();//字符串
                break;
            }
            //excel默认将日期也理解为数字
            case NUMERIC: {
                if (DateUtil.isCellDateFormatted(cell)) {
                    obj = cell.getDateCellValue();//日期
                } else {
                    obj = cell.getNumericCellValue(); // 数字
                }
                break;
            }
            case BOOLEAN: {
                obj = cell.getBooleanCellValue(); // 布尔
                break;
            }
            default: {
                break;
            }
        }
        return obj;
    }
}
