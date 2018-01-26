package com._520it.wms.query;

import com._520it.wms.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class StockOutcomeBillQueryObject extends QueryObject {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date biginDate;//业务开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;//业务结束时间
    private Long depotId=-1L;//仓库ID
    private Long clientId=-1L;//客户ID
    private int status=-1;//审核状态

    public Date getEndDate(){
        return endDate !=null?DateUtil.getEndDate(endDate):null;
    }
}
