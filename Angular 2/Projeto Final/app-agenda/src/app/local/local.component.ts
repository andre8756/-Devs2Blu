import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgModel } from '@angular/forms';
import { LocalService } from '../services/local.service';

@Component({
  selector: 'app-local',
  imports: [CommonModule, FormsModule],
  templateUrl: './local.component.html',
  styleUrl: './local.component.css'
})
export class LocalComponent {
  locais: any[] = []
  local = {
      id: null,
      nome: '',
      endereco: ''
    };
    editando = false;
    erro = '';

    constructor(private service: LocalService){}

    ngOnInit(): void {
      this.service.getLocais().subscribe(data => this.locais = data)
    }

     listarCompromissos() {
      this.service.getLocais().subscribe(data => {
        this.locais = data;
      });
    }

    salvar() {
      if(this.local.nome === ''){
         this.erro = 'O nome deve ser informado';
         return
      }

      if(this.local.endereco === ''){
        this.erro = 'O endereço deve ser informado';
        return
     }

      if (this.editando) {
        this.service.update(this.local.id, this.local).subscribe(() => {
          this.listarCompromissos();
          this.cancelarEdicao();
        });
      } else {
          let lc = {
            nome: this.local.nome,
            endereco: this.local.endereco
          }
          this.service.save(lc).subscribe(() => {
          this.listarCompromissos();
          this.local = {
            id: null,
            nome: '',
            endereco: ''
          }
        });
      }
      this.erro = ''
    }

    editar(local: any) {
      this.local = { ...local };
      this.editando = true;
    }

    cancelarEdicao() {
      this.local = {
        id: null,
        nome: '',
        endereco: ''  
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
