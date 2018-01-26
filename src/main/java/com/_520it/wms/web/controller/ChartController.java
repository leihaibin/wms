package com._520it.wms.web.controller;

import com._520it.wms.query.OrderChartQueryObject;
import com._520it.wms.query.OrderSaleQueryObject;
import com._520it.wms.service.BrandService;
import com._520it.wms.service.ChartService;
import com._520it.wms.service.ClientService;
import com._520it.wms.service.SupplierService;
import com._520it.wms.util.RequiredPermission;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/chart")
public class ChartController {
    @Autowired
    private ChartService    chartService;
    @Autowired
    private BrandService    brandService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private ClientService   clientService;

    @RequestMapping("/order")
    @RequiredPermission("订货报表")
    public String list(@ModelAttribute("qo") OrderChartQueryObject qo, Model model) {
        model.addAttribute("dataList", chartService.queryOrderChart(qo));
        model.addAttribute("brands", brandService.listAll());
        model.addAttribute("groupByMap", OrderChartQueryObject.groupByMap);
        model.addAttribute("suppliers", supplierService.listAll());
        return "/chart/order";
    }

    @RequestMapping("/sale")
    @RequiredPermission("销售报表")
    public String list(@ModelAttribute("qo") OrderSaleQueryObject qo, Model model) {
        model.addAttribute("dataList", chartService.querySaleChart(qo));
        model.addAttribute("brands", brandService.listAll());
        model.addAttribute("groupByMap", OrderSaleQueryObject.groupByMap);
        model.addAttribute("clients", clientService.listAll());
        return "/chart/sale";
    }

    @RequestMapping("/saleByBar")
    public String saleByBar(@ModelAttribute("qo") OrderSaleQueryObject qo, Model model) {
        List<Map<String, Object>> list = chartService.querySaleChart(qo);
        //封装分组条件,表示横坐标数据
        List<Object> groupByList = new ArrayList<>();
        //分装销售总额,表示纵坐标数据
        List<Object> totalAmountList = new ArrayList<>();
        for (Map<String, Object> map : list) {
            groupByList.add(map.get("groupType"));
            totalAmountList.add(map.get("totalAmount"));
        }
        //按照扫描分组
        model.addAttribute("groupType", OrderSaleQueryObject.groupByMap.get(qo.getGroupBy()));
        model.addAttribute("groupByList", JSON.toJSONString(groupByList));
        model.addAttribute("totalAmountList", JSON.toJSONString(totalAmountList));
        return "/chart/saleChartByBar";
    }

    @RequestMapping("/saleByPie")
    public String saleByPie(@ModelAttribute("qo") OrderSaleQueryObject qo, Model model) {
        List<Map<String, Object>> list = chartService.querySaleChart(qo);
        //封装分组条件,表示横坐标数据
        List<Object> groupByList = new ArrayList<>();
        List<Map<String, Object>> data = new ArrayList<>();

        BigDecimal max = BigDecimal.ZERO;//最大销售总额
        for (Map<String, Object> row : list) {
            Map<String, Object> map = new HashMap<>();
            data.add(map);
            BigDecimal amount = (BigDecimal) row.get("totalAmount");
            map.put("name", row.get("groupType"));
            map.put("value", amount);
            if (amount.compareTo(max) > 0) {
                max = amount;
            }
            groupByList.add(row.get("groupType"));
        }

        //按照扫描分组
        model.addAttribute("groupByList", JSON.toJSONString(groupByList));
        model.addAttribute("data", JSON.toJSONString(data));
        model.addAttribute("groupType", OrderSaleQueryObject.groupByMap.get(qo.getGroupBy()));
        model.addAttribute("max", max);
        return "/chart/saleChartByPie";
    }

}
