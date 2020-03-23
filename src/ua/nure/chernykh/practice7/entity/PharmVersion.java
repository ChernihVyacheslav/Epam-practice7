package ua.nure.chernykh.practice7.entity;

import static ua.nure.chernykh.practice7.constants.Constants.LINE_SEPARATOR;

public class PharmVersion {

    private String pharmName;
    private PharmCertificate pharmCertificate;
    private MedicinePackage medicinePackage;
    private MedicineDosage medicineDosage;

    public String getPharmName() {
        return pharmName;
    }

    public void setPharmName(String pharmName) {
        this.pharmName = pharmName;
    }

    public PharmCertificate getPharmCertificate() {
        return pharmCertificate;
    }

    public void setPharmCertificate(PharmCertificate pharmCertificate) {
        this.pharmCertificate = pharmCertificate;
    }

    public MedicinePackage getMedicinePackage() {
        return medicinePackage;
    }

    public void setMedicinePackage(MedicinePackage medicinePackage) {
        this.medicinePackage = medicinePackage;
    }

    public MedicineDosage getMedicineDosage() {
        return medicineDosage;
    }

    public void setMedicineDosage(MedicineDosage medicineDosage) {
        this.medicineDosage = medicineDosage;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(pharmName).append(LINE_SEPARATOR);
        result.append(pharmCertificate).append(LINE_SEPARATOR).append(medicinePackage).append(LINE_SEPARATOR)
                .append(medicineDosage).append(LINE_SEPARATOR);
        return result.toString();
    }
}
