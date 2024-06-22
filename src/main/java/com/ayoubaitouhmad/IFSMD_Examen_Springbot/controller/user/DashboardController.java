package com.ayoubaitouhmad.IFSMD_Examen_Springbot.controller.user;


import com.ayoubaitouhmad.IFSMD_Examen_Springbot.controller.BaseController;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.model.Article;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.model.User;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/home")
public class DashboardController extends BaseController {
    private final ArticleService articleService;

    @Autowired
    public DashboardController(ArticleService articleService) {
        this.articleService = articleService;
    }


    @GetMapping("")
    public String index(Model model, @RequestParam(value = "search" , defaultValue = "") String searchTerm, @RequestParam(value = "page", defaultValue = "0") int page) {
        model.addAttribute("pageTitle", "Home Page");
        Page<Article> articlePage = articleService.getAllArticles(page);
        model.addAttribute("articles", articlePage);

        if (searchTerm != null && !searchTerm.isEmpty()) {
            List<Article> filteredArticles = articlePage.stream()
                    .filter(article -> article.getTitle().toLowerCase().contains(searchTerm.toLowerCase()) ||
                            article.getContent().toLowerCase().contains(searchTerm.toLowerCase()))
                    .collect(Collectors.toList());
            articlePage = new PageImpl<>(filteredArticles, PageRequest.of(page, 8), filteredArticles.size());
            model.addAttribute("searchTerm", searchTerm);
            model.addAttribute("articles", articlePage);

        }

        return "pages/user/home";

    }


}