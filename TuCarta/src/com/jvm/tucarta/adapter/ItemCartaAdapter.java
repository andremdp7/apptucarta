package com.jvm.tucarta.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jvm.tucarta.R;
import com.jvm.tucarta.model.ItemCarta;

public class ItemCartaAdapter extends BaseAdapter{
	
	private ArrayList<ItemCarta> items;
	Context context;
	String colorNombre = "#000000";
	String colorDescripcion = "#000000";
	
	public ItemCartaAdapter(ArrayList<ItemCarta> items, Context c, String colorNombre, String colorDescripcion) {
		this.items = items;
		context = c;
		this.colorNombre = colorNombre;
		this.colorDescripcion = colorDescripcion;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public Object getItem(int pos) {
		// TODO Auto-generated method stub
		return items.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		// TODO Auto-generated method stub
		return pos;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ItemCarta item = items.get(position);
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.list_carta_item,
					null);
		}
		
		TextView nombre_itemCarta = ((TextView) convertView.findViewById(R.id.nombre_itemCarta));
		nombre_itemCarta.setText(item.getNombre());
		nombre_itemCarta.setTextColor(Color.parseColor(colorNombre));
		
		TextView descp_itemCarta = ((TextView) convertView.findViewById(R.id.descp_itemCarta));
		descp_itemCarta.setText(item.getDescripcion());
		descp_itemCarta.setTextColor(Color.parseColor(colorDescripcion));
		
		((TextView) convertView.findViewById(R.id.precio_itemCarta)).setText("S/." + String.format("%.2f", item.getPrecio()));
		
		
		
		return convertView;
		
	}

}
