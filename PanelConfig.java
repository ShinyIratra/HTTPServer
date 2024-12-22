import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PanelConfig extends JPanel {
    private JTextField[] information = new JTextField[2];
    private JLabel labelPort;
    private JLabel labelHtdocs;
    private JButton config = new JButton("Configurer");
    private JRadioButton PHP = new JRadioButton("Activer PHP");
    private String path = null;

    public PanelConfig(String txtPath) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Charger les données depuis le fichier
        List<String> Donnee = ReadFile(txtPath);
        PHP.setSelected(Boolean.parseBoolean(getInformation("PHP", Donnee)));
        path = getInformation("HTDOCS", Donnee);

        // Panneau pour les champs de configuration
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        labelPort = new JLabel("Port : ");
        information[0] = new JTextField(10);
        information[0].setText(getInformation("Port", Donnee));

        labelHtdocs = new JLabel("Htdocs : ");
        information[1] = new JTextField(20);
        information[1].setText(path);

        // Ajouter les composants au panneau
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(labelPort, gbc);
        gbc.gridx = 1;
        formPanel.add(information[0], gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(labelHtdocs, gbc);
        gbc.gridx = 1;
        formPanel.add(information[1], gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        formPanel.add(PHP, gbc);

        // Style du bouton
        config.setBackground(new Color(0, 123, 255));
        config.setForeground(Color.WHITE);
        config.setFocusPainted(false);
        config.setFont(new Font("Arial", Font.BOLD, 14));
        config.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Ajouter un écouteur au bouton
        config.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                saveConfig(txtPath);
            }

            @Override
            public void mouseEntered(MouseEvent e) { }
            @Override
            public void mouseExited(MouseEvent e) { }
            @Override
            public void mousePressed(MouseEvent e) { }
            @Override
            public void mouseReleased(MouseEvent e) { }
        });

        // Ajouter des composants au panneau principal
        add(new JLabel("Configuration du serveur HTTP"), BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(config, BorderLayout.SOUTH);
    }

    private void saveConfig(String txtPath) {
        StringBuilder contenu = new StringBuilder();
        try (BufferedReader lecteur = new BufferedReader(new FileReader(txtPath))) {
            String ligne;
            while ((ligne = lecteur.readLine()) != null) {
                if (ligne.startsWith("Port:")) {
                    if (!information[0].getText().isEmpty()) {
                        ligne = "Port: " + information[0].getText();
                    }
                } else if (ligne.startsWith("HTDOCS:")) {
                    if (!information[1].getText().isEmpty()) {
                        ligne = "HTDOCS: " + information[1].getText();
                        path = information[1].getText();
                    }
                } else if (ligne.startsWith("PHP")) {
                    ligne = "PHP: " + PHP.isSelected();
                }
                contenu.append(ligne).append(System.lineSeparator());
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la lecture du fichier : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Écrire les modifications dans le fichier
        try (BufferedWriter ecrivain = new BufferedWriter(new FileWriter(txtPath))) {
            ecrivain.write(contenu.toString());
            JOptionPane.showMessageDialog(this, "Configuration mise à jour avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'écriture du fichier : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean phpOn() {
        return this.PHP.isSelected();
    }

    public String path() {
        return this.path;
    }

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

    public static String getInformation(String nomConfig, List<String> Information) {
        for (String str : Information) {
            if (str.startsWith(nomConfig)) {
                return str.split(":")[1].trim();
            }
        }
        return "Erreur";
    }
}
