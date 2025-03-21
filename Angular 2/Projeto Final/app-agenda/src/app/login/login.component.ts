import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../services/login.service';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, NgModel } from '@angular/forms';

@Component({
  selector: 'app-login',
  imports: [ RouterLink, CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  contatos: any[] = [];
  contato = { email: '', password: '' };
  erro = '';

  constructor(private service: LoginService, private router: Router) {}

  ngOnInit(): void {
    this.listarContatos();
  }

  listarContatos() {
    this.service.getContatos().subscribe(data => {
      this.contatos = data;
    });
  }

  salvar() {
    if (this.contato.email === '') {
      this.erro = 'O email deve ser informado';
      return;
    }

    if (this.contato.password === '') {
      this.erro = 'A senha deve ser preenchida';
      return;
    }

    let ct = {email: this.contato.email, password: this.contato.password}
    this.service.save(ct).subscribe({
      next: (res) => {
        // Armazena o token no localStorage
        localStorage.setItem('auth_token', res.token);

        // Redireciona usando o Router
        this.router.navigate(['/agenda']);

        this.contato = { email: '', password: '' };
      },
      error: (err) => {
        this.erro = 'Credenciais inválidas'; // Trate erros adequadamente
        console.error('Login error:', err);
      }
    });
    this.erro = '';
  }
}
