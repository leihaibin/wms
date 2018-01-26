package com._520it.wms.service;

import com._520it.wms.domain.SystemMenu;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.SystemMenuQueryObject;

import java.util.List;
import java.util.Map;

public interface SystemMenuService {
    void saveOrUpdate(SystemMenu d);

    void delete(Long id);
    SystemMenu get(Long id);
    PageResult query(SystemMenuQueryObject qo);
    List<SystemMenu> listAll();

    /**
     * 从指定菜单对象开始一级一级往上找父级菜单对象
     * @param current 从该菜单开始网上找
     * @return
     */
    List<Map<String,Object>> queryMenu(SystemMenu current);

    /**
     * 根据父级菜单的sn查询自己所有的子菜单
     * @param parentSn
     * @return
     */
    List<Map<String,Object>> queryMenusByParentSn(String parentSn);
}
