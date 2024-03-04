package web;

/*! Namespace pour le package web */
import java.io.IOException;

/*! Namespace pour les fonctionnalités servlet */
import jakarta.servlet.ServletException;

/*! Namespace pour la prise en charge des annotations servlet */
import jakarta.servlet.annotation.WebServlet;

/*! Namespace pour les fonctionnalités servlet */
import jakarta.servlet.http.HttpServlet;

/*! Namespace pour les fonctionnalités servlet */
import jakarta.servlet.http.HttpServletRequest;

/*! Namespace pour les fonctionnalités servlet */
import jakarta.servlet.http.HttpServletResponse;

/*! Namespace pour les fonctionnalités session */
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class LogoutServlet
 */
/*! \class LogoutServlet
 *  \brief Implémentation du servlet pour la déconnexion d'un utilisateur.
 */
/*! \namespace web
 *  \brief Namespace pour le package web
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    /**
     * Gère les requêtes HTTP GET pour la déconnexion d'un utilisateur.
     * @param req L'objet HttpServletRequest.
     * @param res L'objet HttpServletResponse.
     * @throws ServletException Si une erreur spécifique au servlet se produit.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Invalide la session et redirige vers la page de connexion
        /*! \brief Invalide la session et redirige vers la page de connexion */
        HttpSession session = req.getSession();
        session.invalidate();
        res.sendRedirect("login.html");
    }
}
