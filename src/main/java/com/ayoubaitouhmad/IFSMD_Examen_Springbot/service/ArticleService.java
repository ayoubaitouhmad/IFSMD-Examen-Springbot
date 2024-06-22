package com.ayoubaitouhmad.IFSMD_Examen_Springbot.service;

import com.ayoubaitouhmad.IFSMD_Examen_Springbot.model.Article;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.model.User;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.repository.ArticleRepository;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

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


    public Page<Article> getAllArticles(int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return articleRepository.findAll(pageable);
    }


    public Article saveArticleByUser(Article article, User user) {
        article.setUser(user);
        return articleRepository.save(article);
    }

    public List<Article> searchByTitleOrContent(String string) {
        return articleRepository.searchByTitleOrContent(string);
    }


    @Transactional
    public void deleteArticle(Article article) {

        if (article.getUser().getUsername() != userService.getCurrentUser().getUsername()) {
            throw new IllegalStateException("Cannot delete article not owned by user");
        }

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


}
