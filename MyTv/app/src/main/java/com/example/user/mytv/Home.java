package com.example.user.mytv;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
        TextView lblTEST;
         RecyclerView lstMovies;
         RecycleViewAdapter mAdapter;
         RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        String url=String.format("https://api.themoviedb.org/3/movie/popular?api_key=968cca12b1a8492036b1e1e05af57e3f");
        lstMovies=(RecyclerView) findViewById(R.id.lstMovies);
        MovieSetAndGet[] movies=new MovieSetAndGet[20];
        new GetMoviesTask(movies).execute(url);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        lstMovies.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        lstMovies.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
















        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }










    public class GetMoviesTask extends AsyncTask<String, Void, MovieSetAndGet[]> {
        private TextView lblTEST;
        MovieSetAndGet [] movieItems;
        private Object title;

        public GetMoviesTask(MovieSetAndGet [] movieItems) {
            this.movieItems = movieItems;
        }

        @Override
        protected MovieSetAndGet [] doInBackground(String... strings) {
            MovieSetAndGet [] output=new MovieSetAndGet[0];
            try {
                URL url= new URL(strings[0]);
                HttpURLConnection urlConnection=(HttpURLConnection) url.openConnection();

                InputStream stream =new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder builder= new StringBuilder();

                String inputString;
                while((inputString=bufferedReader.readLine())!=null)
                {
                    builder.append(inputString);
                }

                JSONObject topLevel= new JSONObject(builder.toString());
                JSONArray data = topLevel.getJSONArray("results");

                MovieSetAndGet [] movieItems= new MovieSetAndGet[data.length()];

                for(int i=0; i<data.length();i++)
                {
                    JSONObject obj = (JSONObject) data.get(i);
                    String title=(String)obj.get("original_title");
                    String overview=(String)obj.get("overview");
                    String poster_path=(String)obj.get("poster_path");
                    int vote_count=(int)obj.get("vote_count");
                    String release_date=(String)obj.get("release_date");
                    boolean adult=(boolean)obj.get("adult");
                    String backdrop_path=(String) obj.get("backdrop_path");


                    MovieSetAndGet movie=new MovieSetAndGet();
                    movie.setTitle(title);
                    movie.setAdult(adult);
                    movie.setOverview(overview);
                    movie.setPoster_path(poster_path);
                    movie.setVote_count(vote_count);
                    movie.setRelease_date(release_date);
                    movie.setBackdrop_path(backdrop_path);
                    movieItems[i]=movie;
                }

                output = movieItems;
                urlConnection.disconnect();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return output;
        }
        protected void onPostExecute(MovieSetAndGet [] movieItems) {
            mAdapter = new RecycleViewAdapter();
            lstMovies.setAdapter(mAdapter);
            mAdapter.setMovies(movieItems);

            //this code make the Recyclerview SCROLL HORIZONTAL
           // lstMovies.setLayoutManager(new LinearLayoutManager(Home.this, LinearLayoutManager.HORIZONTAL, false));
        }
    }




























    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
