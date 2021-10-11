package ru.awm.newsblog.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.awm.newsblog.model.Article;


public interface IArticleRepository extends JpaRepository<Article, Integer> {
}