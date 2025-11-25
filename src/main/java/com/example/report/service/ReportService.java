package com.example.report.service;

import com.example.report.model.Employee;
import com.example.report.repository.EmployeeRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Value("${report.storage.path}")
    private String reportStoragePath;

    public String generateEmployeeReport(String reportFormat) throws Exception {
        List<Employee> employees = employeeRepository.findAll();

        File file = ResourceUtils.getFile("classpath:reports/employee_report.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(employees);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Jasper Report System");

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        byte[] report;
        String fileExtension;

        if (reportFormat.equalsIgnoreCase("pdf")) {
            report = JasperExportManager.exportReportToPdf(jasperPrint);
            fileExtension = "pdf";
        } else {
            throw new IllegalArgumentException("Unsupported report format: " + reportFormat);
        }

        String fileName = generateFileName(fileExtension);
        String filePath = saveReportToFile(report, fileName);

        return filePath;
    }

    private String generateFileName(String extension) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String timestamp = LocalDateTime.now().format(formatter);
        return "employee_report_" + timestamp + "." + extension;
    }

    private String saveReportToFile(byte[] reportData, String fileName) throws Exception {
        Path storagePath = Paths.get(reportStoragePath);

        if (!Files.exists(storagePath)) {
            Files.createDirectories(storagePath);
        }

        Path filePath = storagePath.resolve(fileName);

        try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
            fos.write(reportData);
        }

        return filePath.toString();
    }
}
