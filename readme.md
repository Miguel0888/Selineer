# Selineer

Selineer ist eine **Java 8-kompatible Testautomatisierungsbibliothek**, die eine Playwright-ähnliche API bereitstellt, aber auf **Selenium** aufbaut. Der Name "Selineer" leitet sich von **Selenium** und **Engineer** ab. Dieses Projekt wurde speziell entwickelt, um in Umgebungen zu funktionieren, in denen nur **Java 8** verfügbar ist, da Playwright Java 8 nicht unterstützt.

## Programmstart
Für ausführbare JARs siehe Releases. Sie werden von GitHub Actions stehts auf dem neuesten Stand gehalten.

Mit Manifest-Datei (automatisch erzeugft über build.gradle):
`java -jar <RELEASE-NAME>.jar`

Ohne Manifest:
`java -cp <RELEASE-NAME>.jar selineer.Main`

## Ziel des Projekts

Das Hauptziel von Selineer ist es, eine **zu Playwright kompatible API** zu schaffen, die jedoch auf Selenium basiert. Dadurch wird es möglich, moderne Browser-Testautomatisierung im Playwright-Stil auch mit Java 8 durchzuführen. Darüber hinaus wird die Bibliothek später in der Lage sein, **Cucumber-Testdateien** einzulesen und deren Anweisungen im Browser auszuführen.

## Funktionsübersicht

1. **Playwright-ähnliche API:**
   - Vollständig mit **Java 8** kompatibel.
   - Auf **Selenium** basierend, aber mit einer API, die den Stil und die Funktionsweise von Playwright nachahmt.

2. **Cucumber-Testunterstützung:**
   - Integration mit **Cucumber**, um Anweisungen wie `@Given`, `@When` und `@Then` zu interpretieren und auszuführen.
   - **Erster Fokus:** Tests in einem **Chrome-Browser** auszuführen.

3. **Einfache Funktionen im ersten Schritt:**
   - Unterstützung von `@Given` und `@When`.
   - `@Then` ist vorerst nur darauf beschränkt, Screenshots zu machen (z. B. `"Take a Screenshot"`).

## Meilensteine

### **Meilenstein 0: Grundlegende Playwright-kompatible API**
- Entwicklung einer API, die Playwright nachahmt, jedoch mit Selenium funktioniert.
- Sicherstellung der vollständigen **Java 8-Kompatibilität**.

### **Meilenstein 1: Unterstützung von `@Given`, `@When`, `@Then`**
- **`@Given`** und **`@When`**: Implementieren der grundlegenden Anweisungen und Aktionen im Browser.
- **`@Then`**: Vorläufige Unterstützung zum Aufnehmen von Screenshots.

### **Meilenstein 2: Erweiterte Cucumber-Unterstützung**
- Komplette Unterstützung für Cucumber-Szenarien.
- Erweiterung von **`@Then`**, um komplexere Assertions und Validierungen zu ermöglichen.

---

## Voraussetzungen

- **Java 8** (erforderlich für maximale Kompatibilität).
- Gradle für das Projekt-Build-System.
- Selenium-Bibliothek (als Basis für die API).

---

## Automatische Releases
Siehe Step-Definitionen von GH Actions in .github/workflows/build.gradle.

---

## Installation

1. Klone das Repository:
   ```bash
   git clone https://github.com/Miguel0888/Selineer.git
   cd Selineer
   ```

2. Baue das Projekt:
   ```bash
   ./gradlew build
   ```

3. Nutze die Selineer-API in deinem Java-8-Projekt.

---

## Beispiel (zukünftige Funktion)

Ein Beispiel für eine Cucumber-Testdatei, die mit Selineer funktioniert:

```gherkin
Feature: Google Suche

  Scenario: Suche nach "Selineer"
    Given I navigate to "https://www.google.com"
    When I type "Selineer" into the search bar
    And I press "Enter"
    Then Take a Screenshot
```

Die obigen Schritte werden von Selineer interpretiert und im Chrome-Browser ausgeführt, auch unter Java 8.

---

## Lizenz

Dieses Projekt ist unter der [MIT-Lizenz](LICENSE) lizenziert. Siehe die Lizenzdatei für weitere Details.
