package edu.columbia.gksr.tweetdbcrawler.app;


import edu.columbia.gksr.tweetdbcrawler.domain.Tweet;
import edu.columbia.gksr.tweetdbcrawler.mybatis.service.TweetService;
import edu.columbia.gksr.tweetdbcrawler.util.StringUtils;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by saikarthikreddyginni on 2/27/15.
 */

public class App {

    public static final Logger LOGGER = Logger.getLogger(App.class.getCanonicalName());
    public static final int MAX_TWEETS = 20000;

    public static void main(String[] args) {


        LOGGER.log(Level.INFO, "Accessing the twitter.properties file");

        Properties twitterProperties = new Properties();
        InputStream input = App.class.getClassLoader().getResourceAsStream("twitter.properties");
        try {
            twitterProperties.load(input);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "IOException Occurred when getting the twitter.properties", ex);
            System.exit(-1);
        }

        LOGGER.log(Level.INFO, "Creating the Configuration Builder");

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(twitterProperties.getProperty("twitter.OAuthConsumerKey"))
                .setOAuthConsumerSecret(twitterProperties.getProperty("twitter.OAuthConsumerSecret"))
                .setOAuthAccessToken(twitterProperties.getProperty("twitter.OAuthAccessToken"))
                .setOAuthAccessTokenSecret(twitterProperties.getProperty("twitter.OAuthAccessTokenSecret"));

        LOGGER.log(Level.INFO, "Creating the Configuration Builder");

        final TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
        final TweetService tweetService = new TweetService();

        StatusListener listener = new StatusListener() {

            long count = tweetService.getTweetCount();

            @Override
            public void onStatus(Status status) {

                if (status.getGeoLocation() != null) {
                    Tweet tweet = new Tweet();
                    tweet.setId(status.getId());
                    tweet.setUserScreenName(StringUtils.convertToUTF8(status.getUser().getScreenName()));
                    tweet.setUserLocation(StringUtils.convertToUTF8(status.getUser().getLocation()));
                    tweet.setProfileImageURL(status.getUser().getBiggerProfileImageURL());
                    tweet.setStatusText(StringUtils.convertToUTF8(status.getText()));
                    tweet.setLatitude(status.getGeoLocation().getLatitude());
                    tweet.setLongitude(status.getGeoLocation().getLongitude());
                    tweet.setCreatedDate(status.getCreatedAt());
                    tweet.setUpdatedDate(new Date());

                    List<String> hashTags = new ArrayList<String>();
                    HashtagEntity[] entities = status.getHashtagEntities();

                    for (int i = 0; i < entities.length; i++) {
                        hashTags.add(StringUtils.convertToUTF8(entities[i].getText()));
                    }

                    tweet.setHashTags(hashTags);
                    if (count > MAX_TWEETS) {
                        long tweetId = tweetService.getOldestTweetId();
                        tweetService.deleteTweetById(tweetId);
                        LOGGER.log(Level.INFO, "Deleting " + count + " Tweet with tweetId: " + tweetId);
                        count--;
                    }
                    tweetService.insertTweet(tweet);
                    LOGGER.log(Level.INFO, "Inserting " + count + " Tweet with tweetId: " + tweet.getId());
                    count++;
                }
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
                // Do Nothing
            }

            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                // Do Nothing
            }

            @Override
            public void onScrubGeo(long userId, long upToStatusId) {
                // Do Nothing
            }

            @Override
            public void onStallWarning(StallWarning warning) {
                // Do Nothing
            }

            @Override
            public void onException(Exception ex) {
                LOGGER.log(Level.SEVERE, "Exception in Status Listener", ex);
            }
        };
        twitterStream.addListener(listener);
        twitterStream.sample();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("Shutting down...Removing listeners and cleaning up twitterStream");
                twitterStream.clearListeners();
                twitterStream.cleanUp();
                twitterStream.shutdown();
                System.out.println("Shutdown Successful");
            }
        });
    }
}