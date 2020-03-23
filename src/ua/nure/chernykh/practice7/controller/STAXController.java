package ua.nure.chernykh.practice7.controller;

import org.xml.sax.helpers.DefaultHandler;
import ua.nure.chernykh.practice7.constants.Constants;
import ua.nure.chernykh.practice7.constants.XML;
import ua.nure.chernykh.practice7.entity.*;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;
import javax.xml.transform.stream.StreamSource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Controller for StAX parser.
 *
 * @author V.Chernykh
 *
 */
public class STAXController extends DefaultHandler {

    private String xmlFileName;

    // main container
    private Medicines medicines;

    public Medicines getMedicines() {
        return medicines;
    }

    public STAXController(String xmlFileName) {
        this.xmlFileName = xmlFileName;
    }

    /**
     * Parses XML document with StAX (based on event reader). There is no validation during the
     * parsing.
     */
    public void parse() throws XMLStreamException {

        Medicine medicine = null;
        MedicineVersion medicineVersion = null;
        PharmVersion pharmVersion = null;
        PharmCertificate pharmCertificate = null;
        MedicineDosage medicineDosage = null;
        DosagePeriodicity dosagePeriodicity = null;
        MedicinePackage medicinePackage = null;

        // current element name holder
        String currentElement = null;

        XMLInputFactory factory = XMLInputFactory.newInstance();

        factory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, true);

        XMLEventReader reader = factory.createXMLEventReader(
                new StreamSource(xmlFileName));

        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();

            // skip any empty content
            if (event.isCharacters() && event.asCharacters().isWhiteSpace()) {
                continue;
            }

            // handler for start tags
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                currentElement = startElement.getName().getLocalPart();

                if (XML.MEDICINES.equalsTo(currentElement)) {
                    medicines = new Medicines();
                    continue;
                }

                if (XML.MEDICINE.equalsTo(currentElement)) {
                    medicine = new Medicine();
                    continue;
                }

                if (XML.MEDICINE_VERSION.equalsTo(currentElement)) {
                    medicineVersion = new MedicineVersion();
                    continue;
                }

                if (XML.PHARM_VERSION.equalsTo(currentElement)) {
                    pharmVersion = new PharmVersion();
                    continue;
                }

                if (XML.PHARM_CERTIFICATE.equalsTo(currentElement)) {
                    pharmCertificate = new PharmCertificate();
                    continue;
                }

                if (XML.MEDICINE_DOSAGE.equalsTo(currentElement)) {
                    medicineDosage = new MedicineDosage();
                    continue;
                }

                if (XML.MEDICINE_PACKAGE.equalsTo(currentElement)) {
                    medicinePackage = new MedicinePackage();
                    continue;
                }

                if (XML.DOSAGE_PERIODICITY.equalsTo(currentElement)) {
                    dosagePeriodicity = new DosagePeriodicity();
                    Attribute attribute= startElement.getAttributeByName(
                            new QName(XML.PERIODICITY_TYPE.value()));
                    if (attribute != null) {
                        dosagePeriodicity.setPeriodicityType(attribute.getValue());
                    }
                }
            }

            // handler for contents
            if (event.isCharacters()) {
                Characters characters = event.asCharacters();

                if (XML.MEDICINE_NAME.equalsTo(currentElement)) {
                    medicine.setMedicineName(characters.getData());
                    continue;
                }

                if (XML.MEDICINE_GROUP.equalsTo(currentElement)) {
                    medicine.setMedicineGroup(characters.getData());
                    continue;
                }

                if (XML.MEDICINE_PHARM.equalsTo(currentElement)) {
                    medicine.setMedicinePharm(characters.getData());
                    continue;
                }

                if (XML.MEDICINE_ANALOG.equalsTo(currentElement)) {
                    medicine.getMedicineAnalogs().add(characters.getData());
                    continue;
                }

                if (XML.MEDICINE_VERSION_NAME.equalsTo(currentElement)) {
                    medicineVersion.setMedicineVersionName(characters.getData());
                    continue;
                }

                if (XML.PHARM_NAME.equalsTo(currentElement)) {
                    pharmVersion.setPharmName(characters.getData());
                    continue;
                }

                if (XML.CERTIFICATE_NUMBER.equalsTo(currentElement)) {
                    pharmCertificate.setCertificateNumber(Integer.parseInt(characters.getData()));
                    continue;
                }

                if (XML.CERTIFICATE_START_DATE.equalsTo(currentElement)) {
                    try {
                        pharmCertificate.setCertificateStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(characters.getData()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    continue;
                }

                if (XML.CERTIFICATE_END_DATE.equalsTo(currentElement)) {
                    try {
                        pharmCertificate.setCertificateEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(characters.getData()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    continue;
                }

                if (XML.CERTIFICATE_ORGANISATION.equalsTo(currentElement)) {
                    pharmCertificate.setCertificateOrganisation(characters.getData());
                    continue;
                }

                if (XML.PACKAGE_TYPE.equalsTo(currentElement)) {
                    medicinePackage.setPackageType(characters.getData());
                    continue;
                }

                if (XML.MEDICINE_AMOUNT.equalsTo(currentElement)) {
                    medicinePackage.setMedicineAmount(Integer.parseInt(characters.getData()));
                    continue;
                }

                if (XML.MEDICINE_PRICE.equalsTo(currentElement)) {
                    medicinePackage.setMedicinePrice(BigDecimal.valueOf(Double.parseDouble(characters.getData())));
                    continue;
                }

                if (XML.DOSAGE_AMOUNT.equalsTo(currentElement)) {
                    medicineDosage.setDosageAmount(Integer.parseInt(characters.getData()));
                    continue;
                }

                if (XML.DOSAGE_PERIODICITY.equalsTo(currentElement)) {
                    dosagePeriodicity.setDosagePeriodicityAmount(Integer.parseInt(characters.getData()));
                    continue;
                }

            }

            // handler for end tags
            if (event.isEndElement()) {
                EndElement endElement = event.asEndElement();
                String localName = endElement.getName().getLocalPart();

                if (XML.MEDICINE.equalsTo(localName)) {
                    medicines.getMedicines().add(medicine);
                    continue;
                }

                if (XML.MEDICINE_VERSION.equalsTo(localName)) {
                    medicine.getMedicineVersions().add(medicineVersion);
                    continue;
                }

                if (XML.PHARM_VERSION.equalsTo(localName)) {
                    medicineVersion.getPharmVersions().add(pharmVersion);
                    continue;
                }

                if (XML.PHARM_CERTIFICATE.equalsTo(localName)) {
                    pharmVersion.setPharmCertificate(pharmCertificate);
                    continue;
                }

                if (XML.MEDICINE_PACKAGE.equalsTo(localName)) {
                    pharmVersion.setMedicinePackage(medicinePackage);
                    continue;
                }

                if (XML.MEDICINE_DOSAGE.equalsTo(localName)) {
                    pharmVersion.setMedicineDosage(medicineDosage);
                    continue;
                }

                if (XML.DOSAGE_PERIODICITY.equalsTo(localName)) {
                    medicineDosage.setDosagePeriodicity(dosagePeriodicity);
                    continue;
                }
            }
        }
        reader.close();
    }

    public static void main(String[] args) throws Exception {

        // try to parse (valid) XML file (success)
        STAXController staxContr = new STAXController(Constants.VALID_XML_FILE);
        staxContr.parse(); // <-- do parse (success)

        // obtain container
        Medicines medicines = staxContr.getMedicines();

        // we have Medicines object at this point:
        System.out.println("====================================");
        System.out.print("Here are the medicines: \n" + medicines);
        System.out.println("====================================");
    }
}