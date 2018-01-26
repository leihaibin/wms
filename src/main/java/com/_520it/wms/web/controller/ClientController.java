package com._520it.wms.web.controller;

import com._520it.wms.domain.Client;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.ClientService;
import com._520it.wms.util.JSONResult;
import com._520it.wms.util.RequiredPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private ClientService clientService;
    @RequestMapping("/list")
    @RequiredPermission("客户列表")
    public String list(Model model, @ModelAttribute("qo") QueryObject qo){
        model.addAttribute("pageResult",clientService.query(qo));
        return "/client/list";
    }
    @RequestMapping("/input")
    @RequiredPermission("客户编辑")
    public String input(Long id,Model model){

        model.addAttribute("client",clientService.get(id));
        return "/client/input";
    }
    @RequestMapping("/saveOrUpdate")
    @RequiredPermission("保存/更新客户")
    @ResponseBody
    public JSONResult saveOrUpdate(Client client){
        clientService.saveOrUpdate(client);
        return new JSONResult();
    }
    @RequestMapping("/delete")
    @RequiredPermission("删除客户")
    @ResponseBody
    public JSONResult delete(Long id){
        if(id!=null) {
            clientService.delete(id);
        }
        return new JSONResult();
    }
}
