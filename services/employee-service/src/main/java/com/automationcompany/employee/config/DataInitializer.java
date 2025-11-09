package com.automationcompany.employee.config;

import com.automationcompany.employee.model.*;
import com.automationcompany.employee.repository.EmployeeRepository;
import com.automationcompany.commondomain.DepartmentType;
import com.automationcompany.commondomain.EmployeeStatus;
import com.automationcompany.commondomain.EmploymentType;
import com.automationcompany.commondomain.PositionLevel;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Configuration
@Profile("dev")
@RequiredArgsConstructor
@Slf4j
public class DataInitializer {

    private final EmployeeRepository employeeRepository;

    @PostConstruct
    @Transactional
    public void initDatabase() {
        if (employeeRepository.count() > 0) {
            log.info("Database already contains {} employees. Skipping initialization.",
                    employeeRepository.count());
            return;
        }

        log.info("Initializing database with sample employees...");

        var employees = List.of(
                createEmployee("Jan", "Kowalski", "85010112345", "jan.kowalski@automationcompany.com",
                        "+48123456789", LocalDate.of(1985, 1, 1), LocalDate.of(2020, 3, 15),
                        "15000.00", PositionLevel.SENIOR, DepartmentType.SOFTWARE, EmploymentType.FULL_TIME,
                        "ul. Główna 15", "Warszawa", "00-001",
                        "12345678901234567890123456", "PKO BP", "1234567890",
                        "Anna Kowalska", "+48987654321", "Żona"),

                createEmployee("Maria", "Nowak", "90050298765", "maria.nowak@automationcompany.com",
                        "+48234567890", LocalDate.of(1990, 5, 2), LocalDate.of(2018, 1, 10),
                        "20000.00", PositionLevel.LEAD, DepartmentType.AUTOMATION, EmploymentType.FULL_TIME,
                        "ul. Kwiatowa 22", "Kraków", "30-001",
                        "98765432109876543210987654", "mBank", "0987654321",
                        "Piotr Nowak", "+48876543210", "Brat"),

                createEmployee("Piotr", "Wiśniewski", "88112334567", "piotr.wisniewski@automationcompany.com",
                        "+48345678901", LocalDate.of(1988, 11, 23), LocalDate.of(2015, 6, 1),
                        "25000.00", PositionLevel.MANAGER, DepartmentType.MECHANICAL, EmploymentType.FULL_TIME,
                        "ul. Parkowa 8", "Wrocław", "50-001",
                        "11223344556677889900112233", "ING Bank Śląski", "1122334455",
                        "Katarzyna Wiśniewska", "+48765432109", "Matka"),

                createEmployee("Anna", "Kamińska", "95030445678", "anna.kaminska@automationcompany.com",
                        "+48456789012", LocalDate.of(1995, 3, 4), LocalDate.of(2022, 9, 1),
                        "8000.00", PositionLevel.JUNIOR, DepartmentType.ELECTRICAL, EmploymentType.FULL_TIME,
                        "ul. Słoneczna 5", "Poznań", "60-001",
                        "55667788990011223344556677", "Santander Bank", "5566778899",
                        "Tomasz Kamiński", "+48654321098", "Ojciec"),

                createEmployee("Tomasz", "Lewandowski", "82070756789", "tomasz.lewandowski@automationcompany.com",
                        "+48567890123", LocalDate.of(1982, 7, 7), LocalDate.of(2010, 2, 15),
                        "18000.00", PositionLevel.SENIOR, DepartmentType.QUALITY_ASSURANCE, EmploymentType.FULL_TIME,
                        "ul. Leśna 12", "Gdańsk", "80-001",
                        "99887766554433221100998877", "Pekao S.A.", "9988776655",
                        "Joanna Lewandowska", "+48543210987", "Żona"),

                createEmployee("Katarzyna", "Zielińska", "78090867890", "katarzyna.zielinska@automationcompany.com",
                        "+48678901234", LocalDate.of(1978, 9, 8), LocalDate.of(2012, 5, 20),
                        "22000.00", PositionLevel.MANAGER, DepartmentType.HR, EmploymentType.FULL_TIME,
                        "ul. Różana 30", "Łódź", "90-001",
                        "12121212121212121212121212", "Bank Millennium", "1212121212",
                        "Marek Zieliński", "+48432109876", "Mąż"),

                createEmployee("Michał", "Szymański", "75040978901", "michal.szymanski@automationcompany.com",
                        "+48789012345", LocalDate.of(1975, 4, 9), LocalDate.of(2008, 11, 1),
                        "30000.00", PositionLevel.DIRECTOR, DepartmentType.MANAGEMENT, EmploymentType.FULL_TIME,
                        "ul. Zamkowa 1", "Warszawa", "00-002",
                        "34343434343434343434343434", "PKO BP", "3434343434",
                        "Elżbieta Szymańska", "+48321098765", "Żona"),

                createEmployee("Agnieszka", "Woźniak", "98021289012", "agnieszka.wozniak@automationcompany.com",
                        "+48890123456", LocalDate.of(1998, 2, 12), LocalDate.of(2023, 7, 1),
                        "5000.00", PositionLevel.INTERN, DepartmentType.SOFTWARE, EmploymentType.INTERNSHIP,
                        "ul. Młodzieżowa 44", "Katowice", "40-001",
                        "56565656565656565656565656", "Alior Bank", "5656565656",
                        "Barbara Woźniak", "+48210987654", "Matka"),

                createEmployee("Robert", "Dąbrowski", "92060512345", "robert.dabrowski@automationcompany.com",
                        "+48901234567", LocalDate.of(1992, 6, 5), LocalDate.of(2021, 4, 1),
                        "12000.00", PositionLevel.MID, DepartmentType.MAINTENANCE, EmploymentType.FULL_TIME,
                        "ul. Lipowa 18", "Szczecin", "70-001",
                        "78787878787878787878787878", "BNP Paribas", "7878787878",
                        "Magdalena Dąbrowska", "+48109876543", "Żona"),

                createEmployee("Ewa", "Krawczyk", "87030398765", "ewa.krawczyk@automationcompany.com",
                        "+48112345678", LocalDate.of(1987, 3, 3), LocalDate.of(2016, 8, 15),
                        "19000.00", PositionLevel.SENIOR, DepartmentType.FINANCE, EmploymentType.FULL_TIME,
                        "ul. Morska 9", "Gdynia", "81-001",
                        "13131313131313131313131313", "Credit Agricole", "1313131313",
                        "Paweł Krawczyk", "+48998765432", "Mąż")
        );

        employeeRepository.saveAll(employees);
        log.info("✅ Successfully initialized database with {} employees", employees.size());
    }

    private Employee createEmployee(String firstName, String lastName, String pesel, String email,
                                    String phone, LocalDate dob, LocalDate hireDate,
                                    String salary, PositionLevel position, DepartmentType dept,
                                    EmploymentType empType, String street, String city, String postal,
                                    String bankAccount, String bankName, String taxId,
                                    String emergName, String emergPhone, String emergRelation) {
        return Employee.builder()
                .firstName(firstName)
                .lastName(lastName)
                .pesel(pesel)
                .email(email)
                .phoneNumber(phone)
                .dateOfBirth(dob)
                .hireDate(hireDate)
                .salary(new BigDecimal(salary))
                .positionLevel(position)
                .department(dept)
                .employmentType(empType)
                .status(EmployeeStatus.ACTIVE)
                .address(Address.builder()
                        .street(street)
                        .city(city)
                        .postalCode(postal)
                        .country("Polska")
                        .build())
                .bankDetails(BankDetails.builder()
                        .bankAccountNumber(bankAccount)
                        .bankName(bankName)
                        .taxId(taxId)
                        .build())
                .emergencyContact(EmergencyContact.builder()
                        .name(emergName)
                        .phone(emergPhone)
                        .relation(emergRelation)
                        .build())
                .createdBy("SYSTEM")
                .updatedBy("SYSTEM")
                .build();
    }
}