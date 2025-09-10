import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;

public class ClienteForm extends JFrame {
    private JTextField txtCedula, txtNombre, txtApellido, txtDireccion, txtTelefono;
    private JComboBox<String> cbProvincia;
    private JButton btnLimpiar, btnBuscar, btnAgregar, btnModificar, btnEliminar, btnListar;
    private JTable table;
    private DefaultTableModel tableModel;
    private DB db;

    public ClienteForm() {
        db = new DB();

        setTitle("Mantenimiento - Cliente");
        setSize(700, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel lblCedula = new JLabel("Cedula:");
        lblCedula.setBounds(20, 20, 100, 25);
        add(lblCedula);

        txtCedula = new JTextField(15);
        txtCedula.setBounds(120, 20, 150, 25);
        add(txtCedula);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 60, 100, 25);
        add(lblNombre);

        txtNombre = new JTextField(20);
        txtNombre.setBounds(120, 60, 150, 25);
        add(txtNombre);

        JLabel lblApellido = new JLabel("Apellido:");
        lblApellido.setBounds(20, 100, 100, 25);
        add(lblApellido);

        txtApellido = new JTextField(20);
        txtApellido.setBounds(120, 100, 150, 25);
        add(txtApellido);

        JLabel lblDireccion = new JLabel("Direccion:");
        lblDireccion.setBounds(20, 140, 100, 25);
        add(lblDireccion);

        txtDireccion = new JTextField(30);
        txtDireccion.setBounds(120, 140, 150, 25);
        add(txtDireccion);

        JLabel lblTelefono = new JLabel("Telefono:");
        lblTelefono.setBounds(20, 180, 100, 25);
        add(lblTelefono);

        txtTelefono = new JTextField(15);
        txtTelefono.setBounds(120, 180, 150, 25);
        add(txtTelefono);

        JLabel lblProvincia = new JLabel("Provincia:");
        lblProvincia.setBounds(20, 220, 100, 25);
        add(lblProvincia);

        cbProvincia = new JComboBox<>(new String[]{
                "01 - Panama",
                "02 - Colon",
                "03 - David",
                "04 - Santiago",
                "05 - Las Tablas",
                "06 - Cocle",
                "07 - Panama Oeste",
                "08 - Herrera",
                "09 - Chitre",
                "10 - Bocas del Toro"
        });
        cbProvincia.setBounds(120, 220, 150, 25);
        add(cbProvincia);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnLimpiar = new JButton("Limpiar");
        btnBuscar = new JButton("Buscar");
        btnAgregar = new JButton("Agregar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnListar = new JButton("Listar");

        panelBotones.add(btnLimpiar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnAgregar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnListar);

        panelBotones.setBounds(20, 260, 650, 50);
        add(panelBotones);

        tableModel = new DefaultTableModel(
            new String[]{"Cedula", "Nombre", "Apellido", "Direccion", "Telefono", "Provincia"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 320, 650, 200);
        add(scrollPane);

        setEstadoInicial();

        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnBuscar.addActionListener(e -> buscarCliente());
        btnAgregar.addActionListener(e -> agregarCliente());
        btnModificar.addActionListener(e -> modificarCliente());
        btnEliminar.addActionListener(e -> eliminarCliente());
        btnListar.addActionListener(e -> listarClientes());
    }

    private void setEstadoInicial() {
        txtCedula.setEnabled(true);
        btnLimpiar.setEnabled(true);
        btnListar.setEnabled(true);
        btnBuscar.setEnabled(true);
        btnAgregar.setEnabled(false);
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtCedula.setText("");
        txtCedula.setEnabled(true);
        txtNombre.setText("");
        txtApellido.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        cbProvincia.setSelectedIndex(0);

        btnBuscar.setEnabled(true);
        btnAgregar.setEnabled(false);
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
    }

    private void buscarCliente() {
        String cedula = txtCedula.getText().trim();
        if (cedula.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese una cedula para buscar.");
            return;
        }

        try {
            ResultSet rs = db.executeQuery("SELECT * FROM cliente WHERE cedula = '" + cedula + "'");
            if (rs.next()) {
                txtNombre.setText(rs.getString("nombre"));
                txtApellido.setText(rs.getString("apellido"));
                txtDireccion.setText(rs.getString("direccion"));
                txtTelefono.setText(rs.getString("telefono"));
                String provincia = rs.getString("provincia");
                for (int i = 0; i < cbProvincia.getItemCount(); i++) {
                    if (cbProvincia.getItemAt(i).startsWith(provincia)) {
                        cbProvincia.setSelectedIndex(i);
                        break;
                    }
                }

                txtCedula.setEnabled(false);
                btnBuscar.setEnabled(false);
                btnAgregar.setEnabled(false);
                btnModificar.setEnabled(true);
                btnEliminar.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(this, "Cliente no encontrado, puede agregar uno nuevo.");
                btnAgregar.setEnabled(true);
                btnModificar.setEnabled(false);
                btnEliminar.setEnabled(false);
                txtNombre.setText("");
                txtApellido.setText("");
                txtDireccion.setText("");
                txtTelefono.setText("");
            }
            db.cerrar();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al buscar: " + e.getMessage());
        }
    }

    private void agregarCliente() {
        String cedula = txtCedula.getText().trim();
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String provincia = (String) cbProvincia.getSelectedItem();
        String codigoProvincia = provincia.substring(0, 2);

        if (cedula.isEmpty() || nombre.isEmpty() || apellido.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos obligatorios.");
            return;
        }

        try {
            String sql = "INSERT INTO cliente(cedula, nombre, apellido, direccion, telefono, provincia) " +
                         "VALUES('" + cedula + "','" + nombre + "','" + apellido + "','" + direccion + "','" + telefono + "','" + codigoProvincia + "')";
            db.executeUpdate(sql);
            JOptionPane.showMessageDialog(this, "Cliente agregado correctamente.");
            limpiarCampos();
            listarClientes();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al agregar: " + e.getMessage());
        }
    }

    private void modificarCliente() {
        String cedula = txtCedula.getText().trim();
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String provincia = (String) cbProvincia.getSelectedItem();
        String codigoProvincia = provincia.substring(0, 2);

        if (cedula.isEmpty() || nombre.isEmpty() || apellido.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos obligatorios.");
            return;
        }

        try {
            String sql = "UPDATE cliente SET nombre='" + nombre + "', apellido='" + apellido + "', direccion='" + direccion + "', telefono='" + telefono + "', provincia='" + codigoProvincia + "' WHERE cedula='" + cedula + "'";
            db.executeUpdate(sql);
            JOptionPane.showMessageDialog(this, "Cliente modificado correctamente.");
            limpiarCampos();
            listarClientes();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al modificar: " + e.getMessage());
        }
    }

    private void eliminarCliente() {
        String cedula = txtCedula.getText().trim();
        if (cedula.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese una cédula para eliminar.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Esta seguro que desea eliminar el cliente?", "Confirmar eliminacion", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            String sql = "DELETE FROM cliente WHERE cedula='" + cedula + "'";
            db.executeUpdate(sql);
            JOptionPane.showMessageDialog(this, "Cliente eliminado correctamente.");
            limpiarCampos();
            listarClientes();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar: " + e.getMessage());
        }
    }

    private void listarClientes() {
        try {
            ResultSet rs = db.executeQuery("SELECT cedula, nombre, apellido, direccion, telefono, provincia FROM cliente ORDER BY cedula");
            tableModel.setRowCount(0);
            while (rs.next()) {
                String codigoProvincia = rs.getString("provincia");
                String nombreProvincia = codigoProvincia + " - Desconocida";
                for (int i = 0; i < cbProvincia.getItemCount(); i++) {
                    if (cbProvincia.getItemAt(i).startsWith(codigoProvincia)) {
                        nombreProvincia = cbProvincia.getItemAt(i);
                        break;
                    }
                }

                Object[] row = {
                        rs.getString("cedula"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        nombreProvincia
                };
                tableModel.addRow(row);
            }
            db.cerrar();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al listar: " + e.getMessage());
        }
    }
}
