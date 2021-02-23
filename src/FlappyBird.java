import java.awt.*;
import java.applet.Applet;
import java.awt.event.*;
import java.util.Random;

public class FlappyBird extends Applet implements KeyListener {
    //Wszystkie zmienne
    Random r = new Random();
    int birdX = 180, birdY = 256; // X i Y ptaka
    int start = 0; // warianty trybu
    int a = 0; // zmienna pomocnicza do zwiększania się prędkości
    int prze= 6; //początkowa prędkość gry
    int counter = 0; //licznik punktów w grze
    int best = 0; //rekord punktów w danej sesji
    int cntJump = 3; //zmienna pomocnicza do skoku
    int columnX = 440 , columnY = 0; // X i Y pierwszej kolumny
    int colWidth = 40; //szerokość kolumn
    int Tab1[] = new int[5]; //tablica do pozycji X kolumny
    int Tab2[] = new int[5]; //tablice do wysokości kolumny
    String mess1 = "";
    String mess2 = "";
    String mess3 = "";
    String mess4 = "";
    String licznik ="";
    private Graphics doubleg;
    private Image e;
    //Init (m.in. przekazywanie wartości do tablic)
    @Override
    public void init() {
        addKeyListener(this);
        this.setSize(440, 500);
        Tab1[0] = columnX;
        for (int i = 1; i < 5; i++) {
            Tab1[i] = Tab1[i - 1] + 300;
        }
        for (int i = 0; i < 5; i++) {
            Tab2[i] = r.nextInt(300) + 50;
        }
    }

    @Override
    public void paint(Graphics g) {
        setBackground(Color.blue);
        g.setColor(Color.black);
        g.drawOval(birdX, birdY, 20, 20);
        g.setColor(Color.red);
        g.fillOval(birdX, birdY, 20, 20);
        //
        for (int i = 0; i < 5; i++) {
            if (birdX >= Tab1[i]+35 && birdX <= Tab1[i]+40) {
                counter++; //licznik pkt.
                a++;
                if (counter > best) {
                    best = counter;
                }
                if (a == 5) {
                    a = 0;
                    prze += 2;//przyspieszenie
                }
            }
            if (birdY <= 0 || birdY >= 480) { //kolizje góra dół
                start = 3;
            }
            if (birdY <= Tab2[i] && birdX >= Tab1[i] && birdX <= Tab1[i] + 40) { //kolizja z dolną kraw. górnej col.
                start = 3;
            }
            if (birdY >= Tab2[i] + 80 && birdX >= Tab1[i] && birdX <= Tab1[i] + 40) {//kolizja z górną kraw dolnej col.
                start = 3;
            }
            if (birdX+20 >= Tab1[i] && birdX <= Tab1[i] + 40 && birdY <= Tab2[i]) {//kolizja z górną kol od strony lewej
                start = 3;
            }
            if (birdX+20 >= Tab1[i] && birdX <= Tab1[i] + 40 && birdY >= Tab2[i] + 100) {//kolizja z dolną kol od strony lewej
                start = 3;
            }

        }
        g.setColor(Color.black);
        for (int i = 0; i < 5; i++) {
            g.drawRect(Tab1[i], columnY, colWidth, Tab2[i]);
            g.drawRect(Tab1[i], Tab2[i] + 100, colWidth, 500 - Tab2[i] + 100);
        }
        g.setColor(Color.green);
        for (int i = 0; i < 5; i++) {
            g.fillRect(Tab1[i], columnY, colWidth, Tab2[i]);
            g.fillRect(Tab1[i], Tab2[i] + 100, colWidth, 500 - Tab2[i] + 100);
        }
        for (int i = 0; i < 5; i++) {
            if (Tab1[i] <= -colWidth) {
                if (i == 0) {
                    Tab1[i] = Tab1[4] + 300;
                    Tab2[i] = r.nextInt(200) + 50;
                } else {
                    Tab1[i] = Tab1[i - 1] + 300;
                    Tab2[i] = r.nextInt(200) + 50;
                }
            }
        }
        g.setColor(Color.black);
        g.drawRect(1, 1, 231, 50);
        g.setColor(Color.white);
        g.fillRect(1, 1, 231, 50);
        g.setColor(Color.black);
        g.setFont(new Font("", Font.BOLD, 10));
        g.drawString(mess1, 10, 15);
        g.drawString(mess2, 10, 25);
        g.drawString(mess3, 10, 35);
        g.drawString(mess4, 10, 45);
        g.setColor(Color.black);
        g.setFont(new Font("", Font.BOLD, 35));
        g.drawString(licznik, (440/2)-licznik.length(), 460);

        if (start == 0) {
            mess1 = "Wciśnij spację, aby zacząć grę.";
            mess2 = "Skacz za pomocą spacji";
            mess3 = "Aby spauzować grę naciśnij ESC.";
            licznik = "" + counter;
        }
        if (start == 1) {
            if (cntJump <= 2) {//skok ptaka
                birdY = birdY - 15;
                cntJump += 1;
            } else {
                birdY = birdY + 5;//spadanie ptaka
            }
            for (int i = 0; i < 5; i++) {
                Tab1[i] -= prze; //przesuwanie się otoczenia w lewo
            }
            mess1 = "Spacja - Skok.";
            mess2 = "ESC - Pauza.";
            mess3 = "Aby zagrać od nowa naciśnij ENTER.";
            mess4 = "";
            licznik = "" + counter;
        } else if (start == 2) {
            mess1 = "PAUZA jest włączona";
            mess2 = "Naciśnij spację, aby kontynuować.";
            mess3 = "Aby zagrać od nowa naciśnij ENTER.";
            mess4 = "";
            licznik = "" + counter;
        } else if (start == 3) {
            mess1 = "Przegrałeś!";
            mess2 = "Twój wynik to: " + counter + ".";
            mess3 = "Najlepszy wynik podczas tej sesji: " + best + ".";
            mess4 = "Aby zagrać od nowa naciśnij ENTER.";
            licznik = "" + counter;
        } else {
        }
        try {
            Thread.sleep(50); //czas odświeżenia painta
        } catch (InterruptedException e) {
        }
        repaint();
    }
    //doublebuffering (żeby się gra nie "cięła") :D
    @Override
    public void update(Graphics g){
        if (e == null) {
            e = createImage(this.getSize().width, this.getSize().height);
            doubleg = e.getGraphics();
        }
        doubleg.setColor(getBackground());
        doubleg.fillRect(0, 0, this.getSize().width, this.getSize().height);

        doubleg.setColor(getForeground());
        paint(doubleg);
        g.drawImage(e, 0, 0, this);
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_SPACE: //skok
                cntJump = 0;
                start = 1;
                break;
            case KeyEvent.VK_ESCAPE: //pauza
                start = 2;
                break;
            case KeyEvent.VK_ENTER: //ustawienia początkowe w celu zaczęcia gry od nowa
                Tab1[0]=440;
                for(int i=1; i<5; i++) {
                    Tab1[i] = -50;
                }
                birdX = 182;
                birdY = 256;
                start = 1;
                counter = 0;
                a = 0;
                prze = 6;
        }
    }
    @Override
    public void keyTyped (KeyEvent e){
    }
    @Override
    public void keyReleased (KeyEvent e){
    }
}
