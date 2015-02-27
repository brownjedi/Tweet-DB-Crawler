package edu.columbia.gksr.tweetdbcrawler.mybatis.service;

import edu.columbia.gksr.tweetdbcrawler.domain.Tweet;
import edu.columbia.gksr.tweetdbcrawler.mybatis.mapper.TweetMapper;
import edu.columbia.gksr.tweetdbcrawler.mybatis.util.MyBatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * Created by saikarthikreddyginni on 2/27/15.
 */

public class TweetService {

    public Tweet getTweetById(int id) {

        SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession();
        Tweet tweet;
        try {
            TweetMapper tweetMapper = sqlSession.getMapper(TweetMapper.class);
            tweet = tweetMapper.getTweetById(id);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
        return tweet;
    }

    public List<Tweet> getAllTweets() {
        SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession();
        List<Tweet> tweetList;
        try {
            TweetMapper tweetMapper = sqlSession.getMapper(TweetMapper.class);
            tweetList = tweetMapper.getAllTweets();
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
        return tweetList;
    }

    public List<Tweet> getTweetsLimit(int limit) {
        SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession();
        List<Tweet> tweetList;
        try {
            TweetMapper tweetMapper = sqlSession.getMapper(TweetMapper.class);
            tweetList = tweetMapper.getTweetsLimit(limit);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
        return tweetList;
    }

    public List<Tweet> getTweetsByHashTag(String hashTag) {
        SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession();
        List<Tweet> tweetList;
        try {
            TweetMapper tweetMapper = sqlSession.getMapper(TweetMapper.class);
            tweetList = tweetMapper.getTweetsByHashTag(hashTag);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
        return tweetList;
    }

    public List<String> getAllHashTags() {
        SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession();
        List<String> hashTagList;
        try {
            TweetMapper tweetMapper = sqlSession.getMapper(TweetMapper.class);
            hashTagList = tweetMapper.getAllHashTags();
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
        return hashTagList;
    }

    public List<String> getHashTagsLimit(int limit) {
        SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession();
        List<String> hashTagList;
        try {
            TweetMapper tweetMapper = sqlSession.getMapper(TweetMapper.class);
            hashTagList = tweetMapper.getHashTagsLimit(limit);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
        return hashTagList;
    }

    public void updateTweet(Tweet tweet) {
        SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession();
        try {
            TweetMapper tweetMapper = sqlSession.getMapper(TweetMapper.class);
            tweetMapper.updateTweet(tweet);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    public void insertTweet(Tweet tweet) {
        SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession();
        try {
            TweetMapper tweetMapper = sqlSession.getMapper(TweetMapper.class);
            tweetMapper.insertTweet(tweet);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    public void deleteTweet(Tweet tweet) {
        SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession();
        try {
            TweetMapper tweetMapper = sqlSession.getMapper(TweetMapper.class);
            tweetMapper.deleteTweet(tweet);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }
}
