package com.ayoubaitouhmad.IFSMD_Examen_Springbot.repository;

import com.ayoubaitouhmad.IFSMD_Examen_Springbot.model.Article;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query("SELECT a FROM Article a WHERE a.title LIKE %:searchTerm% OR a.content LIKE %:searchTerm%")
    List<Article> searchByTitleOrContent(String searchTerm);
}
