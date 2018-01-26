package com._520it.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Employee extends BaseDomain{

    private String name;
    private String password;
    private String email;
    private int age;
    private Boolean admin =false;
    private Department dept;
    private List<Role> roles =new ArrayList<>();
    public String getRoleNames() {
        if (admin != null && admin) {
            return "[超级管理员]";
        }
        if (roles.size() == 0) {
            return "[未分配角色]";
        }
        StringBuilder sb = new StringBuilder(80);
        sb.append("[");
        for (Role role : roles) {
            sb.append(role.getName()).append(",");
        }
        sb.setCharAt(sb.length() - 1, ']');
        return sb.toString();
}



}