package ru.awm.newsblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.awm.newsblog.model.Article;
import ru.awm.newsblog.model.User;
import ru.awm.newsblog.service.UserService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String showUsers(Model model, @PageableDefault(sort = {"id"},
            direction = Sort.Direction.DESC, size = 20) Pageable pageable) {

        Page<User> listusers = userService.getUsersPage(pageable);
        int totalpages = listusers.getTotalPages();
        if (totalpages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(0, totalpages - 1)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);

        }
        model.addAttribute("listusers", listusers);
        return "users";
    }

    @GetMapping("/{id}/edit")
    public String showEditUser(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        if (user == null) {
            return "redirect:/users/";
        }
        model.addAttribute("user", user);
        return "edit_user";

    }

    @PostMapping("/")
    public String saveUser(User user, @RequestParam("id") Long id) {
        userService.saveUser(user);
        return "redirect:/users/";

    }

    @GetMapping("/{id}/delete")
    public String showDeleteUser(Model model, @PathVariable("id") Long id) {
        User user = userService.findById(id);
        if(user != null && !user.getUsername().equals("admin")){
            model.addAttribute("id", id);
            model.addAttribute("user",user);
            return "delete_user";
        }
        return "redirect:/users/";

    }

    @PostMapping("/{id}/delete")
    public String deleteUser(@RequestParam("id") Long id) {
       User user = userService.findById(id);
       if(user != null){
           userService.deleteUser(id);
       }
        return "redirect:/users/";

    }
}

