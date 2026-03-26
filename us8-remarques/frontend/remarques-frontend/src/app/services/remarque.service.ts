import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Remarque } from '../models/remarque.model';

@Injectable({
  providedIn: 'root'
})
export class RemarqueService {

  private apiUrl = 'http://localhost:8080/remarques';

  constructor(private http: HttpClient) {}

  getRemarques(etudiantId: number): Observable<Remarque[]> {
    return this.http.get<Remarque[]>(`${this.apiUrl}/${etudiantId}`);
  }

  ajouterRemarque(remarque: Remarque): Observable<Remarque> {
    return this.http.post<Remarque>(this.apiUrl, remarque);
  }
}
