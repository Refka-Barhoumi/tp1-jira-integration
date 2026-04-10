# 🎓 Gestion des PFE 

## 📄 Description du projet
Ce projet a pour objectif de gérer l’ensemble des Projets de Fin d’Études (PFE) pour les étudiants, tuteurs et coordinateurs pédagogiques.  

Le système permet de :  
- 👥 Gérer les comptes utilisateurs pour tous les rôles (Étudiants, Tuteurs, Coordinateur, Jury).  
- 📝 Soumettre, valider et suivre les propositions de projets.  
- ✅ Assurer le suivi pédagogique et la validation des comptes rendus.  
- 🎤 Organiser les soutenances et l’évaluation finale.  
- 📚 Archiver et consulter les projets et rapports pour les générations futures.  

Le projet est développé dans le cadre du TP **Jira + GitHub Integration** à la **FST-SBZ (A.U. 2025-2026)**.

---

## 👨‍💻 Acteurs et fonctionnalités principales

| Acteur                  | Fonctionnalités principales |
|--------------------------|----------------------------|
| 👨‍🎓 Étudiant            | Accéder à son espace, soumettre un projet, consulter ses comptes rendus, personnaliser le tableau de bord, gérer projet seul ou en binôme |
| 👩‍🏫 Tuteur              | Accéder à son espace, valider ou commenter les propositions, suivre les comptes rendus, organiser des réunions |
| 🎯 Coordinateur pédagogique | Créer et gérer les comptes, attribuer les tuteurs, suivre l’avancement global des projets |
| 🏛️ Jury                  | Consulter et évaluer les rapports finaux, enregistrer les notes et appréciations, archiver les projets |

---

## 🛠️ Technologies utilisées
- **Frontend :** ⚡ Angular 17+  
- **Backend :** ☕ Spring Boot 3.2  
- **Base de données :** 🐘 PostgreSQL 15 + JSONB  
- **Authentification :** 🔑 JWT (JSON Web Tokens)  
- **DevOps :** 🚀 GitHub Actions (CI/CD)  
- **Diagrammes :** 📊 PlantUML  

---
## 📁 Architecture du projet

### 🗂️ Structure globale

```
PF-TP/
├── backend/       # Spring Boot 3.x
└── frontend/      # Angular 21.x
```

### ☕ Backend — Spring Boot

```
backend/
├── src/main/java/com/example/backend/
│   ├── controller/
│   │   ├── AuthController.java        # Login, Register, Password Reset
│   │   ├── ProjectController.java     # CRUD Projets
│   │   └── StudentController.java     # CRUD Étudiants
│   ├── entity/
│   │   ├── Project.java               # Entité Projet
│   │   ├── ProjectResponse.java       # DTO Projet
│   │   └── Student.java               # Entité Étudiant
│   ├── repository/
│   │   ├── ProjectRepository.java     # Accès données Projet
│   │   └── StudentRepository.java     # Accès données Étudiant
│   ├── security/
│   │   ├── JwtFilter.java             # Filtre JWT
│   │   ├── JwtUtil.java               # Génération/Validation Token
│   │   └── SecurityConfig.java        # Config Spring Security + CORS
│   └── service/
│       ├── PasswordResetService.java  # Réinitialisation mot de passe
│       ├── ProjectService.java        # Logique métier Projet
│       └── StudentService.java        # Logique métier Étudiant
└── src/main/resources/
    └── application.properties         # Config DB + Mail + JWT
```

### ⚡ Frontend — Angular

```
frontend/src/app/
├── components/
│   ├── login/                # Page Connexion (US1)
│   ├── register/             # Page Inscription (US1)
│   ├── forgot-password/      # Mot de passe oublié (US4)
│   ├── reset-password/       # Réinitialisation (US4)
│   ├── dashboard/            # Tableau de bord étudiant (US2)
│   └── project-form/         # Formulaire projet (US5, US6, US7)
├── services/
│   ├── auth.ts               # Service authentification
│   └── project.ts            # Service projets
├── app.routes.ts             # Routing Angular
├── app.config.ts             # Configuration app
└── app.ts                    # Composant racine
```

### 🗄️ Base de données — PostgreSQL

```
pf_tp/
├── student    (id, email, password, fullName, resetToken, resetTokenExpiry)
└── projects   (id, title, description, objectives, technologies,
                participationType, projectContext, companyName,
                companySupervisor, status, student_id, student2_id)
```

### 🔐 Endpoints API

```
POST   /auth/register            # Inscription étudiant
POST   /auth/login               # Connexion + Token JWT
POST   /auth/forgot-password     # Demande réinitialisation
POST   /auth/reset-password      # Nouveau mot de passe

GET    /projects                 # Tous les projets        [JWT]
GET    /projects/student/{id}    # Projets d'un étudiant   [JWT]
POST   /projects                 # Créer un projet         [JWT]
PUT    /projects/{id}            # Modifier un projet      [JWT]
DELETE /projects/{id}            # Supprimer un projet     [JWT]

GET    /students                 # Tous les étudiants      [JWT]
GET    /students/{id}            # Un étudiant             [JWT]
POST   /students                 # Créer un étudiant       [JWT]
PUT    /students/{id}            # Modifier un étudiant    [JWT]
DELETE /students/{id}            # Supprimer un étudiant   [JWT]
```

