package com.service.ServiceImpl;

import com.Dao.TransactionDao;
import com.model.TransactionInfo;
import com.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wesharn
 * @version V1.0
 * @Title:
 * @Package ${package_name}
 * @Description: TODO
 * @date ${date}
 */
@Qualifier("transactionService")
@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionDao transactionDao;

    //@Transactional
    @Override
    public void testA(){
        TransactionInfo recoder =new TransactionInfo();
        recoder.setAmount(10.0);
        recoder.setName("A");
        transactionDao.insertOne(recoder);

    }
    //@Transactional
    public void testB(){
        TransactionInfo recoder =new TransactionInfo();
        recoder.setAmount(20.0);
        recoder.setName("B");
        transactionDao.insertOne(recoder);

    }

}
