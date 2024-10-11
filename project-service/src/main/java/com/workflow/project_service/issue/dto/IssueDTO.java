package com.workflow.project_service.issue.dto;

import com.workflow.project_service.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor

public class IssueDTO {
    private Long id;

    private String title;

    private String description;

    private String status;

    private String priority;

    private LocalDate dueDate;

    private User assignee;

    private User createdBy;

}
