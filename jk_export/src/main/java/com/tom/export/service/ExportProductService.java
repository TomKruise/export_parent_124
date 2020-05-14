package com.tom.export.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.tom.export.domain.ExportProduct;
import com.tom.export.pagination.Page;

/**
 * @Description:	ExportService接口
 * @Author:			传智播客 java学院	传智宋江
 * @Company:		http://java.tom.com
 * @CreateDate:		2016-7-6 19:46:34
 */

public interface ExportProductService {

	public List<ExportProduct> find(String hql, Class<ExportProduct> entityClass, Object[] params);
	public ExportProduct get(Class<ExportProduct> entityClass, Serializable id);
	public Page<ExportProduct> findPage(String hql, Page<ExportProduct> page, Class<ExportProduct> entityClass, Object[] params);
	
	public void saveOrUpdate(ExportProduct entity);
	public void saveOrUpdateAll(Collection<ExportProduct> entitys);
	
	public void deleteById(Class<ExportProduct> entityClass, Serializable id);
	public void delete(Class<ExportProduct> entityClass, Serializable[] ids);
}
