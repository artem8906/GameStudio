package sk.tuke.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.ScoreService;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentWebServiceRest {

    @Autowired
    private CommentService commentService;

    @GetMapping("/{game}")
    public List<Comment> getComments(@PathVariable String game) {
        return commentService.getComments(game);
    }

    @PostMapping
    public void addComment(@RequestBody Comment comment) {
        commentService.addComment(comment);
    }
}
