package com.automationcompany.project.service;

import com.automationcompany.commondomain.dto.EmployeeReadDto;
import com.automationcompany.project.client.EmployeeWebClient;
import com.automationcompany.project.mapper.ProjectMapper;
import com.automationcompany.project.model.Project;
import com.automationcompany.project.model.ProjectServiceType;
import com.automationcompany.project.model.ProjectStatus;
import com.automationcompany.project.model.dto.ProjectCreateDto;
import com.automationcompany.project.model.dto.ProjectDto;
import com.automationcompany.project.repository.ProjectRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import mockwebserver3.MockResponse;
import mockwebserver3.MockWebServer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    private MockWebServer mockWebServer;
    private ProjectService projectService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectMapper projectMapper;

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        WebClient webClient = WebClient.builder()
                .baseUrl(mockWebServer.url("/").toString())
                .build();

        EmployeeWebClient employeeWebClient = new EmployeeWebClient(webClient);
        projectService = new ProjectService(projectRepository, projectMapper, employeeWebClient);
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.close();
    }

    private ProjectCreateDto validCreateDto() {
        return ProjectCreateDto.builder()
                .name("Test Project")
                .status(ProjectStatus.PLANNING)
                .serviceType(ProjectServiceType.MACHINE_DESIGN)
                .startDate(LocalDate.of(2025, 1, 1))
                .build();
    }

    @Test
    void createProject_shouldSave_whenManagerExists() throws Exception {
        EmployeeReadDto manager = new EmployeeReadDto();
        manager.setId(1L);

        mockWebServer.enqueue(new MockResponse.Builder()
                .body(objectMapper.writeValueAsString(manager))
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build());

        ProjectCreateDto dto = ProjectCreateDto.builder()
                .name("Test Project")
                .status(ProjectStatus.PLANNING)
                .serviceType(ProjectServiceType.MACHINE_DESIGN)
                .startDate(LocalDate.of(2025, 1, 1))
                .projectManagerId(1L)
                .build();

        ProjectDto expectedDto = ProjectDto.builder().id(1L).name("Test Project").build();

        when(projectMapper.toEntity(any())).thenReturn(new Project());
        when(projectRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(projectMapper.toDto(any())).thenReturn(expectedDto);

        ProjectDto result = projectService.createProject(dto);

        assertThat(result).isNotNull();
        System.out.println("Manager: " + manager.getFirstName() + " " + manager.getLastName() + " (id=" + manager.getId() + ")");
        System.out.println("Wynik z serwisu: " + result);
    }
}