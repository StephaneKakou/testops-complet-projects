package utils;

import java.util.Random;

public class CompanyCodeGenerator {

    private static Random random = new Random(); // Pour générer des nombres aléatoires

    public static void main(String[] args) {
        // Exemples d'appels à la méthode setCompanyCode
        System.out.println(setCompanyCode("Régime Alsace-Moselle")); // Exemple : AM6702345
        System.out.println(setCompanyCode("Régime général"));        // Exemple : RG7504567
        System.out.println(setCompanyCode("Régime agricole"));       // Exemple : EA3303456
    }

    /**
     * Méthode statique qui retourne un code d'organisme en fonction du régime social.
     * @param regime Le régime social (AM, RG, TNS, EA, R1E, RCC).
     * @return Un code d'organisme unique composé du code régime + code département + 3 chiffres aléatoires.
     */
    public static String setCompanyCode(String regime) {
        String codeOrganisme = "";
        String codeDepartement = "";
        
        // Les trois chiffres aléatoires finaux (000 - 999)
        String troisChiffres = String.format("%03d", random.nextInt(999));

        switch (regime) {
            case "AM":
            case "Régime Alsace-Moselle":
                codeDepartement = getRandomFromArray(new String[]{"67", "68"});
                codeOrganisme = codeDepartement + troisChiffres;
                break;

            case "RG":
            case "Régime général":
                codeDepartement = "75"; // Paris
                codeOrganisme = codeDepartement + troisChiffres;
                break;

            case "Régime TNS":
            case "TNS":
                codeDepartement = "69"; // Rhône
                codeOrganisme = codeDepartement + troisChiffres;
                break;

            case "EA":
            case "Régime agricole":
            codeDepartement = getRandomFromArray(new String[]{"02", "33"}); // Gironde
                codeOrganisme = codeDepartement + troisChiffres;
                break;

            case "R1E":
            case "Régime 1er euro":
                codeDepartement = "13"; // Bouches-du-Rhône
                codeOrganisme = codeDepartement + troisChiffres;
                break;

            case "RCC":
            case "Régime complément CFE":
                codeDepartement = "06"; // Alpes-Maritimes
                codeOrganisme = codeDepartement + troisChiffres;
                break;

            default:
                // Si le régime n'est pas reconnu, retourne un code vide
                return "";
        }

        return codeOrganisme;
    }

    /**
     * Méthode auxiliaire pour obtenir un élément aléatoire d'un tableau (pour Alsace-Moselle).
     */
    private static String getRandomFromArray(String[] array) {
        int index = random.nextInt(array.length);
        return array[index];
    }
}

