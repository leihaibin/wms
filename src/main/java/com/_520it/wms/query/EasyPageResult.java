package com._520it.wms.query;

import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public class EasyPageResult  {
    public static final EasyPageResult EMPTY_PAGE = new EasyPageResult(1,Collections.EMPTY_LIST);
    private int     total;
    private List<?> rows;



    public EasyPageResult(int total,List<?> rows) {
        this.rows = rows;
        this.total = total;
    }

}
