package com.jvm.tucarta.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jvm.tucarta.R;
import com.jvm.tucarta.model.ItemCarta;

public class ItemOrdenAdapter extends BaseAdapter{
	
	private ArrayList<ItemCarta> items;
	Context context;
	String colorTexto = "#000000";
	
	public ItemOrdenAdapter(ArrayList<ItemCarta> items, Context c, String colorTexto) {
		this.items = items;
		context = c;
		this.colorTexto = colorTexto;

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
			convertView = infalInflater.inflate(R.layout.list_orden_item,
					null);
		}
		
		TextView nombre_itemCarta = ((TextView) convertView.findViewById(R.id.nombre_itemOrden));
		nombre_itemCarta.setText(item.getNombre());
		nombre_itemCarta.setTextColor(Color.parseColor(colorTexto));

		((TextView) convertView.findViewById(R.id.cant_itemOrden)).setText(item.getCantidad().toString());
		((TextView) convertView.findViewById(R.id.precio_itemOrden)).setText("S/." + String.format("%.2f", item.getPrecio() * item.getCantidad()));

		return convertView;
		
	}

}
