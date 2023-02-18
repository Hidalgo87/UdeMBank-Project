class MainClass
{
  public static void main(String[] args)
  {
    Bank bank = new Bank();
    bank.welcome();
    int respuesta = bank.input_option();
    if (respuesta == 1)
    {
      int id_cliente = bank.request_id();
      bank.saldo_atm();
      bank.saldo_cliente(id_cliente);
      bank.retirar_dinero(id_cliente);
    }
    
    }
}