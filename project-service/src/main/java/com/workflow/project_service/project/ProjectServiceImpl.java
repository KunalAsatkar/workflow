package com.workflow.project_service.project;

import com.workflow.project_service.chat.Chat;
import com.workflow.project_service.chat.ChatClient;
import com.workflow.project_service.project.dto.ProjectDTO;
import com.workflow.project_service.user.User;
import com.workflow.project_service.user.UserClient;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final UserClient userClient;
    private final ChatClient chatClient;

    public ProjectServiceImpl(ProjectRepository projectRepository, UserClient userClient, ChatClient chatClient) {
        this.projectRepository = projectRepository;
        this.userClient = userClient;
        this.chatClient = chatClient;
    }

    @Override
    public Project createProject(@Valid Project project, String authHeader) throws Exception {
        try {
            //check if user with projectLeadId exist
            User user = userClient.getUserById(project.getProjectLeadId(), authHeader);
            if(user == null) {
                throw new Exception("User not found");
            }
            Project savedProject = projectRepository.save(project);

            // call chat service and create chat for the project
            Chat chat = chatClient.createChat(savedProject.getId());
            savedProject.setChatId(chat.getId());

            savedProject = projectRepository.save(savedProject);

            return savedProject;

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public ProjectDTO getProjectById(Long projectId, String authHeader) throws Exception {
        try {
            Project project = projectRepository.findById(projectId).orElse(null);
            if(project == null) {
                throw new Exception("Project not found");
            }
            User projectLead = userClient.getUserById(project.getProjectLeadId(), authHeader);
            List<User> users = new ArrayList<>();
            project.getUserIds().forEach(id -> users.add(userClient.getUserById(id, authHeader)));
            return ProjectDTO.builder()
                    .id(project.getId())
                    .name(project.getName())
                    .description(project.getDescription())
                    .category(project.getCategory())
                    .tags(project.getTags())
                    .projectLead(projectLead)
                    .users(users)
                    .build();

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Project> getProjectByFilter(Long userId, String category, String tag) throws Exception {
        try {

            List<Project> projects = projectRepository.findByUserId(userId);
            if (category != null) {
                projects = projects.stream()
                        .filter(project -> project.getCategory().equals(category))
                        .toList();

            }
            if (tag != null) {
                projects = projects.stream()
                        .filter(project -> project.getTags().contains(tag))
                        .toList();

            }
            return projects;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Project updateProject(Long projectId, Project project, Long projectLeadId) throws Exception {
        try {

            Project existingProject = checkProjectIdAndProjectLeadId(projectId, projectLeadId);

            existingProject.setName(project.getName().trim());
            existingProject.setDescription(project.getDescription().trim());
            existingProject.setCategory(project.getCategory().trim());
            existingProject.setTags(project.getTags());

            return projectRepository.save(project);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void deleteProject(Long projectId, Long userId) throws Exception {
        try {
            Project existingProject = checkProjectIdAndProjectLeadId(projectId, userId);
            projectRepository.deleteById(projectId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    @Override
    public void addUserToProject(Long projectId, Long userId) throws Exception {
        try {
            Project project = projectRepository.findById(projectId).orElse(null);
            if(project == null) {
                throw new Exception("Project not found");
            }
            List<Long> userIds = project.getUserIds();
            if (userIds.contains(userId)) {
                throw new Exception("user already in the project");
            }

            userIds.add(userId);
            project.setUserIds(userIds);
            projectRepository.save(project);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void removeUserFromProject(Long projectId, Long projectLeadId, Long userId) throws Exception {
        try {

            Project project = checkProjectIdAndProjectLeadId(projectId, projectLeadId);

            // project.getUserIds() will return the list of users;
            if (!project.getUserIds().contains(userId)) {
                throw new Exception("user already in the project");
            }
            project.getUserIds().remove(userId);
            projectRepository.save(project);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Project> searchProject(String keyword, Long userId) throws Exception {
        try {
            return projectRepository.findByNameContainingAndUserIdsContains(keyword, userId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

     @Override
     public Long getChatIdByProjectId(Long projectId) throws Exception{
        Project project = projectRepository.findById(projectId).orElse(null);
        if(project == null) {
            throw new Exception("project not found");
        }
        return project.getChatId();
     }

    Project checkProjectIdAndProjectLeadId(Long projectId, Long projectLeadId) throws Exception {
        Project project = projectRepository.findById(projectId).orElse(null);
        if (project == null) {
            throw new Exception("project not found");
        }
        if(!project.getProjectLeadId().equals(projectLeadId)){
            throw new Exception("project lead id not match");
        }
        return project;
    }
}
