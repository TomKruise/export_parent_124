package com.tom.service.cargo.impl;

import com.tom.dao.cargo.ContractDao;
import com.tom.dao.cargo.ContractProductDao;
import com.tom.dao.cargo.ExtCproductDao;
import com.tom.domain.cargo.*;
import com.tom.domain.vo.ContractProductVo;
import com.tom.service.cargo.ContractService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service // 使用阿里巴巴提供
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private ContractProductDao contractProductDao;

    @Autowired
    private ExtCproductDao extCproductDao;

    @Override
    public Contract findById(String id) {
        return contractDao.selectByPrimaryKey(id);
    }

    @Override
    public void save(Contract contract) {
        // 指定合同id
        contract.setId(UUID.randomUUID().toString());
        // 指定合同创建时间
        contract.setCreateTime(new Date());
        // 指定合同状态
        contract.setState(0);// 草稿
        contractDao.insertSelective(contract);// 动态属性保存
    }

    @Override
    public void update(Contract contract) {
        contractDao.updateByPrimaryKeySelective(contract);// 动态属性修改
    }

    @Override
    public void delete(String id) {
        Contract contract = contractDao.selectByPrimaryKey(id);

        ContractProductExample contractProductExample = new ContractProductExample();
        contractProductExample.createCriteria().andContractIdEqualTo(contract.getId());
        List<ContractProduct> contractProducts = contractProductDao.selectByExample(contractProductExample);

        for (ContractProduct contractProduct : contractProducts) {
            ExtCproductExample extCproductExample = new ExtCproductExample();
            extCproductExample.createCriteria().andContractProductIdEqualTo(contractProduct.getId());
            List<ExtCproduct> extCproducts = extCproductDao.selectByExample(extCproductExample);

            for (ExtCproduct extCproduct : extCproducts) {
                extCproductDao.deleteByPrimaryKey(extCproduct.getId());
            }
            contractProductDao.deleteByPrimaryKey(contractProduct.getId());
        }

        contractDao.deleteByPrimaryKey(id);
    }

    @Override
    public List<Contract> findAll(ContractExample example) {
        return contractDao.selectByExample(example);
    }

    @Override
    public PageInfo findByPage(int pageNum, int pageSize, ContractExample example) {
        PageHelper.startPage(pageNum, pageSize);
        List<Contract> list = contractDao.selectByExample(example);
        return new PageInfo(list);
    }

    @Override
    public List<ContractProductVo> findByShipTime(String shipTime, String companyId) {
        return contractDao.findByShipTime(shipTime, companyId);
    }
}
