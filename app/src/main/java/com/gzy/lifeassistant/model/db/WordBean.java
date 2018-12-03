package com.gzy.lifeassistant.model.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 单词数据库实体类
 *
 * @author gaozongyang
 * @date 2018/12/3
 */
@Entity
public class WordBean {

    @Id(autoincrement = true)
    private Long id;
    private String word;
    private String trans;
    private String phonetic;
    private String tags;

    @Generated(hash = 131991543)
    public WordBean(Long id, String word, String trans, String phonetic,
                    String tags) {
        this.id = id;
        this.word = word;
        this.trans = trans;
        this.phonetic = phonetic;
        this.tags = tags;
    }

    @Generated(hash = 1596526216)
    public WordBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return this.word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTrans() {
        return this.trans;
    }

    public void setTrans(String trans) {
        this.trans = trans;
    }

    public String getPhonetic() {
        return this.phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    public String getTags() {
        return this.tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}