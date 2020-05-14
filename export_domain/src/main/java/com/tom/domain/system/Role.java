package com.tom.domain.system;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Role implements Serializable {
    private String id; // 角色id
    private String name; // 角色名称
    private String remark; // 备注
    private String companyId; // 企业id
    private String companyName; // 企业名称
    private Integer orderNo; // 排序号
    private String createBy; // 创建人
    private String createDept; // 创建人部门
    private Date createTime; // 创建时间
    private String updateBy; // 更新人
    private Date updateTime; // 更新时间
}
