import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-edicao',
  imports: [],
  templateUrl: './edicao.component.html',
  styleUrl: './edicao.component.css'
})
export class EdicaoComponent implements OnInit {

  constructor(private route:ActivatedRoute){

  }

  idEdicao: any;

  ngOnInit(): void {
    this.idEdicao = this.route.snapshot.paramMap.get('id')
  }

}
