# Zadatak 1 – januar 2012 

Napraviti javnu klasu **Beba** u paketu **porodiliste.bebe** koja ima:
* Privatni atribut **ime** koji predstavlja ime i prezime bebe. Početna vrednost je NULL.
* Privatni atribut **vremeRodjenja** koji predstavlja tačan datum i vreme rođenja (klasa
GregorianCalendar). Početna vrednost je NULL.
* Privatni atribut **tezina** koji predstavlja težinu na porođaju u gramima (npr. 3650 gr). Početna
vrednost je 0.
* Privatni atribut **duzina** koji predstavlja dužinu bebe u santimetrima (npr. 55 cm). Početna vrednost je
0.
* Odgovarajuće javne get i set metode za ove atribute. Nedozvoljene vrednosti za ime i prezime su
null Stringovi i svi Stringovi kraći od pet znakova, vreme rođenja mora biti pre trenutnog datuma,
težina mora biti u rasponu od 500 gr do 8000 gr, a dužina veća od 25cm. U slučaju unosa
nedozvoljenih vrednosti baciti izuzetak.
* Redefinisanu equals metodu klase Object koja vraća true ako su atributi imePrezime i
vremeRođenja unetog objekta jednaki imenu i prezimenu bebe i vremenu rođenja, a u suprotnom
false.
* Redefinisanu metodu toString klase Object koja vraća String sa svim podacima o bebi.

Napraviti javni interfejs **Statistike** u paketu **porodiliste.statistika** koji ima :
* Javnu metodu **upisiEkstreme** koja kao ulazni parametar prima String i ne vraća ništa.
* Javnu metodu **vratiFrekvencijskuTabelu** koja nema ulaznih parametara, a vraća niz celih brojeva.

Napraviti javnu klasu **Porodiliste** u paketu **porodiliste** koja implementira interfejs Statistike i ima:

- Privatni atribut **bebe** koji predstavlja niz objekata klase Beba.
- Javni konstruktor koji kao ulazni parametar dobija kapacitet porodilišta i inicijalizuje niz na tu
    vrednost. U slučaju da je uneti broj nula ili manji od nule, baciti PROVERAVANI izuzetak.
- Implementiranu javnu metodu **upisiEkstreme** koja u tekstualni fajl čiji je naziv unet kao parametar
    upisuje podatke o najtežoj bebi. Podaci se upisuju u formatu {imePrezime}{tab}{tezina}{tab}{duzina} (nije potrebno upisati vreme rođenja).
- Javnu metodu **otpustiIzPorodilista** koja kao ulazni parametar dobija jedan objekat klase
    GregorianCalendar a vraća listu objekata klase Beba. Ova metoda bi trebalo da u nizu nađe sve
    bebe sa datumom i vremenom rođenja koji je pre unetog datuma, i da ih iskopira u listu. Na kraju,
    metoda vraća listu kao povratnu vrednost.
- Implementiranu javnu metodu **vratiFrekvencijskuTabelu** koja nema ulaznih parametara, a vraća
    niz koji ima tačno 24 elementa. Svaki element niza bi trebalo da sadrži broj beba u porodilištu koji su
    se rodili u satu koji je jednak indeksu elementa niza (npr. poslednji element niza bi trebalo da sadrži
    broj onih koji su se rodili od 23:00 do 23:59h).