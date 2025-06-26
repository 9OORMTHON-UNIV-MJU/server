package goormthonUniv.MJU.server.Article.controller;


import goormthonUniv.MJU.server.Article.controller.dto.ArticleRequest;
import goormthonUniv.MJU.server.Article.controller.dto.ArticleResponse;
import goormthonUniv.MJU.server.Article.controller.dto.ArticleResponses;
import goormthonUniv.MJU.server.Article.domain.Article;
import goormthonUniv.MJU.server.Article.domain.Image;
import goormthonUniv.MJU.server.Article.service.ArticleService;
import goormthonUniv.MJU.server.global.resolver.annotation.Auth;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping("/articles")
    public ResponseEntity<Void> createArticle(
            @RequestPart("article") ArticleRequest request,
            @RequestPart(value = "images", required = false) List<MultipartFile> images,
            @Auth Long memberId
    ) {
        articleService.createArticle(memberId, request, images);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/articles/{articleId}")
    public ResponseEntity<ArticleResponse> getArticleById(@PathVariable Long articleId) {
        Article article = articleService.findArticle(articleId);

        List<String> imagePaths = article.getImages().stream()
                .map(Image::getStoredPath)
                .toList();

        return ResponseEntity.ok(new ArticleResponse(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                imagePaths
        ));
    }

    @GetMapping("/articles")
    public ResponseEntity<ArticleResponses> getAllArticles() {
        List<Article> articles = articleService.findAllArticles();

        List<ArticleResponse> articleResponses = articles.stream()
                .map(article -> {
                    List<Image> images = article.getImages();
                    List<String> imagePaths = new ArrayList<>();

                    if (images != null && !images.isEmpty()) {
                        imagePaths.add(images.get(0).getStoredPath()); // 이미지 한 장만 가져옴
                    }

                    return new ArticleResponse(
                            article.getId(),
                            article.getTitle(),
                            article.getContent(),
                            imagePaths
                    );
                }).toList();

        return ResponseEntity.ok(new ArticleResponses(articleResponses));
    }
}
