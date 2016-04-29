package com.jvm.tucarta;

import java.util.ArrayList;

import com.jvm.tucarta.camera.CameraActivity;
import com.jvm.tucarta.model.ItemCarta;
import com.jvm.tucarta.model.SesionActual;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class UbicacionActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_ubicacion);
		
		findViewById(R.id.imagebtn_restaurant).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						//Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			            //startActivityForResult(intent, 0);
						SesionActual.orden = new ArrayList<ItemCarta>();
						SesionActual.ordenIngresada = false;
						Intent cameraIntent = new Intent(getApplicationContext(), CameraActivity.class);
					    startActivity(cameraIntent);
					}
				});
	
		findViewById(R.id.imagebtn_calle).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent lugaresIntent = new Intent(getApplicationContext(), LugaresActivity.class);
						startActivity(lugaresIntent);
					}
				});
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	      // TODO Auto-generated method stub
	      super.onActivityResult(requestCode, resultCode, data);
	      Intent cartaIntent = new Intent(getApplicationContext(), CartaOrdenActivity.class);
	      startActivity(cartaIntent);
	      
	   }

}
