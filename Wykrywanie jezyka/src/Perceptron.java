import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Perceptron {

    String name;
    double[] wagi;
    double n;
    double theta = 3.0;



    public Perceptron(String name) {
        this.name = name;
        this.wagi = new double[26];
        double theta = 3.0;
        n = 0.1;

        for (int i = 0; i < this.wagi.length; i++) {
            this.wagi[i] = Math.random() * 10;
        }


    }


    public double policzNet(Jezyk jezyk) {
        double net = 0;


        for (int i = 0; i < this.wagi.length; i++) {
            net += jezyk.wystapienia_liter[i] * this.wagi[i];
        }

        return net - this.theta;
    }

    public boolean sprawdzPojPrzyp(Jezyk jezyk) {
        double net = policzNet(jezyk);
        if (net >= 0) {
            if (this.name.equals(jezyk.nazwaJezyka)) {

                return true;
            } else {
                return false;
            }
        } else {
            if (this.name.equals(jezyk.nazwaJezyka)) {
                return false;
            } else {
                return true;
            }
        }
    }

    public void learning(List<Jezyk> listaJezykow) {
        for (Jezyk j : listaJezykow) {
            boolean powtorz = true;
            while (powtorz) {
                boolean sprawdzenie = sprawdzPojPrzyp(j);
                double y = policzNet(j) >= 0 ? 1 : 0;
                int val = j.nazwaJezyka.equals(this.name) ? 1 : 0;
                for (int i = 0; i < this.wagi.length; i++) {
                    this.wagi[i] = this.wagi[i] + this.n * (val - y) * j.wystapienia_liter[i];
                    this.theta = this.theta - this.n * (val - y);

                }

                if (sprawdzenie) {
                    powtorz = false;

                }

            }
        }
    }

    public String rozpoznajJezyk(Jezyk jezyk) {
        double net = policzNet(jezyk);
        if (net >= 0) {
            return this.name;
        }else return "";
    }




}
