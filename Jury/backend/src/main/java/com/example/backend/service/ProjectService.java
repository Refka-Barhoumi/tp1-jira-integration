package com.example.backend.service;

import com.example.backend.entity.Project;
import com.example.backend.entity.Student;
import com.example.backend.repository.ProjectRepository;
import com.example.backend.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final StudentRepository studentRepository;

    public ProjectService(ProjectRepository projectRepository,
                          StudentRepository studentRepository) {
        this.projectRepository = projectRepository;
        this.studentRepository = studentRepository;
    }

    // Récupérer tous les projets
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    // Récupérer les projets d'un étudiant
    public List<Project> getProjectsByStudent(Long studentId) {
        return projectRepository.findByStudentId(studentId);
    }

    public Project addProject(Project project) {

        // US3 — Vérification du deuxième étudiant si type Binôme
        if ("Binôme".equals(project.getParticipationType())) {
            if (project.getStudent2() == null || project.getStudent2().getId() == null) {
                throw new RuntimeException("Le deuxième étudiant est requis pour un binôme");
            }
            Student student2 = studentRepository.findById(project.getStudent2().getId())
                    .orElseThrow(() -> new RuntimeException("Deuxième étudiant introuvable"));

            // Vérifier que le deuxième étudiant n'est pas dans un autre projet
            List<Project> existingProjects = projectRepository.findByStudentId(student2.getId());
            if (!existingProjects.isEmpty()) {
                throw new RuntimeException("Le deuxième étudiant est déjà dans un autre projet");
            }
            project.setStudent2(student2);
        } else {
            project.setStudent2(null);
        }

        // US6 — Vérification des données entreprise si contexte = Entreprise
        if ("Entreprise".equals(project.getProjectContext())) {
            if (project.getCompanyName() == null || project.getCompanyName().isBlank()) {
                throw new RuntimeException("Le nom de l'entreprise est requis");
            }
        } else {
            project.setCompanyName(null);
            project.setCompanySupervisor(null);
        }

        // Définir le statut initial
        project.setStatus("En cours");
        return projectRepository.save(project);
    }

    public Project updateProject(Long id, Project updatedProject) {
        return projectRepository.findById(id)
                .map(project -> {
                    // Bloquer la modification si le projet est validé
                    if ("Validé".equals(project.getStatus())) {
                        throw new RuntimeException("Modification impossible: projet déjà validé");
                    }
                    project.setTitle(updatedProject.getTitle());
                    project.setDescription(updatedProject.getDescription());
                    project.setObjectives(updatedProject.getObjectives());
                    project.setTechnologies(updatedProject.getTechnologies());
                    project.setParticipationType(updatedProject.getParticipationType());
                    project.setProjectContext(updatedProject.getProjectContext());
                    project.setCompanyName(updatedProject.getCompanyName());
                    project.setCompanySupervisor(updatedProject.getCompanySupervisor());
                    return projectRepository.save(project);
                })
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
    }

    // Supprimer un projet
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}