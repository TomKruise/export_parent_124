package com.tom.service.cargo.impl;

import com.tom.dao.cargo.ContractDao;
import com.tom.dao.cargo.ContractProductDao;
import com.tom.dao.cargo.ExtCproductDao;
import com.tom.domain.cargo.Contract;
import com.tom.domain.cargo.ExtCproduct;
import com.tom.domain.cargo.ExtCproductExample;
import com.tom.service.cargo.ExtCproductService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Service
public class ExtCproductServiceImpl implements ExtCproductService {

    @Autowired
    private ContractProductDao contractProductDao;

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private ExtCproductDao extCproductDao;

    @Override
    public void save(ExtCproduct extc) {
        extc.setId(UUID.randomUUID().toString());
        Double money = 0D;
        Integer num = extc.getCnumber();
        Double price = extc.getPrice();
        if (num != null && price != null) {
            money = num * price;
        }

        extc.setAmount(money);

        extCproductDao.insertSelective(extc);

        Contract contract = contractDao.selectByPrimaryKey(extc.getContractId());

        contract.setTotalAmount(contract.getTotalAmount() + money);

        contract.setExtNum(contract.getExtNum() + 1);

        contractDao.updateByPrimaryKeySelective(contract);
    }

    @Override
    public void update(ExtCproduct extc) {
        Double oldAmount = extCproductDao.selectByPrimaryKey(extc.getId()).getAmount();

        Double newAmount = 0D;

        Integer num = extc.getCnumber();
        Double price = extc.getPrice();

        if (null != num && null != price) {
            newAmount = num * price;
        }

        extc.setAmount(newAmount);

        extCproductDao.updateByPrimaryKeySelective(extc);

        Contract contract = contractDao.selectByPrimaryKey(extc.getContractId());

        contract.setTotalAmount(contract.getTotalAmount() - oldAmount + newAmount);

        contractDao.updateByPrimaryKeySelective(contract);
    }

    @Override
    public void delete(String id) {
        ExtCproduct extCproduct = extCproductDao.selectByPrimaryKey(id);

        extCproductDao.deleteByPrimaryKey(id);

        Contract contract = contractDao.selectByPrimaryKey(extCproduct.getContractId());

        contract.setTotalAmount(contract.getTotalAmount() - extCproduct.getAmount());

        contract.setExtNum(contract.getExtNum() - 1);

        contractDao.updateByPrimaryKeySelective(contract);
    }

    @Override
    public ExtCproduct findById(String id) {
        return extCproductDao.selectByPrimaryKey(id);
    }

    @Override
    public List<ExtCproduct> findAll(ExtCproductExample example) {
        return extCproductDao.selectByExample(example);
    }

    @Override
    public PageInfo findByPage(int pageNum, int pageSize, ExtCproductExample example) {
        PageHelper.startPage(pageNum, pageSize);
        List<ExtCproduct> list = extCproductDao.selectByExample(example);
        return new PageInfo(list);
    }
}
