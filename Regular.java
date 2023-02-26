public class Regular extends Usuario {
    private double comision = 0.015; //Comision del 1.5%

    public Regular(int id, int balance, String password){ super(id, balance, password);
    }

    public double get_comision(){
        return this.comision;
    }
}
