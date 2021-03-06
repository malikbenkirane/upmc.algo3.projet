import java.util.List;
import java.util.LinkedList;

/**
   Implémentation de {@link AbstractCompare} pour le cas Integer (algorithmes
   de la partie 1). On utilise directement la structure de données
   évoquée à la partie 3 (cf. {@link AbstractPenalites}).
*/
public class CompareInteger1 extends AbstractCompare<Integer> {

    private Matrice<Integer> couts; //memo
    private Integer gap;

    /**
       Construit un sequenceur
       @param xy paire de sequences à comparer
       @param p pénalités de correspondance pour la comparaison
    */
    public CompareInteger1(PaireDeSequences xy, PenalitesInteger p,
                           int i, int j) {
        super(xy, p);
        couts = new Matrice<Integer>(i+1, j+1, null);
        gap = p.getPGap();
    }

    private void trace(String m) {
        System.out.println(m);
    }

    /**
       Implémentation de MEMO-COUT1
       Méthode auxilaire pour le calcul du cout (lisibilité...)  On
       suppose que les couts ont étés calculés pour les trois cas
       (i-1,j), (i,j-1) et (i-1,j-1). (cf. méthode cout() surchargée
       de AbstractCompare<T>
       @param i indice i du cout c(i,j) à calculer
       @param j indice j du cout c(i,j) à calculer
    */
    private Integer memoCout(int i, int j) {
        if ( couts.get(i,j) == null ) {
            Integer[] pre = new Integer[3];
            Integer pc = p.penalite(xy.getX(i), xy.getY(j));
            //trace(pc + "<-(" + i + "," + j + ")");
            pre[0] = couts.get(i-1, j-1) + pc;
            pre[1] = couts.get(i, j-1) + gap; 
            pre[2] = couts.get(i-1, j) + gap;
            return AbstractCompare.<Integer>min3(pre[0], pre[1], pre[2]);
        }
        return couts.get(i,j);
    }

    /** 
        Implémentation de COUT1
        Se référer à la documentation de {@link AbstractCompare#cout(int,int)}
        @param i cf. {@link AbstractCompare#cout(int,int)}
        @param j cf. {@link AbstractCompare#cout(int,int)}
    */
    public Integer cout(int i, int j) {
        couts.set(0, 0, 0);
        //int m = couts.nbLignes() - 1;
        //int n = couts.nbColonnes() - 1;
        for ( int k = 1 ; k <= i ; k++ )
            couts.set(k, 0, k * gap);
        for ( int l = 1 ; l <= j ; l++ )
            couts.set(0, l, l * gap);
        for ( int k = 1 ; k <= i ; k++ )
            for (int l = 1 ; l <= j ; l++ )
                couts.set(k, l, memoCout(k, l));
        return couts.get(i,j);
    }

    /**
       Implémentation de REC-SOL1.
       Méthode auxilaire pour calculer l'alignement optimal On suppose
       qu'on a préalablement déterminé la matrice couts.
       (cf. rapport.pdf/Question 1.8)
       @param m liste de paire correspondant à l'alignement optimal
       @param i récursif sur i
       @param j récursif sur j
    */
    private void recSol(List<Paire> m, int i, int j) {
        //cas de base
        if ( i == 0 || j == 0 ) {
            if ( i == 0 && j != 0 ) m.add(0, new Paire('-', xy.getY(j)));
            if ( i != 0 && j == 0 ) m.add(0, new Paire(xy.getX(i), '-'));
            return;
        }
        //induction
        Integer[] pre = new Integer[3];
        Integer pc = p.penalite(xy.getX(i), xy.getY(j));
        pre[0] = couts.get(i-1, j-1) + pc;
        pre[1] = couts.get(i, j-1) + gap; 
        pre[2] = couts.get(i-1, j) + gap;
        //trace(pre[0] + " " + pre[1] + " " + pre[2]);
        if ( couts.get(i,j).equals(pre[0]) ) {
            //trace("recSol1~xi=" + xy.getX(i) + "~yj=" + xy.getY(j));
            m.add(0, new Paire(xy.getX(i), xy.getY(j)));
            recSol(m, i-1, j-1);
        } else if ( couts.get(i,j).equals(pre[1]) ) {
            //trace("recSol1~gap~yj=" + xy.getY(j));
            m.add(0, new Paire('-', xy.getY(j)));
            recSol(m, i, j-1);
        }
        else if ( couts.get(i,j).equals(pre[2]) ) {
            //trace("recSol1~gap~xi=" + xy.getX(i));
            m.add(0, new Paire(xy.getX(i), '-'));
            recSol(m, i-1, j);
        }
    }

    /**
       Implémentation de SOL1
       Se référer à la documentation de {@link AbstractCompare#sol(int,int)}
        @param i cf. {@link AbstractCompare#sol(int,int)}
        @param j cf. {@link AbstractCompare#sol(int,int)}
    */
    public List<Paire> sol(int i, int j) {
        List<Paire> align = new LinkedList<Paire>();
        cout = cout(i,j);
        recSol(align, i, j);
        return align;
    }

}
