package com.workflow.project_service.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByProjectLeadId(Long leadId);

    List<Project> findByNameContainingAndUserIdsContains(String name, Long userId);

    @Query("SELECT p FROM Project p WHERE :userId MEMBER OF p.userIds")
    List<Project> findByUserId(@Param("userId") Long userId);
}
