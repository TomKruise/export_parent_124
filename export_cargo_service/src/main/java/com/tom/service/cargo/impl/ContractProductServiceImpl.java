package com.tom.service.cargo.impl;

import com.tom.dao.cargo.ContractDao;
import com.tom.dao.cargo.ContractProductDao;
import com.tom.dao.cargo.ExtCproductDao;
import com.tom.domain.cargo.*;
import com.tom.service.cargo.ContractProductService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Service
public class ContractProductServiceImpl implements ContractProductService {

    @Autowired
    private ContractProductDao contractProductDao;

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private ExtCproductDao extCproductDao;
	

    @Override
    public void save(ContractProduct cp) {
        cp.setId(UUID.randomUUID().toString());

        Double money = 0d;
        Integer number = cp.getCnumber();
        Double price = cp.getPrice();
        if (null != number && null != price) {
            money = price * number;
        }

        cp.setAmount(money);

        contractProductDao.insertSelective(cp);

        Contract contract = contractDao.selectByPrimaryKey(cp.getContractId());

        Double totalAmount = ((null == contract.getTotalAmount()) ? money : (money + contract.getTotalAmount()));

        contract.setTotalAmount(totalAmount);

        contract.setProNum(contract.getProNum() + 1);

        contractDao.updateByPrimaryKeySelective(contract);


    }

    @Override
    public void update(ContractProduct cp) {
        Double oldAmount = contractProductDao.selectByPrimaryKey(cp.getId()).getAmount();

        Double newAmount = 0d;
        Integer number = cp.getCnumber();
        Double price = cp.getPrice();
        if (null != number && null != price) {
            newAmount = price * number;
        }

        cp.setAmount(newAmount);

        contractProductDao.updateByPrimaryKeySelective(cp);

        Contract contract = contractDao.selectByPrimaryKey(cp.getContractId());

        contract.setTotalAmount(contract.getTotalAmount() - oldAmount + newAmount);

        contractDao.updateByPrimaryKeySelective(contract);
    }

    @Override
    public void delete(String id) {
        ContractProduct contractProduct = contractProductDao.selectByPrimaryKey(id);

        ExtCproductExample extCproductExample = new ExtCproductExample();
        ExtCproductExample.Criteria extCproductExampleCriteria = extCproductExample.createCriteria();
        extCproductExampleCriteria.andContractProductIdEqualTo(id);
        List<ExtCproduct> extCproductList = extCproductDao.selectByExample(extCproductExample);

        Double amount = contractProduct.getAmount();
        for (ExtCproduct cproduct : extCproductList) {
            amount += cproduct.getAmount();
            extCproductDao.deleteByPrimaryKey(cproduct.getId());
        }

        contractProductDao.deleteByPrimaryKey(id);

        Contract contract = contractDao.selectByPrimaryKey(contractProduct.getContractId());

        contract.setTotalAmount(contract.getTotalAmount() - amount);

        contract.setProNum(contract.getProNum() - 1);

        contract.setExtNum(contract.getExtNum() - extCproductList.size());

        contractDao.updateByPrimaryKeySelective(contract);
    }

    @Override
    public ContractProduct findById(String id) {
        return contractProductDao.selectByPrimaryKey(id);
    }

    @Override
    public List<ContractProduct> findAll(ContractProductExample example) {
        return contractProductDao.selectByExample(example);
    }

    @Override
    public PageInfo findByPage(int pageNum, int pageSize, ContractProductExample example) {
        PageHelper.startPage(pageNum, pageSize);
        List<ContractProduct> list = contractProductDao.selectByExample(example);
        return new PageInfo(list);
    }
}
