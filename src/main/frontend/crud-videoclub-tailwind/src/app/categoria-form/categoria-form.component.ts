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
  templateUrl: './categoria-form.component.html',
  styleUrl: './categoria-form.component.css',
  standalone: true
})
export class CategoriaFormComponent {
  categoriaForm: FormGroup;
  isEdit = false;
  categoriaId: number | null = null;

  // Inyeccion de Dependencia
  cd = inject(ChangeDetectorRef);
  fb = inject(FormBuilder);
  categoriaService = inject(CategoriaService);
  router = inject(Router);
  route = inject(ActivatedRoute);

  constructor() {
    this.categoriaForm = this.fb.group({
      nombre: ['', Validators.required],
    });
  }

  ngOnInit() {
    this.route.paramMap.subscribe((params) => {
      const id = params.get('id');
      if (id) {
        this.isEdit = true;
        this.categoriaId = +id;
        this.categoriaService.get(this.categoriaId).subscribe((data) => {
          this.categoriaForm.patchValue(data);
        });
      } else {
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
          this.router.navigate(['/']);
        });
    } else {
      // Crear Categoria
      this.categoriaService
        .create(this.categoriaForm.value)
        .subscribe(() => {
          this.router.navigate(['/']);
        });
    }
  }
}
