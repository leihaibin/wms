package com._520it.wms.service;

import com._520it.wms.domain.Supplier;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.QueryObject;

import java.util.List;

public interface SupplierService {
    void saveOrUpdate(Supplier d);

    void delete(Long id);
    Supplier get(Long id);
    List<Supplier>listAll();
    PageResult query(QueryObject qo);

}
