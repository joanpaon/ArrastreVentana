/*
 * Copyright 2017 José A. Pacheco Ondoño - joanpaon@gmail.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.japo.java.forms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.japo.java.events.KEM;
import org.japo.java.events.MEM;
import org.japo.java.events.MMEM;

/**
 *
 * @author José A. Pacheco Ondoño - joanpaon@gmail.com
 */
public class GUI extends JFrame {

    // Tamaño de la ventana
    public static final int VENTANA_ANC = 300;
    public static final int VENTANA_ALT = 200;
    
    // Posición ventana
    private int xIni;
    private int yIni;

    public GUI() {
        // Inicialización PREVIA
        beforeInit();

        // Creación del interfaz
        initComponents();

        // Inicialización POSTERIOR
        afterInit();
    }

    // Construcción del IGU
    private void initComponents() {
        // Eventos de teclado
        KEM kem = new KEM(this);

        // Eventos de ratón
        MEM mem = new MEM(this);
        MMEM mmem = new MMEM(this);
        
        // Otros componentes
        
        // Panel Principal
        JPanel pnlPpal = new JPanel();
        pnlPpal.setLayout(new BorderLayout());
        pnlPpal.setBackground(Color.MAGENTA);

        // Ventana principal
        setTitle("Ventana Centrada");
        setContentPane(pnlPpal);
        setResizable(false);
        setSize(VENTANA_ANC, VENTANA_ALT);
        setLocationRelativeTo(null);
        setUndecorated(true);
        addKeyListener(kem);
        addMouseListener(mem);
        addMouseMotionListener(mmem);
    }

    // Inicialización antes del IGU
    private void beforeInit() {

    }

    // Inicialización después del IGU
    private void afterInit() {

    }

    // Cerrar programa
    public void terminarPrograma() {
        // Oculta la ventana
        setVisible(false);

        // Devuelve los recursos
        dispose();

        // Cierra el programa
        System.exit(0);
    }

    public void gestionarPulsarTecla(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            terminarPrograma();
        }
    }

    public void gestionarPulsarRaton(MouseEvent e) {
        // Posición inicio arrastre
        xIni = e.getXOnScreen();
        yIni = e.getYOnScreen();
    }

    public void gestionarArrastrarRaton(MouseEvent e) {
        // Coordenada X
        int xFin = e.getXOnScreen();
        int xOff = xFin - xIni;
        xIni = xFin;

        // Coordenada Y
        int yFin = e.getYOnScreen();
        int yOff = yFin - yIni;
        yIni = yFin;

        // Posición de la ventana
        int xWin = getLocation().x;
        int yWin = getLocation().y;
        
        // Posiciona la ventana
        setLocation(xWin + xOff, yWin + yOff);
    }
}
