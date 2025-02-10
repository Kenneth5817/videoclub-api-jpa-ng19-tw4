import {Component, OnInit, inject} from '@angular/core';
import {Categoria} from '../model/categoria.interface';
import { CategoriaService } from '../service/categoria.service';
import { Router } from '@angular/router';
import {DatePipe} from '@angular/common';

@Component({
  selector: 'app-categoria-list',
  imports: [DatePipe],
  templateUrl: './pelicula-list.component.html',
  styleUrl: './pelicula-list.component.css',
  standalone: true
})
export class PeliculaListComponent {
  peliculaService = inject(CategoriaService);
  router = inject(Router);
  categorias: Categoria[] = [];

ngOnInit() {
  this.peliculaService
    .getAll()
    .subscribe((data) => (this.categorias = data));
}

goToCreate() {
  this.router.navigate(['/categorias/crear']);
}

edit(id: string) {
  this.router.navigate(['/categorias/editar', id]);
}

delete(id: string) {
  if (confirm('¿Quieres borrar esta categoria?')) {
    this.peliculaService.delete(id).subscribe(() => {
      this.categorias = this.categorias.filter((cat) => cat.id !== id);
    });
  }
}
}
