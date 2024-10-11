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

public class ZenWindow extends JFrame {

    private JLabel timeLabel; // Etiqueta para la hora
    private JLabel dateLabel; // Etiqueta para la fecha
    private JButton moreAppsButton; // Botón de más aplicaciones
    private Font customFont; // Fuente personalizada

    public ZenWindow() {
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/Jura-VariableFont_wght.ttf")).deriveFont(20f); // Ajusta el tamaño
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont); // Registrar la fuente en el entorno gráfico
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }


        
        // Configurar la ventana
        
        setTitle("Modo Relax");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Ventana maximizada
        setResizable(false); // Evitar cambiar el tamaño de la ventana
        setUndecorated(true); // Eliminar la barra de título
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel principal con fondo de imagen
        JPanel imagePanel = new JPanel(new BorderLayout()) {
            private BufferedImage backgroundImage;

            {
                try {
                    backgroundImage = ImageIO.read(new File("Backgrounds/fondozen.png")); // Reemplaza con la ruta a tu imagen
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, 1920, 1080, this); // Ajustar la imagen al tamaño de la ventana
                }
            }
        };

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

        // Definir el tamaño fijo de la barra de aplicaciones (ancho más pequeño)
        appBar.setPreferredSize(new Dimension(800, 70)); // Ancho fijo de 800 píxeles, alto 70
        appBar.setOpaque(false); // Fondo transparente

        // Crear el panel de aplicaciones
        JPanel appPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        appPanel.setOpaque(false);

        // Botón "Más aplicaciones"
        moreAppsButton = createAppButton("Icons/Icon Button.png", null);
        moreAppsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMoreAppsDialog(); // Mostrar el diálogo de más aplicaciones
            }
        });

        appPanel.add(moreAppsButton); // Añadir el botón de más aplicaciones

        // Añadir botones con iconos de aplicaciones
        appPanel.add(createAppButton("Icons/edgeicon.png", "C:/Program Files (x86)/Microsoft/Edge/Application/msedge.exe"));
        appPanel.add(createAppButton("Icons/PVZ.png", "portables/PLVSZM/PlantsVsZombies.exe"));
        appPanel.add(createAppButton("Icons/calcicon.png", "calc.exe"));
        appPanel.add(createAppButton("Icons/wordicon.png", "C:/Program Files/Microsoft Office/root/Office16/WINWORD.EXE"));
        appPanel.add(createAppButton("Icons/adminicon.png", "Taskmgr.exe"));
        appPanel.add(createAppButton("Icons/pcontrolicon.png", "control.exe"));
        appPanel.add(createAppButton("Icons/sfileicon.png", "explorer.exe"));


        
        // Crear un panel para mostrar la hora y la fecha en formato vertical
        JPanel clockPanel = new JPanel(new GridLayout(2, 1)); // 2 filas, 1 columna (hora arriba, fecha abajo)
        clockPanel.setOpaque(false); // Panel transparente
        clockPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 3, 30)); // Añadir un margen derecho de 20 píxeles

// Crear y configurar los JLabel para la hora y la fecha
timeLabel = new JLabel();
timeLabel.setFont(customFont.deriveFont(Font.PLAIN, 23f)); // Usa la fuente Jura cargada
timeLabel.setForeground(Color.WHITE); // Color de texto blanco
timeLabel.setHorizontalAlignment(SwingConstants.RIGHT); // Alinear a la derecha
timeLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, -2, 0)); // Reducir la separación inferior (margen negativo)

dateLabel = new JLabel();
dateLabel.setFont(customFont.deriveFont(Font.PLAIN, 24f)); // Fuente más grande para la fecha usando customFont
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

        // Añadir la appPanel al appBar
        appBar.setLayout(new BorderLayout());
        appBar.add(appPanel, BorderLayout.WEST); // Botones a la izquierda
        appBar.add(clockPanel, BorderLayout.EAST); // Hora y fecha a la derecha

        // Crear un contenedor para centrar la appBar horizontalmente
        JPanel appBarContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0)); // Centrar horizontalmente
        appBarContainer.setOpaque(false); // Fondo transparente
        appBarContainer.add(appBar); // Agregar la appBar al contenedor

        appBarContainer.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); // Añadir 20 píxeles de margen superior

        // Añadir el contenedor de la appBar al panel principal usando BorderLayout.NORTH para fijarlo arriba
        imagePanel.add(appBarContainer, BorderLayout.NORTH);

        // Crear un espaciador o contenido central para que el appBar se quede en la parte superior
        JPanel spacerPanel = new JPanel();
        spacerPanel.setOpaque(false); // Este actuará como el espacio que ocupa el resto de la ventana

        // Añadir el espaciador en el centro del BorderLayout (ocupará el espacio restante)
        imagePanel.add(spacerPanel, BorderLayout.CENTER);

        // Agregar el panel a la ventana
        add(imagePanel);

        // Hacer visible la ventana
        setVisible(true);
    }

    // Método para mostrar un diálogo con más aplicaciones sin barra de título
    private void showMoreAppsDialog() {
        JDialog moreAppsDialog = new JDialog(this, "Más Aplicaciones", false);
        moreAppsDialog.setUndecorated(true);
        moreAppsDialog.setSize(500, 400);
        moreAppsDialog.setBackground(new Color(0, 0, 0, 0));
        Dimension parentSize = this.getSize();
        Point parentLocation = this.getLocationOnScreen();
    
        Dimension dialogSize = moreAppsDialog.getSize();
        int x = parentLocation.x + (parentSize.width - dialogSize.width) / 2 - 150;
        int y = parentLocation.y + (parentSize.height - dialogSize.height) / 2 - 140;
    
        moreAppsDialog.setLocation(x, y);
    
        moreAppsDialog.addWindowFocusListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowLostFocus(java.awt.event.WindowEvent e) {
                moreAppsDialog.setVisible(false);
            }
        });
    
        JPanel gridPanel = new JPanel() {
            {
                setOpaque(false);
            }
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(34, 34, 34, 235));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
            }
        };
        gridPanel.setLayout(new GridLayout(3, 3, 10, 10));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
        gridPanel.add(createAppButton("Icons/edgeicon.png", "C:/Program Files (x86)/Microsoft/Edge/Application/msedge.exe"));
        gridPanel.add(createAppButton("Icons/PVZ.png", "portables/PLVSZM/PlantsVsZombies.exe"));
        gridPanel.add(createAppButton("Icons/calcicon.png", "calc.exe"));
        gridPanel.add(createAppButton("Icons/wordicon.png", "C:/Program Files/Microsoft Office/root/Office16/WINWORD.EXE"));
        gridPanel.add(createAppButton("Icons/adminicon.png", "Taskmgr.exe"));
        gridPanel.add(createAppButton("Icons/pcontrolicon.png", "control.exe"));
        gridPanel.add(createAppButton("Icons/sfileicon.png", "explorer.exe"));
    
        moreAppsDialog.add(gridPanel);
        moreAppsDialog.setVisible(true);
        moreAppsDialog.repaint();
    }
    

    // Método para crear un botón con un icono de aplicación
    private JButton createAppButton(String iconPath, String appPath) {
        JButton button = new JButton();
        try {
            ImageIcon icon = new ImageIcon(iconPath); // Cargar el icono desde la ruta
            Image scaledIcon = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Escalar el icono
            button.setIcon(new ImageIcon(scaledIcon)); // Establecer el icono en el botón
        } catch (Exception e) {
            e.printStackTrace();
        }

        button.setPreferredSize(new Dimension(60, 60)); // Tamaño del botón
        button.setContentAreaFilled(false); // Botón sin fondo
        button.setFocusPainted(false); // Sin borde al hacer clic
        button.setBorder(null); // Sin bordes

        button.addActionListener(new ActionListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void actionPerformed(ActionEvent e) {
                if (appPath != null) {
                    try {
                        Runtime.getRuntime().exec(appPath); // Ejecutar la aplicación
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RelaxWindow());
    }
}
