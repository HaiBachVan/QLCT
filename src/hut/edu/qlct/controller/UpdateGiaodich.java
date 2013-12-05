package hut.edu.qlct.controller;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class UpdateGiaodich extends DisplayGiaodich {

	public void onListItemClick(ListView listView, View v, int position, long id) {
		super.onListItemClick(listView, v, position, id);

		int id_giaodich = Integer.parseInt(((TextView) v
				.findViewById(R.id.tv_id_giaodich)).getText().toString());

		System.out.println("Id giaodich: " + id_giaodich);

		Intent suachua_giaodich = new Intent(this, SuachuaGiaodich.class);
		bundle.putInt("id_giaodich", id_giaodich);
		suachua_giaodich.putExtras(bundle);
		startActivityForResult(suachua_giaodich, 100);
	}
}