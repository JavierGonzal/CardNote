package com.javier.cardnote.cardnote;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.javier.cardnote.R;
import com.javier.cardnote.data.Event;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by javiergonzalezcabezas on 15/1/18.
 */

public class CardNoteGridAdapter extends RecyclerView
        .Adapter<CardNoteGridAdapter
        .DataObjectHolder> {
    private Context context;
    private List<Event> result;
    private static CardNoteGridAdapter.ClickListener clickListener;

    static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        @BindView(R.id.card_note_grid_item_name_textView)
        TextView name;

        @BindView(R.id.card_note_grid_item_imageView)
        CircleImageView imageView;

        @BindView(R.id.card_note_grid_item_constraintLayout)
        ConstraintLayout constraintLayout;

        DataObjectHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            constraintLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition());
        }
    }
    public void setOnItemClickListener(CardNoteGridAdapter.ClickListener myClickListener) {
        this.clickListener = myClickListener;
    }

    public CardNoteGridAdapter(Context context, List<Event> example) {
        this.context = context;
        result = example;
    }

    @Override
    public CardNoteGridAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_note_grid_item, parent, false);

        CardNoteGridAdapter.DataObjectHolder dataObjectHolder = new CardNoteGridAdapter.DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(CardNoteGridAdapter.DataObjectHolder holder, int position) {

        holder.name.setText(result.get(position).getTitle());
        Glide.with(context).load(result.get(position).getImage()).apply(RequestOptions.circleCropTransform()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    interface ClickListener {
        void onItemClick(int position);
    }
}
