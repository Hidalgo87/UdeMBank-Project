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