package ua.nure.chernykh.practice7.constants;

/**
 * Holds entities declared in XSD document.
 *
 * @author V.Chernykh
 *
 */
public enum XML {
    // elements names
    MEDICINES("Medicines"),

    MEDICINE("Medicine"), MEDICINE_NAME("MedicineName"), MEDICINE_GROUP("MedicineGroup"),
    MEDICINE_PHARM("MedicinePharm"), MEDICINE_ANALOG("MedicineAnalogs"),

    MEDICINE_VERSION("MedicineVersion"), MEDICINE_VERSION_NAME("MedicineVersionName"),

    PHARM_VERSION("PharmVersion"), PHARM_NAME("PharmName"),

    PHARM_CERTIFICATE("PharmCertificate"), CERTIFICATE_NUMBER("CertificateNumber"),
    CERTIFICATE_START_DATE("CertificateStartDate"), CERTIFICATE_END_DATE("CertificateEndDate"),
    CERTIFICATE_ORGANISATION("CertificateOrganisation"),

    MEDICINE_PACKAGE("MedicinePackage"), PACKAGE_TYPE("PackageType"),
    MEDICINE_AMOUNT("MedicineAmount"), MEDICINE_PRICE("MedicinePrice"),

    MEDICINE_DOSAGE("MedicineDosage"), DOSAGE_AMOUNT("DoseAmount"),

    DOSAGE_PERIODICITY("DosagePeriodicity"), //DOSAGE_PERIODICITY_AMOUNT("")

    // attribute name
    PERIODICITY_TYPE("periodicityType");

    private String value;

    XML(String value) {
        this.value = value;
    }

    /**
     * Determines if a name is equal to the string value wrapped by this enum element.<br/>
     * If a SAX/StAX parser make all names of elements and attributes interned you can use
     * <pre>return value == name;</pre> instead <pre>return value.equals(name);</pre>
     * @param name string to compare with value.
     * @return value.equals(name)
     */
    public boolean equalsTo(String name) {
        return value.equals(name);
    }

    public String value() {
        return value;
    }
}
