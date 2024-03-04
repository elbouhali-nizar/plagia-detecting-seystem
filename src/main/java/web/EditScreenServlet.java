package web;

/*! Namespace pour le package web */
// Import pour gérer les exceptions IOException
import java.io.IOException;

/*! Namespace pour les fonctionnalités d'impression */
// Import pour PrintWriter afin d'envoyer des réponses textuelles au client
import java.io.PrintWriter;

/*! Namespace pour les fonctionnalités de connexion à la base de données */
// Import pour la connexion JDBC à la base de données
import java.sql.Connection;

/*! Namespace pour les fonctionnalités JDBC */
// Import pour JDBC DriverManager pour gérer les connexions à la base de données
import java.sql.DriverManager;

/*! Namespace pour les fonctionnalités JDBC */
// Import pour les PreparedStatements afin d'exécuter des requêtes SQL paramétrées
import java.sql.PreparedStatement;

/*! Namespace pour le ResultSet afin de contenir le résultat d'une requête SQL */
// Import pour le ResultSet afin de contenir le résultat d'une requête SQL
import java.sql.ResultSet;

/*! Namespace pour les exceptions SQL */
// Import pour gérer les exceptions SQL
import java.sql.SQLException;

/*! Namespace pour les fonctionnalités liées aux servlets */
// Import pour la prise en charge des annotations servlet
import jakarta.servlet.ServletException;

/*! Namespace pour les annotations servlet */
// Import pour la prise en charge des annotations servlet
import jakarta.servlet.annotation.WebServlet;

/*! Namespace pour les fonctionnalités servlet */
// Import pour HttpServlet afin de créer des servlets
import jakarta.servlet.http.HttpServlet;

/*! Namespace pour les fonctionnalités servlet */
// Import pour gérer les requêtes et les réponses HTTP
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class EditScreenServlet
 */
/*! \class EditScreenServlet
 *  \brief Implémentation du servlet pour l'affichage de l'écran d'édition d'un utilisateur.
 */
/*! \namespace web
 *  \brief Namespace pour le package web
 */
@WebServlet("/editurl")
public class EditScreenServlet extends HttpServlet {
    // Requête SQL pour récupérer les informations d'un utilisateur en fonction de son identifiant
    private final static String query = "select name,email,mobile,dob,city,gender from user where id=?";
    
    /**
     * Gère les requêtes HTTP GET pour afficher l'écran d'édition d'un utilisateur.
     * @param req L'objet HttpServletRequest.
     * @param res L'objet HttpServletResponse.
     * @throws ServletException Si une erreur spécifique au servlet se produit.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Obtient PrintWriter
        PrintWriter pw = res.getWriter();
        // Définit le type de contenu
        res.setContentType("text/html");

        // Obtient l'identifiant de l'utilisateur
        int id = Integer.parseInt(req.getParameter("id"));
        // Lie le fichier Bootstrap
        pw.println("<link rel='stylesheet' href='css/bootstrap.css'></link>");
        // Charge le pilote JDBC
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Génère la connexion à la base de données
        try (Connection con = DriverManager.getConnection("jdbc:mysql:///usermgmt", "root", "root");
             PreparedStatement ps = con.prepareStatement(query);) {
            // Définit la valeur du paramètre
            ps.setInt(1, id);
            // ResultSet
            ResultSet rs = ps.executeQuery();
            rs.next();
            pw.println("<div style='margin:auto;width:500px;margin-top:100px;'>");
            pw.println("<form action='edit?id=" + id + "' method='post'>");
            pw.println("<table class='table table-hover table-striped'>");
            pw.println("<tr>");
            pw.println("<td>Nom</td>");
            pw.println("<td><input type='text' name='name' value='" + rs.getString(1) + "'></td>");
            pw.println("</tr>");
            pw.println("<tr>");
            pw.println("<td>Email</td>");
            pw.println("<td><input type='email' name='email' value='" + rs.getString(2) + "'></td>");
            pw.println("</tr>");
            pw.println("<tr>");
            pw.println("<td>Mobile</td>");
            pw.println("<td><input type='text' name='mobile' value='" + rs.getString(3) + "'></td>");
            pw.println("</tr>");
            pw.println("<tr>");
            pw.println("<td>DOB</td>");
            pw.println("<td><input type='date' name='dob' value='" + rs.getString(4) + "'></td>");
            pw.println("</tr>");
            pw.println("<tr>");
            pw.println("<td>Ville</td>");
            pw.println("<td><input type='text' name='city' value='" + rs.getString(5) + "'></td>");
            pw.println("</tr>");
            pw.println("<tr>");
            pw.println("<td>Genre</td>");
            pw.println("<td><input type='text' name='gender' value='" + rs.getString(6) + "'></td>");
            pw.println("</tr>");
            pw.println("<tr>");
            pw.println("<td><button type='submit' class='btn btn-outline-success'>Éditer</button></td>");
            pw.println("<td><button type='reset' class='btn btn-outline-danger'>Annuler</button></td>");
            pw.println("</tr>");
            pw.println("</table>");
            pw.println("</form>");
        } catch (SQLException se) {
            pw.println("<h2 class='bg-danger text-light text-center'>" + se.getMessage() + "</h2>");
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        pw.println("<a href='home.html'><button class='btn btn-outline-success'>Accueil</button></a>");
        pw.println("</div>");
        // Ferme le flux
        pw.close();
    }

    /**
     * Gère les requêtes HTTP POST pour éditer un enregistrement utilisateur.
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
