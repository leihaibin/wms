package com._520it.wms.service.impl;

import com._520it.wms.domain.Employee;
import com._520it.wms.domain.SystemMenu;
import com._520it.wms.mapper.SystemMenuMapper;
import com._520it.wms.query.EasyPageResult;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.SystemMenuQueryObject;
import com._520it.wms.service.SystemMenuService;
import com._520it.wms.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SystemMenuServiceImpl implements SystemMenuService {
    @Autowired
    private SystemMenuMapper systemMenuMapper;

    public void saveOrUpdate(SystemMenu sm) {
        if (sm.getId() == null) {
            systemMenuMapper.insert(sm);
        } else {
            systemMenuMapper.updateByPrimaryKey(sm);
        }
    }

    public void delete(Long id) {
        systemMenuMapper.deleteByPrimaryKey(id);
    }

    public SystemMenu get(Long id) {
        return systemMenuMapper.selectByPrimaryKey(id);
    }

    public PageResult query(SystemMenuQueryObject qo) {
        int rows = systemMenuMapper.queryForCount(qo);
        if (rows == 0) {
            return PageResult.EMPTY_PAGE;
        }
        List<?> result = systemMenuMapper.queryForList(qo);
        return new PageResult(result, rows, qo.getCurrentPage(), qo.getPageSize());

    }


    public EasyPageResult easy(SystemMenuQueryObject qo) {
        SystemMenuQueryObject arr=new SystemMenuQueryObject();
        arr.setPageSize(qo.getPageSize());
        arr.setCurrentPage(qo.getCurrentPage());
        int total = systemMenuMapper.queryForCount(arr);
        if (total == 0) {
            return EasyPageResult.EMPTY_PAGE;
        }
        List<?> employees = systemMenuMapper.queryForList(arr);
        return new EasyPageResult(total,employees);
    }

    public List<SystemMenu> listAll() {
        return systemMenuMapper.selectAll();
    }

    public List<Map<String, Object>> queryMenu(SystemMenu current) {
        List<Map<String, Object>> list = new ArrayList<>();
        while (current != null) {
            Map<String, Object> map = new HashMap<>();
            list.add(map);
            map.put("id", current.getId());
            map.put("name", current.getName());
            current=current.getParent();
            // 如果 parent 有值,从数据库获取该对象及其parent对象
           /* SystemMenu p = current.getParent();
            current=p;
            if (p != null) {
                current= systemMenuMapper.selectByPrimaryKey(p.getId());
            }else{
                // 如果parent为空,则结束循环
                current=null;
            }*/
        }
        Collections.reverse(list);
        return list;
    }

    public List<Map<String, Object>> queryMenusByParentSn(String parentSn) {
        Employee current = UserContext.getCurrentUser();
        if(current.getAdmin()) {
            return systemMenuMapper.queryMenusByParentSn(parentSn);
        }else{
            return systemMenuMapper.queryMenusByParentSnAndEmpoyeeId(parentSn,current.getId());

        }
    }
}
