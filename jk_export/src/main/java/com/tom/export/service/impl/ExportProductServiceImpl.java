package com.tom.export.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.tom.export.dao.BaseDao;
import com.tom.export.domain.ExportProduct;
import com.tom.export.pagination.Page;
import com.tom.export.service.ExportProductService;

/**
 * @Description:	ExportService接口
 * @Author:			传智播客 java学院	传智宋江
 * @Company:		http://java.tom.com
 * @CreateDate:		2016-7-6 19:46:34
 */

public class ExportProductServiceImpl implements ExportProductService {
	//spring注入dao
	private BaseDao baseDao;
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public List<ExportProduct> find(String hql, Class<ExportProduct> entityClass, Object[] params) {
		return baseDao.find(hql, ExportProduct.class, params);
	}

	public ExportProduct get(Class<ExportProduct> entityClass, Serializable id) {
		return baseDao.get(ExportProduct.class, id);
	}

	public Page<ExportProduct> findPage(String hql, Page<ExportProduct> page, Class<ExportProduct> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, ExportProduct.class, params);
	}

	public void saveOrUpdate(ExportProduct entity) {
		if(entity.getId()==null){								//代表新增
										//状态：0停用1启用 默认启用
		}
		baseDao.saveOrUpdate(entity);
	}



	public void saveOrUpdateAll(Collection<ExportProduct> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	public void deleteById(Class<ExportProduct> entityClass, Serializable id) {
		baseDao.deleteById(ExportProduct.class, id);
	}

	public void delete(Class<ExportProduct> entityClass, Serializable[] ids) {
		baseDao.delete(ExportProduct.class, ids);
	}

}

