package com.tom.service.cargo;

import com.tom.domain.cargo.Contract;
import com.tom.domain.cargo.ContractExample;
import com.github.pagehelper.PageInfo;
import com.tom.domain.vo.ContractProductVo;

import java.util.List;

// 购销合同
public interface ContractService {

	//根据id查询
    Contract findById(String id);

    //保存
    void save(Contract contract);

    //更新
    void update(Contract contract);

    //删除
    void delete(String id);

    /*
     *   查询所有
     * */
    List<Contract> findAll(ContractExample example);


    //分页查询
	public PageInfo findByPage(int pageNum, int pageSize, ContractExample example);

    List<ContractProductVo> findByShipTime(String shipTime, String companyId);

}
