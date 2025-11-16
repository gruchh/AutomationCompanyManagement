package com.automationcompany.project.config;

import com.automationcompany.project.model.*;
import com.automationcompany.project.repository.LocationRepository;
import com.automationcompany.project.repository.ProjectRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Configuration
@Profile("dev")
@RequiredArgsConstructor
@Slf4j
public class DataInitializer {

    private final ProjectRepository projectRepository;
    private final LocationRepository locationRepository;

    @PostConstruct
    @Transactional
    public void initDatabase() {
        if (projectRepository.count() > 0) {
            log.info("Database already contains {} projects. Skipping initialization.",
                    projectRepository.count());
            return;
        }

        log.info("Initializing database with sample locations and projects...");

        // Najpierw tworzymy lokalizacje
        Map<String, Location> locations = createLocations();

        log.info("✅ Successfully initialized {} locations", locations.size());

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
                        locations.get("Tychy"),
                        Set.of(1L, 2L, 3L, 5L, 7L),
                        3L,
                        Set.of(ProjectTechnology.SIEMENS_S7, ProjectTechnology.WINCC, ProjectTechnology.OPC_UA)
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
                        locations.get("Warszawa"),
                        Set.of(1L, 2L, 4L, 6L),
                        6L,
                        Set.of(ProjectTechnology.BECKHOFF_TWINCAT, ProjectTechnology.MQTT)
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
                        locations.get("Kraków"),
                        Set.of(4L, 5L, 10L),
                        null,
                        Set.of(ProjectTechnology.SCHNEIDER_PLC, ProjectTechnology.MODBUS)
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
                        locations.get("Wrocław"),
                        Set.of(3L, 7L, 9L),
                        7L,
                        Set.of(ProjectTechnology.ALLEN_BRADLEY, ProjectTechnology.ETHERNET_IP)
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
                        locations.get("Poznań"),
                        Set.of(4L, 5L),
                        3L,
                        Set.of(ProjectTechnology.SCHNEIDER_PLC)
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
                        locations.get("Gliwice"),
                        Set.of(2L, 9L),
                        7L,
                        Set.of(ProjectTechnology.OPC_UA, ProjectTechnology.C_PLUS_PLUS)
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
                        locations.get("Gdańsk"),
                        Set.of(5L, 8L),
                        null,
                        Set.of(ProjectTechnology.WINCC)
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
                        locations.get("Katowice"),
                        Set.of(1L, 2L, 3L, 4L, 7L),
                        6L,
                        Set.of(ProjectTechnology.KUKA_ROBOT, ProjectTechnology.PYTHON)
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
                        locations.get("Łódź"),
                        Set.of(4L, 10L),
                        null,
                        Set.of(ProjectTechnology.OMRON_PLC)
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
                        locations.get("Szczecin"),
                        Set.of(),
                        null,
                        Set.of(ProjectTechnology.OTHER_TECH)
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
                        locations.get("Grudziądz"),
                        Set.of(1L, 2L, 6L, 7L),
                        6L,
                        Set.of(ProjectTechnology.JAVA, ProjectTechnology.DOCKER)
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
                        locations.get("Września"),
                        Set.of(9L),
                        7L,
                        Set.of(ProjectTechnology.C_SHARP)
                )
        );

        projectRepository.saveAll(projects);
        log.info("✅ Successfully initialized database with {} projects", projects.size());
    }

    private Map<String, Location> createLocations() {
        Map<String, Location> locationMap = new HashMap<>();

        var locations = List.of(
                createLocation("Warszawa, Polska", 52.2297, 21.0122, "Polska", "Warszawa"),
                createLocation("Tychy, Polska", 50.1348, 18.9686, "Polska", "Tychy"),
                createLocation("Kraków, Polska", 50.0647, 19.9450, "Polska", "Kraków"),
                createLocation("Wrocław, Polska", 51.1079, 17.0385, "Polska", "Wrocław"),
                createLocation("Poznań, Polska", 52.4064, 16.9252, "Polska", "Poznań"),
                createLocation("Gliwice, Polska", 50.2945, 18.6714, "Polska", "Gliwice"),
                createLocation("Gdańsk, Polska", 54.3520, 18.6466, "Polska", "Gdańsk"),
                createLocation("Katowice, Polska", 50.2649, 19.0238, "Polska", "Katowice"),
                createLocation("Łódź, Polska", 51.7592, 19.4560, "Polska", "Łódź"),
                createLocation("Szczecin, Polska", 53.4285, 14.5528, "Polska", "Szczecin"),
                createLocation("Grudziądz, Polska", 53.4838, 18.7536, "Polska", "Grudziądz"),
                createLocation("Września, Polska", 52.3254, 17.5661, "Polska", "Września")
        );

        List<Location> savedLocations = locationRepository.saveAll(locations);

        for (Location location : savedLocations) {
            String cityName = location.getCity();
            locationMap.put(cityName, location);
        }

        return locationMap;
    }

    private Location createLocation(String name, Double latitude, Double longitude, String country, String city) {
        return Location.builder()
                .name(name)
                .latitude(latitude)
                .longitude(longitude)
                .country(country)
                .city(city)
                .build();
    }

    private Project createProject(
            String name,
            String code,
            String description,
            LocalDate startDate,
            LocalDate endDate,
            ProjectStatus status,
            ProjectPriority priority,
            ProjectServiceType serviceType,
            Location location,
            Set<Long> employeeIds,
            Long projectManagerId,
            Set<ProjectTechnology> technologies
    ) {
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
                .technologies(technologies)
                .createdBy("SYSTEM")
                .updatedBy("SYSTEM")
                .build();
    }
}