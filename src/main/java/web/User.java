/*! \file User.java
 *  \brief Classe représentant un utilisateur.
 */
/*! \namespace web
 *  \brief Namespace pour le package web
 */
package web;

/*! \class User
 *  \brief Cette classe représente un utilisateur.
 */
public class User {
    private int id;
    private String name;
    private String email;
    private String mobile;
    private String dob;
    private String city;
    private String gender;
    private String document;

    /*! \brief Getter pour l'ID
     *  \return L'ID de l'utilisateur
     */
    public int getId() {
        return id;
    }

    /*! \brief Setter pour l'ID
     *  \param id L'ID à définir
     */
    public void setId(int id) {
        this.id = id;
    }

    /*! \brief Getter pour le nom
     *  \return Le nom de l'utilisateur
     */
    public String getName() {
        return name;
    }

    /*! \brief Setter pour le nom
     *  \param name Le nom à définir
     */
    public void setName(String name) {
        this.name = name;
    }

    /*! \brief Getter pour l'email
     *  \return L'email de l'utilisateur
     */
    public String getEmail() {
        return email;
    }

    /*! \brief Setter pour l'email
     *  \param email L'email à définir
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /*! \brief Getter pour le numéro de mobile
     *  \return Le numéro de mobile de l'utilisateur
     */
    public String getMobile() {
        return mobile;
    }

    /*! \brief Setter pour le numéro de mobile
     *  \param mobile Le numéro de mobile à définir
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /*! \brief Getter pour la date de naissance
     *  \return La date de naissance de l'utilisateur
     */
    public String getDob() {
        return dob;
    }

    /*! \brief Setter pour la date de naissance
     *  \param dob La date de naissance à définir
     */
    public void setDob(String dob) {
        this.dob = dob;
    }

    /*! \brief Getter pour la ville
     *  \return La ville de l'utilisateur
     */
    public String getCity() {
        return city;
    }

    /*! \brief Setter pour la ville
     *  \param city La ville à définir
     */
    public void setCity(String city) {
        this.city = city;
    }

    /*! \brief Getter pour le genre
     *  \return Le genre de l'utilisateur
     */
    public String getGender() {
        return gender;
    }

    /*! \brief Setter pour le genre
     *  \param gender Le genre à définir
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /*! \brief Getter pour le document
     *  \return Le document de l'utilisateur
     */
    public String getDocument() {
        return document;
    }

    /*! \brief Setter pour le document
     *  \param document Le document à définir
     */
    public void setDocument(String document) {
        this.document = document;
    }

    /*! \brief Méthode pour obtenir un aperçu du document
     *  \return Un aperçu du document de l'utilisateur
     */
    public String getDocumentPreview() {
        // Obtient la première ligne du document
        String[] lines = document.split("\n");
        if (lines.length > 0) {
            String firstLine = lines[0];
            // S'il y a plus de lignes, ajoute trois points de suspension
            if (lines.length > 1) {
                firstLine += "...";
            }
            return firstLine;
        }
        return "";
    }
}
