import {Component, OnInit, inject, ChangeDetectorRef} from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { CategoriaService } from '../service/categoria.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-categoria-form',
  imports: [ReactiveFormsModule],
  templateUrl: './pelicula-form.component.html',
  styleUrl: './pelicula-form.component.css',
  standalone: true
})
export class PeliculaFormComponent {
  categoriaForm: FormGroup;
  isEdit = false;
  categoriaId: string | null = null;

  // Inyeccion de Dependencia
  cd = inject(ChangeDetectorRef);
  fb = inject(FormBuilder);
  categoriaService = inject(CategoriaService);
  router = inject(Router);
  route = inject(ActivatedRoute);

  constructor() {
    this.categoriaForm = this.fb.group({
      nombre: ['', Validators.required],
      ultimaActualizacion: [''],
    });
  }

  ngOnInit() {
    this.route.paramMap.subscribe((params) => {
      const id = params.get('id');
      if (id) {
        this.isEdit = true;
        this.categoriaId = id;
        this.categoriaService.get(this.categoriaId).subscribe((data) => {
          this.categoriaForm.patchValue(data);
        });
      } else {
        //para forzar re-renderizado en base al data-bound
        this.cd.detectChanges();
      }
    });
  }

  onSubmit() {
    if (this.categoriaForm.invalid) return;

    if (this.isEdit && this.categoriaId) {
      // Editar Categoria
      this.categoriaService
        .update(this.categoriaId, this.categoriaForm.value)
        .subscribe(() => {
          this.router.navigate(['/categorias']);
        });
    } else {
      // Crear Categoria
      this.categoriaService
        .create(this.categoriaForm.value)
        .subscribe(() => {
          this.router.navigate(['/categorias']);
        });
    }
  }
}
