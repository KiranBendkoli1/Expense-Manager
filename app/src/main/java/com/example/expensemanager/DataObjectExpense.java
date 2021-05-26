package com.example.expensemanager;

class DataObjectExpense {
    String type;
    private Float expenseFigure;
    private String expenseCategory, expenseDescription;

    public DataObjectExpense() {
        type = "Expense";
    }


    public Float getExpenseFigure() {
        return expenseFigure;
    }

    public void setExpenseFigure(Float expenseFigure) {
        this.expenseFigure = expenseFigure;
    }

    public String getIncomeCategory() {
        return expenseCategory;
    }

    public void setExpenseCategory(String expenseCategory) {
        this.expenseCategory = expenseCategory;
    }

    public String getExpenseDescription() {
        return expenseDescription;
    }

    public void setExpenseDescription(String expenseDescription) {
        this.expenseDescription = expenseDescription;
    }
}
