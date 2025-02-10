import {inject, Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {DatePipe} from '@angular/common';
import {HttpClient} from '@angular/common/http';
import {map, Observable, switchMap} from 'rxjs';
import {Categoria} from '../model/categoria.interface';
import {Pelicula} from '../model/pelicula.interface';
@Injectable({
  providedIn: 'root'
})
export class PeliculaService {

  private apiUrl=environment.apiUrl+'/peliculas';
  http=inject(HttpClient);
  datePipe=new DatePipe('es_ES');
  constructor() { }

  getAll(): Observable<Pelicula[]> {
    return this.http.get<Categoria[]>(this.apiUrl);
  }

  get(id: string): Observable<Pelicula> {
    return this.http.get<Pelicula>(`${this.apiUrl}/${id}`);
  }

  create(pelicula: Pelicula): Observable<Pelicula> {
    return this.getAll().pipe(
      map((categorias) => {
        const maxId = categorias.reduce((max, cat) => Math.max(max, Number(cat.id)), 0);
        pelicula.id = '' + (maxId +1);
        pelicula.descripcion = this.datePipe.transform(Date.now(),'yyyy-MM-dd hh:mm:ss')!;
        return pelicula;
      }),
      switchMap((newPelicula) =>
        this.http.post<Pelicula>(this.apiUrl, newPelicula)
      )
    );
  }

  update(id: string, pelicula: Pelicula): Observable<Pelicula> {
    pelicula.id = id;
    pelicula.descripcion = this.datePipe.transform(Date.now(),'yyyy-MM-dd hh:mm:ss')!;
    return this.http.put<Categoria>(`${this.apiUrl}/${id}`, pelicula);
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

}
