package web;

/*! Namespace pour le package web */
import java.io.IOException;

/*! Namespace pour les fonctionnalit�s servlet */
import jakarta.servlet.ServletException;

/*! Namespace pour la prise en charge des annotations servlet */
import jakarta.servlet.annotation.WebServlet;

/*! Namespace pour les fonctionnalit�s servlet */
import jakarta.servlet.http.HttpServlet;

/*! Namespace pour les fonctionnalit�s servlet */
import jakarta.servlet.http.HttpServletRequest;

/*! Namespace pour les fonctionnalit�s servlet */
import jakarta.servlet.http.HttpServletResponse;

/*! Namespace pour les fonctionnalit�s session */
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class LogoutServlet
 */
/*! \class LogoutServlet
 *  \brief Impl�mentation du servlet pour la d�connexion d'un utilisateur.
 */
/*! \namespace web
 *  \brief Namespace pour le package web
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    /**
     * G�re les requ�tes HTTP GET pour la d�connexion d'un utilisateur.
     * @param req L'objet HttpServletRequest.
     * @param res L'objet HttpServletResponse.
     * @throws ServletException Si une erreur sp�cifique au servlet se produit.
     * @throws IOException Si une erreur d'entr�e/sortie se produit.
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
