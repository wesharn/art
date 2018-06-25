package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wesharn
 * @version V1.0
 * @Title:
 * @Package ${package_name}
 * @Description: TODO
 * @date ${date}
 */
@MapperScan("com.mapper")
@SpringBootApplication
public class MasterBoot  {
        public static void main(String[] args) {
            SpringApplication.run(MasterBoot.class, args);
        }

}
