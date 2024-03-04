package web;

/*! Namespace pour le package web */
// Import pour g�rer les exceptions IOException
import java.io.IOException;

/*! Namespace pour les fonctionnalit�s d'impression */
// Import pour PrintWriter afin d'envoyer des r�ponses textuelles au client
import java.io.PrintWriter;

/*! Namespace pour les fonctionnalit�s de connexion � la base de donn�es */
// Import pour la connexion JDBC � la base de donn�es
import java.sql.Connection;

/*! Namespace pour les fonctionnalit�s JDBC */
// Import pour JDBC DriverManager pour g�rer les connexions � la base de donn�es
import java.sql.DriverManager;

/*! Namespace pour les fonctionnalit�s JDBC */
// Import pour les PreparedStatements afin d'ex�cuter des requ�tes SQL param�tr�es
import java.sql.PreparedStatement;

/*! Namespace pour le ResultSet afin de contenir le r�sultat d'une requ�te SQL */
// Import pour le ResultSet afin de contenir le r�sultat d'une requ�te SQL
import java.sql.ResultSet;

/*! Namespace pour les exceptions SQL */
// Import pour g�rer les exceptions SQL
import java.sql.SQLException;

/*! Namespace pour les fonctionnalit�s li�es aux servlets */
// Import pour la prise en charge des annotations servlet
import jakarta.servlet.ServletException;

/*! Namespace pour les annotations servlet */
// Import pour la prise en charge des annotations servlet
import jakarta.servlet.annotation.WebServlet;

/*! Namespace pour les fonctionnalit�s servlet */
// Import pour HttpServlet afin de cr�er des servlets
import jakarta.servlet.http.HttpServlet;

/*! Namespace pour les fonctionnalit�s servlet */
// Import pour g�rer les requ�tes et les r�ponses HTTP
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class EditScreenServlet
 */
/*! \class EditScreenServlet
 *  \brief Impl�mentation du servlet pour l'affichage de l'�cran d'�dition d'un utilisateur.
 */
/*! \namespace web
 *  \brief Namespace pour le package web
 */
@WebServlet("/editurl")
public class EditScreenServlet extends HttpServlet {
    // Requ�te SQL pour r�cup�rer les informations d'un utilisateur en fonction de son identifiant
    private final static String query = "select name,email,mobile,dob,city,gender from user where id=?";
    
    /**
     * G�re les requ�tes HTTP GET pour afficher l'�cran d'�dition d'un utilisateur.
     * @param req L'objet HttpServletRequest.
     * @param res L'objet HttpServletResponse.
     * @throws ServletException Si une erreur sp�cifique au servlet se produit.
     * @throws IOException Si une erreur d'entr�e/sortie se produit.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Obtient PrintWriter
        PrintWriter pw = res.getWriter();
        // D�finit le type de contenu
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
        // G�n�re la connexion � la base de donn�es
        try (Connection con = DriverManager.getConnection("jdbc:mysql:///usermgmt", "root", "root");
             PreparedStatement ps = con.prepareStatement(query);) {
            // D�finit la valeur du param�tre
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
            pw.println("<td><button type='submit' class='btn btn-outline-success'>�diter</button></td>");
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
     * G�re les requ�tes HTTP POST pour �diter un enregistrement utilisateur.
     * @param req L'objet HttpServletRequest.
     * @param res L'objet HttpServletResponse.
     * @throws ServletException Si une erreur sp�cifique au servlet se produit.
     * @throws IOException Si une erreur d'entr�e/sortie se produit.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
