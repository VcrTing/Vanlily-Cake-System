package com.van.lily.module.product;

import com.van.lily.commons.define.base.PackageConstants;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * APP
 *
 */
@SpringBootApplication(scanBasePackages = {
        PackageConstants.FRAMEWORK,
        PackageConstants.COMMONS,
        PackageConstants.MODELS,

        PackageConstants.PRODUCT,
})
@MapperScan(basePackages = PackageConstants.MODELS)
@EnableTransactionManagement
public class App
{
    public static void main( String[] args )
    {

        SpringApplication.run(App.class);
    }
}