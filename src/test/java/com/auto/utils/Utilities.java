package com.auto.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Utilities {
    public static final String ENVIRONMENT = DataUtils.getValueConf("serenity.api.environment").toLowerCase();

    public static String getFilePathFromDataConf(String config) {
        return DataUtils.PATH_RESOURCE + getValueOfDataConf(config);
    }

    public static String getContentsFile(String path) throws IOException {
        try {
            String filePath = Utilities.class.getResource(path).getPath();
            return FileUtils.readFileToString(new File(filePath), String.valueOf(StandardCharsets.UTF_8));
        } catch (Exception e) {
            InputStream in = Utilities.class.getResourceAsStream(path);
            return IOUtils.toString(in, String.valueOf(StandardCharsets.UTF_8));
        }
    }

    public static InputStream getContentAsStream(String path) {
        try {
            return new FileInputStream(Utilities.class.getResource(path).getPath());
        } catch (IOException e) {
            return Utilities.class.getResourceAsStream(path);
        }
    }

    public static String getValueOfDataConf(String path) {
        return readValueFromXMLFile(path, "environments/datasource.xml");
    }

    public static String getEnvServiceValueOfDataConf(String key) {
        String path = "environment." + Utilities.ENVIRONMENT + "." + key;
        return getValueOfDataConf(path);
    }

    public static String getDBInfo(String path) {
        return readValueFromXMLFile(path, "environments/database.xml");
    }

    public static Map<String, Node> readNodeValueFromXMLFile(String fileName, String path) throws Exception {
        Map<String, Node> nodeSet = new HashMap<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc1 = builder.parse(getContentAsStream("/" + fileName));
            XPath xpath = XPathFactory.newInstance().newXPath();
            XPathExpression expression = xpath.compile(path);
            NodeList nList = (NodeList) expression.evaluate(doc1.getDocumentElement(), XPathConstants.NODESET);
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                String value = nNode.getAttributes().getNamedItem("name").getNodeValue();
                nodeSet.put(value, nNode);
            }
        } catch (Exception ex) {
            throw new Exception("Cannot find item " + path + " in " + fileName + ". ", ex);
        }
        return nodeSet;
    }

    public static String readValueFromXMLFile(String path, String fileName) {
        String[] item = path.split("\\.");
        String value = null;
        try {
            Map<String, Node> nodeSet = readNodeValueFromXMLFile(fileName, "//type[@name='" + item[0] + "']//object");
            Node node = nodeSet.get(item[1]);
            NodeList nodeList = ((Element) node).getElementsByTagName("attribute");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node nNode = nodeList.item(i);
                if (nNode.getAttributes().getNamedItem("name").getNodeValue().equals(item[2])) {
                    value = nNode.getAttributes().getNamedItem("value").getNodeValue();
                    break;
                }
            }
        } catch (Exception ex) {
            try {
                throw new Exception("Cannot find item " + path + " in " + fileName + ". ", ex);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return value;
    }

    public
    static String formatBlankSpace(String s) {
        return s.replaceAll("[ \\s+\\n]", "");
    }
}
