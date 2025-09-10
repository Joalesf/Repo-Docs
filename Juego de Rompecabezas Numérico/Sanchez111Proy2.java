import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class Sanchez111Proy2 implements ActionListener {
    JFrame ventana;
    JLabel lbl_u, lbl_fisc, lbl_c, lbl_m, lbl_p, lbl_e, lbl_id, lbl_g, lbl_f, lbl_tiempo, lbl_movimientos, lbl_mensajeVictoria;
    JButton[] btn_botones;
    JButton btn_iniciar, btn_iniciar2, botonEnMovimiento, btn_mejoresJ, btn_guardarJugador;
    javax.swing.Timer timer, cronometro;
    int destinoX, destinoY, segundos = 0, movimientos = 0, espacioX, espacioY;
    boolean enMovimiento = false;
    final int PASO = 5;
    Random rnd;

    DefaultListModel<String> modeloLista = new DefaultListModel<>();
    JList<String> listaJugadores = new JList<>(modeloLista);
    JScrollPane scrollLista;

    JTextField tf_nombreJugador;

    public static void main(String[] args) {
        new Sanchez111Proy2();
    }

    Sanchez111Proy2() {
        ventana = new JFrame("Rompecabezas Numerico");
        ventana.setBounds(500, 100, 600, 600);
        ventana.setLayout(null);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);

        lbl_u = new JLabel("UNIVERSIDAD TECNOLOGICA DE PANAMA");
        lbl_u.setBounds(200, 225, 300, 25);
        ventana.add(lbl_u);

        lbl_fisc = new JLabel("FACULTAD DE INGENIERIA DE SISTEMAS COMPUTACIONALES");
        lbl_fisc.setBounds(160, 260, 365, 25);
        ventana.add(lbl_fisc);

        lbl_c = new JLabel("LIC. DESARROLLO Y GESTION DE SOFTWARE");
        lbl_c.setBounds(200, 295, 300, 25);
        ventana.add(lbl_c);

        lbl_m = new JLabel("DESARROLLO DE SOFTWARE III");
        lbl_m.setBounds(235, 330, 300, 25);
        ventana.add(lbl_m);

        lbl_p = new JLabel("PROF. RICARDO CHAN");
        lbl_p.setBounds(255, 365, 300, 25);
        ventana.add(lbl_p);

        lbl_e = new JLabel("JOSE A. SANCHEZ");
        lbl_e.setBounds(270, 400, 300, 25);
        ventana.add(lbl_e);

        lbl_id = new JLabel("8-1032-2111");
        lbl_id.setBounds(285, 435, 300, 25);
        ventana.add(lbl_id);

        lbl_g = new JLabel("1GS 222");
        lbl_g.setBounds(295, 470, 300, 25);
        ventana.add(lbl_g);

        lbl_f = new JLabel("PANAMA 2025 - 7/6/2025");
        lbl_f.setBounds(250, 505, 300, 25);
        ventana.add(lbl_f);

        btn_iniciar = new JButton("Iniciar / Reiniciar");
        btn_iniciar.setBounds(950, 50, 200, 30);
        btn_iniciar.addActionListener(this);
        ventana.add(btn_iniciar);

        btn_iniciar2 = new JButton("Iniciar2");
        btn_iniciar2.setBounds(950, 100, 100, 30);
        btn_iniciar2.addActionListener(this);
        ventana.add(btn_iniciar2);

        btn_mejoresJ = new JButton("Consultar mejores jugadores");
        btn_mejoresJ.setBounds(950, 150, 200, 30);
        btn_mejoresJ.addActionListener(this);
        ventana.add(btn_mejoresJ);

        lbl_tiempo = new JLabel("Tiempo: 0 s");
        lbl_tiempo.setBounds(950, 210, 150, 30);
        ventana.add(lbl_tiempo);

        lbl_movimientos = new JLabel("Movimientos: 0");
        lbl_movimientos.setBounds(950, 245, 150, 30);
        ventana.add(lbl_movimientos);

        lbl_mensajeVictoria = new JLabel("");
        lbl_mensajeVictoria.setBounds(950, 600, 400, 30);
        ventana.add(lbl_mensajeVictoria);

        tf_nombreJugador = new JTextField();
        tf_nombreJugador.setBounds(950, 640, 200, 25);
        tf_nombreJugador.setVisible(false);
        ventana.add(tf_nombreJugador);

        btn_guardarJugador = new JButton("Guardar");
        btn_guardarJugador.setBounds(1160, 640, 100, 25);
        btn_guardarJugador.setVisible(false);
        btn_guardarJugador.addActionListener(this);
        ventana.add(btn_guardarJugador);

        listaJugadores.setBounds(950, 300, 200, 120);
        scrollLista = new JScrollPane(listaJugadores);
        scrollLista.setBounds(1290, 50, 200, 200);
        ventana.add(scrollLista);

        rnd = new Random();
        btn_botones = new JButton[15];
        espacioX = 950 + (80 + 10) * 3;
        espacioY = 275 + (50 + 10) * 3;

        timer = new javax.swing.Timer(10, e -> {
            if (botonEnMovimiento != null) {
                int xActual = botonEnMovimiento.getX();
                int yActual = botonEnMovimiento.getY();
                if (xActual != destinoX || yActual != destinoY) {
                    int nuevoX = xActual + Integer.compare(destinoX, xActual) * PASO;
                    int nuevoY = yActual + Integer.compare(destinoY, yActual) * PASO;
                    botonEnMovimiento.setLocation(nuevoX, nuevoY);
                } else {
                    botonEnMovimiento = null;
                    enMovimiento = false;
                    timer.stop();
                    verificarVictoria();
                }
            }
        });

        cronometro = new javax.swing.Timer(1000, e -> {
            segundos++;
            lbl_tiempo.setText("Tiempo: " + segundos + " s");
        });

        for (int i = 0; i < 15; i++) {
            btn_botones[i] = new JButton(String.valueOf(i + 1));
            btn_botones[i].addActionListener(this);
            int x = 950 + (80 + 10) * (i % 4);
            int y = 275 + (50 + 10) * (i / 4);
            btn_botones[i].setBounds(x, y, 80, 50);
            ventana.add(btn_botones[i]);
        }

        ventana.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn_iniciar) {
            if (enMovimiento) return;
            for (int i = 0; i < 15; i++) {
                int j = rnd.nextInt(15);
                intercambiarBotones(btn_botones[i], btn_botones[j]);
            }
            segundos = 0;
            movimientos = 0;
            lbl_tiempo.setText("Tiempo: 0 s");
            lbl_movimientos.setText("Movimientos: 0");
            lbl_mensajeVictoria.setText("");
            cronometro.restart();

        } else if (e.getSource() == btn_iniciar2) {
            if (!enMovimiento) {
                JButton ultimo = btn_botones[14];
                botonEnMovimiento = ultimo;
                destinoX = espacioX;
                destinoY = espacioY;
                espacioX = ultimo.getX();
                espacioY = ultimo.getY();
                enMovimiento = true;
                timer.start();
            }

        } else if (e.getSource() == btn_mejoresJ) {
            modeloLista.clear();
            File archivo = new File("mejores_jugadores.txt");
            if (archivo.exists()) {
                try (Scanner sc = new Scanner(archivo)) {
                    while (sc.hasNextLine()) {
                        String nombre = sc.nextLine();
                        modeloLista.addElement(nombre);
                        if (sc.hasNextLine()) {
                            String datos = sc.nextLine();
                            modeloLista.addElement(datos);
                        }
                    }
                } catch (IOException ex) {
                    modeloLista.addElement("Error al leer archivo");
                }
            } else {
                modeloLista.addElement("No hay datos aún");
            }

        } else if (e.getSource() == btn_guardarJugador) {
            grabarJugador();

        } else {
            if (enMovimiento) return;
            for (int i = 0; i < 15; i++) {
                if (e.getSource() == btn_botones[i]) {
                    int botonX = btn_botones[i].getX();
                    int botonY = btn_botones[i].getY();
                    if ((botonX == espacioX && (botonY == espacioY - 60 || botonY == espacioY + 60)) ||
                        (botonY == espacioY && (botonX == espacioX - 90 || botonX == espacioX + 90))) {
                        botonEnMovimiento = btn_botones[i];
                        destinoX = espacioX;
                        destinoY = espacioY;
                        espacioX = botonX;
                        espacioY = botonY;
                        movimientos++;
                        lbl_movimientos.setText("Movimientos: " + movimientos);
                        enMovimiento = true;
                        timer.start();
                    }
                    break;
                }
            }
        }
    }

    private void intercambiarBotones(JButton boton1, JButton boton2) {
        int tempX = boton1.getX();
        int tempY = boton1.getY();
        boton1.setLocation(boton2.getX(), boton2.getY());
        boton2.setLocation(tempX, tempY);
    }

    private void verificarVictoria() {
        for (int i = 0; i < 15; i++) 
        {
            int xEsperado = 950 + (80 + 10) * (i % 4);
            int yEsperado = 275 + (50 + 10) * (i / 4);
            if (btn_botones[i].getX() != xEsperado || btn_botones[i].getY() != yEsperado) {
                return;
            }
        }

        cronometro.stop();
        lbl_mensajeVictoria.setText("Felicidades, Tiempo: " + segundos + "s | Movimientos: " + movimientos);
        tf_nombreJugador.setVisible(true);
        btn_guardarJugador.setVisible(true);
    }

    private void grabarJugador() 
    {
        String nombreNuevo = tf_nombreJugador.getText().trim();
        if (nombreNuevo.isEmpty()) return;

        List<String[]> jugadores = new ArrayList<>();
        File archivo = new File("mejores_jugadores.txt");
        try (Scanner sc = new Scanner(archivo)) 
        {
            while (sc.hasNextLine()) 
            {
                String nombre = sc.nextLine();
                if (!sc.hasNextLine()) break;
                String datos = sc.nextLine();
                int tiempo = Integer.parseInt(datos.split("Tiempo: ")[1].split("s")[0].trim());
                int movimientos = Integer.parseInt(datos.split("Movimientos: ")[1].trim());
                jugadores.add(new String[]{nombre, String.valueOf(tiempo), String.valueOf(movimientos)});
            }
        }catch (IOException e) 
    {
        System.out.println("No se pudo leer el archivo de mejores jugadores.");
    }
    jugadores.add(new String[]{nombreNuevo, String.valueOf(segundos), String.valueOf(movimientos)});

    jugadores.sort(Comparator.comparingInt(j -> Integer.parseInt(j[1])));

    if (jugadores.size() > 5) {
        jugadores = jugadores.subList(0, 5);
    }

    try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
        for (String[] jugador : jugadores) {
            bw.write(jugador[0]);
            bw.newLine();
            bw.write("Tiempo: " + jugador[1] + "s | Movimientos: " + jugador[2]);
            bw.newLine();
        }
    } catch (IOException e) {
        System.out.println("Error al grabar el archivo de mejores jugadores.");
    }

    tf_nombreJugador.setText("");
    tf_nombreJugador.setVisible(false);
    btn_guardarJugador.setVisible(false);
}
}
