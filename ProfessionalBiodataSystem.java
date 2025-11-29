package codeshere.experiments; // Comment this out if running without folder structure

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.io.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.print.*;

/**
 * Professional Biodata Management System
 * A comprehensive Java Swing application for creating and managing professional biodata
 * * Features:
 * - Dark Modern UI
 * - Input validation
 * - Professional biodata generation
 * - Data export & Print
 * * @version 3.0 (Dark Theme Fixed)
 */
public class ProfessionalBiodataSystem extends JFrame {
    
    // UI Components
    private JTextField nameField, ageField, regdField, phoneField, emailField;
    private JTextField collegeField, cgpaField, linkedinField, githubField;
    private JComboBox<String> branchBox, semesterBox, genderBox;
    private JTextArea addressArea, aboutArea, achievementsArea;
    private JCheckBox[] skillsBoxes, hobbiesBoxes;
    private JTextField otherSkillsField, otherHobbiesField;
    private JLabel photoLabel;
    private JButton photoButton, submitButton, clearButton, exportButton;
    private String photoPath = "";
    
    // Data validation patterns
    private final Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private final Pattern phonePattern = Pattern.compile("^[6-9]\\d{9}$");
    
    // UI Colors (Dark Theme)
    private final Color PRIMARY_COLOR = new Color(25, 25, 35);
    private final Color SECONDARY_COLOR = new Color(40, 44, 60);
    private final Color ACCENT_COLOR = new Color(52, 152, 219);
    private final Color SUCCESS_COLOR = new Color(46, 204, 113);
    private final Color ERROR_COLOR = new Color(231, 76, 60);
    private final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 32);
    private final Font LABEL_FONT = new Font("Segoe UI", Font.BOLD, 14);
    private final Font INPUT_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    
    public ProfessionalBiodataSystem() {
        initializeUI();
        setupEventHandlers();
        setVisible(true);
    }
    
    private void initializeUI() {
        setTitle("Professional Biodata Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1000, 700));
        
        // Create main panel with gradient background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                GradientPaint gradient = new GradientPaint(
                    0, 0, PRIMARY_COLOR,
                    getWidth(), getHeight(), SECONDARY_COLOR
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout());
        
        // Create scrollable content panel
        JPanel contentPanel = createContentPanel();
        
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        // Make scroll pane transparent
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        add(mainPanel);
    }
    
    private JPanel createContentPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        
        // Header
        panel.add(createHeaderPanel());
        panel.add(Box.createVerticalStrut(30));
        
        // Main form container
        JPanel formContainer = new JPanel();
        formContainer.setLayout(new BoxLayout(formContainer, BoxLayout.Y_AXIS));
        formContainer.setOpaque(false);
        
        // Sections
        formContainer.add(createPersonalInfoSection());
        formContainer.add(Box.createVerticalStrut(20));
        formContainer.add(createAcademicInfoSection());
        formContainer.add(Box.createVerticalStrut(20));
        formContainer.add(createContactInfoSection());
        formContainer.add(Box.createVerticalStrut(20));
        formContainer.add(createSkillsSection());
        formContainer.add(Box.createVerticalStrut(20));
        formContainer.add(createAdditionalInfoSection());
        formContainer.add(Box.createVerticalStrut(30));
        formContainer.add(createButtonsPanel());
        
        panel.add(formContainer);
        return panel;
    }
    
    private JPanel createHeaderPanel() {
        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BorderLayout());
        
        JLabel title = new JLabel("Professional Biodata System", SwingConstants.CENTER);
        title.setFont(TITLE_FONT);
        title.setForeground(Color.WHITE);
        
        JLabel subtitle = new JLabel("Create Your Professional Profile", SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        subtitle.setForeground(new Color(255, 255, 255, 180));
        
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(subtitle);
        
        header.add(titlePanel, BorderLayout.CENTER);
        return header;
    }
    
    private JPanel createPersonalInfoSection() {
        JPanel section = createSection("Personal Information");
        JPanel grid = new JPanel(new GridBagLayout());
        grid.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Photo section
        JPanel photoPanel = new JPanel(new BorderLayout());
        photoPanel.setOpaque(false);
        photoLabel = new JLabel();
        photoLabel.setPreferredSize(new Dimension(120, 160));
        photoLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY, 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        photoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        photoLabel.setText("<html><center>Click to<br>Add Photo</center></html>");
        photoLabel.setOpaque(true);
        photoLabel.setBackground(Color.WHITE);
        
        photoButton = createStyledButton("Upload Photo", ACCENT_COLOR);
        photoPanel.add(photoLabel, BorderLayout.CENTER);
        photoPanel.add(photoButton, BorderLayout.SOUTH);
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridheight = 3;
        grid.add(photoPanel, gbc);
        
        // Form fields
        gbc.gridheight = 1;
        gbc.gridx = 1;
        nameField = createStyledTextField();
        addFormField(grid, "Full Name *", nameField, gbc, 1, 0);
        
        ageField = createStyledTextField();
        addFormField(grid, "Age *", ageField, gbc, 2, 0);
        
        genderBox = new JComboBox<>(new String[]{"Select Gender", "Male", "Female", "Other"});
        styleComboBox(genderBox);
        addFormField(grid, "Gender", genderBox, gbc, 1, 1);
        
        section.add(grid, BorderLayout.CENTER);
        return section;
    }
    
    private JPanel createAcademicInfoSection() {
        JPanel section = createSection("Academic Information");
        JPanel grid = new JPanel(new GridBagLayout());
        grid.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        regdField = createStyledTextField();
        addFormField(grid, "Registration Number *", regdField, gbc, 0, 0);
        
        String[] branches = {
            "Select Branch", "Computer Science & Engineering", "CSE - AI & Machine Learning",
            "CSE - Data Science", "Information Technology", "Electronics & Communication",
            "Electrical & Electronics", "Mechanical Engineering", "Civil Engineering"
        };
        branchBox = new JComboBox<>(branches);
        styleComboBox(branchBox);
        addFormField(grid, "Branch *", branchBox, gbc, 1, 0);
        
        String[] semesters = {
            "Select Semester", "1st Semester", "2nd Semester", "3rd Semester",
            "4th Semester", "5th Semester", "6th Semester", "7th Semester", "8th Semester"
        };
        semesterBox = new JComboBox<>(semesters);
        styleComboBox(semesterBox);
        addFormField(grid, "Current Semester *", semesterBox, gbc, 0, 1);
        
        cgpaField = createStyledTextField();
        addFormField(grid, "CGPA", cgpaField, gbc, 1, 1);
        
        collegeField = createStyledTextField();
        addFormField(grid, "College/University", collegeField, gbc, 0, 2);
        
        section.add(grid, BorderLayout.CENTER);
        return section;
    }
    
    private JPanel createContactInfoSection() {
        JPanel section = createSection("Contact Information");
        JPanel grid = new JPanel(new GridBagLayout());
        grid.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        phoneField = createStyledTextField();
        addFormField(grid, "Mobile Number *", phoneField, gbc, 0, 0);
        
        emailField = createStyledTextField();
        addFormField(grid, "Email Address *", emailField, gbc, 1, 0);
        
        linkedinField = createStyledTextField();
        addFormField(grid, "LinkedIn Profile", linkedinField, gbc, 0, 1);
        
        githubField = createStyledTextField();
        addFormField(grid, "GitHub Profile", githubField, gbc, 1, 1);
        
        addressArea = createStyledTextArea(3);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.BOTH;
        grid.add(createFieldPanel("Home Address", addressArea), gbc);
        
        section.add(grid, BorderLayout.CENTER);
        return section;
    }
    
    private JPanel createSkillsSection() {
    JPanel section = createSection("Skills & Interests");
    
    JPanel mainContent = new JPanel();
    mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
    mainContent.setOpaque(false);
    
    // --- 1. Technical Skills Subsection ---
    JPanel skillsPanel = new JPanel(new BorderLayout(0, 15));
    skillsPanel.setOpaque(false);
    skillsPanel.setBorder(createSubsectionBorder("Technical Skills"));
    
    JPanel skillsGrid = new JPanel(new GridLayout(3, 3, 20, 15));
    skillsGrid.setOpaque(false);
    skillsGrid.setBackground(SECONDARY_COLOR);
    skillsGrid.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    
    String[] skills = {
        "Java Programming", "Python Programming", "Web Development",
        "Data Structures", "Database Management", "Machine Learning",
        "Android Dev", "Software Testing", "Cloud Computing"
    };
    
    skillsBoxes = new JCheckBox[skills.length];
    for (int i = 0; i < skills.length; i++) {
        skillsBoxes[i] = createStyledCheckBox(skills[i]);
        skillsGrid.add(skillsBoxes[i]);
    }
    
    // Other Skills Field
    otherSkillsField = createStyledTextField();
    JPanel otherSkillsPanel = createFieldPanel("Other Skills (Optional):", otherSkillsField);
    otherSkillsPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 0, 15));
    
    skillsPanel.add(skillsGrid, BorderLayout.CENTER);
    skillsPanel.add(otherSkillsPanel, BorderLayout.SOUTH);
    
    // --- 2. Hobbies Subsection ---
    JPanel hobbiesPanel = new JPanel(new BorderLayout(0, 15));
    hobbiesPanel.setOpaque(false);
    hobbiesPanel.setBorder(createSubsectionBorder("Hobbies & Interests"));
    
    JPanel hobbiesGrid = new JPanel(new GridLayout(3, 3, 20, 15));
    hobbiesGrid.setOpaque(false);
    hobbiesGrid.setBackground(SECONDARY_COLOR);
    hobbiesGrid.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    
    String[] hobbies = {
        "Coding", "Reading Books", "Playing Games", "Photography",
        "Traveling", "Painting", "Dancing", "Singing", "Sports"
    };
    
    hobbiesBoxes = new JCheckBox[hobbies.length];
    for (int i = 0; i < hobbies.length; i++) {
        hobbiesBoxes[i] = createStyledCheckBox(hobbies[i]);
        hobbiesGrid.add(hobbiesBoxes[i]);
    }
    
    // Other Hobbies Field
    otherHobbiesField = createStyledTextField();
    JPanel otherHobbiesPanel = createFieldPanel("Other Hobbies (Optional):", otherHobbiesField);
    otherHobbiesPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 0, 15));
    
    hobbiesPanel.add(hobbiesGrid, BorderLayout.CENTER);
    hobbiesPanel.add(otherHobbiesPanel, BorderLayout.SOUTH);
    
    mainContent.add(skillsPanel);
    mainContent.add(Box.createVerticalStrut(25));
    mainContent.add(hobbiesPanel);
    
    section.add(mainContent, BorderLayout.CENTER);
    return section;
}
    
    private JPanel createAdditionalInfoSection() {
        JPanel section = createSection("Additional Information");
        JPanel content = new JPanel(new GridLayout(2, 1, 0, 20));
        content.setOpaque(false);
        
        aboutArea = createStyledTextArea(4);
        JPanel aboutPanel = createFieldPanel("About Me", aboutArea);
        
        achievementsArea = createStyledTextArea(4);
        JPanel achievementsPanel = createFieldPanel("Achievements & Projects", achievementsArea);
        
        content.add(aboutPanel);
        content.add(achievementsPanel);
        section.add(content, BorderLayout.CENTER);
        return section;
    }
    
    private JPanel createButtonsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panel.setOpaque(false);
        
        submitButton = createStyledButton("Generate Biodata", SUCCESS_COLOR);
        clearButton = createStyledButton("Clear All", ERROR_COLOR);
        exportButton = createStyledButton("Export to File", ACCENT_COLOR);
        
        panel.add(submitButton);
        panel.add(clearButton);
        panel.add(exportButton);
        
        return panel;
    }
    
    private JPanel createSection(String title) {
        JPanel section = new JPanel(new BorderLayout());
        section.setOpaque(false);
        
        section.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 50), 1, true),
                title,
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 16),
                new Color(255, 255, 255, 220)
            ),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        section.setBackground(new Color(255, 255, 255, 10)); 
        section.setOpaque(true);
        
        return section;
    }
    
    private Border createSubsectionBorder(String title) {
        return BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 100)),
            title,
            TitledBorder.LEFT,
            TitledBorder.TOP,
            LABEL_FONT,
            Color.WHITE
        );
    }
    
    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(INPUT_FONT);
        field.setPreferredSize(new Dimension(200, 35));
        field.setBackground(new Color(40, 44, 60)); 
        field.setForeground(Color.WHITE);           
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 100, 100)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return field;
    }
    
    private JTextArea createStyledTextArea(int rows) {
        JTextArea area = new JTextArea(rows, 20);
        area.setFont(INPUT_FONT);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBackground(new Color(40, 44, 60));
        area.setForeground(Color.WHITE);
        area.setCaretColor(Color.WHITE);
        
        area.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 100, 100)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        return area;
    }
    
    private JCheckBox createStyledCheckBox(String text) {
    JCheckBox checkbox = new JCheckBox(text) {
        @Override
        protected void paintComponent(Graphics g) {
            // Create a completely opaque background
            Graphics2D g2 = (Graphics2D) g.create();
            
            // FILL ENTIRE BACKGROUND WITH SOLID COLOR
            g2.setColor(SECONDARY_COLOR);
            g2.fillRect(0, 0, getWidth(), getHeight());
            
            int size = 16;
            int yOffset = (getHeight() - size) / 2;
            int textX = size + 8;
            
            // Simple hover effect
            if (getModel().isRollover()) {
                g2.setColor(new Color(255, 255, 255, 20));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
            
            // Draw checkbox
            g2.setColor(Color.WHITE);
            g2.drawRect(0, yOffset, size, size);
            
            // Draw checkmark if selected
            if (isSelected()) {
                g2.setColor(ACCENT_COLOR);
                g2.fillRect(2, yOffset + 2, size - 4, size - 4);
                
                // White checkmark
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(2f));
                g2.drawLine(4, yOffset + 8, 7, yOffset + 12);
                g2.drawLine(7, yOffset + 12, 12, yOffset + 4);
            }
            
            // Draw text
            g2.setColor(Color.WHITE);
            g2.setFont(INPUT_FONT);
            FontMetrics fm = g2.getFontMetrics();
            int textY = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
            g2.drawString(getText(), textX, textY);
            
            g2.dispose();
        }
    };

    // Make it completely opaque with solid background
    checkbox.setOpaque(true);
    checkbox.setBackground(SECONDARY_COLOR);
    checkbox.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
    checkbox.setContentAreaFilled(false);
    checkbox.setBorderPainted(false);
    checkbox.setFocusPainted(false);
    checkbox.setCursor(new Cursor(Cursor.HAND_CURSOR));
    
    return checkbox;
}
    
    private void styleComboBox(JComboBox<String> combo) {
        combo.setFont(INPUT_FONT);
        combo.setPreferredSize(new Dimension(200, 35));
        combo.setBackground(new Color(40, 44, 60)); 
        combo.setForeground(Color.WHITE);
    }
    
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2.setColor(color.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(color.brighter());
                } else {
                    g2.setColor(color);
                }
                
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));
        
        return button;
    }
    
    private JPanel createFieldPanel(String labelText, JComponent component) {
        JPanel panel = new JPanel(new BorderLayout(0, 5));
        panel.setOpaque(false);
        
        JLabel label = new JLabel(labelText);
        label.setFont(LABEL_FONT);
        label.setForeground(Color.WHITE);
        
        panel.add(label, BorderLayout.NORTH);
        if (component instanceof JTextArea) {
            panel.add(new JScrollPane(component), BorderLayout.CENTER);
        } else {
            panel.add(component, BorderLayout.CENTER);
        }
        
        return panel;
    }
    
    private void addFormField(JPanel grid, String labelText, JComponent component, 
                              GridBagConstraints gbc, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        grid.add(createFieldPanel(labelText, component), gbc);
    }
    
    private void setupEventHandlers() {
        photoButton.addActionListener(e -> selectPhoto());
        submitButton.addActionListener(e -> generateBiodata());
        clearButton.addActionListener(e -> clearAllFields());
        exportButton.addActionListener(e -> exportBiodata());
    }
    
    private void selectPhoto() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif"));
        
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            photoPath = selectedFile.getAbsolutePath();
            
            try {
                ImageIcon icon = new ImageIcon(photoPath);
                Image img = icon.getImage().getScaledInstance(100, 130, Image.SCALE_SMOOTH);
                photoLabel.setIcon(new ImageIcon(img));
                photoLabel.setText("");
            } catch (Exception ex) {
                showError("Error loading image: " + ex.getMessage());
            }
        }
    }
    
    private boolean validateInput() {
        StringBuilder errors = new StringBuilder();
        
        if (nameField.getText().trim().isEmpty()) errors.append("• Full Name is required\n");
        if (ageField.getText().trim().isEmpty()) {
            errors.append("• Age is required\n");
        } else {
            try {
                int age = Integer.parseInt(ageField.getText().trim());
                if (age < 16 || age > 100) errors.append("• Age must be between 16 and 100\n");
            } catch (NumberFormatException e) {
                errors.append("• Age must be a valid number\n");
            }
        }
        
        if (regdField.getText().trim().isEmpty()) errors.append("• Registration Number is required\n");
        if (branchBox.getSelectedIndex() == 0) errors.append("• Please select a branch\n");
        if (semesterBox.getSelectedIndex() == 0) errors.append("• Please select current semester\n");
        
        if (phoneField.getText().trim().isEmpty()) {
            errors.append("• Mobile Number is required\n");
        } else if (!phonePattern.matcher(phoneField.getText().trim()).matches()) {
            errors.append("• Please enter a valid 10-digit mobile number\n");
        }
        
        if (emailField.getText().trim().isEmpty()) {
            errors.append("• Email Address is required\n");
        } else if (!emailPattern.matcher(emailField.getText().trim()).matches()) {
            errors.append("• Please enter a valid email address\n");
        }
        
        if (!cgpaField.getText().trim().isEmpty()) {
            try {
                double cgpa = Double.parseDouble(cgpaField.getText().trim());
                if (cgpa < 0.0 || cgpa > 10.0) errors.append("• CGPA must be between 0.0 and 10.0\n");
            } catch (NumberFormatException e) {
                errors.append("• CGPA must be a valid number\n");
            }
        }
        
        if (errors.length() > 0) {
            showError("Please fix the following errors:\n\n" + errors.toString());
            return false;
        }
        
        return true;
    }
    
    private void generateBiodata() {
        if (!validateInput()) return;
        
        JFrame outputFrame = new JFrame("Professional Biodata - " + nameField.getText());
        outputFrame.setSize(800, 900);
        outputFrame.setLocationRelativeTo(this);
        
        JPanel outputPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(240, 248, 255),
                    getWidth(), getHeight(), new Color(230, 230, 250)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        
        outputPanel.setLayout(new BorderLayout());
        outputPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        
        JPanel contentPanel = createBiodataContent();
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        outputPanel.add(scrollPane, BorderLayout.CENTER);
        
        JButton printButton = createStyledButton("Print Biodata", PRIMARY_COLOR);
        printButton.addActionListener(e -> printBiodata(contentPanel));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.add(printButton);
        outputPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        outputFrame.add(outputPanel);
        outputFrame.setVisible(true);
        
        showSuccess("Biodata generated successfully!");
    }
    
    private JPanel createBiodataContent() {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);
        content.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        
        JLabel titleLabel = new JLabel("PROFESSIONAL BIODATA", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(PRIMARY_COLOR);
        
        if (!photoPath.isEmpty()) {
            try {
                ImageIcon icon = new ImageIcon(photoPath);
                Image img = icon.getImage().getScaledInstance(120, 150, Image.SCALE_SMOOTH);
                JLabel photoDisplay = new JLabel(new ImageIcon(img));
                photoDisplay.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
                header.add(photoDisplay, BorderLayout.EAST);
            } catch (Exception ex) {}
        }
        
        header.add(titleLabel, BorderLayout.CENTER);
        content.add(header);
        
        // Sections
        content.add(createOutputSection("Personal Information",
            "Name: " + nameField.getText(),
            "Age: " + ageField.getText(),
            "Gender: " + (genderBox.getSelectedIndex() > 0 ? genderBox.getSelectedItem() : "Not specified")
        ));
        
        content.add(createOutputSection("Academic Information",
            "Registration Number: " + regdField.getText(),
            "Branch: " + (branchBox.getSelectedIndex() > 0 ? branchBox.getSelectedItem() : "Not specified"),
            "Current Semester: " + (semesterBox.getSelectedIndex() > 0 ? semesterBox.getSelectedItem() : "Not specified"),
            "CGPA: " + (cgpaField.getText().trim().isEmpty() ? "Not specified" : cgpaField.getText()),
            "College/University: " + (collegeField.getText().trim().isEmpty() ? "Not specified" : collegeField.getText())
        ));
        
        content.add(createOutputSection("Contact Information",
            "Mobile Number: " + phoneField.getText(),
            "Email Address: " + emailField.getText(),
            "LinkedIn: " + (linkedinField.getText().trim().isEmpty() ? "Not provided" : linkedinField.getText()),
            "GitHub: " + (githubField.getText().trim().isEmpty() ? "Not provided" : githubField.getText()),
            "Address: " + (addressArea.getText().trim().isEmpty() ? "Not provided" : addressArea.getText())
        ));
        
        StringBuilder skills = new StringBuilder();
        for (JCheckBox cb : skillsBoxes) {
            if (cb.isSelected()) {
                if (skills.length() > 0) skills.append(", ");
                skills.append(cb.getText());
            }
        }
        if (!otherSkillsField.getText().trim().isEmpty()) {
            if (skills.length() > 0) skills.append(", ");
            skills.append(otherSkillsField.getText().trim());
        }
        
        StringBuilder hobbies = new StringBuilder();
        for (JCheckBox cb : hobbiesBoxes) {
            if (cb.isSelected()) {
                if (hobbies.length() > 0) hobbies.append(", ");
                hobbies.append(cb.getText());
            }
        }
        if (!otherHobbiesField.getText().trim().isEmpty()) {
            if (hobbies.length() > 0) hobbies.append(", ");
            hobbies.append(otherHobbiesField.getText().trim());
        }
        
        content.add(createOutputSection("Skills & Interests",
            "Technical Skills: " + (skills.length() > 0 ? skills.toString() : "None specified"),
            "Hobbies & Interests: " + (hobbies.length() > 0 ? hobbies.toString() : "None specified")
        ));
        
        if (!aboutArea.getText().trim().isEmpty() || !achievementsArea.getText().trim().isEmpty()) {
            content.add(createOutputSection("Additional Information",
                "About Me: " + (aboutArea.getText().trim().isEmpty() ? "Not provided" : aboutArea.getText()),
                "Achievements & Projects: " + (achievementsArea.getText().trim().isEmpty() ? "Not provided" : achievementsArea.getText())
            ));
        }
        
        JLabel footer = new JLabel("Generated on: " + new SimpleDateFormat("dd MMM yyyy").format(new Date()), SwingConstants.CENTER);
        footer.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        footer.setForeground(Color.GRAY);
        footer.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        content.add(footer);
        
        return content;
    }
    
    private JPanel createOutputSection(String title, String... items) {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setOpaque(false);
        section.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        section.add(titleLabel);
        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setOpaque(true);
        
        for (String item : items) {
            if (item != null && !item.trim().isEmpty()) {
                JLabel itemLabel = new JLabel(item);
                itemLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                itemLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
                contentPanel.add(itemLabel);
            }
        }
        
        section.add(contentPanel);
        return section;
    }
    
    private void clearAllFields() {
        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to clear all fields?", 
            "Clear All Fields", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (result == JOptionPane.YES_OPTION) {
            nameField.setText("");
            ageField.setText("");
            regdField.setText("");
            phoneField.setText("");
            emailField.setText("");
            collegeField.setText("");
            cgpaField.setText("");
            linkedinField.setText("");
            githubField.setText("");
            addressArea.setText("");
            aboutArea.setText("");
            achievementsArea.setText("");
            
            branchBox.setSelectedIndex(0);
            semesterBox.setSelectedIndex(0);
            genderBox.setSelectedIndex(0);
            
            for (JCheckBox cb : skillsBoxes) cb.setSelected(false);
            for (JCheckBox cb : hobbiesBoxes) cb.setSelected(false);
            
            otherSkillsField.setText("");
            otherHobbiesField.setText("");
            
            photoLabel.setIcon(null);
            photoLabel.setText("<html><center>Click to<br>Add Photo</center></html>");
            photoPath = "";
            
            showSuccess("All fields cleared successfully!");
        }
    }
    
    private void exportBiodata() {
    if (!validateInput()) return;
    
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Save Biodata");
    fileChooser.setFileFilter(new FileNameExtensionFilter("Text files", "txt"));
    fileChooser.setSelectedFile(new File(nameField.getText().replaceAll("\\s+", "_") + "_biodata.txt"));
    
    if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
        File file = fileChooser.getSelectedFile();
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.println("PROFESSIONAL BIODATA");
            writer.println("====================");
            writer.println();
            writer.println("Generated on: " + new SimpleDateFormat("dd MMM yyyy 'at' HH:mm:ss").format(new Date()));
            writer.println();
            
            // Personal Information
            writer.println("PERSONAL INFORMATION");
            writer.println("-------------------");
            writer.println("Name: " + nameField.getText());
            writer.println("Age: " + ageField.getText());
            writer.println("Gender: " + (genderBox.getSelectedIndex() > 0 ? genderBox.getSelectedItem() : "Not specified"));
            writer.println();
            
            // Academic Information
            writer.println("ACADEMIC INFORMATION");
            writer.println("--------------------");
            writer.println("Registration Number: " + regdField.getText());
            writer.println("Branch: " + (branchBox.getSelectedIndex() > 0 ? branchBox.getSelectedItem() : "Not specified"));
            writer.println("Current Semester: " + (semesterBox.getSelectedIndex() > 0 ? semesterBox.getSelectedItem() : "Not specified"));
            writer.println("CGPA: " + (cgpaField.getText().trim().isEmpty() ? "Not specified" : cgpaField.getText()));
            writer.println("College/University: " + (collegeField.getText().trim().isEmpty() ? "Not specified" : collegeField.getText()));
            writer.println();
            
            // Contact Information
            writer.println("CONTACT INFORMATION");
            writer.println("-------------------");
            writer.println("Mobile Number: " + phoneField.getText());
            writer.println("Email Address: " + emailField.getText());
            writer.println("LinkedIn: " + (linkedinField.getText().trim().isEmpty() ? "Not provided" : linkedinField.getText()));
            writer.println("GitHub: " + (githubField.getText().trim().isEmpty() ? "Not provided" : githubField.getText()));
            writer.println("Address: " + (addressArea.getText().trim().isEmpty() ? "Not provided" : addressArea.getText()));
            writer.println();
            
            // Skills
            StringBuilder skills = new StringBuilder();
            for (JCheckBox cb : skillsBoxes) {
                if (cb.isSelected()) {
                    if (skills.length() > 0) skills.append(", ");
                    skills.append(cb.getText());
                }
            }
            if (!otherSkillsField.getText().trim().isEmpty()) {
                if (skills.length() > 0) skills.append(", ");
                skills.append(otherSkillsField.getText().trim());
            }
            
            // Hobbies
            StringBuilder hobbies = new StringBuilder();
            for (JCheckBox cb : hobbiesBoxes) {
                if (cb.isSelected()) {
                    if (hobbies.length() > 0) hobbies.append(", ");
                    hobbies.append(cb.getText());
                }
            }
            if (!otherHobbiesField.getText().trim().isEmpty()) {
                if (hobbies.length() > 0) hobbies.append(", ");
                hobbies.append(otherHobbiesField.getText().trim());
            }
            
            writer.println("SKILLS & INTERESTS");
            writer.println("------------------");
            writer.println("Technical Skills: " + (skills.length() > 0 ? skills.toString() : "None specified"));
            writer.println("Hobbies & Interests: " + (hobbies.length() > 0 ? hobbies.toString() : "None specified"));
            writer.println();
            
            // Additional Information
            if (!aboutArea.getText().trim().isEmpty() || !achievementsArea.getText().trim().isEmpty()) {
                writer.println("ADDITIONAL INFORMATION");
                writer.println("----------------------");
                if (!aboutArea.getText().trim().isEmpty()) {
                    writer.println("About Me:");
                    writer.println(aboutArea.getText());
                    writer.println();
                }
                if (!achievementsArea.getText().trim().isEmpty()) {
                    writer.println("Achievements & Projects:");
                    writer.println(achievementsArea.getText());
                }
            }
            
            writer.println();
            writer.println("--- END OF BIODATA ---");
            
            showSuccess("Biodata exported successfully to: " + file.getAbsolutePath());
        } catch (IOException e) {
            showError("Error exporting biodata: " + e.getMessage());
        }
    }
}
    
    private void printBiodata(JPanel contentPanel) {
        try {
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPrintable((graphics, pageFormat, pageIndex) -> {
                if (pageIndex > 0) return Printable.NO_SUCH_PAGE;
                Graphics2D g2d = (Graphics2D) graphics;
                g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
                double scale = Math.min(pageFormat.getImageableWidth() / contentPanel.getWidth(),
                                        pageFormat.getImageableHeight() / contentPanel.getHeight());
                g2d.scale(scale, scale);
                contentPanel.paint(g2d);
                return Printable.PAGE_EXISTS;
            });
            if (job.printDialog()) {
                job.print();
                showSuccess("Biodata sent to printer successfully!");
            }
        } catch (PrinterException e) {
            showError("Error printing biodata: " + e.getMessage());
        }
    }
    
    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ProfessionalBiodataSystem();
        });
    }
}