import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { HomeComponent } from './componenti/home/home.component';
import { LoginComponent } from './componenti/login/login.component';
import { RegisterComponent } from './componenti/register/register.component';
import { BoardUserComponent } from './board_user/board-user.component';
import { ProfileComponent } from './componenti/profile/profile.component';
// import { authgarGuard } from './authgar.guard';


const routes: Routes = [
  { path:'home', component: HomeComponent},
  { path:'login', component: LoginComponent },
  { path:'profile', component: ProfileComponent},
  { path:'register', component: RegisterComponent },
  { path:'user', component: BoardUserComponent },
  { path:'', redirectTo: 'login', pathMatch:'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
