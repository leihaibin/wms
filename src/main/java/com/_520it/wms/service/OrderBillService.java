package com._520it.wms.service;

import com._520it.wms.domain.OrderBill;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.QueryObject;

public interface OrderBillService {
    void save(OrderBill bill);
    void update(OrderBill bill);

    void delete(Long id);
    OrderBill get(Long id);
    PageResult query(QueryObject qo);
    //审核
    void audit(Long billId);
}
