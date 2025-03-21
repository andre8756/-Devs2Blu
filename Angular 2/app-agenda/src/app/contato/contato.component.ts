import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgModel } from '@angular/forms';
import { ContatoService } from '../services/contato.service';

@Component({
  selector: 'app-contato',
  imports: [CommonModule, FormsModule],
  templateUrl: './contato.component.html',
  styleUrl: './contato.component.css'
})
export class ContatoComponent {
  contatos: any[] = []
    contato = {
        id: null,
        nome: '',
        telefone: '',
        email: ''
      };
    editando = false;
    erro = '';

    constructor(private service: ContatoService){}

    ngOnInit(): void {
      this.service.getContatos().subscribe(data => this.contatos = data)
    }

      listarCompromissos() {
      this.service.getContatos().subscribe(data => {
        this.contatos = data;
      });
    }

    salvar() {
      if(this.contato.nome === ''){
          this.erro = 'O nome deve ser informado';
          return
      }

      if(this.contato.telefone === ''){
        this.erro = 'O endereço deve ser informado';
        return
      }

      if(this.contato.email === ''){
      this.erro = 'O endereço deve ser informado';
      return
    }

      if (this.editando) {
        this.service.update(this.contato.id, this.contato).subscribe(() => {
          this.listarCompromissos();
          this.cancelarEdicao();
        });
      } else {
          let lc = {
            nome: this.contato.nome,
            telefone: this.contato.telefone,
            email: this.contato.email
          }
          this.service.save(lc).subscribe(() => {
          this.listarCompromissos();
          this.contato = {
            id: null,
            nome: '',
            telefone: '',
            email: ''
          }
        });
      }
      this.erro = ''
    }

    editar(contato: any) {
      this.contato = { ...contato };
      this.editando = true;
    }

    cancelarEdicao() {
      this.contato = {
        id: null,
        nome: '',
        telefone: '',
        email: ''  
        };
      this.editando = false;
    }

    excluir(id: number) {
      this.service.delete(id).subscribe(() => {
        this.listarCompromissos();
      });
    }

    voltar() {
      window.location.href = 'http://localhost:4200/agenda';
    }

}
