-- ============================================
-- US8 – Script PostgreSQL 15
-- ============================================

-- CREATE DATABASE us8_db;   ← exécuter d'abord si besoin
-- \c us8_db                 ← se connecter à la base

CREATE TABLE IF NOT EXISTS remarque (
    id          BIGSERIAL PRIMARY KEY,
    contenu     TEXT        NOT NULL,
    date        TIMESTAMP   NOT NULL DEFAULT NOW(),
    etudiant_id BIGINT      NOT NULL
);

INSERT INTO remarque (contenu, date, etudiant_id) VALUES
  ('Votre introduction manque de clarté, merci de la reformuler.', NOW(), 1),
  ('La problématique est bien posée, mais les objectifs restent vagues.', NOW(), 1),
  ('Bonne structure générale. Ajoutez des références bibliographiques.', NOW(), 2);
