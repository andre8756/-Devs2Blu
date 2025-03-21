import { Injectable } from '@angular/core';
import { jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor() { }
  isAuthenticated(): boolean{
    return  !!localStorage.getItem('auth_token')
  }

  isAdmin(): boolean{
    return this.getUserRole() === 'admin';
  }

  getUserId(): string {
    const token = localStorage.getItem('auth_token');
    if (token) {
      const decoded: any = jwtDecode(token);
      return decoded.id; // O JWT deve conter um campo 'role'
    }
    return 'error';
  }

  getUserRole(): string | null {
    const token = localStorage.getItem('auth_token');
    if (token) {
      const decoded: any = jwtDecode(token);
      return decoded.role; // O JWT deve conter um campo 'role'
    }
    return null;
  }

  getUserEmail(): string {
    const token = localStorage.getItem('auth_token');
    if (token) {
      const decoded: any = jwtDecode(token);
      return decoded.username; // O JWT deve conter um campo 'role'
    }
    return 'error';
  }

  logout(): void {
    localStorage.removeItem('auth_token'); // Limpa o token do localStorage
    window.location.href = 'http://localhost:4200/login';// Redireciona para a tela de login
  }

}
