package Start;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	 public static ApplicationContext ctx;

	    public static void main( String[] args ) {
	        ctx = new ClassPathXmlApplicationContext( "bean/Start.xml" );
	    }
	}


