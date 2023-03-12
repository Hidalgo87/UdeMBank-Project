import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
public class Gestion_Financiera {
    Bank banco;
    public Gestion_Financiera(Bank banco_actual){
        this.banco = banco_actual;
    }
    public void retirar_dinero_atm(Usuario cliente){
        int cantidad = cantidad_solicitada();
        if(cliente.get_balance() >= cantidad){
            List<Integer> cajeros = cajeros_disponibles(cantidad);
            int id_cajero = seleccionar_cajero(cajeros);
            ATM cajero_actual = banco.query_atm(id_cajero);
            //Proceso de resta de saldos
            cliente.withdraw_client(cantidad);
            cajero_actual.withdraw_atm(cantidad);
            
            //banco.manejador_archivo.generar_lista_atm();
        }else{
            System.out.println("No tienes suficiente dinero para realizar una operación con ese monto");
            retirar_dinero_atm(cliente);
        }
        
        //Con la cantidad ya solicitada por el usuario, procedemos a mostrarle los ATM y restarle ese dinero al ATM y tambien
        //a restarle ese dinero al cliente pero quitandole la comision que tiene segun su tipo
        
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
                System.out.println("El valor que ingresó como ID es invalido, intentelo de nuevo");
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
        
    public void retirar_dinero_sucursal(){
        
    }
}
