/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package proyectofinal.vistas;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import proyectofinal.controlador.ControladorProducto;
import proyectofinal.dao.ProductoDao;
import proyectofinal.modelos.ModeloProducto;

/**
 *
 * @author gerso
 */
public class VistaProductos extends javax.swing.JFrame {

    /**
     * Creates new form VistaProductos
     */
    
   
    
    
    public VistaProductos() {
        initComponents();
        setTitle("Gestión de Productos");
         setLocationRelativeTo(null);
         txtCodigoProducto.setEditable(false);
         txtIdCategoria.setEditable(false);
         this.productos = this.getProductos();
         
        
         
         
         
         
    }
    
   
   
    
     ProductoDao productoDao = new ProductoDao();
    List<ModeloProducto> productos = new ArrayList<>();
    
    
   public List<ModeloProducto> getProductos(){
     DefaultTableModel tabla = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            // Retorna false para que ninguna celda sea editable
            return false;
        }
    };
       
        productos = productoDao.getProductos();
        
    tabla.addColumn("Codigo Producto");
tabla.addColumn("Nombre Producto");
tabla.addColumn("Descripcion");
tabla.addColumn("Categoria");
tabla.addColumn("Precio Normal");
tabla.addColumn("Precio Promocion");
tabla.addColumn("Imagen");
  
    
 
    for (ModeloProducto producto : productos){
        tabla.addRow(new Object[]{
            producto.getIdProducto(),
            producto.getNombreProducto(),
            producto.getDescripcionProducto(),
            producto.getIdCategoria(),
            producto.getPrecioNormalProducto(),
            producto.getPrecioPromocion(),
            producto.getRutaImagen(), // Puedes usar esto para mostrar en la tabla
            
       
    });
    }
    tblProductos.setModel(tabla);
    return productos;
    }
    
    
    private void seleccionarDatosTabla() {
    int fila = tblProductos.getSelectedRow();  // Obtener la fila seleccionada
    if (fila >= 0) {
        // Asignar datos de la fila seleccionada a los campos de texto
        txtCodigoProducto.setText(tblProductos.getValueAt(fila, 0).toString());
        txtNombreProducto.setText(tblProductos.getValueAt(fila, 1).toString());
        txtDescripcion.setText(tblProductos.getValueAt(fila, 2).toString());
        txtIdCategoria.setText(tblProductos.getValueAt(fila, 3).toString());
        txtPrecioNormalProducto.setText(tblProductos.getValueAt(fila, 4).toString());
        txtPrecioPromocionProducto.setText(tblProductos.getValueAt(fila, 5).toString());
        
        // Obtener la ruta de la imagen desde la tabla
        String rutaImagen = tblProductos.getValueAt(fila, 6).toString();  // Columna donde está la ruta de la imagen

        // Mostrar la ruta de la imagen en el campo de texto
        txtImagen.setText(rutaImagen);

        // Ahora cargamos la imagen en el JLabel
        mostrarImagenEnLabel(rutaImagen);  // Método para cargar y mostrar la imagen
    } else {
        JOptionPane.showMessageDialog(this, "Por favor, seleccione un producto de la tabla.");
    }
}

private void mostrarImagenEnLabel(String rutaImagen) {
    try {
        ImageIcon imageIcon = new ImageIcon(rutaImagen);
        Image image = imageIcon.getImage(); // Convertir a Image
        Image scaledImage = image.getScaledInstance(lblImagen.getWidth(), lblImagen.getHeight(), Image.SCALE_SMOOTH); // Escalar la imagen
        lblImagen.setIcon(new ImageIcon(scaledImage)); // Asignar la imagen al JLabel
    } catch (Exception e) {
        System.out.println("Error al cargar la imagen: " + e.getMessage());
    }
}
    
     private void actualizar() {
       
   try {
        // Verifica que los campos necesarios no estén vacíos
        if (txtCodigoProducto.getText().isEmpty() || 
            txtNombreProducto.getText().isEmpty() || 
            txtDescripcion.getText().isEmpty() || 
            txtIdCategoria.getText().isEmpty() || 
            txtPrecioNormalProducto.getText().isEmpty() || 
            txtPrecioPromocionProducto.getText().isEmpty() || 
            txtImagen.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.");
            return; // Salir si hay campos vacíos
        }

        // Obtiene los valores de los campos de texto
        int codigoProducto = Integer.parseInt(txtCodigoProducto.getText());
        String nombre = txtNombreProducto.getText();
        String descripcion = txtDescripcion.getText();
        int idCategoria = Integer.parseInt(txtIdCategoria.getText());
        double precioNormal = Double.parseDouble(txtPrecioNormalProducto.getText());
        double precioPromocion = Double.parseDouble(txtPrecioPromocionProducto.getText());
        String rutaImagen = txtImagen.getText(); // Ruta de la imagen

        // Llama al método del DAO para actualizar el producto
        productoDao.updateProducto(codigoProducto, nombre, idCategoria, precioNormal, precioPromocion, descripcion, rutaImagen);

        // Refresca la tabla para mostrar el producto actualizado
        getProductos();

        // Limpia los campos después de la actualización
        limpiarCampos();

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Por favor, ingrese valores válidos en los campos numéricos.");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }
}
     
     private void eliminar(){
         int filaSeleccionada = tblProductos.getSelectedRow();
         if (filaSeleccionada >= 0) {
        int codigoProducto = Integer.parseInt(tblProductos.getValueAt(filaSeleccionada, 0).toString()); // Obtener el ID del producto

        // Confirmar la eliminación
        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar este producto?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            productoDao.deleteProducto(codigoProducto); // Llamar al método para eliminar el producto
            getProductos(); // Actualizar la tabla después de la eliminación
            limpiarCampos();
         
        }else{
           JOptionPane.showMessageDialog(this, "Por favor, seleccione un producto de la tabla para eliminar."); 
        }
        
   }
         
     }
     
    private void limpiarCampos(){
        txtCodigoProducto.setText("");
        txtNombreProducto.setText("");
        txtDescripcion.setText("");
        txtIdCategoria.setText("");
        txtPrecioNormalProducto.setText("");
        txtPrecioPromocionProducto.setText("");
        txtImagen.setText("");
        lblCodigoBarras.setIcon(null);
        lblImagen.setIcon(null);
    }
     private void cargarDatosTabla(){
         
         DefaultTableModel tabla = new DefaultTableModel();
             tabla.addColumn("Codigo Producto");
    tabla.addColumn("Nombre Producto");
    tabla.addColumn("Categoria");
    tabla.addColumn("Precio Normal");
    tabla.addColumn("Precio Promocion");
     }
     
     private void agregarProducto(){
         
          if (txtNombreProducto.getText().isEmpty() || 
        txtDescripcion.getText().isEmpty() || 
        txtIdCategoria.getText().isEmpty() || 
        txtPrecioNormalProducto.getText().isEmpty() || 
        txtPrecioPromocionProducto.getText().isEmpty() || 
        txtImagen.getText().isEmpty()) {
        
        JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.");
        return; // Salir del método si los campos están vacíos
    }

    String nombre = txtNombreProducto.getText();
    String descripcion = txtDescripcion.getText();
    int idCategoria = Integer.parseInt(txtIdCategoria.getText());
    double precioNormal = Double.parseDouble(txtPrecioNormalProducto.getText());
    double precioPromocion = Double.parseDouble(txtPrecioPromocionProducto.getText());
    String rutaImagen = txtImagen.getText(); // Ruta de la imagen

    // Llamada al método del DAO
    productoDao.addProducto(nombre, idCategoria, precioNormal, precioPromocion, descripcion, rutaImagen);

    // Refresca la tabla para mostrar el nuevo producto
    getProductos();
}
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblCodigoBarras = new javax.swing.JLabel();
        btnLimpiar = new javax.swing.JButton();
        btnAgregarProducto = new javax.swing.JButton();
        btnActualizarProducto = new javax.swing.JButton();
        btnEliminarProducto = new javax.swing.JButton();
        txtCodigoProducto = new javax.swing.JTextField();
        txtNombreProducto = new javax.swing.JTextField();
        txtPrecioPromocionProducto = new javax.swing.JTextField();
        txtPrecioNormalProducto = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        txtImagen = new javax.swing.JTextField();
        btnSelecImagen = new javax.swing.JButton();
        txtIdCategoria = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cmboxCategorias = new javax.swing.JComboBox<>();
        lblImagen = new javax.swing.JLabel();
        txtImagenCodigoBarras = new javax.swing.JTextField();
        regresarMenu = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProductos = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setText("Código:");

        jLabel2.setText("Nombre:");

        jLabel3.setText("Categoría:");

        jLabel4.setText("Precio Normal:");

        jLabel5.setText("Precio Promoción:");

        jLabel7.setText("Imagen:");

        lblCodigoBarras.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnLimpiar.setText("Buscar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        btnAgregarProducto.setText("Agregar");
        btnAgregarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarProductoActionPerformed(evt);
            }
        });

        btnActualizarProducto.setText("Actualizar");
        btnActualizarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarProductoActionPerformed(evt);
            }
        });

        btnEliminarProducto.setText("Eliminar");
        btnEliminarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProductoActionPerformed(evt);
            }
        });

        txtCodigoProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoProductoActionPerformed(evt);
            }
        });

        txtNombreProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreProductoActionPerformed(evt);
            }
        });

        txtPrecioPromocionProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioPromocionProductoActionPerformed(evt);
            }
        });

        txtPrecioNormalProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioNormalProductoActionPerformed(evt);
            }
        });

        jLabel6.setText("Descripción:");

        txtDescripcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripcionActionPerformed(evt);
            }
        });

        txtImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtImagenActionPerformed(evt);
            }
        });

        btnSelecImagen.setText("Seleccionar");
        btnSelecImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelecImagenActionPerformed(evt);
            }
        });

        txtIdCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdCategoriaActionPerformed(evt);
            }
        });

        jLabel10.setText("Id Categoria:");

        jLabel11.setText("Codigo Barras:");

        cmboxCategorias.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Alimentos frescos", "Lácteos y huevos", "Carnes y embutidos", "Panadería", "Bebidas", "Enlatados y conservas", "Productos de limpieza", "Higiene personal", "Despensa básica", "Snacks y golosinas", "Productos congelados", "Frutas y verduras", "Cereales y pastas", "Bebidas alcohólicas" }));
        cmboxCategorias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmboxCategoriasActionPerformed(evt);
            }
        });

        lblImagen.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtImagenCodigoBarras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtImagenCodigoBarrasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmboxCategorias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIdCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(49, 49, 49))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtCodigoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnLimpiar)
                                .addGap(40, 40, 40)
                                .addComponent(btnAgregarProducto))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnActualizarProducto)
                                .addGap(43, 43, 43)
                                .addComponent(btnEliminarProducto)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtDescripcion)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnSelecImagen)
                                .addGap(25, 25, 25)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPrecioNormalProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPrecioPromocionProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(72, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtImagenCodigoBarras, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblCodigoBarras, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57))))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(443, Short.MAX_VALUE)
                    .addComponent(lblImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(55, 55, 55)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtCodigoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtIdCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addComponent(txtImagenCodigoBarras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnLimpiar)
                            .addComponent(btnAgregarProducto)
                            .addComponent(jLabel11))
                        .addGap(44, 44, 44)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnActualizarProducto)
                            .addComponent(btnEliminarProducto))
                        .addGap(28, 28, 28))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtPrecioNormalProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtPrecioPromocionProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtImagen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(49, 49, 49)
                        .addComponent(btnSelecImagen)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(cmboxCategorias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(180, 180, 180))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(lblCodigoBarras, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15))))))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(118, 118, 118)
                    .addComponent(lblImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(125, Short.MAX_VALUE)))
        );

        regresarMenu.setText("Regresar al Menu");
        regresarMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regresarMenuActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tblProductos.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tblProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblProductos.setCellSelectionEnabled(true);
        tblProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProductosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblProductos);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 464, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel9.setFont(new java.awt.Font("Segoe UI Black", 0, 36)); // NOI18N
        jLabel9.setText("PRODUCTOS");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(170, 170, 170))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(197, 197, 197)
                        .addComponent(regresarMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(regresarMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodigoProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoProductoActionPerformed

    private void txtNombreProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreProductoActionPerformed

    private void txtPrecioPromocionProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioPromocionProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioPromocionProductoActionPerformed

    private void txtPrecioNormalProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioNormalProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioNormalProductoActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        
        limpiarCampos();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnActualizarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarProductoActionPerformed
        // TODO add your handling code here:
        actualizar();
        
                
    }//GEN-LAST:event_btnActualizarProductoActionPerformed

    private void btnAgregarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarProductoActionPerformed
        // TODO add your handling code here:
       
        agregarProducto();
       
    }//GEN-LAST:event_btnAgregarProductoActionPerformed

    private void btnEliminarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProductoActionPerformed
        // TODO add your handling code here:
        
        eliminar();
        limpiarCampos();
    }//GEN-LAST:event_btnEliminarProductoActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
       cargarDatosTabla();
    }//GEN-LAST:event_formWindowOpened

    private void txtDescripcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripcionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionActionPerformed

    private void txtImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtImagenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtImagenActionPerformed

    private void txtIdCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdCategoriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdCategoriaActionPerformed

    private void cmboxCategoriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmboxCategoriasActionPerformed
        // TODO add your handling code here:
      
        String seleccion = (String) cmboxCategorias.getSelectedItem();
        
        int numero = 0;
        switch (seleccion) {
            case "Alimentos frescos":
                numero = 1;
                break;
            case "Lácteos y huevos":
                numero = 2;
                break;
            case "Carnes y embutidos":
                numero = 3;
                break;
            case "Panadería":
                numero = 4;
                break;
            case "Bebidas":
                numero = 5;
                break;
            case "Enlatados y conservas":
                numero = 6;
                break;
            case "Productos de limpieza":
                numero = 7;
                break;
            case "Higiene personal":
                numero = 8;
                break;
            case "Despensa básica":
                numero = 9;
                break;
            case "Snacks y golosinas":
                numero = 10;
                break;
            case "Productos congelados":
                numero = 11;
                break;
            case "Frutas y verduras":
                numero = 12;
                break;
            case "Cereales y pastas":
                numero = 13;
                break;
            case "Bebidas alcohólicas":
                numero = 14;
                break;
            // Puedes añadir más casos según corresponda
        }
        
        txtIdCategoria.setText(String.valueOf(numero));
        
    }//GEN-LAST:event_cmboxCategoriasActionPerformed

    private void btnSelecImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecImagenActionPerformed
      
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Formatos de Archivos JPEG(*.JPEG;*.JPG;*.PNG)", "jpg","jpeg", "png");
    JFileChooser archivo = new JFileChooser();
    archivo.addChoosableFileFilter(filtro);
    archivo.setDialogTitle("Subir producto");

    int ventana = archivo.showOpenDialog(null);
    if (ventana == JFileChooser.APPROVE_OPTION) {
        File file = archivo.getSelectedFile();
        // Asegúrate de guardar la ruta absoluta de la imagen
        txtImagen.setText(file.getAbsolutePath()); 

        // Mostrar la imagen seleccionada
        Image imagen = getToolkit().getImage(txtImagen.getText());
        imagen = imagen.getScaledInstance(110, 110, Image.SCALE_DEFAULT);
        lblImagen.setIcon(new ImageIcon(imagen));
        }
        
        
        
        
    }//GEN-LAST:event_btnSelecImagenActionPerformed

    private void tblProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductosMouseClicked
        // TODO add your handling code here:
        
        seleccionarDatosTabla();
    }//GEN-LAST:event_tblProductosMouseClicked

    private void regresarMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regresarMenuActionPerformed
        // TODO add your handling code here:
        
        VistaMenu menu = new VistaMenu("supervisor");
        this.setVisible(false);
        menu.setVisible(true);
    }//GEN-LAST:event_regresarMenuActionPerformed

    private void txtImagenCodigoBarrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtImagenCodigoBarrasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtImagenCodigoBarrasActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VistaProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaProductos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizarProducto;
    private javax.swing.JButton btnAgregarProducto;
    private javax.swing.JButton btnEliminarProducto;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnSelecImagen;
    private javax.swing.JComboBox<String> cmboxCategorias;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCodigoBarras;
    private javax.swing.JLabel lblImagen;
    private javax.swing.JButton regresarMenu;
    private javax.swing.JTable tblProductos;
    private javax.swing.JTextField txtCodigoProducto;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtIdCategoria;
    private javax.swing.JTextField txtImagen;
    private javax.swing.JTextField txtImagenCodigoBarras;
    private javax.swing.JTextField txtNombreProducto;
    private javax.swing.JTextField txtPrecioNormalProducto;
    private javax.swing.JTextField txtPrecioPromocionProducto;
    // End of variables declaration//GEN-END:variables
}
