package com.roke.codigo;

import javax.swing.text.*;
import java.awt.Color;
import java_cup.runtime.Symbol;



    public class Ideroke extends javax.swing.JFrame {

        private final Color BG_DARCULA = new Color(43, 43, 43);      // Color de Fondo Principal
        private final Color BG_EDITOR = new Color(43, 43, 43);       // Color de Fondo del editor
        private final Color BG_SIDEBAR = new Color(60, 63, 65);      // Color de los Paneles laterales
        private final Color FG_TEXTO = new Color(187, 187, 187);     // Texto gris claro
        private final Color BORDER_COLOR = new Color(50, 50, 50);    // Color de los Bordes
        private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Ideroke.class.getName());

        //Constructor de la interfaz
        public Ideroke() {
            initComponents();
            aplicarEstiloDarcula();
            redirigirSalida();
            textPaneCodigo.getStyledDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                public void insertUpdate(javax.swing.event.DocumentEvent e) {
                    java.awt.EventQueue.invokeLater(() -> aplicarColores());
                }

                public void removeUpdate(javax.swing.event.DocumentEvent e) {
                    java.awt.EventQueue.invokeLater(() -> aplicarColores());
                }

                public void changedUpdate(javax.swing.event.DocumentEvent e) {
                }
            });
        }

        @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelCodigo = new javax.swing.JPanel();
        scrollPaneCodigo = new javax.swing.JScrollPane();
        textPaneCodigo = new javax.swing.JTextPane();
        panelTerminal = new javax.swing.JPanel();
        scrollPaneTerminal = new javax.swing.JScrollPane();
        textPaneTerminal = new javax.swing.JTextPane();
        panelTabla = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        textpaneTabla = new javax.swing.JTextPane();
        panelBarraHerramientas = new javax.swing.JPanel();
        btnRun = new javax.swing.JLabel();
        btnStop = new javax.swing.JLabel();
        btnBuild = new javax.swing.JLabel();
        btnSave = new javax.swing.JLabel();
        btnAnalizar = new javax.swing.JLabel();
        lblRoke = new javax.swing.JLabel();
        panelAlertas = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textPaneAlertas = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        textPaneCodigo.setBackground(new java.awt.Color(153, 153, 153));
        scrollPaneCodigo.setViewportView(textPaneCodigo);

        textPaneTerminal.setEditable(false);
        textPaneTerminal.setBackground(new java.awt.Color(0, 0, 0));
        textPaneTerminal.setForeground(new java.awt.Color(0, 255, 0));
        scrollPaneTerminal.setViewportView(textPaneTerminal);

        javax.swing.GroupLayout panelTerminalLayout = new javax.swing.GroupLayout(panelTerminal);
        panelTerminal.setLayout(panelTerminalLayout);
        panelTerminalLayout.setHorizontalGroup(
            panelTerminalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTerminalLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(scrollPaneTerminal, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelTerminalLayout.setVerticalGroup(
            panelTerminalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPaneTerminal, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelCodigoLayout = new javax.swing.GroupLayout(panelCodigo);
        panelCodigo.setLayout(panelCodigoLayout);
        panelCodigoLayout.setHorizontalGroup(
            panelCodigoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelTerminal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCodigoLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(scrollPaneCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelCodigoLayout.setVerticalGroup(
            panelCodigoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCodigoLayout.createSequentialGroup()
                .addComponent(scrollPaneCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelTerminal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        textpaneTabla.setEditable(false);
        jScrollPane2.setViewportView(textpaneTabla);

        javax.swing.GroupLayout panelTablaLayout = new javax.swing.GroupLayout(panelTabla);
        panelTabla.setLayout(panelTablaLayout);
        panelTablaLayout.setHorizontalGroup(
            panelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTablaLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 541, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelTablaLayout.setVerticalGroup(
            panelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTablaLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        btnRun.setIcon(new javax.swing.ImageIcon(getClass().getResource("/boton-de-reproduccion.png"))); // NOI18N
        btnRun.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRunMouseClicked(evt);
            }
        });

        btnStop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/circulo.png"))); // NOI18N
        btnStop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnStopMouseClicked(evt);
            }
        });

        btnBuild.setIcon(new javax.swing.ImageIcon(getClass().getResource("/martillo.png"))); // NOI18N
        btnBuild.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBuildMouseClicked(evt);
            }
        });

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/disco-flexible.png"))); // NOI18N
        btnSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSaveMouseClicked(evt);
            }
        });

        btnAnalizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vaso.png"))); // NOI18N
        btnAnalizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAnalizarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelBarraHerramientasLayout = new javax.swing.GroupLayout(panelBarraHerramientas);
        panelBarraHerramientas.setLayout(panelBarraHerramientasLayout);
        panelBarraHerramientasLayout.setHorizontalGroup(
            panelBarraHerramientasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBarraHerramientasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnRun)
                .addGap(74, 74, 74)
                .addComponent(btnStop)
                .addGap(77, 77, 77)
                .addComponent(btnBuild)
                .addGap(86, 86, 86)
                .addComponent(btnSave)
                .addGap(64, 64, 64)
                .addComponent(btnAnalizar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelBarraHerramientasLayout.setVerticalGroup(
            panelBarraHerramientasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnSave, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelBarraHerramientasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBarraHerramientasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnStop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRun, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBuild, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAnalizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        lblRoke.setFont(new java.awt.Font("Helvetica Neue", 3, 24)); // NOI18N
        lblRoke.setText("ROKE");

        textPaneAlertas.setEditable(false);
        jScrollPane1.setViewportView(textPaneAlertas);

        javax.swing.GroupLayout panelAlertasLayout = new javax.swing.GroupLayout(panelAlertas);
        panelAlertas.setLayout(panelAlertasLayout);
        panelAlertasLayout.setHorizontalGroup(
            panelAlertasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAlertasLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 533, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelAlertasLayout.setVerticalGroup(
            panelAlertasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAlertasLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panelTabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(lblRoke, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelAlertas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelBarraHerramientas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelBarraHerramientas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(lblRoke)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelTabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(panelAlertas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRunMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRunMouseClicked
        String codigoFuente = textPaneCodigo.getText();

        if (codigoFuente.trim().isEmpty()) {
            textPaneAlertas.setText("Error: El editor está vacío.");
            return;
        }

        ManejadorCompilador.ejecutar(codigoFuente, textPaneTerminal, textPaneAlertas);
    }//GEN-LAST:event_btnRunMouseClicked

    private void btnStopMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStopMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnStopMouseClicked

    private void btnBuildMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuildMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuildMouseClicked

    private void btnSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSaveMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSaveMouseClicked

    private void btnAnalizarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAnalizarMouseClicked
            
            textpaneTabla.setText("");
            textPaneAlertas.setText(""); // Limpiamos alertas antes de empezar

            StringBuilder tablaHTML = new StringBuilder("<html><body style='color:#bd93f9; font-family:sans-serif;'>");
            tablaHTML.append("<table border='0' cellspacing='1' cellpadding='4' width='100%' bgcolor='#282a36'>");

            // 1. Tabla sin la columna COMPONENTE
            tablaHTML.append("<tr bgcolor='#44475a'>")
                    .append("<th>TOKEN</th>")
                    .append("<th>LEXEMA</th>")
                    .append("<th>PATRON</th>")
                    .append("<th>RESERVADA</th>")
                    .append("</tr>");

            StringBuilder outputAlertas = new StringBuilder("--- RESULTADOS DEL ANÁLISIS ---\n");
            String codigo = textPaneCodigo.getText();
            Lexer lexer = new Lexer(new java.io.StringReader(codigo));

            try {
                while (true) {
                    Symbol token = lexer.next_token();
                    if (token.sym == sym.EOF) {
                        break;
                    }

                    String nombreComponente = obtenerNombreToken(token.sym);
                    String lexema = (token.value != null) ? token.value.toString() : "—";
                    String patron = obtenerPatronRegEx(token.sym);
                    String tokenAmigable = obtenerTokenAmigable(token.sym, lexema);

                    boolean reservada = esPalabraReservada(token.sym);
                    String txtReservada = reservada ? "SI" : "NO";
                    String colorFila = reservada ? "#50fa7b" : "#ff5555";

                    // 2. Acumulamos Lexema y Componente para el panel de alertas
                    outputAlertas.append("Lexema: ").append(lexema)
                            .append("  |  Componente: ").append(nombreComponente).append("\n");

                    // 3. Llenamos la tabla (ya sin la celda de componente)
                    tablaHTML.append("<tr>")
                            .append("<td style='color:#8be9fd;'><b>").append(tokenAmigable).append("</b></td>")
                            .append("<td style='color:#f8f8f2;'>").append(lexema).append("</td>")
                            .append("<td style='font-family:Monospaced; color:#6272a4;'>").append(patron).append("</td>")
                            .append("<td style='color:").append(colorFila).append(";'><b>").append(txtReservada).append("</b></td>")
                            .append("</tr>");
                }

                // 4. Mandamos los resultados a sus respectivos paneles
                tablaHTML.append("</table></body></html>");
                textpaneTabla.setContentType("text/html");
                textpaneTabla.setText(tablaHTML.toString());

                textPaneAlertas.setText(outputAlertas.toString());

            } catch (Exception e) {
                textPaneAlertas.setText("Error en el análisis léxico visual: " + e.getMessage());
            }
    }

    //Roque was Here
    private String obtenerTokenAmigable(int symID, String lexema) {
        switch (symID) {
            case sym.TIPO_NUMERIN:
                return "numerin";
            case sym.TIPO_DUVALIN:
                return "duvalin";
            case sym.TIPO_TXT:
                return "txt";
            case sym.ASIGNACION:
                return "->";
            case sym.VER:
                return "ver";
            case sym.FIN_SENTENCIA:
                return "$";
            case sym.MAS:
                return "+";
            case sym.MENOS:
                return "-";
            case sym.MULT:
                return "*";
            case sym.DIV:
                return "/";
            case sym.ID:
                return "id";
            case sym.VALOR_ENTERO:
                return "entero";
            case sym.VALOR_DECIMAL:
                return "decimal";
            case sym.VALOR_TXT:
                return "cadena";
            default:
                return "desconocido";
        }
    }
// MÉTODO AUXILIAR (Añádelo debajo de tus otros métodos en Ideroke)

    private boolean esPalabraReservada(int idSimb) {
        // Aquí pon todos los tokens que consideres palabras reservadas de Roke
        return idSimb == sym.TIPO_NUMERIN
                || idSimb == sym.TIPO_DUVALIN
                || idSimb == sym.TIPO_TXT
                || idSimb == sym.VER;
    }//GEN-LAST:event_btnAnalizarMouseClicked

    public static void main(String args[]) {
        System.setProperty("flatlaf.useWindowDecorations", "true");
        System.setProperty("apple.laf.useScreenMenuBar", "true");

        try {
            javax.swing.UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatDarkLaf());
        } catch (Exception ex) {
            System.err.println("Error al aplicar el tema: " + ex.getMessage());
        }

        java.awt.EventQueue.invokeLater(() -> {
            Ideroke principal = new Ideroke();
            principal.setLocationRelativeTo(null);
            principal.setVisible(true);
        });
    }

    private void redirigirSalida() {
        java.io.PrintStream printStream = new java.io.PrintStream(new java.io.OutputStream() {
            @Override
            public void write(int b) {
                java.awt.EventQueue.invokeLater(() -> {
                    try {
                        Document doc = textPaneTerminal.getDocument();
                        doc.insertString(doc.getLength(), String.valueOf((char) b), null);
                        textPaneTerminal.setCaretPosition(doc.getLength());
                    } catch (BadLocationException e) {
                        logger.log(java.util.logging.Level.SEVERE, null, e);
                    }
                });
            }
        });
        System.setOut(printStream);
        System.setErr(printStream);
    }

    private void aplicarColores() {
        StyledDocument doc = textPaneCodigo.getStyledDocument();
        String texto = textPaneCodigo.getText();

        StyleContext sc = StyleContext.getDefaultStyleContext();

        AttributeSet asDefault = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, FG_TEXTO);

        doc.setCharacterAttributes(0, texto.length(), asDefault, true);

        // 2. Definir palabras reservadas y sus colores
        colorearPalabra(texto, "numerin", Color.ORANGE);
        colorearPalabra(texto, "duvalin", Color.ORANGE);
        colorearPalabra(texto, "txt", Color.ORANGE);
        colorearPalabra(texto, "->", Color.RED);
        colorearPalabra(texto, "$", Color.RED);
        colorearPalabra(texto, "+", Color.RED);
        colorearPalabra(texto, "-", Color.RED);
        colorearPalabra(texto, "*", Color.RED);
        colorearPalabra(texto, "/", Color.RED);
        colorearPalabra(texto, "#", Color.white);
        colorearPalabra(texto, "ver", Color.CYAN);
    }

    private void colorearPalabra(String textoCompleto, String palabra, Color color) {
        StyledDocument doc = textPaneCodigo.getStyledDocument();
        int index = textoCompleto.indexOf(palabra);
        while (index >= 0) {
            SimpleAttributeSet saset = new SimpleAttributeSet();
            StyleConstants.setForeground(saset, color);
            StyleConstants.setBold(saset, true); // Negritas para que resalte
            doc.setCharacterAttributes(index, palabra.length(), saset, false);
            index = textoCompleto.indexOf(palabra, index + palabra.length());
        }
    }

    private void aplicarEstiloDarcula() {
        getContentPane().setBackground(BG_DARCULA);
        panelBarraHerramientas.setBackground(BG_SIDEBAR);
        panelTabla.setBackground(BG_SIDEBAR);
        panelAlertas.setBackground(BG_SIDEBAR);
        panelCodigo.setBackground(BG_DARCULA);
        panelTerminal.setBackground(BG_DARCULA);
        panelTabla.setBackground(BG_DARCULA);
        textPaneCodigo.setBackground(BG_EDITOR);
        textPaneCodigo.setForeground(FG_TEXTO);
        textPaneCodigo.setCaretColor(Color.WHITE); // Cursor blanco para que se vea en lo oscuro
        textPaneCodigo.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 14));
        textpaneTabla.setBackground(BG_EDITOR);
        textpaneTabla.setForeground(FG_TEXTO);
        textpaneTabla.setCaretColor(Color.WHITE); // Cursor blanco 
        textpaneTabla.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 14));
        textPaneTerminal.setBackground(new Color(30, 31, 34)); //toque oscuro
        textPaneTerminal.setForeground(new Color(106, 135, 89)); // Verde intellij
        textPaneAlertas.setBackground(BG_EDITOR);
        textPaneAlertas.setForeground(new Color(204, 66, 66)); //rojo
        javax.swing.border.Border lineBorder = javax.swing.BorderFactory.createLineBorder(BORDER_COLOR);
        scrollPaneCodigo.setBorder(lineBorder);
        scrollPaneTerminal.setBorder(javax.swing.BorderFactory.createTitledBorder(lineBorder, "TERMINAL", 0, 0, null, FG_TEXTO));
        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(lineBorder, "ALERTAS", 0, 0, null, FG_TEXTO));
        lblRoke.setForeground(new Color(152, 118, 170)); //morad
    }

    private String obtenerNombreToken(int id) {
        switch (id) {
            case sym.TIPO_NUMERIN:
                return "Palabra Reservada (Entero)";
            case sym.TIPO_DUVALIN:
                return "Palabra Reservada (Decimal)";
            case sym.TIPO_TXT:
                return "Palabra Reservada (Texto)";
            case sym.ASIGNACION:
                return "Operador de Asignación (->)";
            case sym.FIN_SENTENCIA:
                return "Fin de Sentencia ($)";
            case sym.MAS:
                return "Operador Suma (+)";
            case sym.MENOS:
                return "Operador Resta (-)";
            case sym.MULT:
                return "Operador Multiplicación (*)";
            case sym.DIV:
                return "Operador División (/)";
            case sym.ID:
                return "Identificador";
            case sym.VALOR_ENTERO:
                return "Literal Entero";
            case sym.VALOR_DECIMAL:
                return "Literal Decimal";
            case sym.VALOR_TXT:
                return "Literal Texto";
            case sym.VER:
                return "Instrucción de Salida";
            default:
                return "Token ID: " + id;
        }
    }

    private String obtenerPatronRegEx(int id) {
        switch (id) {
            case sym.TIPO_NUMERIN:
                return "numerin";
            case sym.TIPO_DUVALIN:
                return "duvalin";
            case sym.TIPO_TXT:
                return "txt";
            case sym.ID:
                return "[a-z][a-zA-Z0-9]*";
            case sym.VALOR_ENTERO:
                return "[0-9]+";
            case sym.VALOR_DECIMAL:
                return "[0-9]+\\.[0-9]+";
            case sym.VALOR_TXT:
                return "\"[^\"]*\"";
            case sym.ASIGNACION:
                return "->";
            case sym.FIN_SENTENCIA:
                return "\\$";
            case sym.MAS:
                return "\\+";
            case sym.MENOS:
                return "\\-";
            case sym.MULT:
                return "\\*";
            case sym.DIV:
                return "\\/";
            case sym.VER:
                return "ver";
            default:
                return "—";
        }
    }

/*Roke es un lenguaje de programacion inspirado en el nombre de un integrante del equipo llamado Roque
 *diseñado para tener una variable con nombre chistoso
*/    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnAnalizar;
    private javax.swing.JLabel btnBuild;
    private javax.swing.JLabel btnRun;
    private javax.swing.JLabel btnSave;
    private javax.swing.JLabel btnStop;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblRoke;
    private javax.swing.JPanel panelAlertas;
    private javax.swing.JPanel panelBarraHerramientas;
    private javax.swing.JPanel panelCodigo;
    private javax.swing.JPanel panelTabla;
    private javax.swing.JPanel panelTerminal;
    private javax.swing.JScrollPane scrollPaneCodigo;
    private javax.swing.JScrollPane scrollPaneTerminal;
    private javax.swing.JTextPane textPaneAlertas;
    private javax.swing.JTextPane textPaneCodigo;
    private javax.swing.JTextPane textPaneTerminal;
    private javax.swing.JTextPane textpaneTabla;
    // End of variables declaration//GEN-END:variables
}
