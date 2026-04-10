import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class JuryService {
  private apiUrl = 'http://localhost:8080/api/jury';

  constructor(private http: HttpClient) { }

  // This calls your Java API!
  getSoutenances(email: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/${email}`);
  }
}