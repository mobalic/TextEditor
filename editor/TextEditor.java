package editor;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TextEditor extends JFrame {
    public TextEditor() {
        setTitle("The first stage");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);


        JTextArea jTextArea = new JTextArea();
        jTextArea.setName("TextArea");
        add(jTextArea, BorderLayout.CENTER);


        JTextField searchField = new JTextField();
        searchField.setName("SearchField");


        JFileChooser chooser = new JFileChooser();
        chooser.setName("FileChooser");
        chooser.setVisible(false);


        ImageIcon saveIcon = new ImageIcon("Text Editor/task/src/editor/icons/save.png");
        Image imgSave = saveIcon.getImage();
        Image newImgSave = imgSave.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        saveIcon = new ImageIcon(newImgSave);
        JButton saveButton = new JButton(saveIcon);
        saveButton.setName("SaveButton");
        saveButton.addActionListener(x -> {
            try {
                chooser.setVisible(true);
                saveFile(chooser, jTextArea);
            } catch (NullPointerException ignored) {

            }
        });


        ImageIcon LoadIcon = new ImageIcon("Text Editor/task/src/editor/icons/load.png");
        Image imgLoad = LoadIcon.getImage();
        Image newImgLoad = imgLoad.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        LoadIcon = new ImageIcon(newImgLoad);
        JButton openButton = new JButton(LoadIcon);
        openButton.setName("OpenButton");
        openButton.addActionListener(x -> {
            try {
                chooser.setVisible(true);
                openFile(chooser, jTextArea);
            } catch (Exception ignored) {

            }
        });


        JCheckBox useRegex = new JCheckBox("Use regex");
        useRegex.setName("UseRegExCheckbox");

        LinkedList<String> foundText = new LinkedList<>();
        LinkedList<Integer> indexFound = new LinkedList<>();


        ImageIcon searchIcon = new ImageIcon("Text Editor/task/src/editor/icons/search.png");
        Image imgSearch = searchIcon.getImage();
        Image newImgSearch = imgSearch.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        searchIcon = new ImageIcon(newImgSearch);
        JButton startSearchButton = new JButton(searchIcon);
        startSearchButton.setName("StartSearchButton");
        startSearchButton.addActionListener(x -> {

            Thread thread = new Thread(() -> {

                try {

                    foundText.clear();
                    indexFound.clear();

                    if (!"".equals(searchField.getText())) {

                        String regex = searchField.getText();
                        String string = jTextArea.getText();


                        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
                        final Matcher matcher = pattern.matcher(string);

                        while (matcher.find()) {
                            foundText.add(matcher.group());
                            indexFound.add(matcher.start());
                        }


                        int p0 = indexFound.get(0);
                        int p1 = foundText.get(0).length();


                        jTextArea.setCaretPosition(p0 + p1);
                        jTextArea.select(p0, p0 + p1);
                        jTextArea.grabFocus();
                    }

                } catch (Exception ignored) {

                }

            });

            thread.start();


        });


        ImageIcon previousIcon = new ImageIcon("Text Editor/task/src/editor/icons/previous.png");
        Image imgPrevious = previousIcon.getImage();
        Image newImgPre = imgPrevious.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        previousIcon = new ImageIcon(newImgPre);
        JButton previousMatchButton = new JButton(previousIcon);
        previousMatchButton.setName("PreviousMatchButton");
        previousMatchButton.addActionListener(x -> {


            try {
                if (!indexFound.isEmpty()) {
                    var a = indexFound.getLast();
                    indexFound.removeLast();
                    indexFound.addFirst(a);

                    var b = foundText.getLast();
                    foundText.removeLast();
                    foundText.addFirst(b);


                    int p0 = indexFound.get(0);
                    int p1 = foundText.get(0).length();


                    jTextArea.setCaretPosition(p0 + p1);
                    jTextArea.select(p0, p0 + p1);
                    jTextArea.grabFocus();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        ImageIcon nextMatchIcon = new ImageIcon("Text Editor/task/src/editor/icons/next.png");
        Image imgNextMatch = nextMatchIcon.getImage();
        Image newImgNext = imgNextMatch.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        nextMatchIcon = new ImageIcon(newImgNext);
        JButton nextMatchButton = new JButton(nextMatchIcon);
        nextMatchButton.setName("NextMatchButton");
        nextMatchButton.addActionListener(x -> {

            try {
                if (!indexFound.isEmpty()) {
                    var b = indexFound.getFirst();
                    indexFound.removeFirst();
                    indexFound.add(b);

                    var a = foundText.getFirst();
                    foundText.removeFirst();
                    foundText.add(a);

                    int p0 = indexFound.get(0);
                    int p1 = foundText.get(0).length();


                    jTextArea.setCaretPosition(p0 + p1);
                    jTextArea.select(p0, p0 + p1);
                    jTextArea.grabFocus();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });



        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        jScrollPane.setName("ScrollPane");
        add(jScrollPane);


        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        add(jPanel, BorderLayout.NORTH);
        jPanel.add(openButton);
        jPanel.add(saveButton);
        jPanel.add(searchField);
        jPanel.add(startSearchButton);
        jPanel.add(previousMatchButton);
        jPanel.add(nextMatchButton);
        jPanel.add(useRegex);
        jPanel.add(chooser);

        forceSize(searchField, 170, 30);
        forceSize(saveButton, 30, 30);
        forceSize(openButton, 30, 30);
        forceSize(startSearchButton, 30, 30);
        forceSize(previousMatchButton, 30, 30);
        forceSize(nextMatchButton, 30, 30);


        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);


        JMenu fileMenu = new JMenu("File");
        fileMenu.setName("MenuFile");


        JMenu searchMenu = new JMenu("Search");
        searchMenu.setName("MenuSearch");


        JMenuItem loadItem = new JMenuItem("Open");
        loadItem.setName("MenuOpen");
        loadItem.addActionListener(event -> {
            try {
                chooser.setVisible(true);
                openFile(chooser, jTextArea);
            } catch (Exception ignored) {

            }
        });


        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.setName("MenuSave");
        saveItem.addActionListener(event -> {
            try {
                chooser.setVisible(true);
                saveFile(chooser, jTextArea);
            } catch (NullPointerException ignored) {

            }
        });


        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setName("MenuExit");
        exitItem.addActionListener(event -> dispose());


        JMenuItem menuStartSearch = new JMenuItem("Start search");
        menuStartSearch.setName("MenuStartSearch");
        menuStartSearch.addActionListener(x -> {

            Thread thread = new Thread(() -> {

                try {

                    foundText.clear();
                    indexFound.clear();

                    if (!"".equals(searchField.getText())) {

                        String string = jTextArea.getText();


                        final Pattern pattern = Pattern.compile(searchField.getText(), Pattern.MULTILINE);
                        final Matcher matcher = pattern.matcher(string);

                        while (matcher.find()) {
                            foundText.add(matcher.group());
                            indexFound.add(matcher.start());
                        }


                        int p0 = indexFound.get(0);
                        int p1 = foundText.get(0).length();


                        jTextArea.setCaretPosition(p0 + p1);
                        jTextArea.select(p0, p0 + p1);
                        jTextArea.grabFocus();
                    }

                } catch (Exception ignored) {

                }

            });

            thread.start();

        });


        JMenuItem menuPreviousMatch = new JMenuItem("Previous search");
        menuPreviousMatch.setName("MenuPreviousMatch");
        menuPreviousMatch.addActionListener(x -> {
            try {
                if (!indexFound.isEmpty()) {
                    var a = indexFound.getLast();
                    indexFound.removeLast();
                    indexFound.addFirst(a);

                    var b = foundText.getLast();
                    foundText.removeLast();
                    foundText.addFirst(b);


                    int p0 = indexFound.get(0);
                    int p1 = foundText.get(0).length();


                    jTextArea.setCaretPosition(p0 + p1);
                    jTextArea.select(p0, p0 + p1);
                    jTextArea.grabFocus();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        JMenuItem menuNextMatch = new JMenuItem("Next Match");
        menuNextMatch.setName("MenuNextMatch");
        menuNextMatch.addActionListener(x -> {

            try {
                if (!indexFound.isEmpty()) {
                    var b = indexFound.getFirst();
                    indexFound.removeFirst();
                    indexFound.add(b);

                    var a = foundText.getFirst();
                    foundText.removeFirst();
                    foundText.add(a);

                    int p0 = indexFound.get(0);
                    int p1 = foundText.get(0).length();


                    jTextArea.setCaretPosition(p0 + p1);
                    jTextArea.select(p0, p0 + p1);
                    jTextArea.grabFocus();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        JMenuItem menuUseRegExp = new JMenuItem("Use regular expressions");
        menuUseRegExp.setName("MenuUseRegExp");
        menuUseRegExp.addActionListener(x -> useRegex.setSelected(true));

        menuBar.add(fileMenu);
        fileMenu.add(loadItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);

        menuBar.add(searchMenu);
        searchMenu.add(menuStartSearch);
        searchMenu.add(menuPreviousMatch);
        searchMenu.add(menuNextMatch);
        searchMenu.add(menuUseRegExp);

        setVisible(true);
    }


    public static void forceSize(JComponent component, int width, int height) {
        Dimension d = new Dimension(width, height);
        component.setMaximumSize(d);
        component.setMaximumSize(d);
        component.setPreferredSize(d);
    }



    public static void saveFile(JFileChooser jFileChooser, JTextArea textArea) {
        jFileChooser.showSaveDialog(null);
        File file = new File(jFileChooser.getSelectedFile().getAbsolutePath());
        try (PrintWriter printWriter = new PrintWriter(file)) {
            printWriter.print(textArea.getText());
        } catch (IOException e) {
            System.out.println("Error");
        }

    }



    public static void openFile(JFileChooser jFileChooser, JTextArea textArea) {
        try {
            String chek = "";
            if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                chek = jFileChooser.getSelectedFile().getAbsolutePath();
            }

            FileReader reader = new FileReader(chek);
            BufferedReader br = new BufferedReader(reader);
            textArea.read(br, null);
            br.close();
            textArea.requestFocus();
        } catch (IOException e) {
            textArea.setText(null);
        }
    }
}
