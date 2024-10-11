package com.workflow.project_service.issue;

import com.workflow.project_service.issue.dto.IssueDTO;

import java.util.List;

public interface IssueService {

    IssueDTO createIssue(Issue issue, Long projectId, Long userId) throws Exception;

    IssueDTO getIssueById(Long issueId, String authHeader) throws Exception;

    List<Issue> getIssueByProjectId(Long projectId) throws Exception;

    void deleteIssue(Long issueId, Long userId) throws Exception;

    IssueDTO assignIssue(Long issueId, Long userId,Long assigneeId, String authHeader) throws Exception;

    Issue updateStatus(Long issueId, String status, Long userId) throws Exception;
}
