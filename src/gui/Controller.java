package gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Pair;
import phase1.Expense;

import phase2.DataBaseOperation;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class Controller {
    @FXML
    private TableView<Expense> expenseTable;

    @FXML
    private TableColumn<Expense, String> dateColumn;

    @FXML
    private TableColumn<Expense, String> expenseColumn;

    @FXML
    private TableColumn<Expense, Double> costColumn;
    @FXML
    private TableView <Expense> budgetTable;
    @FXML private TableColumn<Expense, Double> budgetColumn;

    private DataBaseOperation expenseDAO; // Replace with your actual ExpenseDAO

    @FXML
    public void initialize() {
        // Initialize the expense table with the three columns.
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        expenseColumn.setCellValueFactory(new PropertyValueFactory<>("expense"));
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));
        // Initialize the budget table with the one column.
        budgetColumn.setCellValueFactory(new PropertyValueFactory<>("budget"));

        // Initialize your DAO (replace this with your DAO instantiation)
        expenseDAO = new DataBaseOperation();


        // Populate the table on initialization
        populateTable();
        populateBudgetTable();
    }
   @FXML
    public void populateBudgetTable(){
        List<Expense> budget = expenseDAO.fetchExpensesFromDatabase().stream().filter(expense -> expense.getBudget() >0).collect(Collectors.toList());

        //if the list is empty prompt the user to add a budget
       if (budget.isEmpty()) {
           // Open a dialog box to add budget
           Dialog<Pair<String, Double>> dialog = new Dialog<>();
           dialog.setTitle("Add Budget");
           dialog.setHeaderText("Budget is not set. Please add a budget.");

           // Set the button types (OK and Cancel)
           dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

           // Create the budget input fields and labels
           TextField budgetField = new TextField();
           budgetField.setPromptText("Enter Budget");
           Label label = new Label("Budget:");

           // Set the content of the dialog
           dialog.getDialogPane().setContentText("Please enter the budget:");
              dialog.getDialogPane().setContent(new Label("Budget:"));
              dialog.getDialogPane().setContent(budgetField);


           // Request focus on the budget field by default
           Platform.runLater(budgetField::requestFocus);

           // Convert the result to a Pair when the OK button is clicked
           dialog.setResultConverter(dialogButton -> {
               if (dialogButton == ButtonType.OK) {
                   return new Pair<>(label.getText(), Double.parseDouble(budgetField.getText()));
               }
               return null;
           });

           // Show the dialog and process the result
           dialog.showAndWait().ifPresent(result -> {
               // Retrieve the entered budget value
               Double enteredBudget = result.getValue();

               // Now, you can use the enteredBudget value as needed.
               // For example, you can update your data model or database.

               // Refresh the budget table after adding the new budget
               populateBudgetTable();
           });
       }


        budgetTable.getItems().clear();

        budgetTable.getItems().addAll(budget);
    }
    @FXML
    public void populateTable() {
        // Fetch expenses from the database
        List<Expense> expenses = expenseDAO.fetchExpensesFromDatabase();

        // Clear existing items in the table
        expenseTable.getItems().clear();

        // Add fetched expenses to the table
        expenseTable.getItems().addAll(expenses);
    }

    @FXML
    public void addExpense() {
        // Add a new expense to the database (implement this based on your database logic)

        Expense newExpense = new Expense(LocalDate.of(2024, 2, 8), "New Expense", 50.0, 100.0);
        expenseDAO.addExpense(newExpense);

        // Refresh the table
        populateTable();
    }
}
