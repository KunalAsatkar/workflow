package com.workflow.project_service.comment;

import com.workflow.project_service.comment.dto.CommentDTO;

import java.util.List;

public interface CommentService {

    CommentDTO createComment(Long issueId, Long userId, String content, String authHeader) throws Exception;

    void deleteComment(Long commentId, Long userId) throws Exception;

    List<CommentDTO> findCommentByIssueId(Long issueId, String authHeader) throws Exception;
}
