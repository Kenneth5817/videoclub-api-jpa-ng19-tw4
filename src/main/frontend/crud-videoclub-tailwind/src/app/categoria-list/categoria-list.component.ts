import {Component, OnInit, inject} from '@angular/core';
import {Categoria} from '../model/categoria.interface';
import { CategoriaService } from '../service/categoria.service';
import { Router } from '@angular/router';
import {DatePipe} from '@angular/common';

@Component({
  selector: 'app-categoria-list',
  imports: [DatePipe],
  templateUrl: './categoria-list.component.html',
  styleUrl: './categoria-list.component.css',
  standalone: true
})
export class CategoriaListComponent  {
  categoriaService = inject(CategoriaService);
  router = inject(Router);
  categorias: Categoria[] = [];

ngOnInit() {
  this.categoriaService
    .getAll()
    .subscribe((data) => (this.categorias = data));
}

goToCreate() {
  this.router.navigate(['/crear']);
}

edit(id: string) {
  this.router.navigate(['/editar', id]);
}

delete(id: string) {
  if (confirm('¿Quieres borrar esta categoria?')) {
    this.categoriaService.delete(id).subscribe(() => {
      this.categorias = this.categorias.filter((cat) => cat.id !== id);
    });
  }
}
}
