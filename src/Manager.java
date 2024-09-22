import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

public class Manager {
    private static String filePath = "lib/data.json";
    private static int lastId = 0;
    private static int width = 0;
    private static double budgetLimit = Double.MAX_VALUE;
    private static boolean budgetEnabled = false;
    private static List<Expense> expenselist;

    public Manager() throws StreamReadException, DatabindException, IOException {
        Path fileP = Paths.get(filePath);
        if (!Files.exists(fileP)) {
            if (Files.notExists(Path.of("lib"))) {
                Files.createDirectory(Path.of("lib"));
            }
            fileP = Files.createFile(fileP);
        }
        expenselist = read();
    }

    public void add(String description, double amount, String category) throws IOException {
        expenselist.add(new Expense(++lastId, description, amount, category));
        write();
    }

    public void list() {
        if (expenselist.isEmpty()) {
            System.out.println("There is no expense registered.");
            return;
        }
        System.out.printf("%-5s%-15s%-" + (width + 5) + "s%-14s\n", "ID", "Date", "Description", "Amount");

        expenselist.stream().forEach(t -> System.out.printf("%-5d%-15s%-" + (width + 5) + "s%-14.2f\n",
                t.getId(),
                t.getDate(),
                t.getDescription(),
                t.getExpense()));
    }

    public void summery() {
        if (expenselist.isEmpty()) {
            System.out.println("There is no expense registered.");
            return;
        }
        double totalExpense = expenselist.stream().mapToDouble(Expense::getExpense).sum();
        System.out.println("Total expenses: $" + totalExpense);
    }

    public void summery(int month) {
        if (expenselist.isEmpty()) {
            System.out.println("There is no expense registered.");
            return;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE;
        LocalDate now = LocalDate.now();
        double totalExpense = expenselist.stream()
                .filter(t -> LocalDate.parse(t.getDate(), dateTimeFormatter)
                        .equals(LocalDate.of(now.getYear(), month, now.getDayOfMonth())))
                .mapToDouble(t -> t.getExpense()).sum();
        System.out.println("Total expenses: $" + totalExpense);
    }

    public void summery(String category) {
        if (expenselist.isEmpty()) {
            System.out.println("There is no expense registered.");
            return;
        }
        double totalExpense = expenselist.stream().filter(t->t.getCategory().equals(category)).mapToDouble(Expense::getExpense).sum();
        System.out.println("Total expenses in category "+category+": $" + totalExpense);
    }

    public void update(String description, double amount, int id) throws IOException {
        Expense expense = expenselist.stream().filter(t -> t.getId() == id).findFirst()
                .orElseThrow(() -> new CommandNotFoundException(id + " : ID not found."));
        if (!description.isBlank()) {
            expense.setDescription(description);
        }
        if (amount >= 0) {
            expense.setExpense(amount);
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE;
        expense.setDate(LocalDate.now().format(dateTimeFormatter));
        expenselist.set(expenselist.indexOf(expense), expense);
        write();
    }

    public void delete(int id) throws IOException {
        Expense expense = expenselist.stream().filter(t -> t.getId() == id).findFirst()
                .orElseThrow(() -> new CommandNotFoundException(id + " : ID not found."));
        expenselist.remove(expenselist.indexOf(expense));
        write();
    }
    
    public void budget(double limit, boolean isEnabled) throws IOException {
        budgetLimit = limit;
        budgetEnabled = isEnabled;
        write();
    }

    public List<Expense> read() throws StreamReadException, DatabindException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        FileReader fileReader = new FileReader(filePath);
        List<Expense> list;

        try {
            Map<String, Object> dataMap = mapper.readValue(fileReader, new TypeReference<Map<String, Object>>() {
            });
            list = mapper.convertValue(dataMap.get("expenseList"), new TypeReference<List<Expense>>() {
            });
            budgetEnabled = mapper.convertValue(dataMap.get("budgetEnabled"), new TypeReference<Boolean>() {
            });
            budgetLimit = mapper.convertValue(dataMap.get("budgetLimit"), new TypeReference<Double>() {
            });
            Expense exp = list.get(list.size() - 1);
            lastId = exp.getId();
            width = list.stream().mapToInt(t -> t.getDescription().length()).max().orElse(0);
            return list;
        } catch (MismatchedInputException e) {
            list = new ArrayList<>();
            return list;
        }
        
    }
    
    
    private void write() throws IOException {

        if(budgetEnabled){
            double totalExpenseBudget=expenselist.stream().filter(t->LocalDate.parse(t.getDate()).getMonth().equals(LocalDate.now().getMonth())).mapToDouble(t->t.getExpense()).sum();
            System.out.println(totalExpenseBudget);
            if(totalExpenseBudget>budgetLimit){
                System.out.println("Budget Limit for this month exceeded.");
            }
        }


        ObjectMapper mapper = new ObjectMapper();

        FileWriter fileWriter = new FileWriter(filePath);
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("expenseList", expenselist);
        dataMap.put("budgetEnabled", budgetEnabled);
        dataMap.put("budgetLimit", budgetLimit);

        mapper.writerWithDefaultPrettyPrinter().writeValue(fileWriter, dataMap);

        fileWriter.close();
    }

}
