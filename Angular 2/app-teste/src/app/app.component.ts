import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, FormsModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'Olá Mundo!';
  imageUrl = 'https://angular.io/assets/images/logos/angular/angular.png';
  nome = "maria";

  ola(){
    this.nome = 'joao'
    alert('Ola '+ this.nome);
  }

  sla(){
    this.nome = 'maria'
    alert('Olá '+this.nome)
  }

  fruta='melao'
}


