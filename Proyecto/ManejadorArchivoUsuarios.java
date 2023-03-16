import java.io.*;

public class ManejadorArchivoUsuarios {
    Bank bank;
    public ManejadorArchivoUsuarios(Bank banco){
        this.bank = banco;
    }
    public Boolean verificar_password(int id, String password)
    {
        try {
            String archivo = "informacion.txt";
            BufferedReader br = new BufferedReader(new FileReader(archivo));          
            String content = br.readLine();
            Boolean bool = false;
            while (content != null)
            {
                String[] datos = content.split(" ");
                if(Integer.parseInt(datos[0]) == id && datos[1].equals(password)){
                    bool = true;
                    break;
                }
                else{
                    bool = false;
                }
                content = br.readLine();
            }
            br.close();
            return bool;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void escribir_nuevo_usuario(int id, String password, int balance, String type)
    {
        try{
        String archivo = "informacion.txt";
        BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true)); // el true es para saber si tiene que agregar info O eliminar y sobreescribir
        String cadena = String.format("%d %s %d %s",id, password, balance, type);
        bw.append(cadena + "\n");
        bw.flush();
        bw.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void generar_lista_usuarios()
    {
        try {
            String archivo = "informacion.txt";
            BufferedReader br = new BufferedReader(new FileReader(archivo));          
            String content = br.readLine();
            bank.client_list.clear();
            while (content != null)
            {
                String[] datos = content.split(" ");
                int id = Integer.parseInt(datos[0]);
                String password = datos[1];
                int balance = Integer.parseInt(datos[2]);
                String type = datos[3];
                bank.add_client(id, balance, password, type);
                content = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void modificar_archivo(int index, int id_actual, String dato) {
        try {
            File archivo = new File("informacion.txt");
            File archivo_temp = new File("informacion_temp.txt");
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            BufferedWriter bw = new BufferedWriter(new FileWriter(archivo_temp));
            String linea = br.readLine();
            while (linea != null) {
                String[] datos = linea.split(" ");
                if (datos[0].equals(Integer.toString(id_actual))) {
                    datos[index] = dato;
                }
                bw.write(datos[0] + " " + datos[1] + " " + datos[2] + " " + datos[3] + "\n");
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
    

    public void eliminar_cliente_archivo(int id_actual) {
        try {
            File archivo = new File("informacion.txt");
            File archivo_temp = new File("informacion_temp.txt");
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            BufferedWriter bw = new BufferedWriter(new FileWriter(archivo_temp));
            String linea = br.readLine();
            while (linea != null) {
                String[] datos = linea.split(" ");
                if (datos[0].equals(Integer.toString(id_actual))) {
                    ; // SI ES EL CLIENTE A ELIMINAR, NO LO TOMO
                }
                else{
                    bw.write(datos[0] + " " + datos[1] + " " + datos[2] + " " + datos[3] + "\n");   
                }
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
