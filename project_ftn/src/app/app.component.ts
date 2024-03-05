import { Component, SimpleChanges } from '@angular/core';
import { Subscription } from 'rxjs';
import { StorageService } from './_service/storage.service';
import { AuthService } from './_service/auth.service';
import { EventService } from './_shared/event.service';
import { User } from './_shared/User';

@Component({ 
  selector: 'app-root', 
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.css'] 
})
export class AppComponent {

  private roles: string;
  isLoggedIn = false;
  showModeratorBoard = false;
  username?: string;
  pass: string;
  token: string;
  event?: Subscription;
  user: User;

  constructor( private storage: StorageService, private auth: AuthService, private eventBus: EventService ) { }

  ngOnInt(): void {
    this.isLoggedIn = this.storage.isLoggedIn();
    if(!(this.isLoggedIn)){
      this.storage.getUser().subscribe((resp)=>{
        if(resp){
          this.user = {...resp};
          this.roles = this.user.role;
        }
      });
      this.showModeratorBoard = this.roles.includes('ROLE_MODERATOR');
    }
    //    PREDISPOSIZIONE, non utilizzata attualmente
    //this.event = this.eventBus.on('logout', () =>{ this.logout() });
    this.eventBus.on('logout', () =>{ this.logout() });

  }

  ngOnChanges(changes: SimpleChanges): void {
    if(changes['roles']){
      window.location.reload();
    }
  }

  logout(): void {
    var username = this.storage.getEmail();
    var pass = this.storage.getPass();
    var token = this.storage.getAuth();
    if((username && pass && token)){
      this.auth.logout(username, pass, token);
      this.storage.clean();
      this.isLoggedIn = false;
      this.reloadPage();
    }
  }
 
  eventLogout():void{
    var username = this.storage.getEmail();
    var pass = this.storage.getPass();
    var token = this.storage.getAuth();
    this.auth.logout(username,pass,token).subscribe({
      next: res=>{
        this.storage.clean();
        this.reloadPage();
      },
      error: err=>{
        console.log(err);
      }
    }) 
  }

  reloadPage(): void {
    console.log("Ricarico pagina");
    window.location.reload();
  }
}