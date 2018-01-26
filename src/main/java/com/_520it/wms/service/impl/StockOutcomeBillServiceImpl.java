package com._520it.wms.service.impl;

import com._520it.wms.domain.ProductStock;
import com._520it.wms.domain.SaleAccount;
import com._520it.wms.domain.StockOutcomeBill;
import com._520it.wms.domain.StockOutcomeBillItem;
import com._520it.wms.mapper.ProductStockMapper;
import com._520it.wms.mapper.SaleAccountMapper;
import com._520it.wms.mapper.StockOutcomeBillItemMapper;
import com._520it.wms.mapper.StockOutcomeBillMapper;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.StockOutcomeBillService;
import com._520it.wms.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class StockOutcomeBillServiceImpl implements StockOutcomeBillService {
    @Autowired
    private StockOutcomeBillMapper     stockOutcomeBillMapper;
    @Autowired
    private StockOutcomeBillItemMapper stockOutcomeBillItemMapper;
    @Autowired
    private ProductStockMapper         stockMapper;
    @Autowired
    private SaleAccountMapper saleAccountMapper;


    @Override
    public void save(StockOutcomeBill bill) {
        //1.设置制单人和制单时间
        bill.setInputUser(UserContext.getCurrentUser());
        bill.setInputTime(new Date());
        //2.重新设置单据的待审核状态
        bill.setStatus(StockOutcomeBill.STATE_NORMAL);
        //3.迭代单据中每一条明细,去计算单据总金额和总数量
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalNumber = BigDecimal.ZERO;
        for (StockOutcomeBillItem item : bill.getItems()) {
            BigDecimal amount = item.getNumber().multiply(item.getSalePrice()).setScale(2, BigDecimal.ROUND_HALF_UP);
            //明细小计
            totalAmount = totalAmount.add(amount);
            totalNumber = totalNumber.add(item.getNumber());
            item.setAmount(amount);
        }
        //4.设置单据的总数量和总金额
        bill.setTotalAmount(totalAmount);
        bill.setTotalNumber(totalNumber);
        //5.保存单据对象
        stockOutcomeBillMapper.insert(bill);
        //6.再保存每一条明细
        for (StockOutcomeBillItem item : bill.getItems()) {
            item.setBillId(bill.getId());
            stockOutcomeBillItemMapper.insert(item);
        }
    }


    @Override
    public void update(StockOutcomeBill bill) {
        //1.判断是否是未审核,待审核才可以更改
        StockOutcomeBill old = stockOutcomeBillMapper.selectByPrimaryKey(bill.getId());
        if (old.getStatus() == StockOutcomeBill.STATE_NORMAL) {
            //2.设置制单人和制单时间
            bill.setInputUser(old.getInputUser());
            bill.setInputTime(old.getInputTime());
            //2.先把该单据的所有明细删除
            stockOutcomeBillItemMapper.deleteItemId(bill.getId());
        }
        //3.迭代单据中每一条明细,去计算单据总金额和总数量
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalNumber = BigDecimal.ZERO;
        for (StockOutcomeBillItem item : bill.getItems()) {
            BigDecimal amount = item.getSalePrice().multiply(item.getNumber()).setScale(2, BigDecimal.ROUND_UP);
            //明细计算小计
            totalAmount = totalAmount.add(amount);
            totalNumber = totalNumber.add(item.getNumber());
            item.setAmount(amount);
            item.setBillId(bill.getId());
            //4.设置明细小计
            stockOutcomeBillItemMapper.insert(item);
        }


        //5设置单据设置总金额数和总数量
        bill.setTotalAmount(totalAmount);
        bill.setTotalNumber(totalNumber);
        //6.跟新单据独享
        stockOutcomeBillMapper.updateByPrimaryKey(bill);
    }

    //先删除单据的明细再删除单据1
    @Override
    public void delete(Long id) {
        stockOutcomeBillItemMapper.deleteItemId(id);
        stockOutcomeBillMapper.deleteByPrimaryKey(id);
    }

    @Override
    public StockOutcomeBill get(Long id) {
        return stockOutcomeBillMapper.selectByPrimaryKey(id);
    }

    public List<StockOutcomeBill> listAll() {
        return stockOutcomeBillMapper.selectAll();
    }

    @Override
    public PageResult query(QueryObject qo) {
        int rows = stockOutcomeBillMapper.queryForCount(qo);
        if (rows == 0) {
            return PageResult.EMPTY_PAGE;
        }
        List<StockOutcomeBill> result = stockOutcomeBillMapper.queryForList(qo);
        return new PageResult(result, rows, qo.getCurrentPage(), qo.getPageSize());
    }

    @Override
    public void audit(Long billId) {
        //1.查询出单据对象,判断是否处于待审核
        StockOutcomeBill bill = stockOutcomeBillMapper.selectByPrimaryKey(billId);
        if (bill.getStatus() == StockOutcomeBill.STATE_NORMAL) {
            //2.若是:设置已审核状态,审核时间,审核人
            bill.setAuditor(UserContext.getCurrentUser());
            bill.setAuditTime(new Date());
            bill.setStatus(StockOutcomeBill.STATE_AUDIT);
            //3.遍历明细.根据产品ID和仓库ID 获取
            stockOutcomeBillMapper.updateStatus(bill);
            //出库操作
            //迭代出没一个明细
            for (StockOutcomeBillItem item : bill.getItems()) {
                //5.根据仓库ID和货品ID查询库存信息
                ProductStock ps = stockMapper.selectByDepotAndProduct(bill.getDepot().getId(), item.getProduct().getId());
                if (ps == null) {
                    throw new RuntimeException(bill.getDepot().getName() + ",仓库中不存在" + item.getProduct().getName() + "货品!");
                }
                if (ps.getStoreNumber().compareTo(item.getNumber()) < 0) {
                    throw new RuntimeException(bill.getDepot().getName() + ",仓库中" + item.getProduct().getName() + "库存(" + ps.getStoreNumber() + ")不足" + item.getNumber());
                }
                //6.减少库存,修改库存信息
                ps.setStoreNumber(ps.getStoreNumber().subtract(item.getNumber()));
                ps.setAmount(ps.getAmount().subtract(item.getNumber().multiply(ps.getPrice())));
                stockMapper.updateByPrimaryKey(ps);
                //7.记录每一笔销售相关的账
                SaleAccount sa = new SaleAccount();

                sa.setVdate(new Date());
                sa.setNumber(item.getNumber());
                sa.setCostPrice(ps.getPrice());
                sa.setCostAmount(sa.getNumber().multiply(sa.getCostPrice()).setScale(2,BigDecimal.ROUND_HALF_UP));
                sa.setSalePrice(item.getSalePrice());
                sa.setSaleAmount(item.getAmount());
                sa.setProduct(item.getProduct());
                sa.setSaleman(bill.getInputUser());
                sa.setClient(bill.getClient());
                saleAccountMapper.insert(sa);
            }
        }
    }
}
