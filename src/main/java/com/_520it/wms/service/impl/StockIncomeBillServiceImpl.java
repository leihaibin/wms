package com._520it.wms.service.impl;

import com._520it.wms.domain.ProductStock;
import com._520it.wms.domain.StockIncomeBill;
import com._520it.wms.domain.StockIncomeBillItem;
import com._520it.wms.mapper.ProductStockMapper;
import com._520it.wms.mapper.StockIncomeBillItemMapper;
import com._520it.wms.mapper.StockIncomeBillMapper;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.StockIncomeBillService;
import com._520it.wms.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class StockIncomeBillServiceImpl implements StockIncomeBillService {
    @Autowired
    private StockIncomeBillMapper     stockIncomeBillMapper;
    @Autowired
    private StockIncomeBillItemMapper stockIncomeBillItemMapper;
    @Autowired
    private ProductStockMapper  stockMapper;


    public void save(StockIncomeBill bill) {
        //1.设置制单人和制单时间
        bill.setInputUser(UserContext.getCurrentUser());
        bill.setInputTime(new Date());
        //2.重新设置单据的待审核状态
        bill.setStatus(StockIncomeBill.STATE_NORMAL);
        //3.迭代单据中每一条明细,去计算单据总金额和总数量
        BigDecimal totalAmount =BigDecimal.ZERO;
        BigDecimal totalNumber =BigDecimal.ZERO;
        for(StockIncomeBillItem item:bill.getItems()){
            BigDecimal amount=item.getNumber().multiply(item.getCostPrice()).setScale(2,BigDecimal.ROUND_HALF_UP);//明细小计
            totalAmount=totalAmount.add(amount);
            totalNumber=totalNumber.add(item.getNumber());
            item.setAmount(amount);
        }
        //4.设置单据的总数量和总金额
        bill.setTotalAmount(totalAmount);
        bill.setTotalNumber(totalNumber);
        //5.保存单据对象
        stockIncomeBillMapper.insert(bill);
        //6.再保存每一条明细
        for(StockIncomeBillItem item:bill.getItems()) {
        item.setBillId(bill.getId());
        stockIncomeBillItemMapper.insert(item);
        }
    }


    public void update(StockIncomeBill bill) {
        //1.判断是否是未审核,待审核才可以更改
        StockIncomeBill old = stockIncomeBillMapper.selectByPrimaryKey(bill.getId());
        if(old.getStatus()==StockIncomeBill.STATE_NORMAL){
            //2.设置制单人和制单时间
            bill.setInputUser(old.getInputUser());
            bill.setInputTime(old.getInputTime());
        //2.先把该单据的所有明细删除
            stockIncomeBillItemMapper.deleteItemId(bill.getId());
        }
        //3.迭代单据中每一条明细,去计算单据总金额和总数量
        BigDecimal totalAmount=BigDecimal.ZERO;
        BigDecimal totalNumber=BigDecimal.ZERO;
        for (StockIncomeBillItem item : bill.getItems()) {
            BigDecimal amount = item.getCostPrice().multiply(item.getNumber()).setScale(2, BigDecimal.ROUND_UP);//明细计算小计
            totalAmount=totalAmount.add(amount);
            totalNumber=totalNumber.add(item.getNumber());
            item.setAmount(amount);
            item.setBillId(bill.getId());
            //4.设置明细小计
            stockIncomeBillItemMapper.insert(item);
        }


        //5设置单据设置总金额数和总数量
        bill.setTotalAmount(totalAmount);
        bill.setTotalNumber(totalNumber);
        //6.跟新单据独享
        stockIncomeBillMapper.updateByPrimaryKey(bill);
    }
    //先删除单据的明细再删除单据1
    public void delete(Long id) {
        stockIncomeBillItemMapper.deleteItemId(id);
        stockIncomeBillMapper.deleteByPrimaryKey(id);
    }

    public StockIncomeBill get(Long id) {
        return stockIncomeBillMapper.selectByPrimaryKey(id);
    }

    public List< StockIncomeBill> listAll() {
        return stockIncomeBillMapper.selectAll();
    }
    public PageResult query(QueryObject qo){
                int rows=stockIncomeBillMapper.queryForCount(qo);
                        if(rows==0){
                            return PageResult.EMPTY_PAGE;
                        }
                        List<StockIncomeBill> result=stockIncomeBillMapper.queryForList(qo);
                        return new PageResult(result,rows,qo.getCurrentPage(),qo.getPageSize());
    }

    public void audit(Long billId) {
        //1.查询出单据对象,判断是否处于待审核
        StockIncomeBill old = stockIncomeBillMapper.selectByPrimaryKey(billId);
        if(old.getStatus()==StockIncomeBill.STATE_NORMAL){
            //2.若是:设置已审核状态,审核时间,审核人
            old.setStatus(StockIncomeBill.STATE_AUDIT);
            old.setAuditor(UserContext.getCurrentUser());
            old.setAuditTime(new Date());
            //3.修改单据
            stockIncomeBillMapper.updateStatus(old);
            //入库操作
            //4.迭代出入库单中的每一条明细,取出明细中的货品信息
            for (StockIncomeBillItem item : old.getItems()) {
                //5.根据仓库的ID查询库存信息
               ProductStock ps=stockMapper.selectByDepotAndProduct(old.getDepot().getId(),item.getProduct().getId());
                if(ps==null){
                    //不存在信息
                    ps=new ProductStock();
                    ps.setDepot(old.getDepot());
                    ps.setProduct(item.getProduct());
                    ps.setPrice(item.getCostPrice());
                    ps.setStoreNumber(item.getNumber());
                    ps.setAmount(item.getAmount());
                    stockMapper.insert(ps);

                }else{
                    //存在库存信息
                    //库存数量
                    ps.setStoreNumber(ps.getStoreNumber().add(item.getNumber()));
                    //库存总金额
                    ps.setAmount(ps.getAmount().add(item.getAmount()));
                    //库存价格
                    ps.setPrice(ps.getAmount().divide(ps.getStoreNumber(),2,BigDecimal.ROUND_HALF_UP));
                    stockMapper.updateByPrimaryKey(ps);
                }

            }
        }


    }
}
