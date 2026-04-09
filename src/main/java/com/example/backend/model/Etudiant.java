package com.example.backend.model;
import jakarta.persistence.*;
@Entity
@Table(name = "etudiants")public class Etudiant{
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private Long id;
@Column(nullable=false)private String prenom;
@Column(nullable=false)private String nom;
@Column(nullable=false,unique=true)private String email;
@Column(name="mot_de_passe",nullable=false)private String motDePasse;
public Etudiant(){}public Long getId(){return id;}public void setId(Long id){this.id=id;}public String getPrenom(){return prenom;}public void setPrenom(String p){this.prenom=p;}public String getNom(){return nom;}public void setNom(String n){this.nom=n;}public String getEmail(){return email;}public void setEmail(String e){this.email=e;}public String getMotDePasse(){return motDePasse;}public void setMotDePasse(String m){this.motDePasse=m;}}