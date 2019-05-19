package ch.jonasgredig.xprmt.view;

import ch.jonasgredig.xprmt.controller.MainFrameController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainFrame extends JFrame {

    private JFileChooser chooser;
    private String title = "XPRMT";

    private JLabel xScaleInputTitle = new JLabel("X-Scale");
    private JLabel yScaleInputTitle = new JLabel("Y-Scale");
    private JLabel locationTitle = new JLabel("Location");

    private JTextField xScaleInput = new JTextField();
    private JTextField yScaleInput = new JTextField();
    private JTextField locationInput = new JTextField();

    private JButton generateButton = new JButton("Generate Picture");
    private JButton selectFolderButton = new JButton("Select Folder");
    BufferedImage img;
    ImageIcon icon;

    public MainFrame() {
        setTitle(title);

        BorderLayout layout = new BorderLayout();
        setLayout(layout);

        locationInput.setText(System.getProperty("user.home") + "/Desktop/");

        icon = getImage();
        JPanel titleIconPanel = new JPanel();
        JLabel titleImage = new JLabel(icon);
        titleImage.setMaximumSize(new Dimension(100,50));
        titleIconPanel.add(titleImage);
        add(titleIconPanel, BorderLayout.NORTH);

        JPanel xScalePanel = new JPanel();
        xScalePanel.setLayout(new BorderLayout());
        xScalePanel.add(xScaleInputTitle, BorderLayout.NORTH);
        xScalePanel.add(xScaleInput, BorderLayout.SOUTH);

        JPanel yScalePanel = new JPanel();
        yScalePanel.setLayout(new BorderLayout());
        yScalePanel.add(yScaleInputTitle, BorderLayout.NORTH);
        yScalePanel.add(yScaleInput, BorderLayout.SOUTH);

        JPanel locationPanel = new JPanel();
        locationPanel.setLayout(new BorderLayout());
        locationPanel.add(locationTitle, BorderLayout.NORTH);
        locationPanel.add(locationInput, BorderLayout.CENTER);
        locationPanel.add(selectFolderButton, BorderLayout.EAST);

        JPanel scalePanel = new JPanel();
        scalePanel.setLayout(new FlowLayout());
        scalePanel.add(xScalePanel);
        scalePanel.add(yScalePanel);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(scalePanel, BorderLayout.NORTH);
        centerPanel.add(locationPanel, BorderLayout.SOUTH);
        add(centerPanel, BorderLayout.CENTER);

        add(generateButton, BorderLayout.SOUTH);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(200, 100));
        pack();
        setVisible(true);

        generateButton.addActionListener(a -> {
            try {
                int x = Integer.parseInt(xScaleInput.getText());
                int y = Integer.parseInt(yScaleInput.getText());
                MainFrameController mfc = new MainFrameController();
                BufferedImage image = mfc.generateRandomPicture(x, y);
                mfc.savePNG(image, locationInput.getText());
            } catch (FileNotFoundException ioException) {
                JOptionPane.showMessageDialog(this, "Output path couldn't be found!");
            } catch (NullPointerException npException) {
                JOptionPane.showMessageDialog(this, "Output path couldn't be found!");
            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(this, "Output path couldn't be found!");
            } catch (NumberFormatException nfException) {
                JOptionPane.showMessageDialog(this, "No valid scale numbers");
            }

            xScaleInput.setText("");
            yScaleInput.setText("");
        });

        selectFolderButton.addActionListener(e -> {
            chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("~/"));
            chooser.setDialogTitle("Select Folder");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);
            //
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                this.locationInput.setText(chooser.getSelectedFile().getPath());
            } else {
                System.out.println("No Selection ");
            }
        });
    }

    private ImageIcon getImage() {
        try {
            return new ImageIcon(ImageIO.read(new File("res/img/Logo.png")).getScaledInstance(100, -1, Image.SCALE_SMOOTH));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

}
