package com._520it.wms.mapper;

import com._520it.wms.domain.ProductStock;
import com._520it.wms.query.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductStockMapper {
    int insert(ProductStock record);
    int updateByPrimaryKey(ProductStock record);

    ProductStock selectByDepotAndProduct(@Param("depotId")
     Long depotId, @Param("productId") Long productId);

    int queryForCount(QueryObject qo);

    List<?> queryForList(QueryObject qo);
}