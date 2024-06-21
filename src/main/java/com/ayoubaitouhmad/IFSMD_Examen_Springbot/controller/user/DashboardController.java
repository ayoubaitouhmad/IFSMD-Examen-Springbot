package com.ayoubaitouhmad.IFSMD_Examen_Springbot.controller.user;


import com.ayoubaitouhmad.IFSMD_Examen_Springbot.controller.BaseController;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.model.Article;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.model.User;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController extends BaseController {
    private  final ArticleService articleService;

    @Autowired
    public DashboardController(ArticleService articleService) {
        this.articleService = articleService;
    }


    @GetMapping("/home")
    public String welcome(Model model) {
        model.addAttribute("pageTitle", "Home");
        List<Article> articles = articleService.getAllArticles();
        model.addAttribute("articles", articles);
        return "pages/user/home";
    }

}
