package com.abbtech.service;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.util.ArrayList;

@Service
public class CurrentService {
    public String getRate(String date, String code) {
        try {
            String url = "https://www.cbar.az/currencies/" + date + ".xml";


            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new URL(url).openStream());

            var valutes = doc.getElementsByTagName("Valute");

            for (int i = 0; i < valutes.getLength(); i++) {
                var val = valutes.item(i);
                if (val.getAttributes()
                        .getNamedItem("Code")
                        .getTextContent()
                        .equalsIgnoreCase(code)) {

                    String nominal = val.getChildNodes()
                            .item(3)  // Nominal tag
                            .getTextContent();

                    String value = val.getChildNodes()
                            .item(5)  // Value tag
                            .getTextContent();

                    return code + " = " + value + " AZN (Nominal: " + nominal + ")";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Qiymet tapilmadi " + code;
    }

}
