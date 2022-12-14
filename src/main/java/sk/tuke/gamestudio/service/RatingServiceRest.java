package sk.tuke.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.Rating;

import java.util.Date;


public class RatingServiceRest implements RatingService{

    @Value("${remote.server.api}")
    private String url;

    @Autowired
    private RestTemplate template;

    @Override
    public void setRating(Rating rating) {

        Rating toUpdate = null;
        var arr = template.getForEntity(url + "/rates/" + rating.getGame(), Rating[].class).getBody();

        for (Rating r : arr) {
            if (r.getGame().equals(rating.getGame()) && r.getUsername().equals(rating.getUsername())) {
                toUpdate = r;
            }
        }
        if (toUpdate == null) {
            template.postForEntity(url + "/rates/" + rating.getGame() + "/" + rating.getUsername(), rating, Rating[].class);
        } else {
            toUpdate.setRate(rating.getRate());
            toUpdate.setRatedOn(new Date());
            HttpEntity<Rating> requestUpdate = new HttpEntity<>(toUpdate);
            template.exchange(url + "/rates/" + rating.getGame() + "/" + rating.getUsername(), HttpMethod.PUT, requestUpdate, Void.class);
//            template.postForEntity(url+"/rates/"+rating.getGame()+"/"+rating.getUsername(), rating, Rating[].class);
        }
    }


    @Override
    public int getAverageRating(String game) {
        var arr = template.getForEntity(url+"/rates/"+game, Rating[].class).getBody();
        int sum = 0;
        int count = 0;
        for (Rating r : arr) {
           if (r.getGame().equals(game)) {
               sum += r.getRate();
               count++;
           }
        }
        return count==0 ? 0 : sum/count ;
    }

    @Override
    public int getRating(String game, String username) {
        int rate = 0;
        var arr = template.getForEntity(url+"/rates/"+game+"/"+username, Rating[].class).getBody();
        for (Rating rating : arr) {
            if (rating.getUsername().equals(username)) {
                rate = rating.getRate();
            }
        }
        return rate;
    }


    public Rating getRatingObj(String game, String username) {
        var arr = template.getForEntity(url + "/rates/" + game, Rating[].class).getBody();
        Rating forGet = null;
        for (Rating rating : arr) {
            if (rating.getUsername().equals(username) && rating.getGame().equals(game)) {
                forGet = rating;
                break;
            }
        }
        return forGet;
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported");
    }

}
