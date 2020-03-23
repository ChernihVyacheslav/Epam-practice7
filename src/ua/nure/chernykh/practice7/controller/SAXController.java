package ua.nure.chernykh.practice7.controller;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import ua.nure.chernykh.practice7.constants.Constants;
import ua.nure.chernykh.practice7.constants.XML;
import ua.nure.chernykh.practice7.entity.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Controller for SAX parser.
 *
 * @author V.Chernykh
 *
 */
public class SAXController extends DefaultHandler {

    private String xmlFileName;

    // current element name holder
    private String currentElement;

    // main container
    private Medicines medicines;

    private Medicine medicine;

    private MedicineVersion medicineVersion;

    private PharmVersion pharmVersion;

    private PharmCertificate pharmCertificate;

    private MedicineDosage medicineDosage;

    private DosagePeriodicity dosagePeriodicity;

    private MedicinePackage medicinePackage;

    public SAXController(String xmlFileName) {
        this.xmlFileName = xmlFileName;
    }

    /**
     * Parses XML document.
     *
     * @param validate
     *            If true validate XML document against its XML schema. With
     *            this parameter it is possible make parser validating.
     */
    public void parse(boolean validate)
            throws ParserConfigurationException, SAXException, IOException {

        // obtain sax parser factory
        SAXParserFactory factory = SAXParserFactory.newInstance();

        // XML document contains namespaces
        factory.setNamespaceAware(true);

        // set validation
        if (validate) {
            factory.setFeature(Constants.FEATURE_TURN_VALIDATION_ON, true);
            factory.setFeature(Constants.FEATURE_TURN_SCHEMA_VALIDATION_ON, true);
        }

        SAXParser parser = factory.newSAXParser();
        parser.parse(xmlFileName, this);
    }

    // ///////////////////////////////////////////////////////////
    // ERROR HANDLER IMPLEMENTATION
    // ///////////////////////////////////////////////////////////

    @Override
    public void error(org.xml.sax.SAXParseException e) throws SAXException {
        // if XML document not valid just throw exception
        throw e;
    }

    public Medicines getMedicines() {
        return medicines;
    }

    // ///////////////////////////////////////////////////////////
    // CONTENT HANDLER IMPLEMENTATION
    // ///////////////////////////////////////////////////////////


    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {

        currentElement = localName;

        if (XML.MEDICINES.equalsTo(currentElement)) {
            medicines = new Medicines();
            return;
        }

        if (XML.MEDICINE.equalsTo(currentElement)) {
            medicine = new Medicine();
            return;
        }

        if (XML.MEDICINE_VERSION.equalsTo(currentElement)) {
            medicineVersion = new MedicineVersion();
            return;
        }

        if (XML.PHARM_VERSION.equalsTo(currentElement)) {
            pharmVersion = new PharmVersion();
            return;
        }

        if (XML.PHARM_CERTIFICATE.equalsTo(currentElement)) {
            pharmCertificate = new PharmCertificate();
            return;
        }

        if (XML.MEDICINE_DOSAGE.equalsTo(currentElement)) {
            medicineDosage = new MedicineDosage();
            return;
        }

        if (XML.MEDICINE_PACKAGE.equalsTo(currentElement)) {
            medicinePackage = new MedicinePackage();
            return;
        }

        if (XML.DOSAGE_PERIODICITY.equalsTo(currentElement)) {
            dosagePeriodicity = new DosagePeriodicity();
            if (attributes.getLength() > 0) {
                dosagePeriodicity.setPeriodicityType(attributes.getValue(uri,
                        XML.PERIODICITY_TYPE.value()));
            }
        }

    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {

        String elementText = new String(ch, start, length).trim();

        // return if content is empty
        if (elementText.isEmpty()) {
            return;
        }

        if (XML.MEDICINE_NAME.equalsTo(currentElement)) {
            medicine.setMedicineName(elementText);
            return;
        }

        if (XML.MEDICINE_GROUP.equalsTo(currentElement)) {
            medicine.setMedicineGroup(elementText);
            return;
        }

        if (XML.MEDICINE_PHARM.equalsTo(currentElement)) {
            medicine.setMedicinePharm(elementText);
            return;
        }

        if (XML.MEDICINE_ANALOG.equalsTo(currentElement)) {
            medicine.getMedicineAnalogs().add(elementText);
            return;
        }

        if (XML.MEDICINE_VERSION_NAME.equalsTo(currentElement)) {
            medicineVersion.setMedicineVersionName(elementText);
            return;
        }

        if (XML.PHARM_NAME.equalsTo(currentElement)) {
            pharmVersion.setPharmName(elementText);
            return;
        }

        if (XML.CERTIFICATE_NUMBER.equalsTo(currentElement)) {
            pharmCertificate.setCertificateNumber(Integer.parseInt(elementText));
            return;
        }

        if (XML.CERTIFICATE_START_DATE.equalsTo(currentElement)) {
            try {
                pharmCertificate.setCertificateStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(elementText));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return;
        }

        if (XML.CERTIFICATE_END_DATE.equalsTo(currentElement)) {
            try {
                pharmCertificate.setCertificateEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(elementText));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return;
        }

        if (XML.CERTIFICATE_ORGANISATION.equalsTo(currentElement)) {
            pharmCertificate.setCertificateOrganisation(elementText);
            return;
        }

        if (XML.PACKAGE_TYPE.equalsTo(currentElement)) {
            medicinePackage.setPackageType(elementText);
            return;
        }

        if (XML.MEDICINE_AMOUNT.equalsTo(currentElement)) {
            medicinePackage.setMedicineAmount(Integer.parseInt(elementText));
            return;
        }

        if (XML.MEDICINE_PRICE.equalsTo(currentElement)) {
            medicinePackage.setMedicinePrice(BigDecimal.valueOf(Double.parseDouble(elementText)));
            return;
        }

        if (XML.DOSAGE_AMOUNT.equalsTo(currentElement)) {
            medicineDosage.setDosageAmount(Integer.parseInt(elementText));
            return;
        }

        if (XML.DOSAGE_PERIODICITY.equalsTo(currentElement)) {
            dosagePeriodicity.setDosagePeriodicityAmount(Integer.parseInt(elementText));
            return;
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {

        if (XML.MEDICINE.equalsTo(localName)) {
            medicines.getMedicines().add(medicine);
            return;
        }

        if (XML.MEDICINE_VERSION.equalsTo(localName)) {
            medicine.getMedicineVersions().add(medicineVersion);
            return;
        }

        if (XML.PHARM_VERSION.equalsTo(localName)) {
            medicineVersion.getPharmVersions().add(pharmVersion);
            return;
        }

        if (XML.PHARM_CERTIFICATE.equalsTo(localName)) {
            pharmVersion.setPharmCertificate(pharmCertificate);
            return;
        }

        if (XML.MEDICINE_PACKAGE.equalsTo(localName)) {
            pharmVersion.setMedicinePackage(medicinePackage);
            return;
        }

        if (XML.MEDICINE_DOSAGE.equalsTo(localName)) {
            pharmVersion.setMedicineDosage(medicineDosage);
            return;
        }

        if (XML.DOSAGE_PERIODICITY.equalsTo(localName)) {
            medicineDosage.setDosagePeriodicity(dosagePeriodicity);
            return;
        }
    }

    public static void main(String[] args) throws Exception {

        // try to parse valid XML file (success)
        SAXController saxContr = new SAXController(Constants.VALID_XML_FILE);

        // do parse with validation on (success)
        saxContr.parse(true);

        // obtain container
        Medicines medicines = saxContr.getMedicines();

        // we have Medicines object at this point:
        System.out.println("====================================");
        System.out.print("Here are the medicines: \n" + medicines);
        System.out.println("====================================");

//        // now try to parse NOT valid XML (failed)
//        saxContr = new SAXController(Constants.INVALID_XML_FILE);
//        try {
//            // do parse with validation on (failed)
//            saxContr.parse(true);
//        } catch (Exception ex) {
//            System.err.println("====================================");
//            System.err.println("Validation is failed:\n" + ex.getMessage());
//            System.err
//                    .println("Try to print medicine object:" + saxContr.getMedicines());
//            System.err.println("====================================");
//        }
//
//        // and now try to parse NOT valid XML with validation off (success)
//        saxContr.parse(false);
//
//        // we have Test object at this point:
//        System.out.println("====================================");
//        System.out.print("Here is the medicine: \n" + saxContr.getMedicines());
//        System.out.println("====================================");
    }
}