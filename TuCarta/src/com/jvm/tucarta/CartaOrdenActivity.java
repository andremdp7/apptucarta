package com.jvm.tucarta;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.google.gson.Gson;
import com.jvm.tucarta.adapter.ItemCartaAdapter;
import com.jvm.tucarta.connection.ConnectionManager;
import com.jvm.tucarta.forms.LugarCartaResponse;
import com.jvm.tucarta.model.ItemCarta;
import com.jvm.tucarta.model.SesionActual;
import com.jvm.tucarta.services.AsyncCall;
import com.jvm.tucarta.services.ConstanteServicio;
import com.jvm.tucarta.services.Servicio;
import com.koushikdutta.ion.Ion;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;

public class CartaOrdenActivity extends SherlockActivity implements SearchView.OnQueryTextListener{
	ArrayList<ItemCarta> todos_itemCarta;
	ListView itemCartaList;
	String colorNombre = "#000000";
	String colorDescripcion = "#000000";
	float total = 0;
	
	ImageView logo;
	LinearLayout layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_carta_orden);
		setTheme(R.style.Theme_Sherlock_Light_DarkActionBar);
		layout = (LinearLayout) findViewById(R.id.layout_carta_b);
		logo = (ImageView) findViewById(R.id.logo_carta_b);
		
		invocarServicioCartaOrden();
		
		findViewById(R.id.ver_orden_btn).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent ordenIntent = new Intent(getApplicationContext(), OrdenActivity.class);
						startActivity(ordenIntent);
					}
				});
	
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		calcularTotalOrden();
	}
	
	public void calcularTotalOrden(){
		
		float total = 0;
		
		for (int i=0; i<SesionActual.orden.size();i++){
			total += SesionActual.orden.get(i).getPrecio() * SesionActual.orden.get(i).getCantidad();
	    }
		
		((TextView) findViewById(R.id.total_carta_activity)).setText("S/. " + String.format("%.2f", total));
		
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
    
    
    
    /*Metodo para obtener items de la carta*/
    public void invocarServicioCartaOrden() {
    	
    	if (ConnectionManager.connect(this)) {
			// construir llamada al servicio
			String request = Servicio.CartaOrden;
			
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("id_usuario", SesionActual.usuario.getId_usuario());
				jsonObject.put("id_mesa", "ABC");
			} catch (JSONException e) {
				
			}
			new CartaOrden(this).execute(request,jsonObject.toString());
			
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
    
    public class CartaOrden extends AsyncCall {

		public CartaOrden(Activity activity) {
			super(activity,true);
		}

		@Override
		protected void onPostExecute(String result) {

			System.out.println("Recibido: " + result.toString());
			try {
				Gson gson = new Gson();				
				LugarCartaResponse cartaResponse = gson.fromJson(result, LugarCartaResponse.class);
	            
				if ("00".equals(cartaResponse.getResponse_code())) {
					
					todos_itemCarta = (ArrayList<ItemCarta>) cartaResponse.getItems();
					layout.setBackgroundColor(Color.parseColor(cartaResponse.getColor_fondo()));
					Ion.with(logo).load(cartaResponse.getLogo_url());
					colorNombre = cartaResponse.getColor_nombres();
					colorDescripcion = cartaResponse.getColor_descripcion();
					
					ocultarMensajeProgreso();
					mostraritemCarta(colorNombre, colorDescripcion);
				} else {
					ocultarMensajeProgreso();
					String msjError = cartaResponse.getMensaje();
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
    
    /*Metodo para mostrar los itemCarta para comer*/
    public void mostraritemCarta(String colorNombre, String colorDescripcion) {
    	
    	if (todos_itemCarta != null){
			itemCartaList = (ListView) findViewById(R.id.list_carta_b);
			itemCartaList.setAdapter(new ItemCartaAdapter(todos_itemCarta, this, colorNombre, colorDescripcion));
			itemCartaList.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					//mostrar detalle del item
					ItemCarta item = todos_itemCarta.get(position);
					if (item.getCantidad() == null) item.setCantidad(0);
					
					Intent intent = new Intent(getApplicationContext(), CartaItemOrdenActivity.class);
					Bundle mBundle = new Bundle();  
			        mBundle.putSerializable("item",item);  
			        intent.putExtras(mBundle);
		            startActivityForResult(intent, 0);
					
				}
			});
    	}
	}
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      // TODO Auto-generated method stub
      super.onActivityResult(requestCode, resultCode, data);
      
      if (resultCode == Activity.RESULT_OK){
	      int cantidad = data.getIntExtra("cantidad",0);
	      int id_item = data.getIntExtra("id_item",0);
	      
	      boolean encontrado = false;
	      for (int i=0; i<SesionActual.orden.size();i++){
	    	  //item encontrado, se actualiza la cantidad
	    	  if (SesionActual.orden.get(i).getId_item() == id_item){
	    		  SesionActual.orden.get(i).setCantidad(cantidad);
	    		  encontrado = true;
	    		  break;
	    	  }
	      }
	      
	      if (!encontrado){
	    	  for (int i=0; i<todos_itemCarta.size();i++){
	    		  if (todos_itemCarta.get(i).getId_item() == id_item){
	    			  //agregar item a la orden
	    			  SesionActual.orden.add(todos_itemCarta.get(i));
	    			  SesionActual.orden.get(SesionActual.orden.size() - 1).setCantidad(cantidad);
	    			  break;
	    			  
	    		  }
	    	  }
	    	  
	      }
	      
      }
      
   }
    
    

	@Override
	public boolean onQueryTextSubmit(String query) {
		
		filtraritemCarta(query);
		
		return true;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		
		if (newText.isEmpty()){
			itemCartaList.setAdapter(new ItemCartaAdapter(todos_itemCarta, this, colorNombre, colorDescripcion));
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
		itemCartaList.setAdapter(new ItemCartaAdapter(lista_aux, this, colorNombre, colorDescripcion));
	}
	

}
