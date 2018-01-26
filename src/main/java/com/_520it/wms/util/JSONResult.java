package com._520it.wms.util;

import lombok.Getter;
import lombok.Setter;

//封装响应的JSON数据
@Getter
@Setter
public class JSONResult {
    private Boolean success =true;//响应是否成功
    private String msg;//错误信息

    public JSONResult(){
    }
    public void mark(String msg){
        this.success=false;
        this.msg=msg;
    }
}
