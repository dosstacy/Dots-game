package sk.tuke.gamestudio.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import sk.tuke.gamestudio.entity.Comment;
import java.util.List;

@Transactional
public class CommentServiceJPA implements CommentService{
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public void addComment(Comment comment, String username) {
        if(comment == null || username == null){
            throw new GameStudioException("Comment or username cannot be null");
        }

        try {
            comment.setUsername(username);
            entityManager.persist(comment);
        }catch (Exception e){
            throw new GameStudioException(e);
        }
    }

    @Override
    public List<Comment> getUserComments(String username) {
        if(username == null){
            throw new GameStudioException("Username cannot be null");
        }

        List<Comment> comments;
        try{
            comments = entityManager.createNamedQuery("Comment.getUserComments", Comment.class)
                    .setParameter("username", username)
                    .getResultList();
        }catch (Exception e){
            throw new GameStudioException(e);
        }
        return comments;
    }

    @Override
    public List<Comment> getCommentsForCommunity() {
        List<Comment> comments;
        try{
            comments = entityManager.createNamedQuery("Comment.getCommentsForCommunity", Comment.class)
                    .getResultList();
        }catch (Exception e){
            throw new GameStudioException(e);
        }
        return comments;
    }

    @Override
    public void reset() {
        try {
            entityManager.createNamedQuery("Comment.reset").executeUpdate();
        }catch (Exception e){
            throw new GameStudioException(e);
        }
    }
}
