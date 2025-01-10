package utils.models;

public class UserInfo {
  // Champs pour les informations personnelles
  private String firstName;
  private String lastName;
  private String email;
  private String numero;

  // Champs pour les informations du cabinet
  private String officeAddress;
  private String postalCode;
  private String city;

  // Getters et Setters
  public String getfirstName() {
      return firstName;
  }

  public void setFirstName(String firstName) {
      this.firstName = firstName;
  }

  public String getLastName() {
      return lastName;
  }

  public void setLastName(String lastName) {
      this.lastName = lastName;
  }

  public String getEmail() {
      return email;
  }

  public void setEmail(String email) {
      this.email = email;
  }

  public String getNumero() {
      return numero;
  }

  public void setNumero(String numero) {
      this.numero = numero;
  }

  public String getOfficeAddress() {
      return officeAddress;
  }

  public void setOfficeAddress(String officeAddress) {
      this.officeAddress = officeAddress;
  }

  public String getPostalCode() {
      return postalCode;
  }

  public void setPostalCode(String postalCode) {
      this.postalCode = postalCode;
  }

  public String getCity() {
      return city;
  }

  public void setCity(String city) {
      this.city = city;
  }
}
