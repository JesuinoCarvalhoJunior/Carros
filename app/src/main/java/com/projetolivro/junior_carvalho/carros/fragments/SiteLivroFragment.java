package com.projetolivro.junior_carvalho.carros.fragments;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.projetolivro.junior_carvalho.carros.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SiteLivroFragment extends BaseFragment {

    private static final String URL_SOBRE = "http://www.livroiphone.com.br"; //"http://www.livroandroid.com.br/sobre.html";
    private WebView webview;
    private ProgressBar progressBar;

    protected SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_site_livro, container, false);

        webview = (WebView) view.findViewById(R.id.webview);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);

        setWebViewClient(webview);
        webview.loadUrl(URL_SOBRE);

        // swipe to refresh
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeToRefresh);
        swipeRefreshLayout.setOnRefreshListener(OnRefreshListener());
        // cores da animacao
        swipeRefreshLayout.setColorSchemeResources(
                R.color.refresh_progress_1,
                R.color.refresh_progress_2,
                R.color.refresh_progress_3
        );


        return view;
    }



    private SwipeRefreshLayout.OnRefreshListener OnRefreshListener() {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // atualiza pagina
                webview.reload();


            }

        };
    }

    private void setWebViewClient(WebView webview) {
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView webview, String url, Bitmap favicon) {
                super.onPageStarted(webview, url, favicon);
                // liga o progressbar
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView webview, String url) {
                // delisga progressabar
                progressBar.setVisibility(View.INVISIBLE);
                // termina animacao do swipe o refresh
                swipeRefreshLayout.setRefreshing(false);
            }

            // interceptando requisicao non WEbview
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("Livro", "Webview url: " + url);
                if (url != null && url.endsWith("autor.html")) {
                    AboutDialog.showAbout(getFragmentManager());
                    //retorna true para informar que interceptou o evento
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }
}
