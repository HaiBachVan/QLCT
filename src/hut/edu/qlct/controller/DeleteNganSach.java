package hut.edu.qlct.controller;

import hut.edu.qlct.model.DatabaseAdapter;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DeleteNganSach extends DisplayNganSach {
	public static final int DIALOG_XOA_NGANSACH = 100;
	public DatabaseAdapter db;
	public int id_ngansach;

	public void onListItemClick(ListView listView, View v, int position, long id) {
		super.onListItemClick(listView, v, position, id);

		id_ngansach = Integer.parseInt(((TextView) v
				.findViewById(R.id.tv_id_ngansach)).getText().toString());
		System.out.println("Id ngansach: " + id_ngansach);
		showDialog(DIALOG_XOA_NGANSACH);
	}

	// Dialog them the tag
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_XOA_NGANSACH:

			return new AlertDialog.Builder(this)
					.setTitle("Bạn có muốn xóa ngân sách này không???")
					.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									db = new DatabaseAdapter(
											getApplicationContext());

									db.open();
									db.deleteNganSach(id_ngansach);
									db.deleteGiaodichTheoNS(id_ngansach);
									Toast.makeText(getBaseContext(),
											"Bạn đã xóa ngân sách thành công",
											Toast.LENGTH_SHORT).show();
									System.out.println("Id ngansach: "
											+ id_ngansach);
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
