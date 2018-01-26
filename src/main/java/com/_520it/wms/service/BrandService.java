package com._520it.wms.service;

import com._520it.wms.domain.Brand;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.QueryObject;

import java.util.List;

public interface BrandService {
    void saveOrUpdate(Brand d);

    void delete(Long id);
    Brand get(Long id);
    List<Brand>listAll();
    PageResult query(QueryObject qo);

}
