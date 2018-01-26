package com._520it.wms.mapper;

import com._520it.wms.domain.Permission;
import com._520it.wms.query.QueryObject;

import java.util.List;

public interface PermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Permission record);

    List<Permission> selectAll();

    int queryForCount(QueryObject qo);
    List<Permission> queryForList(QueryObject qo);

    List<String> selectAllExpression();
}