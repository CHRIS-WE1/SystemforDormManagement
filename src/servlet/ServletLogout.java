package servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ServletLogout", value = "/ServletLogout")
public class ServletLogout extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("html/text;charset=utf-8");
        HttpSession session=request.getSession();
        session.removeAttribute("user");
        Cookie cookie=new Cookie("JSESSIONID","");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        response.sendRedirect("/Login.jsp");
    }
}
