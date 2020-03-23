package ua.nure.chernykh.practice7.entity;

import static ua.nure.chernykh.practice7.constants.Constants.LINE_SEPARATOR;

public class MedicineDosage {

    private int dosageAmount;
    private DosagePeriodicity dosagePeriodicity;

    public int getDosageAmount() {
        return dosageAmount;
    }

    public void setDosageAmount(int dosageAmount) {
        this.dosageAmount = dosageAmount;
    }

    public DosagePeriodicity getDosagePeriodicity() {
        return dosagePeriodicity;
    }

    public void setDosagePeriodicity(DosagePeriodicity dosagePeriodicity) {
        this.dosagePeriodicity = dosagePeriodicity;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(Integer.toString(dosageAmount)).append(LINE_SEPARATOR)
                .append(dosagePeriodicity);
        return result.toString();
    }
}
