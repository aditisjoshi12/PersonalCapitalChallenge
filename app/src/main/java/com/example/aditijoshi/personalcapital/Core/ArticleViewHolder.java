package com.example.aditijoshi.personalcapital.Core;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aditijoshi.personalcapital.DataModel.Article;
import com.example.aditijoshi.personalcapital.R;

import de.greenrobot.event.EventBus;

/**
 * Created by aditijoshi on 9/24/17.
 */

public class ArticleViewHolder extends RecyclerView.ViewHolder {

    ImageView thumbnail;
    TextView title;
    TextView timestamp;
    LinearLayout outerLayout;
    Context context;


    ArticleViewHolder(LinearLayout outerLayout, Context context) {
        super(outerLayout);
        this.context = context;
        this.outerLayout = outerLayout;
        outerLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = (int) context.getResources().getDimension(R.dimen.article_margin);
        params.rightMargin = (int) context.getResources().getDimension(R.dimen.article_margin);;
        params.bottomMargin = (int) context.getResources().getDimension(R.dimen.article_margin);;
        params.topMargin = (int) context.getResources().getDimension(R.dimen.article_margin);;

        outerLayout.setLayoutParams(params);
        outerLayout.setBackgroundColor(UIHelper.getInstance().getColor(context, R.color.colorWhite));

        thumbnail = new ImageView(context);
        thumbnail.setScaleType(ImageView.ScaleType.FIT_XY);

        timestamp = new TextView(context);
        timestamp.setPadding((int) context.getResources().getDimension(R.dimen.article_padding), (int) context.getResources().getDimension(R.dimen.article_padding), (int) context.getResources().getDimension(R.dimen.article_padding), (int) context.getResources().getDimension(R.dimen.article_padding));
        timestamp.setTextColor(UIHelper.getInstance().getColor(context,R.color.black));

        title = new TextView(context);
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        title.setPadding((int) context.getResources().getDimension(R.dimen.article_padding), (int) context.getResources().getDimension(R.dimen.article_padding), (int) context.getResources().getDimension(R.dimen.article_padding), (int) context.getResources().getDimension(R.dimen.article_padding));
        title.setTextColor(UIHelper.getInstance().getColor(context,R.color.black));

        outerLayout.addView(thumbnail);
        outerLayout.addView(title);
        outerLayout.addView(timestamp);


    }

    public void setup(final Article currentArticle) {

        resetViewHolder();
        title.setText(currentArticle.getTitle());
        timestamp.setText(UIHelper.getInstance().formatDate(currentArticle.getPubDate()));
        UIHelper.getInstance().displayImage(currentArticle.getImageURL(), thumbnail, UIHelper.getInstance().rectangularDisplayOption, null);
        outerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new InflateWebViewEvent(currentArticle.getUrl(), currentArticle.getTitle()));
            }
        });

    }

    private void resetViewHolder() {
        title.setText("");
        thumbnail.setBackground(null);
        timestamp.setText("");
    }


}
