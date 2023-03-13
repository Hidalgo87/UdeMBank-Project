class ClienteInexistenteError extends Exception {
    // Define el constructor y llama al de la clase madre
    public ClienteInexistenteError() {
      super();
      System.out.println("El cliente con ese ID no existe, vuelva a intentarlo");
    }
    
    // Sobrecarga del constructor con versión con mensaje
    public ClienteInexistenteError(String message) {
      super(message);
      System.out.println(message);
    }
  }

  class IdExistenteError extends Exception{
    public IdExistenteError(String message){
      super(message);
      System.out.println(message);
    }
  }
  class SaldoInsuficiente extends Exception{
    public SaldoInsuficiente(String message){
      super(message);
      System.out.println(message);
    }
  }

  class UsuarioNoEncontradoError extends Exception{
    public UsuarioNoEncontradoError(String message){
      super(message);
      System.out.println(message);
    }
  }

  class TextoIngresadoError extends Exception{
    public TextoIngresadoError(String message){
      super(message);
      System.out.println(message);
    }
    
      // si lo desea, puede agregar métodos adicionales aquí   
  }
    
  