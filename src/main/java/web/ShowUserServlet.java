/*! \file ShowUserServlet.java
 *  \brief Servlet pour afficher les données des utilisateurs.
 */
/*! \namespace web
 *  \brief Namespace pour le package web
 */
package web;

/*! \namespace java.io
 *  \brief Import pour les classes liées à la gestion des flux d'entrée/sortie
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*! \namespace jakarta.servlet
 *  \brief Import pour les classes et annotations liées à Servlet
 */
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*! \class ShowUserServlet
 *  \brief Cette classe représente un servlet pour afficher les données des utilisateurs.
 */
@WebServlet("/showdata")
public class ShowUserServlet extends HttpServlet {
    // Requête SQL pour sélectionner toutes les colonnes des utilisateurs
    private final static String query = "select id,name,email,mobile,dob,city,gender,document from user";

    /**
     * Gère les requêtes HTTP GET pour afficher les données des utilisateurs.
     * @param req L'objet HttpServletRequest.
     * @param res L'objet HttpServletResponse.
     * @throws ServletException Si une erreur spécifique au servlet se produit.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Récupération du PrintWriter
        PrintWriter pw = res.getWriter();
        // Définition du type de contenu
        res.setContentType("text/html");
        // Ajout du lien vers Bootstrap
        pw.println("<link rel='stylesheet' href='css/bootstrap.css'></link>");
        pw.println("<h2 class='text-primary text-center'>Données des utilisateurs</h2>");
        // Chargement du pilote JDBC
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Génération de la connexion à la base de données
        try (Connection con = DriverManager.getConnection("jdbc:mysql:///usermgmt", "root", "root");
             PreparedStatement ps = con.prepareStatement(query)) {
            // Récupération du ResultSet
            ResultSet rs = ps.executeQuery();
            pw.println("<div style='margin:auto;width:900px;margin-top:100px;'>");
            pw.println("<table class='table table-hover table-striped'>");
            pw.println("<tr>");
            pw.println("<th>ID</th>");
            pw.println("<th>Nom</th>");
            pw.println("<th>Email</th>");
            pw.println("<th>Numéro de Mobile</th>");
            pw.println("<th>DOB</th>");
            pw.println("<th>Ville</th>");
            pw.println("<th>Genre</th>");
            pw.println("<th>Document</th>");
            pw.println("<th>Éditer</th>");
            pw.println("<th>Supprimer</th>");
            pw.println("</tr>");
            while (rs.next()) {
                String documentContent = rs.getString(8);
                // Extraire la première ligne du document
                String firstLine = documentContent.split("\n")[0];
                // S'il y a plus de lignes, ajouter trois points de suspension
                if (documentContent.contains("\n")) {
                    firstLine += "...";
                }

                pw.println("<tr>");
                pw.println("<td>" + rs.getInt(1) + "</td>");
                pw.println("<td>" + rs.getString(2) + "</td>");
                pw.println("<td>" + rs.getString(3) + "</td>");
                pw.println("<td>" + rs.getString(4) + "</td>");
                pw.println("<td>" + rs.getString(5) + "</td>");
                pw.println("<td>" + rs.getString(6) + "</td>");
                pw.println("<td>" + rs.getString(7) + "</td>");
                pw.println("<td>" + firstLine + "</td>");
                pw.println("<td><a href='editurl?id=" + rs.getInt(1) + "'>Éditer</a></td>");
                pw.println("<td><a href='deleteurl?id=" + rs.getInt(1) + "'>Supprimer</a></td>");
                pw.println("</tr>");
            }
            pw.println("</table>");
        } catch (SQLException se) {
            pw.println("<h2 class='bg-danger text-light text-center'>" + se.getMessage() + "</h2>");
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        pw.println("<button class='btn btn-outline-success d-block'><a href='home.html'>Accueil</a></button>");
        pw.println("</div>");
        // Fermeture du flux
        pw.close();
    }

    /**
     * Gère les requêtes HTTP POST en appelant la méthode doGet.
     * @param req L'objet HttpServletRequest.
     * @param res L'objet HttpServletResponse.
     * @throws ServletException Si une erreur spécifique au servlet se produit.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
