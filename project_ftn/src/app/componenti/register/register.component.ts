import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/_service/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  form: any = { nome: null, cognome: null, email: null, password: null};
  isSuccessful = false;
  isSignupFailed = false;
  errorMessage = '';

  constructor (private auth: AuthService ) { }

  ngOnInit(): void { }

  onSubmit (): void {
    const {nome, cognome, email, password } = this.form;
    this.auth.register(nome, cognome, email, password).subscribe({
      next: data =>{
        console.log(data);
        this.isSuccessful = true;
        this.isSignupFailed = false;
      },
      error: err =>{
        this.errorMessage = err.error.message;
        this.isSignupFailed = true;
      }
    }); 
  }
  
}