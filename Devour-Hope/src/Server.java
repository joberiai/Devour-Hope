import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) {
        try(ServerSocket server = new ServerSocket(55555)){
            CyclicBarrier barrera = new CyclicBarrier(4);
            ExecutorService pool = Executors.newFixedThreadPool(4);

            while (true){
                try{
                    // Offline
                    Socket s = server.accept();
                    ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                    ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());

                    int n = (Integer) ois.readObject();

                    switch (n){
                        case 1:
                            // Offline

                            break;
                        case 2:
                            // Offline

                            break;
                        case 3:
                            // Online
                            HiloOnline hiloOnline = new HiloOnline(s, ois, oos, barrera);

                            pool.execute(hiloOnline);

                            break;
                        case 4:
                            // Ranking
                            HiloRanking hiloRanking = new HiloRanking(s, ois, oos);

                            pool.execute(hiloRanking);

                            break;
                        case 5:

                            break;
                    }

                    //s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updRanking(Jugador winner){
        try{
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse("src\\xml\\ranking.xml");

            Element root = doc.getDocumentElement();
            NodeList list = root.getElementsByTagName("ganador");

            boolean cambiado = false;
            if(winner instanceof JugadorReal){
                int i = 0;
                while (i < list.getLength() && !cambiado){
                    Element ganador = (Element) list.item(i);

                    if (ganador.getElementsByTagName("nombre").item(0).getTextContent() == winner.getUsuario()){
                        int vict = Integer.parseInt(ganador.getElementsByTagName("numVictorias").item(0).getTextContent());
                        vict ++;

                        ganador.getElementsByTagName("numVictorias").item(0).setTextContent(String.valueOf(vict));
                        cambiado = true;
                    }

                    i ++;
                }

                if (!cambiado){
                    Element newGan = doc.createElement("ganador");
                    Element name = doc.createElement("nombre");
                    Element nVict = doc.createElement("numVictorias");

                    name.setTextContent(winner.getUsuario());
                    nVict.setTextContent("1");

                    root.appendChild(newGan);
                    newGan.appendChild(name);
                    newGan.appendChild(nVict);
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            FileWriter writer = new FileWriter("src\\xml\\ranking.xml");
            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }
}
