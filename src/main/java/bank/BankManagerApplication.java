package bank;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class BankManagerApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BankManagerApplication.class);

        OperationsConsoleListener listener = context.getBean(OperationsConsoleListener.class);

        Thread thread = new Thread(listener);
        thread.start();

        System.out.println("Bank application started. Type operations to interact with the system.");
    }

}