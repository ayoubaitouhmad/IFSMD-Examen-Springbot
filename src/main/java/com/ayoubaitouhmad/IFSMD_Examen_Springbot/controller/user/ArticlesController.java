package com.ayoubaitouhmad.IFSMD_Examen_Springbot.controller.user;

import com.ayoubaitouhmad.IFSMD_Examen_Springbot.controller.BaseController;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.model.Article;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.model.BaseModel;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.model.User;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.service.ArticleService;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.service.UserService;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.util.PageUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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

    /***
     * Articles index page
     * @param model
     * @param searchTerm
     * @param page
     * @return
     */
    @GetMapping("")
    public String index(Model model, @RequestParam(value = "search", defaultValue = "") String searchTerm, @RequestParam(value = "page", defaultValue = "0") int page) {
        model.addAttribute("pageTitle", "My Articles");
        List<Article> articles = getCurrentUser().getArticles();
        articles = articleService.filterArticlesListByTitleAndContent(searchTerm, searchTerm, articles);
        Page<BaseModel> articlePage = PageUtil.PageList(articles, page);
        model.addAttribute("searchTerm", searchTerm);
        model.addAttribute("articles", articlePage);
        model.addAttribute("user", getCurrentUser());
        return "pages/user/articles/index";
    }

    /***
     * Return the article creation page
     * @param model
     * @param message
     * @return
     */
    @GetMapping("/create")
    public String create(Model model, @ModelAttribute("message") String message, RedirectAttributes redirectAttributes) {
        if (!model.containsAttribute("articleForm")) {
            model.addAttribute("articleForm", new Article());
        }
        model.addAttribute("pageTitle", "create article");
        model.addAttribute("message", message);
        return "pages/user/articles/create";
    }

    /***
     * Store article
     * @param articleForm
     * @param bindingResult
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/store")
    public String store(@Valid @ModelAttribute("articleForm") Article articleForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("message", "error");
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.articleForm", bindingResult);
            redirectAttributes.addFlashAttribute("articleForm", articleForm);
            return "redirect:/articles/create";
        }

        Article article = articleService.saveArticleByUser(articleForm, getCurrentUser());
        redirectAttributes.addFlashAttribute("alert", PageUtil.alert(PageUtil.ALERT_SUCCESS).setMessage("Article created successfully").getAlert());

        return "redirect:/articles/" + article.getId() + "/edit";
    }

    /***
     * Show article
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        Article article = articleService.findArticleById(id);
        if (article == null) {
            http404();
        }
        model.addAttribute("article", article);
        model.addAttribute("pageTitle", article.getTitle());
        return "pages/user/articles/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Article article = articleService.findArticleById(id);

        if (article == null) {
            http404();
        }

        if (!userService.isUserOwnArticle(article, getCurrentUser())) {
            http403();
        }

        if (!model.containsAttribute("articleForm")) {
            model.addAttribute("articleForm", article);
        }
        model.addAttribute("pageTitle", "Edit Article: " + article.getTitle());
        return "pages/user/articles/edit";
    }

    @PostMapping("/{id}/update")
    public String update(@Valid @ModelAttribute("articleForm") Article articleForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, @PathVariable Long id, Model model) {
        Article article = articleService.findArticleById(id);

        if (article == null) {
            http404();
        }
        if (!userService.isUserOwnArticle(article, getCurrentUser())) {
            http403();
        }
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("message", "error");
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.articleForm", bindingResult);
            redirectAttributes.addFlashAttribute("articleForm", articleForm);
            return "redirect:/articles/" + article.getId() + "/edit";
        }
        article.setTitle(articleForm.getTitle());
        article.setDescription(articleForm.getDescription());
        article.setContent(articleForm.getContent());

        articleService.saveArticle(article);

        redirectAttributes.addFlashAttribute("alert", PageUtil.alert(PageUtil.ALERT_SUCCESS).setMessage("Article Updated successfully").getAlert());

        return "redirect:/articles/" + article.getId() + "/edit";

    }

    @GetMapping("/{id}/delete")
    public String delete(RedirectAttributes redirectAttributes, @PathVariable Long id, Model model) {

        Article article = articleService.findArticleById(id);
        if (article == null) {
            http404();
        }
        if (!userService.isUserOwnArticle(article, getCurrentUser())) {
            http403();
        }

        articleService.deleteArticle(article);
        redirectAttributes.addFlashAttribute("alert", PageUtil.alert(PageUtil.ALERT_SUCCESS).setMessage("Article Deleted successfully").getAlert());
        return "redirect:/articles";

    }

    @GetMapping("/@{username}")
    public String listArticlesByUser(@PathVariable String username, Model model, @RequestParam(value = "search", defaultValue = "") String searchTerm, @RequestParam(value = "page", defaultValue = "0") int page

    ) {

        User user = getCurrentUser();
        List<Article> articles = user.getArticles();

        articles = articleService.filterArticlesListByTitleAndContent(searchTerm, searchTerm, articles);

        Page<BaseModel> articlePage = PageUtil.PageList(articles, page);

        model.addAttribute("user", user);
        model.addAttribute("searchTerm", searchTerm);
        model.addAttribute("pageTitle", user.getUsername() + " Articles");
        model.addAttribute("articles", articlePage);
        return "pages/user/articles/user-articles";
    }

}
