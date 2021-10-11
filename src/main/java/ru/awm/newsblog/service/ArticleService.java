package ru.awm.newsblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.awm.newsblog.model.Article;
import ru.awm.newsblog.repo.ArticleNotFoundException;
import ru.awm.newsblog.repo.ArticleRepository;

import java.util.List;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository repository;

    public void saveArticle(Article article) {
        repository.saveArticle(article);
    }

    public List<Article> getAllArticles() {
        return repository.getAllArticles();
    }

    public Article getArticleById(int id) {
        try {
            return repository.getArticleById(id);
        } catch (ArticleNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteArticleById(int id) {
        repository.deleteArticle(id);
    }

    public Page<Article> getArticlesPage(Pageable pageable) {
        return repository.getArticlesPage(pageable);
    }


}

