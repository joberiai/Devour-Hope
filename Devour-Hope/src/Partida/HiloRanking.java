package Partida;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.swing.text.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class HiloRanking extends Thread{
    private Socket socket;
    ObjectInputStream ois;
    ObjectOutputStream oos;

    public HiloRanking(Socket s, ObjectInputStream ois, ObjectOutputStream oos){
        this.socket = s;
        this.ois = ois;
        this.oos = oos;
    }

    public void run(){
        try{
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse("src/xml/ranking.xml");

            oos.writeObject(doc);
            oos.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }
}
