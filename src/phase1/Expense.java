package phase1;

import org.boon.Str;

import java.sql.Date;
import java.time.LocalDate;

public class Expense {
    private String expense;
    private double cost;
    private double budget;
    private LocalDate date;
    //create an id that auto increments and is unique
    private int id;



    public Expense(int id, double budget, String expense, int cost, LocalDate date) {


            System.out.println("Expense added");
            this.id = id;
            this.budget = budget;
            this.expense = expense;
            this.cost = cost;
            this.date = date;


    }

    public Expense(LocalDate date, String expenseName, double cost, double budget) {
        this.date = date;
        this.expense =expenseName;
        this.cost =  cost;
        this.budget = budget;

    }




    public String getExpense() {
        return expense;
    }

    public double getBudget() {
        return budget;
    }

    public double getCost() {
        return cost;
    }

    public String ToString() {
        return budget + " " + expense + " " + cost;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setExpense(String expense) {
        this.expense = expense;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return   date +
                " " + expense +
                " " + cost ;
    }
}
