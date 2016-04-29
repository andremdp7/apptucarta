package com.jvm.tucarta;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.google.gson.Gson;
import com.jvm.tucarta.LoginActivity.LoginUsuario;
import com.jvm.tucarta.adapter.LugaresListAdapter;
import com.jvm.tucarta.connection.ConnectionManager;
import com.jvm.tucarta.forms.LoginRequest;
import com.jvm.tucarta.forms.LugarListaResponse;
import com.jvm.tucarta.model.Lugar;
import com.jvm.tucarta.model.SesionActual;
import com.jvm.tucarta.model.Usuario;
import com.jvm.tucarta.services.AsyncCall;
import com.jvm.tucarta.services.ConstanteServicio;
import com.jvm.tucarta.services.Servicio;

import android.app.Activity;
import android.app.AlertDialog;
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
		
		invocarServicioListarLugares();
	
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
    
    /*Metodo para buscar lugares para comer*/
    public void invocarServicioListarLugares() {
    	
    	if (ConnectionManager.connect(this)) {
			// construir llamada al servicio
			String request = Servicio.ListaLugares;
			
			new ListaLugares(this).execute(request,"");
			
		} else {
			// Se muestra mensaje de error de conexion con el servicio
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Error");
			builder.setMessage(ConstanteServicio.MENSAJE_PROBLEMA_CONEXION);
			builder.setCancelable(false);
			builder.setPositiveButton("Ok", null);
			builder.create();
			builder.show();	
		}
    
    }
    
    public class ListaLugares extends AsyncCall {

		public ListaLugares(Activity activity) {
			super(activity,true);
		}

		@Override
		protected void onPostExecute(String result) {

			System.out.println("Recibido: " + result.toString());
			try {
				Gson gson = new Gson();				
				LugarListaResponse listaResponse = gson.fromJson(result, LugarListaResponse.class);
	            
				if ("00".equals(listaResponse.getResponse_code())) {
					
					todos_lugares = (ArrayList<Lugar>) listaResponse.getLugares();
					ocultarMensajeProgreso();
					mostrarLugares();
				} else {
					ocultarMensajeProgreso();
					String msjError = listaResponse.getMensaje();
					mostrarError(msjError,false);
				}
			} catch (Exception e) {
				ocultarMensajeProgreso();
				mostrarError(e.toString(),true);
			}
		}
	}
	
	private void mostrarError(String mensaje, boolean excepcion) {

		if (excepcion )System.out.println(mensaje);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Error");
		if (excepcion) builder.setMessage(ConstanteServicio.ERROR_RECIBIR_MENSAJE);
		else builder.setMessage(mensaje);
		builder.setCancelable(false);
		builder.setPositiveButton("Aceptar", null);
		builder.create();
		builder.show();
	}
    
    /*Metodo para mostrar los lugares para comer*/
    private void mostrarLugares() {
    	
    	if (todos_lugares != null){
    	
			lugaresList = (ListView) findViewById(R.id.lista_lugares);
			lugaresList.setAdapter(new LugaresListAdapter(todos_lugares, this));
			lugaresList.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					//abrir actividad de carta aqui
					int id_lugar = todos_lugares.get(position).getId();
					
					Intent cartaIntent = new Intent(getApplicationContext(), CartaActivity.class);
					cartaIntent.putExtra("id_lugar",id_lugar);
					startActivity(cartaIntent);
					
				}
			});
    	}
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
