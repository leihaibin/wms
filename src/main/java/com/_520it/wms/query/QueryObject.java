package com._520it.wms.query;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryObject {
    private int currentPage = 1;
    private int pageSize    = 10;

    public int getStart() {
        return (currentPage - 1) * pageSize;
    }
    //把空字符串换成null
    public String empty2null(String str){
        return hasLength(str)?str:null;
    }

    public boolean hasLength(String str){
        return str != null && !"".equals(str.trim());
    }
}
