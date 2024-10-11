package com.workflow.project_service.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.workflow.project_service.issue.Issue;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private LocalDateTime createdDateTime;

    private Long userId;

    @JsonIgnore
    @ManyToOne
    private Issue issue;

}
