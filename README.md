## Tetris

Gra logiczna

## Wymagania

Aby móc skorzystać z Tetrisa, potrzebne jest oprogramowanie `Java SE Runtime Environment 8` oraz 59 kb wolnego miejsca na dysku.
## Instalacja

Tetris nie wymaga instalacji, po sciągnięciu jest gotowy do użytku. Program do działania wykorzystuje plik tekstowy, który sam tworzy i do którego zapisuje wyniki gier.

## Zasady

Tetris jest grą logiczną, rozgrywającą się na prostokątnej planszy mającej 20 wierszy i 10 kolumn.

Jest 7 rodzajów klocków. Na planszę przez jej górną krawędź wkracza klocek losowego typu. Gdy cały klocek się na niej znajdzie, możliwe jest obracanie go (pod warunkiem, że miejsce które zająłby po obróceniu nie jest już zajęte przez inny klocek), oraz przesuwanie w dół.

Klocki opadają niezależnie od działań gracza (który może je jedynie przyśpieszyć). Docierając do końca planszy lub zajętego obszaru, klocek się zatrzymuje i losowany jest następny.

Jeśli uda się ułożyć wiersz na całej szerokości planszy, następuje usunięcie go i opadnięcie klocków znajdujących się nad nim - gra przyśpiesza, a gracz dostaje punkty. Możliwe jest zapełnienie i usunięcie paru wierszy na raz, co skutkuje uzyskaniem większej ilości punktów, niż zapełnienie jednego wiersza parę razy:

1 wiersz  - 50 punktów<br/>
2 wiersze - 150 punktów<br/>
3 wiersze - 450 punktów<br/>
4 wiersze - 1350 punktów<br/>


Gra kończy się, gdy nowy klocek nie może w pełni wkroczyć na planszę.

## Sterowanie

Skróty klapiszowe dla menu, komunikatu o uzyskaniu wysokiego wyniku i okna najwyższych wyników:

UP / W - w górę<br/>
DOWN / S - w dół<br/>
ENTER - wybierz<br/>
R - resetuj wyniki (dla okna najwyższych wyników)<br/>

Skróty klawiszowe dla okna wyboru po przegranej:

LEFT / A - w lewo<br/>
RIGHT / D - w prawo<br/>
ENTER - wybierz<br/>

Skróty klawiszowe dla okna gry:

E - obrót klocka o 90 stopni w prawo<br/>
Q - obrót klocka o 90 stopni w lewo<br/>
RIGHT / D - przesunięcie klocka w prawo<br/>
LEFT / A - przesunięcie klocka w lewo<br/>
DOWN / S - przyśpieszenie opadania klocka<br/>

Dla wszystkich okien dostępny jest skrót ESCAPE, który powoduje zamknięcie okna i powrót do wcześniejszego okna, w przypadku menu - wyjście z gry.

## Wersja

1.1
