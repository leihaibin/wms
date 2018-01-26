package com._520it.wms.query;

import com._520it.wms.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class OrderSaleQueryObject extends QueryObject {
    private String keyword;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date biginDate;//业务开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;//业务结束时间
    private Long clientId=-1L;//供应商ID
    private Long brandId=-1L;//品牌ID

    private String groupBy ="sm.name";//按照扫描分组
    public static Map<String,Object> groupByMap=new LinkedHashMap<>();
    static {
        groupByMap.put("sm.name","销售人员");
        groupByMap.put("p.name","货品名称");
        groupByMap.put("p.brandName","品牌名称");
        groupByMap.put("c.name","客户");
        groupByMap.put("DATE_FORMAT(sa.vdate,'%Y-%m')","销售日期(月)");
        groupByMap.put("DATE_FORMAT(sa.vdate,'%Y-%m-%d')","销售日期(日)");
    }
    public Date getEndDate(){
        return endDate !=null?DateUtil.getEndDate(endDate):null;
    }
}
