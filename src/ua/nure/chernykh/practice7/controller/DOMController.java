package ua.nure.chernykh.practice7.controller;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;
import ua.nure.chernykh.practice7.constants.Constants;
import ua.nure.chernykh.practice7.constants.XML;
import ua.nure.chernykh.practice7.entity.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;


/**
 * Controller for DOM parser.
 *
 * @author V.Chernykh
 *
 */
public class DOMController {

    private String xmlFileName;

    // main container
    private Medicines medicines;

    public DOMController(String xmlFileName) {
        this.xmlFileName = xmlFileName;
    }

    public Medicines getMedicines() {
        return medicines;
    }

    /**
     * Parses XML document.
     *
     * @param validate
     *            If true validate XML document against its XML schema.
     */
    public void parse(boolean validate)
            throws ParserConfigurationException, SAXException, IOException {

        // obtain DOM parser 
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        // set properties for Factory

        // XML document contains namespaces
        dbf.setNamespaceAware(true);

        // make parser validating
        if (validate) {
            // turn validation on
            dbf.setFeature(Constants.FEATURE_TURN_VALIDATION_ON, true);

            // turn on xsd validation 
            dbf.setFeature(Constants.FEATURE_TURN_SCHEMA_VALIDATION_ON, true);
        }

        DocumentBuilder db = dbf.newDocumentBuilder();

        // set error handler
        db.setErrorHandler(new DefaultHandler() {
            @Override
            public void error(SAXParseException e) throws SAXException {
                // throw exception if XML document is NOT valid
                throw e;
            }
        });

        // parse XML document
        Document document = db.parse(xmlFileName);

        // get root element
        Element root = document.getDocumentElement();

        // create container
        medicines = new Medicines();

        // obtain medicine nodes
        NodeList medicineNodes = root
                .getElementsByTagName(XML.MEDICINE.value());

        // process medicine nodes
        for (int j = 0; j < medicineNodes.getLength(); j++) {
            Medicine medicine = getMedicine(medicineNodes.item(j));
            // add medicine to container
            medicines.getMedicines().add(medicine);
        }
    }

    /**
     * Extracts medicine object from the question XML node.
     *
     * @param mNode
     *            Medicine node.
     * @return Medicine object.
     */
    private static Medicine getMedicine(Node mNode) {
        Medicine medicine = new Medicine();
        Element mElement = (Element) mNode;

        // process medicine name
        Node menNode = mElement.getElementsByTagName(XML.MEDICINE_NAME.value())
                .item(0);
        // set medicine name
        medicine.setMedicineName(menNode.getTextContent());

        // process medicine group
        Node mgNode = mElement.getElementsByTagName(XML.MEDICINE_GROUP.value())
                .item(0);
        // set medicine group
        medicine.setMedicineGroup(mgNode.getTextContent());

        // process medicine pharm
        Node mpNode = mElement.getElementsByTagName(XML.MEDICINE_PHARM.value())
                .item(0);
        // set medicine pharm
        medicine.setMedicinePharm(mpNode.getTextContent());

        // process medicine analogue
        NodeList maNodeList = mElement.getElementsByTagName(XML.MEDICINE_ANALOG.value());
        // set medicine analogue
        for (int j = 0; j < maNodeList.getLength(); j++) {
            // add analogue
            medicine.getMedicineAnalogs().add(maNodeList.item(j).getTextContent());
        }

        // process medicine versions
        NodeList mvNodeList = mElement.getElementsByTagName(XML.MEDICINE_VERSION.value());
        for (int j = 0; j < mvNodeList.getLength(); j++) {
            MedicineVersion medicineVersion = getMedicineVersion(mvNodeList.item(j));

            // add medicine version
            medicine.getMedicineVersions().add(medicineVersion);
        }

        return medicine;
    }

    /**
     * Extracts MedicineVersion object from the answer XML node.
     *
     * @param mvNode
     *            MedicineVersion node.
     * @return MedicineVersion object.
     */
    private static MedicineVersion getMedicineVersion(Node mvNode) {
        MedicineVersion medicineVersion = new MedicineVersion();
        Element mvElement = (Element) mvNode;

        // process medicineVersionName
        String name = mvElement.getElementsByTagName(XML.MEDICINE_VERSION_NAME.value())
                .item(0).getTextContent();
        medicineVersion.setMedicineVersionName(name);

        // process pharm versions
        NodeList pvNodeList = mvElement.getElementsByTagName(XML.PHARM_VERSION.value());
        // set pharm versions
        for (int j = 0; j < pvNodeList.getLength(); j++) {
            // add pharm version
            medicineVersion.getPharmVersions().add(getPharmVersion(pvNodeList.item(j)));
        }
        return medicineVersion;
    }

    /**
     * Extracts PharmVersion object from the answer XML node.
     *
     * @param pvNode
     *            PharmVersion node.
     * @return PharmVersion object.
     */
    private static PharmVersion getPharmVersion(Node pvNode) {
        PharmVersion pharmVersion = new PharmVersion();
        Element pvElement = (Element) pvNode;

        // process pharm name
        String name = pvElement.getElementsByTagName(XML.PHARM_NAME.value())
                .item(0).getTextContent();
        pharmVersion.setPharmName(name);

        //process pharm certificate
        Node pcNode = pvElement.getElementsByTagName(XML.PHARM_CERTIFICATE.value()).item(0);
        PharmCertificate pharmCertificate = getPharmCertificate(pcNode);
        pharmVersion.setPharmCertificate(pharmCertificate);

        //process medicine package
        Node mpNode = pvElement.getElementsByTagName(XML.MEDICINE_PACKAGE.value()).item(0);
        MedicinePackage medicinePackage = getMedicinePackage(mpNode);
        pharmVersion.setMedicinePackage(medicinePackage);

        //process medicine dosage
        Node mdNode = pvElement.getElementsByTagName(XML.MEDICINE_DOSAGE.value()).item(0);
        MedicineDosage medicineDosage = getMedicineDosage(mdNode);
        pharmVersion.setMedicineDosage(medicineDosage);
        
        return pharmVersion;
    }
    
    /**
     * Extracts PharmCertificate object from the answer XML node.
     *
     * @param pcNode
     *            PharmCertificate node.
     * @return PharmCertificate object.
     */
    private static PharmCertificate getPharmCertificate(Node pcNode) {
        PharmCertificate pharmCertificate = new PharmCertificate();
        Element pcElement = (Element) pcNode;

        // process certificate number
        int number = Integer.parseInt(pcElement.getElementsByTagName(XML.CERTIFICATE_NUMBER.value())
                .item(0).getTextContent());
        pharmCertificate.setCertificateNumber(number);

        //process certificate starting date
        Node sdNode = pcElement.getElementsByTagName(XML.CERTIFICATE_START_DATE.value()).item(0);
        try {
            pharmCertificate.setCertificateStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(sdNode.getTextContent()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //process certificate ending date
        Node edNode = pcElement.getElementsByTagName(XML.CERTIFICATE_END_DATE.value()).item(0);
        try {
            pharmCertificate.setCertificateEndDate(new SimpleDateFormat("yyyy-MM-dd")
                    .parse(edNode.getTextContent()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // process certificate organisation
        String organisation = pcElement.getElementsByTagName(XML.CERTIFICATE_ORGANISATION.value())
                .item(0).getTextContent();
        pharmCertificate.setCertificateOrganisation(organisation);

        return pharmCertificate;
    }

    /**
     * Extracts MedicinePackage object from the answer XML node.
     *
     * @param mpNode
     *            MedicinePackage node.
     * @return MedicinePackage object.
     */
    private static MedicinePackage getMedicinePackage(Node mpNode) {
        MedicinePackage medicinePackage = new MedicinePackage();
        Element mpElement = (Element) mpNode;

        // process package type
        String packageType = mpElement.getElementsByTagName(XML.PACKAGE_TYPE.value())
                .item(0).getTextContent();
        medicinePackage.setPackageType(packageType);
        
        // process medicine amount
        int medicineAmount = Integer.parseInt(mpElement.getElementsByTagName(XML.MEDICINE_AMOUNT.value())
                .item(0).getTextContent());
        medicinePackage.setMedicineAmount(medicineAmount);

        //process medicine price
        BigDecimal medicinePrice = BigDecimal.valueOf(Double.parseDouble(
                mpElement.getElementsByTagName(XML.MEDICINE_PRICE.value())
                .item(0).getTextContent()));
        medicinePackage.setMedicinePrice(medicinePrice);

        return medicinePackage;
    }

    /**
     * Extracts MedicineDosage object from the answer XML node.
     *
     * @param mdNode
     *            MedicineDosage node.
     * @return MedicineDosage object.
     */
    private static MedicineDosage getMedicineDosage(Node mdNode) {
        MedicineDosage medicineDosage = new MedicineDosage();
        Element mdElement = (Element) mdNode;

        // process dosage amount
        int dosageAmount = Integer.parseInt(mdElement.getElementsByTagName(XML.DOSAGE_AMOUNT.value())
                .item(0).getTextContent());
        medicineDosage.setDosageAmount(dosageAmount);

        //process dosage periodicity
        Node dpNode = mdElement.getElementsByTagName(XML.DOSAGE_PERIODICITY.value()).item(0);
        DosagePeriodicity dosagePeriodicity = getDosagePeriodicity(dpNode);
        medicineDosage.setDosagePeriodicity(dosagePeriodicity);

        return medicineDosage;
    }

    /**
     * Extracts DosagePeriodicity object from the answer XML node.
     *
     * @param dpNode
     *            DosagePeriodicity node.
     * @return DosagePeriodicity object.
     */
    private static DosagePeriodicity getDosagePeriodicity(Node dpNode) {
        DosagePeriodicity dosagePeriodicity = new DosagePeriodicity();
        Element dpElement = (Element) dpNode;

        // process dosage periodicity amount
        int dosagePeriodicityAmount = Integer.parseInt(dpNode.getTextContent());
        dosagePeriodicity.setDosagePeriodicityAmount(dosagePeriodicityAmount);

        //process periodicity type
        String periodicityType = dpElement.getAttribute(XML.PERIODICITY_TYPE.value());
        dosagePeriodicity.setPeriodicityType(periodicityType);

        return dosagePeriodicity;
    }

    // //////////////////////////////////////////////////////
    // Static util methods
    // //////////////////////////////////////////////////////

    /**
     * Creates and returns DOM of the Medicines container.
     *
     * @param medicines
     *            Medicines object.
     * @throws ParserConfigurationException
     */
    public static Document getDocument(Medicines medicines) throws ParserConfigurationException {

        // obtain DOM parser
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        // set properties for Factory

        // XML document contains namespaces
        dbf.setNamespaceAware(true);

        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.newDocument();

        // create root element
        Element medicinesElement = document.createElement(XML.MEDICINES.value());

        // add root element
        document.appendChild(medicinesElement);

        // add medicine elements
        for (Medicine medicine : medicines.getMedicines()) {

            // add medicine
            Element mElement = document.createElement(XML.MEDICINE.value());
            medicinesElement.appendChild(mElement);

            // add medicine name
            Element mnElement =
                    document.createElement(XML.MEDICINE_NAME.value());
            mnElement.setTextContent(medicine.getMedicineName());
            mElement.appendChild(mnElement);

            // add medicine group
            Element mgElement =
                    document.createElement(XML.MEDICINE_GROUP.value());
            mgElement.setTextContent(medicine.getMedicineGroup());
            mElement.appendChild(mgElement);

            // add medicine pharm
            Element mpElement =
                    document.createElement(XML.MEDICINE_PHARM.value());
            mpElement.setTextContent(medicine.getMedicinePharm());
            mElement.appendChild(mpElement);

            // add analogs
            for (String analogue : medicine.getMedicineAnalogs()) {
                Element aElement = document.createElement(XML.MEDICINE_ANALOG.value());
                aElement.setTextContent(analogue);

                mElement.appendChild(aElement);
            }

            // add medicine versions
            for (MedicineVersion medicineVersion : medicine.getMedicineVersions()) {

                Element mvElement = document.createElement(XML.MEDICINE_VERSION.value());

                // add medicine version name
                Element mvnElement =
                        document.createElement(XML.MEDICINE_VERSION_NAME.value());
                mvnElement.setTextContent(medicineVersion.getMedicineVersionName());
                mvElement.appendChild(mvnElement);

                // add pharm versions
                for (PharmVersion pharmVersion : medicineVersion.getPharmVersions()) {
                    Element pvElement = document.createElement(XML.PHARM_VERSION.value());

                    // add pharm version name
                    Element pvnElement =
                            document.createElement(XML.PHARM_NAME.value());
                    pvnElement.setTextContent(pharmVersion.getPharmName());
                    pvElement.appendChild(pvnElement);

                    // add pharm certificate
                    Element pcElement =
                            document.createElement(XML.PHARM_CERTIFICATE.value());
                    PharmCertificate pharmCertificate = pharmVersion.getPharmCertificate();
                    // add certificate number
                    Element pcnElement =
                            document.createElement(XML.CERTIFICATE_NUMBER.value());
                    pcnElement.setTextContent(Integer.toString(pharmCertificate.getCertificateNumber()));
                    pcElement.appendChild(pcnElement);
                    // add certificate start date
                    Element csdElement =
                            document.createElement(XML.CERTIFICATE_START_DATE.value());
                    csdElement.setTextContent(pharmCertificate.getCertificateStartDate().toString());
                    pcElement.appendChild(csdElement);
                    // add certificate end date
                    Element cedElement =
                            document.createElement(XML.CERTIFICATE_END_DATE.value());
                    cedElement.setTextContent(pharmCertificate.getCertificateEndDate().toString());
                    pcElement.appendChild(cedElement);
                    // add certificate organisation
                    Element coElement =
                            document.createElement(XML.CERTIFICATE_ORGANISATION.value());
                    coElement.setTextContent(pharmCertificate.getCertificateOrganisation());
                    pcElement.appendChild(coElement);

                    pvElement.appendChild(pcElement);

                    // add medicine package
                    Element medpacElement =
                            document.createElement(XML.MEDICINE_PACKAGE.value());
                    MedicinePackage medicinePackage = pharmVersion.getMedicinePackage();
                    // add package type
                    Element ptElement =
                            document.createElement(XML.PACKAGE_TYPE.value());
                    ptElement.setTextContent(medicinePackage.getPackageType());
                    medpacElement.appendChild(ptElement);
                    // add medicine amount
                    Element maElement =
                            document.createElement(XML.MEDICINE_AMOUNT.value());
                    maElement.setTextContent(Integer.toString(medicinePackage.getMedicineAmount()));
                    medpacElement.appendChild(maElement);
                    // add medicine price
                    Element medpElement =
                            document.createElement(XML.MEDICINE_PRICE.value());
                    medpElement.setTextContent(medicinePackage.getMedicinePrice().toString());
                    medpacElement.appendChild(medpElement);

                    pvElement.appendChild(ptElement);

                    pvElement.appendChild(ptElement);
                    // add medicine dosage
                    Element mdElement =
                            document.createElement(XML.MEDICINE_DOSAGE.value());
                    MedicineDosage medicineDosage = pharmVersion.getMedicineDosage();
                    // add dosage amount
                    Element daElement =
                            document.createElement(XML.DOSAGE_AMOUNT.value());
                    daElement.setTextContent(Integer.toString(medicineDosage.getDosageAmount()));
                    mdElement.appendChild(daElement);
                    // add dosage periodicity
                    Element dpElement =
                            document.createElement(XML.DOSAGE_PERIODICITY.value());
                    DosagePeriodicity dosagePeriodicity = medicineDosage.getDosagePeriodicity();
                    // set dosage periodicity amount
                    dpElement.setTextContent(Integer.toString(dosagePeriodicity.getDosagePeriodicityAmount()));
                    if(dosagePeriodicity.getPeriodicityType() != null) {
                        dpElement.setAttribute(XML.PERIODICITY_TYPE.value(),
                                dosagePeriodicity.getPeriodicityType());
                    }

                    mdElement.appendChild(dpElement);

                    pvElement.appendChild(mdElement);

                    mvElement.appendChild(pvElement);
                }
                mElement.appendChild(mvElement);
            }
        }

        return document;
    }

    /**
     * Saves Medicines object to XML file.
     *
     * @param medicines
     *            Medicines object to be saved.
     * @param xmlFileName
     *            Output XML file name.
     */
    public static void saveToXML(Medicines medicines, String xmlFileName)
            throws ParserConfigurationException, TransformerException {
        // Medicines -> DOM -> XML
        saveToXML(getDocument(medicines), xmlFileName);
    }

    /**
     * Save DOM to XML.
     *
     * @param document
     *            DOM to be saved.
     * @param xmlFileName
     *            Output XML file name.
     */
    public static void saveToXML(Document document, String xmlFileName)
            throws TransformerException {

        StreamResult result = new StreamResult(new File(xmlFileName));

        // set up transformation
        TransformerFactory tf = TransformerFactory.newInstance();
        javax.xml.transform.Transformer t = tf.newTransformer();
        t.setOutputProperty(OutputKeys.INDENT, "yes");

        // run transformation
        t.transform(new DOMSource(document), result);
    }

    public static void main(String[] args) throws Exception {

        // try to parse NOT valid XML document with validation on (failed)
        DOMController domContr = new DOMController(Constants.INVALID_XML_FILE);
        try {
            // parse with validation (failed)
            domContr.parse(true);
        } catch (SAXException ex) {
            System.err.println("====================================");
            System.err.println("XML not valid");
            System.err.println("Medicines object --> " + domContr.getMedicines());
            System.err.println("====================================");
        }

        // try to parse NOT valid XML document with validation off (success)
        domContr.parse(false);

        // we have Medicines object at this point:
        System.out.println("====================================");
        System.out.print("Here is the medicines: \n" + domContr.getMedicines());
        System.out.println("====================================");

        // save medicines in XML file
        Medicines medicines = domContr.getMedicines();
        DOMController.saveToXML(medicines, Constants.INVALID_XML_FILE + ".dom-result.xml");
    }
}
