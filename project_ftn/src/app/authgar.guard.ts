import { CanActivateFn, Router } from '@angular/router';
import { StorageService } from './_service/storage.service';
import { inject } from '@angular/core';


export const authgarGuard: CanActivateFn = (route, state) => {

  if(!inject(StorageService).isLoggedIn()){
    inject(Router).navigate(['login']);
    return false;
  }
  return true;
  
};