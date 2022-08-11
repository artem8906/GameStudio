package sk.tuke.gamestudio.minesweeper;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.minesweeper.consoleui.ConsoleUI;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Transactional
public class PlaygroundJPA {

    @PersistenceContext
    private EntityManager entityManager;

    public void play() {
        System.out.println("Opening JPA playground");


        String game = "minesweeper";
        String name = "Jan";
        int rateValue = 4;
        entityManager.persist(new Rating(game, name, 1, new Date()));
        Rating rating = null;
        try {
            rating = (Rating) entityManager.createQuery("select r from Rating r where r.username=:user and r.game =:game").setParameter("user", name).setParameter("game", game)
                    .getSingleResult();
            rating.setRatedOn(new Date());
            rating.setRate(rateValue);
        }
        catch (NoResultException e) {
            entityManager.persist(new Rating(game, name, rateValue, new Date()));
        }



        System.out.println("Closed JPA playground");
    }


}
