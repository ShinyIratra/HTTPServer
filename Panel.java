import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class Panel extends JPanel {
    private int port = 0;
    private List<String> Information = new ArrayList<>();
    private ServerSocket serverSocket = null;
    private boolean running = false;

    private JButton start = new JButton("Démarrer");
    private JButton stop = new JButton("Arrêter");
    private JLabel statusLabel = new JLabel("Serveur arrêté");
    private JTextArea console = new JTextArea(10, 30);
    private JScrollPane scrollPane = new JScrollPane(console);
    private PanelConfig panelConfigs;

    public Panel(String txtPath, PanelConfig panelConfigs) {
        this.panelConfigs = panelConfigs;
        try {
            Information = ReadFile(txtPath);
            port = Integer.parseInt(getInformation("Port", Information));
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Configuration de la mise en page
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(Color.WHITE);

        // Titre et statut
        JLabel titleLabel = new JLabel("Contrôle du Serveur HTTP");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new BorderLayout(10, 10));
        statusPanel.setBackground(Color.WHITE);
        statusLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        statusPanel.add(statusLabel, BorderLayout.WEST);

        // Boutons de contrôle
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        start.setBackground(new Color(60, 179, 113));
        start.setForeground(Color.WHITE);
        stop.setBackground(new Color(220, 20, 60));
        stop.setForeground(Color.WHITE);
        buttonPanel.add(start);
        buttonPanel.add(stop);

        // Console
        console.setEditable(false);
        console.setFont(new Font("Monospaced", Font.PLAIN, 12));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Ajouter les composants
        add(titleLabel, BorderLayout.NORTH);
        add(statusPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(scrollPane, BorderLayout.EAST);

        // Gestion des boutons
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startServer(txtPath);
            }
        });

        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopServer();
            }
        });
    }

    private void startServer(String txtPath) {
        if (!running) {
            running = true;
            Information = ReadFile(txtPath);
            port = Integer.parseInt(getInformation("Port", Information));
            statusLabel.setText("Serveur en cours sur le port : " + port);
            appendToConsole("Serveur démarré sur le port : " + port);

            new Thread(() -> {
                while (running) {
                    try {
                        if (serverSocket.isClosed()) {
                            serverSocket = new ServerSocket(port);
                        }
                        Socket socket = serverSocket.accept();
                        appendToConsole("Connexion acceptée : " + socket);
                        new HttpHandler(socket, panelConfigs).start();
                    } catch (IOException ex) {
                        if (running) {
                            appendToConsole("Erreur : " + ex.getMessage());
                        }
                    }
                }
            }).start();
        } else {
            JOptionPane.showMessageDialog(this, "Le serveur est déjà en cours d'exécution.", "Erreur", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void stopServer() {
        if (running) {
            running = false;
            try {
                if (serverSocket != null && !serverSocket.isClosed()) {
                    serverSocket.close();
                }
                statusLabel.setText("Serveur arrêté avec succès.");
                appendToConsole("Serveur arrêté.");
            } catch (IOException ex) {
                appendToConsole("Erreur : " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Le serveur est déjà arrêté.", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void appendToConsole(String message) {
        SwingUtilities.invokeLater(() -> {
            console.append(message + "\n");
            console.setCaretPosition(console.getDocument().getLength());
        });
    }

    // Lire les informations depuis le fichier
    public static List<String> ReadFile(String txtPath) {
        List<String> Information = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(txtPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                Information.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Information;
    }

    // Récupérer une configuration spécifique
    public static String getInformation(String nomConfig, List<String> Information) {
        for (String str : Information) {
            if (str.startsWith(nomConfig)) {
                return str.split(":")[1].trim();
            }
        }
        return "Erreur";
    }
}
