package com._520it.wms.service;

import com._520it.wms.domain.StockIncomeBill;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.QueryObject;

public interface ProductStockService {
    /**
     * 入库操作
     */
    void stockIncome(StockIncomeBill bill);
    /**
     * 出库操作
     */
    void stockOutcome(StockIncomeBill bill);
    PageResult query(QueryObject qo);


}
