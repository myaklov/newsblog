package ru.awm.newsblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.awm.newsblog.model.Article;
import ru.awm.newsblog.service.ArticleService;
import ru.awm.newsblog.service.UserService;
import ru.awm.newsblog.model.User;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class MainController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private ArticleService articleService;

    @Autowired
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping(path = {"/", "/articles"})
    public String showMainArticles(Model model, @PageableDefault(sort = {"id"},
            direction = Sort.Direction.DESC, size = 5) Pageable pageable) {
        Page<Article> listArticles = articleService.getArticlesPage(pageable);
        int totalpages = listArticles.getTotalPages();
        if (totalpages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(0, totalpages - 1)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);

        }

        model.addAttribute("listArticles", listArticles);
        return "atricles";
    }


    @GetMapping("/admin")
    public String showAdminPage(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "adminpage";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/reg")
    public String showRegPage(Model model) {
        model.addAttribute("newuser", new User());
        return "registration";
    }

    @PostMapping("/reg")
    public String regNewUser(Model model, User user) {
        if (userService.saveNewUser(user)) {
            return "redirect:/login";
        } else {
            model.addAttribute("regError", "Ошибка регистрации");
            return "registration";
        }
    }

    @GetMapping("/articles/{id}")
    public String showArticle(@PathVariable("id") int id, Model model) {
        Article article = articleService.getArticleById(id);
        if (article == null) {
            return "redirect:/";
        }
        model.addAttribute("article", article);
        return "article";
    }


}
