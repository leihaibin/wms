package com._520it.wms.domain;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Product extends BaseDomain {
    private String name;
    private String sn;
    private BigDecimal costPrice;
    private BigDecimal salePrice;
    private String imagePath;
    private String intro;
    private Long brandId;
    private String brandName;
    public String getJsonString(){
        Map<String,Object> jsonMap=new HashMap<>();
        jsonMap.put("id",id);
        jsonMap.put("name",name);
        jsonMap.put("costPrice",costPrice);
        jsonMap.put("salePrice",salePrice);
        jsonMap.put("brandName",brandName);
        return JSON.toJSONString(jsonMap);
    }
    public String getSmallImagePath(){
        if(imagePath==null){
            return"";
        }
        return imagePath.substring(0,imagePath.lastIndexOf('.'))+"_small"+imagePath.substring(imagePath.lastIndexOf('.'));
    }
}
