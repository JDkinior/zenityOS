import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class LoginSystem extends JFrame {

    private JLabel timeLabel;
    private JLabel dateLabel;
    private JLabel greeting;
    private JTextField pinField;
    private JPanel panel;

    public LoginSystem() {
        // Configurar la ventana
        setTitle("Login");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Ventana maximizada
        setResizable(false); // Evitar cambiar el tamaño de la ventana
        setUndecorated(true); // Eliminar la barra de título
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

// Crear un JPanel con fondo degradado
JPanel gradientPanel = new JPanel() {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // Crear un degradado de café oscuro a naranja rojizo
        Color startColor = new Color(31,10,20);  // Café oscuro
        Color endColor = new Color(254,55,36);   // Naranja rojizo moderno
        GradientPaint gradientPaint = new GradientPaint(0, 0, startColor, 0, getHeight(), endColor);
        g2d.setPaint(gradientPaint);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
};


        gradientPanel.setLayout(new BorderLayout()); // Layout para colocar componentes encima

        // Cargar la fuente Jura
        try {
            Font juraFont = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/Jura-VariableFont_wght.ttf")).deriveFont(24f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(juraFont);

            // Panel del reloj (arriba a la izquierda)
            JPanel clockPanel = new JPanel();
            clockPanel.setLayout(new BoxLayout(clockPanel, BoxLayout.Y_AXIS));
            clockPanel.setBorder(new EmptyBorder(20, 20, 0, 0)); // Margen respecto a la esquina superior izquierda
            clockPanel.setOpaque(false); // Hacer el fondo transparente para que se vea el degradado

            // Etiqueta para la hora
            timeLabel = new JLabel();
            timeLabel.setFont(juraFont.deriveFont(28f));
            timeLabel.setForeground(new Color(0xD9D9D9));
            timeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            clockPanel.add(timeLabel);

            // Etiqueta para la fecha
            dateLabel = new JLabel();
            dateLabel.setFont(juraFont.deriveFont(18f));
            dateLabel.setForeground(new Color(0xD9D9D9)); 
            dateLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            clockPanel.add(dateLabel);

            // Actualizar la hora y la fecha cada segundo
            Timer timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateTimeAndDate();
                }
            });
            timer.start();

            gradientPanel.add(clockPanel, BorderLayout.NORTH); // Colocar el reloj en la esquina superior izquierda

            // Panel de contenido centrado
            panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBorder(new EmptyBorder(180, 0, 0, 0)); // Ajustar margen superior para la imagen
            panel.setOpaque(false); // Hacer el panel transparente

            // Panel para centrar el contenido
            JPanel centerPanel = new JPanel(new BorderLayout());
            centerPanel.setOpaque(false); // Hacer el fondo transparente
            centerPanel.add(panel, BorderLayout.NORTH); // Colocar el panel en la parte superior

            // Imagen del usuario circular
            JLabel userImage = new JLabel(new ImageIcon(createCircleImage("Icons/iconlogin.jpeg", 190, 190)));
            userImage.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(userImage);
            panel.add(Box.createRigidArea(new Dimension(0, 25))); // separación

            // Mensaje de saludo según la hora
            greeting = new JLabel();
            greeting.setFont(juraFont);
            greeting.setForeground(new Color(0xD9D9D9)); 
            int hour = LocalTime.now().getHour();
            if (hour >= 0 && hour < 12) {
                greeting.setText("Buenos días");
            } else if (hour >= 12 && hour < 18) {
                greeting.setText("Buenas tardes");
            } else {
                greeting.setText("Buenas noches");
            }
            greeting.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(greeting);
            panel.add(Box.createRigidArea(new Dimension(0, 25))); // Separación

            // Caja de texto para el PIN
            pinField = new JTextField("Pin", 17) {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(Color.WHITE); // Fondo blanco
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 60, 60); // Fondo mucho más redondeado
                    super.paintComponent(g);
                }

                @Override
                public void setBorder(Border border) {
                    // No border
                }
            };

            pinField.setFont(juraFont.deriveFont(24f));
            pinField.setPreferredSize(new Dimension(100, 55));
            pinField.setMaximumSize(new Dimension(350, 55));
            pinField.setForeground(Color.GRAY);
            pinField.setOpaque(false); // Hacer el fondo transparente
            pinField.setBackground(new Color(0, 0, 0, 0));
            pinField.setHorizontalAlignment(JTextField.CENTER);
            pinField.setAlignmentX(Component.CENTER_ALIGNMENT);
            pinField.setFocusable(false); // Inicialmente desactivar el foco en el campo de texto


            
            pinField.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (pinField.getText().equals("Pin")) {
                        pinField.setText("");
                        pinField.setForeground(Color.BLACK);
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (pinField.getText().isEmpty()) {
                        pinField.setForeground(Color.GRAY);
                        pinField.setText("Pin");
                    }
                }
            });

            pinField.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    pinField.setFocusable(true);
                    pinField.requestFocusInWindow();
                }
            });

            panel.add(pinField);

            // Acción cuando se ingresan 4 caracteres
            pinField.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent e) {
                    if (pinField.getText().length() == 4) {
                        performLogin(pinField.getText());
                    }
                }
            });

            gradientPanel.add(centerPanel, BorderLayout.CENTER); // Agregar panel centrado en el panel con degradado

            // Agregar el panel con degradado a la ventana
            add(gradientPanel);

            // Hacer visible la ventana
            setVisible(true);

        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    // Método para actualizar la hora y la fecha
    private void updateTimeAndDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        timeLabel.setText(now.format(timeFormatter));
        dateLabel.setText(now.format(dateFormatter));
    }

    private void performLogin(String pin) {
        if (pin.equals("1234")) {  // Reemplazar con la lógica de validación de PIN real
            greeting.setText("¿Qué modo quieres?");
            panel.remove(pinField);

            JButton relaxButton = createRoundedButton("Relax", new Color(0x28110657, true), new Color(255, 255, 255));
            JButton zenButton = createRoundedButton("Zen", new Color(0xF35A38), new Color(0x2B1C16));

// En la función performLogin dentro de LoginSystem.java
relaxButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        new RelaxWindow(); // Abre la nueva ventana de Relax
        dispose(); // Cierra la ventana actual
    }
});

// En la función performLogin dentro de LoginSystem.java
zenButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        new ZenWindow(); // Abre la nueva ventana de Relax
        dispose(); // Cierra la ventana actual
    }
});


            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
            buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            buttonPanel.setOpaque(false); // Hacer el fondo transparente

            buttonPanel.add(relaxButton);
            buttonPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Separación
            buttonPanel.add(zenButton);

            panel.add(buttonPanel);
            panel.revalidate();
            panel.repaint();
        } else {
            JOptionPane.showMessageDialog(null, "PIN incorrecto");
        }
    }

    private JButton createRoundedButton(String text, Color textColor, Color backgroundColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(backgroundColor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 60, 60);
                super.paintComponent(g);
            }

            @Override
            public void setBorder(Border border) {
                // No border
            }
        };
        button.setFont(new Font("Jura", Font.PLAIN, 22));
        button.setPreferredSize(new Dimension(200, 55));
        button.setMaximumSize(new Dimension(200, 55));
        button.setOpaque(false);
        button.setBackground(new Color(0, 0, 0, 0));
        button.setForeground(textColor);    
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        return button;
    }

    private BufferedImage createCircleImage(String path, int width, int height) {
        try {
            BufferedImage originalImage = ImageIO.read(new File(path));
            BufferedImage circleBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = circleBuffer.createGraphics();
            g2.setClip(new Ellipse2D.Float(0, 0, width, height));
            g2.drawImage(originalImage, 0, 0, width, height, null);
            g2.dispose();
            return circleBuffer;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        new LoginSystem();
    }
}
