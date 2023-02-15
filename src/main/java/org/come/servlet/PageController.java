package org.come.servlet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
	
	/**
	 * 显示主页
	 * @return
	 */
	@RequestMapping(value = "/")
	private String showIndex(){
		return "exchange";
	}
	
	/**
	 * 显示页面
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/{page}")
	public String showPage( @PathVariable String page,HttpServletRequest request ){
		if(request.getSession().getAttribute("manger")!=null)
		return page;
		else  return "login";
			
	}

}
