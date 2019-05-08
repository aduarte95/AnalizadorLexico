public class Lexema {
    private String termino;
    private int frecuencia;

    Lexema(String termino) {
        this.termino = termino;
        this.frecuencia = 1;
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

    public double getTf(double max){
        return frecuencia/max;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equal = this.termino.equals(((Lexema) obj).termino);

            if (equal) {
                ((Lexema) obj).sumeFreq();
            }


        return equal;
    }


}
