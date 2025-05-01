import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/users/login';
  private tokenKey = 'authToken'; // Key to store the token in localStorage

  constructor(private http: HttpClient) {}

  login(credentials: { cpf: string; password: string }): Observable<any> {
    return this.http.post<{ token: string }>(this.apiUrl, credentials).pipe(
      tap((response) => {
        localStorage.setItem(this.tokenKey, response.token); // Store token in localStorage
      })
    );
  }

  logout() {
    localStorage.removeItem(this.tokenKey); // Remove token on logout
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey); // Retrieve token
  }

  isAuthenticated(): boolean {
    return !!this.getToken(); // Check if token exists
  }
}
