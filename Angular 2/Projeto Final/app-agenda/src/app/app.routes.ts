import { Routes } from '@angular/router';
import { CadastroComponent } from './cadastro/cadastro.component';
import { AgendaComponent } from './agenda/agenda.component';
import { LoginComponent } from './login/login.component';
import { ContatoComponent } from './contato/contato.component';
import { LocalComponent } from './local/local.component';
import { AuthGuard } from './guards/auth.guard';
import { AdminGuard } from './guards/admin.guard';

export const routes: Routes = [
  { path:'' , component: CadastroComponent },
  { path:'agenda', component: AgendaComponent, canActivate:[AuthGuard] },
  { path:'login', component: LoginComponent },
  { path:'agenda/contato', component: ContatoComponent},
  { path: 'agenda/local', component: LocalComponent, canActivate:[AdminGuard] }
];
