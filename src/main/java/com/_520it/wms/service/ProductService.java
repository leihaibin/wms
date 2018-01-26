package com._520it.wms.service;

import com._520it.wms.domain.Product;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.ProductQueryObject;

import java.util.List;

public interface ProductService {
    void saveOrUpdate(Product d);

    void delete(Long id);
    Product get(Long id);
    List<Product>listAll();
    PageResult query(ProductQueryObject qo);

}
