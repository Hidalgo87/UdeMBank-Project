public class Platinum extends Usuario{
    private double comision = 0.005; //Comision del 0.5%

    public Platinum(int id, int balance, String password){ super(id, balance, password);
    }

    public double get_comision(){
        return this.comision;

    }
}
    
