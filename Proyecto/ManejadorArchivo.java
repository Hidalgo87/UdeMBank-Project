import java.io.*;


public class ManejadorArchivo {
    Bank bank;
    public ManejadorArchivo(Bank banco){
        this.bank = banco;
    }
    public Boolean verificar_password(int id, String password)
    {
        try {
            BufferedReader br = new BufferedReader(new FileReader("informacion.txt"));          
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
        BufferedWriter bw = new BufferedWriter(new FileWriter("informacion.txt", true)); // el true es para saber si tiene que agregar info O eliminar y sobreescribir
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
            BufferedReader br = new BufferedReader(new FileReader("informacion.txt"));          
            String content = br.readLine();
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
            FileReader fr = new FileReader(archivo);
            BufferedReader br = new BufferedReader(fr);
            FileWriter fw = new FileWriter(archivo_temp);
            BufferedWriter bw = new BufferedWriter(fw);
            String linea = br.readLine();
            while (linea != null) {
                String[] datos = linea.split(" ");
                if (datos[0].equals(Integer.toString(id_actual))) {
                    datos[index] = dato;
                }
                bw.write(datos[0] + " " + datos[1] + " " + datos[2] + " " + datos[3] + "\n");
                linea = br.readLine();
            }
            fr.close();
            br.close();
            bw.close();
            fw.close();
            archivo.delete();
            archivo_temp.renameTo(archivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
