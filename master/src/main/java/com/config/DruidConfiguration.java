package com.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import lombok.Data;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;


/**
 * @author by wesharn
 */
@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
@Data
public class DruidConfiguration {
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    private String driverClassName;
    private Integer initialSize;
    private Integer maxActive;
    private Integer minIdle;
    private Integer maxWait;
    @Value("${spring.datasource.timeBetweenEvictionRunsMillis:60000}")
    private Long timeBetweenEvictionRunsMillis;
    @Value("${spring.datasource.minEvictableIdleTimeMillis:30000}")
    private Long minEvictableIdleTimeMillis;
    @Value("${spring.datasource.validationQuery:select 1}")
    private String validationQuery;
    @Value("${spring.datasource.testWhileIdle:true}")
    private Boolean testWhileIdle;
    @Value("${spring.datasource.testOnBorrow:true}")
    private Boolean testOnBorrow;
    @Value("${spring.datasource.testOnReturn:false}")
    private Boolean testOnReturn;
    @Value("${spring.datasource.poolPreparedStatements:false}")
    private Boolean poolPreparedStatements;
    @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize:-1}")
    private Integer maxPoolPreparedStatementPerConnectionSize;

    private String filters;

    private Properties connectionProperties;

    @Primary
    @Bean(name="dataSource")
    public DataSource druidDataSouce() throws SQLException {

        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(this.url);
        druidDataSource.setUsername(this.username);
        druidDataSource.setPassword(this.password);
        druidDataSource.setDriverClassName(this.driverClassName);
        druidDataSource.setInitialSize(this.initialSize);
        druidDataSource.setMaxActive(this.maxActive);
        druidDataSource.setMinIdle(this.minIdle);
        druidDataSource.setMaxWait(this.maxWait);
        druidDataSource.setTimeBetweenEvictionRunsMillis(this.timeBetweenEvictionRunsMillis);
        druidDataSource.setMinEvictableIdleTimeMillis(this.minEvictableIdleTimeMillis);
        druidDataSource.setValidationQuery(this.validationQuery);
        druidDataSource.setTestWhileIdle(this.testWhileIdle);
        druidDataSource.setTestOnBorrow(this.testOnBorrow);
        druidDataSource.setTestOnReturn(this.testOnReturn);
        druidDataSource.setPoolPreparedStatements(this.poolPreparedStatements);
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(this.maxPoolPreparedStatementPerConnectionSize);
        try {
            druidDataSource.init();
        } catch (Throwable e) {
            throw new RuntimeException("load datasource error, dbProperties is :", e);
        }

        return druidDataSource;
    }


    /**
     * druid统计信息
     * @return
     */
    @Bean
    public ServletRegistrationBean druidStateViewServlet() {
        return new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
    }

    /**
     * druid采集web-jdbc关联监控的数据
     * @return

    */
    @Bean
    public FilterRegistrationBean druidStateFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
    /**
     *创建sqlSessionFactoryBean 实例
     * 并且设置configtion 如驼峰命名.等等
     * 设置mapper 映射路径
     * 设置datasource数据源
     * @return
     * @throws Exception
     */
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactoryBean createSqlSessionFactoryBean(@Qualifier("dataSource") DataSource dataSource) throws Exception {
            SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
            return sqlSessionFactoryBean;
    }
}
