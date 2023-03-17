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

  public abstract int withdraw_balance(int wd_amount)throws SaldoInsuficiente
    ;
  }
