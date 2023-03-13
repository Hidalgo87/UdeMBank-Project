class ATM
{
    int balance;
    int id;

    public ATM(int id_ingresado, int balance_ingresado)
    {
        this.id = id_ingresado;
        this.balance = balance_ingresado;
        //Probando commit desde Arch Linux
    }


    public int get_id()
    {
        return this.id;
    }

    public int get_balance()
    {
        return this.balance;
    }

    void update_balance(int new_balance)
    {
        balance = new_balance;
    }

    public Boolean check_balance_atm(int dinero_pedido)
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

    public int withdraw_atm(int wd_amount)
    {
        int nuevo_balance;
        nuevo_balance = this.balance - wd_amount;
        update_balance(nuevo_balance);//Actualizamos el balance del CAJERO
        
        System.out.println("Nuevo saldo ATM: " +this.balance);
        return this.balance;
    }
}

