class MainClass
{
  public static void main(String[] args)
  {
    Admin admin = new Admin(0, 10000, "admin123");
    Bank bank = new Bank();
    bank.Run(bank);
  }
}