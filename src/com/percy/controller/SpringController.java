package com.percy.controller;
import com.percy.dao.adminUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * @author percy
 * @create 2019-02-12  下午10:23
 * @descreption:
 **/

@Controller
public class SpringController{
    @Autowired
    adminUserDao adminUserDao;

    public SpringController() {
        System.out.println("SpringController");
    }

    @RequestMapping(value = "/")
    public String index(){
        return "index";
    }
    @RequestMapping(value = "spring")
    public String springController(){

        return "index";
    }
    @RequestMapping(value = "login")
    public ModelAndView loginView(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("msg");
        if(adminUserDao.CheckUser(username, password)){
            modelAndView.addObject("message","成功");
        }else {
            modelAndView.addObject("message","失败");
        }
        return modelAndView;
    }
}
