package com._520it.wms.mapper;

import com._520it.wms.domain.OrderBill;
import com._520it.wms.query.QueryObject;

import java.util.List;

public interface OrderBillMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderBill record);

    OrderBill selectByPrimaryKey(Long id);

    List<OrderBill> selectAll();

    int updateByPrimaryKey(OrderBill record);

    int queryForCount(QueryObject qo);

    List<OrderBill> queryForList(QueryObject qo);

    void updateStatus(OrderBill billId);
}