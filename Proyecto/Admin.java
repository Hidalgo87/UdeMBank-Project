import java.util.Scanner;

public class Admin extends Usuario {
    
    public Admin(int id, int balance, String password){ super(id, balance, password);
    }

    public void menu_modificacion(Usuario cliente){
        System.out.println("=========================");
        System.out.println("Modificación de clientes");
        System.out.println("Actualmente está modificando al cliente con ID: "+ cliente.get_id());
        
        System.out.println("Seleccioné el dato que desea modificar");
        System.out.println("1. Modificar numero del ID de un cliente");
        System.out.println("2. Modificar contraseña de un cliente");
        System.out.println("3. Modificar balance de un cliente");
        System.out.println("4. Modificar tipo de cliente (Regular - Platino)");
        seleccionar_opcion(cliente);
    }
    public void seleccionar_opcion(Usuario cliente){
        Scanner input_opcion = new Scanner(System.in);
        Scanner input_new_info = new Scanner(System.in);
        String opcion = input_opcion.nextLine();

        if(opcion.equals("1")){
            System.out.println("Ingrese el nuevo ID del cliente");
            String new_info = input_new_info.nextLine();
            modificar_id_cliente(new_info, cliente);
        }
        if(opcion.equals("2")){
            System.out.println("Ingrese la nueva contraseña del cliente");
            String new_info = input_new_info.nextLine();
            modificar_contraseña_cliente(new_info, cliente);
        }
        if(opcion.equals("3")){
            System.out.println("El balance actual del cliente es " + cliente.get_balance());
            System.out.println("Ingrese el nuevo balance del cliente");
            String new_info = input_new_info.nextLine();
            modificar_balance_cliente(new_info, cliente);
        }
        if(opcion.equals("4")){
            System.out.println("El tipo actual del cliente es " + cliente.getClass().getSimpleName());
            System.out.println("Ingrese el nuevo tipo del cliente (Regular o Platino)");
            String new_info = input_new_info.nextLine().toLowerCase();
            modificar_tipo_cliente(new_info, cliente);
        }
    }


   private void modificar_id_cliente(String new_id, Usuario cliente){
    try{
        int new_id_int = Integer.parseInt(new_id);
        cliente.set_id(new_id_int);
        System.out.println("ID Actualizado correctamente, ahora es: " + cliente.get_id());
    }catch(NumberFormatException e){
        //Falta añadir para cuando se repiten los ID's
        System.out.println("El valor que ingresó como ID es invalido, intentelo de nuevo");
        modificar_id_cliente(new_id, cliente);
        }
    }
    private void modificar_contraseña_cliente(String new_password, Usuario cliente){
        cliente.set_password(new_password);
        System.out.println("La nueva contraseña ahora es "+cliente.get_password());
        //Excepcion por si la contraseña es igual
   }

    private void modificar_balance_cliente(String new_balance, Usuario cliente){
        try{
            int new_balance_int = Integer.parseInt(new_balance);
            cliente.set_balance(new_balance_int);
            System.out.println("Balance actualizado correctamente, ahora es: " + cliente.get_balance());
        }catch(NumberFormatException e){
            //Falta añadir para cuando se repiten los ID's
            System.out.println("El valor que ingresó como balance es invalido, intentelo de nuevo");
            Scanner nb_rigth = new Scanner(System.in);
            String new_balance_rigth = nb_rigth.nextLine();
            modificar_balance_cliente(new_balance_rigth, cliente);
            }
   }
   private Usuario modificar_tipo_cliente(String new_type, Usuario cliente){//FALTA ORGANIZAR
    if(new_type.equals("regular")){
        int old_id = cliente.get_id();
        int old_balance = cliente.get_balance();
        String old_password = cliente.get_password();
        cliente = null; //Referencia de memoria vacia, el garbage collector lo borrá
        Regular new_client = new Regular(old_id, old_balance, old_password);
        System.out.println("El tipo del cliente ahora es "+ new_client.getClass().getSimpleName());
        return new_client;
    }
    if(new_type.equals("platino")){
        int old_id = cliente.get_id();
        int old_balance = cliente.get_balance();
        String old_password = cliente.get_password();
        cliente = null; //Referencia de memoria vacia, el garbage collector lo borrá
        Platinum new_client = new Platinum(old_id, old_balance, old_password);
        System.out.println("El tipo del cliente ahora es "+ new_client.getClass().getSimpleName());
        return new_client;
    }
    return null;
    }
}
