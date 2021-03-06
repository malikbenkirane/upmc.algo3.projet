/**
   Cette classe à pour but de donner un exemple de lecture d'un
   fichier d'instance
*/
public class Afficher {

    public static void main(String[] args) {
        
        try {
            String filename = args[0];
            PaireDeSequences test =
                new PaireDeSequences(filename);
            test.afficher();
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("usage : $ fichier.adn");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}
