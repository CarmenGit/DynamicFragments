package es.cice.dynamicfragments;

//import android.app.FragmentManager;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import es.cice.dynamicfragments.fragments.QuoteFragment;
import es.cice.dynamicfragments.fragments.TitlesListFragment;


public class MainActivity extends AppCompatActivity implements TitlesListFragment.TitlesListFragmentHostingActivity{
    private QuoteFragment qFragment;
    private TitlesListFragment titlesFragment;
    private FrameLayout titlesContainer, quotesContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titlesContainer=(FrameLayout) findViewById(R.id.titleContainer);
        quotesContainer=(FrameLayout) findViewById(R.id.quoteContainer);
        qFragment = new QuoteFragment();
        titlesFragment=new TitlesListFragment();
        FragmentManager fm=getSupportFragmentManager();
        //cada vez que se añada una nueva transacción, hay un cambio
        fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {

                setLayout();
            }
        });
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.titleContainer, titlesFragment);
        ft.addToBackStack("composicion inicial");
        ft.commit();

        fm.executePendingTransactions();

    }

    @Override
    public void showTitle(int index) {
        //comprobamos si el frag está añadido o no y volvemos a reajustar los tamaños
        if (!qFragment.isAdded()){
            //lo añadimos, comunicándonos con el fragmenmanager
            FragmentManager fm= getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.quoteContainer,qFragment);
            //lo metemos en la pila
            ft.addToBackStack(null);
            ft.commit();
            fm.executePendingTransactions();

        }
        qFragment.showTitle(index);
    }

    private void setLayout(){
        //reparte el espacio entre los dos layout contenedores
        //tenemos dos referencias preparadas titlesContainer y quotesContainer
        //estamos configurando el layout dinámicamente
        if (qFragment.isAdded()){
            //hay que definir cuánto ocupa uno y otro
            titlesContainer.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f));
            quotesContainer.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 2f));

        }else {
            titlesContainer.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f));
            quotesContainer.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0f));

        }

    }
}
