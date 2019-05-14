package ch.jonasgredig.xprmt.view;

import ch.jonasgredig.xprmt.controller.MainFrameController;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MainFrame extends JFrame {

    private JLabel title = new JLabel("XPRMT");

    private JLabel xScaleInputTitle = new JLabel("X-Scale");
    private JLabel yScaleInputTitle = new JLabel("Y-Scale");

    private JTextField xScaleInput = new JTextField();
    private JTextField yScaleInput = new JTextField();

    private JButton generateButton = new JButton("Generate Picture");

    private String outputPath = "";

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


        JPanel scalePanel = new JPanel();
        scalePanel.setLayout(new BorderLayout());
        scalePanel.add(xScalePanel, BorderLayout.NORTH);
        scalePanel.add(yScalePanel, BorderLayout.SOUTH);

        add(scalePanel, BorderLayout.CENTER);

        add(generateButton, BorderLayout.SOUTH);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        generateButton.addActionListener(a -> {
            try {
                int x = Integer.parseInt(xScaleInput.getText());
                int y = Integer.parseInt(yScaleInput.getText());
                MainFrameController mfc = new MainFrameController();
                BufferedImage image = mfc.generateRandomPicture(x, y);
                mfc.savePNG(image, outputPath);
                xScaleInput.setText("");
                yScaleInput.setText("");
            } catch (Exception exception) {
                title.setText("ERROR!");
                System.out.println(exception);
            }
        });
    }

}
