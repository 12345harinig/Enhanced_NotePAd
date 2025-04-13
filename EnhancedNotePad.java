// Import section (no changes needed here)
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.undo.*;
import javax.swing.text.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.datatransfer.*;
import java.io.*;
import java.util.*;
import java.nio.file.*;

public class EnhancedNotePad extends JFrame implements ActionListener {
    private JTextArea txt = new JTextArea();
    private UndoManager undoManager = new UndoManager();
    private boolean darkMode = false;
    private File currentFile = null;
    private JLabel wordCountLabel = new JLabel("Word Count: 0");
    private Font currentFont = new Font("Arial", Font.PLAIN, 15);

    public EnhancedNotePad() {
        Container container = getContentPane();
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenu viewMenu = new JMenu("View");
        JMenu formatMenu = new JMenu("Format");
        JMenu helpMenu = new JMenu("Help");

        container.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(txt);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        txt.setFont(currentFont);
        txt.setLineWrap(true);
        txt.setWrapStyleWord(true);
        txt.getDocument().addUndoableEditListener(undoManager);
        txt.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { updateWordCount(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { updateWordCount(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { updateWordCount(); }
        });

        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.add(wordCountLabel);

        container.add(scrollPane, BorderLayout.CENTER);
        container.add(statusPanel, BorderLayout.SOUTH);

        // File menu items
        createMenuItem(fileMenu, "New");
        createMenuItem(fileMenu, "Open");
        createMenuItem(fileMenu, "Save");
        createMenuItem(fileMenu, "Save As");
        createMenuItem(fileMenu, "Save as PDF");
        fileMenu.addSeparator();
        createMenuItem(fileMenu, "Import Speech");  // ✅ NEW
        fileMenu.addSeparator();
        createMenuItem(fileMenu, "Exit");

        // Edit menu items
        createMenuItem(editMenu, "Undo");
        createMenuItem(editMenu, "Redo");
        createMenuItem(editMenu, "Cut");
        createMenuItem(editMenu, "Copy");
        createMenuItem(editMenu, "Paste");
        createMenuItem(editMenu, "Find and Replace");
        createMenuItem(editMenu, "Auto Correct");

        // View menu items
        createMenuItem(viewMenu, "Toggle Dark Mode");

        // Format menu items
        createMenuItem(formatMenu, "Font Size");
        createMenuItem(formatMenu, "Font Color");
        createMenuItem(formatMenu, "Background Color");

        // Help menu items
        createMenuItem(helpMenu, "About Notepad");

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(viewMenu);
        menuBar.add(formatMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
        setSize(600, 600);
        setTitle("Untitled - Notepad");
        setVisible(true);
    }

    private void createMenuItem(JMenu menu, String text) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.addActionListener(this);
        menu.add(menuItem);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "New":
                txt.setText("");
                currentFile = null;
                setTitle("Untitled - Notepad");
                break;
            case "Open":
                openFile();
                break;
            case "Save":
                saveFile();
                break;
            case "Save As":
                saveFileAs();
                break;
            case "Save as PDF":
                saveAsPDF();
                break;
            case "Import Speech":  // ✅ NEW FEATURE
                importSpeech();
                break;
            case "Font Size":
                changeFontSize();
                break;
            case "Font Color":
                changeFontColor();
                break;
            case "Background Color":
                changeBackgroundColor();
                break;
            case "Undo":
                if (undoManager.canUndo()) undoManager.undo();
                break;
            case "Redo":
                if (undoManager.canRedo()) undoManager.redo();
                break;
            case "Toggle Dark Mode":
                toggleDarkMode();
                break;
            case "Find and Replace":
                findAndReplace();
                break;
            case "Auto Correct":
                autoCorrect();
                break;
            case "Cut":
                cutText();
                break;
            case "Copy":
                copyText();
                break;
            case "Paste":
                pasteText();
                break;
            case "About Notepad":
                JOptionPane.showMessageDialog(this, "Enhanced Notepad by Harini", "Notepad", JOptionPane.INFORMATION_MESSAGE);
                break;
            case "Exit":
                System.exit(0);
                break;
        }
    }

    private void updateWordCount() {
        String text = txt.getText();
        String[] words = text.trim().split("\\s+");
        int wordCount = words.length == 1 && words[0].isEmpty() ? 0 : words.length;
        wordCountLabel.setText("Word Count: " + wordCount);
    }

    // Placeholder methods for file operations
    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text files", "txt"));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                String content = new String(Files.readAllBytes(file.toPath()));
                txt.setText(content);
                currentFile = file;
                setTitle(file.getName() + " - Notepad");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error opening file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveFile() {
        if (currentFile == null) {
            saveFileAs();
        } else {
            try {
                Files.write(currentFile.toPath(), txt.getText().getBytes());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveFileAs() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text files", "txt"));
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                Files.write(file.toPath(), txt.getText().getBytes());
                currentFile = file;
                setTitle(file.getName() + " - Notepad");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveAsPDF() {
        // Save as PDF logic
    }

    private void changeFontSize() {
        String sizeStr = JOptionPane.showInputDialog(this, "Enter font size:", "Font Size", JOptionPane.PLAIN_MESSAGE);
        if (sizeStr != null) {
            try {
                int size = Integer.parseInt(sizeStr);
                currentFont = new Font(currentFont.getName(), currentFont.getStyle(), size);
                txt.setFont(currentFont);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid font size.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void changeFontColor() {
        Color color = JColorChooser.showDialog(this, "Choose Font Color", txt.getForeground());
        if (color != null) {
            txt.setForeground(color);
        }
    }

    private void changeBackgroundColor() {
        Color color = JColorChooser.showDialog(this, "Choose Background Color", txt.getBackground());
        if (color != null) {
            txt.setBackground(color);
        }
    }

    private void toggleDarkMode() {
        darkMode = !darkMode;
        if (darkMode) {
            txt.setBackground(Color.DARK_GRAY);
            txt.setForeground(Color.WHITE);
            getContentPane().setBackground(Color.DARK_GRAY);
        } else {
            txt.setBackground(Color.WHITE);
            txt.setForeground(Color.BLACK);
            getContentPane().setBackground(Color.WHITE);
        }
    }

    private void findAndReplace() {
        String search = JOptionPane.showInputDialog(this, "Enter text to find:");
        String replace = JOptionPane.showInputDialog(this, "Enter text to replace with:");
        if (search != null && replace != null) {
            txt.setText(txt.getText().replaceAll(search, replace));
        }
    }

    private void autoCorrect() {
        String text = txt.getText();
        text = text.replace("hte", "the");  // Example typo fix
        txt.setText(text);
    }

    private void importSpeech() {
        File speechFile = new File("recognized_text.txt");
        if (!speechFile.exists()) {
            JOptionPane.showMessageDialog(this, "Speech file not found! Please run the Python script first.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(speechFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                txt.append(line + "\n");
            }
            JOptionPane.showMessageDialog(this, "Speech imported successfully!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error reading speech file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Clipboard operations (cut, copy, paste)
    private void cutText() {
        String selectedText = txt.getSelectedText();
        if (selectedText != null) {
            StringSelection stringSelection = new StringSelection(selectedText);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
            txt.replaceSelection("");
        }
    }

    private void copyText() {
        String selectedText = txt.getSelectedText();
        if (selectedText != null) {
            StringSelection stringSelection = new StringSelection(selectedText);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
        }
    }

    private void pasteText() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        try {
            String clipboardText = (String) clipboard.getData(DataFlavor.stringFlavor);
            txt.insert(clipboardText, txt.getCaretPosition());
        } catch (UnsupportedFlavorException | IOException e) {
            JOptionPane.showMessageDialog(this, "Error pasting text: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new EnhancedNotePad();
    }
}
