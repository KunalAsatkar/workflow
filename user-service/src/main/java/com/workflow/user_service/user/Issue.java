package com.workflow.user_service.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "issues")
@Data
public class Issue {
    @Id
    private Long id;
    @ManyToOne
    private User assignee;

}
