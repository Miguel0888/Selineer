# Selineer

Selineer ist eine **Java 8-kompatible Testautomatisierungsbibliothek**, die eine Playwright-ähnliche API bereitstellt, jedoch ohne den Node.js-Server. Stattdessen verwendet Selineer einen speziell entwickelten Adapter auf Basis von **CDP4J**, um direkt mit modernen Browsern zu interagieren. Dieses Projekt wurde speziell entwickelt, um Playwrights Funktionalität auch in Umgebungen mit Java 8 verfügbar zu machen.

---

## Programmstart

Für ausführbare JARs siehe die Releases. Diese werden durch **GitHub Actions** stets auf dem neuesten Stand gehalten.

### **Mit Manifest-Datei (automatisch erzeugt über `build.gradle`)**
```bash
java -jar <RELEASE-NAME>.jar
```

### **Ohne Manifest**
```bash
java -cp <RELEASE-NAME>.jar selineer.Main
```

---

## Ziel des Projekts

Das Hauptziel von Selineer ist es, eine **Playwright-kompatible API** zu schaffen, die moderne Browser-Testautomatisierung im Playwright-Stil ermöglicht – jedoch vollständig mit **Java 8**. Die Bibliothek bietet eine native Java-Lösung, die sich von Playwrights Node.js-Server loslöst und stattdessen auf **CDP4J** basiert.

---

## Funktionsübersicht

### **1. Playwright-ähnliche API**
- Vollständig **Java 8-kompatibel**.
- Basierend auf einem speziellen Adapter, der direkt mit **CDP4J** kommuniziert.
- Kein Einsatz des Node.js-Servers erforderlich.

### **2. Cucumber-Testunterstützung**
- Geplante Integration mit **Cucumber**, um Gherkin-Syntax wie `@Given`, `@When` und `@Then` zu interpretieren und auszuführen.
- Erster Fokus auf Tests im **Chrome-Browser**.

### **3. Aktuelle Funktionen**
- **Navigation zu Webseiten**: Der Adapter unterstützt bereits das Öffnen von Webseiten und die Navigation.
- **Pläne für die nächsten Schritte**:
  - Unterstützung für DOM-Interaktionen wie Klicks und Eingaben mit Selektoren.
  - Erweiterung auf komplexere Aktionen, die in Playwright üblich sind.

---

## Meilensteine

### **Meilenstein 0: Grundlegende API (aktuell erreicht)**
- Entwicklung einer API, die Playwright nachahmt, jedoch ohne Node.js funktioniert.
- Unterstützung von **CDP4J** zur direkten Kommunikation mit dem Browser.
- Aktuelle Funktionalität:
  - Verbindung zum Browser herstellen.
  - Navigation zu Webseiten.

### **Meilenstein 1: Selektor- und Button-Interaktionen**
- Unterstützung für gängige DOM-Aktionen wie Button-Klicks und Texteingaben.
- Erweiterung der API um grundlegende Selektorfunktionen (z. B. `click(selector)`).

### **Meilenstein 2: Erweiterte DOM-Interaktionen**
- Unterstützung für komplexe DOM-Manipulationen und Validierungen.
- Erweiterung der Test-API um die Möglichkeit, Attribute und Strukturen auszulesen.

### **Meilenstein 3: Integration mit Cucumber**
- Interpretation von Gherkin-Syntax (`@Given`, `@When`, `@Then`).
- Vollständige Unterstützung von Cucumber-Szenarien.

---

## Voraussetzungen

- **Java 8**: Erforderlich für maximale Kompatibilität.
- **Gradle**: Für das Projekt-Build-System.
- **CDP4J**: Für die direkte Kommunikation mit Chrome über das Chrome DevTools Protocol.

---

## Automatische Releases

Selineer verwendet **GitHub Actions**, um kontinuierlich neue Versionen zu bauen und bereitzustellen. Die Konfiguration findest du in `.github/workflows/build.yml`.

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

## Beispiel: Geplante Nutzung

Ein zukünftiges Beispiel für eine Cucumber-Testdatei, die mit Selineer funktioniert:

```gherkin
Feature: Google Suche

  Scenario: Suche nach "Selineer"
    Given I navigate to "https://www.google.com"
    When I type "Selineer" into the search bar
    And I press "Enter"
    Then Take a Screenshot
```

Die obigen Schritte werden von Selineer interpretiert und im Chrome-Browser ausgeführt – vollständig mit Java 8.

---

## Lizenz

Dieses Projekt ist unter der [MIT-Lizenz](LICENSE) lizenziert. Siehe die Lizenzdatei für weitere Details.

---

## Drittanbieter-Bibliotheken

- **[CDP4J](https://github.com/webfolderio/cdp4j):** Zur Kommunikation mit dem Chrome DevTools Protocol.
- **[Playwright](https://github.com/microsoft/playwright):** Inspiration für die API.
- **[Playwright-Java](https://github.com/microsoft/playwright-java):** Teile der Interfaces basieren auf diesem Projekt.
- **Apache License 2.0:** Lizenz für Drittanbieter-Code.

---

### **Wichtige Änderungen**
- **Selenium wurde durch CDP4J ersetzt**, da Selenium in Java 8 nur den WebDriver unterstützt und keine direkte CDP-Unterstützung bietet. Beachte hierzu den notwendigen Compilerschalter `-parameters` in der `build.gradle` des Subprojects cdp4j.
- Der Adapter arbeitet vollständig ohne Node.js und verwendet stattdessen eine native Java-Lösung.

Falls Fragen bestehen oder Feedback benötigt wird, zögere nicht, ein Issue zu erstellen. 😊

