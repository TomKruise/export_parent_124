package com.tom.app;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class MailConsumer {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("applicationContext-mq-consumer.xml");
        app.start();
        System.in.read();
    }
}
