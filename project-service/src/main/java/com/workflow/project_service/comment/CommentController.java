package com.workflow.project_service.comment;

import com.workflow.project_service.comment.dto.CommentDTO;
import com.workflow.project_service.comment.dto.CommentRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("project/{projectId}/issue/{issueId}/comment")
public class CommentController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final CommentServiceImpl commentService;

    public CommentController(CommentServiceImpl commentService) {
        this.commentService = commentService;
    }

    @GetMapping("hello")
    public String hello() {
        return "Hello World";
    }

    @PostMapping("create")
    public ResponseEntity<CommentDTO> createComment(@PathVariable Long issueId, @RequestBody CommentRequest commentRequest,
                                                    @RequestParam Long userId, @RequestHeader("Authorization") String authHeader) {
        try {
            CommentDTO savedComment = commentService.createComment(issueId, userId,
                    commentRequest.getContent(), authHeader);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping()
    public ResponseEntity<List<CommentDTO>> getCommentsByIssueId(@PathVariable("issueId") Long issueId, @RequestHeader("Authorization") String authHeader) {
        try {
            List<CommentDTO> commentDTOS = commentService.findCommentByIssueId(issueId, authHeader);
            return ResponseEntity.status(HttpStatus.OK).body(commentDTOS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId, @RequestParam Long userId) {
        try {
            commentService.deleteComment(commentId, userId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
