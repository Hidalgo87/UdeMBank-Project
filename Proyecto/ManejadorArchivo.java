import java.io.*;


public class ManejadorArchivo {
    public Boolean verificar_password(int id, String password)
    {
        try {
            BufferedReader br = new BufferedReader(new FileReader("informacion.txt"));
            String content = br.readLine();
            content = br.readLine(); //IGNORAMOS LA PRIMERA LINEA DEL TXT
            Boolean bool = false;
            while (content != null)
            {
                String[] datos = content.split(" ");
                if(Integer.parseInt(datos[0]) == id && datos[1].equals(password)){
                    bool = true;
                    System.out.println("true");
                    break;
                }
                else{
                    System.out.println("false");
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
    
    public void escribir_nuevo_usuario(int id, String password, int balance)
    {
        try{
        BufferedWriter bw = new BufferedWriter(new FileWriter("informacion.txt", true)); // el true es para saber si tiene que agregar info O eliminar y sobreescribir
        String cadena = String.format("%d %s %d",id, password, balance);
        bw.append(cadena + "\n");
        bw.flush();
        bw.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    
}
