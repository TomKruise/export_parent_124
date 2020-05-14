package com.tom.service.cargo.impl;

import com.tom.dao.cargo.*;
import com.tom.domain.cargo.*;
import com.tom.domain.vo.ExportProductVo;
import com.tom.domain.vo.ExportVo;
import com.tom.service.cargo.ExportService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Service
public class ExportServiceImpl implements ExportService {

    @Autowired
    private ExportDao exportDao; // 报运dao

    @Autowired
    private ExportProductDao exportProductDao;//报运单商品dao

    @Autowired
    private ExtEproductDao extEproductDao;  //报运单附件Dao

    @Autowired
    private ContractDao contractDao; // 合同dao

    @Autowired
    private ContractProductDao contractProductDao; //合同货物dao

    @Autowired
    private ExtCproductDao extCproductDao;  //合同附件Dao

    //保存

    public void save(Export export) {
        export.setId(UUID.randomUUID().toString());

        String[] contractIds = export.getContractIds().split(",");

        String customerContract = "";

        int proNum = 0;
        int extNum = 0;
        for (String contractId : contractIds) {
            Contract contract = contractDao.selectByPrimaryKey(contractId);
            customerContract += contract.getContractNo() + " ";
            proNum += contract.getProNum();
            extNum += contract.getExtNum();

            contract.setState(2);
            contractDao.updateByPrimaryKeySelective(contract);
        }

        export.setCustomerContract(customerContract);
        export.setProNum(proNum);
        export.setExtNum(extNum);
        export.setState(0);
        exportDao.insertSelective(export);

        ContractProductExample cpExample = new ContractProductExample();
        cpExample.createCriteria().andContractIdIn(Arrays.asList(contractIds));
        List<ContractProduct> contractProducts = contractProductDao.selectByExample(cpExample);
        for (ContractProduct cp : contractProducts) {
            ExportProduct ep = new ExportProduct();
            BeanUtils.copyProperties(cp, ep);
            ep.setId(UUID.randomUUID().toString());
            ep.setExportId(export.getId());
            exportProductDao.insertSelective(ep);

            ExtCproductExample extCproductExample = new ExtCproductExample();
            extCproductExample.createCriteria().andContractProductIdEqualTo(cp.getId());
            List<ExtCproduct> extCproducts = extCproductDao.selectByExample(extCproductExample);
            for (ExtCproduct extCproduct : extCproducts) {
                ExtEproduct exte = new ExtEproduct();
                BeanUtils.copyProperties(extCproduct, exte);
                exte.setId(UUID.randomUUID().toString());
                exte.setExportId(export.getId());
                exte.setExportProductId(ep.getId());
                extEproductDao.insertSelective(exte);
            }
        }
    }

    //更新
    public void update(Export export) {
        exportDao.updateByPrimaryKeySelective(export);
        List<ExportProduct> exportProducts = export.getExportProducts();
        if (exportProducts != null && exportProducts.size() > 0) {
            for (ExportProduct exportProduct : exportProducts) {
                exportProductDao.updateByPrimaryKeySelective(exportProduct);
            }
        }
    }

    //删除
    public void delete(String id) {

    }

    //根据id查询
    public Export findById(String id) {
        return exportDao.selectByPrimaryKey(id);
    }

    //分页
    public PageInfo findByPage(int pageNum, int pageSize, ExportExample example) {
        PageHelper.startPage(pageNum, pageSize);
        List<Export> list = exportDao.selectByExample(example);
        return new PageInfo(list);
    }

    @Override
    public void exportE(String id) {
        Export export = exportDao.selectByPrimaryKey(id);
        ExportProductExample exportProductExample = new ExportProductExample();
        exportProductExample.createCriteria().andExportIdEqualTo(id);

        List<ExportProduct> exportProducts = exportProductDao.selectByExample(exportProductExample);

        ExportVo exportVo = new ExportVo();

        BeanUtils.copyProperties(export, exportVo);
        exportVo.setExportId(id);

        List<ExportProductVo> productVos = new ArrayList<>();

        for (ExportProduct exportProduct : exportProducts) {
            ExportProductVo exportProductVo = new ExportProductVo();
            BeanUtils.copyProperties(exportProduct, exportProductVo);
            exportProductVo.setExportProductId(exportProduct.getId());

            productVos.add(exportProductVo);
        }

        exportVo.setProducts(productVos);

        WebClient webClient = WebClient.create("http://localhost:8088/ws/export/user");

        webClient.post(exportVo);

        export.setState(1);//已报运

        exportDao.updateByPrimaryKeySelective(export);
    }

    @Override
    public void updateE(ExportResult exportResult) {
        Export export = exportDao.selectByPrimaryKey(exportResult.getExportId());
        export.setState(exportResult.getState());
        export.setRemark(exportResult.getRemark());
        exportDao.updateByPrimaryKeySelective(export);

        Set<ExportProductResult> products = exportResult.getProducts();

        if (products != null && products.size() > 0) {
            for (ExportProductResult product : products) {
                ExportProduct exportProduct = exportProductDao.selectByPrimaryKey(product.getExportProductId());
                exportProduct.setTax(product.getTax());
                exportProductDao.updateByPrimaryKeySelective(exportProduct);
            }
        }
    }
}
