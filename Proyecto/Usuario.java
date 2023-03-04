public abstract class Usuario
{
    private int id;
    private int balance;
    private String password;

    public Usuario(int id_ingresado, int balance_ingresado, String password)
    {
        this.id = id_ingresado;
        this.balance = balance_ingresado;
        this.password = password;

    }
    //Añadir Setter
    public int get_id()
    {
        return this.id;
    }
    public int get_balance(){
        return this.balance;
    }
    public String get_password(){
        return this.password;
    }


    public int set_id(int new_id){
        this.id = new_id;
        return this.id;
    }
    public int set_balance(int new_balance){
        this.balance = new_balance;
        return this.balance;
    }
    public String set_password(String new_password){
        this.password = new_password;
        return this.password;
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
