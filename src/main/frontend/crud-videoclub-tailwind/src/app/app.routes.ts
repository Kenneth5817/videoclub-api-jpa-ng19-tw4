import { Routes } from '@angular/router';
import { CategoriaListComponent } from './categoria-list/categoria-list.component';
import { CategoriaFormComponent } from './categoria-form/categoria-form.component';

export const routes: Routes = [
  { path: '', component: CategoriaListComponent },
  { path: 'crear', component: CategoriaFormComponent },
  { path: 'editar/:id', component: CategoriaFormComponent },
];
