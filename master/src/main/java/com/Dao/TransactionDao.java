package com.Dao;

import com.mapper.TransactionMapper;
import com.model.TransactionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wesharn
 * @version V1.0
 * @Title:
 * @Package ${package_name}
 * @Description: TODO
 * @date ${date}
 */
@Component
public class TransactionDao {

    @Autowired
    private TransactionMapper transactionMapper;

 /*   public List<TransactionInfo> selectAll(){

        return transactionMapper.selectAll();
    }*/

    public void insertOne(TransactionInfo record){
        transactionMapper.insert(record.getName(),record.getAmount());
    }
}
