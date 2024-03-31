package sk.tuke.gamestudio.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import sk.tuke.gamestudio.entity.Comment;

import java.util.List;

@Transactional
public class CommentServiceJPA implements CommentService{
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public void addComment(Comment comment, String username) {
        comment.setUsername(username);
        entityManager.persist(comment);
    }

    @Override
    public List<Comment> getUserComments(String username) {
        return entityManager.createNamedQuery("Comment.getUserComments", Comment.class).setParameter("username", username).getResultList();
    }

    @Override
    public List<Comment> getCommentsForCommunity() {
        return entityManager.createNamedQuery("Comment.getCommentsForCommunity", Comment.class).getResultList();
    }

    @Override
    public void reset() {
        entityManager.createNamedQuery("Comment.reset").executeUpdate();
    }
}
