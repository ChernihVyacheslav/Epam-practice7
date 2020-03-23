package ua.nure.chernykh.practice7.entity;

import java.util.ArrayList;
import java.util.List;

import static ua.nure.chernykh.practice7.constants.Constants.LINE_SEPARATOR;

public class Medicines {
    private List<Medicine> medicines;

    public List<Medicine> getMedicines() {
        if (medicines == null) {
            medicines = new ArrayList<>();
        }
        return medicines;
    }

    @Override
    public String toString() {
        if (medicines == null || medicines.isEmpty()) {
            return "No medicines found";
        }
        StringBuilder result = new StringBuilder();
        for (Medicine medicine : medicines) {
            result.append(medicine).append(LINE_SEPARATOR);
        }
        return result.toString();
    }
}
