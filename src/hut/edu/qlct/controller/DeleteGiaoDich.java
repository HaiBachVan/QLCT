package hut.edu.qlct.controller;

import hut.edu.qlct.model.DatabaseAdapter;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class DeleteGiaoDich extends DisplayGiaodich {
	public static final int DIALOG_XOA_GIAO_DICH = 0;
	public DatabaseAdapter db;
	public int id_giaodich;

	public void onListItemClick(ListView listView, View v, int position, long id) {
		super.onListItemClick(listView, v, position, id);

		id_giaodich = Integer.parseInt(((TextView) v
				.findViewById(R.id.tv_id_giaodich)).getText().toString());
		showDialog(DIALOG_XOA_GIAO_DICH);

	}

	// Dialog them the tag
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_XOA_GIAO_DICH:

			return new AlertDialog.Builder(this)
					.setTitle("Bạn có muốn xóa giao dich này không???")
					.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									System.out.println("ID_GIAODICH: "
											+ id_giaodich);
									db = new DatabaseAdapter(
											getApplicationContext());
									db.open();
									db.deleteGiaoDich(id_giaodich);
									fillData();
									db.close();
								}
							})
					.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {

									closeContextMenu();

								}
							}).create();
		}
		return null;
	}

}
