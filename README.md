DHSH WINF - Grundlagen der Programmierung 2

# Projekt: FlappyBird mit java Swing

von Marvin und Martin


## Abstrakt das Konzept Flappy Bird
Entwicklung einer Anwendung, welche an das aus 2017 bekannte Videospiel "Flappy Bird" erinnert,
ein Vogel bewegt sich durch schnell auf ihn zu bewegene Hindernisse (R�hren) durch. Hierbei ist die herrausvorderung
als Spieler den Vogel durch l�cken zwischen zwei Hindernisse, welche von oben und unten eine l�cke erstellen, zu steuern. 
Eine Spielrunde endet im falle das der Vogel mit einer R�hre Kollidiert.


![Bild von FlappyBird](img/readmeImg/FlappyBird.png)



## Schwierigkeits einstellungen

Je nach pers�nlicher eignung und F�higkeit der Spieler soll es f�r diese
m�glich sein jeweils eine eigene Schwirigkeit festzulegen.
Hierbei haben drei gr��en in die Schwierigkeitseinstellung einfluss.
1. Die geschwindigkeit des Vogels
2. Die geschwindigkeit der R�hren
3. Der abstand der R�rhren

Pro durchflogene R�hre wird der Highscore um die gew�hlten schwierigkeitswerte erh�ht

![Bild der Einstellungen](img/readmeImg/Einstellungen.PNG)



## Speichern und Laden

Das Speichern und Laden der Highscores geschieht im Hintergrund, so wird beim starten der Anwendung
die bisherigen Highscores geladen. Diese kann man sich Anschlie�end im Startmen� anschauen. Zudem wird nach beenden eines Spieldurchlaufes die eigene Plazierung 
unter allen bisherigen Highscores angezeigt.

## Bewegung
Nach dem Starte des Spiels ist es m�glich, mithilfe der Leertaste sowie der "PfeilTaste nach oben" den Vogel auf der Y-Achse zu bewegen
 und diesen durch die einzelnen hindernisse zu man�vrieren. Eine Bewegung auf der X-Achse ist hierbei nicht m�glich und findet auch nicht statt.


## Client-Server-Modell
Nach beenden eines Spieldurchlaufes werden alle Highscores an einen Server gesendet. Dieser �berpr�ft ob die gesendeten Highscores bereits in der highscoreliste des Servers
gespeichert sind, und speichert diese anschlie�end ab.
