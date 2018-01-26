package com._520it.wms.service.impl;

import com._520it.wms.domain.Brand;
import com._520it.wms.domain.OrderBillItem;
import com._520it.wms.mapper.OrderBillItemMapper;
import com._520it.wms.service.OrderBillItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderBillItemServiceImpl implements  OrderBillItemService {
    @Autowired
    private  OrderBillItemMapper orderBillItemMapper;
    public void saveOrUpdate( OrderBillItem d) {
        if(d.getId()==null) {
            orderBillItemMapper.insert(d);
        }
    }







   /* public PageResult query(QueryObject qo){
                int rows=orderBillItemMapper.queryForCount(qo);
                        if(rows==0){
                            return PageResult.EMPTY_PAGE;
                        }
                        List<?> result=orderBillItemMapper.queryForList(qo);
                        return new PageResult(result,rows,qo.getCurrentPage(),qo.getPageSize());
    }*/
}
