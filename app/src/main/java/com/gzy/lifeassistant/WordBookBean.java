package com.gzy.lifeassistant;

import java.util.List;

/**
 * Created by Sunny on 2018/11/29.
 */

public class WordBookBean {

    private WordbookBean wordbook;

    public WordbookBean getWordbook() {
        return wordbook;
    }

    public void setWordbook(WordbookBean wordbook) {
        this.wordbook = wordbook;
    }

    public static class WordbookBean {
        private List<ItemBean> item;

        List<ItemBean> getItem() {
            return item;
        }

        public void setItem(List<ItemBean> item) {
            this.item = item;
        }

        public static class ItemBean {

            private String word;
            private String trans;
            private String phonetic;
            private String tags;

            public String getWord() {
                return word;
            }

            public void setWord(String word) {
                this.word = word;
            }

            public String getTrans() {
                return trans;
            }

            public void setTrans(String trans) {
                this.trans = trans;
            }

            public String getPhonetic() {
                return phonetic;
            }

            public void setPhonetic(String phonetic) {
                this.phonetic = phonetic;
            }

            public String getTags() {
                return tags;
            }

            public void setTags(String tags) {
                this.tags = tags;
            }

            @Override
            public String toString() {
                return "ItemBean{" +
                        "word='" + word + '\'' +
                        ", trans='" + trans + '\'' +
                        ", phonetic='" + phonetic + '\'' +
                        ", tags='" + tags + '\'' +
                        '}';
            }
        }
    }
}
