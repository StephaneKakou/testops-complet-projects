package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.exec.ExecuteException;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.aad.msal4j.IAccount;
import com.microsoft.aad.msal4j.IAuthenticationResult;
import com.microsoft.aad.msal4j.MsalException;
import com.microsoft.aad.msal4j.PublicClientApplication;
import com.microsoft.aad.msal4j.SilentParameters;
import com.microsoft.aad.msal4j.UserNamePasswordParameters;

import modules.UseFullMethods;

public class UsersConnectToApiGraph {

    public static String authority;
    public static Set<String> scope;
    public static String clientId;
    public static String username;
    public static String password;

    public static void main(String args[]) throws Exception {

        setUpSampleData();

        PublicClientApplication pca = PublicClientApplication.builder(clientId)
                .authority(authority)
                .build();

        // Obtenir la liste des comptes à partir du cache de jetons de l'application, et
        // rechercher dans ces comptes
        // le nom d'utilisateur configuré
        // getAccounts() sera vide lors de ce premier appel, car les comptes sont
        // ajoutés au
        // cache lors de l'acquisition d'un jeton
        Set<IAccount> accountsInCache = pca.getAccounts().join();
        IAccount account = getAccountByUsername(accountsInCache, username);

        // Tentative d'acquisition d'un jeton lorsque le compte de l'utilisateur n'est
        // pas dans le cache de l'application.
        // cache de jetons de l'application
        IAuthenticationResult result = acquireTokenUsernamePassword(pca, scope, account, username, password);
        // UseFullMethods.log.info("Account username: " + result.account().username());
        // UseFullMethods.log.info("Access token: " + result.accessToken());
        // UseFullMethods.log.info("Id token: " + result.idToken());
        // UseFullMethods.log.info();

        accountsInCache = pca.getAccounts().join();
        account = getAccountByUsername(accountsInCache, username);

        // Tentative d'acquisition du jeton à nouveau, maintenant que le compte de
        // l'utilisateur et un jeton sont
        // dans le cache de jetons de l'application
        result = acquireTokenUsernamePassword(pca, scope, account, username, password);
        // UseFullMethods.log.info("Account username: " + result.account().username());
        // UseFullMethods.log.info("Access token: " + result.accessToken());
        // UseFullMethods.log.info("Id token: " + result.idToken());
        String textToSeach = "Signature de votre devis d’assurance";
        fetchLatestMessage(result.accessToken(), new Date(), textToSeach);

    }

    @SuppressWarnings("deprecation")
    public static IAuthenticationResult acquireTokenUsernamePassword(PublicClientApplication pca,
            Set<String> scope,
            IAccount account,
            String username,
            String password) throws Exception {
        IAuthenticationResult result;
        try {

            SilentParameters silentParameters = SilentParameters
                    .builder(scope)
                    .account(account)
                    .build();
            // Essayer d'acquérir le jeton silencieusement. Cela échouera au premier appel
            // appel de acquireTokenUsernamePassword()
            // parce que le cache de jetons n'a pas de données pour l'utilisateur que vous
            // essayez d'acquérir.
            // d'acquérir un jeton pour
            result = pca.acquireTokenSilently(silentParameters).join();
            UseFullMethods.log.info("Succès de l'appel de la methode acquireTokenSilently ");
        } catch (Exception ex) {
            if (ex.getCause() instanceof MsalException) {
                UseFullMethods.log.warn("L'appel de la methode acquireTokenSilently a échoué : " + ex.getCause());
                UserNamePasswordParameters parameters = UserNamePasswordParameters
                        .builder(scope, username, password.toCharArray())
                        .build();
                // Essayer d'acquérir un jeton via le nom d'utilisateur/mot de passe. En cas de
                // succès, vous devriez voir
                // le jeton et les informations du compte imprimés sur la console
                result = pca.acquireToken(parameters).join();
                UseFullMethods.log.info("Jeton pour username/password recupéré avec succes");
            } else {
                // Traiter les autres exceptions en conséquence
                throw ex;
            }
        }
        return result;
    }

    /**
     * Fonction d'aide pour renvoyer un compte à partir d'un ensemble de comptes
     * donné en fonction de
     * le nom d'utilisateur donné,
     * ou retourner null si aucun compte de l'ensemble ne correspond
     */
    public static IAccount getAccountByUsername(Set<IAccount> accounts, String username) {
        if (accounts.isEmpty()) {
            UseFullMethods.log.info("*****************************************************");
            UseFullMethods.log.info("********* Vérification en cache d'un compte *********");
            UseFullMethods.log.info("*****************************************************");
            UseFullMethods.log.info("****** Aucun compte n'est présent dans le cache *****");
        } else {
            UseFullMethods.log.info("==Accounts in cache: " + accounts.size());
            for (IAccount account : accounts) {
                if (account.username().equals(username)) {
                    return account;
                }
            }
        }
        return null;
    }

    /**
     * Fonction d'aide propre à cet exemple. Dans une application réelle, ces
     * fonctions
     * ne seraient pas aussi codées en dur, par exemple
     * des valeurs telles que nom d'utilisateur/mot de passe proviendraient de
     * l'utilisateur, et des utilisateurs différents
     * utilisateurs peuvent avoir besoin d'un champ d'application différent
     */
    public static void setUpSampleData() throws IOException {
        Properties properties = new Properties();
        File src = new File("src/main/java/files/configs/config.properties");
        FileInputStream fis = new FileInputStream(src);

        try {
            properties.load(fis);

            authority = properties.getProperty("AUTHORITY", "");
            scope = Collections.singleton(properties.getProperty("SCOPE", ""));
            clientId = properties.getProperty("CLIENT-ID", "");
            username = properties.getProperty("USER_NAME", "");
            password = properties.getProperty("USER-PASSWORD", "");

            System.out.println("*************************************************************");
            System.out.println("********* INFO : Properties loaded successfully *************");
            System.out.println("*************************************************************");
        } catch (IOException e) {
            System.err.println("Erreur :  loading properties file: " + e.getMessage());
            throw e;
        }
    }

    /*
     * Methode servant de requetes après avoir obtenu un jeton
     * Pour l'authentification. Elle trie en recupérant le mail le plus recent
     */

    @SuppressWarnings({ "deprecation" })
    public static List<Map<String, String>> fetchLatestMessage(String accessToken, Date specificDate, String textToSeach)
            throws ExecuteException, InterruptedException {
        // DateTimeFormatter formatter =
        // DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
        String formattedSpecificDate = date.format(specificDate).concat("T" + time.format(specificDate) + "Z");
        // formattedSpecificDate = "2024-06-7T16:00:27Z";
//        UseFullMethods.log.info("formattedSpecificDate: " + formattedSpecificDate);
        boolean found = false;
        int attempt = 0;
        List<Map<String, String>> emailInfos = new ArrayList<>();
        
      
        String requestURL = "https://graph.microsoft.com/v1.0/me/mailFolders/inbox/messages?$filter=receivedDateTime%20lt%20"
                + formattedSpecificDate + "&%20$orderby=receivedDateTime%20desc%20&%24top=5";
        while (!found && attempt < 20) {
            URI endpoint = URI.create(requestURL);
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet request = new HttpGet(endpoint.toString());
            request.setHeader("Authorization", "Bearer " + accessToken);

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String responseBody = EntityUtils.toString(entity);
                    // UseFullMethods.log.info("responseBody" + responseBody);
                    for (Map<String, Object> map : parseMessagesFromResponse(responseBody)) {
                        Map<String, String> emailInfo = new HashMap<>();

                        if (map.get("subject").toString().contains(textToSeach)) {
                            emailInfo.put("subject", map.get("subject").toString());
                            emailInfo.put("bodyPreview", map.get("bodyPreview").toString());
                            emailInfo.put("body", map.get("body").toString());
                            emailInfos.add(emailInfo);
                        }
                    }
                    
//                    UseFullMethods.log.info(" ***  Statut de la liste d'email **** : " + !emailInfos.isEmpty());
                    if (!emailInfos.isEmpty()) {
                        found = true;
                        UseFullMethods.log.info(emailInfos.size() + " : MESSAGE(S) TROUVE(S) AVEC LE SUBJECT" + textToSeach);

                        return emailInfos;

                    }
                }
            } catch (Exception e) {
                UseFullMethods.log.error("Erreur lors de l'appel de l'API Graph: " + e.getMessage());

            } finally {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    UseFullMethods.log.error("Erreur lors de la fermeture du client HTTP : " + e.getMessage());
                }
            }

            if (!found) {
                attempt++;
                UseFullMethods.log.warn("******NO MORE MESSAGES FOUND *********** " + found);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    UseFullMethods.log.error("Interruption pendant l'attente : " + e.getMessage());
                }
            }
        }
        return emailInfos;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" }) // Notez l'utilisation de "rawtypes" ici car nous manipulons
                                                   // directement une List
    public static List<Map<String, Object>> parseMessagesFromResponse(String responseBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> messages = new ArrayList<>();
        try {
            Map<String, Object> messageMap = objectMapper.readValue(responseBody, Map.class);
            Object valueObj = messageMap.get("value");
            if (valueObj instanceof List) {
                List messagesList = (List) valueObj;
                for (Object obj : messagesList) {
                    if (obj instanceof Map) {
                        messages.add((Map<String, Object>) obj);
                    }
                }
            } else {
                UseFullMethods.log.info("value is not a list.");
            }
        } catch (Exception e) {
            UseFullMethods.log.info("Error parsing response: " + e.getMessage());
        }
        return messages;
    }

    public static boolean FindString(String[] list, String recherche) {
        for (String s : list) {
            if (s.contains(recherche)) {
                return true;
            }
        }

        return false;
    }

     public static String extractSixDigitSequenceFromEmail(String html) {
        try {
            Document doc = Jsoup.parse(html);
            Element bodyElement = doc.body(); // Corrected to use Element instead of Elements
            Pattern pattern = Pattern.compile("\\b\\d{6}\\b"); // Regular expression to find a sequence of 6 digits
            
            for (Element element : bodyElement.children()) { // Iterate over direct children of the body
                Matcher matcher = pattern.matcher(element.text());
                if (matcher.find()) {
                    return matcher.group(); // Returns the first sequence of 6 digits found
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            UseFullMethods.log.info("Error extracting six-digit sequence: " + e.getMessage());
        }
    
        return null; // Returns null if no six-digit sequence is found
    }

}
