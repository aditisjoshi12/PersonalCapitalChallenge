package com.example.aditijoshi.personalcapital.NetworkUtils;

import java.io.IOException;

import java.io.StringReader;
import java.util.ArrayList;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.util.Log;

import com.example.aditijoshi.personalcapital.DataModel.Article;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XMLParser {
    private String title = "title";
    private String link = "link";
    private String description = "description";
    private String imageURL = "media:content";
    private String timeStamp = "pubDate";
    private String imageURLProperty = "url";

    public String getFeedTitle() {
        return feedTitle;
    }

    private String feedTitle = "";

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Article> parseRSSFeed(String input) {

        Document doc = getDomElement(input); // getting DOM element
        NodeList nl = doc.getElementsByTagName(title);
        if(nl.getLength()>0) {
            nl = nl.item(0).getChildNodes();
            if(nl.getLength()>0) {
                feedTitle = nl.item(0).getNodeValue();
            }
        }
        nl = doc.getElementsByTagName("item");
        ArrayList<Article> articles = new ArrayList<>();

// looping through all item nodes <item>
        for (int i = 0; i < nl.getLength(); i++) {
            String sTitle = getValue((Element) nl.item(i), title); // name child value
            String sLink = getValue((Element) nl.item(i), link); // cost child value

            String sDescription = getValue((Element) nl.item(i), description); // description child value
            String sImageURL = getValue((Element) nl.item(i), imageURL); // description child value
            String sTimeStamp = getValue((Element) nl.item(i), timeStamp); // description child value
            articles.add(new Article(sTitle, sDescription, sTimeStamp, sLink, sImageURL));

        }

        return articles;
    }

    public String getValue(Element item, String str) {
        NodeList n = item.getElementsByTagName(str);
        return this.getElementValue(n.item(0));
    }

    public final String getElementValue(Node elem) {
        Node child;
        if (elem != null) {
            if (elem.hasChildNodes()) {

                for (child = elem.getFirstChild(); child != null; child = child.getNextSibling()) {

                    if (child.getNodeType() == Node.TEXT_NODE) {
                        return child.getNodeValue();
                    } else if (child.getNodeType() == Node.CDATA_SECTION_NODE) {
                        return ((CharacterData) child).getData();
                    }
                }
            } else if (elem.getNodeName().equals(imageURL)) {
                NamedNodeMap attr = elem.getAttributes();
                Node nodeAttr = attr.getNamedItem(imageURLProperty);
                return nodeAttr.getNodeValue();
            }
        }
        return "";
    }

    public Document getDomElement(String xml) {
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {

            DocumentBuilder db = dbf.newDocumentBuilder();

            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc = db.parse(is);

        } catch (ParserConfigurationException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (SAXException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (IOException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        }
        // return DOM
        return doc;
    }

}
