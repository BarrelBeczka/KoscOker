# ANALIZA INSTRUKCJI I ZASTOSOWANYCH TECHNOLOGII

W tym dokumencie opisano powiązania między instrukcjami laboratoryjnymi (plik `pdf_analysis_output.txt`) a kodem naszego projektu "KoscOker". Dla każdego elementu wyjaśniono teorię ("Co to jest?") oraz praktykę ("Jak to działa u nas?").

---

## 1. Wątki (Threads)
**Gdzie w instrukcji:** Instrukcja nr 7 (temat: "Współbieżne wykonywanie zadań – wątki").
**Gdzie w kodzie:** Klasa `WatekGry.java`.

### Co to są wątki? (Teoria)
Wątki to "podprogramy" działające równolegle. Wyobraź sobie kucharza (Procesor). Gdyby miał tylko jedną rękę (jeden wątek główny), musiałby przerywać krojenie, żeby zamieszać zupę. Wątki pozwalają mu robić te rzeczy "na raz". W Androidzie jest to kluczowe, ponieważ **Wątek Główny (UI Thread)** zajmuje się rysowaniem przycisków i reagowaniem na dotyk. Jeśli w tym wątku zrobilibyśmy coś długiego (np. zatrzymali czas na 2 sekundy pętlą `sleep`), aplikacja by zamarzła ("Application Not Responding"). Dlatego długie operacje wrzucamy do osobnego wątku w tle.

### Jak to działa w naszym programie?
Wykorzystaliśmy klasę `Thread` w pliku `WatekGry`.
1. **Cel:** Chcemy, aby kości "tulały się" przez 2 sekundy, zmieniając cyferki.
2. **Realizacja:**
   - Klasa `WatekGry` rozszerza `Thread`.
   - W metodzie `run()` mamy pętlę `while`, która wykonuje się 20 razy.
   - W środku pętli mamy `Thread.sleep(100)`, co wstrzymuje wątek na 0.1 sekundy.
   - Po każdej pauzie losujemy nowe liczby dla kości i odświeżamy widok.
3. **Dlaczego tak?** Gdybyśmy zrobili `sleep` w `MainActivity`, przyciski przestałyby działać na 2 sekundy. Dzięki osobnemu wątkowi, animacja leci w tle, a aplikacja wciąż żyje.

---

## 2. Baza Danych SQLite
**Gdzie w instrukcji:** Instrukcja nr 8 (temat: "Obsługa lokalnej bazy danych SQLite").
**Gdzie w kodzie:** Klasa `BazaDanych.java`.

### Co to jest SQLite? (Teoria)
SQLite to mała, lekka baza danych wbudowana w telefon. W przeciwieństwie do zmiennych w programie (które znikają po wyłączeniu aplikacji), dane w bazie są **takie same jak pliki** – zostają na zawsze, dopóki ich nie usuniemy. Używa się języka SQL (Structured Query Language) do zarządzania danymi (komendy typu: `CREATE TABLE`, `INSERT`, `SELECT`).

### Jak to działa w naszym programie?
Wykorzystaliśmy klasę pomocniczą `SQLiteOpenHelper`.
1. **Tworzenie:** W metodzie `onCreate` wykonujemy SQL: `CREATE TABLE wyniki (...)`. Tworzymy tabelę z kolumnami: ID, Punkty Gracza 1, Punkty Gracza 2, Data.
2. **Dodawanie:** Gdy gra się kończy, metoda `dodajWynik` robi `INSERT INTO wyniki...`, zapisując punkty.
3. **Odczyt:** W ekranie wyników (`Wyniki.java`) metoda `pobierzWszystkieWyniki` robi `SELECT * FROM wyniki`. Dzięki temu nawet po restarcie telefonu widzisz historię gier.

---

## 3. Grafika 2D (Canvas / Custom View)
**Gdzie w instrukcji:** Instrukcja nr 9 (temat: "Tryb graficzny w systemie android...").
**Gdzie w kodzie:** Klasa `WidokStolu.java`.

### Co to jest Canvas? (Teoria)
Canvas (Płótno) to obiekt w Androidzie, który pozwala "rysować" kodem. Zamiast wstawiać gotowy obrazek `.jpg`, piszesz instrukcje typu: "narysuj koło w punkcie X,Y o promieniu R i kolorze Czerwonym". Pozwala to na tworzenie niestandardowych elementów interfejsu (Custom Views), które wyglądają dokładnie tak, jak chcemy.

### Jak to działa w naszym programie?
Stworzyliśmy własną klasę widoku `WidokStolu`.
1. **Rysowanie:** W metodzie `onDraw` używamy obiektu `Canvas`.
   - `drawRect`: Rysujemy zielony stół i białe kostki.
   - `drawText`: Rysujemy cyferki na kostkach.
2. **Interakcja:** Metoda `onTouchEvent` sprawdza, gdzie dotknąłeś ekranu. Jeśli współrzędne dotyku (X, Y) pokrywają się z rysunkiem kostki, zmieniamy jej stan (zaznaczamy ją).
To o wiele bardziej zaawansowane niż zwykłe użycie `ImageView`, bo mamy pełną kontrolę nad wyglądem i logiką.

---

## 4. Fragmenty (Fragments)
**Gdzie w instrukcji:** Instrukcja nr 14 (temat: "Fragmenty").
**Gdzie w kodzie:** Klasy `Menu`, `Gra`, `Wyniki`, `MainActivity`.

### Co to są Fragmenty? (Teoria)
Fragment to taka "pod-aktywność", część interfejsu, którą można wymieniać. Wyobraź sobie RAMĘ obrazu (Activity) i PŁÓTNA (Fragmenty). Rama wisi na ścianie cały czas ta sama, ale możemy wyjmować z niej płótna i wkładać inne. Dzięki temu nie musimy uruchamiać nowych ciężkich Activity, tylko podmieniamy zawartość ekranu.

### Jak to działa w naszym programie?
Nasz projekt jest **Single Activity App** (Aplikacja z jedną Aktywnością).
1. `MainActivity` to tylko kontener (puste pudełko).
2. Na starcie wkładamy do niego fragment `Menu`.
3. Gdy klikniesz "Graj", kod w `MainActivity` podmienia `Menu` na fragment `Gra`.
4. Dzięki temu przejścia są płynne, a obsługa przycisku "Wstecz" jest łatwa (cofamy transakcję fragmentu).

---

## 5. Listy (RecyclerView)
**Gdzie w instrukcji:** Instrukcja nr 12 (temat: "Prezentacja danych... RecyclerView").
**Gdzie w kodzie:** Klasa `Wyniki.java`, `MojAdapterWynikow.java`.

### Co to jest RecyclerView? (Teoria)
To ulepszona lista (lepsza niż stare ListView). Jest "Recycler" (Inwestor Recyklingu) – gdy przewijasz listę w dół, górne elementy, które znikają z ekranu, nie są niszczone. Są "czyszczone" i przenoszone na dół, żeby wyświetlić nowe dane. Dzięki temu telefon nie zacina się nawet przy liście 10 000 elementów.

### Jak to działa w naszym programie?
Wykorzystujemy go w ekranie `Wyniki`.
- Layout `fragment_wyniki.xml` zawiera `<RecyclerView>`.
- Adapter (`MojAdapterWynikow`) bierze listę obiektów `Wynik` (z bazy danych) i dla każdego z nich tworzy mały widok (wiersz).
- To profesjonalne podejście do wyświetlania danych w Androidzie.
