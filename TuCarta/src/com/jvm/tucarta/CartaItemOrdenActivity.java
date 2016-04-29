package com.jvm.tucarta;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.jvm.tucarta.model.ItemCarta;
import com.koushikdutta.ion.Ion;

public class CartaItemOrdenActivity extends SherlockActivity{
	
	int cantidad = 1;
	Double precio_unit;
	Double precio_total;
	TextView nombre;
	TextView descripcion;
	TextView tv_precio_unit;
	TextView tv_precio_total;
	ImageView imagen;
	ItemCarta item;
	
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carta_item_orden);
        
        item = (ItemCarta)getIntent().getSerializableExtra("item");  

        
        //mostrar detalle de item
        nombre = ((TextView) findViewById(R.id.nombre_itemCartaActivity));
        descripcion = ((TextView) findViewById(R.id.descp_itemCartaActivity));
        tv_precio_unit = ((TextView) findViewById(R.id.precio_itemCartaActivity));
        tv_precio_total = ((TextView) findViewById(R.id.precio_totalCartaActivity));
        
        nombre.setText(item.getNombre());
        descripcion.setText(item.getDescripcion());
        precio_unit = item.getPrecio();
        if (item.getCantidad() > 0) precio_total = item.getPrecio() * item.getCantidad();
        else precio_total = item.getPrecio();
        tv_precio_unit.setText("Precio Unidad: S/. " + String.format("%.2f", precio_unit));
        tv_precio_total.setText("Precio Total: S/. " + String.format("%.2f", precio_total));
        
        imagen = (ImageView) findViewById(R.id.imagen_itemCartaActivity);
        Ion.with(imagen).load(item.getImagen_url());
        
        findViewById(R.id.ordenarItem_button).setOnClickListener(
			new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent returnIntent = new Intent();
					returnIntent.putExtra("cantidad",cantidad);
					returnIntent.putExtra("id_item",item.getId_item());
					setResult(Activity.RESULT_OK,returnIntent);
					finish();
				}
		});

        configPicker();
        
    }
	
	@Override
	public void onBackPressed() {
		Intent returnIntent = new Intent();
		returnIntent.putExtra("cantidad",cantidad);
		returnIntent.putExtra("id_item",item.getId_item());
		setResult(Activity.RESULT_CANCELED,returnIntent);
		
		super.onBackPressed();
	}

    public void configPicker()
    {

        NumberPicker np = (NumberPicker) findViewById(R.id.cantidad_picker);

        np.setOnValueChangedListener(new OnValueChangeListener()
        {
            public void onValueChange(NumberPicker picker, int oldVal, 
                int newVal)
            {
            	cantidad = newVal;
            	//actualizar precio total
            	precio_total = precio_unit * cantidad;
                tv_precio_total.setText("Precio Total: S/. " + String.format("%.2f", precio_total));
            	
            }        
        });

        np.setMaxValue(100);
        np.setMinValue(1);
        np.setWrapSelectorWheel(false);
        if (item.getCantidad() == 0) np.setValue(1);
        else np.setValue(item.getCantidad());
        
        
        
    }


}
