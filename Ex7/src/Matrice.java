/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Date;
import java.util.Random;

/**
 *
 * @author Pascal Fares
 * source https://github.com/ljug/applicationconcurentes/blob/master/ExempleCompletIntro/ExemplesConcurenceEtOptimisation/src/main/java/lb/edu/isae/matrice/Matrice.java
 */
public class Matrice {

    public double[][] matrice;

    public Matrice(int nbLignes, int nbColonnes) {
        matrice = new double[nbLignes][nbColonnes];
    }

    public Matrice(double[][] matrice) {
        this.matrice = matrice;
    }

    public static Matrice fabrique(int nbLignes, int nbColonnes) {
        Matrice res = new Matrice(nbLignes, nbColonnes);
        Random random = new Random();
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                res.matrice[i][j] = random.nextDouble() * 10;
            }
        }
        return res;
    }

    public static Matrice multiply(double[][] matrice1, double[][] matrice2) {
        int nbLigneM1 = matrice1.length;
        int nbColonnesM1 = matrice1[0].length;

        int NbColonnesM2 = matrice2[0].length;

        Matrice result = new Matrice(nbLigneM1, NbColonnesM2);

        Multiplier[] threads = new Multiplier[nbLigneM1];
        for (int i = 0; i < nbLigneM1; i++) {
            threads[i] = new Multiplier(result, matrice1, matrice2, i, nbColonnesM1, NbColonnesM2);
            threads[i].start();
        }
        for (int i = 0; i < nbLigneM1; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    @Override
    public String toString() {
        StringBuilder resultat = new StringBuilder();
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice[i].length; j++) {
                resultat.append(matrice[i][i]).append("\t");
            }
            resultat.append("\n");
        }
        return resultat.toString();
    }

    public static void main(String... args) {
        Matrice m1 = fabrique(500, 500);
        //System.out.println(m1);
        Matrice m2 = fabrique(500, 500);
        //System.out.println(m2);

        Date start = new Date();
        Matrice resultat = multiply(m1.matrice, m2.matrice);
        Date end = new Date();
        long tot=end.getTime() - start.getTime();
        System.out.printf("Serial: %ds%dms%n", tot/1000,tot%1000);

        //System.out.println(resultat);

    }

    static class Multiplier extends Thread {

        private Matrice resultat;
        private double[][] m1, m2;
        private int indexLigneM1, nbColonnesM1, nbColonnesM2;

        public Multiplier (Matrice resultat, double[][] m1, double[][] m2, int indexLigneM1, int nbColonnesM1, int nbColonnesM2) {
            this.resultat = resultat;
            this.m1 = m1;
            this.m2 = m2;
            this.indexLigneM1 = indexLigneM1;
            this.nbColonnesM1 = nbColonnesM1;
            this.nbColonnesM2 = nbColonnesM2;
        }

        @Override
        public void run() {
            super.run();
            for (int j = 0; j < nbColonnesM2; j++) {
                resultat.matrice[indexLigneM1][j] = 0.;
                for (int k = 0; k < nbColonnesM1; k++) {
                    resultat.matrice[indexLigneM1][j] += m1[indexLigneM1][k] * m2[k][j];
                }
            }
        }
    }
}