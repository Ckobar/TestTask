package servlet;

import main.AccountService;
import main.UserProfile;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Алексей on 22.09.2017.
 */
public class ServletForRegistration extends HttpServlet {
    private AccountService accountService;

    public ServletForRegistration(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Displays a page for registration
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        response.setStatus(HttpServletResponse.SC_OK);

        Map<String, Object> pageVariables = new HashMap();
        pageVariables.put("tempMess", "");

        response.getWriter().println(PageGenerator.getPage("registration.html", pageVariables));

    }

    /**
     * Create a new user
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("newLogin");
        String password = request.getParameter("newPassword");
        String email = request.getParameter("newEmail");


        try {

                if (accountService.addUser(name, new UserProfile(name, password, email)))
                    successfullyCreateNewUser(response, name, email);
               else
                    errorCreateNewUser(response);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

    }

    /**
     * If successful - creates a new user
     * @param response
     * @param nameUser
     * @param emailUser
     * @throws IOException
     */
    private void successfullyCreateNewUser(HttpServletResponse response, String nameUser, String emailUser) throws IOException {
        Map<String, Object> pageVariables = new HashMap();
        pageVariables.put("name", nameUser);
        pageVariables.put("name", nameUser);
        pageVariables.put("email", emailUser);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(PageGenerator.getPage("successfullyUser.html", pageVariables));
    }

    /**
     * Resets the form in case of failure
     * @param response
     * @throws IOException
     */
    private void errorCreateNewUser(HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables = new HashMap();
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(PageGenerator.getPage("registration.html", pageVariables));
    }
}

