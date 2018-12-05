package com.gzy.lifeassistant.ui.word;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gzy.lifeassistant.App;
import com.gzy.lifeassistant.R;
import com.gzy.lifeassistant.model.db.DaoSession;
import com.gzy.lifeassistant.model.db.WordBean;
import com.gzy.lifeassistant.model.db.WordBeanDao;

import java.util.ArrayList;
import java.util.List;

/**
 * 单词列表界面
 *
 * @author gaozongyang
 * @date 2018/12/5
 */
public class WordListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private List<WordBean> wordBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);

        initData();
        initView();
    }

    private void initData() {
        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        WordBeanDao wordBeanDao = daoSession.getWordBeanDao();
        wordBeanList.addAll(wordBeanDao.queryBuilder().list());
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.word_list_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new Adapter());
    }

    public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(WordListActivity.this);
            View root = inflater.inflate(R.layout.recycler_item_word, viewGroup, false);
            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            return new ViewHolder(root);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            viewHolder.bind(wordBeanList.get(i));
        }

        @Override
        public int getItemCount() {
            return wordBeanList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private TextView numTextView;
            private TextView conetentTextView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                numTextView = itemView.findViewById(R.id.recycler_item_word_num_text_view);
                conetentTextView = itemView.findViewById(R.id.recycler_item_word_content_text_view);
            }

            public void bind(WordBean wordBean) {
                numTextView.setText(String.valueOf(wordBean.getId()));
                conetentTextView.setText(wordBean.getWord());
            }
        }
    }
}
