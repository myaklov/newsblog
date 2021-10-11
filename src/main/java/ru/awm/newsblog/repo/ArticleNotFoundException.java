package ru.awm.newsblog.repo;

public class ArticleNotFoundException extends Throwable {
    public ArticleNotFoundException(String message) {
        super(message);
    }
}
