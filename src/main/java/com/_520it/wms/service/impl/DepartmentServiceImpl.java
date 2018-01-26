package com._520it.wms.service.impl;

import com._520it.wms.domain.Department;
import com._520it.wms.mapper.DepartmentMapper;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;
    public void saveOrUpdate(Department d) {
        if(d.getId()==null){
           departmentMapper.insert(d);
        }else{
            departmentMapper.updateByPrimaryKey(d);
        }
    }

    public void delete(Long id) {
        departmentMapper.deleteByPrimaryKey(id);
    }

    public Department get(Long id) {
        return departmentMapper.selectByPrimaryKey(id);
    }

    public List<Department> listAll() {
        return departmentMapper.selectAll();
    }

    @Override
    public PageResult query(QueryObject qo) {
                int rows=departmentMapper.queryForCount(qo);
                        if(rows==0){
                            return PageResult.EMPTY_PAGE;
                        }
                        List<?> result=departmentMapper.queryForList(qo);
                        return new PageResult(result,rows,qo.getCurrentPage(),qo.getPageSize());
    }
}
