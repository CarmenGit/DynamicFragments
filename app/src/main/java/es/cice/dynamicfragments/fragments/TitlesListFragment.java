package es.cice.dynamicfragments.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import es.cice.dynamicfragments.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TitlesListFragment extends Fragment {
    private Context ctx;
    private ListView titlesLV;



    public TitlesListFragment() {
        // Required empty public constructor
    }


    /**
     * Called when a fragment is first attached to its context.
     * {@link #onCreate(Bundle)} will be called after this.
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //si el contexto satisface la interfaz
        if (context instanceof TitlesListFragmentHostingActivity)
            ctx=context;
        //sino producir una excepción
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View layout=inflater.inflate(R.layout.fragment_titles_list, container, false);
        titlesLV = (ListView) layout.findViewById(R.id.titlesLV);
        String [] titles=ctx.getResources().getStringArray(R.array.title);
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(ctx,
                android.R.layout.simple_list_item_1,android.R.id.text1, titles);
        titlesLV.setAdapter(adapter);
        titlesLV.setOnItemClickListener(new AdapterView.OnItemClickListener(){


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //llama a la actividad que lo aloja ctx
                ((TitlesListFragmentHostingActivity)ctx).showTitle(position);

            }
        });
        return layout;
    }
    /**
    *esta interfaz define los requisitos de comunicacion de TitlesListFragment con la actividad que lo aloje
    */
    public interface TitlesListFragmentHostingActivity{
        public void showTitle(int index);
    }

}
