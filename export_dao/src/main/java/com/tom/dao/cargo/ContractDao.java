package com.tom.dao.cargo;

import com.tom.domain.cargo.Contract;
import com.tom.domain.cargo.ContractExample;
import com.tom.domain.vo.ContractProductVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ContractDao {

	//删除
    int deleteByPrimaryKey(String id);

	//保存
    int insertSelective(Contract record);

	//条件查询
    List<Contract> selectByExample(ContractExample example);

	//id查询
    Contract selectByPrimaryKey(String id);

	//更新
    int updateByPrimaryKeySelective(Contract record);

    List<ContractProductVo> findByShipTime(@Param("shipTime") String shipTime, @Param("companyId") String companyId);
}