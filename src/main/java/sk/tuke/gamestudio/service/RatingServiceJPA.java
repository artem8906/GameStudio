package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Rating;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
public class RatingServiceJPA implements RatingService{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) {
//
//        entityManager.createQuery("select avg (rate) from Rating rt where rt.game = :myGame")
//                .setParameter("myGame", game).getSingleResult();
//
//        if (true) {
//
//        }
//
//        else{
            entityManager.persist(rating);
        }


    @Override
    public int getAverageRating(String game) {
        return (Integer) entityManager.createQuery("select avg (rate) from Rating rt where rt.game = :myGame")
                .setParameter("myGame", game).getSingleResult();
    }

    @Override
    public int getRating(String game, String username) {
//        return (Integer) entityManager.createQuery("select rate from Rating rt where rt.game = :myGame and rt.user = :MyUser")
//                .setParameter("myGame", game).setParameter("myUser", username).getSingleResult();
        Rating rating = (Rating) entityManager.createQuery("select rt from Rating rt where rt.game = :myGame and rt.username = :MyUser")
                .setParameter("myGame", game).setParameter("myUser", username).getSingleResult();
                return rating.getRate();
    }

    @Override
    public void reset() {
        entityManager.createNativeQuery("DELETE from rating").executeUpdate();
    }
}
