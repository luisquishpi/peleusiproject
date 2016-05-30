package ec.peleusi.utils;

import java.lang.reflect.Field;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class JTableCustomized<T> {
	Object[] filaDatos;
	Integer[] columnasEditables = null;
	Class<?>[] tipoColumnas = null;
	Integer[] anchoColumnas = null;
	String[] camposEntidad = null;
	DefaultTableModel modelo;
	Integer[] columnasFijas = null;
	private transient TableRowSorter<? extends TableModel> sorter;

	public JTable crearTabla(String[] cabecera, List<T> listaEntidad) {
		modelo = new DefaultTableModel(null, cabecera) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				if (columnasEditables != null) {
					for (int i = 0; i < columnasEditables.length; i++) {
						if (columnIndex == columnasEditables[i])
							return true;
					}
				}
				return false;
			}
		};
		filaDatos = new Object[cabecera.length];
		// Cargar Datos al modelo
		for (T entidad : listaEntidad) {
			modelo.addRow(agregarDatosAFila(entidad));
		}

		JTable tabla = new JTable(modelo) {
			private static final long serialVersionUID = 1L;

			// Tipo de datos
			@Override
			public Class<?> getColumnClass(int column) {
				if (tipoColumnas != null) {
					for (int i = 0; i < tipoColumnas.length; i++) {
						if (column == i)
							return tipoColumnas[i].getClass();
					}
				}
				return String.class;
			}
		};
		tabla.setPreferredScrollableViewportSize(tabla.getPreferredSize());
		// Fija ancho de las columnas
		if (anchoColumnas != null) {
			Integer columnaFija = -1;
			for (int i = 0; i < anchoColumnas.length; i++) {
				if (columnasFijas != null) {
					for (int j = 0; j < columnasFijas.length; j++) {
						if (columnasFijas[j] == i) {
							columnaFija = columnasFijas[j];
							break;
						}
					}
				}
				if (columnaFija >= 0) {
					tabla.getColumnModel().getColumn(columnaFija).setMaxWidth(anchoColumnas[i]);
					tabla.getColumnModel().getColumn(columnaFija).setMinWidth(anchoColumnas[i]);
				}
				tabla.getColumnModel().getColumn(i).setPreferredWidth(anchoColumnas[i]);
				columnaFija = -1;
			}
		}
		tabla.getTableHeader().setReorderingAllowed(false);
		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tabla.setFillsViewportHeight(true);
		tabla.setShowGrid(false);
		sorter = new TableRowSorter<DefaultTableModel>(modelo);
		tabla.setRowSorter(sorter);
		return tabla;
	}

	private Object[] agregarDatosAFila(T entidad) {
		Object c;
		c = (T) entidad;
		Field[] atributos = c.getClass().getDeclaredFields();
		// Agrega Todos los Atributo a la fila
		if (camposEntidad == null) {
			for (int i = 0; i < atributos.length; i++) {
				System.out.println("> "+atributos[i].getName());
				Field atributo = null;
				try {
					atributo = c.getClass().getDeclaredField(atributos[i].getName());
				} catch (NoSuchFieldException e1) {
					e1.printStackTrace();
				} catch (SecurityException e1) {
					e1.printStackTrace();
				}
				atributo.setAccessible(true);
				try {
					filaDatos[i] = atributo.get(c);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}else{
			// Agrega los Atributos a la fila solo de la lista
			for (int i = 0; i < camposEntidad.length; i++) {
				Field atributo = null;
				try {
					atributo = c.getClass().getDeclaredField(camposEntidad[i]);
				} catch (NoSuchFieldException e1) {
					e1.printStackTrace();
				} catch (SecurityException e1) {
					e1.printStackTrace();
				}
				atributo.setAccessible(true);
				try {
					filaDatos[i] = atributo.get(c);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			
		}
		return filaDatos;
	}

	public Integer[] getColumnasEditables() {
		return columnasEditables;
	}

	public void setColumnasEditables(Integer[] columnasEditables) {
		this.columnasEditables = columnasEditables;
	}

	public Class<?>[] getTipoColumnas() {
		return tipoColumnas;
	}

	public void setTipoColumnas(Class<?>[] tipoColumnas) {
		this.tipoColumnas = tipoColumnas;
	}

	public Integer[] getAnchoColumnas() {
		return anchoColumnas;
	}

	public void setAnchoColumnas(Integer[] anchoColumnas) {
		this.anchoColumnas = anchoColumnas;
	}

	public String[] getCamposEntidad() {
		return camposEntidad;
	}

	public void setCamposEntidad(String[] camposEntidad) {
		this.camposEntidad = camposEntidad;
	}

	public DefaultTableModel getModelo() {
		return modelo;
	}

	public void setModelo(DefaultTableModel modelo) {
		this.modelo = modelo;
	}

	public Integer[] getColumnasFijas() {
		return columnasFijas;
	}

	public void setColumnasFijas(Integer[] columnasFijas) {
		this.columnasFijas = columnasFijas;
	}

	public TableRowSorter<? extends TableModel> getSorter() {
		return sorter;
	}

	public void setSorter(TableRowSorter<? extends TableModel> sorter) {
		this.sorter = sorter;
	}

}
