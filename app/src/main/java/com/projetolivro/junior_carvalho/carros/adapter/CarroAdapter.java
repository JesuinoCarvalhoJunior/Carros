package com.projetolivro.junior_carvalho.carros.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.projetolivro.junior_carvalho.carros.R;
import com.projetolivro.junior_carvalho.carros.domain.Carro;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Junior_Carvalho on 27/03/2016.
 */
public class CarroAdapter extends RecyclerView.Adapter<CarroAdapter.CarrosViewHolder> {

    protected static final String TAG = "livroandroid";
    private final List<Carro> carros;
    private final Context context;
    private CarroOnClickListener carroOnClickListener;

    public CarroAdapter(Context context, List<Carro> carros, CarroOnClickListener carroOnClickListener) {
        this.context = context;
        this.carros = carros;
        this.carroOnClickListener = carroOnClickListener;
    }

    @Override
    public int getItemCount() {
        return this.carros != null ? this.carros.size() : 0;
    }

    @Override
    public CarrosViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_carro, viewGroup, false);
        CarrosViewHolder holder = new CarrosViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final CarrosViewHolder holder, final int position) {
        // atualia a view
        Carro c = carros.get(position);
        holder.tNome.setText(c.nome);
        holder.progress.setVisibility(View.VISIBLE);

        // faz download da foto e mostra o progressbar
        Picasso.with(context).load(c.urlFoto).fit().into(holder.img, new Callback() {

            // metodos da interface Callback
            @Override
            public void onSuccess() {
                // download OK
                holder.progress.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                holder.progress.setVisibility(View.GONE);
            }
        });
        // click
        if (carroOnClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // a variavel position é final
                    carroOnClickListener.onClickCarro(holder.itemView, position);
                    // carroOnClickListener.onClickCarro(holder.itemView, position);
                }
            });
        }
    }
/*

    // implementa interface
    public void CarroOnClickListener(View view, int idx) {

    }
*/


    // remover a interface CarroOnClickListener  e usar este metodo
    public interface CarroOnClickListener {
        public void onClickCarro(View view, int idx);
    }

    // viewholder com as view
    public static class CarrosViewHolder extends RecyclerView.ViewHolder {
        public TextView tNome;
        ImageView img;
        ProgressBar progress;
        CardView cardView;

        public CarrosViewHolder(View view) {
            super(view);
            // cria as views para salvar no ViweHolder
            tNome = (TextView) view.findViewById(R.id.text);
            img = (ImageView) view.findViewById(R.id.img);
            progress = (ProgressBar) view.findViewById(R.id.progressImg);
            cardView = (CardView) view.findViewById(R.id.card_view);
        }
    }

}
