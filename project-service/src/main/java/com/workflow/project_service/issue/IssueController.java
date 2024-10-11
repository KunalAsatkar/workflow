package com.workflow.project_service.issue;

import com.workflow.project_service.issue.dto.IssueDTO;
import com.workflow.project_service.issue.dto.RequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("project/{projectId}/issue")
public class IssueController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping("hello")
    public ResponseEntity<String> hello(@PathVariable("projectId") String projectId) {
        return ResponseEntity.ok("Hello " + projectId);
    }

    @PostMapping("create")
    public ResponseEntity<IssueDTO> createIssue(@RequestBody Issue issue, @PathVariable Long projectId, @RequestParam Long userId) {
        try {
            IssueDTO issueDTO = issueService.createIssue(issue, projectId, userId);

            return ResponseEntity.status(HttpStatus.CREATED).body(issueDTO);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("{issueId}")
    public ResponseEntity<IssueDTO> getIssue(@PathVariable("issueId") Long issueId, @RequestHeader("Authorization") String authHeader) {
        try {
            IssueDTO issueDTO = issueService.getIssueById(issueId, authHeader);
            return ResponseEntity.ok(issueDTO);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping()
    public ResponseEntity<List<Issue>> getIssueByProject(@PathVariable("projectId") Long projectId) {
        try {
            List<Issue> issues = issueService.getIssueByProjectId(projectId);
            return ResponseEntity.ok(issues);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("{issueId}/delete")
    public ResponseEntity<Void> deleteIssue(@PathVariable("issueId") Long issueId, @RequestParam Long userId) {
        try {
            issueService.deleteIssue(issueId, userId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("{issueId}/update/status")
    public ResponseEntity<Issue> updateIssueStatus(@PathVariable("issueId") Long issueId, @RequestBody RequestDTO requestDTO,
            @RequestParam Long userId) {
        try {
            Issue issue = issueService.updateStatus(issueId, requestDTO.getStatus(), userId);
            return ResponseEntity.ok(issue);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("{issueId}/assign")
    public ResponseEntity<IssueDTO> assignIssue(@PathVariable("issueId") Long issueId, @RequestParam Long userId,
                @RequestHeader("Authorization") String authHeader,@RequestBody Long assigneeId) {
        try {
            IssueDTO issueDTO = issueService.assignIssue(issueId, userId, assigneeId, authHeader);
            return ResponseEntity.ok(issueDTO);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
