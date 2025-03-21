import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { DiretivasComponent } from './app/diretivas/diretivas.component';
import { TodoListComponent } from './app/todo-list/todo-list.component';

bootstrapApplication(DiretivasComponent, appConfig)
  .catch((err) => console.error(err));

  bootstrapApplication(TodoListComponent, appConfig)
  .catch((err) => console.error(err));
