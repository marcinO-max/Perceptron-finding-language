import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {



    public static void main(String[] args) {

        List<Jezyk> jezyki = new ArrayList<>();
        List <String> listaPlikow = new ArrayList<>();
        List <Perceptron> listP = new ArrayList<>();
        String zczytywanie= "";

        File f = new File("jezyki Nai");
        listaPlikow.addAll(Arrays.asList(f.list()));
        for (String s : listaPlikow){
            File x = new File(f.getPath() + "/" + s);
            Perceptron p =new Perceptron(s);
            listP.add(p);
            for(String tekst : Arrays.asList(x.list())){
                try {
                    zczytywanie = "";
                    Scanner scanner = new Scanner(new File(x.getPath()+"/" + tekst));
                    while(scanner.hasNext()){
                        zczytywanie += scanner.nextLine();

                    }
                    jezyki.add(utworz_jezyk(zczytywanie,s));

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        //uczenie perceptronu


        for (int i =0; i<12;i++) {
            for (Perceptron p : listP) {
                p.learning(jezyki);
            }
        }






        //przykładowy tekst
        //ogólna nazwa drukowanej wypowiedzi w prasie, od notatki informacyjnej na temat polityczny, społeczny, kulturalny, sportowy – po krótki esej. Dobry artykuł powinien być przejrzysty kompozycyjnie, zawierać logiczny wywód, w którym kolejne argumenty stanowią potwierdzenie tezy lub opis sytuacji i wydarzeń."
        //start gui
        JFrame jf = new JFrame("Popup");
        jf.setVisible(true);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setSize(new Dimension(300,300));
        jf.setLayout(new GridLayout(3,1));
        jf.repaint();
        JLabel jl = new JLabel("Wprowadz tekst");
        jf.add(jl);
        JTextField jtf = new JTextField();
        jf.add(jtf);
        JButton jb = new JButton("Ok");
        jf.add(jb);
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String wprowadzanie = jtf.getText();
                Jezyk test = utworz_jezyk(wprowadzanie,"unknown");


                boolean rozpoznaneSlowoTemp=false;
                String rozpoznanyJezyk="";
                String tempSlowo="";
                for(Perceptron p : listP){
                    tempSlowo=p.rozpoznajJezyk(test);
                    System.out.println(p.name + " " + p.policzNet(test));
                    if (!tempSlowo.equals("")){
                        if (rozpoznaneSlowoTemp){
                            System.out.println("Nie udało się jednoznacznie okreslić języka!");

                            return;
                        }
                        rozpoznanyJezyk=tempSlowo;
                        rozpoznaneSlowoTemp=true;


                    }




                }
                if(rozpoznanyJezyk.equals("")){
                    System.out.println("Nie udało się rozpoznać języka");
                    
                }else {
                    System.out.println("Rozpoznany jezyk: " + rozpoznanyJezyk);
                }


            }
        });
        jf.setSize(new Dimension(301,301));
        jf.repaint();
        //koniec gui




    }

    public static Jezyk utworz_jezyk(String s, String n){
        int tempVal;
        int[] tab = new int[26];


        Arrays.fill(tab,0);

        for(int i = 0 ; i < s.length();i++){
            tempVal = Character.getNumericValue(s.charAt(i))-10;
            if(tempVal < 0 || tempVal >26){
                continue;

            }

            tab[Character.getNumericValue(s.charAt(i))-10]++;

        }
        return new Jezyk(n,tab) ;
    }


}
