import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/_service/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  
  content?: any;

  constructor() { }

  ngOnInit(): void {}

}
