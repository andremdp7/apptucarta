package com.jvm.tucarta.services;

import com.jvm.tucarta.connection.ConnectionManager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

public abstract class AsyncCall extends AsyncTask<String, Integer, String> {

	private ProgressDialog progressDialog = null;

	public AsyncCall(Activity activity, boolean dialogo) {
		super();
		if (dialogo){
			progressDialog = new ProgressDialog(activity);
		}
	}


	@Override
	protected void onPreExecute() {
		if (progressDialog != null) {
			progressDialog.setMessage("Cargando...");
			progressDialog.show();
		}
		
		
		
	}

	@Override
	protected String doInBackground(String... params) {
		System.out.println("Servicio: " + params[0]);
		System.out.println("Enviado: " + params[1]);
		
		return ConnectionManager.downloadUrl(params[0],params[1]);
	}

	public void ocultarMensajeProgreso() {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
	}

}