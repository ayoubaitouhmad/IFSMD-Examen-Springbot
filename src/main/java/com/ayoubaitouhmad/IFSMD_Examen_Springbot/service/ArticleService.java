package com.ayoubaitouhmad.IFSMD_Examen_Springbot.service;

import com.ayoubaitouhmad.IFSMD_Examen_Springbot.model.Article;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.model.User;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService  {


    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }


    public ArticleRepository articleRepository() {
        return  articleRepository;
    }


    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }


    public Article saveArticleByUser(Article article , User user) {
        article.setUser(user);
        return articleRepository.save(article);
    }

    public List<Article> searchByTitleOrContent(String string) {
        return  articleRepository.searchByTitleOrContent(string);
    }


}
