package com.automationcompany.project.config;

import com.automationcompany.project.model.Project;
import com.automationcompany.project.model.ProjectPriority;
import com.automationcompany.project.model.ProjectServiceType;
import com.automationcompany.project.model.ProjectStatus;
import com.automationcompany.project.repository.ProjectRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Configuration
@Profile("dev")
@RequiredArgsConstructor
@Slf4j
public class DataInitializer {

    private final ProjectRepository projectRepository;

    @PostConstruct
    @Transactional
    public void initDatabase() {
        if (projectRepository.count() > 0) {
            log.info("Database already contains {} projects. Skipping initialization.",
                    projectRepository.count());
            return;
        }

        log.info("Initializing database with sample projects...");

        var projects = List.of(
                createProject(
                        "Automatyzacja linii produkcyjnej FIAT",
                        "PRJ-2024-001",
                        "Kompleksowa automatyzacja linii produkcyjnej w zakładzie FIAT w Tychach",
                        LocalDate.of(2024, 1, 15),
                        LocalDate.of(2024, 12, 31),
                        ProjectStatus.IN_PROGRESS,
                        ProjectPriority.HIGH,
                        ProjectServiceType.PRODUCTION_SUPPORT,
                        "Tychy, Polska",
                        Set.of(1L, 2L, 3L, 5L, 7L), // IDs pracowników z employee service
                        3L // Project Manager ID
                ),

                createProject(
                        "Projekt maszyny pakującej XYZ-2000",
                        "PRJ-2024-002",
                        "Projektowanie i realizacja nowej maszyny pakującej dla przemysłu spożywczego",
                        LocalDate.of(2024, 3, 1),
                        LocalDate.of(2025, 2, 28),
                        ProjectStatus.IN_PROGRESS,
                        ProjectPriority.CRITICAL,
                        ProjectServiceType.MACHINE_DESIGN,
                        "Warszawa, Polska",
                        Set.of(1L, 2L, 4L, 6L),
                        6L
                ),

                createProject(
                        "Modernizacja systemu elektrycznego",
                        "PRJ-2024-003",
                        "Modernizacja systemu elektrycznego w zakładzie produkcyjnym",
                        LocalDate.of(2024, 2, 1),
                        LocalDate.of(2024, 8, 31),
                        ProjectStatus.COMPLETED,
                        ProjectPriority.MEDIUM,
                        ProjectServiceType.ELECTRICAL_DESIGN,
                        "Kraków, Polska",
                        Set.of(4L, 5L, 10L),
                        null
                ),

                createProject(
                        "Realizacja przenośnika taśmowego",
                        "PRJ-2024-004",
                        "Budowa i montaż systemu przenośników taśmowych",
                        LocalDate.of(2024, 5, 1),
                        null,
                        ProjectStatus.IN_PROGRESS,
                        ProjectPriority.HIGH,
                        ProjectServiceType.MACHINE_REALIZATION,
                        "Wrocław, Polska",
                        Set.of(3L, 7L, 9L),
                        7L
                ),

                createProject(
                        "Prace elektryczne - hala montażowa",
                        "PRJ-2024-005",
                        "Wykonanie instalacji elektrycznej w nowej hali montażowej",
                        LocalDate.of(2024, 4, 15),
                        LocalDate.of(2024, 10, 31),
                        ProjectStatus.IN_PROGRESS,
                        ProjectPriority.MEDIUM,
                        ProjectServiceType.ELECTRICAL_WORKS,
                        "Poznań, Polska",
                        Set.of(4L, 5L),
                        3L
                ),

                createProject(
                        "System hydrauliczny prasy",
                        "PRJ-2024-006",
                        "Projektowanie i montaż systemu hydraulicznego dla prasy 500T",
                        LocalDate.of(2024, 6, 1),
                        LocalDate.of(2024, 11, 30),
                        ProjectStatus.IN_PROGRESS,
                        ProjectPriority.HIGH,
                        ProjectServiceType.HYDRAULICS,
                        "Gliwice, Polska",
                        Set.of(2L, 9L),
                        7L
                ),

                createProject(
                        "Wsparcie produkcji - Q1 2024",
                        "PRJ-2024-007",
                        "Bieżące wsparcie techniczne linii produkcyjnych",
                        LocalDate.of(2024, 1, 1),
                        LocalDate.of(2024, 3, 31),
                        ProjectStatus.COMPLETED,
                        ProjectPriority.LOW,
                        ProjectServiceType.PRODUCTION_SUPPORT,
                        "Gdańsk, Polska",
                        Set.of(5L, 8L),
                        null
                ),

                createProject(
                        "Prototyp robota spawalniczego",
                        "PRJ-2024-008",
                        "Projekt i realizacja prototypu robota spawalniczego",
                        LocalDate.of(2024, 7, 1),
                        LocalDate.of(2025, 6, 30),
                        ProjectStatus.PLANNING,
                        ProjectPriority.CRITICAL,
                        ProjectServiceType.MACHINE_DESIGN,
                        "Katowice, Polska",
                        Set.of(1L, 2L, 3L, 4L, 7L),
                        6L
                ),

                createProject(
                        "Modernizacja sterowania PLC",
                        "PRJ-2024-009",
                        "Aktualizacja systemu sterowania PLC w zakładzie",
                        LocalDate.of(2024, 3, 15),
                        LocalDate.of(2024, 9, 15),
                        ProjectStatus.ON_HOLD,
                        ProjectPriority.MEDIUM,
                        ProjectServiceType.ELECTRICAL_WORKS,
                        "Łódź, Polska",
                        Set.of(4L, 10L),
                        null
                ),

                createProject(
                        "Linia montażowa - projekt pilotażowy",
                        "PRJ-2023-010",
                        "Projekt pilotażowy nowej linii montażowej",
                        LocalDate.of(2023, 10, 1),
                        LocalDate.of(2024, 1, 31),
                        ProjectStatus.CANCELLED,
                        ProjectPriority.LOW,
                        ProjectServiceType.MACHINE_REALIZATION,
                        "Szczecin, Polska",
                        Set.of(),
                        null
                ),

                createProject(
                        "Automatyzacja magazynu wysokiego składowania",
                        "PRJ-2024-011",
                        "Projektowanie systemu automatycznego magazynu WMS",
                        LocalDate.of(2024, 8, 1),
                        LocalDate.of(2025, 7, 31),
                        ProjectStatus.IN_PROGRESS,
                        ProjectPriority.HIGH,
                        ProjectServiceType.MACHINE_DESIGN,
                        "Warszawa, Polska",
                        Set.of(1L, 2L, 6L, 7L),
                        6L
                ),

                createProject(
                        "Instalacja hydrauliczna - linia lakiernicza",
                        "PRJ-2024-012",
                        "Montaż systemu hydraulicznego dla nowej linii lakierniczej",
                        LocalDate.of(2024, 9, 1),
                        LocalDate.of(2025, 1, 31),
                        ProjectStatus.PLANNING,
                        ProjectPriority.MEDIUM,
                        ProjectServiceType.HYDRAULICS,
                        "Tychy, Polska",
                        Set.of(9L),
                        7L
                )
        );

        projectRepository.saveAll(projects);
        log.info("✅ Successfully initialized database with {} projects", projects.size());
    }

    private Project createProject(String name, String code, String description,
                                  LocalDate startDate, LocalDate endDate,
                                  ProjectStatus status, ProjectPriority priority,
                                  ProjectServiceType serviceType, String location,
                                  Set<Long> employeeIds, Long projectManagerId) {
        return Project.builder()
                .name(name)
                .code(code)
                .description(description)
                .startDate(startDate)
                .endDate(endDate)
                .status(status)
                .priority(priority)
                .serviceType(serviceType)
                .location(location)
                .employeeIds(employeeIds)
                .projectManagerId(projectManagerId)
                .createdBy("SYSTEM")
                .updatedBy("SYSTEM")
                .build();
    }
}