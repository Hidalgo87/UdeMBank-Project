import java.util.Scanner;


import java.util.List;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class Gestion_Financiera {
    Bank banco;
    
    public Gestion_Financiera(Bank banco_actual){
        this.banco = banco_actual;
    }
    //RETIRAR DINERO DESDE ATM
    public void retirar_dinero_atm(Usuario cliente){
        int cantidad = cantidad_solicitada();
        if(cliente.get_balance() >= cantidad){
            List<Integer> cajeros = cajeros_disponibles(cantidad);
            int id_cajero = seleccionar_cajero(cajeros);//Verificar que cajeros tienen ese dinero
            ATM cajero_actual = banco.query_atm(id_cajero);

            //Proceso de resta de saldos
            try{
            int new_balance = cliente.withdraw_balance(cantidad); //Le restamos el monto + comision al usuario y retornamos el neuvo balance
            String nuevo_balance = Integer.toString(new_balance);//Lo convertimos a String porque el metodo de abajo lo debe recibir asi
            //Ahora actualizamos el balance del cliente en el archivo TXT
            banco.manejador_archivo.modificar_archivo(2, cliente.get_id(), nuevo_balance);
            }catch(SaldoInsuficiente e){
                //Para el caso de que el usuario no le alcance a pagar la comisión
                retirar_dinero_atm(cliente);
            }
            
            //Finalmente actualizamos el saldo del cajero
            banco.balance_banco = banco.balance_banco - cantidad;//Pero antes, tambien debemos restarle ese dinero al banco
            //System.out.println("DINERO DEL BANCO: "+banco.balance_banco);
            int new_balance_atm = cajero_actual.withdraw_atm(cantidad);
            String nuevo_balance_atm = Integer.toString(new_balance_atm);
            banco.manejadorArchivoATM.modificar_archivo_atm(1, id_cajero, nuevo_balance_atm);
            

        }else{
            System.out.println("No tienes suficiente dinero para realizar una operación con ese monto");
            retirar_dinero_atm(cliente);
        } 
    }

    private int cantidad_solicitada(){
        Scanner input = new Scanner(System.in);
        int cantidad = 0;
        while (true) {
            System.out.println("Ingrese la cantidad de dinero que solicita para su movimiento");
            try{
                cantidad = Integer.parseInt(input.nextLine());
                return cantidad;
            }
            catch(NumberFormatException e){
                System.out.println("El valor que ingresó como monto es invalido, intentelo de nuevo");
            }
        }
    }

    private List<Integer> cajeros_disponibles(int cantidad_solicitada){
        List<Integer> cajeros = new ArrayList<Integer>();

        System.out.println("ID de cajeros con suficiente dinero para su solicitud");
        for(ATM atm_actual : banco.atm_list){
            if(atm_actual.get_balance() >= cantidad_solicitada){
                System.out.println(atm_actual.get_id());
                cajeros.add(atm_actual.get_id());
            }
        }
        return cajeros;
    }
    private int seleccionar_cajero(List<Integer> cajeros){
        Scanner input_opcion = new Scanner(System.in);
        System.out.println("Ingrese el ID del cajero que quiere utilizar");
        int opcion = input_opcion.nextInt();
        //Falta excepcion por si mete strings

        for(Integer id_atm_actual : cajeros){
            if(id_atm_actual == opcion){
                System.out.println("CAJERO ACTUAL: " +id_atm_actual);
                return id_atm_actual;
            }
        }
        System.out.println("El ID que ingreśo es invalido, vuelva a intentarlo");
        return seleccionar_cajero(cajeros);
    }
    
    //RETIRAR DINERO DESDE SUCURSAL
    public void retirar_dinero_sucursal(Usuario cliente){
        int cantidad = cantidad_solicitada();
        if(cliente.get_balance() >= cantidad){
        try{
            int new_balance = cliente.withdraw_balance(cantidad); 
            String nuevo_balance = Integer.toString(new_balance);
            banco.manejador_archivo.modificar_archivo(2, cliente.get_id(), nuevo_balance);
            }catch(SaldoInsuficiente e){
                retirar_dinero_atm(cliente);
            }
            //Ahora procedemos a quitarle el dinero a la sucursal virtual (balance total del banco)
            banco.balance_banco = banco.balance_banco - cantidad;
            System.out.println("Nuevo saldo sucursal virtual: "+banco.balance_banco);
        }
    }
    //DEPOSITAR DINERO DESDE UN ATM
    public void depositar_dinero_atm(Usuario cliente){
        int cantidad = cantidad_solicitada();
        System.out.println("La lista de cajeros es la siguiente");
        banco.imprimir_lista_atm();
        System.out.println("Ingrese el ID del cajero que desea seleccionar para depositar");
        Scanner sc = new Scanner(System.in);
        String id = sc.nextLine();
        int id_cajero = 0;
        try{
            id_cajero = Integer.parseInt(id);
        }catch(InputMismatchException e){
            System.out.println("Asegurese de ingresar un ID correcto (número)");
            depositar_dinero_atm(cliente);
        }
        //Añadimos el deposito hecho por el cliente, a el cajero
        ATM cajero = banco.query_atm(id_cajero);
        int nuevo_saldo_cajero = cajero.get_balance() + cantidad;
        cajero.update_balance(nuevo_saldo_cajero);
        String nuevo_balance_cajero = Integer.toString(nuevo_saldo_cajero);
        banco.manejadorArchivoATM.modificar_archivo_atm(1, cajero.get_id(), nuevo_balance_cajero);
        banco.balance_banco = banco.balance_banco + cantidad;
        
        //Actualizamos el balance del cliente tanto del objeto como del txt
        int nuevo_saldo_cliente = cliente.get_balance() + cantidad;
        cliente.update_balance(nuevo_saldo_cliente);
        System.out.println("Su nuevo saldo ahora es: "+cliente.get_balance());
        String nuevo_balance_cliente = Integer.toString(nuevo_saldo_cliente);
        banco.manejador_archivo.modificar_archivo(2, cliente.get_id(), nuevo_balance_cliente);

    }

    //DEPOSITAR DINERO DESDE UNA SUCURSAL VIRTUAL
    public void depositar_dinero_sucursal(Usuario cliente){
        int cantidad = cantidad_solicitada();
        int nuevo_saldo_cliente = cliente.get_balance() + cantidad;
        cliente.update_balance(nuevo_saldo_cliente);
        System.out.println("Su nuevo saldo ahora es: "+cliente.get_balance());
        String nuevo_balance_cliente = Integer.toString(nuevo_saldo_cliente);
        banco.manejador_archivo.modificar_archivo(2, cliente.get_id(), nuevo_balance_cliente);

        //Se lo sumamos a la sucursal virtual
        banco.balance_banco = banco.balance_banco + cantidad;
    }

    //TRANSFERIR DINERO A OTRO CLIENTE
    public void transferir_dinero(Usuario cliente){
        int cantidad = cantidad_solicitada();
        System.out.println("A continuación, ingrese el ID del usuario al que desea enviarle dinero");
        int id = banco.request_id(); //Le pedimos el ID del cliente al que le quiere transferir
        Usuario transfer_client = banco.query_client(id);
        int nuevo_balance = transfer_client.get_balance() + cantidad;
        

        //Ahora le quitamos el dinero que envió al cliente que hizo la transferencia
        try{
            int new_balance = cliente.withdraw_balance(cantidad); 
            String nuevo_balance_cliente = Integer.toString(new_balance);
            banco.manejador_archivo.modificar_archivo(2, cliente.get_id(), nuevo_balance_cliente);
            }catch(SaldoInsuficiente e){
                transferir_dinero(cliente);
            }
            transfer_client.update_balance(nuevo_balance);//A este punto, el otro cliente ya tendrá el dinero enviado
            String nuevo_balance_transfer = Integer.toString(nuevo_balance);
            banco.manejador_archivo.modificar_archivo(2, transfer_client.get_id(), nuevo_balance_transfer);
            System.out.println("Transferencia realizada con exito! ");
    }
}
