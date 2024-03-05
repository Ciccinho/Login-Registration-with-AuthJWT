import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';
import { User } from '../_shared/User';

const USER_KEY = 'auth-user';   
const EMAIL_KEY = 'email-user';
const PASS_KEY = 'pass-user';


@Injectable({ providedIn: 'root' })

export class StorageService {

  constructor( private auth: AuthService ) { }

  //salvataggio nel SessionStorage dell'user dopo la richiesta di Login
  public saveUser (user: string, email: string, password: string): Observable<boolean>{
    window.sessionStorage.setItem(USER_KEY, user);
    window.sessionStorage.setItem(EMAIL_KEY, email);
    window.sessionStorage.setItem(PASS_KEY, password);
    return new Observable(value =>{
      value.next(true);
    });
  }

  public getUser (): Observable<any>{
    var user = window.sessionStorage.getItem(EMAIL_KEY);
    var token = window.sessionStorage.getItem(USER_KEY);     
    if(user) {
      return  this.auth.getUser(user, token!);
    } 
    return new Observable();
  }
  
  // token presente fino a quando utente connesso
  public isLoggedIn (): boolean {
    const token = window.sessionStorage.getItem(USER_KEY);
    return !!token;      
  }
    
  public getEmail(): string {
    var email = window.sessionStorage.getItem(EMAIL_KEY);
    return email!;
  }

  public getPass(): string {
    var pass = window.sessionStorage.getItem(PASS_KEY);
    return pass!;
  }

  public getAuth(): string {
    var auth = window.sessionStorage.getItem(USER_KEY);
    return auth!;
  }
     
  public getRole(): Observable<any> {
    const email = window.sessionStorage.getItem(EMAIL_KEY);
    const token = window.sessionStorage.getItem(USER_KEY);
    return this.auth.getRole(email!, token!);
  }

  // cancellazione user dal SessionStorage
  clean(): void {
    console.log("Richiesta logout: cancellazione user dal SessionStorage");
    window.sessionStorage.clear();
  }

}