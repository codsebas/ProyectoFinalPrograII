package proyectofinal.modelos;

import javax.swing.table.DefaultTableModel;

public class ProductoTableModel extends DefaultTableModel {
    public ProductoTableModel() {
        super(new Object[]{"Id Producto", "Nombre", "Precio", "Stock", "Acción"}, 0);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return column == 4;
    }
}
