package com._520it.wms.mapper;

import com._520it.wms.domain.Role;
import com._520it.wms.query.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    Role selectByPrimaryKey(Long id);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);
    int queryForCount(QueryObject qo);
    List<Role> queryForList(QueryObject qo);

    void insertRelationByPermission(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);

    void deleteRelationByPermission(Long roleId);

    void insertRelationBySystemMenu(@Param("roleId") Long roleId, @Param("menuId") Long menuId);

    void deleteRelationBySystemMenu(Long roleId);
}
