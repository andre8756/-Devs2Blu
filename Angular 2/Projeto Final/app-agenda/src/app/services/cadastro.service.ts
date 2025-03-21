import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CadastroService {

  private apiUrl = 'https://api-users-gdsb.onrender.com/register'

  constructor(private client: HttpClient) { }

  getContatos(): Observable<any>{
    return this.client.get<any[]>(this.apiUrl)
  }

  save(contato:any):Observable<any>{
     return this.client.post<any>(this.apiUrl,contato)
  }

  update(id: any, contato: any): Observable<any> {
    return this.client.put<any>(`${this.apiUrl}/${id}`, contato);
  }

  delete(id: any): Observable<any> {
    return this.client.delete<any>(`${this.apiUrl}/${id}`);
  }
}
