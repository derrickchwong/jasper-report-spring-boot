package com.example.report;

import com.example.report.model.Employee;
import com.example.report.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(EmployeeRepository employeeRepository) {
        return args -> {
            employeeRepository.save(new Employee("John", "Doe", "john.doe@example.com", "IT", 75000.0));
            employeeRepository.save(new Employee("Jane", "Smith", "jane.smith@example.com", "HR", 65000.0));
            employeeRepository.save(new Employee("Michael", "Johnson", "michael.johnson@example.com", "Finance", 80000.0));
            employeeRepository.save(new Employee("Emily", "Davis", "emily.davis@example.com", "IT", 72000.0));
            employeeRepository.save(new Employee("David", "Wilson", "david.wilson@example.com", "Marketing", 68000.0));
            employeeRepository.save(new Employee("Sarah", "Brown", "sarah.brown@example.com", "IT", 77000.0));
            employeeRepository.save(new Employee("Robert", "Taylor", "robert.taylor@example.com", "Finance", 82000.0));
            employeeRepository.save(new Employee("Lisa", "Anderson", "lisa.anderson@example.com", "HR", 63000.0));

            System.out.println("Sample employee data loaded successfully!");
        };
    }
}
