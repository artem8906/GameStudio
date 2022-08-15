package sk.tuke.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.Score;

import java.util.Arrays;
import java.util.List;

public class ScoreServiceRest implements ScoreService{
    private String url = "http://localhost:8080/api";

    @Autowired
    private RestTemplate template;

    @Override
    public void addScore(Score score) {
        template.postForEntity(url+"/score", score, Score.class);
    }

    @Override
    public List<Score> getBestScores(String game) {
       return Arrays.asList(template.getForEntity(url+"/score/"+game, Score[].class).getBody());
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported");
    }
}
