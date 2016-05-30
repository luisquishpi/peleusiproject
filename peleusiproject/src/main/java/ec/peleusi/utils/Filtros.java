package ec.peleusi.utils;

import java.util.regex.Pattern;

import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class Filtros {

	public Filtros() {
	}

	public static void filterModel(JTable tabla, DefaultTableModel modelo, String query, int[] columnas) {
		TableRowSorter<DefaultTableModel> tableRowSorter = new TableRowSorter<DefaultTableModel>(modelo);
		tabla.setRowSorter(tableRowSorter);
		tableRowSorter.setRowFilter((RowFilter.regexFilter(
				Pattern.compile("(?i)" + query, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE).toString(),
				columnas)));
	}
}
