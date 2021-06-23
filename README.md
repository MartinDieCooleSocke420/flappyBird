DHSH WINF - Grundlagen der Programmierung 2

# Projekt: FlappyBird mit java Swing

von Marvin und Martin


## Abstrakt das Konzept Flappy Bird
Entwicklung einer Anwendung, welche an das aus 2017 bekannte Videospiel "Flappy Bird" erinnert,
ein Vogel bewegt sich durch schnell auf ihn zu bewegene Hindernisse (Röhren) durch. Hierbei ist die Herausvorderung
als Spieler den Vogel durch Lücken zwischen zwei Hindernisse, welche von oben und unten eine lücke erstellen, zu steuern. 
Eine Spielrunde endet im Falle das der Vogel mit einer Röhre kollidiert.


![Bild von FlappyBird](img/readmeImg/FlappyBird.png)



## Schwierigkeits einstellungen

Je nach persönlicher Eignung und Fähigkeit der Spieler soll es für diese
möglich sein, jeweils eine eigene Schwierigkeit festzulegen.
Hierbei haben drei Größen in die Schwierigkeitseinstellung einfluss.
1. Die Geschwindigkeit des Vogels
2. Die Geschwindigkeit der Röhren
3. Der Abstand der Röhren

Pro durchflogene Röhre wird der Highscore um die gewählten Schwierigkeitswerte erhöht

![Bild der Einstellungen](img/readmeImg/Einstellungen.PNG)



## Speichern und Laden

Das Speichern und Laden der Highscores geschieht im Hintergrund, so wird beim Starten der Anwendung
die bisherigen Highscores geladen. Diese kann man sich anschließend im Startmenü anschauen. Zudem wird nach Beenden eines Spieldurchlaufes die eigene Platzierung 
unter allen bisherigen Highscores angezeigt.

## Bewegung
Nach dem Starte des Spiels ist es möglich, mithilfe der Leertaste sowie der "Pfeiltaste nach oben" den Vogel auf der Y-Achse zu bewegen
 und diesen durch die einzelnen Hindernisse zu manövrieren. Eine Bewegung auf der X-Achse ist hierbei nicht möglich und findet auch nicht statt.


## Client-Server-Modell
Nach Beenden eines Spieldurchlaufes werden alle Highscores an einen Server gesendet. Dieser überprüft, ob die gesendeten Highscores bereits in der Highscoreliste des Servers
gespeichert sind, und speichert diese anschließend ab. Zudem erfragt sich der Client beim Programmstart und ende alle Highscores vom Server. Im Falle das der Server offline ist,
kann jedoch mit den lokalen Highscores gespielt werden. Somit ist ein Offlinemodus ohne Internetverbindung möglich.

##UML-Diagramm

