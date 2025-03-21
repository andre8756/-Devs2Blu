import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { CadastroComponent } from './cadastro/cadastro.component';
import { ConsultasComponent } from './consultas/consultas.component';
import { ErrorPageComponent } from './error-page/error-page.component';

export const routes: Routes = [
    { path:'', component: HomeComponent },
    { path:'cadastro', component: CadastroComponent},
    { path: 'consulta', component: ConsultasComponent},
    { path:'**', component: ErrorPageComponent}
];
