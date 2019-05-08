import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Stream;

public class Analizador {
    private String directory;
    private BufferedWriter writer;
    private BufferedReader buffer;
    private TokenPersonalizado token;
    private AnalizadorLexicoDoc analizadorJFlex;
    private String formatoArchivoSalida = "%-30s %-12s %-20s%n";
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    private int freq, tf;
    private String currentFile = "current_file.txt";
    private List<Lexema> terminos;

    Analizador(String directory) {
        this.directory = directory;
        this.terminos = new LinkedList<Lexema>();
    }

    public void listeArchivos() {
        try(Stream<Path> paths = Files.walk(Paths.get(directory))) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    try {
                        analizar(filePath.toFile());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void analizar(File file) throws IOException{
        try {
            // Se trata de leer el archivo y analizarlo en la clase que se ha creado con JFlex
            buffer = new BufferedReader(new FileReader(file));
            analizadorJFlex = new AnalizadorLexicoDoc(buffer);

            while(true){
                // Obtener el token analizado y mostrar su información
                token = analizadorJFlex.yylex();
                if(!analizadorJFlex.existenTokens())
                    break;

                //System.out.println(token.toString()); //Imprime lo que analiza

                if((token.getToken()).equals("Termino")) { //Si es un término lo escribe en el archivo.

                    Lexema termino = new Lexema(token.getLexema());

                    if(!terminos.contains(termino)) {
                        terminos.add(termino);
                    }
                }
            }

            escribaTok(file);
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private void escribaTok(File file) {
        try {
            writer = new BufferedWriter(new FileWriter(nuevoNombre(file) + ".tok")); //Escribe en archivo todos los términos para contar frecuencias después

            int max = getTf();

            ListIterator<Lexema> iterator = terminos.listIterator();
            Lexema lexema;

            while (iterator.hasNext()) {
                lexema = iterator.next();
                writer.write(String.format(formatoArchivoSalida, lexema.getTermino(), lexema.getFreq(), df2.format(lexema.getTf(max))));
            }

            writer.close();
            terminos.clear();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private int getTf() {
        ListIterator<Lexema> iterator = terminos.listIterator();
        Lexema lexema;
        int max = 0;

        while (iterator.hasNext()) {
            lexema = iterator.next();

            if(lexema.getFreq() > max) {
                max = lexema.getFreq();
            }
        }

        return max;
    }


    //Tomado de: http://www.technicalkeeda.com/java-tutorials/get-filename-without-extension-using-java
    private static String nuevoNombre(File file) {
        String fileName = "";

        try {
            if (file != null && file.exists()) {
                String name = file.getName();
                fileName = name.replaceFirst("[.][^.]+$", ""); //+ ".tok";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileName;

    }
}