package com.hayroyal.tom.diabetial.BmiSpace;

public class Formular {
    private double inputKg;
    private double inputM;

    public Formular(double inputKg, double inputM) {
        this.inputKg = inputKg;
        this.inputM = inputM;
    }

    public double getInputKg() {
        return inputKg;
    }

    public double getInputM() {
        return inputM;
    }

    public double computeBMI(double kg, double m) {
        return kg / (Math.pow(m,2));
    }

}