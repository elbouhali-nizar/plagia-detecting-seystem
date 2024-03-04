/*! \file User.java
 *  \brief Classe repr�sentant un utilisateur.
 */
/*! \namespace web
 *  \brief Namespace pour le package web
 */
package web;

/*! \class User
 *  \brief Cette classe repr�sente un utilisateur.
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
     *  \param id L'ID � d�finir
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
     *  \param name Le nom � d�finir
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
     *  \param email L'email � d�finir
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /*! \brief Getter pour le num�ro de mobile
     *  \return Le num�ro de mobile de l'utilisateur
     */
    public String getMobile() {
        return mobile;
    }

    /*! \brief Setter pour le num�ro de mobile
     *  \param mobile Le num�ro de mobile � d�finir
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
     *  \param dob La date de naissance � d�finir
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
     *  \param city La ville � d�finir
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
     *  \param gender Le genre � d�finir
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
     *  \param document Le document � d�finir
     */
    public void setDocument(String document) {
        this.document = document;
    }

    /*! \brief M�thode pour obtenir un aper�u du document
     *  \return Un aper�u du document de l'utilisateur
     */
    public String getDocumentPreview() {
        // Obtient la premi�re ligne du document
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
