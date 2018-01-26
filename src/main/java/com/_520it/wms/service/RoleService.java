package com._520it.wms.service;

import com._520it.wms.domain.Role;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.QueryObject;

import java.util.List;

public interface RoleService {
    void saveOrUpdate(Role d, Long[] permissionIds,Long []menuIds);

    void delete(Long id);
    Role get(Long id);
    PageResult query(QueryObject qo);

    List<Role> listAll();
}
