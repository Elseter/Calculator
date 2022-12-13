package example.javafxprojects;

import javafx.scene.control.Label;

import java.math.BigDecimal;

public class CalcRVK {
    private String currentValue = "";
    private int currentInt = 0;
    private double currentDub = 0.0;
     double storedDub;

    String operator = "";

    private final Label label;

    public CalcRVK(Label label){
        this.label = label;
    }

    //-------------------------------------------------
    // Methods to alter the display string
    public void appendCurrentDisplay(String value){
        if (currentValue.equals("") && (value.equals(".") || value.equals("-")))
            currentValue = "0";
        if (value.equals("-")) {
            if (currentValue.contains("."))
                currentValue = String.valueOf(currentDub * -1);
            else{
                currentValue = String.valueOf(currentInt * -1);
            }
        }
        else
            currentValue += value;

        if (currentValue.contains(".")){
            currentDub = Double.parseDouble(currentValue);
        }else{
            currentInt = Integer.parseInt(currentValue);
        }
    }
    public void displayCurrentDisplay(){
        this.label.setText(currentValue);
    }
    public void resetVisible(){
        currentValue = "";
        currentInt = 0;
        currentDub = 0.0;
    }
    public void resetVisible(String value, String label) {
        currentValue = value;
        this.label.setText(label);
        currentInt = 0;
        currentDub = 0.0;

    }
    //-------------------------------------------------
    // Setter Functions
    public void setOperator(String op){
        this.operator = op;
    }
    public void setStored(){
        int storedInt;
        try{
            storedInt = Integer.parseInt(currentValue);
            storedDub = storedInt;
        }
        catch (NumberFormatException e){
            if (currentValue.equals("")) {
                storedInt = 0;
                storedDub = 0.0;
            }
            else {
                storedDub = Double.parseDouble(currentValue);
                storedInt = 0;
            }
        }
        System.out.println(storedDub);
        System.out.println(storedInt);

    }
    public void getCurrent() {
        try {
            currentInt = Integer.parseInt(currentValue);
            currentDub = currentInt;
        } catch (NumberFormatException e) {
            if (currentValue.equals("")) {
                currentInt = 0;
                currentDub = 0.0;
            } else {
                currentDub = Double.parseDouble(currentValue);
                currentInt = 0;
            }
        }
    }

    //-------------------------------------------------
    // Math Functions
    public void modulus(){
        try{
            int workVal = Integer.parseInt(currentValue);
            currentDub = workVal / 1000.0;
        }
        catch (Exception e){
            double workVal = Double.parseDouble(currentValue);
            currentDub = workVal / 1000.0;
        }
        currentValue = String.valueOf(BigDecimal.valueOf(currentDub));
        displayCurrentDisplay();
    }
    public void addition(){
        storedDub = currentDub + storedDub;
        currentValue = String.valueOf(storedDub);
        currentDub = storedDub;
    }
    public void subtraction(){
        storedDub = storedDub - currentDub;
        currentValue = String.valueOf(storedDub);
        currentDub = storedDub;
    }
    public void multiplication(){
        System.out.println(currentDub + "*" + storedDub);
        storedDub = currentDub * storedDub;
        currentValue = String.valueOf(storedDub);
        currentDub = storedDub;
    }
    public void division(){
        System.out.println(storedDub + "/" + currentDub);
        storedDub = storedDub / currentDub;
        currentValue = String.valueOf(storedDub);
        currentDub = storedDub;
    }


}
