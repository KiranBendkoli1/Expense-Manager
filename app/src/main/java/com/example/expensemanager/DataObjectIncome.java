package com.example.expensemanager;

class DataObjectIncome {
    private String  type;
    private Float incomeFigure;
    private String incomeCategory, incomeDescription;

    public DataObjectIncome() {

    }
    public void setType(String type){
        this.type = type ;
    }

    public Float getIncomeFigure() {
        return incomeFigure;
    }

    public void setIncomeFigure(Float incomeFigure) {
        this.incomeFigure = incomeFigure;
    }

    public String getIncomeCategory() {
        return incomeCategory;
    }

    public void setIncomeCategory(String incomeCategory) {
        this.incomeCategory = incomeCategory;
    }

    public String getIncomeDescription() {
        return incomeDescription;
    }

    public void setIncomeDescription(String incomeDescription) {
        this.incomeDescription = incomeDescription;
    }
}
