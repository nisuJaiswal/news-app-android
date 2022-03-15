package com.example.newsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class NewsDetails extends AppCompatActivity {
    String title,desc,content,imgUrl,url;
    private TextView newsTitle,newsDesc, newsContent;
    private Button readMore;
    private ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        title = getIntent().getStringExtra("title");
        desc = getIntent().getStringExtra("desc");
        content = getIntent().getStringExtra("content");
        imgUrl = getIntent().getStringExtra("img");
        url = getIntent().getStringExtra("url");

        newsTitle = findViewById(R.id.newsTitle);
        newsDesc = findViewById(R.id.newsDesc);
        newsContent = findViewById(R.id.content);
        img = findViewById(R.id.newsImg);
        readMore = findViewById(R.id.readMore);

        newsTitle.setText(title);
        newsDesc.setText(desc);
        newsContent.setText(content);
        Picasso.get().load(imgUrl).into(img);

        readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });


    }
}