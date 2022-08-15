package sk.tuke.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.RatingService;

import java.util.List;

@RestController
@RequestMapping("/api/rates")
public class RatingWebServiceRest {
    @Autowired
    private RatingService ratingService;

    @GetMapping("/{game}")
    public int getRating(@PathVariable String game) {
        return ratingService.getRating(game, "Artem");

    }

    @PostMapping
    public void addRating(@RequestBody Rating rating) {
        ratingService.setRating(rating);
    }
}
