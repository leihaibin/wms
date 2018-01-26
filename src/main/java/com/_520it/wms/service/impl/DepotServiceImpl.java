package com._520it.wms.service.impl;

import com._520it.wms.domain.Depot;
import com._520it.wms.mapper.DepotMapper;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.DepotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepotServiceImpl implements  DepotService {
    @Autowired
    private  DepotMapper depotMapper;
    public void saveOrUpdate( Depot d) {
        if(d.getId()==null){
           depotMapper.insert(d);
        }else{
            depotMapper.updateByPrimaryKey(d);
        }
    }

    public void delete(Long id) {
        depotMapper.deleteByPrimaryKey(id);
    }

    public Depot get(Long id) {
        return depotMapper.selectByPrimaryKey(id);
    }

    public List< Depot> listAll() {
        return depotMapper.selectAll();
    }
    public PageResult query(QueryObject qo){
                int rows=depotMapper.queryForCount(qo);
                        if(rows==0){
                            return PageResult.EMPTY_PAGE;
                        }
                        List<?> result=depotMapper.queryForList(qo);
                        return new PageResult(result,rows,qo.getCurrentPage(),qo.getPageSize());
    }
}
