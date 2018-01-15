package com.javier.cardnote.cardnote;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.javier.cardnote.R;
import com.javier.cardnote.data.Event;
import com.javier.cardnote.utils.InteractionListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.javier.cardnote.utils.Constants.RESTORE_LIST;

/**
 * Created by javiergonzalezcabezas on 15/1/18.
 */

public class CardNoteGridFragment extends Fragment implements CardNoteGridAdapter.ClickListener {

    @BindView(R.id.card_note_grid_fragment_recycler_view)
    RecyclerView recyclerView;

    ArrayList<Event> events;
    InteractionListener listener;

    public CardNoteGridFragment() {
        // Required empty public constructor
    }

    public static CardNoteGridFragment newInstance() {
        return new CardNoteGridFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            events = savedInstanceState.getParcelableArrayList(RESTORE_LIST);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(RESTORE_LIST, events);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.card_note_grid_fragment, container, false);
        ButterKnife.bind(this, view);
        showCardNote();
        return view;
    }

    public void initList(ArrayList<Event> result) {
        events = result;
    }

    public void showCardNote() {
        CardNoteGridAdapter adapter = new CardNoteGridAdapter(getActivity(), events);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

        final int spanCount = getResources().getInteger(R.integer.grid_columns);
        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), spanCount);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onItemClick(int position) {
        listener.onFragmentInteraction(events.get(position).getImage());
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof InteractionListener) {
            //init the listener
            listener = (InteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement InteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
