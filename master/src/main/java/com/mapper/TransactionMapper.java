package com.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * @author wesharn
 * @version V1.0
 * @Title:
 * @Package ${package_name}
 * @Description: TODO
 * @date ${date}
 */
@Service
@Mapper
public interface TransactionMapper {
    @Insert("INSERT INTO TRANSACTION(NAME, AMOUNT) VALUES(#{name}, #{amount}")
    int insert(@Param("name") String name, @Param("amount") Double amount);

}
/*public interface TransactionMapper extends BasicMapper<TransactionInfo> {
    @Insert("INSERT INTO TRANSACTION(NAME, AMOUNT) VALUES(#{name}, #{amount}")
    int insert(@Param("name") String name, @Param("amount") Double aount);

}*/
