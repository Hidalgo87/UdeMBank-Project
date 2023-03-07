import java.util.InputMismatchException;
import java.util.Scanner;

public class Admin extends Usuario {
    ManejadorArchivo manejador_archivo;
    Bank bank;

    public Admin(int id, int balance, String password, ManejadorArchivo ma){ super(id, balance, password);
        manejador_archivo = ma;
        bank = manejador_archivo.bank;
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
        try{
        Scanner input_opcion = new Scanner(System.in);
        Scanner input_new_info = new Scanner(System.in);
        int opcion = input_opcion.nextInt();
        if(opcion == 1){
            System.out.println("Ingrese el nuevo ID del cliente");
            String new_info = input_new_info.nextLine();
            modificar_id_cliente(new_info, cliente);
        }
        if(opcion == 2){
            System.out.println("Ingrese la nueva contraseña del cliente");
            String new_info = input_new_info.nextLine();
            modificar_contraseña_cliente(new_info, cliente);
        }
        if(opcion == 3){
            System.out.println("El balance actual del cliente es " + cliente.get_balance());
            System.out.println("Ingrese el nuevo balance del cliente");
            String new_info = input_new_info.nextLine();
            modificar_balance_cliente(new_info, cliente);
        }
        if(opcion == 4){
            System.out.println("El tipo actual del cliente es " + cliente.getClass().getSimpleName());
            System.out.println("Ingrese el nuevo tipo del cliente (Regular o Platino)");
            String new_info = input_new_info.nextLine().toLowerCase();
            System.out.println("va a entrar");
            modificar_tipo_cliente(new_info, cliente);
        }
        }
        catch (InputMismatchException e){
            System.out.println("Los ID's solo tienen valores númericos, vuelva a intentarlo");
            seleccionar_opcion(cliente);
        }
    }

    private void modificar_id_cliente(String new_id, Usuario cliente){
    try{
        if(!bank.id_disponible(Integer.parseInt(new_id))){
            throw new IdExistenteError("Ese id no está disponible");
        }
        int new_id_int = Integer.parseInt(new_id);
        manejador_archivo.modificar_archivo(0, cliente.get_id(), new_id);
        cliente.set_id(new_id_int);
        System.out.println("ID Actualizado correctamente, ahora es: " + cliente.get_id());
    }catch(NumberFormatException e){
        //Falta añadir para cuando se repiten los ID's
        System.out.println("El valor que ingresó como ID es invalido, intentelo de nuevo");
        String right_id = String.valueOf(bank.request_id());
        modificar_id_cliente(right_id, cliente);
        }
        catch (IdExistenteError e){
        String right_id = String.valueOf(bank.request_id());
        modificar_id_cliente(right_id, cliente);  
        }
    }
    
    private void modificar_contraseña_cliente(String new_password, Usuario cliente){
        manejador_archivo.modificar_archivo(1, cliente.get_id(), new_password);
        cliente.set_password(new_password);
        System.out.println("La nueva contraseña ahora es "+cliente.get_password());
        //Excepcion por si la contraseña es igual
   }

    private void modificar_balance_cliente(String new_balance, Usuario cliente){
        try{
            manejador_archivo.modificar_archivo(2, cliente.get_id(), new_balance);
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
    
    private Usuario modificar_tipo_cliente(String new_type, Usuario cliente){// todo: FALTA ACTUALIZAR LA LISTA (con index obtenido se elimina el pasado y se usa add_client)
    if(new_type.equals("regular")){
        //Intentar cambiar su tipo con ayuda del txt, no del objeto
        manejador_archivo.modificar_archivo(3, cliente.get_id(), new_type);
        int old_id = cliente.get_id();
        int old_balance = cliente.get_balance();
        String old_password = cliente.get_password();
        //cliente = null; //Referencia de memoria vacia, el garbage collector lo borrá
        System.out.println("entró");
        cliente = bank.query_client(old_id);
        System.out.println("ea " + cliente.get_password());
        Regular new_client = new Regular(old_id, old_balance, old_password);
        System.out.println("El tipo del cliente ahora es "+ new_client.getClass().getSimpleName());
        return new_client;
    }
    if(new_type.equals("platino")){
        manejador_archivo.modificar_archivo(3, cliente.get_id(), new_type);
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

    public void solicitar_datos_ncliente(){
        //Primero pedimos los datos del nuevo cliente
            Scanner input = new Scanner(System.in);
            int id = 0;
            while (true) {
                System.out.println("Ingrese el ID del nuevo cliente");
                try{
                    id = Integer.parseInt(input.nextLine());
                    if(bank.id_disponible(id) && id !=0){
                        break;
                    }
                    else{
                        System.out.println("El ID que ingresó ya existe, vuelva a intentarlo");//OJO: NO ESTOY APLICANDO LA EXCEPCION
                    }
                }catch(NumberFormatException e){
                    System.out.println("El valor que ingresó como ID es invalido, intentelo de nuevo");
                }
            }
        
            int balance = 0;
            while (true) {
                System.out.println("Ingrese el balance del nuevo cliente");
                try{
                    balance = Integer.parseInt(input.nextLine());
                    break;
                }catch(NumberFormatException e){
                    System.out.println("El valor que ingresó como balance es invalido, intentelo de nuevo");
                }
            }
        
            System.out.println("Ingrese la contraseña del nuevo cliente");
            String password = input.nextLine();
            //Llamamos al metodo que la encripta y le mandamos "password" 
            System.out.println("Ingrese el tipo del nuevo cliente (Regular o Platino)");
            String type = input.nextLine();
            System.out.println("Está creando un nuevo cliente con los siguientes datos");
            System.out.println("ID: "+id + "- Balance: " + balance + "- Contraseña: " + password+ "- Tipo: " + type);
            bank.add_client(id, balance, password, type);
            manejador_archivo.escribir_nuevo_usuario(id,password, balance, type);
            
        }
    
    public void eliminar_cliente(int id){
        Usuario cliente = bank.query_client(id);
        
    }
}
