import java.math.BigDecimal;
public class Platino extends Usuario{
    private BigDecimal comision = new BigDecimal("0.005"); //Comision del 0.5%

    public Platino(int id, int balance, String password){ super(id, balance, password);
    }

    public BigDecimal get_comision() {
        return this.comision;
    }
    @Override
    public int withdraw_balance(int wd_amount)throws SaldoInsuficiente{
        BigDecimal montoRetiro = new BigDecimal(wd_amount);
        BigDecimal montoComision = montoRetiro.multiply(comision);//El valor de la comision se multiplica con el monto, con el metodo Multiply
        BigDecimal nuevo_balance = new BigDecimal(this.get_balance()).subtract(montoRetiro.add(montoComision));//Se le resta el monto + comision al balance
        if(get_balance() <= montoRetiro.add(montoComision).intValue()){
            throw new SaldoInsuficiente("Oops... estás usando casi todo tu saldo y no te alcanza para pagar la comisión!");
            
        }else{
            update_balance(nuevo_balance.intValue());    
            System.out.println("Movimiento realizado con éxito!");
            System.out.println("Monto del retiro: " + montoRetiro+"$");
            System.out.println("Comisión aplicada: " + montoComision.intValue()+"$");
            System.out.println("Nuevo saldo: " + this.get_balance()+"$");
            return this.get_balance();
            }
        
    } 
}   
    
