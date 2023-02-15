package filter;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import users.*;

@WebFilter(filterName = "FilterLogin",urlPatterns = "/*")
public class FilterLogin implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        resp.setContentType("text/html;charset=utf-8");
        User user = (User) req.getSession().getAttribute("user");

        if(req.getServletPath().contains(".css")||req.getServletPath().contains(".js")) {
            chain.doFilter(request, response);
        }else if(req.getRequestURI().equals("/Login.jsp")||req.getContextPath().equals("/bootstrap5")||req.getRequestURI().equals("/ServletLogin2")){
            chain.doFilter(request, response);
        }else if(user !=null){
            chain.doFilter(request, response);
        }else {
            resp.sendRedirect("/Login.jsp");
            //System.out.println(req.getContextPath());
            //resp.getWriter().println(req.getContextPath());
        }
        System.out.println(req.getContextPath());
    }
}
