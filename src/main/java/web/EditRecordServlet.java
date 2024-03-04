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
 * Servlet implementation class EditRecordServlet
 */
/*! \class EditRecordServlet
 *  \brief Impl�mentation du servlet pour l'�dition d'enregistrements utilisateur.
 */
/*! \namespace web
 *  \brief Namespace pour le package web
 */
@WebServlet("/edit")
public class EditRecordServlet extends HttpServlet {
    // Requ�te SQL pour mettre � jour un enregistrement utilisateur en fonction de son identifiant
    private final static String query = "update user set name=?,email=?,mobile=?,dob=?,city=?,gender=? where id=?";
    
    /**
     * G�re les requ�tes HTTP GET pour �diter un enregistrement utilisateur.
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
        // Lie le fichier Bootstrap
        pw.println("<link rel='stylesheet' href='css/bootstrap.css'></link>");
        // Obtient les valeurs
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String mobile = req.getParameter("mobile");
        String dob = req.getParameter("dob");
        String city = req.getParameter("city");
        String gender = req.getParameter("gender");
        // Charge le pilote JDBC
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // G�n�re la connexion � la base de donn�es
        try (Connection con = DriverManager.getConnection("jdbc:mysql:///usermgmt", "root", "root");
             PreparedStatement ps = con.prepareStatement(query);) {
            // D�finit les valeurs
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, mobile);
            ps.setString(4, dob);
            ps.setString(5, city);
            ps.setString(6, gender);
            ps.setInt(7, id);
            // Ex�cute la requ�te
            int count = ps.executeUpdate();
            pw.println("<div class='card' style='margin:auto;width:300px;margin-top:100px'>");
            if (count == 1) {
                pw.println("<h2 class='bg-dark text-white text-center'>Enregistrement modifi� avec succ�s</h2>");
            } else {
                pw.println("<h2 class='bg-dark text-white text-center'>Enregistrement non modifi�</h2>");
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
