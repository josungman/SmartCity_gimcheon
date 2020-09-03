package com.DBtoDB;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RcvMsgTest {
    public static ApplicationContext ctx;

    public static void main( String[] args ) {
        ctx = new ClassPathXmlApplicationContext( "rcv/bean.xml" );
    }
}
