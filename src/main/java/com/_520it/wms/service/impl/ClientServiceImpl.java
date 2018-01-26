package com._520it.wms.service.impl;

import com._520it.wms.domain.Client;
import com._520it.wms.mapper.ClientMapper;
import com._520it.wms.query.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements  ClientService {
    @Autowired
    private  ClientMapper clientMapper;
    public void saveOrUpdate( Client d) {
        if(d.getId()==null){
           clientMapper.insert(d);
        }else{
            clientMapper.updateByPrimaryKey(d);
        }
    }

    public void delete(Long id) {
        clientMapper.deleteByPrimaryKey(id);
    }

    public Client get(Long id) {
        return clientMapper.selectByPrimaryKey(id);
    }

    public List< Client> listAll() {
        return clientMapper.selectAll();
    }
    public PageResult query(QueryObject qo){
                int rows=clientMapper.queryForCount(qo);
                        if(rows==0){
                            return PageResult.EMPTY_PAGE;
                        }
                        List<?> result=clientMapper.queryForList(qo);
                        return new PageResult(result,rows,qo.getCurrentPage(),qo.getPageSize());
    }
}
