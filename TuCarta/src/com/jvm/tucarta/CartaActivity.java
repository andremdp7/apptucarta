package com.jvm.tucarta;

import java.util.ArrayList;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.jvm.tucarta.adapter.ItemCartaAdapter;
import com.jvm.tucarta.model.ItemCarta;
import com.jvm.tucarta.model.Lugar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class CartaActivity extends SherlockActivity implements SearchView.OnQueryTextListener{
	ArrayList<ItemCarta> todos_itemCarta;
	ListView itemCartaList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_carta);
		setTheme(R.style.Theme_Sherlock_Light_DarkActionBar);
		
		mostraritemCarta();
	
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
    
    /*Metodo para mostrar los itemCarta para comer*/
    public void mostraritemCarta() {
    	
    	todos_itemCarta = cargaritemCartaHardcode();
    	
		itemCartaList = (ListView) findViewById(R.id.list_carta_a);
		itemCartaList.setAdapter(new ItemCartaAdapter(todos_itemCarta, this));
		itemCartaList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//mostrar detalle del item		
				
			}
		});
	}
	
    
    private ArrayList<ItemCarta> cargaritemCartaHardcode(){
    	ArrayList<ItemCarta> listaitemCarta = new ArrayList<ItemCarta>();
    	
    	ItemCarta ic1 = new ItemCarta(1, "AJÍ DE GALLINA",
				"Hecho según una receta antigua, con queso serrano, ají amarillo, mirasol, y acompañado de papa amarilla. El plato más querido de los limeños.", 
				460, 15);
    	
    	ItemCarta ic2 = new ItemCarta(2, "POLLITO NIKKEI",
				"Jugoso filete de pierna, glaseado en salsa nikkei y servido sobre un contundente arroz chaufa blanco de verduritas.", 
				860, 20);
		
		ItemCarta ic3 = new ItemCarta(3, "ANTICUCHÓN",
				"De pollo en salsa anticuchera; acompañado de papas doradas, choclo a la mantequilla y salsas de rocoto y huancaína.", 
				1260, 23);
		
		ItemCarta ic4 = new ItemCarta(4, "MI SUPREMA MARYLAND",
				"Crujiente suprema de pollo acompañada de papas amarillas fritas, plátano asado con queso, huevo de corral y choclo a la crema. Otro de nuestros favoritos.", 
				1660, 18);
		
		ItemCarta ic5 = new ItemCarta(5, "TAPADITO DE CHANCHITO CAPÓN", 
				"Chaufa aeropuerto cubierto de chanchito asado y milanesa de cerdo al panko, ensalada de col, huevo frito y salsa chasiu acaramelada.", 
				460, 16);
		
		ItemCarta ic6 = new ItemCarta(6, "EL GRAN COMBINADO", 
				"Sabroso arroz con pollo acompañado de salsa criolla y plátano frito. Además, choclito con ocopa a un lado y papita a la huancaína al otro. Todo un señor plato.", 
				860, 20);
		
		ItemCarta ic7 = new ItemCarta(7, "CHIJAU KAY MARINERO", 
				"Pesca del día rellena de langostinos, bañada en salsa de ajonjolí y acompañada de chaufa aeropuerto.", 
				1260, 22);
		
		ItemCarta ic8 = new ItemCarta(8, "EL POLLO Y EL RISOTTO", 
				"Pollito saltado a la criolla con champiñones sobre risotto de zapallo y ají.", 
				1660, 18);
    	
    	listaitemCarta.add(ic1);
    	listaitemCarta.add(ic2);
    	listaitemCarta.add(ic3);
    	listaitemCarta.add(ic4);
    	listaitemCarta.add(ic5);
    	listaitemCarta.add(ic6);
    	listaitemCarta.add(ic7);
    	listaitemCarta.add(ic8);
    			
    	return listaitemCarta;
    }

	@Override
	public boolean onQueryTextSubmit(String query) {
		
		filtraritemCarta(query);
		
		return true;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		
		if (newText.isEmpty()){
			itemCartaList.setAdapter(new ItemCartaAdapter(todos_itemCarta, this));
		} else{
			filtraritemCarta(newText);
		}
		
		return true;
	}
	
	private void filtraritemCarta(String query){
		ArrayList<ItemCarta> lista_aux = new ArrayList<ItemCarta>();
		for(int i=0; i<todos_itemCarta.size(); i++){
			if (todos_itemCarta.get(i).getNombre().toUpperCase().contains(query.toUpperCase())){
				lista_aux.add(todos_itemCarta.get(i));
			}
			
		}
		itemCartaList.setAdapter(new ItemCartaAdapter(lista_aux, this));
	}
	

}
