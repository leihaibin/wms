package com._520it.wms.mapper;

import com._520it.wms.domain.Employee;
import com._520it.wms.query.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Employee record);

    Employee selectByPrimaryKey(Long id);


    int updateByPrimaryKey(Employee record);

    int queryForCount(QueryObject qo);

    List<?> queryForList(QueryObject qo);

    List<?> queryForEasy(QueryObject qo);

    void insertRelationByRole(@Param("employeeId") Long employeeId, @Param("roleId") Long roleId);

    void deleteRelationByRole(Long employeeId);

    Employee selectByUsernamePassword( @Param("username")String username, @Param("password") String password);

    List<String> selectPermissionExpressionByEmployeeId(Long employeeId);

    void batchDelete(@Param("ids") Long[] ids);
}