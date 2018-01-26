package com._520it.wms.service;

import com._520it.wms.domain.StockIncomeBill;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.QueryObject;

public interface StockIncomeBillService {
    void save(StockIncomeBill bill);
    void update(StockIncomeBill bill);

    void delete(Long id);
    StockIncomeBill get(Long id);
    PageResult query(QueryObject qo);
    //审核
    void audit(Long billId);
}
