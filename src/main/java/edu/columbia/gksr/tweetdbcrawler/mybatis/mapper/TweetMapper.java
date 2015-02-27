package edu.columbia.gksr.tweetdbcrawler.mybatis.mapper;


import edu.columbia.gksr.tweetdbcrawler.domain.Tweet;

import java.util.List;

/**
 * Created by saikarthikreddyginni on 2/27/15.
 */

public interface TweetMapper {

    public Tweet getTweetById(long id);

    public List<Tweet> getAllTweets();

    public List<Tweet> getTweetsLimit(int limit);

    public List<Tweet> getTweetsByHashTag(String hashTag);

    public List<String> getAllHashTags();

    public List<String> getHashTagsLimit(int limit);

    public void updateTweet(Tweet tweet);

    public void insertTweet(Tweet tweet);

//    public void insertHashTags(List<String> hashTags, int tweetId);

    public void deleteTweet(Tweet tweet);

//    public void deleteHashTags(int tweetId);

}
