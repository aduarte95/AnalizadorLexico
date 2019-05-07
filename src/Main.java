import java.io.BufferedWriter;

public class Main {
    public static void main(String[] args) {
        String directory;
        FileControl fileControl;

        if (args.length == 1) {
            directory = args[0];
        } else {
            directory = "documents2";
        }

        fileControl = new FileControl(directory);

        fileControl.listeArchivos();
    }
}
