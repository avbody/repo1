package com.random.crawler_news.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.random.crawler_news.pojo.NewsItem;
import com.random.crawler_news.service.NewsItemService;
import com.random.crawler_news.utils.HttpUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class NewsItemTask {
    @Autowired
    private NewsItemService newsItemService;

    @Autowired
    private HttpUtils httpUtils;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    //设置定时任务执行完成后，再间隔100秒执行一次
    @Scheduled(fixedDelay = 100 * 1000)
    public void pocess(){
        String url = "https://search.cctv.com/search.php?qtext=%E5%8F%B0%E6%B9%BE&type=web&sort=date&datepid=4&channel=&vtime=-1&is_search=1&page=";
        //爬取央视网一个月内的台湾新闻
        for (int i = 1; i < 1000; i++) {
            String html = this.httpUtils.getHtml(url + i);
            Document document = Jsoup.parse(html);
            Elements elements = document.select("div.tright");
            if (CollectionUtils.isEmpty(elements)){
                break;
            }
            for (Element element : elements) {
                NewsItem newsItem = new NewsItem();
                //解析新闻网页
                String newsPage = element.select("[lanmu1]").attr("lanmu1");
                //判断新闻是否已存储
                List<NewsItem> exsitNews = this.newsItemService.findExsitNews(newsPage);
                if (!CollectionUtils.isEmpty(exsitNews)){
                    //已存储跳过
                    continue;
                }

                //解析新闻时间
                String timeStr = element.select("span.tim").text(); //2023-09-15 19:40:49
                String time = timeStr.substring(timeStr.lastIndexOf("：") + 1);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date newsDate = null;
                try {
                    newsDate = format.parse(time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //进入新闻页面
                String newshtml = this.httpUtils.getHtml(newsPage);
                Document newsDocument = Jsoup.parse(newshtml);
                //解析标题
                String newsTitle = newsDocument.select("#title_area > h1").text();

                //解析信息来源
                String newsSource = newsDocument.select("span.source").text();
                if (StringUtils.isEmpty(newsSource)){
                    newsSource = newsDocument.select("[name=source]").attr("content");
                }


                //解析来源链接
                String sourceHref = null;
                Elements linkEls = newsDocument.select("a[href]");
                for (Element linkEl : linkEls) {
                    if (StringUtils.equals(linkEl.text(),newsSource)){
                        sourceHref = linkEl.attr("href");
                    }
                }

                //解析编辑
                String newsEditor = null;
                if (!CollectionUtils.isEmpty(newsDocument.select("div.zebian > span"))){
                    String editorStr = newsDocument.select("div.zebian > span").first().text();
                    newsEditor = editorStr.substring(editorStr.lastIndexOf("：") + 1);
                }


                //解析责任编辑
                String zbStr = newsDocument.select("#zb").text();
                String editorInCharge = zbStr.substring(zbStr.lastIndexOf("：") + 1);

                //解析新闻正文
                Elements textEl = newsDocument.select("#content_area");
                if (StringUtils.isEmpty(textEl.text())){
                    textEl = newsDocument.select("#text_area");
                }
                textEl.select("p > script").parents().remove();
                String newsText = textEl.html();

                //解析图片
                Elements imgEls = textEl.select("img[src]");
                String newsPics = null;
                if (!CollectionUtils.isEmpty(imgEls)){
                    List<String> picList = new ArrayList<>();
                    for (Element imgEl : imgEls) {
                        String oldPic = "https:" + imgEl.attr("src");
                        String newsPic = this.httpUtils.getImage(oldPic);
                        picList.add(newsPic);
                        newsText = newsText.replace(imgEl.attr("src"), newsPic);
                    }
                    try {
                        newsPics = this.MAPPER.writeValueAsString(picList);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }

                newsItem.setNewsTitle(newsTitle);
                newsItem.setNewsText(newsText);
                newsItem.setEditorInCharge(editorInCharge);
                newsItem.setNewsDate(newsDate);
                newsItem.setNewsEditor(newsEditor);
                newsItem.setNewsPics(newsPics);
                newsItem.setNewsPage(newsPage);
                newsItem.setNewsSource(newsSource);
                newsItem.setSourceHref(sourceHref);
                newsItem.setCreateTime(new Date());

                this.newsItemService.save(newsItem);
            }
        }
    }
}
