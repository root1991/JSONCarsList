package com.root.testproject.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.root.testproject.activities.R;
import com.root.testproject.model.Data;
import com.squareup.picasso.Picasso;

public class ExtAdapter extends BaseAdapter {
	private ArrayList<Data> mData;
	private Context mContext;
	private LayoutInflater mInflater;

	public ExtAdapter(Context context, ArrayList<Data> data) {
		mInflater = LayoutInflater.from(context);
		mContext = context;
		mData = data;
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		Data item = mData.get(position);
		if (item != null) {
			return item.id;
		}
		return 0;
	}

	class ViewHolder {
		TextView textViewCarName;
		TextView textViewCarId;
		ImageView imageViewCarLogo;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_list_of_cars, null);
			holder.textViewCarId = (TextView) convertView
					.findViewById(R.id.textView_car_id);
			holder.textViewCarName = (TextView) convertView
					.findViewById(R.id.textView_car_name);
			holder.imageViewCarLogo = (ImageView) convertView
					.findViewById(R.id.imageView_car_logo);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Data oneItem = mData.get(position);
		int id = oneItem.id;
		String carName = oneItem.name;
		String photoUrl = oneItem.makeIcon;

		Picasso.with(mContext).load(photoUrl)
				.placeholder(R.drawable.placeholder).error(R.drawable.error)
				.resize(100, 100).centerInside().into(holder.imageViewCarLogo);

		if (carName != null && holder.textViewCarName != null) {
			if (holder.textViewCarName != null) {
				holder.textViewCarName.setText(carName);
				holder.textViewCarId.setText(String.valueOf(id));
				if ((id % 2) == 0) {
					convertView.setBackgroundColor(mContext.getResources()
							.getColor(R.color.dark_grey));
				} else {
					convertView.setBackgroundColor(mContext.getResources()
							.getColor(android.R.color.black));
				}

			}
		}
		return convertView;
	}

}
