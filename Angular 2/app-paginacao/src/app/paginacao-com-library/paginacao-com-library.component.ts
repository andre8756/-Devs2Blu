import { CommonModule, NgFor } from '@angular/common';
import { Component } from '@angular/core';
import { NgxPaginationModule } from 'ngx-pagination';

@Component({
  selector: 'app-paginacao-com-library',
  imports: [CommonModule, NgFor, NgxPaginationModule],
  templateUrl: './paginacao-com-library.component.html',
  styleUrl: './paginacao-com-library.component.css'
})
export class PaginacaoComLibraryComponent {
  items = Array.from({ length: 100 }, (_, i) => `Item ${'ola'}`);
page = 1;
}
