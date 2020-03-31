import java.io.*;
import java.util.*;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.net.URL;
import org.xml.sax.InputSource;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
public class BusquedaNoticias extends Application{
	
	
	int i = 0;

	private Label messageLbl = new Label("Pulsa siguiente para leer la primera noticia");
	public static List<Noticia> notice = new ArrayList<Noticia>();//es static y public porque la usa en el main
	int n=0;
	String s;
	int aa;
	

	public static void main(String[] args) throws Exception  {
		
				try {
				  BusquedaNoticias bn = new BusquedaNoticias();//para poder llamar a la clase userHandler tengo q crear este obj del mismo main
         SAXParserFactory factory = SAXParserFactory.newInstance();
         SAXParser saxParser = factory.newSAXParser();
         UserHandler handler = bn.new UserHandler();

         StringBuilder texto = new StringBuilder();
         String urlText = "http://ep00.epimg.net/rss/tags/noticias_mas_vistas.xml";
         
            BufferedReader in = null;
            URL url = new URL(urlText);
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine;
            while((inputLine = in.readLine()) != null) {
               texto.append(inputLine);
            }        

            InputSource is = new InputSource(new StringReader(texto.toString()));
       saxParser.parse(is, handler);
       notice = handler.getNotlist();  

        Application.launch(args);
      } catch (Exception e) {
         e.printStackTrace();
      }

	}

	@Override
	public void start(Stage stage) throws Exception{
		Label elp = new Label("EL PAIS");
		TextField t = new TextField();
	  	nuevanoticia(0,t.getText());
		Button siguiente = new Button("Siguiente");
		Button anterior = new Button("Anterior");
		Button buscar = new Button("Buscar");
    stage.setOnCloseRequest(event -> { System.out.println("Un placer mantenerlo informado, Gracias por confiar en El Pais. ");

     });
		siguiente.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override public void handle(ActionEvent e) 
            {
             i++;
             aa=1;
             nuevanoticia(i,t.getText());   
            }
        });
		
		anterior.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override public void handle(ActionEvent e) 
            {
               if(i!=0){
                  i--;
                  aa=0;
                  nuevanoticia(i,t.getText());}
            }
        });
        

        buscar.setOnAction(new EventHandler<ActionEvent>()
        	{
        		@Override public void handle(ActionEvent e){
        		n=1;
            aa=1;
        		
        		
        		nuevanoticia(0,t.getText());}
        	});
		

		HBox hbox=new HBox();
		hbox.getChildren().addAll(siguiente, anterior);
		hbox.setSpacing(15);
		HBox hbox2 = new HBox();
		hbox2.getChildren().addAll(t,buscar);
		VBox vbox = new VBox();
      	vbox.getChildren().addAll(elp,messageLbl,hbox, hbox2);
      	vbox.setSpacing(15);
      	vbox.setMinSize(150,100);
	  	vbox.setStyle("-fx-padding: 10;" +
	                "-fx-border-style: solid inside;" +
	                "-fx-border-width: 2;" +
	                "-fx-border-insets: 5;" +
	                "-fx-border-radius: 5;" +
	                "-fx-border-color: red;");

	  	Scene scene = new Scene(vbox);
      	stage.setTitle("Buscador de Noticias de El País"); 
      	stage.setScene(scene);
      	stage.show();



					

	  				}
	  			

	public void printMessage(String message)
    {
        // Set the Text of the Label
        messageLbl.setText(message);
    }

	public void nuevanoticia(int num,String s) {
		int v=0;
		if(num>notice.size()){
			printMessage("No hay más noticias");
			i--;
		}
		if(n==0){
      if(num==notice.size()-1){
                     printMessage("No hay más noticias");
                      num --;
                      
                    }else{
         printMessage(notice.get(i).toString());}
         }else if(n==1){
            	
            	String REG_EXP = ".*"+s+".*";
            	
            	while(v==0){
            	if((!notice.get(num).getTitle().matches(REG_EXP))&&(!notice.get(num).getDescription().matches(REG_EXP))){
            		if(aa==1){
                  if(num==notice.size()-1){
                     printMessage("No hay más noticias");
                      num --;
                      v=1;
                    }else {
            		num++;}}
            		else if(aa==0){
            			num--;
            		}
            		i=num;
            		System.out.println(i);
            		
            	}else if((notice.get(num).getTitle().matches(REG_EXP))||(notice.get(num).getDescription().matches(REG_EXP))){
            		printMessage(notice.get(num).toString());
            		v=1;
            		i=num;
            	}

            }
            }
    }

	class UserHandler extends DefaultHandler {
   boolean btitle = false;
   boolean bdescription = false;
   boolean bitem = false;
   private Noticia noticia; // = new Noticia();
   private List<Noticia> notlist = new ArrayList<Noticia>();
   private StringBuilder data = null;


   @Override
   public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if (qName.equalsIgnoreCase("item")) {
         bitem = true;
              noticia = new Noticia();
        }

        if ((qName.equalsIgnoreCase("title")) && (bitem)) {
        	
                        data = new StringBuilder();
                        btitle = true;           
         }
        if ((qName.equalsIgnoreCase("description")) && (bitem)) {
                        data = new StringBuilder();
         bdescription = true;
        }
   }

   @Override
   public void endElement(String uri, String localName, String qName) throws SAXException {
      if (qName.equalsIgnoreCase("item") && bitem) {
         notlist.add(noticia);
        bitem = false;
      }
      if (qName.equalsIgnoreCase("title") && bitem && btitle) {
        noticia.setTitle(data.toString());
        btitle = false;
      }
      if (qName.equalsIgnoreCase("description") && bitem && bdescription) {
        noticia.setDescription(data.toString());
        bdescription = false;
      }
   }

      @Override
      public void characters(char ch[], int start, int length) throws SAXException {
          if(btitle || bdescription) {
          			data.append(new String(ch, start, length));
          } 
      }

   public List<Noticia> getNotlist() {
         return notlist;
   }
}


	
}
