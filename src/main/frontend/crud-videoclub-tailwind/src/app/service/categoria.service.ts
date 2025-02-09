import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map, Observable, switchMap} from 'rxjs';
import {environment} from '../../environments/environment';
import {Categoria} from "../model/categoria.interface";
import {DatePipe} from '@angular/common';

@Injectable({
  providedIn: 'root',
})
export class CategoriaService {
  private apiUrl = environment.apiUrl + '/categorias'; // this endpoint was generated in Step 3
  http = inject(HttpClient);
  datePipe = new DatePipe('es_ES');

  getAll(): Observable<Categoria[]> {
    return this.http.get<Categoria[]>(this.apiUrl);
  }

  get(id: string): Observable<Categoria> {
    return this.http.get<Categoria>(`${this.apiUrl}/${id}`);
  }

  create(categoria: Categoria): Observable<Categoria> {
    return this.getAll().pipe(
      map((categorias) => {
        const maxId = categorias.reduce((max, cat) => Math.max(max, Number(cat.id)), 0);
        categoria.id = '' + maxId +1;
        categoria.ultimaActualizacion = this.datePipe.transform(Date.now(),'yyyy-MM-dd hh:mm:ss')!;
        return categoria;
      }),
      switchMap((newCategoria) =>
        this.http.post<Categoria>(this.apiUrl, newCategoria)
      )
    );
  }

  update(id: string, categoria: Categoria): Observable<Categoria> {
    categoria.id = id;
    categoria.ultimaActualizacion = this.datePipe.transform(Date.now(),'yyyy-MM-dd hh:mm:ss')!;
    return this.http.put<Categoria>(`${this.apiUrl}/${id}`, categoria);
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
