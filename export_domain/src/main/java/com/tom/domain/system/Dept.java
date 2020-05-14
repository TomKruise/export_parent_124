package com.tom.domain.system;

import lombok.Data;

import java.io.Serializable;

@Data
public class Dept implements Serializable {
    private String id;        // 部门id
    private String deptName; // 部门名称
    private Dept parent;     // 父部门
    private String state;   // 状态  1：可用、0：不可用
    private String companyId; // 企业id
    private String companyName; // 企业名称
}
