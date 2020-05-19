import java.util.Random;

public class Compteur extends Thread {

    private int n;

    //Un compteur de nom "nom" compte de 1 jusqu'a "n"
    public Compteur (String nom, int n) {
        super(nom);
        this.n = n;
    }

    @Override
    public void run() {
        super.run();
        for (int i = 1; i <= n; i++) {
            System.out.printf("%s: %d\n", getName(), i);
            try {
                sleep((int)(Math.random() * 500));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.printf("%s a fini de compter jusqu'a %d\n", getName(), this.n);
    }

    public static void main(String[] args) {
        Compteur[] compteurs = new Compteur[] {
                new Compteur("Compteur 1", 10),
                new Compteur("Compteur 2", 10),
                new Compteur("Compteur 3", 10)
        };
        for (Compteur compteur: compteurs) {
            compteur.start();
        }
    }
}

