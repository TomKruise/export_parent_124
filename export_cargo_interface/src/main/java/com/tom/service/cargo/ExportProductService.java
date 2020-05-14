package com.tom.service.cargo;


import com.tom.domain.cargo.ExportProduct;
import com.tom.domain.cargo.ExportProductExample;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface ExportProductService {

	//根据条件查询
	List<ExportProduct> findAll(ExportProductExample example);
}
