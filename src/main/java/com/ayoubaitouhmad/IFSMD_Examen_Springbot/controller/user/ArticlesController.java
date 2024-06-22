package com.ayoubaitouhmad.IFSMD_Examen_Springbot.controller.user;


import com.ayoubaitouhmad.IFSMD_Examen_Springbot.controller.BaseController;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.model.Article;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.model.User;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.service.ArticleService;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
    public String index(Model model,
                        @RequestParam(value = "search", defaultValue = "") String searchTerm, @RequestParam(value = "page", defaultValue = "0") int page

    ) {


        logger.info("getUsername: {}",userService.getCurrentUser().getUsername());
        model.addAttribute("pageTitle", "My Articles");

        List<Article> articles = getCurrentUser().getArticles();


        if (searchTerm != null && !searchTerm.isEmpty()) {
            articles = articles.stream()
                    .filter(article -> article.getTitle().toLowerCase().contains(searchTerm.toLowerCase()) ||
                            article.getContent().toLowerCase().contains(searchTerm.toLowerCase()))
                    .collect(Collectors.toList());
        }

        model.addAttribute("searchTerm", searchTerm);


        int start = (int) PageRequest.of(page, 8).getOffset();
        int end = Math.min((start + PageRequest.of(page, 8).getPageSize()), articles.size());
        Page<Article> articlePage = new PageImpl<>(articles.subList(start, end), PageRequest.of(page, 8), articles.size());


        model.addAttribute("articles", articlePage);
        model.addAttribute("user", getCurrentUser());
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


    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {


        try {
            Article article = articleService.getArticleForEdit(id);
            if (!model.containsAttribute("articleForm")) {
                model.addAttribute("articleForm", article);
            }
            model.addAttribute("pageTitle", "Edit Article: " + article.getTitle());
            return "pages/user/articles/edit";
        } catch (RuntimeException e) {
            model.addAttribute("message", e.getMessage());
            return "redirect:/articles";  // Redirect or show an error page
        }




    }


    @PostMapping("/{id}/update")
    public String update(
            @Valid @ModelAttribute("articleForm") Article articleForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            @PathVariable Long id, Model model
    ) {
        Article article = articleService.articleRepository().findById(id).orElseThrow(() -> new RuntimeException("User not found with id " + id));

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("message", "error");
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.articleForm", bindingResult);
            redirectAttributes.addFlashAttribute("articleForm", articleForm);
            return "redirect:/articles/"+article.getId()+"/edit";
        }
        article.setTitle(articleForm.getTitle());
        article.setDescription(articleForm.getDescription());
        article.setContent(articleForm.getContent());

        articleService.getArticleRepository().save(article);


        redirectAttributes.addFlashAttribute("message", "done");
        return "redirect:/articles/"+article.getId()+"/edit";

    }


    @GetMapping("/{id}/delete")
    public String delete(
            RedirectAttributes redirectAttributes,
            @PathVariable Long id, Model model
    ) {

        Article article = articleService.articleRepository().findById(id).orElseThrow(() -> new RuntimeException("User not found with id " + id));

        try {
            logger.info("getId {}:",article.getId());
            articleService.deleteArticle(article);
            redirectAttributes.addFlashAttribute("message", "Article deleted successfully!");
        } catch (Exception e) {
            logger.info("getMessage {}:",e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Article not found.");
        }


        logger.info("getTitle {}:",article.getTitle());


        return "redirect:/articles";

    }


    @GetMapping("/@{username}")
    public String listArticlesByUser(
            @PathVariable String username, Model model,
            @RequestParam(value = "search", defaultValue = "") String searchTerm,
            @RequestParam(value = "page", defaultValue = "0") int page

    ) {

        User user = userService.findByUserName(username).orElseThrow(() -> new RuntimeException("User not found with username " + username));
        List<Article> articles = user.getArticles();

        if (searchTerm != null && !searchTerm.isEmpty()) {
            articles = articles.stream()
                    .filter(article -> article.getTitle().toLowerCase().contains(searchTerm.toLowerCase()) ||
                            article.getContent().toLowerCase().contains(searchTerm.toLowerCase()))
                    .collect(Collectors.toList());
        }

        int start = (int) PageRequest.of(page, 8).getOffset();
        int end = Math.min((start + PageRequest.of(page, 8).getPageSize()), articles.size());
        Page<Article> articlePage = new PageImpl<>(articles.subList(start, end), PageRequest.of(page, 8), articles.size());




        model.addAttribute("user", user);
        model.addAttribute("searchTerm", searchTerm);
        model.addAttribute("pageTitle", " Articles");
        model.addAttribute("articles", articlePage);
        return "pages/user/articles/user-articles";
    }


}
