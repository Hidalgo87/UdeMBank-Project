import java.util.Scanner;

import java.util.ArrayList;
import java.util.InputMismatchException;
//import java.util.InputMismatchException;
import java.util.List;
class Bank
{
    List<Usuario> client_list = new ArrayList<Usuario>();
    List<ATM> atm_list = new ArrayList<ATM>();
    ManejadorArchivo manejador_archivo = new ManejadorArchivo(this);
    Admin admin = new Admin(0, 10000, "240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9", manejador_archivo);;  

    public Bank()
    {
      define_atms();
      manejador_archivo.generar_lista_usuarios();
    }
    public void Run(Bank bank){
    int id_ingresada = request_id(); 
    String password_ingresada = request_password();
    String password = PasswordEncrypter.encrypt(password_ingresada);//Para validar las contraseñas primero guardamos la contraseña ingresada por el usuario
    //Luego la encriptamos y la comparamos con las contraseñas almacenadas en el TXT, de tal forma que se comparan contraseñas encriptadas

    if(id_ingresada == 0 && password.equals("240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9")){
      menu_administrador(); //Imprime el menu de opciones para el administrador
      int respuesta = input_option();
      opcion_admin(respuesta);
      //imprimir_lista();
    }
    else if(manejador_archivo.verificar_password(id_ingresada, password)){ // es un cliente del banco
      bank.menu_cliente();
    }
    else{
      try {
        throw new  UsuarioNoEncontradoError("No se encontró un usuario registrado con esas credenciales");
      } catch (UsuarioNoEncontradoError e) {
        Run(bank); // volver a solicitar las credenciales
      }
    }
    //int respuesta = bank.input_option();//Guardamos el retorno del llamado al metodo input opcion que le pide una opcion al usuario
      //bank.saldo_atm();
      //bank.saldo_cliente(id_cliente);
      //bank.retirar_dinero(id_cliente);
    //}
  }


    
    void define_atms()
    {
      add_atm(1, 12000); 
    }

    
    void add_client(int id, int balance, String password, String client_type)
    {
      /*
      try{
      if(!id_disponible(id)){
        throw new IdExistenteError("Ya existe un usuario con ese id " + id);
      }
    }
    catch (IdExistenteError e)
    {
      int newid = request_id();
      add_client(newid, balance, password, client_type);
    }
    /* */
      Usuario client = null;
      if(client_type.equals("regular")){
        Regular regular = new Regular(id, balance, password);
        client = regular;
      }

      else if(client_type.equals("platino")){
        Platinum platino = new Platinum(id, balance, password);
        client = platino;
      }
      client_list.add(client);
      
    }

    public Boolean id_disponible(int id)
    {
      for (Usuario usuario: client_list){
        if(usuario.get_id() == id){
          return false;
        }
      }
      return true;
    }
    //Metodo para crear objeto de tipo ATM y añadirlo a la lista de clientes
    void add_atm(int id, int balance)
    {
      ATM atm = new ATM(id, balance);
      atm_list.add(atm);
    }
    
  
  public int request_id()
    {
      try{
        Scanner input_id = new Scanner(System.in);
        System.out.println("Ingrese su ID: ");
        int id = input_id.nextInt();
        //input_id.close();
        return id;
      }
      catch (InputMismatchException e ){
        System.out.println("Ingrese solo numeros");
        return request_id();
      }
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
         try {
          Scanner opcion_menu = new Scanner(System.in);
            int respuesta = opcion_menu.nextInt();
            return respuesta;
         }
         catch (InputMismatchException e)
         {
          System.out.println("Ingrese solo numeros");
          return input_option();
         }
        }
        
    public void menu_administrador(){
      System.out.println("========================================");
      System.out.println("Bienvenido administrador del banco UdeM");
      System.out.println("Escoja una de las siguientes opciones");
      System.out.println("1. Ir al menú de clientes");
      System.out.println("2. Modificar datos un cliente");
      System.out.println("3. Agregar un nuevo cliente");
      System.out.println("4. Eliminar un cliente");
      System.out.println("5. Salir del programa");
        }

      public Usuario query_client(int id_ingresado) {
        try{
          for (Usuario cliente : client_list) {
            if (cliente.get_id() == id_ingresado) {
              return cliente;
            }
          }
          throw new ClienteInexistenteError("El cliente con el ID: "+id_ingresado+" no existe, vuelva a intentarlo"); //lanzar una excepcion que imprima un mensaje de error pero que no corte el flujo 
        }catch(ClienteInexistenteError e){
          id_ingresado = request_id();
          return query_client(id_ingresado);
        }
      }

    public void opcion_admin(int respuesta){
      if(respuesta == 1){
        menu_cliente();
      }
      else if(respuesta == 2){
        int id_cliente = request_id();
        Usuario cliente = query_client(id_cliente);
        admin.menu_modificacion(cliente);

      }else if(respuesta == 3){
        admin.solicitar_datos_ncliente();

      }else if(respuesta == 4){
        System.out.println("Lista actual antes "+client_list);
        int id_cliente = request_id();
        Usuario cliente = query_client(id_cliente);
        admin.eliminar_cliente(id_cliente, cliente);
      }else if(respuesta == 5){
        System.exit(0);
      }
      //imprimir_lista();
      menu_administrador(); //Cuando ejecute alguna opción se le muestra de nuevo las opciones
      int resp = input_option();
      opcion_admin(resp);
    }

    private void imprimir_lista(){
      System.out.println("Lista de clientes");
      for (Usuario cliente_i : client_list) {
        System.out.println(cliente_i);
    }
    }
      
    boolean verify_client(int client_id) {
      for (Usuario cliente_evaluado : client_list) { //cilo Para cada objeto de tipo cliente en la lista de clientes
        int cliente_evaluado_id = cliente_evaluado.get_id(); //guardamos el ID del cliente en la variable cliente_evaluado_id
        if (client_id == cliente_evaluado_id) {//Si la id que ingresó el cliente es igual a la ID que se encuentra en la lista de clientes, retorna true
          return true;
        }
      }
      return false;
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