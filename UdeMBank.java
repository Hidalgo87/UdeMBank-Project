import java.util.Scanner;
import java.util.ArrayList;
//import java.util.InputMismatchException;
import java.util.List;
class Bank
{
    List<Usuario> client_list = new ArrayList<Usuario>();
    List<ATM> atm_list = new ArrayList<ATM>();

    public Bank()
    {
        define_elements();
    }
    public void Run(Bank bank){
    int id_ingresada = bank.request_id();
    String password_ingresada = request_password();
    if(id_ingresada == 0 && password_ingresada.equals("admin123")){ //La comparacion debe hacerse con el id y contraseña del admin que está almacenado en el TXT
      menu_administrador();
    }
    else bank.menu_cliente(); //Llamamos al metodo Menu que imprime un mensaje de bienvenida y las diferentes opciones
    int respuesta = bank.input_option();//Guardamos el retorno del llamado al metodo input opcion que le pide una opcion al usuario
    if (respuesta == 1)
    {
      ;
    }
      //bank.saldo_atm();
      //bank.saldo_cliente(id_cliente);
      //bank.retirar_dinero(id_cliente);
    //}
  }
    //llamamos al metodo add_client y add_atm que se encarga de instaciar los objetos de cliente y atm
    void define_elements()
    {
      add_client(1, 10000, "regular"); //Atributos id y balance para cliente
      add_client(2, 20000, "platino");
      add_atm(1, 12000); //Atributos de id y balance para cliente
    }

    //Metodo para crear objeto de tipo cliente y añadirlo a la lista de clientes
    void add_client(int id, int balance, String client_type)
    {
      Usuario client = null;
      if(client_type == "regular"){
        Regular regular = new Regular(1, 10000, "pass123");
        client = regular;
      }

      else if(client_type == "platino"){
        Platinum platino = new Platinum(2, 12000, "password24");
        client = platino;
      }
      //Client cliente = new Client(id, balance);
      client_list.add(client);
    }
    //Metodo para crear objeto de tipo ATM y añadirlo a la lista de clientes
    void add_atm(int id, int balance)
    {
      ATM atm = new ATM(id, balance);
      atm_list.add(atm);
    }
    
  
  public int request_id()
    {
        Scanner input_id = new Scanner(System.in);
        System.out.println("Ingrese su ID: ");
        int id = input_id.nextInt();
        //input_id.close();
        return id;
    }
    public String request_password()
    {
        Scanner input_password = new Scanner(System.in);
        System.out.println("Ingrese su contraseña: ");
        String password = input_password.nextLine();
        return password;
    }
        //Menu de bienvenida
        public void menu_cliente()
        {
            System.out.println("Bienvenido al banco UdeM, aquí están las opciones");
            System.out.println("1. Retirar dinero desde un ATM");
            System.out.println("2. Retirar dinero via sucursal virtual");
            System.out.println("3. Depositar desde un ATM");
            System.out.println("4. Depositar dinero via sucursal virtual");
            System.out.println("5. Transferir dinero a otro cliente");
        }
    
        //Ingreso de la opcion del usuario, 1 para retirar dinero
        public int input_option()
        {
         //try {
          Scanner opcion_menu = new Scanner(System.in);
            int respuesta = opcion_menu.nextInt();
    
            return respuesta;
        }
        
    public void menu_administrador(){
      System.out.println("Bienvenido administrador del banco UdeM");
      System.out.println("Escoja una de las siguientes opciones");
      System.out.println("1. Ir al menú de clientes");
      System.out.println("2. Modificar datos un cliente");
      System.out.println("3. Agregar un nuevo cliente");
      System.out.println("4. Eliminar un cliente");
        }
}
    /* 
    //Metodo para verificar que el id ingresador por el cliente se encuentre en la lista de clientes
    boolean verify_client(int client_id) {
      for (Client cliente_evaluado : client_list) { //cilo Para cada objeto de tipo cliente en la lista de clientes
        int cliente_evaluado_id = cliente_evaluado.get_id(); //guardamos el ID del cliente en la variable cliente_evaluado_id
        if (client_id == cliente_evaluado_id) {//Si la id que ingresó el cliente es igual a la ID que se encuentra en la lista de clientes, retorna true
          return true;
        }
      }
      return false;
    }

    ATM query_atm(int id_atm) {
      for (ATM ATM_evaluado : atm_list) { //Realizamos el mismo proceso para verificar que un ATM se encuentre registrado
        if (ATM_evaluado.get_id() == id_atm) {
          return ATM_evaluado;
        }
      }
      return null;
    }
    //Metodo para verificar que el balance del ATM sea mayor o igual a la solicitada por el usuario
    Boolean verify_atm_balance(int id_atm, int cantidad)
    {
        ATM atm = query_atm(id_atm); //Variable de tipo ATM
        if (atm.get_balance() <= cantidad)
        {
            System.out.println("No hay suficiente dinero en el cajero");
            return false;
        }
        else
        {
            return true;
        }
    }
    //Metodo para verificar que el cliente exista y 
    public void retirar_dinero(int id)
    {
        try
        {
            if (verify_client(id))
            {
              int amount = request_amount();
              Client cliente = query_client(id);
              if(cliente.check_balance_client(amount))
              {
                if (atm_list.get(0).check_balance_atm(amount)) //Las listas no tienen la propiedad de indice accesible por '[]', por lo tanto se llama al metodo get() y su indice
                {
                  atm_list.get(0).withdraw_atm(amount);
                  cliente.withdraw_client(amount);
                }
                else
                {
                  //ERROR NO HAY DINERO EN CAJERO
                  System.out.println("No hay suficiente dinero en el cajero. Intente de nuevo con menos cantidad");
                  retirar_dinero(id);
                }
              }
              else
              {
                System.out.println("No tienes el suficiente dinero. Intenta con menos");
                retirar_dinero(id);
              }
            }
            else
            {
                //ERROR cliente no existe 
                throw new ClienteInexistenteError("No se encontró el cliente");
            }
        }
        catch (ClienteInexistenteError e)
        {
            System.out.println("Por favor Ingrese un ID válido");
            retirar_dinero(id);
        }
    }


    public Client query_client(int id_ingresado) {
      for (Client cliente : client_list) {
        if (cliente.get_id() == id_ingresado) {
          return cliente;
        }
      }
      return null;
    }

    public void saldo_cliente(int id){
      Client cliente = query_client(id);
      System.out.println("Bienvenido persona con id " +id + " Tu saldo actual es "+ cliente.get_balance());
    }
    public void saldo_atm(){
      ATM current_atm = atm_list.get(0);
      System.out.println("El saldo actual del cajero es "+ current_atm.get_balance());
    }

    
    int request_amount()
    {
        Scanner input_amount = new Scanner(System.in);
        System.out.println("Ingrese la cantidad de dinero que desea retirar: ");
        int cantidad = input_amount.nextInt();
        //input_amount.close();
        return cantidad; //Cantidad que requiere retirar el usuario
    }
}
/* */