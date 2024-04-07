package sk.tuke.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.services.CommentService;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentServiceRest {

    @Autowired
    private CommentService commentService;

    @PostMapping("/addComment")
    public void addComment(@RequestBody Comment comment, @RequestParam String username){
        commentService.addComment(comment, username);
    }

    @GetMapping("/{username}")
    public List<Comment> getUserComments(@PathVariable String username){
        return commentService.getUserComments(username);
    }

    @GetMapping("/communityComments")
    public List<Comment> getCommentsForCommunity(){
        return commentService.getCommentsForCommunity();
    }

}
