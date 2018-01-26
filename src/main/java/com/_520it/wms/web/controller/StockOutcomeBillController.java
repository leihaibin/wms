package com._520it.wms.web.controller;

import com._520it.wms.domain.StockOutcomeBill;
import com._520it.wms.query.StockOutcomeBillQueryObject;
import com._520it.wms.service.ClientService;
import com._520it.wms.service.DepotService;
import com._520it.wms.service.StockOutcomeBillService;
import com._520it.wms.util.JSONResult;
import com._520it.wms.util.RequiredPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/stockOutcomeBill")
public class StockOutcomeBillController {
    @Autowired
    private StockOutcomeBillService stockOutcomeBillService;
    @Autowired
    private DepotService depotService;
    @Autowired
    private ClientService clientService;
    @RequestMapping("/list")
    @RequiredPermission("销售出库列表")
    public String list(Model model, @ModelAttribute("qo")StockOutcomeBillQueryObject qo){
        model.addAttribute("depots",depotService.listAll());
        model.addAttribute("clients",clientService.listAll());
        model.addAttribute("pageResult",stockOutcomeBillService.query(qo));
        return "/stockOutcomeBill/list";
    }
    @RequestMapping("/input")
    @RequiredPermission("销售出库编辑")
    public String input(Long id,Model model){
        if(id!=null){
            model.addAttribute("stockOutcomeBill",stockOutcomeBillService.get(id));
        }
        model.addAttribute("clients",clientService.listAll());
        model.addAttribute("depots",depotService.listAll());
        return "/stockOutcomeBill/input";
    }
    @RequestMapping("/view")
    @RequiredPermission("销售出库查看")
    public String view(Long id,Model model){
        model.addAttribute("stockOutcomeBill",stockOutcomeBillService.get(id));
        return "/stockOutcomeBill/view";
    }
    @RequestMapping("/saveOrUpdate")
    @RequiredPermission("保存/更新销售出库")
    @ResponseBody
    public JSONResult saveOrUpdate(StockOutcomeBill stockOutcomeBill){
        if(stockOutcomeBill.getId()==null){
            stockOutcomeBillService.save(stockOutcomeBill);
        }else{
            stockOutcomeBillService.update(stockOutcomeBill);
        }
        return new JSONResult();
    }
    @RequestMapping("/delete")
    @RequiredPermission("删除销售出库")
    @ResponseBody
    public JSONResult delete(Long id){
        if(id!=null) {
            stockOutcomeBillService.delete(id);
        }
        return new JSONResult();
    }
    @RequestMapping("/audit")
    @RequiredPermission("审核销售出库")
    @ResponseBody
    public JSONResult audit(Long id){
        JSONResult jsonResult = new JSONResult();
        try {
            stockOutcomeBillService.audit(id);
        }catch (Exception e){
            jsonResult.mark(e.getMessage());
        }
        return jsonResult;
    }
}
