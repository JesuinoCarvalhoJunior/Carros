package com.projetolivro.junior_carvalho.carros.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.projetolivro.junior_carvalho.carros.R;
import com.projetolivro.junior_carvalho.carros.adapter.CarroAdapter;
import com.projetolivro.junior_carvalho.carros.adapter.Interface.CarroOnClickListener;
import com.projetolivro.junior_carvalho.carros.domain.Carro;
import com.projetolivro.junior_carvalho.carros.domain.CarroService;

import java.util.List;


public class CarrosFragment extends BaseFragments {


    private int tipo;
    protected RecyclerView recyclerView;
    private List<Carro> carros;

    private CarroOnClickListener carroOnClickListener;

    // metodo para instanciar o fragment da lista de carros com tipo correto
    // obs. newInsance é nomeclaura padrao
    public static CarrosFragment newInstance(int tipo) {

        Bundle args = new Bundle();
        args.putInt("tipo", tipo);

        CarrosFragment f = new CarrosFragment();
        f.setArguments(args);


        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            //lê o tipo dos argumento
            this.tipo = getArguments().getInt("tipo");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_carros, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        /*TextView text = (TextView) view.findViewById(R.id.text);
        text.setText("Carros" + getString(tipo));
*/
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        taskCarros();
    }

    private void taskCarros() {
        // busca os carros pelo tipo
        CarroService cs = new CarroService();
        this.carros = cs.getCarros(getContext(), tipo);
        // atualiza a lista
        recyclerView.setAdapter(new CarroAdapter(getContext(), carros, onClickCarro()));
    }

    private CarroOnClickListener onClickCarro() {
        return new CarroOnClickListener() {
            @Override
            public void onClickCarro(View view, int idx) {
                Carro c = carros.get(idx);
                Toast.makeText(getContext(), "Carro: " + c.nome, Toast.LENGTH_SHORT).show();
            }
        };
    }



}
