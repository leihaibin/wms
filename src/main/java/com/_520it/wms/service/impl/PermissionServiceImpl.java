package com._520it.wms.service.impl;

import com._520it.wms.domain.Permission;
import com._520it.wms.mapper.PermissionMapper;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.PermissionService;
import com._520it.wms.util.RequiredPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private ApplicationContext ctx;

    public void delete(Long id) {
        permissionMapper.deleteByPrimaryKey(id);
    }

    public List<Permission> listAll() {
        return  permissionMapper.selectAll();
    }

    public PageResult query(QueryObject qo) {
        int rows = permissionMapper.queryForCount(qo);
        if (rows == 0) {
            return PageResult.EMPTY_PAGE;
        }
        List<?> result = permissionMapper.queryForList(qo);
        return new PageResult(result, rows, qo.getCurrentPage(), qo.getPageSize());
    }

    public void reload() {
        //-------------------------------------
        //如果权限表达式在数据库中,就不在保存
        List<String> exps=permissionMapper.selectAllExpression();
        //-------------------------------------
        //1.扫描所有Controller类

        Map<String, Object> beans = ctx.getBeansWithAnnotation(Controller.class);
        beans.putAll(ctx.getBeansWithAnnotation(RestController.class));
        for (Object bean : beans.values()) {
            Method[]ms=bean.getClass().getDeclaredMethods();
        //2.扫描每一个Controller类中的每一个方法
            for (Method m : ms) {
                //3.判断Controller方法是否有RequiredPermission注解所有标注
                RequiredPermission rp = m.getAnnotation(RequiredPermission.class);
                //4.如果没有不管,有:说明对应有一个权限:创建Permission对象,设置属性,保存到数据库
                String exp = bean.getClass().getName() + ":" + m.getName();
                if (!exps.contains(exp)) {
                    if (rp != null) {
                        Permission p = new Permission();
                        p.setName(rp.value());
                        p.setExpression(exp);
                        permissionMapper.insert(p);
                    }
                }
            }

        }

    }
}