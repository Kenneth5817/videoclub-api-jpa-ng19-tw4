import {Component, OnInit, inject, ChangeDetectorRef} from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { CategoriaService } from '../service/categoria.service';
import { ActivatedRoute, Router } from '@angular/router';
import {PeliculaService} from '../service/pelicula.service';

@Component({
  selector: 'app-pelicula-form',
  imports: [ReactiveFormsModule],
  templateUrl: './pelicula-form.component.html',
  styleUrl: './pelicula-form.component.css',
  standalone: true
})
export class PeliculaFormComponent {
  peliculaForm: FormGroup;
  isEdit = false;
  categoriaId: string | null = null;

  // Inyeccion de Dependencia
  cd = inject(ChangeDetectorRef);
  fb = inject(FormBuilder);
  peliculaService = inject(PeliculaService);
  router = inject(Router);
  route = inject(ActivatedRoute);

  constructor() {
    this.peliculaForm = this.fb.group({
      nombre: ['', Validators.required],
      ultimaActualizacion: [''],
    });
  }

  ngOnInit() {
    this.route.paramMap.subscribe((params) => {
      const id = params.get('id');
      if (id) {
        this.isEdit = true;
        this.idPelicula = id;
        this.peliculaService.get(this.idPelicula).subscribe((data) => {
          this.peliculaForm.patchValue(data);
        });
      } else {
        //para forzar re-renderizado en base al data-bound
        this.cd.detectChanges();
      }
    });
  }

  onSubmit() {
    if (this.peliculaForm.invalid) return;

    if (this.isEdit && this.idPelicula) {
      // Editar Categoria
      this.peliculaService
        .update(this.idPelicula, this.peliculaForm.value)
        .subscribe(() => {
          this.router.navigate(['/peliculas']);
        });
    } else {
      // Crear Categoria
      this.peliculaService
        .create(this.peliculaForm.value)
        .subscribe(() => {
          this.router.navigate(['/peliculas']);
        });
    }
  }
}
