package xyz.anfun.customer_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import xyz.anfun.customer_service.netty.NettyServer;

@SpringBootApplication
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
        try {
            System.out.println("启动服务器 -- ");
            new NettyServer(8088).start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
