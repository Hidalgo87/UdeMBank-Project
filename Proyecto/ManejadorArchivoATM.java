import java.io.*;

public class ManejadorArchivoATM {
    Bank bank;
    public ManejadorArchivoATM(Bank banco){
        bank = banco;
    }

    public void escribir_nuevo_amt(int id, int balance)
    {
        try{
        BufferedWriter bw = new BufferedWriter(new FileWriter("lista_atm.txt", true)); // el true es para saber si tiene que agregar info O eliminar y sobreescribir
        String cadena = String.format("%d %s",id, balance);
        bw.append(cadena + "\n");
        bw.flush();
        bw.close();
        }
        catch (IOException e){
            System.out.println("Error al escribir en el archivo de ATM");
            e.printStackTrace();
        }
    }
    public void generar_lista_atm()
    {
        try {
            BufferedReader br = new BufferedReader(new FileReader("lista_atm.txt"));          
            String content = br.readLine();
            //Ignoramos la primera linea
            content = br.readLine();
            bank.atm_list.clear();//Con el objetivo de que no se duplique la lista si se hace mas de dos veces
            while (content != null)
            {
                String[] datos = content.split(" ");
                int id = Integer.parseInt(datos[0]);
                int balance = Integer.parseInt(datos[1]);
                bank.add_atm(id, balance);
                content = br.readLine();
            }
            br.close();
            restar_dineros_atm();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void restar_dineros_atm(){
        int balance_total_atm = 0;
            for(ATM atm_actual : bank.atm_list){
                balance_total_atm += atm_actual.get_balance();
            }
        bank.balance_banco = bank.balance_banco - balance_total_atm;
    }
    
    public void modificar_archivo_atm(int index, int id_actual, String dato) {
        try {
            File archivo = new File("lista_atm.txt");
            File archivo_temp = new File("lista_atm_temp.txt");
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            BufferedWriter bw = new BufferedWriter(new FileWriter(archivo_temp));
            String linea = br.readLine();
            while (linea != null) {
                String[] datos = linea.split(" ");
                if (datos[0].equals(Integer.toString(id_actual))) {
                    datos[index] = dato;
                }
                bw.write(datos[0] + " " + datos[1] + "\n");
                linea = br.readLine();
            }
            br.close();
            bw.close();
            archivo.delete();
            archivo_temp.renameTo(archivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
