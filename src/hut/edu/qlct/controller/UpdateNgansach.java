package hut.edu.qlct.controller;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class UpdateNgansach extends DisplayNganSach {

	public void onListItemClick(ListView listView, View v, int position, long id) {
		super.onListItemClick(listView, v, position, id);

		int id_ngansach = Integer.parseInt(((TextView) v
				.findViewById(R.id.tv_id_ngansach)).getText().toString());
		System.out.println("Id ngansach: " + id_ngansach);

		Intent suachua_ngansach = new Intent(this, SuachuaNganSach.class);
		bundle.putInt("id_ngansach", id_ngansach);
		suachua_ngansach.putExtras(bundle);
		startActivityForResult(suachua_ngansach, 100);
	}

}
