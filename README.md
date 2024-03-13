# Login-Registration-with-AuthJWT
Frontend e Backend di una pagina web per la registrazione, login e visione dei dati personali tramite autenticazione token JWT.

# Frontend
Realizzato con angular 16, file essenziali per la comunicazione tra frontend e backend presenti nella cartella "_service" sono: 
interceptor.ts una classe che intercetta le chiamate HTTP e permette o meno le stesse e al verificarmi di un errore permette di gestirlo all'interno del a classe stessa 
auth.service.ts che è specifica per le comunicazioni con autenticazione tramite il token 
storage.service.ts serve per il salvataggio dei dati essenziali per la navigazione su SessionStorage del browser.
I file predisposti ma non utilizzati sono tutta la cartella Board_Moderator predisposizione per un ruolo diverso da USER per la gestione degli user, e la cartella Profile realizzato per gli utenti che hanno più ruoli (USER, ADMIN, ecc...) dove possono visualizzare e eventualmente modificare i dati.

# Backend
Realizzato in  linguaggio Java 17, tramite l'utilizzo di SproingBoot e SpringBoot Security 6.
All'interno della cartella "auth" sono presenti le classi dedicate al settaggio e alla gestione delle richieste e delle risposte con autenticazine.
Nella cartella "config" sono presenti le classi per la configurazione e gestione del JWT (JSON Web Token), la configurazione dei pattern pubblici e privati dell'applicazione, la configuazione del CORS per abilitare la chiamate esterne al Backend.
Le cartelle restanti (controller, service ì, repository) utilizzate per lo standard API.
All'interno di "resourses" il file application.proprierties serve per abilitare il container del database della repository dove vengono gestiti i dati (modificica, aggiunta e rimozione).
Nel file pom.xml sono presenti le dipendeze e i driver presenti e utilizzati.