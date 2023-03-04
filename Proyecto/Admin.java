import java.util.Scanner;

public class Admin extends Usuario {

    public Admin(int id, int balance, String password){ super(id, balance, password);
    }

    public Usuario modificar_cliente(Usuario cliente){
        System.out.println("=========================");
        System.out.println("Modificación de clientes");
        System.out.println("Actualmente está modificando al cliente con ID: "+ cliente.get_id());
        System.out.println("Si no desea modificar un atributo escriba 'n' o 'N'");
        System.out.println("Ingrese el nuevo ID del usuario");

        Scanner input_new_id = new Scanner(System.in);
        Scanner input_new_balance = new Scanner(System.in);
        Scanner input_new_password = new Scanner(System.in);
        Scanner input_new_type = new Scanner(System.in);

        String new_id = input_new_id.nextLine();
        if(new_id.equals("n") || new_id.equals("N")){
            System.out.println("Ingrese el nuevo balance del cliente");
            String new_balance = input_new_balance.nextLine();
            modificar_balance_cliente(new_balance, cliente); //Si el administrador no quiere cambiar el ID pasa directamente a cambiar el balance
        }
        else modificar_id_cliente(new_id, cliente);//Si el administrador ingresó un nuevo id lo mandaremos al metodo para que se modifiquey luego pasara al metodo para cambiar el balance
        //
        System.out.println("Ingrese el nuevo balance del cliente");
        String new_balance = input_new_balance.nextLine();
        if(new_balance.equals("n") || new_balance.equals("N")){
            
        }
        modificar_balance_cliente(new_balance, cliente);
        return cliente;

   }
   public void modificar_id_cliente(String new_id, Usuario cliente){
    try{
        int new_id_int = Integer.parseInt(new_id);
        cliente.set_id(new_id_int);
        System.out.println("ID Actualizado correctamente, ahora es: " + cliente.get_id());
    }catch(NumberFormatException e){
        //Falta añadir para cuando se repiten los ID's
        System.out.println("El nuevo ID que ingresó no es valido, intentelo de nuevo");
        modificar_balance_cliente(new_id, cliente);
        }
    }

    public void modificar_balance_cliente(String new_balance, Usuario cliente){
        System.out.println("Entraste a mod balance");
   }
}
