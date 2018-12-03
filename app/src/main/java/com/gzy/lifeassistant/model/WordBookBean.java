package com.gzy.lifeassistant.model;

import com.gzy.lifeassistant.model.db.WordBean;

import java.util.List;

/**
 * 四级英语单词书
 *
 * @author gaozongyang
 * @date 2018/12/3
 */
public class WordBookBean {

    private WordbookBean wordbook;

    public WordbookBean getWordbook() {
        return wordbook;
    }

    public void setWordbook(WordbookBean wordbook) {
        this.wordbook = wordbook;
    }

    public class WordbookBean {
        private List<WordBean> item;

        public List<WordBean> getItem() {
            return item;
        }

        public void setItem(List<WordBean> item) {
            this.item = item;
        }
    }
}
