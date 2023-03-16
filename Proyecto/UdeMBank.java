import java.util.Scanner;

import java.util.ArrayList;
import java.util.InputMismatchException;
//import java.util.InputMismatchException;
import java.util.List;
class Bank
{
    int balance_banco = 500000;
    List<Usuario> client_list = new ArrayList<Usuario>();
    List<ATM> atm_list = new ArrayList<ATM>();
    ManejadorArchivoUsuarios manejador_archivo = new ManejadorArchivoUsuarios(this);
    ManejadorArchivoATM manejadorArchivoATM = new ManejadorArchivoATM(this);
    Admin admin = new Admin(0, 100000, "240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9", this);
    Gestion_Financiera gestor_finanza = new Gestion_Financiera(this);

    public Bank()
    {
      manejadorArchivoATM.generar_lista_atm();
      manejador_archivo.generar_lista_usuarios();
      client_list.add(admin);
    }
    public void Run(){
    int id_ingresada = request_id(); 
    String password_ingresada = request_password();
    String password = PasswordEncrypter.encrypt(password_ingresada);//Para validar las contraseñas primero guardamos la contraseña ingresada por el usuario
    //Luego la encriptamos y la comparamos con las contraseñas almacenadas en el TXT, de tal forma que se comparan contraseñas encriptadas

    if(id_ingresada == 0 && password.equals("240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9")){
      menu_administrador(); //Imprime el menu de opciones para el administrador
      int respuesta = input_option();
      opcion_admin(respuesta);
    }

    //CASO PARA EL CLIENTE NORMAL
    else if(manejador_archivo.verificar_password(id_ingresada, password)){ // es un cliente del banco
      menu_cliente();
      int respuesta = input_option();
      opcion_usuario(respuesta, id_ingresada);
    }
    else{
      try {
        throw new UsuarioNoEncontradoError("No se encontró un usuario registrado con esas credenciales");
      } catch (UsuarioNoEncontradoError e) {
        Run(); // volver a solicitar las credenciales
      }
    }
  }

    void add_atm(int id, int balance)
    {
      ATM atm = new ATM(id, balance);
      atm_list.add(atm);
    }

    void add_client(int id, int balance, String password, String client_type)
    {
      Usuario client = null;
      if(client_type.equals("regular")){
        Regular regular = new Regular(id, balance, password);
        client = regular;
      }

      else if(client_type.equals("platino")){
        Platino platino = new Platino(id, balance, password);
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
    public Boolean id_disponible_atm(int id)
    {
      for (ATM atm: atm_list){
        if(atm.get_id() == id){
          return false;
        }
      }
      return true;
    }
    public boolean balance_disponible(int balance){
      if(balance <= balance_banco){
        balance_banco = balance_banco - balance;
        System.out.println("Nuevo balance del banco: " +balance_banco);
        return true;
      }
    return false;  
    }

  public int request_id()
    {
      try{
        Scanner input_id = new Scanner(System.in);
        System.out.println("Ingrese el ID: ");
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
            System.out.println("==================================================");
            System.out.println("Bienvenido al banco UdeM, aquí están las opciones");
            System.out.println("1. Retirar dinero desde un ATM");
            System.out.println("2. Retirar dinero via sucursal virtual");
            System.out.println("3. Depositar desde un ATM");
            System.out.println("4. Depositar dinero via sucursal virtual");
            System.out.println("5. Transferir dinero a otro cliente");
            System.out.println("6. Salir del programa");
        }
    
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
      System.out.println("DINERO TOTAL DEL BANCO: "+balance_banco);
      System.out.println("========================================");
      System.out.println("Bienvenido administrador del banco UdeM");
      System.out.println("Escoja una de las siguientes opciones");
      System.out.println("1. Ir al menú de clientes");
      System.out.println("2. Modificar datos un cliente");
      System.out.println("3. Agregar un nuevo cliente");
      System.out.println("4. Eliminar un cliente");
      System.out.println("5. Crear un ATM");
      System.out.println("6. Salir del programa");
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

      public ATM query_atm(int id_ingresado) {
          for (ATM atm_actual : atm_list) {
            if (atm_actual.get_id() == id_ingresado) {
              return atm_actual;
            }
          }return null;//Aqui no va a llegar ningun ID que no existe, entonces nunca retornará null
        }
      

    public void opcion_admin(int respuesta){
      if(respuesta == 1){
        menu_cliente();
        int respuesta_menu_c = input_option();
        opcion_usuario(respuesta_menu_c, 0);
      }
      else if(respuesta == 2){
        int id_cliente = request_id();
        Usuario cliente = query_client(id_cliente);
        admin.menu_modificacion(cliente);

      }else if(respuesta == 3){
        admin.solicitar_datos_nuevo_cliente();

      }else if(respuesta == 4){
        System.out.println("Lista actual antes "+client_list);
        int id_cliente = request_id();
        Usuario cliente = query_client(id_cliente);
        admin.eliminar_cliente(id_cliente, cliente);

      }else if(respuesta == 5){
        admin.crear_atm();
      }else if(respuesta == 6){
        System.out.println("Gracias por visitar el UdeMBank, vuelve pronto!");
        System.exit(0);
      }else{
        System.out.println("Por favor elija sólo una de las opciones mostradas");
      }
      menu_administrador(); //Cuando ejecute alguna opción se le muestra de nuevo las opciones
      int resp = input_option();
      opcion_admin(resp);
    }

    public void opcion_usuario(int respuesta, int id_usuario){
      Usuario cliente = query_client(id_usuario);
      if(respuesta == 1){
        gestor_finanza.retirar_dinero_atm(cliente);

      }else if(respuesta == 2){
        gestor_finanza.retirar_dinero_sucursal(cliente);

      }else if(respuesta == 3){
        gestor_finanza.depositar_dinero_atm(cliente);

      }else if(respuesta == 4){
        gestor_finanza.depositar_dinero_sucursal(cliente);
      }else if(respuesta == 5){
        gestor_finanza.transferir_dinero(cliente);
      }else if(respuesta ==6){
        System.out.println("Gracias por visitar el UdeMBank, vuelve pronto!");
        System.exit(0);
      }else{
        System.out.println("Por favor elija sólo una de las opciones mostradas");
      }
      menu_cliente();
      int resp = input_option();
      opcion_usuario(resp, id_usuario);
    }

    public void imprimir_lista(){
      System.out.println("Lista de clientes");
      for (Usuario cliente_i : client_list) {
        System.out.println(cliente_i);
      }
    }

    public void imprimir_lista_atm(){
      System.out.println("Lista de cajeros: ");
      for (ATM atm : atm_list) {
        System.out.println("ID: "+atm.get_id()+" Balance: "+ atm.get_balance());
      }
    }
      
    boolean verify_client(int client_id) {
      for (Usuario cliente_evaluado : client_list) { 
        int cliente_evaluado_id = cliente_evaluado.get_id(); 
        if (client_id == cliente_evaluado_id) {
          return true;
        }
      }
      return false;
    }
}