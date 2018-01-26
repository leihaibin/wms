package com._520it.wms.service.impl;

import com._520it.wms.domain.Role;
import com._520it.wms.mapper.RoleMapper;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    public void saveOrUpdate(Role r, Long[] permissionIds,Long []menuIds) {
        if (r.getId() == null) {
            roleMapper.insert(r);
        } else {
            //删除该角色拥有的所有权限
            roleMapper.deleteRelationByPermission(r.getId());
            //删除该角色拥有的所有菜单
            roleMapper.deleteRelationBySystemMenu(r.getId());
            //
            roleMapper.updateByPrimaryKey(r);
        }
    //维护中间关系表
        if (permissionIds != null) {
            for (Long pId : permissionIds) {
                roleMapper.insertRelationByPermission(r.getId(), pId);
            }
        }
        if (menuIds != null) {
            for (Long menuId : menuIds) {
                roleMapper.insertRelationBySystemMenu(r.getId(), menuId);
            }
        }
    }

    public void delete(Long id) {
        //删除该角色拥有的所有权限
        roleMapper.deleteRelationByPermission(id);
        //删除该角色拥有的所有菜单
        roleMapper.deleteRelationBySystemMenu(id);
        //删除角色
        roleMapper.deleteByPrimaryKey(id);
    }

    public Role get(Long id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    public PageResult query(QueryObject qo) {
        int rows = roleMapper.queryForCount(qo);
        if (rows == 0) {
            return PageResult.EMPTY_PAGE;
        }
        List<?> result = roleMapper.queryForList(qo);
        return new PageResult(result, rows, qo.getCurrentPage(), qo.getPageSize());
    }

    public List<Role> listAll() {
        return roleMapper.selectAll();
    }

}
