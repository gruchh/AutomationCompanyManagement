package com.automationcompany.project.controller;

import com.automationcompany.project.model.ProjectServiceType;
import com.automationcompany.project.model.ProjectStatus;
import com.automationcompany.project.model.dto.ProjectCreateDto;
import com.automationcompany.project.model.dto.ProjectDto;
import com.automationcompany.project.service.ProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectController.class)
@WithMockUser
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProjectService projectService;

    @Test
    void createProject_shouldReturn201() throws Exception {
        ProjectDto response = ProjectDto.builder()
                .id(1L)
                .name("Test Project")
                .status(ProjectStatus.PLANNING)
                .serviceType(ProjectServiceType.MACHINE_DESIGN)
                .startDate(LocalDate.of(2025, 1, 1))
                .build();

        when(projectService.createProject(any())).thenReturn(response);

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
}