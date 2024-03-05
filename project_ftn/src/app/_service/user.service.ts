import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';
import { StorageService } from './storage.service';

const API_URL = 'http://localhost:8080/api/public/user/';

@Injectable({ providedIn: 'root' })

export class UserService {

  constructor(private auth: AuthService, private storage: StorageService) {}

  getUserBoard(): Observable<string> {
    console.log("accesso getUserBoard di user.service");
    return this.auth.getRole(this.storage.getEmail(), this.storage.getAuth());
  }
    // predisposizione, non utilizzato attualmente
  getModeratorBoard(): Observable<any> {
    console.log("accesso getUserBoard di user.service");
    return this.auth.getRole(this.storage.getEmail(), this.storage.getAuth());
  }

}