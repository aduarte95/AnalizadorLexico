import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
 
public class Main {
    public static void main(String[] args) {
 
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("clean_text.txt"));; //Escribe en archivo limpio
            String archivo;
            String option = "-d"; //Por defecto se revisa un documento
            
            if (args.length >= 2) { //Sino hay argumentos o los suficientes, analiza el docuemnto de prueba.
                archivo = args[0];
                option = args[1];
            } else {
                archivo = "prueba.txt";
            }
           
            // Se trata de leer el archivo y analizarlo en la clase que se ha creado con JFlex
            BufferedReader buffer = new BufferedReader(new FileReader(archivo));
            AnalizadorLexicoDoc analizadorJFlex = new AnalizadorLexicoDoc(buffer);

            while(true){
                // Obtener el token analizado y mostrar su información
                TokenPersonalizado token = analizadorJFlex.yylex();
                
                if(!analizadorJFlex.existenTokens())
                    break;

                System.out.println(token.toString()); //Imprime lo que analiza
                
                if((token.getToken()).equals("Termino")) { //Si es un término lo escribe en el archivo.
                    writer.write(token.getLexema());
                    writer.newLine();
                }
            }
            
            writer.close(); 
        
        } 
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}