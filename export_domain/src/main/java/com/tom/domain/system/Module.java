package com.tom.domain.system;

import lombok.Data;

import java.io.Serializable;

@Data
public class Module implements Serializable {
    private String id; // 菜单id
    private String parentId; // 父菜单id
    private String parentName; // 父菜单名称
    private String name; // 菜单名称
    private int layerNum;
    private int isLeaf; // 是否为叶子节点
    private String ico; // 图标
    private String cpermission; // 菜单权限
    private String curl; // 链接
    private String ctype;// 菜单类型   0 主菜单、1 左侧菜单、2 按钮、3 链接
    private String state; // 状态
    private String belong;// 从属关系 SaaS菜单、企业菜单
    private String cwhich;
    private int quoteNum;
    private String remark; // 备注
    private int orderNo; // 排序号
}
