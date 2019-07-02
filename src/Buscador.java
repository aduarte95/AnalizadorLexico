import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Vector;

import static java.lang.Double.parseDouble;

public class Buscador {

    //Los terminos que constituyan la consulta
    private String consulta[] = new String[10];
    //Las paginas que constituyan la respuesta
    public Vector<String> paginas = new Vector<>();
    //Los pesos de las paginas
    public Vector<Double> pesos = new Vector<>();

    Buscador(String[] consulta)
    {
        this.consulta = consulta;
    }

    /*
        Se tiene que mostrar el link a las paginas y a la que se tiene en caché

        La forma para recuperar los datos es:
        1. Buscar cada palabra en Postings
        2. Sacar el array de Strings que es la linea donde se encuentra
        3. Sacar el alias del archivo URL
        4. Agregarle al final ".html"
        5. Mostrar el link
         */

    //Recibe la direccion completa de un archivo y un parametro de busqueda (en este caso un termino)
    public void parseFile(String fileName,String searchStr) throws FileNotFoundException {
        Scanner scan = new Scanner(new File(fileName));
        int cont = 0;
        while(scan.hasNext()){
            String line = scan.nextLine().toLowerCase().toString();

            cont++;
            //System.out.println(cont);

            //Agrego un espacio para que busque solo esa palabra
            //Ejemplo, si busca "audio", que no busque "audiobook"
            if(line.contains(searchStr + " "))
            {
                String arrayPagina = line.replaceAll("\\s+", "&");
                //De esta forma queda en el [1] la pagina y en el [2] el peso
                String[] posting = arrayPagina.split("&");
                //System.out.println(posting[1] + " " + posting[2] + " " + cont);
                Double pesoIndividual = Double.parseDouble(posting[2]);
                for(int i=0; i<=pesos.size(); i++)
                {
                    if(!pesos.isEmpty())
                    {
                        if(pesos.get(i)<pesoIndividual)
                        {
                            //Verifica que la pagina ya no se encuentre en el vector "paginas"
                            if(!paginas.contains(posting[1] + ".html"))
                            {
                                //Se agrega en "pesos" el peso (ASI POR AHORA)
                                pesos.add(i, pesoIndividual);
                                //Se agrega en "paginas" el nombre de la pagina (se agrega el .html)
                                paginas.add(i, posting[1] + ".html");
                                i=pesos.size() + 1;
                            }
                        }
                        //Si llega al final del vector y no se ha guardado, se guarda al final
                        if(i+1 == pesos.size())
                        {
                            //Verifica que la pagina ya no se encuentre en el vector "paginas"
                            if(!paginas.contains(posting[1] + ".html"))
                            {
                                //Se agrega en "pesos" el peso (ASI POR AHORA)
                                pesos.add(i+1, pesoIndividual);
                                //Se agrega en "paginas" el nombre de la pagina (se agrega el .html)
                                paginas.add(i+1, posting[1] + ".html");
                                i=pesos.size() + 1;
                            }
                        }
                    }
                    //Si el vector "pesos" esta vacio se agrega el valor en el primer espacio
                    else
                    {
                        //Verifica que la pagina ya no se encuentre en el vector "paginas"
                        if(!paginas.contains(posting[1] + ".html"))
                        {
                            //Se agrega en "pesos" el peso (ASI POR AHORA)
                            pesos.add(0, pesoIndividual);
                            //Se agrega en "paginas" el nombre de la pagina (se agrega el .html)
                            paginas.add(0, posting[1] + ".html");
                            i=pesos.size() + 1;
                        }
                    }
                }
            }
        }
    }

    //Termina con todas las paginas que hay que mostrar en orden en el vector "respuestaAConsulta"
    public void responderConsulta()
    {
        for (String termino:consulta)
        {
            try
            {
                parseFile(System.getProperty("user.dir") +"\\Postings.txt", termino);
            }
            catch(Exception e)
            {
                System.out.println("El archivo no existe");
            }

        }
    }

}
