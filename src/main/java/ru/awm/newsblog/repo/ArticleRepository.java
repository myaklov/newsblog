package ru.awm.newsblog.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.awm.newsblog.model.Article;

import java.util.List;
import java.util.Optional;

@Repository
public class ArticleRepository {
    @Autowired
    private IArticleRepository repo;

    public Article getArticleById(int id) throws ArticleNotFoundException {
        Optional<Article> article = repo.findById(id);
        if (article.isPresent()) {
            return article.get();
        }
        throw new ArticleNotFoundException("article id=" + id + " not found");
    }

    public void saveArticle(Article article) {
        repo.save(article);
    }

    public List<Article> getAllArticles() {
        return repo.findAll();
    }

    public Page<Article> getArticlesPage(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public void deleteArticle(int id) {
        repo.deleteById(id);
    }


}