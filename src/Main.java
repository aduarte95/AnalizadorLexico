public class Main {
    public static void main(String[] args) {
        String directory;
        Analizador analizador;

        if (args.length == 1) {
            directory = args[0];
        } else {
            directory = "C:/Users/alexa/OneDrive/Recuperación de Información/Investigacion/AnalizadorLexico/src/documents2";
        }

        analizador = new Analizador(directory);

        analizador.listeArchivos();
    }
}
