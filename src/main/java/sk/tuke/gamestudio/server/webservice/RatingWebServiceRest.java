package sk.tuke.gamestudio.server.webservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.RatingService;



@RestController
@RequestMapping("/api/rates")
public class RatingWebServiceRest {
    @Autowired
    private RatingService ratingService;

    @GetMapping("/{game}/{username}")
    public int getRating(@PathVariable String game, @PathVariable String name) {
        return ratingService.getRating(game, name);

    }

    @PostMapping()
    public void addRating(@RequestBody Rating rating) {
//        Rating toUpdate = ratingService.getRating(rating.getGame(), rating.getUsername());
//        if (toUpdate.getRate()==0) {
//            ratingService.setRating(rating);
//        }
//        else {
//            toUpdate.setRatedOn(new Date());
//            toUpdate.setRate(rating.getRate());
//            ratingService.setRating();
//        }
        ratingService.setRating(rating);

        }
    }

