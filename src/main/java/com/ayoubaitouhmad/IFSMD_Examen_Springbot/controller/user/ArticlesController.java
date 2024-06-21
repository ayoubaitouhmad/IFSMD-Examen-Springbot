package com.ayoubaitouhmad.IFSMD_Examen_Springbot.controller.user;


import com.ayoubaitouhmad.IFSMD_Examen_Springbot.controller.BaseController;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.model.Article;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.model.User;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.service.ArticleService;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/articles")
public class ArticlesController extends BaseController {

    private final ArticleService articleService;
    private final UserService userService;

    @Autowired
    public ArticlesController(ArticleService articleService, UserService userService) {
        this.articleService = articleService;
        this.userService = userService;
    }

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("pageTitle", getCurrentUser().getName() + " Articles");
        model.addAttribute("articles", getCurrentUser().getArticles());
        return "pages/user/articles/index";
    }

    @GetMapping("/search")
    public String search(@RequestParam("search") String searchTerm, Model model) {
        List<Article> articles = getCurrentUser().getArticles();



        if (searchTerm != null && !searchTerm.isEmpty()) {
            articles = articles.stream()
                    .filter(article -> article.getTitle().toLowerCase().contains(searchTerm.toLowerCase()) ||
                            article.getContent().toLowerCase().contains(searchTerm.toLowerCase()))
                    .collect(Collectors.toList());
            model.addAttribute("articles", articles);
            model.addAttribute("searchTerm", searchTerm);
        }else {
            model.addAttribute("articles", getCurrentUser().getArticles());
            model.addAttribute("searchTerm", null);
        }


        return "pages/user/articles/index";
    }




    @GetMapping("/create")
    public String create(Model model, @ModelAttribute("message") String message) {

        if (!model.containsAttribute("articleForm")) {
            model.addAttribute("articleForm", new Article());
        }
        model.addAttribute("pageTitle", "create article");
        model.addAttribute("message", message);
        return "pages/user/articles/create";
    }

    @PostMapping("/store")
    public String store(
            @Valid @ModelAttribute("articleForm") Article articleForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        logger.info("errors : {}", bindingResult.getAllErrors());
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("message", "error");
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.articleForm", bindingResult);
            redirectAttributes.addFlashAttribute("articleForm", articleForm);
            return "redirect:/articles/create";
        }


        articleService.saveArticleByUser(articleForm, getCurrentUser());
        logger.info("getTitle : {}", articleForm.getTitle());
        logger.info("getDescription : {}", articleForm.getDescription());
        logger.info("getContent : {}", articleForm.getContent());
        redirectAttributes.addFlashAttribute("message", "Profile updated successfully!");
        return "redirect:/articles/create";
    }


    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        Article article = articleService.articleRepository().findById(id).orElseThrow(() -> new RuntimeException("User not found with id " + id));
        model.addAttribute("article", article);
        model.addAttribute("pageTitle", article.getTitle());
        return "pages/user/articles/show";
    }


}
