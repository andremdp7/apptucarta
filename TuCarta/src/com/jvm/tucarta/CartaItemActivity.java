package com.jvm.tucarta;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.jvm.tucarta.model.ItemCarta;
import com.koushikdutta.ion.Ion;

public class CartaItemActivity extends SherlockActivity{
	
	int cantidad = 1;
	Double precio_unit;

	TextView nombre;
	TextView descripcion;
	TextView tv_precio_unit;

	ImageView imagen;
	ItemCarta item;
	
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carta_item);
        
        item = (ItemCarta)getIntent().getSerializableExtra("item");  

        
        //mostrar detalle de item
        nombre = ((TextView) findViewById(R.id.nombre_itemCarta));
        descripcion = ((TextView) findViewById(R.id.descp_itemCarta));
        tv_precio_unit = ((TextView) findViewById(R.id.precio_itemCarta));

        
        nombre.setText(item.getNombre());
        descripcion.setText(item.getDescripcion());
        precio_unit = item.getPrecio();
        tv_precio_unit.setText("Precio Unidad: S/. " + String.format("%.2f", precio_unit));
        
        imagen = (ImageView) findViewById(R.id.imagen_itemCarta);
        Ion.with(imagen).load(item.getImagen_url());
        
        findViewById(R.id.regresar_cartaItem_btn).setOnClickListener(
			new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					finish();
				}
		});

        
    }


}

