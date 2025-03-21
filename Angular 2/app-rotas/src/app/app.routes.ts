import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { CadastroComponent } from './cadastro/cadastro.component';
import { ConsultaComponent } from './consulta/consulta.component';
import { PageErroComponent } from './page-erro/page-erro.component';
import { EdicaoComponent } from './edicao/edicao.component';


export const routes: Routes = [
    { path:'', component: HomeComponent },
    { path:'cadastro', component: CadastroComponent},
    { path: 'consulta', component: ConsultaComponent},
    { path: 'consulta/:id', component: EdicaoComponent },
    { path:'**', component: PageErroComponent}
];
