package com.van.lily.module.order;

import com.van.lily.commons.define.base.PackageConstants;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * APP
 *
 */
@SpringBootApplication(scanBasePackages = {
        PackageConstants.FRAMEWORK,
        PackageConstants.COMMONS,
        PackageConstants.MODELS,

        PackageConstants.ORDER,
})
@MapperScan(basePackages = PackageConstants.MODELS)
@EnableTransactionManagement
@EnableScheduling
public class App
{
    public static void main( String[] args )
    {

        SpringApplication.run(App.class);
    }
}