package com._520it.wms.service;

import com._520it.wms.domain.Employee;
import com._520it.wms.query.EasyPageResult;
import com._520it.wms.query.EmployeeQueryObject;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.QueryObject;

public interface EmployeeService {
    void saveOrUpdate(Employee d, Long[] roleId);

    void delete(Long id);
    Employee get(Long id);
    PageResult query(QueryObject qo);
    EasyPageResult easy(EmployeeQueryObject qo);

    void checkLogin( String username, String password);
/**
 * 批量删除
 * @param
 * */
    void batchDelete(Long []ids);
}

