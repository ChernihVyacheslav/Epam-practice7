package ua.nure.chernykh.practice7;

/**
 * Entry point for practice 7 (simple version).
 * @author V.Chernykh
 *
 */
public class Main {
    public static void usage() {
        System.out.println("java ua.nure.chernykh.practice7.Main xmlFileName");
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            usage();
            return;
        }

        String xmlFileName = args[0];
        System.out.println("Input ==> " + xmlFileName);

        ////////////////////////////////////////////////////////
        // DOM
        ////////////////////////////////////////////////////////

        // get
//        DOMController domController = new DOMController(xmlFileName);
//        domController.parse(true);
//        Medicines medicines = domController.getMedicines();
//
//        // sort (case 1)
////        Sorter.sortMedicinesByMedicineName(test);
//
//        // save
//        String outputXmlFile = "output.dom.xml";
//        DOMController.saveToXML(medicines, outputXmlFile);
//        System.out.println("Output ==> " + outputXmlFile);
//
//        ////////////////////////////////////////////////////////
//        // SAX
//        ////////////////////////////////////////////////////////
//
//        // get
//        SAXController saxController = new SAXController(xmlFileName);
//        saxController.parse(true);
//        medicines = saxController.getMedicines();
//
//        // sort  (case 2)
////        Sorter.sortMedicinesByMedicineGroup(test);
//
//        // save
//        outputXmlFile = "output.sax.xml";
//
//        // other way:
//        DOMController.saveToXML(medicines, outputXmlFile);
//        System.out.println("Output ==> " + outputXmlFile);
//
//        ////////////////////////////////////////////////////////
//        // StAX
//        ////////////////////////////////////////////////////////
//
//        // get
//        STAXController staxController = new STAXController(xmlFileName);
//        staxController.parse();
//        medicines = staxController.getMedicines();
//
//        // sort  (case 3)
////        Sorter.sortPharmVersionsByPackageType(test);
//
//        // save
//        outputXmlFile = "output.stax.xml";
//        DOMController.saveToXML(medicines, outputXmlFile);
//        System.out.println("Output ==> " + outputXmlFile);
    }

}