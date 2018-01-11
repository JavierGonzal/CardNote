package com.javier.cardnote.cardnote;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.javier.cardnote.R;
import com.javier.cardnote.data.Example;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by javiergonzalezcabezas on 11/1/18.
 */

public class CardNoteAdapter extends RecyclerView
        .Adapter<CardNoteAdapter
        .DataObjectHolder> {

    private Context context;
    private List<Example> result;
    private static CardNoteAdapter.ClickListener clickListener;

    static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        @BindView(R.id.card_note_item_title_textView)
        TextView name;

        @BindView(R.id.card_note_item_imageView)
        CircleImageView imageView;

        @BindView(R.id.card_note_item_description_textView)
        TextView description;

        @BindView(R.id.card_note_item_constraintLayout)
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
    public void setOnItemClickListener(CardNoteAdapter.ClickListener myClickListener) {
        this.clickListener = myClickListener;
    }

    public CardNoteAdapter(Context context, List<Example> example) {
        this.context = context;
        result = example;
    }

    @Override
    public CardNoteAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_note_item, parent, false);

        CardNoteAdapter.DataObjectHolder dataObjectHolder = new CardNoteAdapter.DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(CardNoteAdapter.DataObjectHolder holder, int position) {

        holder.name.setText(result.get(position).getTitle());
        holder.description.setText(String.valueOf(result.get(position).getDescription()));
        //holder.imageView.setImageDrawable(result.get(position).getWeather().get(FIRST_POSITION).getMain(), context));

    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    interface ClickListener {
        void onItemClick(int position);
    }
}

