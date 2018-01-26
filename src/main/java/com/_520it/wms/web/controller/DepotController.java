package com._520it.wms.web.controller;

import com._520it.wms.domain.Depot;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.DepotService;
import com._520it.wms.util.JSONResult;
import com._520it.wms.util.RequiredPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/depot")
public class DepotController {
    @Autowired
    private DepotService depotService;
    @RequestMapping("/list")
    @RequiredPermission("仓库列表")
    public String list(Model model, @ModelAttribute("qo") QueryObject qo){
        model.addAttribute("pageResult",depotService.query(qo));
        return "/depot/list";
    }
    @RequestMapping("/input")
    @RequiredPermission("仓库编辑")
    public String input(Long id,Model model){

        model.addAttribute("depot",depotService.get(id));
        return "/depot/input";
    }
    @RequestMapping("/saveOrUpdate")
    @RequiredPermission("保存/更新仓库")
    @ResponseBody
    public JSONResult saveOrUpdate(Depot depot){
        depotService.saveOrUpdate(depot);
        return new JSONResult();
    }
    @RequestMapping("/delete")
    @RequiredPermission("删除仓库")
    @ResponseBody
    public JSONResult delete(Long id){
        if(id!=null) {
            depotService.delete(id);
        }
        return new JSONResult();
    }
}
