package utils;

import com.microsoft.aad.msal4j.IAccount;
import com.microsoft.aad.msal4j.IAuthenticationResult;
import com.microsoft.aad.msal4j.PublicClientApplication;

import java.io.IOException;
import java.util.Set;

public interface IUserConnectionToGraph {
    /**
     * Charge les données d'échantillon nécessaires pour l'authentification et les requêtes à l'API Graph.
     * @throws IOException Si une erreur se produit lors du chargement des données d'échantillon.
     */
    void setUpSampleData() throws IOException;

    /**
     * Acquiert un token d'authentification pour un compte spécifique en utilisant le nom d'utilisateur et le mot de passe.
     * @param pca L'instance de PublicClientApplication utilisée pour l'acquisition du token.
     * @param scope Le(s) scope(s) requis pour l'acquisition du token.
     * @param account Le compte pour lequel acquérir le token.
     * @param username Le nom d'utilisateur pour l'authentification.
     * @param password Le mot de passe pour l'authentification.
     * @return Le résultat de l'acquisition du token.
     * @throws Exception Si une erreur se produit lors de l'acquisition du token.
     */
    IAuthenticationResult acquireTokenUsernamePassword(PublicClientApplication pca, Set<String> scope, IAccount account, String username, String password) throws Exception;

    /**
     * Renvoie un compte par son nom d'utilisateur à partir d'un ensemble de comptes.
     * @param accounts L'ensemble des comptes disponibles.
     * @param username Le nom d'utilisateur pour rechercher le compte.
     * @return Le compte correspondant au nom d'utilisateur, ou null si aucun compte correspondant n'est trouvé.
     */
    IAccount getAccountByUsername(Set<IAccount> accounts, String username);

    /**
     * Fait une requête à l'API Microsoft Graph pour récupérer le dernier message reçu.
     * @param accessToken Le token d'accès pour authentifier la requête.
     * @return L'objet représentant le dernier message reçu, ou null si aucun message n'est trouvé.
     * @throws Exception Si une erreur se produit lors de l'exécution de la requête.
     */
    Object fetchLatestMessage(String accessToken) throws Exception;
}
