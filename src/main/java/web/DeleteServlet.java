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
 * Implémentation du servlet pour la suppression d'utilisateurs.
 */
/*! \class DeleteServlet
 *  \brief Implémentation du servlet pour la suppression d'utilisateurs.
 */
/*! \namespace web
 *  \brief Namespace pour le package web
 */
@WebServlet("/deleteurl")
public class DeleteServlet extends HttpServlet {

    // Requête SQL pour supprimer un utilisateur en fonction de son identifiant
    private final static String query = "delete from user where id = ?";

    /**
     * Gère les requêtes HTTP GET pour supprimer un enregistrement utilisateur.
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
        // Lie le fichier Bootstrap
        pw.println("<link rel='stylesheet' href='css/bootstrap.css'></link>");
        // Obtient les valeurs
        int id = Integer.parseInt(req.getParameter("id"));
        // Charge le pilote JDBC
        try {
            // Charge le pilote JDBC pour MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Génère la connexion à la base de données
        try (Connection con = DriverManager.getConnection("jdbc:mysql:///usermgmt", "root", "root");
             PreparedStatement ps = con.prepareStatement(query);) {
            // Définit les valeurs
            ps.setInt(1, id);
            // Exécute la requête
            int count = ps.executeUpdate();
            pw.println("<div class='card' style='margin:auto;width:300px;margin-top:100px'>");
            if (count == 1) {
                pw.println("<h2 class='bg-dark text-white text-center'>Enregistrement supprimé avec succès</h2>");
            } else {
                pw.println("<h2 class='bg-dark text-light text-center'>Enregistrement non supprimé</h2>");
            }
        } catch (SQLException se) {
            pw.println("<h2 class='bg-danger text-light text-center'>" + se.getMessage() + "</h2>");
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        pw.println("<a href='home.html'><button class='btn btn-outline-success'>Accueil</button></a>");
        pw.println("&nbsp; &nbsp;");
        pw.println("<a href='showdata'><button class='btn btn-outline-success'>Afficher les utilisateurs</button></a>");
        pw.println("</div>");
        // Ferme le flux
        pw.close();
    }

    /**
     * Gère les requêtes HTTP POST pour supprimer un enregistrement utilisateur.
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
