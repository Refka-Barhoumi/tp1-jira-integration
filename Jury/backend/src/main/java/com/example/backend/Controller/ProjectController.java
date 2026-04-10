package com.example.backend.controller;

import com.example.backend.entity.Project;
import com.example.backend.entity.ProjectResponse;
import com.example.backend.service.ProjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    // Récupérer tous les projets
    @GetMapping
    public List<ProjectResponse> getAllProjects() {
        return projectService.getAllProjects()
                .stream()
                .map(ProjectResponse::from)
                .collect(Collectors.toList());
    }

    // Récupérer les projets d'un étudiant
    @GetMapping("/student/{studentId}")
    public List<ProjectResponse> getProjectsByStudent(@PathVariable Long studentId) {
        return projectService.getProjectsByStudent(studentId)
                .stream()
                .map(ProjectResponse::from)
                .collect(Collectors.toList());
    }

    // Ajouter un projet
    @PostMapping
    public ProjectResponse addProject(@RequestBody Project project) {
        return ProjectResponse.from(projectService.addProject(project));
    }

    // Modifier un projet
    @PutMapping("/{id}")
    public ProjectResponse updateProject(@PathVariable Long id, @RequestBody Project project) {
        return ProjectResponse.from(projectService.updateProject(id, project));
    }

    // Supprimer un projet
    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }
}