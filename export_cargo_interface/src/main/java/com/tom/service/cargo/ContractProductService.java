package com.tom.service.cargo;

import com.tom.domain.cargo.ContractProduct;
import com.tom.domain.cargo.ContractProductExample;
import com.github.pagehelper.PageInfo;
import com.tom.domain.vo.ContractProductVo;

import java.util.List;

// 货物
public interface ContractProductService {

    /**
     * 保存
     */
    void save(ContractProduct contractProduct);

    /**
     * 更新
     */
    void update(ContractProduct contractProduct);

    /**
     * 删除
     */
    void delete(String id);

    /**
     * 根据id查询
     */
    ContractProduct findById(String id);

    /*
     *   查询所有
     * */
    List<ContractProduct> findAll(ContractProductExample example);

    /**
     * 分页查询
     */
    PageInfo findByPage(int pageNum, int pageSize, ContractProductExample example);

}
