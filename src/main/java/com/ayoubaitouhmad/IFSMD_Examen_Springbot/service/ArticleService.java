package com.ayoubaitouhmad.IFSMD_Examen_Springbot.service;

import com.ayoubaitouhmad.IFSMD_Examen_Springbot.model.Article;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.model.User;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.repository.ArticleRepository;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserService userService;

    public ArticleService(ArticleRepository articleRepository, UserService userService) {
        this.articleRepository = articleRepository;
        this.userService = userService;
    }

    public ArticleRepository articleRepository() {
        return articleRepository;
    }

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public Page<Article> getAllArticlesPageable(int page) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by("updatedAt").ascending());
        return articleRepository.findAll(pageable);
    }

    public Article saveArticleByUser(Article article, User user) {
        article.setUser(user);
        return saveArticle(article);
    }

    public Article saveArticle(Article article) {
        return articleRepository.save(article);
    }

    public List<Article> searchByTitleOrContent(String string) {
        return articleRepository.searchByTitleOrContent(string);
    }

    @Transactional
    public void deleteArticle(Article article) {
        articleRepository.deleteById(article.getId());
    }

    public Article getArticleForEdit(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article not found with id " + articleId));

        User currentUser = userService.getCurrentUser();
        if (!article.getUser().getUsername().equals(currentUser.getUsername())) {
            throw new IllegalStateException("Cannot edit article not owned by user");
        }

        return article;
    }

    public List<Article> filterArticlesListByTitle(String title, List<Article> articlesToFilter) {
        if (title != null && !title.isEmpty()) {
            articlesToFilter = articlesToFilter
                    .stream()
                    .filter(article -> article.getTitle().toLowerCase().contains(title.toLowerCase()))
                    .collect(Collectors.toList());
        }
        return articlesToFilter;
    }

    public List<Article> filterArticlesListByContent(String content, List<Article> articlesToFilter) {
        if (content != null && !content.isEmpty()) {
            articlesToFilter = articlesToFilter
                    .stream()
                    .filter(article -> article.getContent().toLowerCase().contains(content.toLowerCase()))
                    .collect(Collectors.toList());
        }
        return articlesToFilter;
    }

    public List<Article> filterArticlesListByTitleAndContent(String content, String title, List<Article> articlesToFilter) {
        articlesToFilter = filterArticlesListByContent(content, articlesToFilter);
        return filterArticlesListByTitle(title, articlesToFilter);

    }

    public Article findArticleById(Long articleId) {
        return articleRepository.findById(articleId).orElse(null);
    }

}
