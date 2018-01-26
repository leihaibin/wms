package com._520it.wms.web.controller;

import com._520it.wms.domain.StockIncomeBill;
import com._520it.wms.query.StockIncomeBillQueryObject;
import com._520it.wms.service.DepotService;
import com._520it.wms.service.StockIncomeBillService;
import com._520it.wms.util.JSONResult;
import com._520it.wms.util.RequiredPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/stockIncomeBill")
public class StockIncomeBillController {
    @Autowired
    private StockIncomeBillService stockIncomeBillService;
    @Autowired
    private DepotService depotService;
    @RequestMapping("/list")
    @RequiredPermission("到货入库列表")
    public String list(Model model, @ModelAttribute("qo")StockIncomeBillQueryObject qo){
        model.addAttribute("depots",depotService.listAll());
        model.addAttribute("pageResult",stockIncomeBillService.query(qo));
        return "/stockIncomeBill/list";
    }
    @RequestMapping("/input")
    @RequiredPermission("到货入库编辑")
    public String input(Long id,Model model){
        if(id!=null){
            model.addAttribute("stockIncomeBill",stockIncomeBillService.get(id));
        }
        model.addAttribute("depots",depotService.listAll());
        return "/stockIncomeBill/input";
    }
    @RequestMapping("/view")
    @RequiredPermission("到货入库查看")
    public String view(Long id,Model model){
        model.addAttribute("stockIncomeBill",stockIncomeBillService.get(id));
        return "/stockIncomeBill/view";
    }
    @RequestMapping("/saveOrUpdate")
    @RequiredPermission("保存/更新到货入库")
    @ResponseBody
    public JSONResult saveOrUpdate(StockIncomeBill stockIncomeBill){
        if(stockIncomeBill.getId()==null){
            stockIncomeBillService.save(stockIncomeBill);
        }else{
            stockIncomeBillService.update(stockIncomeBill);
        }
        return new JSONResult();
    }
    @RequestMapping("/delete")
    @RequiredPermission("删除到货入库")
    @ResponseBody
    public JSONResult delete(Long id){
        if(id!=null) {
            stockIncomeBillService.delete(id);
        }
        return new JSONResult();
    }
    @RequestMapping("/audit")
    @RequiredPermission("审核到货入库")
    @ResponseBody
    public JSONResult audit(Long id){
        if(id!=null) {
            stockIncomeBillService.audit(id);
        }
        return new JSONResult();
    }
}
