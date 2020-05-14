package com.tom.service.cargo.impl;

import com.tom.dao.cargo.ExportProductDao;
import com.tom.domain.cargo.ExportProduct;
import com.tom.domain.cargo.ExportProductExample;
import com.tom.service.cargo.ExportProductService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class ExportProductServiceImpl implements ExportProductService {

	@Autowired
	private ExportProductDao exportProductDao;

	@Override
	public List<ExportProduct> findAll(ExportProductExample example) {
		return exportProductDao.selectByExample(example);
	}
}
