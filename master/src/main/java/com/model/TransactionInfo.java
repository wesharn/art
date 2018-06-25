package com.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author wesharn
 * @version V1.0
 * @Title:
 * @Package ${package_name}
 * @Description: TODO
 * @date ${date}
 */
@Data
@Table(name ="transaction")
//spring-data-mogodb对应注解 @Document(collection="mongodb 对应 collection 名") @Field
public class TransactionInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "amount")
    private Double amount;
}
