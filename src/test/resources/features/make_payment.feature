# language: es
@MakePayment
Característica: Realizar pagos en EriBank
  Como cliente de EriBank
  Quiero enviar pagos a personas de distintos países
  Para transferir dinero de forma rápida

  Antecedentes:
    Dado que Andres inició sesión en EriBank

  @HappyPath
  Esquema del escenario: Pago exitoso hacia <pais>
    Cuando realiza un pago de <monto> a "<nombre>" con teléfono "<telefono>" hacia "<pais>"
    Entonces su saldo se descuenta con el valor del pago

    Ejemplos:
      | pais       | monto | nombre         | telefono   |
      | Iceland    | 10    | Bjorn Olafsson | 3001234567 |
      | Bangladesh | 15    | Rahim Uddin    | 3007654321 |
      | Ethiopia   | 20    | Abebe Bikila   | 3009876543 |
