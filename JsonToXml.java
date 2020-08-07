package com.example.demo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Scanner;
import javax.xml.bind.JAXB;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.springframework.util.ResourceUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

public class JsonToXml {
    public static String getStringFromDocument(Document doc) {
        try {
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);
            return writer.toString();
        } catch (TransformerException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    private static String getValue(String tag, Element element) {
        NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodes.item(0);
        return node.getNodeValue();
    }
    private static short getType(String tag, Element element) {
        NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodes.item(0);
        return element.getNodeType();
    }
    public static void main(String[] args) throws IOException {
        StringWriter sw = new StringWriter();
        MyObj mobj = new MyObj();
        mobj.setId(1);
        mobj.setName("shivaji");
        mobj.setValue(23.00);
        JAXB.marshal(mobj, sw);
        FileWriter fil = new FileWriter("D:\\Tutorials\\MyBlogs\\data.xml");
        fil.write(sw.toString());
        fil.close();
        System.out.println(sw);
        StringBuilder sb = new StringBuilder();
        try {
            File file = ResourceUtils.getFile("D:\\Tutorials\\MyBlogs\\data.xml");
            Scanner sc = new Scanner(file);
            while (sc.hasNext()) {
                System.out.println(sc.nextLine());
            }
            System.out.println(sc);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            // String xml = getStringFromDocument(doc);
            doc.getDocumentElement().normalize();
            System.out.println("root of xml file" + doc.getDocumentElement().getNodeName());
            NodeList nodes = doc.getElementsByTagName(doc.getDocumentElement().getNodeName());
            System.out.println("==========================" + nodes.getLength());
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    System.out.println("Id: " + getValue("id", element));
                    System.out.println("Name: " + getValue("name", element));
                    System.out.println("Value: " + getValue("value", element));
                    System.out.println("Id: " + getType("id", element));
                    System.out.println("Name: " + getType("name", element));
                    System.out.println("Value: " + getType("value", element));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlRootElement(name = "myObj")
    @XmlType
    public static class MyObj {
        @XmlAttribute(name = "id")
        private int id;
        @XmlAttribute(name = "name")
        private String name ;
        @XmlAttribute(name = "value")
        private double value;
        public int getId() {
            return id;
        }
        public void setId(int id) {
            this.id = id;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public double getValue() {
            return value;
        }
        public void setValue(double value) {
            this.value = value;
        }
    }
}
