package sk.tuke.gamestudio.server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.SpringClient;
import sk.tuke.gamestudio.minesweeper.consoleui.ConsoleUI;
import sk.tuke.gamestudio.service.*;

import javax.persistence.Entity;

@SpringBootApplication
@EntityScan(basePackages = "sk.tuke.gamestudio.entity")
public class GameStudioServer {
    public static void main(String[] args) {

        SpringApplication.run(GameStudioServer.class);

//        new SpringApplicationBuilder(SpringClient.class).web(WebApplicationType.NONE).run(args);
    }

    @Bean
    public ScoreService scoreService(){
        return new ScoreServiceJPA();
    }

    @Bean
    public CommentService commentService() {return new CommentServiceJPA();}

    @Bean
    public RatingService ratingService() {return new RatingServiceJPA();}



}


