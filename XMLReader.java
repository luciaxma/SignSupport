package signsupport;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by Lucia on 2017/10/02.
 */
public class XMLReader {

    ArrayList<Screen> screenArr = new ArrayList<>();

    public ArrayList<Screen> parse() {

        //try to read in the file
        try {

            File XmlFile = new File("/Users/Lucia/Dropbox/UCT 2017/CSC3003S/Capstone Project/Code/SignSupport/src/signsupport/elearnerselfstudy.xml");
            //Defines a factory API that enables applications to obtain a parser that produces DOM object trees from XML documents.
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(XmlFile);

            //need to normalize - read the stack overflow
            document.getDocumentElement().normalize();


            //list of lessons - it is reading the elements into the list fine
            NodeList nLessonList = document.getElementsByTagName("lesson");

            //list for the screens - it is reading the elements into the list fine, the error is somewhere else
            NodeList nScreenList = document.getElementsByTagName("screen");

            //lesson list iteration
            for (int temp = 0; temp < nLessonList.getLength(); temp++) {
                Node nNode = nLessonList.item(temp);
                //System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;


                }//end if
            }//end for loop through tree

            //screen list iteration
            for (int temp = 0; temp < nScreenList.getLength(); temp++) {

                Node nNode2 = nScreenList.item(temp);


                // create new screens for array
                Screen screen = new Screen();

                if (nNode2.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode2;

                    // add data to screen object
                    screen.setScreenID(eElement.getElementsByTagName("screenID").item(0).getTextContent());
                    screen.setVideoURL(eElement.getElementsByTagName("video").item(0).getTextContent());
                    screen.setVidCaption(eElement.getElementsByTagName("vid_caption").item(0).getTextContent());

                    // check to see if there is an image path
                    if (eElement.getElementsByTagName("image").item(0) != null) {
                        screen.setImagePath(eElement.getElementsByTagName("image").item(0).getTextContent());
                    }

                    screenArr.add(screen);

                }//end if
            }//end for list iteration
        }//end try

        //catch
        catch (Exception e) {
            e.printStackTrace();
        }//end catch

        /*System.out.println("_____________________________________________________________");
        System.out.println("Printing out Screen array");

        for (int i = 0; i < screenArr.size(); i++) {

            System.out.println(screenArr.get(i));
        }*/

        return screenArr;

    }


}

