package net.lustenauer.vaadindemo;

import net.lustenauer.vaadindemo.dao.PlcRepository;
import net.lustenauer.vaadindemo.model.Plc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App {
    private final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {

        SpringApplication.run(App.class, args);
    }

    @Bean
    public CommandLineRunner loadData(PlcRepository repository) {
        return (args) -> {
            // save a couple of customers
            repository.save(new Plc("RBG1", "Regalbediengerät 1", "192.168.10.1", 10000, 102, 0, 1, true));
            repository.save(new Plc("RBG2", "Regalbediengerät 2", "192.168.10.2", 10000, 102, 0, 1, true));
            repository.save(new Plc("RBG3", "Regalbediengerät 3", "192.168.10.3", 10000, 102, 0, 1, true));
            repository.save(new Plc("RBG4", "Regalbediengerät 4", "192.168.10.4", 10000, 102, 0, 1, true));

        };
    }
}
