public class Lexema {
    private String termino;
    private int frecuencia;

    Lexema(String termino) {
        this.termino = termino;
        this.frecuencia = 0;
    }

    public void sumeFreq(){
        ++this.frecuencia;
    }

    public String getTermino() {
        return termino;
    }

    public int getFreq() {
        return frecuencia;
    }

    public int getTf(){
        return 0;
    }
}
