package com.example.aditijoshi.personalcapital.Core;

import android.content.Context;
import android.text.Html;
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

public class HeaderView {


    ImageView thumbnail;
    TextView title;
    TextView timestamp;
    TextView summary;
    LinearLayout outerLayout;
    Context context;


    HeaderView(LinearLayout outerLayout, Context context) {

        this.context = context;
        this.outerLayout = outerLayout;
        outerLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        outerLayout.setLayoutParams(params);
        outerLayout.setBackgroundColor(UIHelper.getInstance().getColor(context, R.color.colorWhite));

        ViewGroup.LayoutParams viewParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        thumbnail = new ImageView(context);
        thumbnail.setLayoutParams(viewParams);
        thumbnail.setScaleType(ImageView.ScaleType.CENTER_CROP);

        title = new TextView(context);
        title.setLayoutParams(viewParams);
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        title.setPadding((int) context.getResources().getDimension(R.dimen.article_padding), (int) context.getResources().getDimension(R.dimen.article_padding), (int) context.getResources().getDimension(R.dimen.article_padding), (int) context.getResources().getDimension(R.dimen.article_padding));
        title.setTextColor(UIHelper.getInstance().getColor(context,R.color.black));
        summary = new TextView(context);
        summary.setLayoutParams(viewParams);
        summary.setMaxLines(3);
        summary.setPadding((int) context.getResources().getDimension(R.dimen.article_padding), (int) context.getResources().getDimension(R.dimen.article_padding), (int) context.getResources().getDimension(R.dimen.article_padding), (int) context.getResources().getDimension(R.dimen.article_padding));
        summary.setTextColor(UIHelper.getInstance().getColor(context,R.color.black));

        timestamp = new TextView(context);
        timestamp.setLayoutParams(viewParams);
        timestamp.setPadding((int) context.getResources().getDimension(R.dimen.article_padding), (int) context.getResources().getDimension(R.dimen.article_padding), (int) context.getResources().getDimension(R.dimen.article_padding), (int) context.getResources().getDimension(R.dimen.article_padding));
        timestamp.setTextColor(UIHelper.getInstance().getColor(context,R.color.black));

        outerLayout.addView(thumbnail);
        outerLayout.addView(title);
        outerLayout.addView(summary);
        outerLayout.addView(timestamp);
        resetViewHolder();

    }

    public void setup(final Article currentArticle) {

        resetViewHolder();
        title.setText(currentArticle.getTitle());
        summary.setText(Html.fromHtml(currentArticle.getDescription()));

        String s = summary.getText().toString();
        if(s.length()>8) {
            s = s.substring(0, s.length() - 8);
            s = s.concat("...");
        }
        summary.setText(Html.fromHtml(s));
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
        summary.setText("");
        timestamp.setText("");
    }

}
