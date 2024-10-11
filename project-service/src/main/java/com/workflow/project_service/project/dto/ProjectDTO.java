package com.workflow.project_service.project.dto;

import com.workflow.project_service.user.User;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data @Builder
public class ProjectDTO {
    private Long id;
    private String name;
    private String description;
    private String category; // fullstack, backend, frontend
    private User projectLead;

    private List<String> tags = new ArrayList<>();

    private List<User> users = new ArrayList<>();
}
