public class Compteur extends Thread {

    private int n;
    private static int position = 0;
    private static final Object LOCK = new Object();

    private static Compteur[] compteurs;

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
            //interruption
            compteurs[(int)(Math.random() * compteurs.length)].interrupt();
            try {
                sleep((int)(Math.random() * 500));
            } catch (InterruptedException e) {
                //e.printStackTrace();
            }
        }
        synchronized (LOCK) {
            System.out.printf("%s a fini de compter jusqu'a %d en position %d\n", getName(), this.n, position++);
        }
    }

    public static void main(String[] args) {
        compteurs = new Compteur[] {
                new Compteur("Compteur 1", 5),
                new Compteur("Compteur 2", 5),
                new Compteur("Compteur 3", 5),
                new Compteur("Compteur 4", 5),
                new Compteur("Compteur 5", 5),
                new Compteur("Compteur 6", 5),
                new Compteur("Compteur 7", 5),
                new Compteur("Compteur 8", 5),
                new Compteur("Compteur 9", 5),
                new Compteur("Compteur 10", 5),
                new Compteur("Compteur 11", 5),
                new Compteur("Compteur 12", 5)
        };
        for (Compteur compteur: compteurs) {
            compteur.start();
        }
    }
}

