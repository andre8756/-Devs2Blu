import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CadastroService } from '../services/cadastro.service';

@Component({
  selector: 'app-root',
  imports: [ RouterLink, CommonModule, FormsModule],
  templateUrl: './cadastro.component.html',
  styleUrl: './cadastro.component.css'
})
export class CadastroComponent implements OnInit{
  contatos: any[] = []
  contato = { name: '', email: '', password: '', role: '' };
  editando = false;
  erro = '';

  constructor(private service: CadastroService){}

  ngOnInit(): void {
    this.service.getContatos().subscribe(data => this.contatos = data)
  }

   listarContatos() {
    this.service.getContatos().subscribe(data => {
      this.contatos = data;
    });
  }

  salvar() {
    if(this.contato.name === ''){
       this.erro = 'O nome deve ser informado';
       return
    }

    if(this.contato.email === ''){
      this.erro = 'O email deve ser informado';
      return
   }

   if(this.contato.password === ''){
      this.erro = 'A senha deve ser preenchida'
      return
   }

   if(this.contato.role === ''){
    this.erro = 'O fone deve ser informado';
    return
  }

    let ct = {nome: this.contato.name, email: this.contato.email, password: this.contato.password, role: this.contato.role}
    this.service.save(ct).subscribe(() => {
      this.listarContatos();
      this.contato = { name: '', email: '', password:'', role: '' }
      window.location.href = 'http://localhost:4200/login';
  });

    this.erro = ''
  }

  cancelarEdicao() {
    this.contato = { name: '', email: '', password:'', role: '' };
    this.editando = false;
  }

}


