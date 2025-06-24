package goormthonUniv.MJU.server.Article.controller.dto;

import java.util.List;

public record ArticleResponse(
        Long id,
        String title,
        String content,
        List<String> imagePaths
) {
}
