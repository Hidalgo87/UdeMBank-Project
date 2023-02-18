public class Client
{
    int id;
    int balance;

    public Client(int id_ingresado, int balance_ingresado)
    {
        this.id = id_ingresado;
        this.balance = balance_ingresado;
    }

    //Añadir Setter
    public int get_id()
    {
        return this.id;
    }
    public int get_balance(){
        return this.balance;
    }

    void update_balance(int new_balance)
    {
      this.balance = new_balance;
    }

    public Boolean check_balance_client(int dinero_pedido)
    {
        if (this.balance < dinero_pedido)
        {
            return false;
        }
        else
        {
          return true;
        }
    }

  public void withdraw_client(int wd_amount)
  {
      int nuevo_balance;
      nuevo_balance = balance - wd_amount;
      update_balance(nuevo_balance);    
      System.out.println("Se realizó el retiro con exito");
      System.out.println("Nuevo saldo: " +this.balance);
  }

  }
