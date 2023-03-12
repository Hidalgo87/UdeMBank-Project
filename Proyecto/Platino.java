import java.math.BigDecimal;
public class Platino extends Usuario{
    private BigDecimal comision = new BigDecimal("0.015"); //Comision del 0.5%

    public Platino(int id, int balance, String password){ super(id, balance, password);
    }

    public BigDecimal get_comision() {
        return this.comision;
    }
    @Override
    public void withdraw_client(int wd_amount) {
        BigDecimal montoRetiro = new BigDecimal(wd_amount);
        BigDecimal montoComision = montoRetiro.multiply(comision);//El valor de la comision se multiplica con el monto, con el metodo Multiply
        BigDecimal nuevo_balance = new BigDecimal(this.get_balance()).subtract(montoRetiro.add(montoComision));//Se le resta el monto + comision al balance
        update_balance(nuevo_balance.intValue());    
        System.out.println("Se realizó el retiro con éxito");
        System.out.println("Monto del retiro: " + montoRetiro);
        System.out.println("Comisión aplicada: " + montoComision);
        System.out.println("Nuevo saldo: " + this.get_balance());
    } 
}   
    
