package com._520it.wms.service;

import com._520it.wms.domain.Depot;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.QueryObject;

import java.util.List;

public interface DepotService {
    void saveOrUpdate(Depot d);

    void delete(Long id);
    Depot get(Long id);
    List<Depot>listAll();
    PageResult query(QueryObject qo);

}
