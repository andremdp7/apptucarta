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

public class CartaListViewActivity extends SherlockActivity implements SearchView.OnQueryTextListener{
	ArrayList<ItemCarta> todos_itemCarta;
	ListView itemCartaList;
	float total = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_carta_lista);
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
    	
    	com.actionbarsherlock.view.MenuItem itemCerrarSesion = opciones.add("Cerrar sesi�n");
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
    	
		itemCartaList = (ListView) findViewById(R.id.list_carta);
		itemCartaList.setAdapter(new ItemCartaAdapter(todos_itemCarta, this));
		itemCartaList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//mostrar detalle de plato
				total+= todos_itemCarta.get(position).getPrecio();
				((TextView) findViewById(R.id.total_pedido)).setText("S/." + String.format("%.2f", total));
				
			}
		});
	}
	
    
    private ArrayList<ItemCarta> cargaritemCartaHardcode(){
    	ArrayList<ItemCarta> listaitemCarta = new ArrayList<ItemCarta>();
    	
    	ItemCarta ic1 = new ItemCarta(1, "AJ� DE GALLINA",
				"Hecho seg�n una receta antigua, con queso serrano, aj� amarillo, mirasol, y acompa�ado de papa amarilla. El plato m�s querido de los lime�os.", 
				460, 15);
    	
    	ItemCarta ic2 = new ItemCarta(2, "POLLITO NIKKEI",
				"Jugoso filete de pierna, glaseado en salsa nikkei y servido sobre un contundente arroz chaufa blanco de verduritas.", 
				860, 20);
		
		ItemCarta ic3 = new ItemCarta(3, "ANTICUCH�N",
				"De pollo en salsa anticuchera; acompa�ado de papas doradas, choclo a la mantequilla y salsas de rocoto y huanca�na.", 
				1260, 23);
		
		ItemCarta ic4 = new ItemCarta(4, "MI SUPREMA MARYLAND",
				"Crujiente suprema de pollo acompa�ada de papas amarillas fritas, pl�tano asado con queso, huevo de corral y choclo a la crema. Otro de nuestros favoritos.", 
				1660, 18);
		
		ItemCarta ic5 = new ItemCarta(5, "TAPADITO DE CHANCHITO CAP�N", 
				"Chaufa aeropuerto cubierto de chanchito asado y milanesa de cerdo al panko, ensalada de col, huevo frito y salsa chasiu acaramelada.", 
				460, 16);
		
		ItemCarta ic6 = new ItemCarta(6, "EL GRAN COMBINADO", 
				"Sabroso arroz con pollo acompa�ado de salsa criolla y pl�tano frito. Adem�s, choclito con ocopa a un lado y papita a la huanca�na al otro. Todo un se�or plato.", 
				860, 20);
		
		ItemCarta ic7 = new ItemCarta(7, "CHIJAU KAY MARINERO", 
				"Pesca del d�a rellena de langostinos, ba�ada en salsa de ajonjol� y acompa�ada de chaufa aeropuerto.", 
				1260, 22);
		
		ItemCarta ic8 = new ItemCarta(8, "EL POLLO Y EL RISOTTO", 
				"Pollito saltado a la criolla con champi�ones sobre risotto de zapallo y aj�.", 
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
