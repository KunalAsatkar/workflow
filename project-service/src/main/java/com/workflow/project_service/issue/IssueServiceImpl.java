package com.workflow.project_service.issue;

import com.workflow.project_service.issue.dto.IssueDTO;
import com.workflow.project_service.project.Project;
import com.workflow.project_service.project.ProjectRepository;
import com.workflow.project_service.user.User;
import com.workflow.project_service.user.UserClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueServiceImpl implements IssueService {

    private final IssueRepository issueRepository;
    private final ProjectRepository projectRepository;
    private final UserClient userClient;

    public IssueServiceImpl(IssueRepository issueRepository, ProjectRepository projectRepository, UserClient userClient) {
        this.issueRepository = issueRepository;
        this.projectRepository = projectRepository;
        this.userClient = userClient;
    }

    @Override
    public IssueDTO createIssue(Issue issue, Long projectId, Long userId) throws Exception {
        try{

            Project project = projectRepository.findById(projectId).orElse(null);
            if (project == null) {
                throw new Exception("Project not found");
            }
            issue.setCreatedBy(userId);
            issue.setProject(project);
            issue.setCreatedBy(userId);
            Issue savedIssue = issueRepository.save(issue);
            return IssueDTO.builder()
                    .id(savedIssue.getId())
                    .title(savedIssue.getTitle())
                    .description(savedIssue.getDescription())
                    .status(savedIssue.getStatus())
                    .priority(savedIssue.getPriority())
                    .dueDate(savedIssue.getDueDate())
                    .build();
        }
        catch(Exception e){
            throw new Exception("Issue creation failed");
        }
    }

    @Override
    public IssueDTO getIssueById(Long issueId, String authHeader) throws Exception {
        try{
            System.out.println("getIssueById");
            Issue issue = issueRepository.findById(issueId).orElse(null);
            if (issue == null) {
                throw new Exception("Issue not found");
            }

//            System.out.println(issue.toString());
//            do not print issue.toString as it cause circular calling
//            result stack overflow error

            User createdBy = userClient.getUserById(issue.getCreatedBy(), authHeader);
            User assignee = null;
            if(issue.getAssigneeId() != null){
                // as assignee can be null if the issue is yet to be assigned
                assignee = userClient.getUserById(issue.getAssigneeId(), authHeader);
            }

            return issueDTOBuilder(issue, createdBy, assignee);
        }
        catch(Exception e){
            throw new Exception("Issue not found");
        }

    }

    @Override
    public List<Issue> getIssueByProjectId(Long projectId) throws Exception {
        try{
            return issueRepository.findByProjectId(projectId);
        }
        catch(Exception e){
            throw new Exception("Issue not found");
        }
    }


    @Override
    public void deleteIssue(Long issueId, Long userId) throws Exception {

        Issue issue = issueRepository.findById(issueId).orElse(null);
        if (issue == null || !userId.equals(issue.getCreatedBy())) {
            throw new Exception("Issue not found");
        }

        issueRepository.deleteById(issueId);

    }

    @Override
    public IssueDTO assignIssue(Long issueId, Long userId, Long assigneeId, String authHeader) throws Exception {

        Issue issue = issueRepository.findById(issueId).orElse(null);
        if (issue == null) {
            throw new Exception("Issue not found");
        }

        User createdBy = userClient.getUserById(issue.getCreatedBy(), authHeader);
        User assignee = userClient.getUserById(assigneeId, authHeader);

        if(assignee == null || createdBy == null) {
            throw new Exception("User not found");
        }

        issue.setAssigneeId(assigneeId);
        Issue savedIssue = issueRepository.save(issue);
        return issueDTOBuilder(savedIssue, createdBy, assignee);
    }

    @Override
    public Issue updateStatus(Long issueId, String status, Long userId) throws Exception {
        Issue issue = issueRepository.findById(issueId).orElse(null);
        if (issue == null) {
            throw new Exception("Issue not found");
        }
        issue.setStatus(status);
        return issueRepository.save(issue);
    }

    public IssueDTO issueDTOBuilder(Issue issue, User createdBy, User assignee) throws Exception {
        return IssueDTO.builder()
                .id(issue.getId())
                .title(issue.getTitle())
                .description(issue.getDescription())
                .status(issue.getStatus())
                .priority(issue.getPriority())
                .dueDate(issue.getDueDate())
                .createdBy(createdBy)
                .assignee(assignee)
                .build();
    }
}
