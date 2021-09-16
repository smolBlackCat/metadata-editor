package com.moura.components;

import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 * A class responsible for showing the user all the fields that a file contains.
 */
public class MetadataFields {
	
	private Map<String, JComponent[]> fields = new HashMap<>();
	private JPanel fieldsPanel = new JPanel();
	private JScrollPane mainPane = new JScrollPane(fieldsPanel);
	private JFrame frame;

	/**
	 * The default class constructor.
	 * @param frame The window this component will appear.
	 */
	public MetadataFields(JFrame frame) {
		fieldsPanel.setLayout(new GridLayout(0, 1));
		this.frame = frame;
	}

	/**
	 * Sets all the metadata fields in the component through a map.
	 * @param metadata Map of strings containing all the fields and its values.
	 */
	public void setupFields(Map<String, String> metadata) {
		for (Map.Entry<String, String> keyValue: metadata.entrySet()) {
			JLabel currentLabel = new JLabel(keyValue.getKey());
			JTextField currentTextField = new JTextField(keyValue.getValue());
			fieldsPanel.add(currentLabel);
			fieldsPanel.add(currentTextField);
			fields.put(keyValue.getKey(), new JComponent[] {currentLabel, currentTextField});
		}
		fieldsPanel.revalidate();
	}

	/**
	 * Adds a new field to the component.
	 * @param key A String representing the field.
	 * @param value A String representing the value of the field.
	 * @return True when it was possible to add this new field. False when this
	 * field already exists.
	 */
	public boolean addField(String key, String value) {
		if (fields.get(key) == null) {
			JLabel label = new JLabel(key);
			JTextField textField = new JTextField(value);
			fieldsPanel.add(label);
			fieldsPanel.add(textField);
			fieldsPanel.revalidate();
			fields.put(key, new JComponent[] {label, textField});
			return true;
		}
		return false;
	}

	/**
	 * Adds the component to the frame.
	 */
	public void add() {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10, 0, 0, 10);
		frame.add(mainPane, c);
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception err) {
			err.printStackTrace();
		}

		JFrame frame = new JFrame("MetadataFields test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridBagLayout());
		
		MetadataFields metadataFields = new MetadataFields(frame);
		JButton button = new JButton("Add a field");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String randomKey = Integer.toString((int) (Math.random() * 50));
				String randomValue = Integer.toString((int) (Math.random() * 50));
				metadataFields.addField(randomKey, randomValue);
			}
		});
		metadataFields.add();

		frame.add(button);

		frame.pack();
		frame.setVisible(true);
	}
}