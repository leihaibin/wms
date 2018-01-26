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
public class OrderChartQueryObject extends QueryObject {
    private String keyword;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date biginDate;//业务开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;//业务结束时间
    private Long supplierId=-1L;//供应商ID
    private Long brandId=-1L;//品牌ID

    private String groupBy ="iu.name";//按照扫描分组
    public static Map<String,Object> groupByMap=new LinkedHashMap<>();
    static {
        groupByMap.put("iu.name","订货人员");
        groupByMap.put("p.name","货品名称");
        groupByMap.put("p.brandName","品牌名称");
        groupByMap.put("s.name","供应商");
        groupByMap.put("DATE_FORMAT(bill.vdate,'%Y-%m')","订货日期(月)");
        groupByMap.put("DATE_FORMAT(bill.vdate,'%Y-%m-%d')","订货日期(日)");
    }
    public Date getEndDate(){
        return endDate !=null?DateUtil.getEndDate(endDate):null;
    }
}
