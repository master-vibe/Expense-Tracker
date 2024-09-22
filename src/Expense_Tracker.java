public class Expense_Tracker {
   public static void main(String[] args) {
      try {
         if (args.length == 0) {
            throw new CommandNotFoundException("No command provided.");
         }

         Manager manager = new Manager();
         String command = args[0];

         switch (command) {
            case "add":
               if (args.length < 5) {
                  throw new CommandNotFoundException("Required values are not provided.");
               }
               String description = "";
               double amount = -1;
               String category = "AllInOneCategory";
               for (int i = 0; i < args.length; i++) {
                  String arg = args[i];
                  if (arg.startsWith("--")) {
                     switch (arg.substring(2)) {
                        case "description":
                           description = args[i + 1];
                           break;
                        case "amount":
                           amount = Double.parseDouble(args[i + 1]);
                           break;
                        case "category":
                           category = args[i + 1];
                           break;
                        default:
                           throw new CommandNotFoundException("Option not found");
                     }
                  }
               }
               if (!description.isBlank() && amount >= 0) {
                  manager.add(description, amount, category);
                  System.out.println("Expense added:");
                  System.out.println("Description: " + description);
                  System.out.println("Amount: " + amount);
                  System.out.println("Category: " + category);
               }
               break;
            case "list":
               manager.list();
               break;
            case "summery":
               if (args.length == 1) {
                  manager.summery();
               } else if (args.length >= 3 && args[1].equals("--month")) {
                  manager.summery(Integer.parseInt(args[2]));
               }else if (args.length >= 3 && args[1].equals("--category")) {
                  manager.summery(args[2]);
               } else {
                  throw new CommandNotFoundException("Required values or options not found.");
               }
               break;
            case "update":
               if (args.length < 5) {
                  throw new CommandNotFoundException("Required values are not provided.");
               }
               double updateAmount = -1;
               int id = 0;
               String updateDescription = "";
               for (int i = 0; i < args.length; i++) {
                  String arg = args[i];
                  if (arg.startsWith("--")) {
                     switch (arg.substring(2)) {
                        case "amount":
                           updateAmount = Double.parseDouble(args[i + 1]);
                           break;
                        case "id":
                           id = Integer.parseInt(args[i + 1]);
                           break;
                        case "description":
                           updateDescription = args[i + 1];
                           break;
                        default:
                           throw new CommandNotFoundException("Option not found");
                     }
                  }
               }
               manager.update(updateDescription, updateAmount, id);
               System.out.println("Updating entry:");
               System.out.println("ID: " + id);
               System.out.println("Description: " + updateDescription);
               System.out.println("Amount: " + updateAmount);
               break;
            case "delete":
               if (args.length < 3) {
                  throw new CommandNotFoundException("Required values are not provided.");
               }
               if (args[1].equals("--id")) {
                  int deleteId = Integer.parseInt(args[2]);
                  manager.delete(deleteId);
                  System.out.println("Entry with ID " + deleteId + " deleted.");
               } else {
                  throw new CommandNotFoundException("Option not found");
               }
               break;
            case "budget":
               if (args.length < 3) {
                  throw new CommandNotFoundException("Required values are not provided.");
               }
               double limit = -1;
               boolean isEnabled = false;
               for (int i = 0; i < args.length; i++) {
                  String arg = args[i];
                  if (arg.startsWith("--")) {
                     switch (arg.substring(2)) {
                        case "limit":
                           limit = Double.parseDouble(args[i + 1]);
                           break;
                        case "enable":
                           isEnabled = true;
                           break;
                        case "disable":
                           isEnabled = false;
                           break;
                        default:
                           throw new CommandNotFoundException("Option not found");
                     }
                  }
               }
               manager.budget(limit, isEnabled);
               System.out.println("Budget limit set to " + limit + " and budget tracking is "
                     + (isEnabled ? "enabled" : "disabled") + ".");
               break;
            default:
               throw new CommandNotFoundException("Command not recognized.");
         }
      } catch (CommandNotFoundException e) {
         System.out.println("Error: " + e.getMessage());
         help();
      } catch (Exception e) {
         System.out.println("An unexpected error occurred: " + e.getMessage());
         help();
      }
   }

   static void help() {
      System.out.println("Usage:");
      System.out.println("  add --description <desc> --amount <amt> [--category <cat>]");
      System.out.println("  list");
      System.out.println("  summery [--month <mm>]");
      System.out.println("  update --id <id> [--description <desc>] [--amount <amt>]");
      System.out.println("  delete --id <id>");
      System.out.println("  budget --limit <amt> [--enable | --disable]");
   }
}
