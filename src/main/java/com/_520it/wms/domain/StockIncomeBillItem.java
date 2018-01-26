package com._520it.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class StockIncomeBillItem extends BaseDomain {

    private Product product;//货品对象
    private BigDecimal costPrice;//采购价格
    private BigDecimal number;//采购数量
    private BigDecimal amount;//金额小计
    private String remark;//备注
    private Long billId;//对应采购订单id
}