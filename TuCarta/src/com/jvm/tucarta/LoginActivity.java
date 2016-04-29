package com.jvm.tucarta;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.google.gson.Gson;
import com.jvm.tucarta.connection.ConnectionManager;
import com.jvm.tucarta.forms.LoginRequest;
import com.jvm.tucarta.model.SesionActual;
import com.jvm.tucarta.model.Usuario;
import com.jvm.tucarta.services.AsyncCall;
import com.jvm.tucarta.services.ConstanteServicio;
import com.jvm.tucarta.services.Servicio;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity {
	/**
	 * A dummy authentication store containing known user names and passwords.
	 * TODO: remove after connecting to a real authentication system.
	 */
	private static final String[] DUMMY_CREDENTIALS = new String[] {
			"foo@example.com:hello", "bar@example.com:world" };

	/**
	 * The default email to populate the email field with.
	 */
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserLoginTask mAuthTask = null;

	// Values for email and password at the time of the login attempt.
	private String mUser;
	private String mPassword;

	// UI references.
	private EditText mUserView;
	private EditText mPasswordView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);

		// Set up the login form.
		mUserView = (EditText) findViewById(R.id.user);
		mUserView.setText(mUser);

		mPasswordView = (EditText) findViewById(R.id.password);
		/*
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});
				*/

		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
					    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
						attemptLogin();
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mUserView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mUser = mUserView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 4) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid user.
		if (TextUtils.isEmpty(mUser)) {
			mUserView.setError(getString(R.string.error_field_required));
			focusView = mUserView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			
			invocarServicioLogin();
			//mAuthTask = new UserLoginTask();
			//mAuthTask.execute((Void) null);
		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}
	
	private void invocarServicioLogin(){
		
		if (ConnectionManager.connect(this)) {
			// construir llamada al servicio
			String request = Servicio.Login;
			
			LoginRequest loginRequest = new LoginRequest();
			loginRequest.setUsuario(mUser);
			loginRequest.setPassword(mPassword);
			Gson gson = new Gson();
			
			new LoginUsuario(this).execute(request,gson.toJson(loginRequest));
			
		} else {
			showProgress(false);
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
	
	public class LoginUsuario extends AsyncCall {

		public LoginUsuario(Activity activity) {
			super(activity,false);
		}

		@Override
		protected void onPostExecute(String result) {

			System.out.println("Recibido: " + result.toString());
			try {
				JSONObject jsonObject = new JSONObject(result);
				String respuesta = jsonObject.getString("response_code");
				if ("00".equals(respuesta)) {
					/*setear usuario actual*/
					SesionActual.usuario = new Usuario();
					SesionActual.usuario.setId_usuario(jsonObject.getInt("id_usuario"));
					SesionActual.usuario.setNombre(mUser);
					SesionActual.usuario.setFecha_registro(jsonObject.getString("fecha_registro"));
					
					showProgress(false);
					
					Intent ubicacionIntent = new Intent(getApplicationContext(), UbicacionActivity.class);
					startActivity(ubicacionIntent);
				} else {
					showProgress(false);
					ocultarMensajeProgreso();
					String msjError = jsonObject.getString("mensaje");
					mostrarError(msjError,false);
				}
			} catch (JSONException e) {
				ocultarMensajeProgreso();
				mostrarError(e.toString(),true);
			} catch (NullPointerException ex) {
				ocultarMensajeProgreso();
				mostrarError(ex.toString(),true);
			}
		}
	}
	
	private void mostrarError(String mensaje, boolean excepcion) {
		showProgress(false);
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

	

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.

			
			try {
				// Simulate network access.
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				return false;
			}
			return true;
			
			
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			showProgress(false);

			if (success) {
				Intent ubicacionIntent = new Intent(getApplicationContext(), UbicacionActivity.class);
				startActivity(ubicacionIntent);
			} else {
				
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}
}
