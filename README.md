# Expense Tracker CLI Application

## Overview

The `Expense_Tracker` application is a simple command-line tool for managing personal expenses. It allows users to add, update, delete, and list expenses, as well as set budget limits and view summaries of their spending. The application uses Jackson to handle JSON data for reading and writing expense records.

## Features

- **Add Expenses**: Add new expenses with a description, amount, and optional category.
- **List Expenses**: Display all recorded expenses.
- **Summarize Expenses**: View a summary of expenses, either for the entire record or filtered by month or category.
- **Update Expenses**: Modify existing expense records by ID.
- **Delete Expenses**: Remove an expense record by ID.
- **Budget Management**: Set and manage budget limits, with the option to enable or disable budget tracking.

## Setup

1. Clone the repository or download the source code to your local machine.
2. Ensure that you have the batch script (`expense-tracker.bat` for Windows) or shell script (`expense-tracker.sh` for macOS/Linux) included in the project directory.

## Usage

The provided batch or shell script will handle both the compilation and execution of the Java program. To use the Expense Tracker CLI, follow these steps based on your operating system:

### Note

You need to navigate to the root folder of the project where the batch (`expense-tracker.bat`) or shell script (`expense-tracker.sh`) is located. Alternatively, you can add the script to your system's PATH environment variable to run it from any location.

### For Windows:

Open Command Prompt, navigate to the project root directory, and run:

  1. Run the Script:
      ```bash
      expense-tracker <options> [value]
      ```

### For MACOS:

Open Terminal, navigate to the project root directory, and run:

  1. Change Permissions:
      ```bash
      chmod +x expense-tracker.sh
      ```
  2. Run the Script:
      ```bash
      ./expense-tracker.sh <options> [value]

## Command Usage

### 1. Add an Expense
To add an expense, use the `add` command with the following options:

- `--description <desc>`: The description of the expense.
- `--amount <amt>`: The amount of the expense.
- `--category <cat>` (optional): The category of the expense. If not provided, defaults to "AllInOneCategory".

**Example**:
```bash
expense-activity add --description "Lunch" --amount 200 --category "Food"
```

### 2. List All Expenses
To list all expenses, use the `list` command.

**Example**:
```bash
expense-activity list
```

### 3. Summarize Expenses
To view a summary of expenses, use the `summery` command. 

You can also filter the summary by month or category:

- Filter by Month:
  - Use `--month <mm>` to specify the month number (e.g., `01` for January).

- Filter by Category:
  - Use `--category <cat>` to specify the desired category.

**Example**:
```bash
expense-activity summery --month 03
```

### 4. Update an Expense
To update an existing expense, use the `update` command with the following options:

- `--id <id>`: The ID of the expense to update.
- `--description <desc>` (optional): The new description of the expense.
- `--amount <amt>` (optional): The new amount of the expense.

**Example**:
```bash
expense-activity update --id 1 --amount 250
```

### 5. Delete an Expense
To delete an expense by its ID, use the `delete` command.

- `--id <id>`: The ID of the expense to delete.

**Example**:
```bash
expense-activity delete --id 1
```

### 6. Manage Budget
To set a budget limit, use the `budget` command:

- `--limit <amt>`: The budget limit amount.
- `--enable`: Enable budget tracking.
- `--disable`: Disable budget tracking.

**Example**:
```bash
expense-activity budget --limit 500 --enable
```

## Error Handling

The application includes comprehensive error handling to guide the user when an error occurs:

- If a command is missing or incorrect, the application will throw a `CommandNotFoundException` and display a usage guide.
- If required options or values are not provided, appropriate error messages will be shown.
- An unexpected error will display a general error message and prompt the user to review the command usage.

## Data Persistence with Jackson

The application uses Jackson for handling JSON data, allowing for easy storage and retrieval of expense records. Jackson is used to:

- **Read Data**: Load existing expenses from a JSON file at startup.
- **Write Data**: Save any changes (additions, updates, deletions) to the JSON file, ensuring data persistence across sessions.

## Conclusion

This Expense Tracker CLI application provides a straightforward way to manage personal finances through the command line. With the integration of Jackson for JSON data handling, it ensures data is persistently stored and easily accessible.