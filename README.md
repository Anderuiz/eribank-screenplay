# EriBank – Automatización móvil con Screenplay

Automatización del **happy path** de **Make Payment** y **Mortgage Request** en la app de pruebas **EriBank** (Android), usando el patrón **Screenplay** con Serenity BDD, Cucumber y Appium.

## 1. Stack

| Herramienta | Versión | Uso |
|---|---|---|
| Java | 17 | Lenguaje |
| Gradle (wrapper incluido) | 8.10.2 | Build y ejecución (`./gradlew`) |
| Serenity BDD (Screenplay) | 4.2.1 | Patrón Screenplay y reportes |
| Cucumber | 7.18 | Escenarios en Gherkin (español) |
| Appium Java Client | 9.3.0 | Automatización móvil |
| Appium Server + UiAutomator2 | 3.8 / 8.7 (compatible con Appium 2.x) | Driver de Android |
| JUnit Platform Suite | 1.10 | Runner |
| JUnit 4 | 4.13.2 | Requerido por el reporter de Serenity (`SerenityReporterParallel`) |

Probado en un emulador Android (Pixel, API 37, ARM64) sobre macOS con Apple Silicon.

## 2. Estructura del proyecto

```
src/test/java/co/com/eribank
├── runners/            CucumberTestSuite: punto de entrada de las pruebas
├── stepdefinitions/    Traducen Gherkin a acciones del actor (Hooks, Login, MakePayment, MortgageRequest)
├── tasks/              Acciones de negocio: Login, MakePayment, SelectCountry, RequestMortgage,
│                       FillPersonalInformation, ChooseLoanConditions
├── interactions/       Acciones técnicas reutilizables: SelectOption (con scroll), HideKeyboard,
│                       DismissSystemDialogs (diálogos de Android para apps antiguas)
├── questions/          Lo que el actor verifica: CurrentBalance, HomeScreenIsDisplayed
├── userinterfaces/     Localizadores (Targets) por pantalla
├── models/             PaymentData, MortgageData (inmutables, patrón Builder)
├── exceptions/         Excepciones de negocio con mensajes claros
└── utils/              AppLocators, AppConfig, MemoryKeys, WaitTime
src/test/resources
├── features/           make_payment.feature, mortgage_request.feature
├── app/                (aquí va EriBank.apk – no se versiona)
└── serenity.conf       Configuración de Appium, credenciales y Serenity
```

## 3. Proceso realizado

1. **Análisis de la app**: se identificaron las pantallas (Login, Home, Make Payment, lista de países, Mortgage Request paso 1 y paso 2) y sus `resource-id` leyendo los layouts del propio APK; luego se verificaron en ejecución con el *page source* que Serenity guarda en cada paso.
2. **Escenarios en Gherkin** (en español) con estructura **AAA**:
   - **Arrange** → `Antecedentes`: el actor inicia sesión y recuerda su saldo inicial.
   - **Act** → `Cuando`: realiza el pago / la solicitud.
   - **Assert** → `Entonces`: valida el resultado con una `Question`.
3. **Make Payment**: un `Esquema del escenario` con 3 ejemplos (**Iceland, Bangladesh, Ethiopia**). La verificación compara el saldo final con `saldo inicial − monto`.
4. **Mortgage Request**: escenario con los datos pedidos: **Type of Loan = Car, Years = 10, Occupation = Private Job, Yearly Income = 20,00,000**. El valor 20,00,000 está en notación india (20 lakhs); la app lo muestra en formato occidental como `2,000,000`, que es el texto que se selecciona. Se valida que la app regrese a la pantalla principal tras guardar.
5. **Modelado Screenplay**: el actor (`Andres`) tiene la habilidad `BrowseTheWeb` (driver de Appium), ejecuta `Tasks` compuestas por `Interactions` y consulta `Questions`.

### Buenas prácticas aplicadas (valor adicional)

| Tema | Cómo se aplica |
|---|---|
| **Patrones de diseño** | Screenplay; **Builder** en `PaymentData`/`MortgageData`; **Factory methods** (`MakePayment.with(...)`, `SelectOption.withText(...)`); **Composite** (`RequestMortgage` compone `FillPersonalInformation` + `ChooseLoanConditions`). |
| **Clean Code** | Nombres expresivos, métodos cortos, sin números mágicos (`WaitTime`), sin strings repetidos (`AppLocators`), credenciales fuera del código (`serenity.conf`). |
| **SOLID** | **S**: cada clase hace una sola cosa (una pantalla, una tarea, una pregunta). **O**: nuevos flujos se agregan con nuevas Tasks sin modificar las existentes. **L/I**: todas las acciones cumplen las interfaces `Task`, `Interaction`, `Question`. **D**: los pasos dependen de abstracciones (`Performable`, `Question`), no del driver. |
| **FIRST** | **Fast**: esperas explícitas, sin `Thread.sleep`. **Independent**: la app se reinicia en cada escenario (`serenity.restart.browser.for.each = scenario`, `noReset = false`). **Repeatable**: datos controlados en el feature. **Self-validating**: cada escenario termina en una aserción. **Timely**: escritos junto con el análisis del flujo. |
| **Patrón AAA** | `Dado` (Arrange) / `Cuando` (Act) / `Entonces` (Assert). |
| **Manejo de excepciones** | `PaymentNotCompletedException` y `MortgageRequestNotSentException` vía `orComplainWith`; `OptionNotFoundException` cuando una opción no aparece ni con scroll; `BalanceFormatException` si el saldo no se puede leer; `HideKeyboard` tolera que el teclado ya esté cerrado; `DismissSystemDialogs` tolera que el diálogo desaparezca durante el clic (`StaleElementReferenceException`); el Builder valida montos ≤ 0. |

## 4. Cómo ejecutar la prueba

### Prerrequisitos

1. **JDK 17** y **Android SDK** (variables `JAVA_HOME` y `ANDROID_HOME`).
2. **Appium 2 o 3** y el driver UiAutomator2 (requiere Node.js):
   ```bash
   npm install -g appium
   appium driver install uiautomator2
   ```
3. Un **emulador** o dispositivo Android encendido (`adb devices` debe listarlo).
4. Descargar el APK oficial de EriBank e instalarlo en el emulador:
   ```bash
   curl -L -o src/test/resources/app/EriBank.apk https://experitest.s3.amazonaws.com/eribank.apk
   adb install -r --bypass-low-target-sdk-block src/test/resources/app/EriBank.apk
   ```
   EriBank es una app antigua (targetSdk 10). Desde Android 14 el sistema bloquea instalar apps tan viejas, por eso se usa `--bypass-low-target-sdk-block` y la app se instala antes de la prueba (Appium solo la abre con `appPackage`/`appActivity`).

### Configuración

Revisar `src/test/resources/serenity.conf`:
- `appium.deviceName`: el nombre que muestra `adb devices` (por defecto `emulator-5554`).
- `eribank.username` / `eribank.password`: credenciales de EriBank (`company` / `company`).

### Ejecución

```bash
# 1. Levantar Appium en otra terminal
appium

# 2. Ejecutar todas las pruebas
./gradlew clean test

# Solo Make Payment
./gradlew clean test -Dcucumber.filter.tags="@MakePayment"

# Solo Mortgage Request
./gradlew clean test -Dcucumber.filter.tags="@MortgageRequest"

# Otro dispositivo
./gradlew clean test -Dappium.deviceName="R58M123ABC"
```


### Reporte

Al terminar se genera el reporte de Serenity en `target/site/serenity/index.html`, con capturas de pantalla de cada acción.

## 5. Resultado de la ejecución

Ejecución completa con `./gradlew clean test` (12-10-2026):

| Escenario | Datos | Resultado |
|---|---|---|
| Make Payment | País **Iceland**, monto 10 | ✅ Pasó |
| Make Payment | País **Bangladesh**, monto 15 | ✅ Pasó |
| Make Payment | País **Ethiopia**, monto 20 | ✅ Pasó |
| Mortgage Request | Car · 10 años · Private Job · 20,00,000 | ✅ Pasó |

`BUILD SUCCESSFUL` — 4 de 4 escenarios exitosos. Evidencia (capturas de cada acción) en `target/site/serenity/index.html`.

## 6. Decisiones técnicas y hallazgos

Puntos que surgieron al ejecutar contra la app real y cómo se resolvieron:

- **App antigua (targetSdk 10):** Android 14+ bloquea su instalación normal, por eso se instala con `adb install --bypass-low-target-sdk-block` antes de la prueba y Appium solo la abre (`appPackage`/`appActivity`).
- **Diálogos del sistema:** al abrirla, Android muestra la revisión de permisos y el aviso *"built for an older version of Android"*; tras aceptar la revisión regresa al escritorio. La interacción `DismissSystemDialogs` acepta ambos y vuelve a traer la app al frente con `mobile: activateApp`.
- **Appium 2/3 (protocolo W3C):** las capacidades propias de Appium deben llevar el prefijo `appium:`. Serenity 4.2.1 lo agrega con `appium.process.desired.capabilities = true` y la lista `appium.additional.capabilities` (ver `serenity.conf`).
- **ID con error de tipeo en la app:** el botón de Mortgage es `mortageRequestButton` (sin "g"); el localizador usa el ID real.
- **Botón Save fuera de pantalla:** en el paso 2 de Mortgage el botón queda debajo de las listas; su localizador hace *scroll* hasta él (`UiScrollable`).
- **Formato del ingreso anual:** el reto pide `20,00,000` (notación india, 20 lakhs); la app muestra el mismo valor como `2,000,000`, que es lo que se selecciona.
- **Selección por lista:** cada opción del paso 2 se busca dentro de su propia lista (`typeOfLoanListView`, `numberOfYearsListView`, …) para evitar coincidencias de texto entre listas.

## 7. Otros escenarios que probaría

EriBank no tiene un catálogo de productos; su flujo de "compra" son los **pagos** y la **solicitud de crédito**. Sobre ese flujo probaría:

**Login**
- Credenciales inválidas → mensaje de error y no ingresa.
- Usuario o contraseña vacíos → botón Login no permite continuar.
- Logout → vuelve a Login y no se puede regresar con "atrás".

**Make Payment**
- Monto mayor al saldo disponible → el pago es rechazado y el saldo no cambia.
- Monto en 0, negativo o con decimales → validación del campo.
- Campos obligatorios vacíos (teléfono, nombre, monto, país) → `Send Payment` deshabilitado.
- Teléfono con letras o caracteres especiales.
- Escribir el país a mano vs. seleccionarlo de la lista.
- Cancelar en el diálogo de confirmación (`No`) → el saldo no cambia.
- Botón `Cancel` del formulario → regresa a Home sin cambios.
- Pagos consecutivos → el saldo se descuenta de forma acumulada.
- Pagar exactamente el saldo total → saldo queda en 0.
- Persistencia: el saldo se mantiene tras cerrar sesión y volver a entrar.

**Mortgage Request**
- Otras combinaciones de tipo de préstamo, años, ocupación e ingresos (tabla de ejemplos).
- Campos obligatorios vacíos en el paso 1 → no permite `Next`.
- Edad no numérica, menor de edad o fuera de rango.
- No seleccionar una opción en el paso 2 → no permite `Save`.
- Botón `Cancel`/atrás en cada paso → no se envía la solicitud.

**No funcionales**
- Rotación de pantalla en medio del formulario (no se pierden datos).
- App en segundo plano y regreso.
- Ejecución en distintas versiones de Android y tamaños de pantalla.
- Pérdida de conexión durante el envío.

## 8. Nota sobre localizadores

Los `resource-id` corresponden a la versión de EriBank `com.experitest.ExperiBank`. Si tu APK es distinto (por ejemplo, el monto es una barra deslizante en lugar de un campo de texto), verifica los IDs con **Appium Inspector** y ajústalos solo en `userinterfaces/` — el resto del código no cambia.
