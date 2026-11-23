# Bilverkstad – Spring Data JPA

REST-baserat system för ett bilverkstadsföretag som hanterar kunder och fordon. Projektet är byggt med Spring Boot, Spring Web, Spring Data JPA och en H2-databas (i minnet).

En kund kan ha flera fordon. Ett fordon kopplas till en kund i ett separat steg.

## Krav

- Java 17 eller senare (samma version som projektet är konfigurerat för i `pom.xml`)
- Maven (eller Maven Wrapper `mvnw` om den finns i projektet)

## Starta projektet

### Alternativ 1: Startskript

Linux / macOS / Git Bash:

```bash
chmod +x start.sh
./start.sh
```

Windows (cmd eller PowerShell):

```
start.bat
```

### Alternativ 2: Direkt med Maven

```bash
mvn spring-boot:run
```

### Alternativ 3: Från IntelliJ

Öppna projektet och kör klassen med `@SpringBootApplication`.

Applikationen startar på **http://localhost:8080**.

Databasen är H2 i minnet, vilket betyder att all data försvinner när applikationen stoppas.

## Projektstruktur

| Paket | Innehåll |
|---|---|
| `se.yrgo.entity` | `Customer`, `Vehicle` |
| `se.yrgo.repository` | `CustomerRepository`, `VehicleRepository` |
| `se.yrgo.dto` | `CustomerDTO`, `VehicleDTO`, `VehicleBrandDTO` |
| `se.yrgo.projection` | `VehicleBrandProjection` |
| `se.yrgo.controller` | `CarWorkshopController` |

## Endpoints

| Funktion | Metod | URL |
|---|---|---|
| Skapa kund | POST | `/customers?name=Anna&phone=123456789` |
| Hämta kund-ID via namn | GET | `/customer-id?name=Anna` |
| Lista alla kunder med fordon | GET | `/customers` |
| Skapa fordon | POST | `/vehicles?registrationNumber=ABC123&brand=Volvo&model=V60&productionYear=2022` |
| Lista alla fordon | GET | `/vehicles` |
| Lista fordon med visst märke | GET | `/vehicles-by-brand?brand=Volvo` |
| Koppla fordon till kund | POST | `/addvehicletocustomer?customerId=1&vehicleId=1` |
| Uppdatera bilmärke | PUT | `/vehicles/1/brand?brand=Saab` |
| Lista registreringsnummer och märke | GET | `/vehicle-brand-info` |

## Exempelanrop (curl)

Anropen är i den ordning de lämpar sig att testa.

### Skapa kund

```bash
curl -X POST "http://localhost:8080/customers?name=Anna&phone=123456789"
```

Exempelsvar:

```json
{"id":1,"name":"Anna","phoneNumber":"123456789","vehicles":[]}
```

### Hämta kund-ID via namn

```bash
curl "http://localhost:8080/customer-id?name=Anna"
```

Exempelsvar: `1`. Om kunden inte finns returneras 404.

### Skapa fordon

```bash
curl -X POST "http://localhost:8080/vehicles?registrationNumber=ABC123&brand=Volvo&model=V60&productionYear=2022"
```

Exempelsvar:

```json
{"id":1,"registrationNumber":"ABC123","brand":"Volvo","model":"V60","productionYear":"2022"}
```

### Koppla fordon till kund

```bash
curl -X POST "http://localhost:8080/addvehicletocustomer?customerId=1&vehicleId=1"
```

Returnerar kunden med fordonet i sin lista. Om kunden eller fordonet saknas returneras 404.

### Lista alla kunder tillsammans med deras fordon

```bash
curl "http://localhost:8080/customers"
```

Kunder utan fordon kommer också med (tom lista).

### Lista alla fordon

```bash
curl "http://localhost:8080/vehicles"
```

### Lista fordon med visst märke

```bash
curl "http://localhost:8080/vehicles-by-brand?brand=Volvo"
```

Returnerar en tom lista om inget fordon har märket.

### Uppdatera bilmärke

```bash
curl -X PUT "http://localhost:8080/vehicles/1/brand?brand=Saab"
```

Returnerar det uppdaterade fordonet. Om fordonet inte finns returneras 404.

### Lista registreringsnummer och bilmärke

```bash
curl "http://localhost:8080/vehicle-brand-info"
```

Exempelsvar:

```json
[{"registrationNumber":"ABC123","brand":"Saab"}]
```

## Anmärkningar

- `GET /customer-id` förutsätter att namnet är unikt. Om flera kunder har samma namn uppstår ett fel.
- I `curl`-exemplen på Windows (cmd) fungerar citattecken runt URL:en på samma sätt. Utan citattecken kan tecknet `&` tolkas av skalet.
