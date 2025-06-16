package goormthonUniv.MJU.server.Article.controller;


import goormthonUniv.MJU.server.Article.controller.dto.ArticleRequest;
import goormthonUniv.MJU.server.Article.service.ArticleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping("/articles/{memberId}")
    public ResponseEntity<Void> createArticle(
            @RequestPart("article") ArticleRequest request,
            @RequestPart(value = "images", required = false) List<MultipartFile> images,
            @PathVariable Long memberId
    ) {
        articleService.createArticle(memberId, request, images);

        return ResponseEntity.ok().build();
    }
}
