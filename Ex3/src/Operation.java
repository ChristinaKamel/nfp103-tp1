public class Operation extends Thread {
    private Compte compte;
    private static final Object LOCK = new Object();

    public Operation(String nom, Compte compte) {
        super(nom);
        this.compte = compte;
    }

    public void run() {
        while (true) {
            int i = (int) (Math.random() * 10000);
            String nom = getName();
            System.out.print(nom+"\n");
            int solde;
            synchronized (LOCK) {
//                compte.operationNulle(i);
                compte.ajouter(i);
                compte.retirer(i);
                solde = compte.getSolde();
            }
            System.out.print(nom+"\n");
            if (solde != 0) {
                System.out.println(nom + ":**solde=" + solde);
                System.exit(1);
            }
        }
    }

    public static void main(String[] args) {
        final Object LOCK = new Object();
        Compte compte = new Compte();
        for (int i = 0; i < 20; i++) {
            Operation operation = new Operation("" + (char) ('A' + i), compte);
            operation.start();
        }
    }
}

  
