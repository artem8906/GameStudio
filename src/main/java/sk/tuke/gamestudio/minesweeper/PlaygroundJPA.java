package sk.tuke.gamestudio.minesweeper;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.minesweeper.consoleui.ConsoleUI;

import javax.persistence.EntityManager;
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

        entityManager.persist(new Score("minesweeper", "Jan", 0, new Date()));

        String game = "minesweeper";

        List<Score> bestScore =
                entityManager.createQuery("select sc from score sc where sc.game = :myGame order by sc.points desc")
                        .setParameter("myGame", game).getResultList();


        System.out.println("Closed JPA playground");
    }


}
