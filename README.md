# Jasper Report Spring Boot Application

A Spring Boot application that generates JasperReports and stores them in a local path (GCS bucket mount).

## Prerequisites

- Java 17 or higher
- Gradle

## Technologies Used

- Spring Boot 4.0.0
- Spring Data JPA
- JasperReports 6.21.0
- H2 Database (in-memory)
- OpenPDF 1.3.30

## Configuration

The application stores generated reports to a local path configured in `application.properties`:

```properties
report.storage.path=/home/wongderrick/workspaces/jasper-reports-gcs-bucket
```

Update this path to match your GCS bucket mount point.

## Database

The application uses an in-memory H2 database with sample employee data loaded on startup.

### H2 Console

Access the H2 console at: `http://localhost:8080/h2-console`

- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (empty)

## Running the Application

```bash
./gradlew bootRun
```

The application will start on `http://localhost:8080`

## API Endpoints

### Generate Employee Report

```
GET /api/reports/employees?format=pdf
```

**Parameters:**
- `format` (optional): Report format. Default is `pdf`

**Response:**
```json
{
  "message": "Report generated successfully",
  "filePath": "/home/wongderrick/workspaces/jasper-reports-gcs-bucket/employee_report_20251125_143022.pdf"
}
```

The report is saved to the configured storage path with a timestamped filename.

## Sample Data

The application loads 8 sample employees on startup:
- John Doe (IT, $75,000)
- Jane Smith (HR, $65,000)
- Michael Johnson (Finance, $80,000)
- Emily Davis (IT, $72,000)
- David Wilson (Marketing, $68,000)
- Sarah Brown (IT, $77,000)
- Robert Taylor (Finance, $82,000)
- Lisa Anderson (HR, $63,000)

## Building the Project

```bash
./gradlew build
```

## Report Template

The JasperReports template is located at:
```
src/main/resources/reports/employee_report.jrxml
```

The template generates a PDF report with employee information in a table format.
