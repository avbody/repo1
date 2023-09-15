package com.random.crawler_news.pojo;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "news_item")
public class NewsItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_id")
    private Long newsId; //主键id
    private String newsTitle; //标题
    private String newsPage; //新闻网页
    private String newsSource; //信息来源
    private String sourceHref; //来源链接
    private Date newsDate; //新闻时间
    private String newsText; //新闻正文
    private String newsPics; //图片地址
    private String newsEditor; //编辑
    private String editorInCharge; //责任编辑
    private Date createTime; //创建时间

    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsPage() {
        return newsPage;
    }

    public void setNewsPage(String newsPage) {
        this.newsPage = newsPage;
    }

    public String getNewsSource() {
        return newsSource;
    }

    public void setNewsSource(String newsSource) {
        this.newsSource = newsSource;
    }

    public String getSourceHref() {
        return sourceHref;
    }

    public void setSourceHref(String sourceHref) {
        this.sourceHref = sourceHref;
    }

    public Date getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(Date newsDate) {
        this.newsDate = newsDate;
    }

    public String getNewsText() {
        return newsText;
    }

    public void setNewsText(String newsText) {
        this.newsText = newsText;
    }

    public String getNewsPics() {
        return newsPics;
    }

    public void setNewsPics(String newsPics) {
        this.newsPics = newsPics;
    }

    public String getNewsEditor() {
        return newsEditor;
    }

    public void setNewsEditor(String newsEditor) {
        this.newsEditor = newsEditor;
    }

    public String getEditorInCharge() {
        return editorInCharge;
    }

    public void setEditorInCharge(String editorInCharge) {
        this.editorInCharge = editorInCharge;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
