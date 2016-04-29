package com.jvm.tucarta;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.jvm.tucarta.LoginActivity.LoginUsuario;
import com.jvm.tucarta.connection.ConnectionManager;
import com.jvm.tucarta.forms.LoginRequest;
import com.jvm.tucarta.forms.UsuarioRegistrarRequest;
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


public class RegisterActivity extends Activity{

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserRegisterTask mAuthTask = null;

	// Values for email and password at the time of the login attempt.
	private String mUser;
	private String mEmail;
	private String mPassword;

	// UI references.
	private EditText mUserView;
	private EditText mEmailView;
	private EditText mPasswordView;
	private View mRegisterFormView;
	private View mRegisterStatusView;
	private TextView mRegisterStatusMessageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_register);

		// Set up the login form.
		mUserView = (EditText) findViewById(R.id.register_user);
		mUserView.setText(mUser);
		
		mEmailView = (EditText) findViewById(R.id.register_email);
		mEmailView.setText(mEmail);

		mPasswordView = (EditText) findViewById(R.id.register_password);
		/*mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.register || id == EditorInfo.IME_NULL) {
							attemptRegister();
							return true;
						}
						return false;
					}
				});
				*/

		mRegisterFormView = findViewById(R.id.register_form);
		mRegisterStatusView = findViewById(R.id.register_status);
		mRegisterStatusMessageView = (TextView) findViewById(R.id.register_status_message);

		findViewById(R.id.new_user_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
					    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
						attemptRegister();
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
	public void attemptRegister() {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mUserView.setError(null);
		mPasswordView.setError(null);
		mEmailView.setError(null);

		// Store values at the time of the login attempt.
		mUser = mUserView.getText().toString();
		mPassword = mPasswordView.getText().toString();
		mEmail = mEmailView.getText().toString();

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
		
		// Check for a valid user
		if (TextUtils.isEmpty(mUser)) {
			mUserView.setError(getString(R.string.error_field_required));
			focusView = mUserView;
			cancel = true;
		} else if  (mUser.length() < 5) {
			mUserView.setError(getString(R.string.error_invalid_username));
			focusView = mUserView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} else if (!mEmail.contains("@")) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mRegisterStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			
			invocarServicioRegistrar();
			//mAuthTask = new UserRegisterTask();
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

			mRegisterStatusView.setVisibility(View.VISIBLE);
			mRegisterStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mRegisterStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mRegisterFormView.setVisibility(View.VISIBLE);
			mRegisterFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mRegisterFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mRegisterStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}
	
	private void invocarServicioRegistrar(){
		if (ConnectionManager.connect(this)) {
			// construir llamada al servicio
			String request = Servicio.Registro;
			
			UsuarioRegistrarRequest registroRequest = new UsuarioRegistrarRequest();
			registroRequest.setUsuario(mUser);
			registroRequest.setPassword(mPassword);
			registroRequest.setCorreo(mEmail);
			Gson gson = new Gson();
			
			new RegistrarUsuario(this).execute(request,gson.toJson(registroRequest));
			
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
	
	public class RegistrarUsuario extends AsyncCall {

		public RegistrarUsuario(Activity activity) {
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
					SesionActual.usuario.setCorreo(mEmail);
					
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
	public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.

			try {
				// Simulate network access.
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				return false;
			}

			/*
			for (String credential : DUMMY_CREDENTIALS) {
				String[] pieces = credential.split(":");
				if (pieces[0].equals(mUser)) {
					// Account exists, return true if the password matches.
					return pieces[1].equals(mPassword);
				}
			}
			*/

			// TODO: register the new account here.
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
				mPasswordView
						.setError(getString(R.string.error_incorrect_password));
				mPasswordView.requestFocus();
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}
}

