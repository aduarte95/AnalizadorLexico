import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.*;

public class Analizador {
    private String directory;
    private BufferedWriter writer;
    private BufferedReader buffer;
    private BufferedReader reader;
    private TokenPersonalizado token;
    private AnalizadorLexicoDoc analizadorJFlex;
    private String formatoArchivoSalida = "%-30s %-12s %-20s%n";
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    private List<Lexema> terminos;
    private List<Lexema> vocabulario;
    private int totalDocumentos;
    private String listUrl[] = new String[751];
    private String listColeccion[] = new String[751];

    Analizador(String directory) {
        this.directory = directory;
        this.terminos = new LinkedList<>();
        this.vocabulario = new LinkedList<>();
        this.totalDocumentos = 0;
    }

    public void listeArchivos() {
        try{
            //Lee URLs.txt (debe estar en el mismo directorio)
            URL path = Analizador.class.getResource("URLs.txt");
            File f = new File(path.getFile());
            reader = new BufferedReader(new FileReader(f));
            int cnt = 0;
            String line = reader.readLine();
            while (line != null)
            {

                //Primero se divide la linea por el primer espacio que se encuentra en la linea
                String[] parts = line.split(" ");
                //Luego se asegura de que termine con .html (hay algunos que no)
                //Se guardan en listURL[]
                if(!parts[0].substring(parts[0].length() - 5).equals(".html"))
                {
                    //System.out.println(parts[0]);
                    //System.out.println(parts[0].substring(parts[0].length() - 5));
                    parts[0] += ".htm";
                }
                listUrl[cnt] = parts[0];
                System.out.println("De URLs: " + listUrl[cnt] + "\n");
                //Lee la siguiente linea
                line = reader.readLine();
                cnt++;
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

        //Se guardan los nombres de los archivos disponibles en Coleccion en listColeccion[]
        File folder = new File(directory);
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                listColeccion[i] = listOfFiles[i].getName();
                System.out.println("De Coleccion: " + listOfFiles[i].getName() + "\n");
            }
        }

        //Se revisa si cada valor de listUrl[] esta en listColeccion[]
        //Si lo esta, se ejecuta el analisis de dicho archivo
        for (int i = 0; i < listUrl.length-1; i++) {
            if(Arrays.stream(listColeccion).anyMatch(listUrl[i]::equals))
            {
                try {
                    ++totalDocumentos;
                    //Directorio + El nombre del archivo
                    //System.out.println("OJO: " +  directory + listUrl[i]);
                    analizar(new File(directory + listUrl[i]));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        escribaVocabulario();

        /*
        try(Stream<Path> paths = Files.walk(Paths.get(directory))) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    try {
                        ++totalDocumentos;
                        analizar(filePath.toFile());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            escribaVocabulario();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }

    public void analizar(File file) throws IOException{
        try {
            // Se trata de leer el archivo y analizarlo en la clase que se ha creado con JFlex
            Reader fstream = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
            buffer = new BufferedReader(fstream);
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

                        termino = new Lexema(token.getLexema());
                        if(!vocabulario.contains(termino)) {
                            vocabulario.add(termino);
                        }
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
            Writer fstream = new OutputStreamWriter(new FileOutputStream("TokFiles/" + nuevoNombre(file) + ".tok"), StandardCharsets.UTF_8);
            writer = new BufferedWriter(fstream); //Escribe en archivo todos los términos para contar frecuencias después

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

    private void escribaVocabulario() {
        try {

                Writer fstream = new OutputStreamWriter(new FileOutputStream("vocabulario.txt"), StandardCharsets.UTF_8);
                BufferedWriter writerVocabulario = new BufferedWriter(fstream); //Escribe en archivo todos los términos para contar frecuencias después

            ListIterator<Lexema> iterator = vocabulario.listIterator();
            Lexema lexema;

            while (iterator.hasNext()) {
                lexema = iterator.next();
                writerVocabulario.write(String.format(formatoArchivoSalida, lexema.getTermino(), lexema.getFreq(), df2.format(getIdf(lexema.getFreq()))));
            }

            writerVocabulario.close();
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

    private double getIdf(double aparicion) {
        return Math.log10(totalDocumentos/aparicion);
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