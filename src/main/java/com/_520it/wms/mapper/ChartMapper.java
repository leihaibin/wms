package com._520it.wms.mapper;

import com._520it.wms.query.OrderChartQueryObject;
import com._520it.wms.query.OrderSaleQueryObject;

import java.util.List;
import java.util.Map;

public interface ChartMapper {
    //查看订货保持表
    List<Map<String,Object>>queryOrderChart(OrderChartQueryObject qo);
    //查看订货保持表
    List<Map<String,Object>>querySaleChart(OrderSaleQueryObject qo);
}
