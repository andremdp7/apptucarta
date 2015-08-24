package com.jvm.tucarta.adapter;

import java.util.ArrayList;

import com.jvm.tucarta.R;
import com.jvm.tucarta.model.Lugar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LugaresListAdapter extends BaseAdapter{
	
	private ArrayList<Lugar> lugares;
	Context context;
	
	public LugaresListAdapter(ArrayList<Lugar> lugares, Context c) {
		this.lugares = lugares;
		context = c;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lugares.size();
	}

	@Override
	public Object getItem(int pos) {
		// TODO Auto-generated method stub
		return lugares.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		// TODO Auto-generated method stub
		return pos;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final Lugar placeSelected = lugares.get(position);
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.list_lugares_item,
					null);
		}
		
		((TextView) convertView.findViewById(R.id.nombre_lugar)).setText(placeSelected.getNombre());
		((TextView) convertView.findViewById(R.id.descp_lugar)).setText(placeSelected.getDescripcion());
		
		ImageView img = (ImageView) convertView.findViewById(R.id.imagen_lugar);
		img.setImageResource(placeSelected.getImagen());
		
		return convertView;
		
	}

}
