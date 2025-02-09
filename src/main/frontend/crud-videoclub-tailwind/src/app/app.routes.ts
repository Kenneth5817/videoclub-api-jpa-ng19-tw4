import { Routes } from '@angular/router';
import { CategoriaListComponent } from './categoria-list/categoria-list.component';
import { CategoriaFormComponent } from './categoria-form/categoria-form.component';
import {HomeComponent} from './home/home.component';

export const routes: Routes = [
  { path: '', component: HomeComponent},
  { path: 'home', component: HomeComponent},
  { path: 'categorias', component: CategoriaListComponent },
  { path: 'categorias/crear', component: CategoriaFormComponent },
  { path: 'categorias/editar/:id', component: CategoriaFormComponent },
];
