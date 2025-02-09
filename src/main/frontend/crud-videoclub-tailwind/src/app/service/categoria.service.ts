import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map, Observable, switchMap} from 'rxjs';
import {environment} from '../../environments/environment';
import {Categoria} from "../model/categoria.interface";

@Injectable({
  providedIn: 'root',
})
export class CategoriaService {
  private apiUrl = environment.apiUrl + '/categorias'; // this endpoint was generated in Step 3
  http = inject(HttpClient);

  getAll(): Observable<Categoria[]> {
    return this.http.get<Categoria[]>(this.apiUrl);
  }

  get(id: number): Observable<Categoria> {
    return this.http.get<Categoria>(`${this.apiUrl}/${id}`);
  }

  create(categoria: Categoria): Observable<Categoria> {
    return this.getAll().pipe(
      map((categorias) => {
        const maxId = categorias.reduce((max, cat) => Math.max(max, cat.id), 0);
        categoria.id = maxId + 1;
        return categoria;
      }),
      switchMap((newCategoria) =>
        this.http.post<Categoria>(this.apiUrl, newCategoria)
      )
    );
  }

  update(id: number, categoria: Categoria): Observable<Categoria> {
    categoria.id = id;
    return this.http.put<Categoria>(`${this.apiUrl}/${id}`, categoria);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
