public class Compte {

    private int solde = 0;

    public void ajouter(int somme) {
        solde += somme;
        System.out.printf(" ajoute %d \n", somme);
    }

    public void retirer(int somme) {
        solde -= somme;
        System.out.printf(" retire %d \n", somme);
    }

    public void operationNulle(int somme) {
        solde += somme;
        System.out.printf(" ajoute %d \n", somme);
        solde -= somme;
        System.out.printf(" retire %d \n", somme);
    }

    public int getSolde() {
        return solde;
    }
}
    
