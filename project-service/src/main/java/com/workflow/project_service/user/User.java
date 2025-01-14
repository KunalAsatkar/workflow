package com.workflow.project_service.user;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private int projectCnt;
}
