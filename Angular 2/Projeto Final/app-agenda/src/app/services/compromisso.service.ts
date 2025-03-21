import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CompromissoService {

  private apiUrl = 'http://localhost:3000/compromissos'
  
  constructor(private client: HttpClient) { }

  getCompromissos(): Observable<any>{
    return this.client.get<any[]>(this.apiUrl)
  }

  save(compromisso:any):Observable<any>{
     return this.client.post<any>(this.apiUrl,compromisso)
  }

  update(id: any, compromisso: any): Observable<any> {
    return this.client.put<any>(`${this.apiUrl}/${id}`, compromisso);
  }
  
  delete(id: any): Observable<any> {
    return this.client.delete<any>(`${this.apiUrl}/${id}`);
  }

}