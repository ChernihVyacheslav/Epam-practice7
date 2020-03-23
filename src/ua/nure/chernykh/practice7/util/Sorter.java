package ua.nure.chernykh.practice7.util;

import ua.nure.chernykh.practice7.constants.Constants;
import ua.nure.chernykh.practice7.controller.DOMController;
import ua.nure.chernykh.practice7.entity.*;

import java.util.Collections;
import java.util.Comparator;

public class Sorter {

    // //////////////////////////////////////////////////////////
    // these are comparators
    // //////////////////////////////////////////////////////////

    /**
     * Sorts medicines by medicine name.
     */
    public static final Comparator<Medicine> SORT_MEDICINES_BY_MEDICINE_NAME = new Comparator<Medicine>() {
        @Override
        public int compare(Medicine o1, Medicine o2) {
            return o1.getMedicineName().compareTo(o2.getMedicineName());
        }
    };

    /**
     * Sorts medicines by medicine group.
     */
    public static final Comparator<Medicine> SORT_MEDICINES_BY_MEDICINE_GROUP = new Comparator<Medicine>() {
        @Override
        public int compare(Medicine o1, Medicine o2) {
            return o1.getMedicineGroup().compareTo(o2.getMedicineGroup());
        }
    };

    /**
     * Sorts pharm versions by medicine package type.
     */
    public static final Comparator<PharmVersion> SORT_PHARM_VERSIONS_BY_MEDICINE_PACKAGE_TYPE =
            new Comparator<PharmVersion>() {
                @Override
                public int compare(PharmVersion o1, PharmVersion o2) {
                    return o1.getMedicinePackage().getPackageType()
                            .compareTo(o2.getMedicinePackage().getPackageType());
                }
            };

    /**
     * Sorts pharm versions by price.
     */
    public static final Comparator<PharmVersion> SORT_PHARM_VERSIONS_BY_PRICE =
            new Comparator<PharmVersion>() {
                @Override
                public int compare(PharmVersion o1, PharmVersion o2) {
                    return o1.getMedicinePackage().getMedicinePrice()
                            .compareTo(o2.getMedicinePackage().getMedicinePrice());
                }
            };

    // //////////////////////////////////////////////////////////
    // these methods take Medicines object and sort it
    // with according comparator
    // //////////////////////////////////////////////////////////

    public static final void sortMedicinesByMedicineName(Medicines medicines) {
        Collections.sort(medicines.getMedicines(), SORT_MEDICINES_BY_MEDICINE_NAME);
    }

    public static final void sortMedicinesByMedicineGroup(Medicines medicines) {
        Collections.sort(medicines.getMedicines(), SORT_MEDICINES_BY_MEDICINE_GROUP);
    }

    public static final void sortPharmVersionsByPackageType(Medicines medicines) {
        for (Medicine medicine : medicines.getMedicines()) {
            for(MedicineVersion medicineVersion : medicine.getMedicineVersions()) {
                Collections.sort(medicineVersion.getPharmVersions(),
                        SORT_PHARM_VERSIONS_BY_MEDICINE_PACKAGE_TYPE);
            }
        }
    }

    public static final void sortPharmVersionsByPrice (Medicines medicines) {
        for (Medicine medicine : medicines.getMedicines()) {
            for(MedicineVersion medicineVersion : medicine.getMedicineVersions()) {
                Collections.sort(medicineVersion.getPharmVersions(),
                        SORT_PHARM_VERSIONS_BY_PRICE);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        // Medicines.xml --> Medicines object
        DOMController domController = new DOMController(
                Constants.VALID_XML_FILE);
        domController.parse(false);
        Medicines medicines = domController.getMedicines();

        System.out.println("====================================");
        System.out.println(medicines);
        System.out.println("====================================");

        System.out.println("====================================");
        Sorter.sortMedicinesByMedicineName(medicines);
        System.out.println(medicines);
        System.out.println("====================================");

        System.out.println("====================================");
        Sorter.sortPharmVersionsByPackageType(medicines);
        System.out.println(medicines);
    }
}
