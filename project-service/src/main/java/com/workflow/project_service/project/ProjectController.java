package com.workflow.project_service.project;

import com.workflow.project_service.project.dto.ProjectDTO;
import com.workflow.project_service.project.dto.RemoveUserReq;
import com.workflow.project_service.project.dto.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("project")
public class ProjectController {
    private final Logger log = LoggerFactory.getLogger(ProjectController.class);
    private final ProjectServiceImpl projectService;


    public ProjectController(ProjectServiceImpl projectService) {
        this.projectService = projectService;
    }

    @GetMapping("hello")
    public String hello() {
        return "Hello World";
    }

    @PostMapping("create")
    public ResponseEntity<Project> saveProject(@RequestBody Project project, @RequestHeader("Authorization") String authHeader) {
        log.info(project.toString());
        try {
            Project savedProject = projectService.createProject(project, authHeader);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProject);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("{projectId}")
    public ResponseEntity<ProjectDTO> getProject(@PathVariable Long projectId, @RequestHeader("Authorization") String authHeader) {
        try {
            ProjectDTO projectDTO = projectService.getProjectById(projectId, authHeader);
            return ResponseEntity.status(HttpStatus.OK).body(projectDTO);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new InternalError(e.getMessage());
        }
    }

    @GetMapping("filter")
    public ResponseEntity<List<Project>> getProjectsByFilter(@RequestParam Long userId,
            @RequestParam(required = false) String category, @RequestParam(required = false) String tag) {
        try {
            List<Project> projects = projectService.getProjectByFilter(userId, category, tag);
            return ResponseEntity.status(HttpStatus.OK).body(projects);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new InternalError(e.getMessage());
        }
    }


    @PatchMapping("{projectId}/update")
    public ResponseEntity<Project> updateProject(@PathVariable Long projectId, @RequestBody Project project,
            @RequestParam Long userId) {

        try {
            Project updatedProject = projectService.updateProject(projectId, project, userId);
            return ResponseEntity.status(HttpStatus.OK).body(updatedProject);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new InternalError(e.getMessage());
        }
    }

    @DeleteMapping("{projectId}/delete")
    public ResponseEntity<ResponseMessage> deleteProject(@PathVariable Long projectId, @RequestParam Long userId) {
        try {
            projectService.deleteProject(projectId, userId);
            return ResponseEntity.ok(new ResponseMessage("Project deleted successfully"));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage(e.getMessage()));
        }
    }

    @GetMapping("search")
    public ResponseEntity<List<Project>> searchProjects(@RequestParam Long userId, @RequestParam String keyword) {
        try {
            List<Project> projects = projectService.searchProject(keyword, userId);
            return ResponseEntity.status(HttpStatus.OK).body(projects);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /***adding user through invite link
     *check with invite link
     ***/
    @PostMapping("{projectId}/add/user")
    public ResponseEntity<ResponseMessage> addUser(@PathVariable Long projectId, @RequestParam Long userId){
        try{
            projectService.addUserToProject(projectId, userId);
            ResponseMessage responseMessage = new ResponseMessage("user added successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
        }
        catch(Exception e){
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage(e.getMessage()));
        }
    }

    @PostMapping("{projectId}/remove/user")
    public ResponseEntity<ResponseMessage> removeUser(@PathVariable Long projectId, @RequestParam Long projectLeadId, @RequestBody RemoveUserReq removeUserReq){
        try{
            projectService.removeUserFromProject(projectId, projectLeadId, removeUserReq.getUserId());
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("user removed successfully"));
        }
        catch(Exception e){
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage(e.getMessage()));
        }
    }





     @GetMapping("{projectId}/chat")
     public ResponseEntity<?> getChatId(@PathVariable Long projectId) {
        try{
            Long chatId = projectService.getChatIdByProjectId(projectId);
            return ResponseEntity.ok(chatId);
        }
        catch(Exception e){
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
     }

}
