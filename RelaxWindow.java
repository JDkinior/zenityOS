import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.text.SimpleDateFormat;
import java.util.Date;




public class RelaxWindow extends JFrame {

    private JLabel timeLabel; // Etiqueta para la hora
    private JLabel dateLabel; // Etiqueta para la fecha
    private JButton moreAppsButton; // Botón de más aplicaciones

    public RelaxWindow() {
        // Configurar la ventana
        setTitle("Modo Relax");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Ventana maximizada
        setResizable(false); // Evitar cambiar el tamaño de la ventana
             setUndecorated(true); // Eliminar la barra de título
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel principal con fondo de imagen
        JPanel imagePanel = new JPanel() {
            private BufferedImage backgroundImage;

            {
                try {
                    backgroundImage = ImageIO.read(new File("Backgrounds/fondo2.png")); // Reemplaza con la ruta a tu imagen
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, -30, -28, 2000, 1200, this); // Ajustar la imagen al tamaño de la ventana
                }
            }
        };

        imagePanel.setLayout(new GridBagLayout()); // Usar GridBagLayout para redimensionamiento

        // Crear la barra de aplicaciones superior
        JPanel appBar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(34, 34, 34, 235)); // Color #222222 con transparencia 92%
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 70, 70); // Bordes redondeados
            }
        };

        // Definir un tamaño fijo para el ancho de la barra de aplicaciones (por ejemplo, 1000 píxeles)
        appBar.setPreferredSize(new Dimension(1000, 70)); // Ancho: 1000 píxeles, Alto: 70 píxeles
        appBar.setMaximumSize(new Dimension(1000, 70)); // Asegurar que el ancho no se expanda más allá de 1000 píxeles
        appBar.setOpaque(false); // Hacer transparente el fondo del JPanel

        // Crear un panel para mostrar la hora y la fecha en formato vertical
        JPanel clockPanel = new JPanel(new GridLayout(2, 1)); // 2 filas, 1 columna (hora arriba, fecha abajo)
        clockPanel.setOpaque(false); // Panel transparente
        clockPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 3, 30)); // Añadir un margen derecho de 20 píxeles

        // Crear y configurar los JLabel para la hora y la fecha
        timeLabel = new JLabel();
        timeLabel.setFont(new Font("Jura", Font.BOLD, 20)); // Usa la fuente Jura si está cargada
        timeLabel.setForeground(Color.WHITE); // Color de texto blanco
        timeLabel.setHorizontalAlignment(SwingConstants.RIGHT); // Alinear a la derecha
        timeLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, -2, 0)); // Reducir la separación inferior (margen negativo)

        dateLabel = new JLabel();
        dateLabel.setFont(new Font("Jura", Font.PLAIN, 24)); // Fuente más grande para la fecha
        dateLabel.setForeground(Color.WHITE); // Color de texto blanco
        dateLabel.setHorizontalAlignment(SwingConstants.RIGHT); // Alinear a la derecha

        clockPanel.add(timeLabel); // Agregar la hora al panel
        clockPanel.add(dateLabel); // Agregar la fecha debajo

        // Actualizar el reloj cada segundo
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm a"); // Formato para hora
                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy"); // Formato para fecha
                String currentTime = timeFormatter.format(new Date()); // Hora actual
                String currentDate = dateFormatter.format(new Date()); // Fecha actual
                timeLabel.setText(currentTime); // Actualizar el texto de la hora
                dateLabel.setText(currentDate); // Actualizar el texto de la fecha
            }
        });
        timer.start(); // Iniciar el temporizador para el reloj

        moreAppsButton = createAppButton("Icons/Icon Button.png", null); // Usa el icono para el botón de más apps
        // Acción al presionar el botón "Más Apps"
        moreAppsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMoreAppsDialog(); // Mostrar el diálogo de más apps
            }
        });

        // Crear botones de aplicaciones
        JPanel appPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Panel para las aplicaciones
        appPanel.setOpaque(false); // Hacer transparente

        // Añadir botones con iconos de aplicaciones
        JButton app1Button = createAppButton("Icons/edgeicon.png", "C:/Program Files (x86)/Microsoft/Edge/Application/msedge.exe");
        JButton app2Button = createAppButton("Icons/PVZ.png", "portables/PLVSZM/PlantsVsZombies.exe");
        JButton app3Button = createAppButton("Icons/calcicon.png", "calc.exe"); // Calculadora de Windows
        JButton app4Button = createAppButton("Icons/wordicon.png", "C:/Program Files/Microsoft Office/root/Office16/WINWORD.EXE"); // Calculadora de Windows
        JButton app5Button = createAppButton("Icons/adminicon.png", "Taskmgr.exe"); // Calculadora de Windows
        JButton app6Button = createAppButton("Icons/pcontrolicon.png", "control.exe"); // Calculadora de Windows
        JButton app7Button = createAppButton("Icons/sfileicon.png", "explorer.exe"); // Calculadora de Windows

        appPanel.add(moreAppsButton); // Añadir el botón de "Más Apps"
        appPanel.add(app7Button);
        appPanel.add(app3Button);
        appPanel.add(app5Button);
        appPanel.add(app6Button);
        appPanel.add(app1Button);
        appPanel.add(app4Button);
        appPanel.add(app2Button);


        appBar.setLayout(new BorderLayout());
        appBar.add(appPanel, BorderLayout.WEST); // Agregar panel de aplicaciones a la izquierda
        appBar.add(clockPanel, BorderLayout.EAST); // Agregar el panel de reloj a la derecha de la barra de aplicaciones

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 0, 0, 0); // Añadir margen superior de 20 píxeles

        imagePanel.add(appBar, gbc); // Agregar la barra de aplicaciones con GridBagConstraints

        // Cargar la fuente Jura (si es necesario)
        try {
            Font juraFont = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/Jura-VariableFont_wght.ttf")).deriveFont(24f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(juraFont);
            timeLabel.setFont(juraFont); // Aplicar la fuente Jura a la hora
            dateLabel.setFont(juraFont.deriveFont(24f)); // Aplicar la fuente Jura a la fecha con tamaño más grande

            // Crear un panel vacío para actuar como espaciador
            JPanel spacerPanel = new JPanel();
            spacerPanel.setOpaque(false); // Hacer transparente para que no sea visible

            // Ajustar el GridBagConstraints para el espaciador
            gbc.gridy = 1;
            gbc.weighty = 1; // Asegura que el espaciador ocupe todo el espacio disponible
            gbc.anchor = GridBagConstraints.NORTH; // Ancla la barra de aplicaciones al norte
            imagePanel.add(spacerPanel, gbc); // Añadir el espaciador debajo del appBar

        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        // Agregar el panel a la ventana
        add(imagePanel);

        // Hacer visible la ventana
        setVisible(true);
    }


// Método para mostrar un diálogo con más aplicaciones sin barra de título
private void showMoreAppsDialog() {
    JDialog moreAppsDialog = new JDialog(this, "Más Aplicaciones", false);
    moreAppsDialog.setUndecorated(true); // Eliminar la barra de título
    moreAppsDialog.setSize(500, 400);
    // Obtener el tamaño de la ventana principal
    Dimension parentSize = this.getSize();
    Point parentLocation = this.getLocationOnScreen();

    // Obtener el tamaño del diálogo
    Dimension dialogSize = moreAppsDialog.getSize();

    // Calcular la posición en X para centrarlo horizontalmente
    int x = parentLocation.x + (parentSize.width - dialogSize.width) / 2 - 20;

    // Modificar manualmente la posición en Y (por ejemplo, ajustar 100 píxeles hacia arriba)
    int y = parentLocation.y + (parentSize.height - dialogSize.height) / 2 - 240; // Ajusta -100 para subir

    // Establecer la nueva posición del diálogo
    moreAppsDialog.setLocation(x, y);

    // Cerrar el diálogo cuando se haga clic fuera de él
    moreAppsDialog.addWindowFocusListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowLostFocus(java.awt.event.WindowEvent e) {
            moreAppsDialog.setVisible(false); // Ocultar el diálogo al perder el foco
        }
    });

    // Crear un panel con bordes redondeados
    JPanel gridPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(getBackground());

            // Dibujar un rectángulo redondeado como fondo
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50); // 50 es el radio de las esquinas
        }

        @Override
        public void setOpaque(boolean isOpaque) {
            super.setOpaque(false); // Asegurarse de que el panel sea transparente
        }
    };
    gridPanel.setLayout(new GridLayout(2, 4, 10, 10)); // Panel con cuadrícula de 2 filas y 4 columnas
    gridPanel.setBackground(new Color(34, 34, 34)); // Fondo oscuro

    // Añadir las aplicaciones al panel
    gridPanel.add(createAppButton("Icons/edgeicon.png", "C:/Program Files (x86)/Microsoft/Edge/Application/msedge.exe"));
    gridPanel.add(createAppButton("Icons/PVZ.png", "portables/PLVSZM/PlantsVsZombies.exe"));
    gridPanel.add(createAppButton("Icons/calcicon.png", "calc.exe"));
    gridPanel.add(createAppButton("Icons/wordicon.png", "C:/Program Files/Microsoft Office/root/Office16/WINWORD.EXE"));
    gridPanel.add(createAppButton("Icons/adminicon.png", "Taskmgr.exe"));
    gridPanel.add(createAppButton("Icons/pcontrolicon.png", "control.exe"));
    gridPanel.add(createAppButton("Icons/sfileicon.png", "explorer.exe"));

    moreAppsDialog.setBackground(new Color(0, 0, 0, 0)); // Diálogo transparente

    // Agregar el panel al diálogo y hacerlo visible
    moreAppsDialog.add(gridPanel);
    moreAppsDialog.setVisible(true);
}



   // Método para crear un botón de aplicación con un icono y ejecución de una aplicación local
   private JButton createAppButton(String iconPath, String command) {
    JButton button = new JButton();
    try {
        BufferedImage appIcon = ImageIO.read(new File(iconPath));
        ImageIcon normalIcon = new ImageIcon(appIcon.getScaledInstance(50, 50, Image.SCALE_SMOOTH)); // Icono normal
        ImageIcon pressedIcon = new ImageIcon(darkenImage(appIcon).getScaledInstance(50, 50, Image.SCALE_SMOOTH)); // Icono oscurecido

        button.setIcon(normalIcon); // Establecer el icono normal
        button.setPressedIcon(pressedIcon); // Establecer el icono cuando se presiona
    } catch (IOException e) {
        e.printStackTrace();
    }

    button.setOpaque(false); // Hacer transparente el botón
    button.setContentAreaFilled(false); // Hacer transparente el fondo del botón
    button.setBorderPainted(false); // Sin bordes
    button.setFocusable(false); // Desactivar el enfoque para evitar el borde visual

    // Ejecutar la aplicación local al hacer clic en el botón
    button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // Ejecuta el comando del sistema o la ruta de la aplicación
                ProcessBuilder pb = new ProcessBuilder(command);
                pb.start();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    });

    return button;
}

// Método para oscurecer la imagen con bordes redondeados
private BufferedImage darkenImage(BufferedImage image) {
    BufferedImage darkened = new BufferedImage(
        image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = darkened.createGraphics();
    
    // Dibujar la imagen original
    g.drawImage(image, 0, 0, null);
    
    // Ajustar la transparencia y el color negro
    g.setComposite(AlphaComposite.SrcOver.derive(0.5f)); // Ajusta la transparencia (0.5 para oscurecer)
    
    // Dibujar un rectángulo redondeado sobre la imagen
    g.setColor(Color.BLACK);
    int arcWidth = 50; // Ancho del arco
    int arcHeight = 40; // Alto del arco
    g.fillRoundRect(0, 0, image.getWidth(), image.getHeight(), arcWidth, arcHeight);
    
    g.dispose();
    return darkened;
}

    public static void main(String[] args) {
        new RelaxWindow();
    }
}
