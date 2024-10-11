package com.workflow.user_service.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email
    @Column(unique = true, nullable = false)
    private String username;
    private String firstName;
    private String lastName;
    private int projectCnt;
    @Size(min = 6) // do not set max = 20 'cause the length of hashed password is more that this
    private String password;

//    @ElementCollection
//    private List<Long> createdProjects = new ArrayList<>();
//
//    @OneToMany(mappedBy = "assignee", cascade = CascadeType.ALL)
//    private List<Issue> assignedIssues = new ArrayList<>();

}
