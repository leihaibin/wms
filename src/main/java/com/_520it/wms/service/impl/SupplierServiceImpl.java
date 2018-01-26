package com._520it.wms.service.impl;

import com._520it.wms.domain.Supplier;
import com._520it.wms.mapper.SupplierMapper;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    private SupplierMapper supplierMapper;
    public void saveOrUpdate(Supplier d) {
        if(d.getId()==null){
           supplierMapper.insert(d);
        }else{
            supplierMapper.updateByPrimaryKey(d);
        }
    }

    public void delete(Long id) {
        supplierMapper.deleteByPrimaryKey(id);
    }

    public Supplier get(Long id) {
        return supplierMapper.selectByPrimaryKey(id);
    }

    public List<Supplier> listAll() {
        return supplierMapper.selectAll();
    }
    public PageResult query(QueryObject qo){
                int rows=supplierMapper.queryForCount(qo);
                        if(rows==0){
                            return PageResult.EMPTY_PAGE;
                        }
                        List<?> result=supplierMapper.queryForList(qo);
                        return new PageResult(result,rows,qo.getCurrentPage(),qo.getPageSize());
    }
}
