/*
 * Copyright 2019 José A. Pacheco Ondoño - joanpaon@gmail.com.
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

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Properties;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.japo.java.events.KEM;
import org.japo.java.events.MEM;
import org.japo.java.events.MMEM;
import org.japo.java.libraries.UtilesSwing;

/**
 *
 * @author José A. Pacheco Ondoño - joanpaon@gmail.com
 */
public final class GUI extends JFrame {

    // Propiedades App
    public static final String PRP_FORM_HEIGHT = "form_height";
    public static final String PRP_FORM_WIDTH = "form_width";
    public static final String PRP_LOOK_AND_FEEL_PROFILE = "look_and_feel_profile";

    // Valores por Defecto
    public static final int DEF_FORM_HEIGHT = 300;
    public static final int DEF_FORM_WIDTH = 500;
    public static final String DEF_LOOK_AND_FEEL_PROFILE = UtilesSwing.LNF_WINDOWS_PROFILE;

    // Referencias
    private final Properties prp;

    // Componentes
    private JLabel lblRotulo;
    private JPanel pnlPpal;

    // Posición ventana
    private int xIni;
    private int yIni;

    // Constructor
    public GUI(Properties prp) {
        // Conectar Referencia
        this.prp = prp;

        // Inicialización Anterior
        initBefore();

        // Creación Interfaz
        initComponents();

        // Inicializacion Posterior
        initAfter();
    }

    // Construcción del IGU
    private void initComponents() {
        // Rótulo
        lblRotulo = new JLabel("Arrástrame (ESC - Salir)");
        lblRotulo.setForeground(Color.WHITE);
        lblRotulo.setFont(new Font("Cambria", Font.PLAIN, 32));

        // Panel Principal
        pnlPpal = new JPanel();
        pnlPpal.setBackground(Color.MAGENTA);
        pnlPpal.setLayout(new GridBagLayout());
        pnlPpal.add(lblRotulo);

        // Ventana principal
        setContentPane(pnlPpal);
        try {
            int height = Integer.parseInt(prp.getProperty(PRP_FORM_HEIGHT));
            int width = Integer.parseInt(prp.getProperty(PRP_FORM_WIDTH));
            setSize(width, height);
        } catch (NumberFormatException e) {
            setSize(DEF_FORM_WIDTH, DEF_FORM_HEIGHT);
        }
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setUndecorated(true);
    }

    // Inicialización Anterior    
    private void initBefore() {
        // Establecer LnF
        UtilesSwing.establecerLnFProfile(prp.getProperty(
                PRP_LOOK_AND_FEEL_PROFILE, DEF_LOOK_AND_FEEL_PROFILE));
    }

    // Inicialización Posterior
    private void initAfter() {
        // Registra Gestores de Eventos
        addKeyListener(new KEM(this));
        addMouseListener(new MEM(this));
        addMouseMotionListener(new MMEM(this));
    }

    // Gestión de la Pulsación de Teclas
    public final void gestionarTeclas(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            UtilesSwing.terminarPrograma(this);
        }
    }

    // Inicio de Arrastre de Ventana
    public final void iniciarArrastre(MouseEvent e) {
        // Posición inicio arrastre
        xIni = e.getXOnScreen();
        yIni = e.getYOnScreen();
    }

    // Arrastre de Ventana
    public final void gestionarArrastre(MouseEvent e) {
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
