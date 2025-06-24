package goormthonUniv.MJU.server.Article.repository;

import goormthonUniv.MJU.server.Article.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
