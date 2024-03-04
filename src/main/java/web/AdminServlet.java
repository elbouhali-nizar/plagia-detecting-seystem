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

/*! Namespace pour les fonctionnalités JDBC */
// Import pour le ResultSet afin de contenir le résultat d'une requête SQL
import java.sql.ResultSet;

/*! Namespace pour les exceptions SQL */
// Import pour gérer les exceptions SQL
import java.sql.SQLException;

/*! Namespace pour les collections */
// Import pour ArrayList afin de stocker une liste dynamique d'objets User
import java.util.ArrayList;

/*! Namespace pour les collections */
// Import pour l'interface List pour définir une liste générique
import java.util.List;

/*! Namespace pour les fonctionnalités liées aux servlets */
// Import pour gérer les exceptions liées aux servlets
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
 * Implémentation du servlet pour la gestion des administrateurs.
 */
/*! \class AdminServlet
 *  \brief Implémentation du servlet pour la gestion des administrateurs.
 */
/*! \namespace web
 *  \brief Namespace pour le package web
 */
@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    
    // Requête SQL pour récupérer les informations des utilisateurs
    private final static String userQuery = "select id, name, email, mobile, dob, city, gender, document from user";
    
    // Requête SQL pour récupérer les informations d'authentification de l'administrateur
    private final static String adminQuery = "select username, password from admin";
    
    /**
     * Gère les requêtes HTTP GET pour la page d'administration.
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
        pw.println("<h2 class='text-primary text-center'>Page d'administration</h2>");
        
        // Charge le pilote JDBC
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Vérifie si l'utilisateur est connecté
        if (!isLoggedIn(req)) {
            // Si non connecté, redirige vers la page de connexion
            res.sendRedirect("login.html");
            return;
        }
        
        List<User> userList = new ArrayList<>();
        
        // Génère la connexion à la base de données
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
        
        // Définit les données récupérées comme attribut dans la requête
        req.setAttribute("userList", userList);

        // Transfère la requête à la page admin.jsp
        req.getRequestDispatcher("/admin.jsp").forward(req, res);
    }

    /**
     * Gère les requêtes HTTP POST pour l'authentification.
     * @param req L'objet HttpServletRequest.
     * @param res L'objet HttpServletResponse.
     * @throws ServletException Si une erreur spécifique au servlet se produit.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Obtient le nom d'utilisateur et le mot de passe des paramètres de la requête
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // Vérifie si le nom d'utilisateur et le mot de passe fournis correspondent aux informations d'authentification de l'administrateur
        if (isValidAdmin(username, password)) {
            // Si correspondant, définit l'attribut 'loggedIn' à true
            req.getSession().setAttribute("loggedIn", true);
           
            // Redirige vers la page d'administration
            res.sendRedirect("admin");
        } else {
            // Si non correspondant, redirige vers la page de connexion avec un message d'erreur
            res.sendRedirect("login.html?error=1");
        }
    }

    /**
     * Vérifie si les informations d'authentification de l'administrateur sont valides.
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

        // Établit la connexion à la base de données
        try (Connection con = DriverManager.getConnection("jdbc:mysql:///usermgmt", "root", "root");
             PreparedStatement ps = con.prepareStatement(adminQuery)) {

            // Exécute la requête
            ResultSet rs = ps.executeQuery();

            // Vérifie s'il existe un enregistrement correspondant dans la table admin
            while (rs.next()) {
                String dbUsername = rs.getString("username");
                String dbPassword = rs.getString("password");

                // Compare le nom d'utilisateur et le mot de passe fournis avec ceux de la base de données
                if (username.equals(dbUsername) && password.equals(dbPassword)) {
                    return true;
                }
            }

            // Aucun enregistrement correspondant trouvé
            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Vérifie si l'utilisateur est actuellement connecté.
     * @param req L'objet HttpServletRequest.
     * @return True si l'utilisateur est connecté, false sinon.
     */
    private boolean isLoggedIn(HttpServletRequest req) {
        // Vérifie si l'attribut 'loggedIn' est défini à true dans la session
        Object loggedInAttribute = req.getSession().getAttribute("loggedIn");
        return loggedInAttribute != null && (Boolean) loggedInAttribute;
    }
}
