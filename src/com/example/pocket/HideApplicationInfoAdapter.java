package com.example.pocket;

import java.util.HashMap;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

//自定义适配器类，提供给listView的自定义view
public class HideApplicationInfoAdapter extends BaseAdapter {

	private List<AppInfo> mlistAppInfo = null;
	private static HashMap<Integer, Boolean> isSelected;
	LayoutInflater infater = null;

	public HideApplicationInfoAdapter(Context context, List<AppInfo> apps) {
		infater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mlistAppInfo = apps;
		isSelected = new HashMap<Integer, Boolean>();
		initdata();
	}

	public void initdata() {
		for (int i = 0; i < mlistAppInfo.size(); i++)
			getIsSelected().put(i, false);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		System.out.println("size***" + mlistAppInfo.size());
		return mlistAppInfo.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mlistAppInfo.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertview, ViewGroup arg2) {
		System.out.println("getView at " + position);
		final int index = position;
		View view = null;
		ViewHolder holder = null;
		if (convertview == null || convertview.getTag() == null) {
			view = infater.inflate(R.layout.hide_app_item, null);
			holder = new ViewHolder(view);
			view.setTag(holder);
		} else {
			view = convertview;
			holder = (ViewHolder) convertview.getTag();
		}
		AppInfo appInfo = (AppInfo) getItem(position);
		holder.appIcon.setImageDrawable(appInfo.getAppIcon());
		holder.tvAppLabel.setText(appInfo.getAppLabel());
		holder.tvPkgName.setText(appInfo.getPkgName());
		holder.checkBox
		.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked)
					getIsSelected().put(index, true);
				else
					getIsSelected().put(index, false);
			}
		});
		holder.checkBox.setChecked(getIsSelected().get(position));
		return view;
	}

	class ViewHolder {
		ImageView appIcon;
		TextView tvAppLabel;
		TextView tvPkgName;
		CheckBox checkBox;

		public ViewHolder(View view) {
			this.appIcon = (ImageView) view.findViewById(R.id.imgApp);
			this.tvAppLabel = (TextView) view.findViewById(R.id.tvAppLabel);
			this.tvPkgName = (TextView) view.findViewById(R.id.tvPkgName);
			this.checkBox = (CheckBox) view.findViewById(R.id.checkBox1);
		}
	}

	public static HashMap<Integer, Boolean> getIsSelected() {
		return isSelected;
	}
	public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
		HideApplicationInfoAdapter.isSelected = isSelected;
	}

}
