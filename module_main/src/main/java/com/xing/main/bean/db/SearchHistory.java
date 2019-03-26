package com.xing.main.bean.db;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "SearchHistory")
public class SearchHistory {

    @Id(autoincrement = true)
    private Long id;
    private String keyword;
    private Long time;

    public SearchHistory(String keyword, Long time) {
        this.keyword = keyword;
        this.time = time;
    }


    @Generated(hash = 238310654)
    public SearchHistory(Long id, String keyword, Long time) {
        this.id = id;
        this.keyword = keyword;
        this.time = time;
    }

    @Generated(hash = 1905904755)
    public SearchHistory() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeyword() {
        return this.keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Long getTime() {
        return this.time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "SearchHistory{" +
                "id=" + id +
                ", keyword='" + keyword + '\'' +
                ", time=" + time +
                '}';
    }
}
