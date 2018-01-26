package com._520it.wms.service.impl;

import com._520it.wms.domain.Brand;
import com._520it.wms.domain.Product;
import com._520it.wms.mapper.BrandMapper;
import com._520it.wms.mapper.ProductMapper;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.ProductQueryObject;
import com._520it.wms.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements  ProductService {
    @Autowired
    private  ProductMapper productMapper;
    @Autowired
    private BrandMapper brandMapper;
    public void saveOrUpdate( Product d) {
        if(d.getBrandId()!=null){
            Brand brand = brandMapper.selectByPrimaryKey(d.getBrandId());
            d.setBrandName(brand.getName());
        }
        if(d.getId()==null){
           productMapper.insert(d);
        }else{
            productMapper.updateByPrimaryKey(d);
        }
    }

    @Override
    public void delete(Long id) {
        productMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Product get(Long id) {
        return productMapper.selectByPrimaryKey(id);
    }

    @Override
    public List< Product> listAll() {
        return productMapper.selectAll();
    }
    @Override
    public PageResult query(ProductQueryObject qo){
                int rows=productMapper.queryForCount(qo);
                        if(rows==0){
                            return PageResult.EMPTY_PAGE;
                        }
                        List<?> result=productMapper.queryForList(qo);
                        return new PageResult(result,rows,qo.getCurrentPage(),qo.getPageSize());
    }
}
