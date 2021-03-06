package com.tom.export.vo;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="export")
public class ExportResult {
	private String exportId;
	private Integer state;
	private String remark;
	private Set<ExportProductResult> products;
	public String getExportId() {
		return exportId;
	}
	public void setExportId(String exportId) {
		this.exportId = exportId;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Set<ExportProductResult> getProducts() {
		return products;
	}
	public void setProducts(Set<ExportProductResult> products) {
		this.products = products;
	}
	
}
