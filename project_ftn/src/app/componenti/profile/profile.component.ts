import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/_service/auth.service';
import { StorageService } from 'src/app/_service/storage.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})


export class ProfileComponent implements OnInit  {
  
  currentUser: any;

  constructor(private storage: StorageService,private auth: AuthService) { }

  ngOnInit(): void {}

  onSubmit():void{
    var email = this.storage.getEmail();
    var token = this.storage.getAuth();
    this.currentUser = this.auth.getUser(email, token).subscribe({
      next: data=>{
        this.currentUser.nome = data.nome;
        this.currentUser.cognome = data.cognome;
        this.currentUser.email = data.email;
        this.currentUser.role = data.role;
      }
    });
  }
  
}
