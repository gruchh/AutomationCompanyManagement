package com.automationcompany.project.repository.specification;

import com.automationcompany.employee.model.Employee;
import com.automationcompany.project.model.Project;
import com.automationcompany.project.model.ProjectTechnology;
import com.automationcompany.project.model.dto.ProjectFilterDto;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProjectSpecification {

    public static Specification<Project> build(ProjectFilterDto f) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (f.getStatuses() != null && !f.getStatuses().isEmpty()) {
                predicates.add(root.get("status").in(f.getStatuses()));
            }

            if (f.getServiceTypes() != null && !f.getServiceTypes().isEmpty()) {
                predicates.add(root.get("serviceType").in(f.getServiceTypes()));
            }

            if (f.getPriorities() != null && !f.getPriorities().isEmpty()) {
                predicates.add(root.get("priority").in(f.getPriorities()));
            }

            if (f.getTechnologies() != null && !f.getTechnologies().isEmpty()) {
                Join<Project, ProjectTechnology> techJoin = root.join("technologies");
                predicates.add(techJoin.in(f.getTechnologies()));
                query.distinct(true);
            }

            if (f.getManagerId() != null) {
                predicates.add(cb.equal(root.get("manager").get("id"), f.getManagerId()));
            }

            if (f.getEmployeeId() != null) {
                Join<Project, Employee> empJoin = root.join("employees");
                predicates.add(cb.equal(empJoin.get("id"), f.getEmployeeId()));
                query.distinct(true);
            }

            if (f.getLocationId() != null) {
                predicates.add(cb.equal(root.get("location").get("id"), f.getLocationId()));
            }

            if (f.getLocationName() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("location").get("name")),
                        "%" + f.getLocationName().toLowerCase() + "%"
                ));
            }

            if (f.getCountry() != null) {
                predicates.add(cb.equal(root.get("location").get("country"), f.getCountry()));
            }

            if (f.getCity() != null) {
                predicates.add(cb.equal(root.get("location").get("city"), f.getCity()));
            }

            // DATES
            if (f.getStartDateFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("startDate"), f.getStartDateFrom()));
            }

            if (f.getStartDateTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("startDate"), f.getStartDateTo()));
            }

            if (f.getEndDateFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("endDate"), f.getEndDateFrom()));
            }

            if (f.getEndDateTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("endDate"), f.getEndDateTo()));
            }

            if (f.getSearchQuery() != null && !f.getSearchQuery().isBlank()) {
                String like = "%" + f.getSearchQuery().toLowerCase() + "%";

                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("name")), like),
                        cb.like(cb.lower(root.get("description")), like)
                ));
            }

            if (f.getMinTeamSize() != null) {
                predicates.add(cb.ge(cb.size(root.get("employees")), f.getMinTeamSize()));
            }

            if (f.getMaxTeamSize() != null) {
                predicates.add(cb.le(cb.size(root.get("employees")), f.getMaxTeamSize()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}