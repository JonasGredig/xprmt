package ch.jonasgredig.xprmt.view;

import ch.jonasgredig.xprmt.helper.ImageGenerator;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;

public class MainFrame extends JFrame {

    private JFileChooser chooser;
    private String title;

    private JLabel xScaleInputTitle;
    private JLabel yScaleInputTitle;
    private JLabel locationTitle;
    private JLabel filenameTitle;
    private JComboBox<String> fileType;

    private JTextField xScaleInput;
    private JTextField yScaleInput;
    private JTextField locationInput;
    private JTextField filenameInput;
    private JCheckBox redCheckbox;
    private JCheckBox greenCheckbox;
    private JCheckBox blueCheckbox;
    private JCheckBox chronoCheckbox;

    private JButton generateButton;
    private JButton selectFolderButton;

    private ImageIcon icon;
    private ImageGenerator imageGenerator;

    public MainFrame() {
        setTitle(title);

        BorderLayout layout = new BorderLayout();
        setLayout(layout);

        title = "XPRMT";
        xScaleInputTitle  = new JLabel("X-Scale");
        yScaleInputTitle = new JLabel("Y-Scale");
        locationTitle = new JLabel("Output location:");
        filenameTitle = new JLabel("Filename: ");
        fileType = new JComboBox<>();
        xScaleInput = new JTextField();
        yScaleInput = new JTextField();
        locationInput = new JTextField();
        filenameInput = new JTextField();
        redCheckbox = new JCheckBox("Red");
        greenCheckbox = new JCheckBox("Green");
        blueCheckbox = new JCheckBox("Blue");
        chronoCheckbox = new JCheckBox("Black/White");
        generateButton = new JButton("Generate Image");
        selectFolderButton = new JButton("Select Folder");

        icon = getImage();
        JPanel titleIconPanel = new JPanel();
        JLabel titleImage = new JLabel(icon);
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

        JPanel imageOptionsPanel = new JPanel();
        imageOptionsPanel.setLayout(new FlowLayout());
        imageOptionsPanel.add(xScalePanel);
        imageOptionsPanel.add(yScalePanel);
        imageOptionsPanel.add(redCheckbox);
        imageOptionsPanel.add(greenCheckbox);
        imageOptionsPanel.add(blueCheckbox);
        imageOptionsPanel.add(chronoCheckbox);

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
        setMinimumSize(new Dimension(450, 250));
        setMaximumSize(new Dimension(800, 500));
        setUiDefaults();
        pack();

        chronoCheckbox.addActionListener(a -> {
            if (chronoCheckbox.isSelected()) {
                redCheckbox.setSelected(false);
                greenCheckbox.setSelected(false);
                blueCheckbox.setSelected(false);
                redCheckbox.setEnabled(false);
                greenCheckbox.setEnabled(false);
                blueCheckbox.setEnabled(false);

            } else {
                redCheckbox.setEnabled(true);
                greenCheckbox.setEnabled(true);
                blueCheckbox.setEnabled(true);
            }
        });

        generateButton.addActionListener(a -> {
            handleGenerateImage();
        });

        selectFolderButton.addActionListener(e -> {
            chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("~/"));
            chooser.setDialogTitle("Select Folder");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                this.locationInput.setText(chooser.getSelectedFile().getPath());
            }
        });

        setVisible(true);
    }

    private void handleGenerateImage() {
        try {
            int x = Integer.parseInt(xScaleInput.getText());
            int y = Integer.parseInt(yScaleInput.getText());
            if (x > 0 && y > 0) {
                if (!filenameInput.getText().equals("")) {
                    imageGenerator = new ImageGenerator();
                    BufferedImage image = imageGenerator.generateRandomPicture(x, y, redCheckbox.isSelected(), greenCheckbox.isSelected(), blueCheckbox.isSelected(), chronoCheckbox.isSelected());
                    File tmpDir = new File(locationInput.getText() + "/" + filenameInput.getText() + fileType.getSelectedItem());
                    if (!tmpDir.exists()) {
                        imageGenerator.savePNG(image, locationInput.getText() + "/" + filenameInput.getText() + fileType.getSelectedItem(), fileType.getSelectedItem().toString().substring(1));
                        JOptionPane.showMessageDialog(this, "Image successfully generated!", "Successfull!", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        int i = 0;
                        boolean fileExists = true;
                        File tmpDir2;
                        while (fileExists) {
                            i++;
                            tmpDir2 = new File(locationInput.getText() + "/" + filenameInput.getText() + "_" + i + fileType.getSelectedItem());
                            fileExists = tmpDir2.exists();
                        }
                        Object[] options = {
                                "Cancel",
                                "Use: " + filenameInput.getText() + "_" + i,
                                "Overrite " + filenameInput.getText()
                        };
                        int intent = JOptionPane.showOptionDialog(this,
                                "A file with this name does already exist!", "File already exists!",
                                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                                null, options, options[1]);
                        if (intent == 2) {
                            imageGenerator.savePNG(image, locationInput.getText() + "/" + filenameInput.getText() + fileType.getSelectedItem(), fileType.getSelectedItem().toString().substring(1));
                            JOptionPane.showMessageDialog(this, "Image successfully generated!", "Successfull!", JOptionPane.INFORMATION_MESSAGE);
                        } else if (intent == 1) {
                            imageGenerator.savePNG(image, locationInput.getText() + "/" + filenameInput.getText() + "_" + i + fileType.getSelectedItem(), fileType.getSelectedItem().toString().substring(1));
                            JOptionPane.showMessageDialog(this, "Image successfully generated!", "Successfull!", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Please add a filename!", "Failed!", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "X and Y scale must be at least 1 or more!", "Failed!", JOptionPane.ERROR_MESSAGE);
            }
        } catch (FileNotFoundException ioException) {
            JOptionPane.showMessageDialog(this, "Please add a valid output path!", "Error!", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException nfException) {
            JOptionPane.showMessageDialog(this, "Please enter valid scale numbers!", "Error!", JOptionPane.ERROR_MESSAGE);
        } catch (Exception excp) {
            JOptionPane.showMessageDialog(this, "Please enter valid scale numbers!", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        setUiDefaults();
    }

    private void setUiDefaults() {
        locationInput.setText(System.getProperty("user.home") + "/Desktop/");
        filenameInput.setText("Image");
        xScaleInput.setText("100");
        yScaleInput.setText("100");
        redCheckbox.setSelected(true);
        greenCheckbox.setSelected(true);
        blueCheckbox.setSelected(true);
        chronoCheckbox.setSelected(false);
    }

    private ImageIcon getImage() {
        try {
            return new ImageIcon(ImageIO.read(new File("res/img/Logo.png")).getScaledInstance(100, -1, Image.SCALE_SMOOTH));
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
