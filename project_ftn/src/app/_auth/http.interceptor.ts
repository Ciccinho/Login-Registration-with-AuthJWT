import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpInterceptor, HttpEvent, HTTP_INTERCEPTORS, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { StorageService } from '../_service/storage.service';
import { EventService } from '../_shared/event.service';

const TOKEN_HEADER = 'Authorization'       // '/api/v1/auth/authenticate'

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private storageSer: StorageService, private eventBus: EventService ) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>>{ 
    request = request.clone({ withCredentials: true, });
    return next.handle(request).pipe(
      catchError(
        (error)=>{      //IF gestione errori http 401 e 403
        if(error instanceof HttpErrorResponse && !request.url.includes(TOKEN_HEADER) && error.status === 401){
          return this.handle401Error(request, next);
        }
        if(error instanceof HttpErrorResponse && error.status === 403){
          return this.handle403Error(request, next);
        }             
        return throwError(()=> error);
        }
      )
    );
  }

  private handle401Error( request: HttpRequest<any>, next: HttpHandler){
   this.eventLogout();
    return next.handle(request);
  }

  private handle403Error( request: HttpRequest<any>, next: HttpHandler){
    this.eventLogout();
    return next.handle(request);
  }

  private eventLogout ():void{
    if(this.storageSer.isLoggedIn()){
      this.storageSer.clean();
      window.location.reload();
    }
  }

}

export const httpInterceptorProviders = [
  { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
];