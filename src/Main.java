import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        /*
        String directory;
        Analizador analizador;

        if (args.length == 1) {
            directory = args[0];
        } else {
            directory = "documents2/";
        }

        analizador = new Analizador(directory);
        analizador.listeArchivos();

        TokParser tokParser = new TokParser();
        tokParser.crearWTD();
        tokParser.crearPostings();
        tokParser.crearIndice();
        */


        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("Escriba su consulta:");
        String[] terminos = reader.nextLine().split(" ");
        for (String word: terminos)
        {
            System.out.println(word);
        }
        
        /* POR CONSOLA 
        String[] terminos = args[0].split(" ");
        for (String word: terminos)
        {
            System.out.println(word);
        }*/

        Buscador buscador = new Buscador(terminos);
        buscador.responderConsulta();
        System.out.println("Las pesos que devuelve como respuesta son \n" + buscador.pesos);
        System.out.println("Las paginas que devuelve como respuesta son \n" + buscador.paginas);

        reader.close();

        try
        {
            FileWriter fw=new FileWriter(System.getProperty("user.dir") +"\\RespuestaConsulta.txt");
            for(int i=0; i<buscador.paginas.size(); i++)
            {
                fw.write(buscador.getUrlPagina(buscador.paginas.get(i)));
                fw.write("\r\n");
            }
            fw.close();
        }
        catch(Exception e){System.out.println(e);}
    }
}
