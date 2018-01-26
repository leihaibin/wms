package com._520it.wms.service;

import com._520it.wms.domain.StockOutcomeBill;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.QueryObject;

public interface StockOutcomeBillService {
    void save(StockOutcomeBill bill);
    void update(StockOutcomeBill bill);

    void delete(Long id);
    StockOutcomeBill get(Long id);
    PageResult query(QueryObject qo);
    //审核
    void audit(Long billId);
}
