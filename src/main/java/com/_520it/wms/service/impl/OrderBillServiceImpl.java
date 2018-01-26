package com._520it.wms.service.impl;

import com._520it.wms.domain.OrderBill;
import com._520it.wms.domain.OrderBillItem;
import com._520it.wms.mapper.OrderBillItemMapper;
import com._520it.wms.mapper.OrderBillMapper;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.OrderBillService;
import com._520it.wms.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class OrderBillServiceImpl implements  OrderBillService {
    @Autowired
    private  OrderBillMapper orderBillMapper;
    @Autowired
    private OrderBillItemMapper orderBillItemMapper;

    @Override
    public void save(OrderBill bill) {
        //1.设置制单人和制单时间
        bill.setInputUser(UserContext.getCurrentUser());
        bill.setInputTime(new Date());
        //2.重新设置单据的待审核状态
        bill.setStatus(OrderBill.STATE_NORMAL);
        BigDecimal totalAmount =BigDecimal.ZERO;
        BigDecimal totalNumber =BigDecimal.ZERO;
        //3.迭代单据中每一条明细,去计算单据总金额和总数量
        for(OrderBillItem item:bill.getItems()){
            BigDecimal amount=item.getCostPrice().multiply(item.getNumber()).setScale(2,BigDecimal.ROUND_HALF_UP);//明细小计
            totalAmount=totalAmount.add(amount);
            totalNumber=totalNumber.add(item.getNumber());

            item.setAmount(amount);

        }
        bill.setTotalAmount(totalAmount);
        bill.setTotalNumber(totalNumber);
        //4.保存单据对象
        orderBillMapper.insert(bill);
        //5.设置明细所属单据的ID
        for(OrderBillItem item:bill.getItems()) {
        item.setBillId(bill.getId());
        orderBillItemMapper.insert(item);
        }
    }

    @Override
    public void update(OrderBill bill) {
        //1.判断是否是未审核,待审核才可以更改
        OrderBill old = orderBillMapper.selectByPrimaryKey(bill.getId());
        if(old.getStatus()==OrderBill.STATE_NORMAL){
            //2.设置制单人和制单时间
            bill.setInputUser(old.getInputUser());
            bill.setInputTime(old.getInputTime());
        //2.先把该单据的所有明细删除
            orderBillItemMapper.deleteItemId(bill.getId());
        }
        //3.迭代单据中每一条明细,去计算单据总金额和总数量
        BigDecimal totalAmount=BigDecimal.ZERO;
        BigDecimal totalNumber=BigDecimal.ZERO;
        for (OrderBillItem item : bill.getItems()) {
            BigDecimal amount = item.getCostPrice().multiply(item.getNumber()).setScale(2, BigDecimal.ROUND_UP);//明细计算小计
            totalAmount=totalAmount.add(amount);
            totalNumber=totalNumber.add(item.getNumber());
            //设置明细小计
            item.setAmount(amount);
            item.setBillId(bill.getId());
            orderBillItemMapper.insert(item);
        }
        //4.//给单据设置总金额数和总数量
        bill.setTotalAmount(totalAmount);
        bill.setTotalNumber(totalNumber);
        //5.跟新单据独享
        orderBillMapper.updateByPrimaryKey(bill);
    }

    public void delete(Long id) {
        orderBillItemMapper.deleteItemId(id);
        orderBillMapper.deleteByPrimaryKey(id);
    }

    public OrderBill get(Long id) {
        return orderBillMapper.selectByPrimaryKey(id);
    }

    public List< OrderBill> listAll() {
        return orderBillMapper.selectAll();
    }
    public PageResult query(QueryObject qo){
                int rows=orderBillMapper.queryForCount(qo);
                        if(rows==0){
                            return PageResult.EMPTY_PAGE;
                        }
                        List<OrderBill> result=orderBillMapper.queryForList(qo);
                        return new PageResult(result,rows,qo.getCurrentPage(),qo.getPageSize());
    }

    public void audit(Long billId) {
        //1.查询出单据对象,判断是否处于待审核
        OrderBill old = orderBillMapper.selectByPrimaryKey(billId);
        if(old.getStatus()==OrderBill.STATE_NORMAL){
            //若是:设置已审核状态,审核时间,审核人
            old.setStatus(OrderBill.STATE_AUDIT);
            old.setAuditor(UserContext.getCurrentUser());
            old.setAuditTime(new Date());
            orderBillMapper.updateStatus(old);
        }


    }
}
