package com.DBtoDB;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SndMsgTest {
    public static ApplicationContext ctx;

    public static void main( String[] args ) {
        ctx = new ClassPathXmlApplicationContext( "snd/bean.xml" );
    }
}
