package com._520it.wms.service.impl;

import com._520it.wms.mapper.ChartMapper;
import com._520it.wms.query.OrderChartQueryObject;
import com._520it.wms.query.OrderSaleQueryObject;
import com._520it.wms.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class ChartServiceImpl implements ChartService {
    @Autowired
    private ChartMapper chartMapper;

    @Override
    public List<Map<String, Object>> queryOrderChart(OrderChartQueryObject qo) {
        return  chartMapper.queryOrderChart(qo);
    }

    @Override
    public List<Map<String, Object>> querySaleChart(OrderSaleQueryObject qo) {
        return chartMapper.querySaleChart(qo);
    }
}
