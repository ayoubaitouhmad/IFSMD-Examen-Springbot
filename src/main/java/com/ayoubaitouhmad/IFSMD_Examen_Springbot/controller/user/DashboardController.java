package com.ayoubaitouhmad.IFSMD_Examen_Springbot.controller.user;

import com.ayoubaitouhmad.IFSMD_Examen_Springbot.controller.BaseController;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.model.Article;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.model.BaseModel;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.service.ArticleService;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/home")
public class DashboardController extends BaseController {
    private final ArticleService articleService;

    @Autowired
    public DashboardController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("")
    public String index(Model model, @RequestParam(value = "search", defaultValue = "") String searchTerm, @RequestParam(value = "page", defaultValue = "0") int page) {
        model.addAttribute("pageTitle", "Home Page");
        List<Article> articles = articleService.getAllArticles();
        articles = articleService.filterArticlesListByTitleAndContent(searchTerm, searchTerm, articles);
        Page<BaseModel> articlePage = PageUtil.PageList(articles, page);
        model.addAttribute("searchTerm", searchTerm);
        model.addAttribute("articles", articlePage);
        return "pages/user/home";
    }
}