package dao;






import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class LoginAdmin implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String login = (String) request.getSession().getAttribute("id");
			
		if (login == null || !login.equals("admin")) {
			response.sendRedirect(request.getContextPath()+"/member/adminChk");
			//  response.sendRedirect(request.getContextPath()+"/member/loginChk?key=user");
			//  response.sendRedirect(request.getContextPath()+"/member/loginChk?key=admin");
			return false;
		} else {
			return true;
		}
	}
		

}
