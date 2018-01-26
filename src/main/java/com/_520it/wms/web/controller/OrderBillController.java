package com._520it.wms.web.controller;

import com._520it.wms.domain.OrderBill;
import com._520it.wms.query.OrderBillQueryObject;
import com._520it.wms.service.OrderBillService;
import com._520it.wms.service.SupplierService;
import com._520it.wms.util.JSONResult;
import com._520it.wms.util.RequiredPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/orderBill")
public class OrderBillController {
    @Autowired
    private OrderBillService orderBillService;
    @Autowired
    private SupplierService supplierService;
    @RequestMapping("/list")
    @RequiredPermission("采购订单列表")
    public String list(Model model, @ModelAttribute("qo") OrderBillQueryObject qo){
        model.addAttribute("suppliers",supplierService.listAll());
        model.addAttribute("pageResult",orderBillService.query(qo));
        return "/orderBill/list";
    }
    @RequestMapping("/input")
    @RequiredPermission("采购订单编辑")
    public String input(Long id,Model model){
        if(id!=null){
            model.addAttribute("orderBill",orderBillService.get(id));
        }
        model.addAttribute("suppliers",supplierService.listAll());
        return "/orderBill/input";
    }
    @RequestMapping("/view")
    @RequiredPermission("采购订单查看")
    public String view(Long id,Model model){
        model.addAttribute("orderBill",orderBillService.get(id));
        return "/orderBill/view";
    }
    @RequestMapping("/saveOrUpdate")
    @RequiredPermission("保存/更新采购订单")
    @ResponseBody
    public JSONResult saveOrUpdate(OrderBill orderBill){
        if(orderBill.getId()==null){
            orderBillService.save(orderBill);
        }else{
            orderBillService.update(orderBill);
        }
        return new JSONResult();
    }
    @RequestMapping("/delete")
    @RequiredPermission("删除采购订单")
    @ResponseBody
    public JSONResult delete(Long id){
        if(id!=null) {
            orderBillService.delete(id);
        }
        return new JSONResult();
    }
    @RequestMapping("/audit")
    @RequiredPermission("审核采购订单")
    @ResponseBody
    public JSONResult audit(Long id){
        if(id!=null) {
            orderBillService.audit(id);
        }
        return new JSONResult();
    }
}
