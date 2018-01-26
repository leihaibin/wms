package com._520it.wms.service;

import com._520it.wms.domain.Permission;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.QueryObject;

import java.util.List;

public interface PermissionService {

    void delete(Long id);
    List<Permission> listAll();
    PageResult query(QueryObject qo);
    //重新加载权限
    void reload();
}
