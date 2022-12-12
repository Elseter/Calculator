package example.javafxprojects;

import javafx.scene.control.Label;

public class CalcRVK {
    private String currentValue = "";
    private int currentInt = 0;
    private int storedInt;
    private double currentDub = 0.0;
    private double storedDub;

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
    public void displayCurrentDisplay(Label label){
        label.setText(currentValue);
    }
    public void reset(String value) {
        currentValue = value;
        currentInt = 0;
        currentDub = 0.0;
    }
    //-------------------------------------------------
    // Setter Functions
    public void setStored(int value){storedInt = value;}
    public void setStored(double value){storedDub = value;}

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


    }
    public void multiplication(){

    }
}
