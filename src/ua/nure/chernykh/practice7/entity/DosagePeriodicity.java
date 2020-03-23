package ua.nure.chernykh.practice7.entity;

import static ua.nure.chernykh.practice7.constants.Constants.LINE_SEPARATOR;

public class DosagePeriodicity {

    private int dosagePeriodicityAmount;
    private String periodicityType;

    public int getDosagePeriodicityAmount() {
        return dosagePeriodicityAmount;
    }

    public void setDosagePeriodicityAmount(int dosagePeriodicityAmount) {
        this.dosagePeriodicityAmount = dosagePeriodicityAmount;
    }

    public String getPeriodicityType() {
        return periodicityType;
    }

    public void setPeriodicityType(String periodicityType) {
        this.periodicityType = periodicityType;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(Integer.toString(dosagePeriodicityAmount)).append(LINE_SEPARATOR)
                .append(periodicityType);
        return result.toString();
    }
}
