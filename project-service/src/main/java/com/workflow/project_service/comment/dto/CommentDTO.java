package com.workflow.project_service.comment.dto;

import com.workflow.project_service.user.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data @Builder
public class CommentDTO {
    private Long id;
    private String content;
    private LocalDateTime createdDateTime;
    private User user;
}
