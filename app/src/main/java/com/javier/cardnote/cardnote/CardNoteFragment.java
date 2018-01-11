package com.javier.cardnote.cardnote;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.javier.cardnote.R;
import com.javier.cardnote.data.Example;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.design.widget.Snackbar.LENGTH_LONG;

/**
 * Created by javiergonzalezcabezas on 11/1/18.
 */

public class CardNoteFragment extends Fragment implements CardNoteContract.View,CardNoteAdapter.ClickListener {
    @BindView(R.id.card_note_fragment_progress)
    ProgressBar progressBar;

    @BindView(R.id.card_note_fragment_retry_button)
    Button retry;

    @BindView(R.id.card_note_fragment_contraintLayout)
    ConstraintLayout constraintLayout;

    @BindView(R.id.card_note_fragment_recycler_view)
    RecyclerView recyclerView;

    CardNoteAdapter adapter;

    CardNoteContract.Presenter presenter;

    List<Example> exampleList;

    public CardNoteFragment() {
        // Required empty public constructor
    }

    public static CardNoteFragment newInstance() {
        return new CardNoteFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.card_note_fragment, container, false);
        ButterKnife.bind(this, view);
        presenter.subscribe();
        return view;
    }
    @Override
    public void setPresenter(CardNoteContract.Presenter presenter) {
        this.presenter = presenter;

    }

    @Override
    public void showCardNote(List<Example> listElements) {
        exampleList = listElements;
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new CardNoteAdapter(getActivity(), listElements);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

    }

    @Override
    public void showError() {
        progressBar.setVisibility(View.GONE);
        retry.setVisibility(View.VISIBLE);
        Snackbar.make(constraintLayout, getActivity().getResources().getText(R.string.error_server).toString(), LENGTH_LONG).show();
        retry.setText(getString(R.string.retry));
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (!active) {
            retry.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(int position) {
    }

    /**@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof InteractionListener) {
            //init the listener
            mListener = (InteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement InteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
     **/

    @Override
    public void onPause() {
        super.onPause();
        presenter.unSubscribe();
    }

    @OnClick(R.id.card_note_fragment_retry_button)
    public void onClick(){
        setLoadingIndicator(false);
        presenter.fetch();
    }

}
