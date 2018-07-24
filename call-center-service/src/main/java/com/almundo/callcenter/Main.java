package com.almundo.callcenter;

import com.almundo.callcenter.config.AppConfiguration;
import com.almundo.callcenter.core.Dispatcher;
import com.almundo.callcenter.model.Call;
import com.almundo.callcenter.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
@EntityScan(basePackageClasses = {Main.class, AppConfiguration.class})
public class Main {

    private Dispatcher dispatcher;

    public static void main(String[] args) {
        final ConfigurableApplicationContext app = SpringApplication.run(Main.class, args);
        SpringApplication.exit(app);
    }

    @Bean
    public CommandLineRunner startCallCenter(EmployeeRepository employeeRepository) {
        return args -> {
            dispatcher = Dispatcher.getInstance(employeeRepository);
            execute();
        };
    }

    public void execute() {
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        // method reference introduced in Java 8
        executorService.submit(this::run);
        executorService.submit(this::run);
        executorService.submit(this::run);
        executorService.submit(this::run);
        executorService.submit(this::run);
        executorService.submit(this::run);
        executorService.submit(this::run);
        executorService.submit(this::run);
        executorService.submit(this::run);
        executorService.submit(this::run);

        // close executorService
        executorService.shutdown();
    }

    public void run() {
        try {
            Random random = new Random();
            dispatcher.dispatchCall(new Call(5000, String.valueOf(random.nextInt(10))));
        } catch (Exception e) {
            System.out.println("Exception is caught");
        }
    }
}
