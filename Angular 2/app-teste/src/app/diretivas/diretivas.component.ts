import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-diretivas',
  imports: [CommonModule, FormsModule],
  templateUrl: './diretivas.component.html',
  styleUrl: './diretivas.component.css'
})
export class DiretivasComponent {
  mostrar = true;
  newFruta = '';
  showCesta = true;

  trocar(){
    this.mostrar = !this.mostrar
  }

  frutas = ['maça', 'uva', 'pera']

  showLista(){
    this.showCesta = !this.showCesta
  }

  addLista(){
    this.frutas.push(this.newFruta)
    this.newFruta = '';
  }

  deleteList(){
    const newLista = this.frutas.filter(item => item !== this.newFruta);
    this.frutas = newLista;
  }

}
