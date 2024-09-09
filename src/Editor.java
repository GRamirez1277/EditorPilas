import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class Editor extends javax.swing.JFrame {

    private Stack<String> pilaPortapapeles;
    private String portapapeles = "";
    private Color fuenteColor = Color.black;
    private boolean textoCortado = false;
    private AttributeSet[] estiloCopiado;
    private StyledDocument documento;
    private Style estilo;

    public Editor() {
        initComponents();
        estiloCopiado = new AttributeSet[0];
        documento = areaTexto.getStyledDocument();
        estilo = areaTexto.addStyle("Estilito", null);
        
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fuentes = ge.getAvailableFontFamilyNames();

        DefaultComboBoxModel<String> modelo = (DefaultComboBoxModel<String>) selectorFuente.getModel();
        for (String fuente : fuentes) {
            modelo.addElement(fuente);
        }

        pilaPortapapeles = new Stack<>();

        areaTexto.addKeyListener(new java.awt.event.KeyAdapter() {
    @Override
    public void keyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_BACK_SPACE || evt.getKeyCode() == java.awt.event.KeyEvent.VK_DELETE) {
            return;
        }
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_LEFT || evt.getKeyCode() == java.awt.event.KeyEvent.VK_RIGHT) {
            return;
        }
        combinarEstilos();
    }
});

        areaTexto.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    combinarEstilos();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {}

            @Override
            public void changedUpdate(DocumentEvent e) {}
        });
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        button1 = new java.awt.Button();
        botonNegrita = new javax.swing.JToggleButton();
        guardarArchivo = new javax.swing.JButton();
        abrirArchivo = new javax.swing.JButton();
        colorLetra = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        areaTexto = new javax.swing.JTextPane();
        tamañoFuente = new javax.swing.JComboBox<>();
        selectorFuente = new javax.swing.JComboBox<>();
        botonCortar = new javax.swing.JButton();
        botonCopiar = new javax.swing.JButton();
        botonPegar = new javax.swing.JButton();

        button1.setLabel("button1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        botonNegrita.setBackground(new java.awt.Color(255, 255, 255));
        botonNegrita.setForeground(new java.awt.Color(0, 0, 0));
        botonNegrita.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/negrita.png")));
        botonNegrita.setText("Negrita");
        botonNegrita.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        botonNegrita.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botonNegrita.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                combinarEstilos();
            }
        });

        guardarArchivo.setText("Guardar texto");

        abrirArchivo.setText("Abrir archivo");
        abrirArchivo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                abrirArchivoActionPerformed(evt);
            }
        });

        colorLetra.setBackground(new java.awt.Color(255, 255, 255));
        colorLetra.setForeground(new java.awt.Color(0, 0, 0));
        colorLetra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cambiarColor.png")));
        colorLetra.setText("Color");
        colorLetra.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                colorLetraActionPerformed(evt);
            }
        });

        jScrollPane2.setViewportView(areaTexto);

        tamañoFuente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"12", "14", "18", "24", "36", "72", "96"}));
        tamañoFuente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                combinarEstilos();
            }
        });

        selectorFuente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                combinarEstilos();
            }
        });

        javax.swing.JPanel panelBotones = new javax.swing.JPanel();
panelBotones.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

botonCortar = new javax.swing.JButton();
botonCortar.setBackground(new java.awt.Color(255, 255, 255));
botonCortar.setForeground(new java.awt.Color(0, 0, 0));
botonCortar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cortar.png")));
botonCortar.setText("Cortar");
botonCortar.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent evt) {
        botonCortarActionPerformed(evt);
    }
});

botonCopiar = new javax.swing.JButton();
botonCopiar.setBackground(new java.awt.Color(255, 255, 255));
botonCopiar.setForeground(new java.awt.Color(0, 0, 0));
botonCopiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/copiar.png")));
botonCopiar.setText("Copiar");
botonCopiar.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent evt) {
        botonCopiarActionPerformed(evt);
    }
});

botonPegar = new javax.swing.JButton();
botonPegar.setBackground(new java.awt.Color(255, 255, 255));
botonPegar.setForeground(new java.awt.Color(0, 0, 0));
botonPegar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/pegar.png")));
botonPegar.setText("Pegar");
botonPegar.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent evt) {
        botonPegarActionPerformed(evt);
    }
});

panelBotones.add(botonCortar);
panelBotones.add(botonCopiar);
panelBotones.add(botonPegar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
getContentPane().setLayout(layout);
layout.setHorizontalGroup(
    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(abrirArchivo, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(guardarArchivo, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(selectorFuente, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tamañoFuente, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(colorLetra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(botonNegrita, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addComponent(panelBotones) // Agrega el panel de botones aquí
                .addGap(59, 59, 59))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 786, javax.swing.GroupLayout.PREFERRED_SIZE)))
);
layout.setVerticalGroup(
    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
    .addGroup(layout.createSequentialGroup()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tamañoFuente, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(selectorFuente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(colorLetra)
                    .addComponent(botonNegrita)))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelBotones))) // Agrega el panel de botones aquí
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
            .addComponent(abrirArchivo)
            .addComponent(guardarArchivo))
        .addContainerGap())
);

        pack();
    }

    private void abrirArchivoActionPerformed(ActionEvent evt) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos de texto", "txt"));
        int seleccion = fileChooser.showOpenDialog(this);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            try {
                areaTexto.read(new java.io.FileReader(archivo), null);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void colorLetraActionPerformed(ActionEvent evt) {
        fuenteColor = JColorChooser.showDialog(this, "Seleccionar color de texto", fuenteColor);
        combinarEstilos();
    }

    private void botonCortarActionPerformed(ActionEvent evt) {
        if(areaTexto.getSelectedText()!=null) {
        portapapeles=areaTexto.getSelectedText();
        textoCortado=true;
        pilaPortapapeles.push(areaTexto.getText());
        StyledDocument doc=areaTexto.getStyledDocument();
        int start=areaTexto.getSelectionStart();
        int length=areaTexto.getSelectionEnd() - start;
        AttributeSet[] attrs = new AttributeSet[length];
        for (int i = 0; i < length; i++) {
            Element element = doc.getCharacterElement(start + i);
            attrs[i] = element.getAttributes();
        }
        estiloCopiado = attrs;
        areaTexto.replaceSelection("");
        pilaPortapapeles.pop();
    }
    }

    private void botonCopiarActionPerformed(ActionEvent evt) {
    if(areaTexto.getSelectedText()!=null) {
        pilaPortapapeles.push(areaTexto.getText());
        portapapeles=areaTexto.getSelectedText();
        StyledDocument doc = areaTexto.getStyledDocument();
        int start=areaTexto.getSelectionStart();
        int length=areaTexto.getSelectionEnd() - start;
        AttributeSet[] attrs=new AttributeSet[length];
        for (int i=0;i<length;i++) {
            Element element = doc.getCharacterElement(start + i);
            attrs[i] = element.getAttributes();
        }
        estiloCopiado = attrs;
    }
}

    private void botonPegarActionPerformed(ActionEvent evt) {
        if(!portapapeles.isEmpty()){
        pilaPortapapeles.push(areaTexto.getText());
        StyledDocument doc = areaTexto.getStyledDocument();
        int caretPosition = areaTexto.getCaretPosition();
        for (int i = 0; i < estiloCopiado.length; i++) {
            try {
                doc.insertString(caretPosition + i, String.valueOf(portapapeles.charAt(i)), estiloCopiado[i]);
            } catch (BadLocationException ex) {
                Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(textoCortado){
            portapapeles="";
            textoCortado=false;
        }
    }
    }

    private void combinarEstilos() {
    try {
        StyleConstants.setBold(estilo, botonNegrita.isSelected());
        StyleConstants.setFontSize(estilo, Integer.parseInt(tamañoFuente.getSelectedItem().toString()));
        StyleConstants.setFontFamily(estilo, selectorFuente.getSelectedItem().toString());
        StyleConstants.setForeground(estilo, fuenteColor);

        int start = areaTexto.getSelectionStart();
        int length = areaTexto.getSelectionEnd() - start;

        if (length > 0) {
            documento.setCharacterAttributes(start, length, estilo, true);
        } else {
            areaTexto.setCharacterAttributes(estilo, true);
        }
    } catch (Exception ex) {
        ex.printStackTrace();
    }
}



    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new Editor().setVisible(true));
    }

    private javax.swing.JTextPane areaTexto;
    private javax.swing.JButton abrirArchivo;
    private javax.swing.JButton botonCortar;
    private javax.swing.JButton botonCopiar;
    private javax.swing.JButton botonPegar;
    private javax.swing.JToggleButton botonNegrita;
    private javax.swing.JButton colorLetra;
    private javax.swing.JButton guardarArchivo;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JComboBox<String> selectorFuente;
    private javax.swing.JComboBox<String> tamañoFuente;
    private java.awt.Button button1;
}
