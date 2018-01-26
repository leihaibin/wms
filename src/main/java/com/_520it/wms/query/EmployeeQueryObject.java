package com._520it.wms.query;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeQueryObject extends QueryObject {
    private String keyword;
    private Long deptId=-1L;
    public String getKeyword(){

        return super.empty2null(keyword);
    }
}
