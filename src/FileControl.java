import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileControl {
    private String directory;
    private BufferedWriter writer;
    private BufferedReader buffer;
    private TokenPersonalizado token;
    private AnalizadorLexicoDoc analizadorJFlex;

    FileControl (String directory) {
        this.directory = directory;
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
            writer = new BufferedWriter(new FileWriter(nuevoNombre(file))); //Escribe en archivo limpio

            while(true){
                // Obtener el token analizado y mostrar su información
                token = analizadorJFlex.yylex();
                if(!analizadorJFlex.existenTokens())
                    break;

                //System.out.println(token.toString()); //Imprime lo que analiza

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

    //Tomado de: http://www.technicalkeeda.com/java-tutorials/get-filename-without-extension-using-java
    private static String nuevoNombre(File file) {
        String fileName = "";

        try {
            if (file != null && file.exists()) {
                String name = file.getName();
                fileName = name.replaceFirst("[.][^.]+$", "") + "_clean.txt";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileName;

    }
}