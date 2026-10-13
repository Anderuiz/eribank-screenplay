# language: es
@MortgageRequest
Característica: Solicitar un crédito en EriBank
  Como cliente de EriBank
  Quiero solicitar un crédito desde la aplicación
  Para financiar la compra de un vehículo

  Antecedentes:
    Dado que Andres inició sesión en EriBank

  # Yearly Income: el reto pide 20,00,000 (notación india = 20 lakhs).
  # La app muestra el mismo valor en formato occidental: 2,000,000.
  @HappyPath
  Escenario: Solicitud exitosa de crédito para carro
    Cuando solicita un crédito con los siguientes datos
      | nombre | apellido | edad | direccion1  | direccion2 | pais     | tipoPrestamo | anios | ocupacion   | ingresoAnual |
      | Andres | Sabogal  | 30   | Calle 100 1 | Apto 101   | Colombia | Car          | 10    | Private Job | 2,000,000    |
    Entonces la solicitud se envía y regresa a la pantalla principal
