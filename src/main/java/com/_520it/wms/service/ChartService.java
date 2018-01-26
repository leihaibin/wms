package com._520it.wms.service;

import com._520it.wms.query.OrderChartQueryObject;
import com._520it.wms.query.OrderSaleQueryObject;

import java.util.List;
import java.util.Map;

public interface ChartService {
    //查看订货报表
    List<Map<String,Object>> queryOrderChart(OrderChartQueryObject qo);
    //查看销售报表
    List<Map<String,Object>> querySaleChart(OrderSaleQueryObject qo);
}
