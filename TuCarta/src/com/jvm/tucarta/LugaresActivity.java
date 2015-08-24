package com.jvm.tucarta;

import java.util.ArrayList;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.jvm.tucarta.adapter.LugaresListAdapter;
import com.jvm.tucarta.model.Lugar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class LugaresActivity extends SherlockActivity implements SearchView.OnQueryTextListener {
	
	ArrayList<Lugar> todos_lugares;
	ListView lugaresList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_lugares);
		setTheme(R.style.Theme_Sherlock_Light_DarkActionBar);
		
		mostrarLugares();
	
	}
	
	/*Metodo para generar la action bar customizada*/
    @Override
    public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
    	
    	SearchView searchView = new SearchView(getSupportActionBar().getThemedContext());
    	searchView.setQueryHint("Buscar...");
        searchView.setOnQueryTextListener(this);
        
    	menu.add("Buscar")
	        .setIcon(R.drawable.abs__ic_search)
	        .setActionView(searchView)
	        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

    	com.actionbarsherlock.view.SubMenu opciones = menu.addSubMenu("Opciones");
    	opciones.getItem().setIcon(R.drawable.abs__ic_menu_moreoverflow_holo_dark)
    		.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
    	
    	com.actionbarsherlock.view.MenuItem itemCerrarSesion = opciones.add("Cerrar sesión");
    	itemCerrarSesion.setOnMenuItemClickListener(new com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent salirIntent = new Intent(getApplicationContext(), BienvenidaActivity.class);
				startActivity(salirIntent);
				return false;
			}
		});
    	
    	return super.onCreateOptionsMenu(menu);
    }
    
    /*Metodo para mostrar los lugares para comer*/
    public void mostrarLugares() {
    	
    	todos_lugares = cargarLugaresHardcode();
    	
		lugaresList = (ListView) findViewById(R.id.lista_lugares);
		lugaresList.setAdapter(new LugaresListAdapter(todos_lugares, this));
		lugaresList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//abrir actividad de carta aqui
				if (position==3){
					Intent cartaIntent = new Intent(getApplicationContext(), CartaActivity.class);
					startActivity(cartaIntent);
				}
			}
		});
	}
	
    
    private ArrayList<Lugar> cargarLugaresHardcode(){
    	ArrayList<Lugar> listaLugares = new ArrayList<Lugar>();
    	Lugar lg1 = new Lugar(1,"Starbucks","Lugar para tomar cafe",R.drawable.starbucks);
    	Lugar lg2 = new Lugar(2,"Chilli's","Lugar para comer",R.drawable.chilis);
    	Lugar lg3 = new Lugar(3,"TGI Fridays","Lugar para comer y beber. Tiene televisores para partidos de fútbol",R.drawable.tgi_fridays);
    	Lugar lg4 = new Lugar(4,"Tanta","Tanta es tu sala, comedor o terraza. Celebramos en familia la cocina y su diversidad",R.drawable.tanta);
    	
    	listaLugares.add(lg1);
    	listaLugares.add(lg2);
    	listaLugares.add(lg3);
    	listaLugares.add(lg4);
    			
    	return listaLugares;
    }

	@Override
	public boolean onQueryTextSubmit(String query) {
		
		filtrarLugares(query);
		
		return true;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		
		if (newText.isEmpty()){
			lugaresList.setAdapter(new LugaresListAdapter(todos_lugares, this));
		} else{
			filtrarLugares(newText);
		}
		
		return true;
	}
	
	private void filtrarLugares(String query){
		ArrayList<Lugar> lista_aux = new ArrayList<Lugar>();
		for(int i=0; i<todos_lugares.size(); i++){
			if (todos_lugares.get(i).getNombre().toUpperCase().contains(query.toUpperCase())){
				lista_aux.add(todos_lugares.get(i));
			}
			
		}
		lugaresList.setAdapter(new LugaresListAdapter(lista_aux, this));
	}
	

}
