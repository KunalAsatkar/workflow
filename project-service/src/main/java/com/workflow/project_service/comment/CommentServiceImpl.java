package com.workflow.project_service.comment;

import com.workflow.project_service.comment.dto.CommentDTO;
import com.workflow.project_service.issue.Issue;
import com.workflow.project_service.issue.IssueRepository;
import com.workflow.project_service.user.User;
import com.workflow.project_service.user.UserClient;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final IssueRepository issueRepository;
    private final UserClient userClient;

    public CommentServiceImpl(CommentRepository commentRepository, IssueRepository issueRepository, UserClient userClient) {
        this.commentRepository = commentRepository;
        this.issueRepository = issueRepository;
        this.userClient = userClient;
    }

    @Override
    public CommentDTO createComment(Long issueId, Long userId, String content, String authHeader) throws Exception {
        Issue issue = issueRepository.findById(issueId).orElse(null);
        if (issue == null) {
            throw new Exception("Issue with id " + issueId + " not found");
        }
        // talk to user check if user exist
        Comment comment = Comment.builder()
                .issue(issue)
                .content(content)
                .userId(userId)
                .createdDateTime(LocalDateTime.now())
                .build();

        Comment savedComment = commentRepository.save(comment);

        issue.getComments().add(savedComment);// check why you need this

        User user = userClient.getUserById(userId, authHeader);

        return commentDTOBuilder(savedComment, user);
    }

    @Override
    public void deleteComment(Long commentId, Long userId) throws Exception {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment == null) {
            throw new Exception("comment not found with id " + commentId);
        }
        if (!comment.getUserId().equals(userId)) {
            throw new Exception("user is not owner of comment");
        }
        commentRepository.delete(comment);
    }

    @Override
    public List<CommentDTO> findCommentByIssueId(Long issueId, String authHeader) throws Exception {

        List<Comment> comments = commentRepository.findCommentByIssueId(issueId);

        List<CommentDTO> commentDTOS = new ArrayList<>();

        comments.forEach(comment -> {
            User user = userClient.getUserById(comment.getUserId(), authHeader);
            commentDTOS.add(commentDTOBuilder(comment, user));
        });

        return commentDTOS;
    }

    public CommentDTO commentDTOBuilder(Comment comment, User user) {
        return CommentDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .createdDateTime(comment.getCreatedDateTime())
                .user(user)
                .build();
    }
}
