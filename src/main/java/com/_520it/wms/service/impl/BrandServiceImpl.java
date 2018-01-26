package com._520it.wms.service.impl;

import com._520it.wms.domain. Brand;
import com._520it.wms.mapper. BrandMapper;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service. BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements  BrandService {
    @Autowired
    private  BrandMapper brandMapper;
    public void saveOrUpdate( Brand d) {
        if(d.getId()==null){
           brandMapper.insert(d);
        }else{
            brandMapper.updateByPrimaryKey(d);
        }
    }

    public void delete(Long id) {
        brandMapper.deleteByPrimaryKey(id);
    }

    public Brand get(Long id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    public List< Brand> listAll() {
        return brandMapper.selectAll();
    }
    public PageResult query(QueryObject qo){
                int rows=brandMapper.queryForCount(qo);
                        if(rows==0){
                            return PageResult.EMPTY_PAGE;
                        }
                        List<?> result=brandMapper.queryForList(qo);
                        return new PageResult(result,rows,qo.getCurrentPage(),qo.getPageSize());
    }
}
