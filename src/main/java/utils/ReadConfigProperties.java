package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ReadConfigProperties {
    private Properties pro;

    // private static final Logger logger =
    // LogManager.getLogger(ConfigDataProvider.class);
    public ReadConfigProperties() {
        try {
            File src = new File("src/main/java/files/configs/config.properties");
            FileInputStream fis = new FileInputStream(src);
            this.pro = new Properties();
            this.pro.load(fis);
        } catch (FileNotFoundException file) {
            file.printStackTrace();
            System.out.println("Not able to load config file " + file.getMessage());
        } catch (IOException io) {
            io.printStackTrace();
            System.out.println("Not able to load config file " + io.getMessage());
        }
    }

    public String getUrlSpace(String env) {
        return this.pro.getProperty("UrlTestSpace" + env.toUpperCase());
    }

    public String getUrlGestionnaireSpace(String env) {
        return this.pro.getProperty("urlGestionnaireSpace" + env.toUpperCase());
    }

    public String getUrlAssureurSpace(String env) {
        return this.pro.getProperty("urlAssureurSpace" + env.toUpperCase());
    }

    public String getEmailGestionnaireDeblocSpace(String env) {
        return this.pro.getProperty("emailGestionnaireDeblocSpace" + env.toUpperCase());
    }

    public String getEmailGestionnaireValidateurSpace(String env) {
        return this.pro.getProperty("emailGestionnaireValidateurSpace" + env.toUpperCase());
    }

    public String getEmailAssureurSpace(String env) {
        return this.pro.getProperty("emailAssureurSpace" + env.toUpperCase());
    }

    public String getEmailCourtierSpace(String env) {
        return this.pro.getProperty("emailCourtierSpace" + env.toUpperCase());
    }
    public String getPasswordCourtierSpace(String env) {
        return this.pro.getProperty("passwordCourtierSpace" + env.toUpperCase());
    }
    public String getPasswordAssureurSpace(String env) {
        return this.pro.getProperty("passwordAssureurSpace" + env.toUpperCase());
    }

    public String getPassword() {
        return this.pro.getProperty("password");
    }

    public String getEnv() {
        return this.pro.getProperty("ENV");
    }

    public String getInsureEmailSPVIE() {
        return this.pro.getProperty("insureEmailSPVIE");
    }

    public String getInsureEmailPassword() {
        return this.pro.getProperty("insureEmailPassword");
    }

    public String getInsureEmailKIASSURE() {
        return this.pro.getProperty("insureEmailKIASSURE");
    }

    public String getPasswordSpace(String env) {
        return this.pro.getProperty("password" + env.toUpperCase());
    }

    public String getBrowser(String typeBrower) {
        return this.pro.getProperty("browser"+typeBrower.toUpperCase());
    }
    public String getNumberPage() {
        return this.pro.getProperty("URL_NUMBER");
    }
    public String getEmailForTest() {
        return this.pro.getProperty("emailForTesting");
    }
    public String getClientId() {
        return this.pro.getProperty("CLIENT_ID");
    }
    public String getUsername() {
        return this.pro.getProperty("USER_NAME");
    }
    public String getUserPassword() {
        return this.pro.getProperty("USER_PASSWORD");
    }
    public String getAuthority() {
        return this.pro.getProperty("AUTHORITY");
    }
    public String getScope() {
        return this.pro.getProperty("SCOPE");
    }

    public String getEmailOfLogin(String nameSpace, String env) {
        String email = "";
        switch (nameSpace) {
            case "Espace courtier":
                email =  this.getEmailCourtierSpace(env);
                break;
            case "Espace assureur":
                email =  this.getEmailAssureurSpace(env);
                break;
            case "Espace gestionnaire":
                email =  this.getEmailGestionnaireValidateurSpace(env);
                break;
            default:
                System.out.println(nameSpace + "is not found");
                break;
        }
        return email;
    }
    public String getPasswordForLogin(String nameSpace, String env) {
        String email = "";
        switch (nameSpace) {
            case "Espace courtier":
                email =  this.getPasswordCourtierSpace(env);
                break;
            case "Espace assureur":
                email =  this.getPasswordAssureurSpace(env);
                break;
            case "Espace getionnaire":
                email =  this.getEmailGestionnaireValidateurSpace(env);
                break;
            default:
                System.out.println(nameSpace + "is not found");
                break;
        }
        return email;
    }
}
