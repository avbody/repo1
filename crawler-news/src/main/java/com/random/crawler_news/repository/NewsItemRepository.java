package com.random.crawler_news.repository;

import com.random.crawler_news.pojo.NewsItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsItemRepository extends JpaRepository<NewsItem,Long> {

    List<NewsItem> findAllByNewsPage(String newsPage);
}
