import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ContatoService } from './contato.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgxPaginationModule } from 'ngx-pagination';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, CommonModule, FormsModule, NgxPaginationModule],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  contatos: any[] = [];
  contato = { id: null, nome: '', email: '', fone: '' };
  editando = false;
  erro = '';

  // Variáveis para paginação
  items: string[] = [];
  page = 1;

  constructor(private service: ContatoService) {}

  ngOnInit(): void {
    this.listarContatos();
  }

  listarContatos() {
    this.service.getContatos().subscribe(data => {
      this.contatos = data;
      this.atualizarItems(); // Atualiza a lista de itens com base no tamanho de contatos
    });
  }

  atualizarItems() {
    this.items = this.contatos.map((contato, index) => `Contato: ${contato.nome}, ${contato.email}, ${contato.fone}`);
  }

  salvar() {
    if (this.contato.nome === '') {
      this.erro = 'O nome deve ser informado';
      return;
    }

    if (this.contato.email === '') {
      this.erro = 'O email deve ser informado';
      return;
    }

    if (this.contato.fone === '') {
      this.erro = 'O fone deve ser informado';
      return;
    }

    if (this.editando) {
      this.service.update(this.contato.id, this.contato).subscribe(() => {
        this.listarContatos();
        this.cancelarEdicao();
        alert('Contato editado com sucesso');
      });
    } else {
      let ct = { nome: this.contato.nome, email: this.contato.email, fone: this.contato.fone };
      this.service.save(ct).subscribe(() => {
        this.listarContatos();
        this.contato = { id: null, nome: '', email: '', fone: '' };
      });
    }
    this.erro = '';
  }

  editar(contato: any) {
    this.contato = { ...contato };
    this.editando = true;
  }

  editarItem(item: string) {
    // Extrair as informações do item
    const index = this.items.indexOf(item);
    const contato = this.contatos[index];

    this.contato = { ...contato };
    this.editando = true;
  }

  excluirItem(item: string) {
    const index = this.items.indexOf(item);
    const id = this.contatos[index].id;

    this.service.delete(id).subscribe(() => {
      this.listarContatos();
    });
  }

  cancelarEdicao() {
    this.contato = { id: null, nome: '', email: '', fone: '' };
    this.editando = false;
  }

  excluir(id: number) {
    this.service.delete(id).subscribe(() => {
      this.listarContatos();
    });
  }
}
