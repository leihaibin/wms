package com._520it.wms.service.impl;

import com._520it.wms.domain.Employee;
import com._520it.wms.mapper.EmployeeMapper;
import com._520it.wms.query.EasyPageResult;
import com._520it.wms.query.EmployeeQueryObject;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.EmployeeService;
import com._520it.wms.util.MD5;
import com._520it.wms.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    public void saveOrUpdate(Employee e, Long[] roleIds) {
        if (e.getId() == null) {
        e.setPassword(MD5.encode(e.getPassword()));
            employeeMapper.insert(e);
        } else {
            employeeMapper.deleteRelationByRole(e.getId());
            employeeMapper.updateByPrimaryKey(e);
        }
        //维护关系
        if (roleIds != null) {
            for (Long roleId : roleIds) {
                employeeMapper.insertRelationByRole(e.getId(), roleId);
            }
        }
    }

    public void delete(Long id) {
        employeeMapper.deleteRelationByRole(id);
        employeeMapper.deleteByPrimaryKey(id);
    }

    public Employee get(Long id) {

        return employeeMapper.selectByPrimaryKey(id);
    }

    public PageResult query(QueryObject qo) {
        int rows = employeeMapper.queryForCount(qo);
        if (rows == 0) {
            return PageResult.EMPTY_PAGE;
        }
        List<?> result = employeeMapper.queryForList(qo);
        return new PageResult(result, rows, qo.getCurrentPage(), qo.getPageSize());
    }

    public EasyPageResult easy(EmployeeQueryObject qo) {
        EmployeeQueryObject arr=new EmployeeQueryObject();
        arr.setPageSize(qo.getPageSize());
        arr.setCurrentPage(qo.getCurrentPage());
        arr.setKeyword(qo.getKeyword());
        arr.setDeptId(qo.getDeptId());
        int total = employeeMapper.queryForCount(arr);
        if (total == 0) {
            return EasyPageResult.EMPTY_PAGE;
        }
        List<?> employees = employeeMapper.queryForList(arr);
        return new EasyPageResult(total,employees);
    }

    //此处不需要@paprm
    public void checkLogin( String username,String password) {
        //发SQL 验证账号和密码
        Employee current = employeeMapper.selectByUsernamePassword(username,MD5.encode(password+username));
        if(current==null){
            throw new RuntimeException("账号或密码错误!");
        }
        //2.把登录信息存储到session中
        UserContext.setCurrentUser(current);
        //3.把所有用户的所有权限表达式存储到session中
        List<String> exps =employeeMapper.selectPermissionExpressionByEmployeeId(current.getId());
        UserContext.setPermissionExpressions(exps);
    }

    public void batchDelete(Long[] ids) {
        employeeMapper.batchDelete(ids);
    }


}
