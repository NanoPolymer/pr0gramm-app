# pr0gramm App für Android (Community-Fork)

[![Debug Build](https://github.com/NanoPolymer/pr0gramm-app/actions/workflows/debug.yml/badge.svg)](https://github.com/NanoPolymer/pr0gramm-app/actions/workflows/debug.yml)

Community-Fork der ehemals offiziellen, nativen pr0gramm-App ([Original](https://github.com/pr0gramm-com/pr0gramm-app)).
Die offizielle Unterstützung wurde eingestellt. Dieser Fork hält die schnelle, native App am Leben.

## Installation

1. Die neueste `app-release.apk` von der [Releases-Seite](https://github.com/NanoPolymer/pr0gramm-app/releases/latest) herunterladen.
2. Installieren. Android fragt einmalig nach der Erlaubnis, Apps aus dieser Quelle zu installieren.

Die App läuft neben der offiziellen App und benötigt mindestens Android 7.
Updates erkennt die App selbst und bietet sie zur Installation an.

## Unterschiede zum Original

* Keine Werbung
* Kein Tracking: Firebase, Crashlytics und Statistiken an pr0gramm-Server sind entfernt
* Option "Bereits gesehen" im Filter-Menü: blendet bereits gesehene Hochlads aus
* Bilder und Videos kommen von den neuen pr0.app-Servern
* Updates über GitHub statt über app.pr0gramm.com

## Features

* Feed und einzelne Posts, Wechsel zwischen SFW, NSFW, NSFL und POL
* Anmelden mit eigenem Account, Favoriten und Sammlungen
* Posts, Tags und Kommentare voten, Kommentare favorisieren
* Kommentieren, antworten, Tags hinzufügen
* Suche nach Tags, Nutzern und in den Favoriten, Lesezeichen für Suchen
* Nachrichten lesen und schreiben, Benachrichtigungen für neue Nachrichten
* Hochladen von Bildern und Videos direkt vom Handy
* Vorladen von Bildern und Videos für unterwegs
* Videos mit Ton und Untertiteln

## Entwicklung

* Debug-Build: `./gradlew assembleDebug`
* Release: GitHub Actions, Workflow "Release" manuell starten.
  `release.sh` erhöht die Version, baut die signierte APK und erstellt das GitHub-Release mit `update.json`.
  Der Changelog kommt aus den `feat:`- und `fix:`-Commits seit dem letzten Release.

## Lizenz

MIT, siehe [LICENSE](LICENSE).
