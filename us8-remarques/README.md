# US8 – Système de Remarques Tuteur → Étudiant

## Structure du projet
```
us8-remarques/
├── database/
│   └── init.sql
├── backend/                          (Spring Boot 3.2 / Java 17)
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/us8/remarques/
│       │   ├── RemarquesBackendApplication.java
│       │   ├── model/Remarque.java
│       │   ├── repository/RemarqueRepository.java
│       │   ├── service/RemarqueService.java
│       │   └── controller/RemarqueController.java
│       └── resources/application.properties
└── frontend/                         (Angular 17)
    └── src/app/
        ├── app.module.ts
        ├── app.component.ts / .html
        ├── models/remarque.model.ts
        ├── services/remarque.service.ts
        └── remarque-etudiant/
            ├── remarque-etudiant.component.ts
            ├── remarque-etudiant.component.html
            └── remarque-etudiant.component.css
```

---

## ÉTAPE 1 — PostgreSQL

```bash
psql -U postgres
CREATE DATABASE us8_db;
\c us8_db
\i database/init.sql
```

---

## ÉTAPE 2 — Lancer le Backend

```bash
cd backend
mvn clean install
mvn spring-boot:run
# Serveur disponible sur http://localhost:8080
```

---

## ÉTAPE 3 — Créer et Lancer Angular

```bash
# Créer le projet Angular (une seule fois)
ng new frontend --style=css --routing=false
cd frontend

# Remplacer src/app/ par les fichiers fournis, puis :
npm install
ng serve
# Application disponible sur http://localhost:4200
```

---

## Tests Postman

### GET – Liste des remarques
```
GET http://localhost:8080/remarques/1
```

### POST – Ajouter une remarque
```
POST http://localhost:8080/remarques
Content-Type: application/json

{
  "contenu": "Votre plan est trop succinct.",
  "etudiantId": 1
}
```
