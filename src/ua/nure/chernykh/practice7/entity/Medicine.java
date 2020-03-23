package ua.nure.chernykh.practice7.entity;


import ua.nure.chernykh.practice7.constants.Constants;

import java.util.ArrayList;
import java.util.List;

public class Medicine {

    private String medicineName;
    private String medicineGroup;
    private String medicinePharm;
    private List<String> medicineAnalogs;
    private List<MedicineVersion> medicineVersions;

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getMedicineGroup() {
        return medicineGroup;
    }

    public void setMedicineGroup(String medicineGroup) {
        this.medicineGroup = medicineGroup;
    }

    public String getMedicinePharm() {
        return medicinePharm;
    }

    public void setMedicinePharm(String medicinePharm) {
        this.medicinePharm = medicinePharm;
    }

    public List<String> getMedicineAnalogs() {
        if(medicineAnalogs == null) {
            medicineAnalogs = new ArrayList<>();
        }
        return medicineAnalogs;
    }

    public List<MedicineVersion> getMedicineVersions() {
        if(medicineVersions == null) {
            medicineVersions = new ArrayList<>();
        }
        return medicineVersions;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(medicineName).append(Constants.LINE_SEPARATOR);
        result.append(medicineGroup).append(Constants.LINE_SEPARATOR).append(medicinePharm).append(Constants.LINE_SEPARATOR);
        for(String analogue : getMedicineAnalogs()) {
            result.append(analogue).append(Constants.LINE_SEPARATOR);
        }
        for(MedicineVersion version : getMedicineVersions()) {
            result.append(version);
        }
        return result.toString();
    }
}
