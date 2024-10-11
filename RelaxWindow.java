import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import javax.imageio.ImageIO;
import java.text.SimpleDateFormat;
import java.util.Date;



public class RelaxWindow extends JFrame {

    private JLabel timeLabel; // Etiqueta para la hora
    private JLabel dateLabel; // Etiqueta para la fecha

    public RelaxWindow() {
        // Configurar la ventana
        setTitle("Modo Relax");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Ventana maximizada
        setResizable(false); // Evitar cambiar el tamaño de la ventana
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
                    g.drawImage(backgroundImage, -25, -20, 1970, 1100, this); // Ajustar la imagen al tamaño de la ventana
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

        // Crear botones de aplicaciones
        JPanel appPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Panel para las aplicaciones
        appPanel.setOpaque(false); // Hacer transparente

        // Añadir botones con iconos de aplicaciones
        JButton app1Button = createAppButton("Icons/googleicon.png", "https://www.google.com");
        JButton app2Button = createAppButton("Icons/discordicon.png", "https://discord.com/");
        JButton app3Button = createAppButton("Icons/calcicon.png", "https://www.desmos.com/scientific?lang=es");

        appPanel.add(app1Button);
        appPanel.add(app2Button);
        appPanel.add(app3Button);

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

    // Método para crear un botón de aplicación con un icono y un hipervínculo
    private JButton createAppButton(String iconPath, String url) {
        JButton button = new JButton();
        try {
            BufferedImage appIcon = ImageIO.read(new File(iconPath));
            button.setIcon(new ImageIcon(appIcon.getScaledInstance(50, 50, Image.SCALE_SMOOTH))); // Ajustar tamaño del icono
        } catch (IOException e) {
            e.printStackTrace();
        }
        button.setOpaque(false); // Hacer transparente el botón
        button.setContentAreaFilled(false); // Hacer transparente el fondo del botón
        button.setBorderPainted(false); // Sin bordes

        // Abrir el navegador cuando se presione el botón
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(url));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        return button;
    }

    public static void main(String[] args) {
        new RelaxWindow();
    }
}
