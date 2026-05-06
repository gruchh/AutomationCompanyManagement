package com.automationcompany.project.controller;

import com.automationcompany.commondomain.dto.EmployeeReadDto;
import com.automationcompany.project.client.EmployeeWebClient;
import com.automationcompany.project.model.ProjectServiceType;
import com.automationcompany.project.model.ProjectStatus;
import com.automationcompany.project.model.dto.ProjectCardDto;
import com.automationcompany.project.model.dto.ProjectCreateDto;
import com.automationcompany.project.model.dto.ProjectDto;
import com.automationcompany.project.model.dto.ProjectFilterDto;
import com.automationcompany.project.service.ProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(ProjectController.class)
@WithMockUser
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProjectService projectService;

    @MockitoBean
    private EmployeeWebClient employeeWebClient;

    @Test
    void createProject_shouldReturn201() throws Exception {
        ProjectDto response = ProjectDto.builder()
                .id(1L)
                .name("Test Project")
                .status(ProjectStatus.PLANNING)
                .serviceType(ProjectServiceType.MACHINE_DESIGN)
                .startDate(LocalDate.of(2025, 1, 1))
                .build();

        when(projectService.createProject(any())).thenReturn(Mono.just(response));

        ProjectCreateDto request = ProjectCreateDto.builder()
                .name("Test Project")
                .status(ProjectStatus.PLANNING)
                .serviceType(ProjectServiceType.MACHINE_DESIGN)
                .startDate(LocalDate.of(2025, 1, 1))
                .build();

        mockMvc.perform(post("/api/public/projects")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test Project"));
    }

    @Test
    void createProject_shouldReturn400_whenNameBlank() throws Exception {
        ProjectCreateDto request = ProjectCreateDto.builder()
                .name("")
                .status(ProjectStatus.PLANNING)
                .serviceType(ProjectServiceType.MACHINE_DESIGN)
                .startDate(LocalDate.of(2025, 1, 1))
                .build();

        mockMvc.perform(post("/api/public/projects")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void searchProjectCards_shouldReturn200() throws Exception {
        List<ProjectCardDto> response = List.of(
                ProjectCardDto.builder().id(1L).name("Test Project").build()
        );

        when(projectService.filterProjects(any())).thenReturn(response);

        ProjectFilterDto filter = ProjectFilterDto.builder()
                .sortBy("name")
                .sortDirection("ASC")
                .build();

        mockMvc.perform(post("/api/public/projects/cards/search")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filter)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Test Project"));
    }

    @Test
    void getAllEmployees_shouldReturn200() throws Exception {
        when(employeeWebClient.getAllEmployees()).thenReturn(Flux.just(
                EmployeeReadDto.builder().id(1L).build(),
                EmployeeReadDto.builder().id(2L).build()
        ));

        mockMvc.perform(get("/api/public/projects/employees")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));
    }
}