package it.aulab.progetto_finale.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.aulab.progetto_finale.dtos.ArticleDto;
import it.aulab.progetto_finale.dtos.CategoryDto;
import it.aulab.progetto_finale.models.Article;
import it.aulab.progetto_finale.models.Category;
import it.aulab.progetto_finale.repositories.ArticleRepository;
import it.aulab.progetto_finale.services.ArticleService;
import it.aulab.progetto_finale.services.CrudService;
import jakarta.validation.Valid;





@Controller
@RequestMapping("/articles")
public class ArticleController {
   
    @Autowired
    private ArticleService articleService;

    @Autowired
    @Qualifier("categoryService")
    private CrudService<CategoryDto,Category,Long> categoryService;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ModelMapper modelMapper;



    @GetMapping
    public String articleIndex(Model viewModel) {

        viewModel.addAttribute("title","Tutti gli articoli");

        List<ArticleDto> articles=new ArrayList<ArticleDto>();
        for(Article article:articleRepository.findByIsAcceptedTrue()){
            articles.add(modelMapper.map(article, ArticleDto.class));
        }

        Collections.sort(articles, Comparator.comparing(ArticleDto::getPublishDate).reversed());
        viewModel.addAttribute("articles",articles);

    return "article/articles";
    }
    


     @GetMapping("create")
    public String articleCreate(Model viewModel) {
        viewModel.addAttribute("title", "Crea un articolo");
        viewModel.addAttribute("article", new Article());
        viewModel.addAttribute("categories", categoryService.readAll());
        return "article/create";
    }


    @PostMapping
    public String articleStore(@Valid @ModelAttribute("article") Article article, 
                                BindingResult result, 
                                RedirectAttributes redirectAttributes, 
                                Principal principal, 
                                MultipartFile file,
                                Model viewModel) {
        
        
        if (result.hasErrors()) {
            viewModel.addAttribute("title", "Crea un articolo");
            viewModel.addAttribute("article", article);
            viewModel.addAttribute("categories", categoryService.readAll());
            return "article/create";
        }
        
        articleService.create(article, principal, file);
        redirectAttributes.addFlashAttribute("successMessage", "Articolo aggiunto con successo!");
        
        return "redirect:/";
}

@GetMapping("detail/{id}")
public String detailArticle(@PathVariable("id") Long id, Model viewModel) {
    viewModel.addAttribute("title", "Dettagli articolo");
    viewModel.addAttribute("article", articleService.read(id));
    
    return "article/detail";
}

@GetMapping("/edit/{id}")
public String editArticle(@PathVariable("id") Long id, Model viewModel) {
    viewModel.addAttribute("title", "Article update");
    viewModel.addAttribute("article", articleService.read(id));
    viewModel.addAttribute("categories", categoryService.readAll());
    return "article/edit";
}

@GetMapping("revisor/detail/{id}")
public String revisorDetailArticle(@PathVariable("id") long id,Model viewModel){
    viewModel.addAttribute("title", "Dettaglio Articolo");
    viewModel.addAttribute("article",articleService.read(id));
    return"revisor/detail";
}

@PostMapping("/accept")
    public String articleSetAccepted(@RequestParam("action") String action, @RequestParam("articleId") long articleId, RedirectAttributes redirectAttributes) {
        if(action.equals("accept")){
            articleService.setIsAccepted(true,articleId);
            redirectAttributes.addFlashAttribute("resultMessage","Articolo accettato con successo");
        }else if(action.equals("reject")){
            articleService.setIsAccepted(false,articleId);
            redirectAttributes.addFlashAttribute("resultMessage","Articolo rifiutato con successo");
        }else{
            redirectAttributes.addFlashAttribute("resultMessage","Operazione non corretta");
        }
        return"redirect:/revisor/dashboard";
        
    }

    @GetMapping("/search")
    public String articleSearch(@Param("keyword") String keyword, Model viewModel) {
        viewModel.addAttribute("title", "Articoli trovati per la ricerca");
        List<ArticleDto> articles=articleService.search(keyword);

        List<ArticleDto> acceptedArticles=articles.stream().filter(article -> Boolean.TRUE.equals(article.getIsAccepted())).collect(Collectors.toList());
        viewModel.addAttribute("articles", acceptedArticles);
        return "article/articles";
    }

    @PostMapping("/update/{id}")
    public String articleUpdate(@PathVariable("id")Long id, 
                                @Valid @ModelAttribute("article") Article article, 
                                BindingResult result, 
                                RedirectAttributes redirectAttributes, 
                                Principal principal, 
                                MultipartFile file, 
                                Model viewModel) {

   
        if (result.hasErrors()) {
            viewModel.addAttribute("title", "Article update");
            article.setImage(articleService.read(id).getImage());
            viewModel.addAttribute("article", article);
            viewModel.addAttribute("categories", categoryService.readAll());
            return "article/edit";
        }
        
        articleService.update(id, article, file);
        redirectAttributes.addFlashAttribute("successMessage", "Articolo modificato con successo!");

        return "redirect:/articles"; 
    }

    @GetMapping("/delete/{id}")
    public String articleDelete (@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
      
        articleService.delete(id);
      
        redirectAttributes.addFlashAttribute("successMessage","Articolo cancellato con successo");
        return "redirect:/writer/dashboard";
    }
}
