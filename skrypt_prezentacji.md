# SKRYPT PREZENTACJI PROJEKTU "KOSCOKER"

*(Ten tekst jest przygotowany tak, abyś mógł go przeczytać lub opowiedzieć własnymi słowami. Tekst w nawiasach to wskazówki dla Ciebie.)*

---

**Dzień dobry.**
Chciałbym przedstawić projekt aplikacji mobilnej o nazwie **"KoscOker"**. Jest to gra logiczno-losowa, będąca wirtualną adaptacją popularnego Pokera Kościanego.

### 1. Cel i funkcjonalność aplikacji
Głównym celem projektu było stworzenie gry, która pozwala użytkownikowi na rozgrywkę z drugim graczem (w trybie "gorącego krzesła" - na jednym telefonie). Aplikacja umożliwia:
- Przeprowadzenie pełnej rozgrywki (dwa rzuty, wybieranie kości).
- Automatyczne zliczanie punktów i rozpoznawanie układów pokerowych (np. Full, Kareta).
- Zapisywanie historii wyników w trwałej pamięci telefonu.

### 2. Architektura i użyte technologie
Aplikację napisałem w języku **Java** w środowisku Android Studio. Oparłem ją na nowoczesnej architekturze **Single Activity**.
Co to oznacza? Mamy tylko jedno główne okno (`MainActivity`), a poszczególne ekrany (Menu, Gra, Wyniki) są **Fragmentami**. Dzięki temu przełączanie się między ekranami jest bardzo płynne i lekkie dla systemu.

W projekcie wykorzystałem kilka kluczowych technologii z instrukcji laboratoryjnych:

**A) Własny widok (Custom View) i Grafika 2D**
To jest najciekawsza część techniczna projektu. Zamiast używać gotowych obrazków kości, stworzyłem własną klasę `WidokStolu`.
W tej klasie "rysuję" stół i kości za pomocą kodu, używając obiektu **Canvas** (Płótno).
- Metoda `onDraw` rysuje prostokąty i cyfry.
- Metoda `onTouchEvent` wykrywa, gdzie użytkownik dotknął ekranu. Dzięki matematyce obliczam, w którą kość kliknięto, i zmieniam jej kolor na szary (blokada).

**B) Wielowątkowość (Threads)**
Aby gra była dynamiczna, zaimplementowałem animację rzutu kośćmi. Nie mogłem tego zrobić w głównym wątku, bo zablokowałoby to interfejs.
Dlatego stworzyłem klasę `WatekGry`, która dziedziczy po klasie `Thread`.
- Wątek ten działa w tle przez około 2 sekundy.
- W pętli co 100 milisekund losuje nowe liczby i odświeża widok.
- Daje to płynny efekt "turlania się" kości bez zawieszania aplikacji.

**C) Baza Danych SQLite**
Aby wyniki nie znikały po wyłączeniu aplikacji, użyłem wbudowanej bazy danych **SQLite**.
- Klasa `BazaDanych` zarządza tworzeniem tabeli i zapisem danych.
- Dzięki temu w osobnym ekranie "Wyniki" możemy zobaczyć listę wszystkich rozegranych partii, pobraną z bazy zapytaniem SQL `SELECT`.
- Do wyświetlenia tej listy użyłem komponentu **RecyclerView**, który jest wydajniejszy niż zwykłe listy.

### 3. Logika Gry
Logika rozpoznawania układów znajduje się w klasie `LogikaPokera`. Jest to czysty algorytm, który dostał tablicę 5 liczb i sprawdza po kolei: "Czy to Poker?", "Czy to Kareta?", "Czy to Full?". Dzięki temu aplikacja jest bezbłędnym sędzią.

### Podsumowanie
Projekt pozwolił mi połączyć wiele zagadnień: od rysowania grafiki, przez wielowątkowość, aż po bazy danych i obsługę fragmentów. Aplikacja jest kompletna, stabilna i realizuje wszystkie założenia instrukcji.

---
*(Koniec. Jeśli wykładowca zapyta o konkrety, np. "Jak zrobiłeś animację?", odwołaj się do punktu B o wątkach.)*
