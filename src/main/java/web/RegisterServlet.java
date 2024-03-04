/*! \file RegisterServlet.java
 *  \brief Servlet pour l'enregistrement des utilisateurs.
 */
/*! \namespace web
 *  \brief Namespace pour le package web
 */
package web;

/*! \namespace java.io
 *  \brief Import pour les classes li�es � la gestion des flux d'entr�e/sortie
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/*! \namespace java.sql
 *  \brief Import pour les classes JDBC (Java Database Connectivity) pour g�rer les connexions � la base de donn�es
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*! \namespace jakarta.servlet
 *  \brief Import pour les classes et annotations li�es � Servlet
 */
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

/*! \class RegisterServlet
 *  \brief Cette classe repr�sente un servlet pour l'enregistrement des utilisateurs.
 */
@WebServlet("/register")
@MultipartConfig
public class RegisterServlet extends HttpServlet {
    // Requ�te SQL pour ins�rer un utilisateur avec un document
    private final static String query = "insert into user(name,email,mobile,dob,city,gender,document) values(?,?,?,?,?,?,?)";

    // Chargement du pilote JDBC MySQL au d�marrage de l'application
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * G�re les requ�tes HTTP POST pour l'enregistrement des utilisateurs.
     * @param req L'objet HttpServletRequest.
     * @param res L'objet HttpServletResponse.
     * @throws ServletException Si une erreur sp�cifique au servlet se produit.
     * @throws IOException Si une erreur d'entr�e/sortie se produit.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // R�cup�ration des informations de l'utilisateur depuis le formulaire
        String name = req.getParameter("userName");
        String email = req.getParameter("email");
        String mobile = req.getParameter("mobile");
        String dob = req.getParameter("dob");
        String city = req.getParameter("city");
        String gender = req.getParameter("gender");

        // Gestion de l'upload de fichier
        Part filePart = req.getPart("document");
        if (filePart != null) {
            try (InputStream is = filePart.getInputStream()) {
                // Traitement du flux d'entr�e (sauvegarde en base de donn�es, etc.)
                byte[] buffer = new byte[is.available()];
                is.read(buffer);
                String documentContent = new String(buffer);

                // Modification de la PreparedStatement pour inclure le contenu du document
                try (Connection con = DriverManager.getConnection("jdbc:mysql:///usermgmt", "root", "root");
                     PreparedStatement ps = con.prepareStatement(query)) {
                    ps.setString(1, name);
                    ps.setString(2, email);
                    ps.setString(3, mobile);
                    ps.setString(4, dob);
                    ps.setString(5, city);
                    ps.setString(6, gender);
                    ps.setString(7, documentContent);

                    // Ex�cution de la requ�te
                    int count = ps.executeUpdate();

                    // Affichage du r�sultat
                    PrintWriter pw = res.getWriter();
                    pw.println("<html>");
                    pw.println("<head>");
                    pw.println("<link rel='stylesheet' href='css/bootstrap.css'></link>");
                    pw.println("<script src='https://code.jquery.com/jquery-3.6.0.min.js'></script>");
                    pw.println("</head>");
                    pw.println("<body>");
                    pw.println("<div class='card' style='margin:auto;width:300px;margin-top:100px'>");
                    if (count == 1) {
                        // Ajout de JavaScript pour afficher l'alerte et rediriger vers home.html
                        pw.println("<script>");
                        pw.println("  $(document).ready(function() {");
                        pw.println("    alert('Devoir ajout� avec succ�s !');");
                        pw.println("    window.location.href = 'home.html';");
                        pw.println("  });");
                        pw.println("</script>");
                    } else {
                        pw.println("<h2 class='bg-danger text-light text-center'>Devoir non ajout�</h2>");
                    }
                    pw.println("</div>");
                    pw.println("</body>");
                    pw.println("</html>");
                    pw.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // G�rer le cas o� aucun document n'est t�l�charg�
            PrintWriter pw = res.getWriter();
            pw.println("Aucun document t�l�charg�.");
            pw.close();
        }
    }
}
