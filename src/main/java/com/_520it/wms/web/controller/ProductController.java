package com._520it.wms.web.controller;

import com._520it.wms.domain.Product;
import com._520it.wms.query.ProductQueryObject;
import com._520it.wms.service.BrandService;
import com._520it.wms.service.ProductService;
import com._520it.wms.util.JSONResult;
import com._520it.wms.util.RequiredPermission;
import com._520it.wms.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private ServletContext servletContext;
    @RequestMapping("/list")
    @RequiredPermission("货品列表")
    public String list(Model model, @ModelAttribute("qo") ProductQueryObject qo){
        model.addAttribute("pageResult",productService.query(qo));
        model.addAttribute("brands",brandService.listAll());
        return "/product/list";
    }
    @RequestMapping("/selectProductList")
    public String selectProductList(Model model, @ModelAttribute("qo") ProductQueryObject qo){
        qo.setPageSize(20);
        model.addAttribute("pageResult",productService.query(qo));
        model.addAttribute("brands",brandService.listAll());
        return "/product/selectProductList";
    }
    @RequestMapping("/input")
    @RequiredPermission("货品编辑")
    public String input(Long id,Model model){
        model.addAttribute("brands",brandService.listAll());
        model.addAttribute("product",productService.get(id));
        return "/product/input";
    }
    @RequestMapping("/saveOrUpdate")
    @RequiredPermission("保存/更新货品")
    @ResponseBody
    public JSONResult saveOrUpdate(Product product, MultipartFile pic){
        if(pic !=null && product.getImagePath()!=null){
            UploadUtil.deleteFile(servletContext,product.getImagePath());
        }
        //----------------------------------------------
        //保存图片
        if(pic !=null) {
            String fileName = UploadUtil.upload(pic, servletContext.getRealPath("/upload/"));
            product.setImagePath(fileName);
            System.out.println("-------------"+fileName);
        }
            productService.saveOrUpdate(product);
        return new JSONResult();
    }
    @RequestMapping("/delete")
    @RequiredPermission("删除货品")
    @ResponseBody
    public JSONResult delete(Long id,String imagePath){
        //删除图片
        if(imagePath !=null){
            UploadUtil.deleteFile(servletContext,imagePath);
        }

        //-----------------------------
        if(id!=null) {
            productService.delete(id);
        }
        return new JSONResult();
    }
}
