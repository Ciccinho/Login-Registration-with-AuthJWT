import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/_service/auth.service';
import { StorageService } from 'src/app/_service/storage.service';

const ROLE_KEY = 'role-user';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {
  
  form: any = { email: null, password: null };
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  email: any;
  password: any;
  roles: any;
  
  constructor( private auth: AuthService, private storage: StorageService ) { }
  
  ngOnInit(): void {
    if(this.storage.isLoggedIn()){
      this.isLoggedIn = true;
      this.email = this.storage.getEmail();
      this.storage.getRole().subscribe((resp:any)=>{
        this.roles = resp.response;
      });
    }
  }

  onSubmit(): void {
    const {email, password} = this.form;
    this.email = email;
    this.password = password;
    this.auth.login(email, password).subscribe({
      next: data =>{ 
        this.storage.saveUser(data.token, this.email, this.password).subscribe( resp =>{
          if(resp){
            this.storage.getRole().subscribe((data:any)=>{ 
              window.sessionStorage.setItem(ROLE_KEY, JSON.stringify(data));
              console.log('data: ',data);
              this.isLoginFailed = false;
              this.isLoggedIn = true;
              this.roles = data.response;
            });
          }
        });
      },
      error: err =>{
        this.errorMessage = err.message;
        this.isLoginFailed = true;
      }
    });
  }

}
