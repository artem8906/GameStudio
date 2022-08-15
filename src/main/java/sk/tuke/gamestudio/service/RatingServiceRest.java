package sk.tuke.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.Rating;


public class RatingServiceRest implements RatingService{

    @Value("${remote.server.api}")
    private String url;

    @Autowired
    private RestTemplate template;

    @Override
    public void setRating(Rating rating) {


    }

    @Override
    public int getAverageRating(String game) {
        var arr = template.getForEntity(url+"/rates/"+game, Rating[].class).getBody();
        int sum = 0;
        for (Rating r : arr) {
            sum += r.getRate();
        }
        return sum/arr.length;
    }

    @Override
    public int getRating(String game, String username) {
        int rate = 0;
        var arr = template.getForEntity(url+"/rates/"+game, Rating[].class).getBody();
        for (Rating rating : arr) {
            if (rating.getUsername().equals(username)) {
                rate = rating.getRate();
            }
        }
        return rate;
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported");
    }

}
