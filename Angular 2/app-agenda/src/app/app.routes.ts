import { Routes } from '@angular/router';
import { CadastroComponent } from './cadastro/cadastro.component';
import { AgendaComponent } from './agenda/agenda.component';
import { LoginComponent } from './login/login.component';

export const routes: Routes = [
  { path:'' , component: CadastroComponent },
  { path:'agenda', component: AgendaComponent },
  { path:'login', component: LoginComponent }
];
