import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ContatoService } from './../contato.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-cadastro',
  imports: [RouterOutlet, CommonModule, FormsModule],
  templateUrl: './cadastro.component.html',
  styleUrl: './cadastro.component.css'
})
export class CadastroComponent implements OnInit {

  contatos: any[] = [] //contatos é um array de qualquer coisa (e vazio)
  novoNome = '';
  novoEmail = '';
  novoFone = '';

  constructor(private service: ContatoService){}

    ngOnInit(): void {
        this.service.getContatos().subscribe(data => this.contatos = data)
    }

    inserir(){
      let contato = {nome: this.novoNome, email:this.novoEmail, fone:this.novoFone}
      this.service.save(contato).subscribe(data => console.log(data), error => console.log(error));
      this.ngOnInit();
      this.cancelar();
    }

    cancelar(){
      this.novoNome = ''
      this.novoEmail = ''
      this.novoFone = ''
    }

    editPessoa(){

    }

    deletaPessoa(){

    }

}
