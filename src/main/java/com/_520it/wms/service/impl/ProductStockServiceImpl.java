package com._520it.wms.service.impl;

import com._520it.wms.domain.StockIncomeBill;
import com._520it.wms.mapper.ProductStockMapper;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.ProductStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductStockServiceImpl implements ProductStockService {
    @Autowired
    private ProductStockMapper productStockMapper;
    @Override
    public void stockIncome(StockIncomeBill bill) {

    }

    @Override
    public void stockOutcome(StockIncomeBill bill) {

    }

    @Override
    public PageResult query(QueryObject qo) {
                int rows=productStockMapper.queryForCount(qo);
                        if(rows==0){
                            return PageResult.EMPTY_PAGE;
                        }
                        List<?> result=productStockMapper.queryForList(qo);
                        return new PageResult(result,rows,qo.getCurrentPage(),qo.getPageSize());

    }
}
