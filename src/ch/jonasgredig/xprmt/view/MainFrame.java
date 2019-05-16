package ch.jonasgredig.xprmt.view;

import ch.jonasgredig.xprmt.controller.MainFrameController;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MainFrame extends JFrame {

    private JFileChooser chooser;
    private JLabel title = new JLabel("XPRMT");

    private JLabel xScaleInputTitle = new JLabel("X-Scale");
    private JLabel yScaleInputTitle = new JLabel("Y-Scale");
    private JLabel locationTitle = new JLabel("Location");

    private JTextField xScaleInput = new JTextField();
    private JTextField yScaleInput = new JTextField();
    private JTextField locationInput = new JTextField();

    private JButton generateButton = new JButton("Generate Picture");
    private JButton selectFolderButton = new JButton("Select Folder");


    public MainFrame() {
        setTitle(title.getText());
        BorderLayout layout = new BorderLayout();
        setLayout(layout);

        add(title, BorderLayout.NORTH);

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
                xScaleInput.setText("");
                yScaleInput.setText("");
            } catch (Exception exception) {
                title.setText("ERROR!");
                System.out.println(exception);
            }
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
            }
            else {
                System.out.println("No Selection ");
            }
        });
    }

}
