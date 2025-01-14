package it.aulab.progetto_finale.dtos;
import java.time.LocalDate;

import it.aulab.progetto_finale.models.Category;
import it.aulab.progetto_finale.models.Image;
import it.aulab.progetto_finale.models.User;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Setter
@Getter
@NoArgsConstructor
public class ArticleDto {
    private Long id;
    private String title;
    private String subtitle;
    private String body;
    private LocalDate publishDate;
    private Boolean isAccepted;
    private User user;
    private Category category;
    private Image image;
    
}