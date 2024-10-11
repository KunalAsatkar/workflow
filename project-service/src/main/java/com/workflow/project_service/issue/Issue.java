package com.workflow.project_service.issue;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.workflow.project_service.comment.Comment;
import com.workflow.project_service.project.Project;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String status;
    private String priority;
    private LocalDate dueDate;
    private Long createdBy;
    private Long assigneeId;

    @JsonIgnore
    @ManyToOne
    private Project project; // it will create a column project_id

    @JsonIgnore // whenever we fetch the issue from frontend
    // that time we are telling that don't fetch this field
    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;
}
