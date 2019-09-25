package com.itheima;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//该注解声明该类是一个SpringBoot的引导类 -入口
@SpringBootApplication
public class MySpringBootApplication {

    //main是java程序的入口
    public static void main(String[] args) {
        //run方法表示要运行SpringBoot的引导类
        //参数：就是SpringBoot引导类的字节码对象
        SpringApplication.run(MySpringBootApplication.class);

    }
}

