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
  filtre: 'toutes' | 'lues' | 'non-lues' = 'toutes';

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
      error: () => {
        this.erreur = 'Impossible de charger les remarques.';
        this.chargement = false;
      }
    });
  }

  soumettre(): void {
    if (!this.nouvelleRemarque.contenu.trim()) {
      this.erreur = 'Le contenu est obligatoire.';
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
        console.error('Erreur POST:', err);
        this.erreur = 'Erreur lors de l\'envoi. Vérifiez le backend.';
      }
    });
  }

  get remarquesFiltrees(): Remarque[] {
    if (this.filtre === 'lues') return this.remarques.filter(r => r.lu);
    if (this.filtre === 'non-lues') return this.remarques.filter(r => !r.lu);
    return this.remarques;
  }

  get nonLuesCount(): number {
    return this.remarques.filter(r => !r.lu).length;
  }

  get luesCount(): number {
    return this.remarques.filter(r => r.lu).length;
  }

  marquerLue(remarque: Remarque): void {
    remarque.lu = true;
  }

  formaterDate(dateStr?: string): string {
    if (!dateStr) return '';
    return new Date(dateStr).toLocaleDateString('fr-FR', {
      day: '2-digit', month: 'long', year: 'numeric',
      hour: '2-digit', minute: '2-digit'
    });
  }
}