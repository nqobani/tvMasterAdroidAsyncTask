package com.example.user.mytv;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by User on 06/09/2016.RecycleViewAdapter
 */
public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {
    private MovieSetAndGet[] mMovies;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        public ImageView imgPoster;
        public TextView lblTitle;
        public TextView lblReleaseDate;
        public TextView lblOverview;
        public TextView lblVote;
        public ViewHolder(View v) {
            super(v);
            imgPoster =(ImageView) v.findViewById(R.id.imgPoster);
            lblTitle=(TextView)v.findViewById(R.id.lblTitle);
            lblOverview=(TextView)v.findViewById(R.id.lblOverView);
            lblReleaseDate=(TextView)v.findViewById(R.id.lblReleaseDate);
            lblVote=(TextView)v.findViewById(R.id.lblVoteCount);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecycleViewAdapter() {
        //new GetMoviesTask(mMovies).execute(url);

        //mMovies = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    public void setMovies(MovieSetAndGet[] movies) {
        mMovies = movies;
        notifyDataSetChanged();
    }
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        Picasso.with(holder.imgPoster.getContext()).load("https://image.tmdb.org/t/p/w780"+mMovies[position].getBackdrop_path()).into(holder.imgPoster);
        holder.lblOverview.setText(mMovies[position].getOverview());
        holder.lblTitle.setText(mMovies[position].getTitle());
        holder.lblReleaseDate.setText((mMovies[position].getRelease_date()));
        holder.lblVote.setText("Voite Count: "+mMovies[position].getVote_count());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mMovies.length;
    }
}