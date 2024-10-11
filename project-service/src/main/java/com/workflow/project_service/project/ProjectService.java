package com.workflow.project_service.project;

import com.workflow.project_service.project.dto.ProjectDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProjectService {
    Project createProject(Project project, String authHeader) throws Exception;

    ProjectDTO getProjectById(Long projectId, String authHeader) throws Exception;

    List<Project> getProjectByFilter(Long userId, String category, String tag) throws Exception;

    void deleteProject(Long projectId, Long userId) throws Exception;

    Project updateProject(Long projectId, Project project, Long userId) throws Exception;

    void addUserToProject(Long projectId, Long userId) throws Exception;

    void removeUserFromProject(Long projectId, Long projectLeadId, Long userId) throws Exception;

    List<Project> searchProject(String keyword, Long userId) throws Exception;

    Long getChatIdByProjectId(Long projectId) throws Exception;
}
