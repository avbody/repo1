package com.random.crawler_news.service;

import com.random.crawler_news.pojo.NewsItem;
import com.random.crawler_news.repository.NewsItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NewsItemService {
    @Autowired
    private NewsItemRepository newsItemRepository;

    @Transactional
    public List<NewsItem> findExsitNews(String newsPage){
        return this.newsItemRepository.findAllByNewsPage(newsPage);
    };

    @Transactional
    public void save(NewsItem newsItem){
        this.newsItemRepository.save(newsItem);
    }
}
