package com.tom.domain.system;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysLog implements Serializable {
    private String id;
    private String userName; // 操作人
    private String ip;
    private Date time;
    private String method;
    private String action;
    private String companyId; // 操作人所属公司id
    private String companyName; // 操作人所属公司名称
}
