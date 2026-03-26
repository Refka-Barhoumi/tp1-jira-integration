export interface Remarque {
  id?: number;
  contenu: string;
  date?: string;
  etudiantId: number;
  etudiantNom?: string;
  etudiantPrenom?: string;
  lu?: boolean;
  tuteurNom?: string;
}