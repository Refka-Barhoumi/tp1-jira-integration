package com.example.backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.backend.entity.Student;
import com.example.backend.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    // Injection par constructeur
    public StudentService(StudentRepository studentRepository,PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 1. Récupérer tous les étudiants
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // 2. Récupérer un étudiant par ID
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    // 3. Ajouter un nouvel étudiant
    public Student addStudent(Student student) {
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        return studentRepository.save(student);
    }

    // 4. Mettre à jour les informations d'un étudiant
    public Student updateStudent(Long id, Student updatedStudent) {
        return studentRepository.findById(id)
                .map(student -> {
                    student.setFullName(updatedStudent.getFullName());
                    student.setEmail(updatedStudent.getEmail());
                    student.setPassword(passwordEncoder.encode(updatedStudent.getPassword()));
                    return studentRepository.save(student);
                })
                .orElseThrow(() -> new RuntimeException("Student not found with id " + id));
    }

    // 5. Supprimer un étudiant
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}