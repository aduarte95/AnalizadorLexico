import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.*;

public class TokParser {

    private BufferedWriter writer;
    private BufferedReader buffer;
    private String formatoArchivoSalidaWtd = "%-30s %-20s%n";
    private String formatoArchivoSalidaIndice = "%-30s %-12s %-12s%n";
    private String formatoArchivoSalidaPostings = "%-30s %-30s %-20s%n";
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    private List<EntradaPostings> entradasPostings;
    File[] listOfFiles;
    private List<Lexema> terminos;
    private List<Lexema> vocabulario;
    private int totalDocumentos;
    private String listUrl[] = new String[751];
    private String listColeccion[] = new String[751];

    TokParser() {
        this.entradasPostings = new ArrayList<>();
        this.terminos = new LinkedList<>();
        this.vocabulario = new LinkedList<>();
        this.totalDocumentos = 0;
    }

    public void crearWTD() {
        File folder = new File(System.getProperty("user.dir") + "\\src\\TokFiles" );
        System.out.println(System.getProperty("user.dir") + "\\src\\TokFiles");
        listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {

            if (listOfFiles[i].isFile()) {
                listColeccion[i] = listOfFiles[i].getName();
                System.out.println(listOfFiles[i].getName() + "\n");
                try{

                    String fileAlias = listOfFiles[i].getName().split("\\.")[0];
                    OutputStream outputStream = new FileOutputStream(System.getProperty("user.dir") +"\\src\\WtdFiles\\" + fileAlias + ".wtd");
                    Writer ostream = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
                    writer = new BufferedWriter(ostream);


                    Reader istream = new InputStreamReader(new FileInputStream(listOfFiles[i]), StandardCharsets.UTF_8);
                    buffer = new BufferedReader(istream);
                    String line = buffer.readLine();
                    while (line != null)
                    {
                        String[] parts = line.split("\\s+");
                        float f = Float.parseFloat(parts[1]);
                        float idf = Float.parseFloat(parts[2]);
                        float w = f*idf;

                        EntradaPostings entrada = new EntradaPostings(parts[0], fileAlias, w);
                        entradasPostings.add(entrada);
                        writer.write(String.format(formatoArchivoSalidaWtd, parts[0], df2.format(w)));
                        line = buffer.readLine();
                    }
                }
                catch (Exception e) {
                    System.out.println(e.toString());
                }
            }

        }
    }

    public void crearIndice(){

        String currentWord;
        String lastWord = "";
        int lineCount = 0;
        int currentLine = 1;
        int firstAppearence = 1;
        try {
            OutputStream outputStream = new FileOutputStream(System.getProperty("user.dir") +"\\src\\Indice.txt");
            Writer ostream = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
            writer = new BufferedWriter(ostream);


            Reader istream = new InputStreamReader(new FileInputStream(System.getProperty("user.dir") + "\\src\\Postings.txt"), StandardCharsets.UTF_8);
            buffer = new BufferedReader(istream);
            String line = buffer.readLine();

            while (line != null)
            {

                currentWord = line.split("\\s+")[0];
                if(!currentWord.equals(lastWord)){
                    writer.write(String.format(formatoArchivoSalidaIndice, lastWord, firstAppearence, lineCount));
                    lineCount = 0;
                    lastWord = currentWord;
                    firstAppearence = currentLine;
                }
                lineCount++;
                currentLine++;
                line = buffer.readLine();
            }
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void crearPostings(){
        Collections.sort(entradasPostings, new Comparator<EntradaPostings>() {
            @Override
            public int compare(EntradaPostings o1, EntradaPostings o2) {

                String x1 = o1.termino;
                String x2 = o2.termino;
                int sComp = x1.compareTo(x2);

                if (sComp != 0) {
                    return sComp;
                }

                x1 = o1.alias;
                x2 = o2.alias;
                return x1.compareTo(x2);
            }
        });

        try{
            OutputStream outputStream = new FileOutputStream(System.getProperty("user.dir") +"\\src\\Postings.txt");
            Writer ostream = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
            writer = new BufferedWriter(ostream);

            for(int i = 0; i < entradasPostings.size(); i++){

                writer.write(String.format(formatoArchivoSalidaPostings, entradasPostings.get(i).termino, entradasPostings.get(i).alias, entradasPostings.get(i).w));
            }

        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }

}