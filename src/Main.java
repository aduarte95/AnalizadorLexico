import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
 
public class Main {
    public static void main(String[] args) {
 
        try {
            BufferedWriter writer;
            TokenPersonalizado token;
            String archivo;
            int cont = 0;
            int terminos = 0;
            
            if (args.length == 1) {
                archivo = args[0];
            } else {
                archivo = "prueba.txt";
            }
			
            // Se trata de leer el archivo y analizarlo en la clase que se ha creado con JFlex
            BufferedReader buffer = new BufferedReader(new FileReader(archivo));
			AnalizadorLexicoDoc analizadorJFlex = new AnalizadorLexicoDoc(buffer);
			writer = new BufferedWriter(new FileWriter("clean_text_doc.txt"));; //Escribe en archivo limpio
                
			while(true){
				// Obtener el token analizado y mostrar su información
				token = analizadorJFlex.yylex();
				cont++;
				if(!analizadorJFlex.existenTokens())
					break;

				System.out.println(token.toString()); //Imprime lo que analiza

				if((token.getToken()).equals("Termino")) { //Si es un término lo escribe en el archivo.
					writer.write(token.getLexema());
					writer.newLine();
					terminos++;
				}
            }
            writer.close(); 
            
            System.out.println("\nTotal de tokens: " + cont);
            System.out.println("Terminos escritos: " + terminos);
            
        } 
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
