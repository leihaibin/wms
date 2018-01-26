package com._520it.wms.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class OrderBill extends BaseDomain {
    public static final int STATE_NORMAL=0;//待审核
    public static final int STATE_AUDIT=1;//已审核
    private String sn;//单据编号
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date vdate;//业务时间
    private int status =STATE_NORMAL;//审核状态
    private BigDecimal totalAmount;//采购总金额
    private BigDecimal totalNumber;//采购总数量
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date inputTime;//制单时间
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date auditTime;//审核时间
    private Employee inputUser;//制单人
    private Employee auditor;//审核人
    private Supplier supplier;//供应商
    private List<OrderBillItem> items =new ArrayList<>();//订单的明细列表
    public String getDisplayStatus(){
        return this.status==STATE_NORMAL?"待审核":"已审核";
    }
}
