package com._520it.wms.query;

import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public class PageResult {
    public static final PageResult EMPTY_PAGE = new PageResult(Collections.EMPTY_LIST, 0, 1, 1);
    private List<?> result;
    private int     totalCount;

    private int currentPage = 1;
    private int pageSize    = 10;

    private int prevPage;
    private int nextPage;
    private int totalPage;

    public PageResult(List<?> result, int totalCount, int currentPage, int pageSize) {
        this.result = result;
        this.totalCount = totalCount;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        this.totalPage=totalPage == 0 ? 1 : this.totalPage;
        this.prevPage = currentPage - 1 > 1 ? currentPage - 1 : 1;
        this.nextPage = currentPage + 1 < totalPage ? currentPage + 1 : totalPage;
    }
}
