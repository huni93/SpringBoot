package dao;

import java.io.UnsupportedEncodingException;



import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class LoginUser implements HandlerInterceptor {
    
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String login=(String)request.getSession().getAttribute("id");
		if(login==null) {
			response.sendRedirect(request.getContextPath()+"/member/loginChk");
			return false; 
		} else {
		return true; }
	}
	
	
	

}
