public class CommandNotFoundException extends RuntimeException {
   public CommandNotFoundException() {
      super("Command Not Found");
   }

   public CommandNotFoundException(String message) {
      super(message);
   }
}
