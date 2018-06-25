package com.service.ServiceImpl;

import com.Dao.TransactionDao;
import com.model.TransactionInfo;
import com.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wesharn
 * @version V1.0
 * @Title:
 * @Package ${package_name}
 * @Description: TODO
 * @date ${date}
 */
@Service
public class TransactionSideServiceImpl implements TransactionService {
    @Autowired
    private TransactionDao transactionDao;

    //@Transactional(propagation=Propagation.REQUIRED)
    @Override
    public void testA(){
        TransactionInfo recoder =new TransactionInfo();
        recoder.setAmount(10.0);
        recoder.setName("C");
        transactionDao.insertOne(recoder);
    }
}
