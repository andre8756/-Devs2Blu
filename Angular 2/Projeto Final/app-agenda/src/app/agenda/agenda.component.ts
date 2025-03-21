import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgModel } from '@angular/forms';
import { CompromissoService } from '../services/compromisso.service';
import { LocalService } from '../services/local.service';
import { ContatoService } from '../services/contato.service';
import { RouterLink } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-agenda',
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './agenda.component.html',
  styleUrl: './agenda.component.css'
})
export class AgendaComponent implements OnInit {
  compromissos: any[] = []
  locais: any[] = []
  contatos: any[] = []
  compromisso = {
    id: null,
    titulo: '',
    descricao: '',
    data: '',
    hora: '',
    contatoId: null,
    localId: null,
    userId: null
  };
  editando = false;
  erro = '';
  isAdmin = false;
  userId = '';

  userEmail = '';

  constructor(
    private service: CompromissoService,
    private localService: LocalService,
    private contatoService: ContatoService,
    private authService: AuthService
  ){}

  ngOnInit(): void {
    this.userEmail = this.authService.getUserEmail();

    this.service.getCompromissos().subscribe(data => this.listarCompromissos(data));

    this.localService.getLocais().subscribe(data => {
      this.locais = data;
    });

    this.contatoService.getContatos().subscribe(data => {
      this.contatos = data;
    });

    this.isAdmin = this.authService.isAdmin();

    this.userId = this.authService.getUserId();
  }

   listarCompromissos(compromissos: any[]): void {
    this.compromissos = compromissos.filter(compromisso => compromisso.userId === this.userId || this.isAdmin);
  }

  salvar() {
    if(this.compromisso.titulo === ''){
       this.erro = 'O titulo deve ser informado';
       return
    }

    if(this.compromisso.descricao === ''){
      this.erro = 'A descricao deve ser informado';
      return
   }

   if(this.compromisso.data === ''){
    this.erro = 'A data deve ser informado';
    return
  }

    if (this.editando) {
      this.service.update(this.compromisso.id, this.compromisso).subscribe(() => {
        this.service.getCompromissos().subscribe(data => this.listarCompromissos(data));
        this.cancelarEdicao();
      });
    } else {
        let ct = {
          titulo: this.compromisso.titulo,
          descricao: this.compromisso.descricao,
          data: this.compromisso.data,
          hora: this.compromisso.hora,
          contatoId: this.compromisso.contatoId,
          localId: this.compromisso.localId,
          userId: this.authService.getUserId()
        }
        this.service.save(ct).subscribe(() => {
        this.service.getCompromissos().subscribe(data => this.listarCompromissos(data));
        this.compromisso = {
          id: null,
          titulo: '',
          descricao: '',
          data: '',
          hora: '',
          contatoId: null,
          localId: null,
          userId: null
        }
      });
    }
    this.erro = ''
  }

  editar(contato: any) {
    this.compromisso = { ...contato };
    this.editando = true;
  }

  cancelarEdicao() {
    this.compromisso = {
      id: null,
      titulo: '',
      descricao: '',
      data: '',
      hora: '',
      contatoId: null,
      localId: null,
      userId: null
    };
    this.editando = false;
  }

  excluir(id: number) {
    this.service.delete(id).subscribe(() => {
      this.service.getCompromissos().subscribe(data => this.listarCompromissos(data));
    });
  }

  getLocalInfo(localId: number): string {
    const localEncontrado = this.locais.find(l => l.id === localId);
    return localEncontrado
      ? `${localEncontrado.nome} - ${localEncontrado.endereco}`
      : 'Local não encontrado';
  }

  getContatoInfo(contatoId: number): string {
    const contatoEncontrado = this.contatos.find(c => c.id === contatoId);
    return contatoEncontrado
      ? `${contatoEncontrado.nome} - ${contatoEncontrado.telefone}`
      : 'Contato não encontrado';
  }

  logout(): void {
    this.authService.logout(); // Chama o método de logout do AuthService
  }

}
