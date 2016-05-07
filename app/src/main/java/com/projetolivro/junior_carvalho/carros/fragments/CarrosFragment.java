package com.projetolivro.junior_carvalho.carros.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.projetolivro.junior_carvalho.carros.CarrosApplication;
import com.projetolivro.junior_carvalho.carros.R;
import com.projetolivro.junior_carvalho.carros.activity.CarroActivity;
import com.projetolivro.junior_carvalho.carros.adapter.CarroAdapter;
import com.projetolivro.junior_carvalho.carros.domain.BD.CarroDB;
import com.projetolivro.junior_carvalho.carros.domain.Carro;
import com.projetolivro.junior_carvalho.carros.domain.CarroService;
import com.squareup.otto.Subscribe;

import org.parceler.Parcels;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import livroandroid.lib.utils.AndroidUtils;
import livroandroid.lib.utils.IOUtils;
import livroandroid.lib.utils.SDCardUtils;


public class CarrosFragment extends BaseFragment {

    //Essa classe contem a lista de carros

    protected RecyclerView recyclerView;
    private int tipo;
    private List<Carro> carros;
    private SwipeRefreshLayout swipeLayout;

    private ActionMode actionMode;


    //  private CarroOnClickListener carroOnClickListener;

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
        //Registra a classe para receber os evento/mensagem vindo de CarroFragment
        CarrosApplication.getInstance().getBus().register(this);
    }


    @Subscribe
    public void onBusAtualizaListaCarros(String refresh) {
        // recebeu o evento/mensagem , entao atualiza lista
        // utiliza para quando editar ou remover carros da lista
        taskCarros(false);
    }

    public void onDestroy() {
        super.onDestroy();
        // cancelar recebimento do evento/emnsagem
        CarrosApplication.getInstance().getBus().unregister(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_carros, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        // Swipe to Refresh
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeToRefresh);
        swipeLayout.setOnRefreshListener(OnRefreshListener());
        swipeLayout.setColorSchemeResources(
                R.color.refresh_progress_1,
                R.color.refresh_progress_2,
                R.color.refresh_progress_3);
        return view;
    }

    private SwipeRefreshLayout.OnRefreshListener OnRefreshListener() {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //verifica conexao com intenet
                if (AndroidUtils.isNetworkAvailable(getContext())) {
                    // Atualiza ao fazer o gesto Pull to Refresh
                    taskCarros(true);
                } else {
                    swipeLayout.setRefreshing(false);
                    snack(recyclerView, R.string.error_conexao_indisponivel);
                }

            }
        };
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        taskCarros(false);
    }


    private void taskCarros(boolean pullToRefresh) {
        // Busca os carros: Dispara a Task
        startTask("carros", new GetCarrosTask(pullToRefresh), pullToRefresh ? R.id.swipeToRefresh : R.id.progress);
    }


/*    private void taskCarros() {


        //busca os carros: dispara a task
        startTask("carros", new GetCarrosTask(), R.id.progress);
    }*/

    // Task para buscar os carros
    private class GetCarrosTask implements TaskListener<List<Carro>> {
        private boolean refresh;

        public GetCarrosTask(boolean refresh) {
            this.refresh = refresh;
        }

        @Override
        public List<Carro> execute() throws Exception {
            // ver o progress
            Thread.sleep(500);
            // Busca os carros em background (Thread)
            return CarroService.getCarros(getContext(), tipo, refresh);
        }

        @Override
        public void updateView(List<Carro> carros) {
            if (carros != null) {
                // Salva a lista de carros no atributo da classe
                CarrosFragment.this.carros = carros;
                // Atualiza a view na UI Thread
                recyclerView.setAdapter(new CarroAdapter(getContext(), carros, onClickCarro()));
            }
        }

        @Override
        public void onError(Exception e) {
            // Qualquer exceção lançada no método execute vai cair aqui.
            alert("Ocorreu algum erro ao buscar os dados. " + e.getMessage());
        }

        @Override
        public void onCancelled(String s) {
        }

    }

    private CarroAdapter.CarroOnClickListener onClickCarro() {
        return new CarroAdapter.CarroOnClickListener() {
            @Override
            public void onClickCarro(View view, int idx) {

                Carro c = carros.get(idx);
                if (actionMode == null) {
                    Intent intent = new Intent(getContext(), CarroActivity.class);
                    intent.putExtra("carro", Parcels.wrap(c));
                    startActivity(intent);
                } else { // se a CAB estver ativa
                    // Seleciona o carro
                    c.selected = !c.selected;
                    // Atualiza o título com a quantidade de carros selecionados
                    updateActionModeTitle();
                    // Redesenha a lista
                    recyclerView.getAdapter().notifyDataSetChanged();


                }
            }

            @Override
            public void onLongClickCarro(View view, int idx) {
                if (actionMode != null) {
                    return;
                }
                // liga/inicia a action bar de contexto (CAB)
                actionMode = getAppCompatActivity().
                        startSupportActionMode(getActionModeCallback());

                Carro c = carros.get(idx);
                // Seleciona o carro
                c.selected = true;
                //solicita ao Andoird para desenha a lista novamente
                //necessario para quando adicona, ou remove intes da lista
                //ou deseja trocar a cor do carro seleciona
                recyclerView.getAdapter().notifyDataSetChanged();
                //atualiza o titulo para mpostrar a quantidade de carros selecionados
                updateActionModeTitle();
            }
        };
    }


    //retorno o objeto do tipo ActionMode.Callback a quanl contem os
    //metodos para inflar as opç~coe do menu que CAB vai ter, alem de tratar
    //os eventos de clique nestas acçõe.

    private ActionMode.Callback getActionModeCallback() {
        return new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // infla o menu especifico da action nar de caontexto (CAB)
                MenuInflater inflate = getActivity().getMenuInflater();
                inflate.inflate(R.menu.menu_frag_carro_cab, menu);


                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
                // esse metodo é chamado sempre que o CAB é invalidado
                // Nao sera utilizado no exemplo de carros
            }

            //sempre chamdo ao clicar em algum das opcaos da CAN e funciona
            //da mesma forma que o famoso metodo onOptionsItemSelected(item) que
            //trata os evetos das acos da acition bar
            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

                final List<Carro> selectedCarros = getSelectedCarros();
                if (item.getItemId() == R.id.action_delete) {
                    CarroDB db = new CarroDB(getContext());
                    try {
                        for (Carro c : selectedCarros) {
                            //deleta do Bd ao carro selecionado
                            db.delete(c);
                            //remove da lista
                            carros.remove(c);
                            //                  CarrosApplication.getInstance().getBus().post("refresh");
                        }
                    } finally {
                        db.close();
                    }
                    snack(recyclerView, +selectedCarros.size() + " Carros excluídos com sucesso!");


                } else if (item.getItemId() == R.id.action_share) {
                    // Dispara a tarefa para fazer download das fotos para compartilhar
                    startTask("compartilhar", new CompartilharTask(selectedCarros));

                }
                //encerra o action mode
                mode.finish();
                return true;
            }

            // sempre chamado quando a CAB é encerrada, limpa os recursos
            //para action bar voltar ao seu estao padrao
            @Override
            public void onDestroyActionMode(ActionMode mode) {
                //limpa o estado
                actionMode = null;
                //configurar todos os carro para não selecionar
                for (Carro c : carros) {
                    c.selected = false;
                }
                //solicita ao Andoird para desenha a lista novamente
                //necessario para quando adicona, ou remove intes da lista
                //ou deseja trocar a cor do carro seleciona
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        };

    }


    // atualiza o titulo da action nar (CAB = Contextual Action Bar)
    private void updateActionModeTitle() {

        if (actionMode != null) {
            actionMode.setTitle("Selecione os carros");
            actionMode.setSubtitle(null);

            List<Carro> selectedCarros = getSelectedCarros();
            if (selectedCarros.size() == 1) {
                actionMode.setSubtitle("1 carro selecionado");
            } else if (selectedCarros.size() > 1) {
                actionMode.setSubtitle(selectedCarros.size() + " carros selecionados");
            }

            //retorna titulo da nav bar
            if(selectedCarros.size() == 0){
                recyclerView.getAdapter().notifyDataSetChanged();
                actionMode.finish();
            }
        }



    }


    // retorna a lista de carros sleecionados
    private List<Carro> getSelectedCarros() {
        List<Carro> list = new ArrayList<Carro>();
        for (Carro c : carros) {
            if (c.selected) {
                list.add(c);
            }
        }
        return list;
    }

    // Task para fazer o download
// Faça import da classe android.net.Uri;
    private class CompartilharTask implements TaskListener {
        // Lista de arquivos para compartilhar
        ArrayList<Uri> imageUris = new ArrayList<Uri>();
        private final List<Carro> selectedCarros;

        public CompartilharTask(List<Carro> selectedCarros) {
            this.selectedCarros = selectedCarros;
        }

        @Override
        public Object execute() throws Exception {
            if (selectedCarros != null) {
                for (Carro c : selectedCarros) {
                    // Faz o download da foto do carro para arquivo
                    String url = c.urlFoto;
                    String fileName = url.substring(url.lastIndexOf("/"));
                    // Cria o arquivo no SD card
                    File file = SDCardUtils.getPrivateFile(getContext(), "carros", fileName);
                    IOUtils.downloadToFile(c.urlFoto, file);
                    // Salva a Uri para compartilhar a foto
                    imageUris.add(Uri.fromFile(file));
                }
            }
            return null;
        }

        @Override
        public void updateView(Object response) {
            // Cria a intent com a foto dos carros
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
            shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
            shareIntent.setType("image/*");
            // Cria o Intent Chooser com as opções para compartilhar
            startActivity(Intent.createChooser(shareIntent, "Enviar Carros"));
        }

        @Override
        public void onError(Exception exception) {
            alert("Erro ao compartilhar.");
        }

        @Override
        public void onCancelled(String cod) {
            // nada a implementar
        }
    }




    /*    private CarroAdapter.CarroOnClickListener onClickCarro() {
        return new CarroAdapter.CarroOnClickListener() {
            @Override
            public void onClickCarro(View view, int idx) {
                Carro c = carros.get(idx);

                Intent intent = new Intent(getContext(), CarroActivity.class);
                //intent.putExtra("carro", c);
                // usando o Parceler o objeto é criado em tempo de execucaçõ

                intent.putExtra("carro", Parcels.wrap(c)); // converte o objeto em Parcelable
                startActivity(intent);
                  Toast.makeText(getContext(), "Carro: " + c.nome, Toast.LENGTH_SHORT).show();
            }
        };
    }*/

/*    private CarroAdapter.CarroOnClickListener onClickCarro() {
        return new CarroAdapter.CarroOnClickListener() {
            @Override
            public void onClickCarro(View view, int idx) {
                Carro c = carros.get(idx);
                //Toast.makeText(getContext(), "Carro: " + c.nome, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), CarroActivity.class);
                intent.putExtra("carro", Parcels.wrap(c));
                startActivity(intent);
            }
        };
    }*/


}
