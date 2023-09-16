package com.random.crawler_news;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.random.crawler_news.pojo.NewsItem;
import com.random.crawler_news.service.NewsItemService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@SpringBootTest(classes = CrawlerApplication.class)
@RunWith(SpringRunner.class)
public class testService {
    @Autowired
    private NewsItemService service;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void testFind() {
        List<NewsItem> items = this.service.findExsitNews("haha");
        if (CollectionUtils.isEmpty(items)) {
            System.out.println("没有找到");
        } else {
            items.forEach(newsItem -> System.out.println(newsItem.getCreateTime()));
        }

    }

    @Test
    public void testSave() {
        NewsItem newsItem = new NewsItem();
        newsItem.setCreateTime(new Date());
        newsItem.setEditorInCharge("zhangsan");
        newsItem.setNewsText("hhahahahahahha");
        newsItem.setNewsTitle("一条新闻");
        this.service.save(newsItem);
    }

    @Test
    public void testUUID() {
        System.out.println(UUID.randomUUID().toString());
    }

    @Test
    public void testString() {
        String str = "发布时间：2023-09-15 19:40:49";
        System.out.println(str.substring(str.lastIndexOf("：") + 1));
    }

    @Test
    public void testJsoup() {

        Document document = Jsoup.parse("<span class=''>央视网</span>");
        String aa = document.select("span.source").text();
        System.out.println(StringUtils.equals(aa, null));
    }

    @Test
    public void testJsoup1() {
        String str = "<!--repaste.body.begin--><p><strong>　　央视网消息：</strong>9月14日，国务院新闻办公室举行新闻发布会，解读日前公布的《中共中央 国务院关于支持福建探索海峡两岸融合发展新路 建设两岸融合发展示范区的意见》（以下简称《意见》）。相关负责人表示，《意见》最大亮点，就是突出&ldquo;融&rdquo;这个目标，以通促融、以惠促融、以情促融，努力在福建全域建设两岸融合发展示范区。<br/><br/>　　<strong>推进闽台应通尽通 完善物流集散体系<br/></strong></p><p style=\"text-align: center;\" class=\"photo_img_20190808\"><img src=\"//p3.img.cctvpic.com/photoworkspace/contentimg/2023/09/15/2023091509460632378.jpg\" alt=\"\"/></p><p>　　福建与台湾隔海相望，地相近、人相亲。会上介绍，下一步，一是要适度超前开展交通物流基础设施的建设，加大资金等要素保障力度，推动闽台基础设施应通尽通。构建立体式、综合性的对台通道枢纽，畅通闽台与大陆其他地区的连接通道，完善区域物流集散体系。<br/><br/>　　<strong>争取实现两岸民众坐着高铁跨过海峡</strong><br/></p><p style=\"text-align: center;\" class=\"photo_img_20190808\"><img src=\"//p4.img.cctvpic.com/photoworkspace/contentimg/2023/09/15/2023091509461553323.jpg\" alt=\"\"/></p><p>　　二是进一步优化、加密福建沿海与台湾本岛及金门、马祖客货运输航线，为两岸同胞往来闽台和台胞在闽的停居留，创造更便利的条件和更加宽松的环境。<br/><br/>　　<strong>加强两岸能源领域合作 推动绿色转型</strong><br/></p><p style=\"text-align: center;\" class=\"photo_img_20190808\"><img src=\"//p2.img.cctvpic.com/photoworkspace/contentimg/2023/09/15/2023091509462370538.jpg\" alt=\"\"/></p><p>　　丛亮也表示，大陆已经建成世界上最大的能源体系，具备向台湾地区大规模输送绿色电力的条件。愿意加强两岸能源领域合作，共同推动两岸能源绿色转型发展。<br/></p><!--repaste.body.end-->";
        System.out.println(Jsoup.parse(str).text());
    }

    @Test
    public void testJsoup2() throws IOException {
        String str = FileUtils.readFileToString(new File("C:\\Users\\random\\Desktop\\html\\111.html"), "utf8");
        Document document = Jsoup.parse(str);
        Element element = document.select("#content_area").first();
        element.select("p > script").parents().remove();
        System.out.println(element.html());
    }

    @Test
    public void testToString() throws JsonProcessingException {

        List<String> list = new ArrayList<>();
        list.add("abcd");
        list.add("cdef");
        System.out.println(this.MAPPER.writeValueAsString(list));
    }
}