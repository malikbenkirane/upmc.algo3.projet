/**
   Cette classe à pour but de donner un exemple pour lire un fichier
   de pénalités.
 */
public class AfficherPenalites {

    public static void main(String[] args) {

        try {
            String filename = args[0];
            PenalitesInteger p =
                new PenalitesInteger(filename);
            p.afficher();
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("usage : $ fichier-de-pénalités");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}
