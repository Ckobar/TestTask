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
public class ServletForEntry extends HttpServlet {
    private AccountService accountService;
    private String tempAct = "";

    public ServletForEntry(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Displays a page for input
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_OK);

        Map<String, Object> pageVariables = new HashMap();

        pageVariables.put("tempAct", "");
        response.getWriter().println(PageGenerator.getPage("entry.html", pageVariables));
    }

    /**
     * Sends the result of the user's request to the browser
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String password = request.getParameter("password");
        String name = request.getParameter("login");

        checkField(name, password, response);
    }

    /**
     * Checks for correctness of data entry
     * @param nameUser
     * @param passUser
     * @param response
     * @throws IOException
     */
    private void checkField(String nameUser, String passUser, HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables = new HashMap();
        if (nameUser.isEmpty() || passUser.isEmpty()) {
            tempAct = "login or password is empty";
            pageVariables.put("tempAct", tempAct);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println(PageGenerator.getPage("entry.html", pageVariables));

        } else if(!searchUserAccount(nameUser,passUser)){
            tempAct = "User not found";
            pageVariables.put("tempAct", tempAct);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println(PageGenerator.getPage("entry.html", pageVariables));
        }
        else {
            UserProfile userProfile = accountService.getUser(nameUser);

            pageVariables.put("name", userProfile.getEmail());
            pageVariables.put("email", userProfile.getLogin());

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println(PageGenerator.getPage("user.html", pageVariables));
        }
    }

    /**
     * Checks the presence of the user in the "database"
     * @param nameUser
     * @param passUser
     * @return
     */
    private boolean searchUserAccount(String nameUser, String passUser){
        try {
            UserProfile userProfile = accountService.getUser(nameUser);
            if(!userProfile.getPassword().equals(passUser)) return false;
        }
        catch (NullPointerException e){
            return false;
        }
        return true;
    }

}
