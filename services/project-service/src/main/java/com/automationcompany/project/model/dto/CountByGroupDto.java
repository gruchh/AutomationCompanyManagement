package com.automationcompany.project.model.dto;

import com.automationcompany.project.model.ProjectServiceType;
import com.automationcompany.project.model.ProjectStatus;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Represents a count of projects grouped by a category (e.g., status, location).")
public record CountByGroupDto(
        @Schema(description = "The name of the group (e.g., 'ACTIVE', 'Wdrożenie', 'Warszawa').")
        String groupName,

        @Schema(description = "The number of projects belonging to this group.")
        Long count
) {
    public CountByGroupDto(ProjectStatus status, Long count) {
        this(status.name(), count);
    }

    public CountByGroupDto(ProjectServiceType serviceType, Long count) {
        this(serviceType.name(), count);
    }
}
