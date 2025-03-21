import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../login.service';

@Component({
  selector: 'app-login',
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
    this.service.save(ct).subscribe(() => {
    this.contato = { email: '', password:'' }

    window.location.href = 'http://localhost:4200/agenda';
  })
    this.erro = '';
  }
}
