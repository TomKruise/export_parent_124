package com.tom.domain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class PageBean implements Serializable {
    private long total;     //总记录数
    private int pages;      //总页数
    private List list;      //当前页记录
    private int pageNum;    //当前页
    private int pageSize;   //每页的数量

    private int prePage;    // 上一页
    private int nextPage;   //下一页
    private int navigateFirstPage; //导航条上的第一页（start）
    private int navigateLastPage;  //导航条上的最后一页（end）

    public PageBean(int pageNum, int pageSize, long total, List list) {
        // 封装 当前页和每页数量
        this.pageNum = pageNum;
        this.pageSize = pageSize;

        // 封装 总记录数 和 list
        this.total = total;
        this.list = list;

        // 计算 总页数
        this.pages = (int) Math.ceil(1.0 * total / pageSize);

        // 计算 上一页和下一页
        this.prePage = pageNum - 1 < 1 ? 1 : pageNum - 1;
        this.nextPage = pageNum + 1 > pages ? pages : pageNum + 1;

        // 计算 起始页和结束页
        if (pages < 5) {// 不足5页，有多少显示多少
            this.navigateFirstPage = 1;
            this.navigateLastPage = pages;
        } else { // 超过5页，前二后二
            this.navigateFirstPage = pageNum - 2;
            this.navigateLastPage = pageNum + 2;

            if (this.navigateFirstPage < 1) {
                this.navigateFirstPage = 1;
                this.navigateLastPage = this.navigateFirstPage + 4;
            }

            if (this.navigateLastPage > pages) {
                this.navigateLastPage = pages;
                this.navigateFirstPage = this.navigateLastPage - 4;
            }

        }
    }
}
