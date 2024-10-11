package com.workflow.project_service.project;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.workflow.project_service.issue.Issue;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String category; // fullstack, backend, frontend
    private Long projectLeadId;

    @ElementCollection
    private List<String> tags = new ArrayList<>();

    private Long chatId;

    @JsonIgnore
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Issue> issues = new ArrayList<>();

    @ElementCollection
    private List<Long> userIds = new ArrayList<>();
}
