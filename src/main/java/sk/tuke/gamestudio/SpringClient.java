package sk.tuke.gamestudio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.tuke.gamestudio.minesweeper.PlaygroundJPA;
import sk.tuke.gamestudio.minesweeper.consoleui.ConsoleUI;
import sk.tuke.gamestudio.service.*;

import java.util.List;
import java.util.function.Predicate;

@SpringBootApplication
public class SpringClient {
    public static void main(String[] args) {
        SpringApplication.run(SpringClient.class);
    }

//    @Bean
//    public CommandLineRunner runner (ConsoleUI console) {
//        return s->console.play();
//    }

//    @Bean
//    public CommandLineRunner runnerJPA (PlaygroundJPA console) {
//        return s->console.play();
//    }



//    @Bean
//    public CommandLineRunner runnerJPA1 (PlaygroundJPA console) {
//        return new CommandLineRunner() {
//            @Override
//            public void run(String... args) throws Exception {
//                console.play();
//            }
//        };
//    }

    @Bean
    public ConsoleUI console() {
        return new ConsoleUI();
    }

    @Bean
    public PlaygroundJPA consoleJPA() {
        return new PlaygroundJPA();
    }

    @Bean
    public ScoreService scoreService(){
        return new ScoreServiceJPA();
    }

    @Bean
    public CommentService commentService() {
        return new CommentServiceJPA();
//        return new CommentServiceJDBC();
    }

    @Bean
    public RatingService ratingService() {
//        return new RatingServiceJDBC();
        return new RatingServiceJPA();

    }



}
