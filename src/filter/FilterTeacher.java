package filter;

import users.User;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "FilterTeacher",urlPatterns = "/teacher/*")
public class FilterTeacher implements Filter {
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
        if(user.getLevel()==1){
            ((HttpServletResponse) response).sendRedirect("../StudentIndex.html");
        }else if(user.getLevel()==2){
            chain.doFilter(request, response);
        } else if (user.getLevel()==3) {
            ((HttpServletResponse) response).sendRedirect("../ManagerIndex.html");
        }
    }
}
