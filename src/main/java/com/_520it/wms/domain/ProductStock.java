package com._520it.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
//库存对象
@Getter
@Setter
public class ProductStock extends BaseDomain{
    private Product product;//货品
    private Depot depot;//仓库
    private BigDecimal price;//库曾价格.使用一定加权平均算法
    private BigDecimal storeNumber;//库蹭凉
    private BigDecimal amount;//库存总额
}
