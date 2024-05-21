package expeditors.backend.adoptapp;

import expeditors.backend.adoptapp.domain.Adopter;
import expeditors.backend.adoptapp.domain.Pet;
import expeditors.backend.adoptapp.service.AdopterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static java.lang.System.out;

//@SpringBootApplication
public class AdopterSpringBootApp {
    public static void main(String[] args) {
        SpringApplication.run(AdopterSpringBootApp.class, args);
    }
}

