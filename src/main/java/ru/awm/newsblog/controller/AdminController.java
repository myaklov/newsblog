package ru.awm.newsblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.awm.newsblog.model.Article;
import ru.awm.newsblog.service.ArticleService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ArticleService service;

    @GetMapping("/article/new")
    public String showNewArticle(Model model) {
        model.addAttribute("article", new Article());
        return "new_article";
    }


    @PostMapping("/article/save_article")
    public String saveArticle(Article article) {
        service.saveArticle(article);
        return "redirect:/articles/";
    }

    @GetMapping("/article/{id}/edit")
    public String showEditArticle(@PathVariable("id") int id, Model model) {
        Article article = service.getArticleById(id);
        if (article == null) {
            return "redirect:/articles/";
        }
        model.addAttribute("article", article);
        return "edit_article";
    }

    @GetMapping("/article/{id}/delete")
    public String deleteArticle(@PathVariable("id") int id, Model model) {
        Article article = service.getArticleById(id);
        if (article == null) {
            return "redirect:/articles/";
        }
        service.deleteArticleById(id);
        return "redirect:/articles/";
    }

}
