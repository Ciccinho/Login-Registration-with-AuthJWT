import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

const AUTH_API = 'http://localhost:8080/api/v1/auth/';
const USER_API = 'http://localhost:8080/api/public/user/';
const httpOptions = { headers: new HttpHeaders ({'Content-Type': 'application/json'})};

@Injectable({ providedIn: 'root' })

export class AuthService {

  constructor (private http: HttpClient) { }

  login (email: string, password: string ): Observable<any> {
    return this.http.post( AUTH_API +'authenticate', {email, password}, httpOptions);
  }

  register (nome: string, cognome: string, email: string, password: string ): Observable<any> {
    return this.http.post( AUTH_API +'register', {nome, cognome, email, password}, httpOptions);
  }

  getUser (email: string, token: string): Observable<any> {
    const header = new HttpHeaders({'Content-Type': 'application/json','Authorization':`Bearer ${token}`});
    return this.http.get( USER_API +'getUser/'+`${email}`, {headers: header});
  }

  getRole (email: string, token: string): Observable<any>{
    const header = new HttpHeaders({'Content-Type': 'application/json','Authorization':`Bearer ${token}`});
    return this.http.get( USER_API +'getRole/'+`${email}`, {headers: header});
  }
  
  logout (email: string, password: string, token: string ): Observable<any> {
    const headersOut = new HttpHeaders({'Content-Type':'application/json', 'Email':email, 'Password': password,'Authorization':`Bearer ${token}`})
    return this.http.post( AUTH_API +'logout/', { headersOut });
  }
}
