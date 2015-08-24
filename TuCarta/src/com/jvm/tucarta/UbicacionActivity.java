package com.jvm.tucarta;

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
						Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			            startActivityForResult(intent, 0);
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
	      Intent cartaIntent = new Intent(getApplicationContext(), CartaListViewActivity.class);
	      startActivity(cartaIntent);
	      
	   }

}
