# SMARTMENU APP

Made by Caglar Celikoz &amp; Hakan Pinar

MINIMALE VEREISTEN
+ Meer dan 3 schermen
+ Gebruik van Fragments
+ Responsive layout
+ Minstens 1 tabel aanwezig in combinatie met MASTER/DETAIL view
+ Gebruik van RecyclerView
+ Nodige event handling &amp; navigatie aanwezig
+ Gebruik van resources (strings, afbeeldingen, ...)
+ Gebruik van SharedPreferences
+ Asynchrone &amp; Threading aanwezig
+ Gebruik van 1 web service

EXTRA'S
+ Pushberichten
+ Animaties
+ Camera
+ QR-Code
+ e-mail om wachtwoord te resetten wordt verzonden door firebase

FUNCTIONALITEIT
Het idee is dat er in een restaurant op ieder tafel een qr-code aanwezig is die de tafel-id voorstelt.
De tafel-id wordt ingescand of er kan ook gekozen om VIP tafel te kiezen.
Vervolgens heeft de klant de kans om een keuze te maken uit verschillende categoriën waarin producten aanwezig zijn.
De klant bepaalt zijn keuze en kan het in een mand toevoegen.
Daarna kan de klant zijn/haar order bevestigen en kiezen voor een review achter te laten.
Er wordt dan een push notification getoond dat de order in behandeling is.
Om de functionaliteit te beperken hebben we na 10 seconden nog een Push notification ingesteld waarin wordt vermeld dat de order voorbereid is. Hierbij hebben we gebruik gemaakt van threads.
Al de gegevens worden in firebase opgeslaan.
We hebben de melding naar de chefkok niet geïmplementeerd in dit project omdat het veel te uitgebreid was voor dit vak.
De gegevens van de categoriën, producten, binnengekomen reviews, gebruikers en orders worden opgeslaan in firebase.
Er is ook een admin-gebruiker aanwezig die de producten kan beheren (updaten, verwijderen) en de reviews kan zien.
De Master/Detail weergave op 1 scherm hebben we geïmplementeerd bij de product-detail weergave van de admin.
Als er geen internet-connectie aanwezig is wordt er een melding getoond.


