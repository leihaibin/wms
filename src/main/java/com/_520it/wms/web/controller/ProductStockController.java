package com._520it.wms.web.controller;

import com._520it.wms.query.ProductStockQueryObject;
import com._520it.wms.service.BrandService;
import com._520it.wms.service.DepotService;
import com._520it.wms.service.ProductStockService;
import com._520it.wms.util.RequiredPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/productStock")
public class ProductStockController {
    @Autowired
    private ProductStockService productStockService;
    @Autowired
    private BrandService        brandService;
    @Autowired
    private DepotService        depotService;
    @RequestMapping("/list")
    @RequiredPermission("品牌列表")
    public String list(Model model, @ModelAttribute("qo") ProductStockQueryObject qo){
        model.addAttribute("pageResult",productStockService.query(qo));
        model.addAttribute("brands",brandService.listAll());
        model.addAttribute("depots",depotService.listAll());
        return "/productStock/list";
    }

}
