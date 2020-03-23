package ua.nure.chernykh.practice7.entity;

import java.util.ArrayList;
import java.util.List;

import static ua.nure.chernykh.practice7.constants.Constants.LINE_SEPARATOR;

public class MedicineVersion {

    private String medicineVersionName;
    private List<PharmVersion> pharmVersions;

    public String getMedicineVersionName() {
        return medicineVersionName;
    }

    public void setMedicineVersionName(String medicineVersionName) {
        this.medicineVersionName = medicineVersionName;
    }

    public List<PharmVersion> getPharmVersions() {
        if(pharmVersions == null) {
            pharmVersions = new ArrayList<>();
        }
        return pharmVersions;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(medicineVersionName).append(LINE_SEPARATOR);
        for(PharmVersion pharmVersion : pharmVersions) {
            result.append(pharmVersion).append(LINE_SEPARATOR);
        }
        return result.toString();
    }
}
