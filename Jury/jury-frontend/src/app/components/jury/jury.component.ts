import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-jury',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './jury.component.html',
  styleUrls: ['./jury.component.css']
})
export class JuryComponent {
  email: string = '';
  soutenances: any[] = [];

  // 1. AJOUTE CES VARIABLES POUR LA US3
  afficherFormulaireReset: boolean = false;
  nouveauMdp: string = '';

  constructor(private http: HttpClient) {}

  chargerSoutenances() {
    if (this.email) {
      this.http.get<any[]>(`http://localhost:8080/api/jury/${this.email}`)
        .subscribe({
          next: (data) => {
            this.soutenances = data;
          },
          error: (err) => console.error('Erreur chargement', err)
        });
    }
  }

  enregistrerModif(s: any) {
    console.log("Données envoyées :", s);
    // Note : On utilise l'URL correcte correspondant à ton backend
    this.http.put(`http://localhost:8080/api/jury/soutenance/${s.id}`, s)
      .subscribe({
        next: (response) => {
          alert('Modification enregistrée avec succès !');
        },
        error: (err) => {
          console.error('Erreur sauvegarde', err);
          alert('Erreur lors de la sauvegarde sur le serveur');
        }
      });
  }

  // 2. AJOUTE CETTE FONCTION POUR LA US3
  reinitialiserMdp() {
    const payload = { email: this.email, newPassword: this.nouveauMdp };
    this.http.put('http://localhost:8080/api/jury/reset-password', payload)
      .subscribe({
        next: (res) => {
          alert("Mot de passe modifié avec succès !");
          this.afficherFormulaireReset = false; // Ferme le formulaire
          this.nouveauMdp = ''; // Vide le champ
        },
        error: (err) => {
          console.error('Erreur reset password', err);
          alert("Erreur lors de la modification");
        }
      });
  }

} // <--- Laisse bien cette accolade à la fin