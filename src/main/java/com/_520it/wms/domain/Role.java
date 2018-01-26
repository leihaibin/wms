package com._520it.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter@Setter
public class Role extends BaseDomain {

    private String name;
    private String sn;
    //权限和角色是多对多
    List<Permission>permissions=new ArrayList<>();
    List<SystemMenu>menus=new ArrayList<>();
    //菜單和角色是多对多

}