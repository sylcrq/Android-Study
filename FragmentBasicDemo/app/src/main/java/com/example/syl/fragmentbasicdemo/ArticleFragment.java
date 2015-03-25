package com.example.syl.fragmentbasicdemo;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArticleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArticleFragment extends Fragment {

    private static final String ARG_CURRENTPAGENUM = "current_page_num";

    private int mCurrentPageNum;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ArticleFragment.
     */
    public static ArticleFragment newInstance(int pageNum) {
        ArticleFragment fragment = new ArticleFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CURRENTPAGENUM, pageNum);
        fragment.setArguments(args);
        return fragment;
    }

    public ArticleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCurrentPageNum = getArguments().getInt(ARG_CURRENTPAGENUM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_article, container, false);

        TextView textView = (TextView)view.findViewById(R.id.article);
        updateArticle(textView);

        return view;
    }

    private void updateArticle(TextView textView) {
        textView.setText(Article.ARTICLE[mCurrentPageNum]);
    }
}
