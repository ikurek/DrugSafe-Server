package com.ikurek.drugsafeserver.dataupdater;

import com.ikurek.drugsafeserver.model.Drug;
import com.ikurek.drugsafeserver.model.Packaging;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

import static com.ikurek.drugsafeserver.dataupdater.DataUpdaterConstants.FILE_NAME;

public class DataSourceParser {

    // Parser elements
    private DocumentBuilderFactory documentBuilderFactory;
    private DocumentBuilder documentBuilder;
    private Document document;

    // List storing all POJOs
    private Set<Drug> listOfDrugs = new HashSet<>();
    private String creationDate = new String();

    // Temporary variables to awoid constant recreation
    private Drug tempDrug;
    private Packaging tempPackaging;
    private String tempSubstance;
    private Set<String> tempListOfSubstances;
    private Set<Packaging> tempListOfPackagings;


    public DataSourceParser() {
        documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Set<Drug> run() {
        File file = new File(FILE_NAME);

        try {
            document = documentBuilder.parse(file);
            parseDocument();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this.listOfDrugs;
    }

    public void parseDocument() {
        Element root = document.getDocumentElement();
        creationDate = root.getAttribute("stanNaDzien");
        NodeList productElements = root.getElementsByTagName("produktLeczniczy");

        for (int i = 0; i < productElements.getLength(); i++) {
            parseSingleProduct(productElements.item(i));
        }
    }

    public void parseSingleProduct(Node productNode) {
        // Validate if Node is XML element
        if (productNode.getNodeType() == Node.ELEMENT_NODE) {

            // Cast node as XML element
            Element productElement = (Element) productNode;

            // Rebuild temporary drug as empty
            tempDrug = new Drug();

            // Read basic drug data
            parseSingleDrugDataFromElement(productElement);

            // Parse substances
            parseSubstancesFromElement(productElement);

            // Parse packages
            parsePackagesFromElement(productElement);

            listOfDrugs.add(tempDrug);
        }

    }

    private void parseSingleDrugDataFromElement(Element productElement) {

        try {
            tempDrug.setPermissionNumber(Long.parseLong(productElement.getAttribute("numerPozwolenia")));
        } catch (NumberFormatException nfe) {
            tempDrug.setPermissionNumber(null);
        }

        try {
            tempDrug.setId(Long.parseLong(productElement.getAttribute("id")));
        } catch (NumberFormatException nfe) {
            tempDrug.setId(null);
        }

        tempDrug.setName(getProperAttributeValueFromElement(productElement, "nazwaProduktu"));
        tempDrug.setDrugType(getProperAttributeValueFromElement(productElement, "rodzajPreparatu"));
        tempDrug.setCommonName(getProperAttributeValueFromElement(productElement, "nazwaPowszechnieStosowana"));
        tempDrug.setAmmountOfSubstance(getProperAttributeValueFromElement(productElement, "moc"));
        tempDrug.setType(getProperAttributeValueFromElement(productElement, "postac"));
        tempDrug.setOwner(getProperAttributeValueFromElement(productElement, "podmiotOdpowiedzialny"));
        tempDrug.setProcedureType(getProperAttributeValueFromElement(productElement, "typProcedury"));
        tempDrug.setPermissionExpiry(getProperAttributeValueFromElement(productElement, "waznoscPozwolenia"));
        tempDrug.setAtc(getProperAttributeValueFromElement(productElement, "kodATC"));
    }

    private void parseSubstancesFromElement(Element productElement) {
        // Rebuild temporary variables
        tempSubstance = new String();
        tempListOfSubstances = new HashSet<>();

        // Find element that represents substances node
        Element substancesNode = (Element) productElement.getElementsByTagName("substancjeCzynne").item(0);

        // Iterate over all substances
        for (int i = 0; i < substancesNode.getElementsByTagName("substancjaCzynna").getLength(); i++) {
            tempSubstance = substancesNode.getElementsByTagName("substancjaCzynna").item(i).getTextContent();
            tempListOfSubstances.add(tempSubstance);
        }

        tempDrug.setSubstances(tempListOfSubstances);
    }

    private void parsePackagesFromElement(Element productElement) {
        // Rebuild temporary variables
        tempPackaging = new Packaging();
        tempListOfPackagings = new HashSet<>();

        // Find element that represents substances node
        Element substancesNode = (Element) productElement.getElementsByTagName("opakowania").item(0);

        // Iterate over all substances
        for (int i = 0; i < substancesNode.getElementsByTagName("opakowanie").getLength(); i++) {
            tempPackaging = new Packaging();
            parseSinglePackageFromElement((Element) substancesNode.getElementsByTagName("opakowanie").item(i));
            tempListOfPackagings.add(tempPackaging);
        }

        tempDrug.setPackaging(tempListOfPackagings);
    }

    private void parseSinglePackageFromElement(Element packageElement) {
        try {
            tempPackaging.setSize(Long.parseLong(packageElement.getAttribute("wielkosc")));
        } catch (NumberFormatException nfe) {
            tempPackaging.setSize(null);
        }

        try {
            tempPackaging.setEan(Long.parseLong(packageElement.getAttribute("kodEAN")));
        } catch (NumberFormatException nfe) {
            tempPackaging.setEan(null);
        }

        try {
            tempPackaging.setId(Long.parseLong(packageElement.getAttribute("id")));
        } catch (NumberFormatException nfe) {
            tempPackaging.setId(null);
        }

        switch (packageElement.getAttribute("skasowane")) {
            case "NIE":
                tempPackaging.setRemoved(false);
                break;

            case "TAK":
                tempPackaging.setRemoved(true);
                break;

            default:
                tempPackaging.setRemoved(null);
                break;
        }

        tempPackaging.setSizeUnit(getProperAttributeValueFromElement(packageElement, "jednostkaWielkosci"));
        tempPackaging.setCategory(getProperAttributeValueFromElement(packageElement, "kategoriaDostepnosci"));
        tempPackaging.setEuNumber(getProperAttributeValueFromElement(packageElement, "numerEu"));
        tempPackaging.setParallelDistributor(getProperAttributeValueFromElement(packageElement, "dystrybutorRownolegly"));
    }

    private String getProperAttributeValueFromElement(Element element, String attribute) {

        // Check if given attribute exists inside element
        if (element.hasAttribute(attribute)) {

            // Check if field is not empty
            if (!element.getAttribute(attribute).equals("") || !element.getAttribute(attribute).trim().isEmpty()) {

                // Return element if not
                return element.getAttribute(attribute);

            } else {

                return null;
            }

        } else {

            return null;
        }


    }
}
