package ua.nure.chernykh.practice7.entity;

import java.math.BigDecimal;

import static ua.nure.chernykh.practice7.constants.Constants.LINE_SEPARATOR;

public class MedicinePackage {

    private String packageType;
    private int medicineAmount;
    private BigDecimal medicinePrice;

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public int getMedicineAmount() {
        return medicineAmount;
    }

    public void setMedicineAmount(int medicineAmount) {
        this.medicineAmount = medicineAmount;
    }

    public BigDecimal getMedicinePrice() {
        return medicinePrice;
    }

    public void setMedicinePrice(BigDecimal medicinePrice) {
        this.medicinePrice = medicinePrice;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(packageType).append(LINE_SEPARATOR);
        result.append(medicineAmount).append(LINE_SEPARATOR).append(medicinePrice);
        return result.toString();
    }
}
