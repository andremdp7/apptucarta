package com.jvm.tucarta;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class BienvenidaActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_bienvenida);
		
		findViewById(R.id.enter_sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent cartaIntent = new Intent(getApplicationContext(), LoginActivity.class);
						startActivity(cartaIntent);
					}
				});
	
		findViewById(R.id.register_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent cartaIntent = new Intent(getApplicationContext(), RegisterActivity.class);
						startActivity(cartaIntent);
					}
				});
	}


}
