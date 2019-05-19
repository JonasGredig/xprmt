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
    private JLabel locationTitle = new JLabel("Output location:");
    private JLabel filenameTitle = new JLabel("Filename: ");
    private JComboBox<String> fileType = new JComboBox<>();

    private JTextField xScaleInput = new JTextField();
    private JTextField yScaleInput = new JTextField();
    private JTextField locationInput = new JTextField();
    private JTextField filenameInput = new JTextField();
    private JCheckBox red;
    private JCheckBox green;
    private JCheckBox blue;
    private JCheckBox chrono;

    private JButton generateButton = new JButton("Generate Image");
    private JButton selectFolderButton = new JButton("Select Folder");

    private ImageIcon icon;

    public MainFrame() {
        setTitle(title);

        BorderLayout layout = new BorderLayout();
        setLayout(layout);

        icon = getImage();
        JPanel titleIconPanel = new JPanel();
        JLabel titleImage = new JLabel(icon);
        titleImage.setMaximumSize(new Dimension(100, 50));
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

        red = new JCheckBox("Red");
        green = new JCheckBox("Green");
        blue = new JCheckBox("Blue");
        chrono = new JCheckBox("Black/White");

        JPanel imageOptionsPanel = new JPanel();
        imageOptionsPanel.setLayout(new FlowLayout());
        imageOptionsPanel.add(xScalePanel);
        imageOptionsPanel.add(yScalePanel);
        imageOptionsPanel.add(red);
        imageOptionsPanel.add(green);
        imageOptionsPanel.add(blue);
        imageOptionsPanel.add(chrono);

        fileType.addItem(".png");
        fileType.addItem(".bmp");
        fileType.addItem(".jpg");

        JPanel filenamePanel = new JPanel();
        filenamePanel.setLayout(new BorderLayout());
        filenamePanel.add(filenameTitle, BorderLayout.NORTH);
        filenamePanel.add(filenameInput, BorderLayout.CENTER);
        filenamePanel.add(fileType, BorderLayout.EAST);

        JPanel fileOptionsPanel = new JPanel();
        fileOptionsPanel.setLayout(new BorderLayout());
        fileOptionsPanel.add(filenamePanel, BorderLayout.NORTH);
        fileOptionsPanel.add(locationPanel, BorderLayout.SOUTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(imageOptionsPanel, BorderLayout.NORTH);
        centerPanel.add(fileOptionsPanel, BorderLayout.SOUTH);
        add(centerPanel, BorderLayout.CENTER);

        add(generateButton, BorderLayout.SOUTH);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(200, 100));
        setUiDefaults();
        pack();
        setVisible(true);

        chrono.addActionListener(a -> {
            if (chrono.isSelected()) {
                red.setSelected(false);
                green.setSelected(false);
                blue.setSelected(false);
                red.setEnabled(false);
                green.setEnabled(false);
                blue.setEnabled(false);

            } else {
                red.setEnabled(true);
                green.setEnabled(true);
                blue.setEnabled(true);
            }
        });

        generateButton.addActionListener(a -> {
            try {
                if (!filenameInput.getText().equals("")) {
                    int x = Integer.parseInt(xScaleInput.getText());
                    int y = Integer.parseInt(yScaleInput.getText());
                    MainFrameController mfc = new MainFrameController();
                    BufferedImage image = mfc.generateRandomPicture(x, y, red.isSelected(), green.isSelected(), blue.isSelected(), chrono.isSelected());
                    File tmpDir = new File(locationInput.getText() + "/" + filenameInput.getText() + fileType.getSelectedItem());
                    boolean exists = tmpDir.exists();
                    if (!exists) {
                        mfc.savePNG(image, locationInput.getText() + "/" + filenameInput.getText() + fileType.getSelectedItem(), fileType.getSelectedItem().toString().substring(1));
                        JOptionPane.showMessageDialog(this, "Image successfully generated!", "Successfull!", JOptionPane.INFORMATION_MESSAGE);

                    } else {
                        int i = 0;
                        boolean exists2 = true;
                        File tmpDir2;
                        while (exists2){
                            i++;
                            tmpDir2 = new File(locationInput.getText() + "/" + filenameInput.getText() + "_" + i + fileType.getSelectedItem());
                            exists2 = tmpDir2.exists();
                        }
                        Object[] options = {
                                "Cancel",
                                "Use: " + filenameInput.getText() + "_" + i,
                                "Overwrite " + filenameInput.getText()
                        };
                        int intent = JOptionPane.showOptionDialog(this,
                                "A file with this name does already exist!",
                                "File already exists!",
                                JOptionPane.YES_NO_CANCEL_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                options,
                                options[1]);
                        if (intent == 2) {
                            mfc.savePNG(image, locationInput.getText() + "/" + filenameInput.getText() + fileType.getSelectedItem(), fileType.getSelectedItem().toString().substring(1));
                            JOptionPane.showMessageDialog(this, "Image successfully generated!", "Successfull!", JOptionPane.INFORMATION_MESSAGE);
                        } else if (intent == 1) {
                            mfc.savePNG(image, locationInput.getText() + "/" + filenameInput.getText() + "_" + i + fileType.getSelectedItem(), fileType.getSelectedItem().toString().substring(1));
                            JOptionPane.showMessageDialog(this, "Image successfully generated!", "Successfull!", JOptionPane.INFORMATION_MESSAGE);

                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(this, "Please add a filename!", "Failed!", JOptionPane.ERROR_MESSAGE);
                }
            } catch (FileNotFoundException ioException) {
                JOptionPane.showMessageDialog(this, "Please add a valid output path!", "Error!", JOptionPane.ERROR_MESSAGE);
            } catch (NullPointerException npException) {
                JOptionPane.showMessageDialog(this, "Please add a valid output path!", "Error!", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(this, "Please add a valid output path!", "Error!", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException nfException) {
                JOptionPane.showMessageDialog(this, "Please enter valid scale numbers!", "Error!", JOptionPane.ERROR_MESSAGE);
            }

            setUiDefaults();
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

    private void setUiDefaults() {
        locationInput.setText(System.getProperty("user.home") + "/Desktop/");
        filenameInput.setText("Image");
        xScaleInput.setText("100");
        yScaleInput.setText("100");
        red.setSelected(true);
        green.setSelected(true);
        blue.setSelected(true);
        chrono.setSelected(false);
    }

    private ImageIcon getImage() {
        try {
            return new ImageIcon(ImageIO.read(new File("res/img/Logo.png")).getScaledInstance(100, -1, Image.SCALE_SMOOTH));
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
