package com.jvm.tucarta;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockActivity;
import com.google.gson.Gson;
import com.jvm.tucarta.LugaresActivity.ListaLugares;
import com.jvm.tucarta.adapter.ItemOrdenAdapter;
import com.jvm.tucarta.connection.ConnectionManager;
import com.jvm.tucarta.forms.LugarListaResponse;
import com.jvm.tucarta.model.ItemCarta;
import com.jvm.tucarta.model.Lugar;
import com.jvm.tucarta.model.SesionActual;
import com.jvm.tucarta.services.AsyncCall;
import com.jvm.tucarta.services.ConstanteServicio;
import com.jvm.tucarta.services.Servicio;

public class OrdenActivity extends SherlockActivity{
	
	private Activity mActivity;
	
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orden);
        mActivity = this;
        		
        mostrarItemOrden();
        
        if (SesionActual.ordenIngresada){
			((Button)findViewById(R.id.ingresar_orden_btn)).setText("MODIFICAR ORDEN");
			
		}
        
        findViewById(R.id.ingresar_orden_btn).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						invocarServicioListarLugares();
					}
				});      
        
    }
	
	/*Metodo para mostrar los items de la orden*/
    public void mostrarItemOrden() {
    	
    	if (SesionActual.orden != null){
    		ListView itemOrdenList = (ListView) findViewById(R.id.list_orden);
    		itemOrdenList.setAdapter(new ItemOrdenAdapter(SesionActual.orden, this, "#000000"));
    		
    		itemOrdenList.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					
					//mostrar detalle del item
					ItemCarta item = SesionActual.orden.get(position);
					Intent intent = new Intent(getApplicationContext(), CartaItemOrdenActivity.class);
					Bundle mBundle = new Bundle();  
			        mBundle.putSerializable("item",item);  
			        intent.putExtras(mBundle);
		            startActivityForResult(intent, 0);
					
					
				}
			});
    		
    		//calcular total orden
    		float total = 0;
    		
    		for (int i=0; i<SesionActual.orden.size();i++){
    			total += SesionActual.orden.get(i).getPrecio() * SesionActual.orden.get(i).getCantidad();
    	    }
    		
    		((TextView) findViewById(R.id.total_ordenActivity)).setText("S/. " + String.format("%.2f", total));
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
  	      
        }
        
        mostrarItemOrden();
        
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
			
			if (SesionActual.ordenIngresada){
				Toast.makeText(mActivity, "Orden modificada",
		 				   Toast.LENGTH_LONG).show();
				
			} else{
				SesionActual.ordenIngresada = true;
				Toast.makeText(mActivity, "Orden ingresada",
	 				   Toast.LENGTH_LONG).show();
			}
			
			finish();
			
		}
	}

}
