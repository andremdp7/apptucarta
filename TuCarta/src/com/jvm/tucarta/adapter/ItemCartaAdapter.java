package com.jvm.tucarta.adapter;

import java.util.ArrayList;

import android.content.Context;
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
	
	public ItemCartaAdapter(ArrayList<ItemCarta> items, Context c) {
		this.items = items;
		context = c;
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
		
		((TextView) convertView.findViewById(R.id.nombre_itemCarta)).setText(item.getNombre());
		((TextView) convertView.findViewById(R.id.descp_itemCarta)).setText(item.getDescripcion());
		((TextView) convertView.findViewById(R.id.precio_itemCarta)).setText("S/." + String.format("%.2f", item.getPrecio()));
		
		
		
		return convertView;
		
	}

}
