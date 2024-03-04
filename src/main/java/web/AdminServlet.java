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

/*! Namespace pour les fonctionnalit�s JDBC */
// Import pour le ResultSet afin de contenir le r�sultat d'une requ�te SQL
import java.sql.ResultSet;

/*! Namespace pour les exceptions SQL */
// Import pour g�rer les exceptions SQL
import java.sql.SQLException;

/*! Namespace pour les collections */
// Import pour ArrayList afin de stocker une liste dynamique d'objets User
import java.util.ArrayList;

/*! Namespace pour les collections */
// Import pour l'interface List pour d�finir une liste g�n�rique
import java.util.List;

/*! Namespace pour les fonctionnalit�s li�es aux servlets */
// Import pour g�rer les exceptions li�es aux servlets
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
 * Impl�mentation du servlet pour la gestion des administrateurs.
 */
/*! \class AdminServlet
 *  \brief Impl�mentation du servlet pour la gestion des administrateurs.
 */
/*! \namespace web
 *  \brief Namespace pour le package web
 */
@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    
    // Requ�te SQL pour r�cup�rer les informations des utilisateurs
    private final static String userQuery = "select id, name, email, mobile, dob, city, gender, document from user";
    
    // Requ�te SQL pour r�cup�rer les informations d'authentification de l'administrateur
    private final static String adminQuery = "select username, password from admin";
    
    /**
     * G�re les requ�tes HTTP GET pour la page d'administration.
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
        pw.println("<h2 class='text-primary text-center'>Page d'administration</h2>");
        
        // Charge le pilote JDBC
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // V�rifie si l'utilisateur est connect�
        if (!isLoggedIn(req)) {
            // Si non connect�, redirige vers la page de connexion
            res.sendRedirect("login.html");
            return;
        }
        
        List<User> userList = new ArrayList<>();
        
        // G�n�re la connexion � la base de donn�es
        try (Connection con = DriverManager.getConnection("jdbc:mysql:///usermgmt", "root", "root");
             PreparedStatement ps = con.prepareStatement(userQuery)) {
            // ResultSet
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setName(rs.getString(2));
                user.setEmail(rs.getString(3));
                user.setMobile(rs.getString(4));
                user.setDob(rs.getString(5));
                user.setCity(rs.getString(6));
                user.setGender(rs.getString(7));
                user.setDocument(rs.getString(8));
                
                userList.add(user);
            }
        } catch (SQLException se) {
            pw.println("<h2 class='bg-danger text-light text-center'>" + se.getMessage() + "</h2>");
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // D�finit les donn�es r�cup�r�es comme attribut dans la requ�te
        req.setAttribute("userList", userList);

        // Transf�re la requ�te � la page admin.jsp
        req.getRequestDispatcher("/admin.jsp").forward(req, res);
    }

    /**
     * G�re les requ�tes HTTP POST pour l'authentification.
     * @param req L'objet HttpServletRequest.
     * @param res L'objet HttpServletResponse.
     * @throws ServletException Si une erreur sp�cifique au servlet se produit.
     * @throws IOException Si une erreur d'entr�e/sortie se produit.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Obtient le nom d'utilisateur et le mot de passe des param�tres de la requ�te
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // V�rifie si le nom d'utilisateur et le mot de passe fournis correspondent aux informations d'authentification de l'administrateur
        if (isValidAdmin(username, password)) {
            // Si correspondant, d�finit l'attribut 'loggedIn' � true
            req.getSession().setAttribute("loggedIn", true);
           
            // Redirige vers la page d'administration
            res.sendRedirect("admin");
        } else {
            // Si non correspondant, redirige vers la page de connexion avec un message d'erreur
            res.sendRedirect("login.html?error=1");
        }
    }

    /**
     * V�rifie si les informations d'authentification de l'administrateur sont valides.
     * @param username Le nom d'utilisateur fourni.
     * @param password Le mot de passe fourni.
     * @return True si les informations d'authentification de l'administrateur sont valides, false sinon.
     */
    private boolean isValidAdmin(String username, String password) {
        // Charge le pilote JDBC
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        // �tablit la connexion � la base de donn�es
        try (Connection con = DriverManager.getConnection("jdbc:mysql:///usermgmt", "root", "root");
             PreparedStatement ps = con.prepareStatement(adminQuery)) {

            // Ex�cute la requ�te
            ResultSet rs = ps.executeQuery();

            // V�rifie s'il existe un enregistrement correspondant dans la table admin
            while (rs.next()) {
                String dbUsername = rs.getString("username");
                String dbPassword = rs.getString("password");

                // Compare le nom d'utilisateur et le mot de passe fournis avec ceux de la base de donn�es
                if (username.equals(dbUsername) && password.equals(dbPassword)) {
                    return true;
                }
            }

            // Aucun enregistrement correspondant trouv�
            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * V�rifie si l'utilisateur est actuellement connect�.
     * @param req L'objet HttpServletRequest.
     * @return True si l'utilisateur est connect�, false sinon.
     */
    private boolean isLoggedIn(HttpServletRequest req) {
        // V�rifie si l'attribut 'loggedIn' est d�fini � true dans la session
        Object loggedInAttribute = req.getSession().getAttribute("loggedIn");
        return loggedInAttribute != null && (Boolean) loggedInAttribute;
    }
}
