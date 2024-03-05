import { Injectable } from '@angular/core';
import { filter, map } from 'rxjs/operators';
import { Subject, Subscription } from 'rxjs';
import { EventData } from './event';

@Injectable({ providedIn: 'root' })

    //PREDISPOSIZIONE, non utilizzata attualmente

export class EventService {

  private subject$ = new Subject<EventData>();

  emit(event: EventData) {
    this.subject$.next(event);
  }

  on(eventName: string, action: any): Subscription {
    debugger;
    console.log('sono in Event',eventName , '    -    ', action);
    return this.subject$.pipe(filter((e: EventData)=> {
      console.log('event->', e);
      return e.nome === eventName}),
     map((e: EventData)=> e["value"])).subscribe(action);

  }
}
