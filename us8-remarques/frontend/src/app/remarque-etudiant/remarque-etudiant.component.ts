import { Component, OnInit } from '@angular/core';
import { RemarqueService } from '../services/remarque.service';
import { Remarque } from '../models/remarque.model';

@Component({
  selector: 'app-remarque-etudiant',
  templateUrl: './remarque-etudiant.component.html',
  styleUrls: ['./remarque-etudiant.component.css']
})
export class RemarqueEtudiantComponent implements OnInit {

  etudiantId: number = 1;
  remarques: Remarque[] = [];
  nouvelleRemarque: Remarque = { contenu: '', etudiantId: 1 };
  message: string = '';
  erreur: string = '';
  chargement: boolean = false;

  constructor(private remarqueService: RemarqueService) {}

  ngOnInit(): void {
    this.chargerRemarques();
  }

  chargerRemarques(): void {
    this.chargement = true;
    this.erreur = '';
    this.remarqueService.getRemarques(this.etudiantId).subscribe({
      next: (data) => {
        this.remarques = data;
        this.chargement = false;
      },
      error: (err) => {
        this.erreur = 'Impossible de charger les remarques. Vérifiez que le backend tourne sur le port 8080.';
        this.chargement = false;
        console.error(err);
      }
    });
  }

  soumettre(): void {
    if (!this.nouvelleRemarque.contenu.trim()) {
      this.erreur = 'Le contenu de la remarque est obligatoire.';
      return;
    }
    this.erreur = '';
    this.nouvelleRemarque.etudiantId = this.etudiantId;

    this.remarqueService.ajouterRemarque(this.nouvelleRemarque).subscribe({
      next: (remarque) => {
        this.remarques.unshift(remarque);
        this.message = 'Remarque ajoutée avec succès !';
        this.nouvelleRemarque = { contenu: '', etudiantId: this.etudiantId };
        setTimeout(() => this.message = '', 3000);
      },
      error: (err) => {
        this.erreur = 'Erreur lors de l\'envoi de la remarque.';
        console.error(err);
      }
    });
  }

  formaterDate(dateStr?: string): string {
    if (!dateStr) return '';
    const d = new Date(dateStr);
    return d.toLocaleDateString('fr-FR', {
      day: '2-digit', month: 'long', year: 'numeric',
      hour: '2-digit', minute: '2-digit'
    });
  }
}
