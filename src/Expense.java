import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Expense {
   private int id;
   private String description;
   private String category;
   private double expense;
   private String date;


   public Expense() {
   }


   public Expense(int var1, String var2, double var3, String var5) {
      this.id = var1;
      this.description = var2;
      this.category = var5;
      this.expense = var3;
      DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE;
      this.date = LocalDate.now().format(dateTimeFormatter);
   }

   public int getId() {
      return this.id;
   }

   public void setId(int var1) {
      this.id = var1;
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String var1) {
      this.description = var1;
   }

   public String getCategory() {
      return this.category;
   }

   public void setCategory(String var1) {
      this.category = var1;
   }

   public double getExpense() {
      return this.expense;
   }

   public void setExpense(double var1) {
      this.expense = var1;
   }

   public String getDate() {
      return this.date;
   }

   public void setDate(String date) {
      this.date =date;
   }

   @Override
   public String toString() {
      return "{" +
            "\"id\": \"" + getId() + "\"," +
            "\"description\": \"" + getDescription() + "\"," +
            "\"category\": \"" + getCategory() + "\"," +
            "\"expense\": \"" + getExpense() + "\"," +
            "\"date\": \"" + getDate() + "\"" +
            "}";
   }

}
