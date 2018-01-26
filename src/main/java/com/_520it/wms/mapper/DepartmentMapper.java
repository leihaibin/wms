package com._520it.wms.mapper;

import com._520it.wms.domain.Department;
import com._520it.wms.query.QueryObject;

import java.util.List;

public interface DepartmentMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Department record);

    Department selectByPrimaryKey(Long id);

    List<Department> selectAll();

    int updateByPrimaryKey(Department record);

    int queryForCount(QueryObject qo);

    List<Department> queryForList(QueryObject qo);
}