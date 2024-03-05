import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/_service/user.service';
import { StorageService } from '../_service/storage.service';
import { AuthService } from '../_service/auth.service';
import { User } from '../_shared/User';

@Component({
  selector: 'app-board-user',
  templateUrl: './board-user.component.html',
  styleUrls: ['./board-user.component.css']
})
export class BoardUserComponent implements OnInit {

  user: User;
  content?: Object;

  constructor(private userService: UserService, private storService: StorageService) { }

  ngOnInit(): void {
    this.storService.getUser().subscribe(resp=>{
      if(resp){
        this.user = {...resp};
      }
    });


    
  }
  

}