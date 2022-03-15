package com.example.newsapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CategoryRVAdapter.CategoryClickInterface{

    private RecyclerView category,news;
    private ProgressBar loading;
    private ArrayList<Articles> articlesArrayList;
    private ArrayList<CategoryRVModel> categoryRVModelArrayList;
    private CategoryRVAdapter categoryRVAdapter;
    private NewsRVAdapter newsRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        news = findViewById(R.id.verticleRV);
        category = findViewById(R.id.horizontalRV);
        loading = findViewById(R.id.loadingBar);
        articlesArrayList = new ArrayList<>();
        categoryRVModelArrayList = new ArrayList<>();
        newsRVAdapter = new NewsRVAdapter(articlesArrayList,this);
        categoryRVAdapter = new CategoryRVAdapter(categoryRVModelArrayList,this,this::onCategoryClick);
        news.setLayoutManager(new LinearLayoutManager(this));
        news.setAdapter(newsRVAdapter);
        category.setAdapter(categoryRVAdapter);
        getCategories();
        getAllNews("All");
        newsRVAdapter.notifyDataSetChanged();

    }

    private void getCategories(){
        categoryRVModelArrayList.add(new CategoryRVModel("All", "https://images.pexels.com/photos/6878196/pexels-photo-6878196.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        categoryRVModelArrayList.add(new CategoryRVModel("Technology", "https://images.pexels.com/photos/3201478/pexels-photo-3201478.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        categoryRVModelArrayList.add(new CategoryRVModel("Science", "https://images.pexels.com/photos/2150/sky-space-dark-galaxy.jpg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        categoryRVModelArrayList.add(new CategoryRVModel("Sports", "https://images.pexels.com/photos/46798/the-ball-stadion-football-the-pitch-46798.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        categoryRVModelArrayList.add(new CategoryRVModel("General", "https://images.pexels.com/photos/2325307/pexels-photo-2325307.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        categoryRVModelArrayList.add(new CategoryRVModel("Health", "https://images.pexels.com/photos/4525126/pexels-photo-4525126.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        categoryRVModelArrayList.add(new CategoryRVModel("Entertainment", "https://images.pexels.com/photos/7991489/pexels-photo-7991489.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));

        categoryRVAdapter.notifyDataSetChanged();
    }

    private void getAllNews(String category){
        loading.setVisibility(View.VISIBLE);
        articlesArrayList.clear();
        String categoryUrl = "https://newsapi.org/v2/top-headlines?country=in&category=" + category + "&apiKey=3334616adccd4f8db590bdb240beff59";
        String url = "https://newsapi.org/v2/top-headlines?country=in&exludeDomain=stackoverflow.com&language=en&apiKey=3334616adccd4f8db590bdb240beff59";
        String BASEURL = "https://newsapi.org";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetroFitAPI retroFitAPI = retrofit.create(RetroFitAPI.class);
        Call<NewsModel> call;
        if(category.equals("All")){
            call = retroFitAPI.getAllNews(url);
        }
        else{
            call = retroFitAPI.getAllNews(categoryUrl);
        }

        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                NewsModel newsModel = response.body();
                loading.setVisibility(View.GONE);
                ArrayList<Articles> articles = newsModel.getArticles();

                    for(int i = 0 ; i < articles.size() ; i++){
                        articlesArrayList.add(new Articles(articles.get(i).getTitle(),articles.get(i).getDescription(),articles.get(i).getUrlToImage(),articles.get(i).getUrl(),articles.get(i).getContent()));

                }
                newsRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {

            }
        });
    }
    @Override
    public void onCategoryClick(int position) {
        String category = categoryRVModelArrayList.get(position).getCategory();
        getAllNews(category);
    }
}