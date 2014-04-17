/**
 * 
 */
package com.larry.biometrics.ui;

import static javax.swing.GroupLayout.DEFAULT_SIZE;
import static javax.swing.GroupLayout.PREFERRED_SIZE;
import static javax.swing.GroupLayout.Alignment.LEADING;
import static javax.swing.GroupLayout.Alignment.TRAILING;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.Group;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;

/**
 * @author USER
 * 
 */
public class InfoDialog {
	/** The dialog to show. */
	protected JDialog dialog;

	/**
	 * Create a new Dialog.
	 * 
	 * @param baseName
	 *            the base name of the resource bundle, a fully qualified class
	 *            name.
	 * @param locale
	 *            the locale for which a resource bundle is desired.
	 * @param font
	 *            the font to use for the components.
	 */
	public InfoDialog(String baseName, Font font) {

		initComponents();
		initText(baseName, font);
	}

	private void initComponents() {

		headerLabel = new JLabel();
		headerLabel.setBackground(new java.awt.Color(27, 51, 86));
		// headerLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource(
		// "/images/tt_subheader_logo.png")));
		headerLabel.setText(" ");
		headerLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
		headerLabel.setAlignmentX(1.0F);
		headerLabel.setAlignmentY(1.0F);
		headerLabel.setFocusable(false);
		headerLabel.setOpaque(true);

		messageLabel = new JLabel();

		// remindMeCheckBox = new JCheckBox();

		closeButton = new JButton();
		closeButton.setAction(new AbstractAction() {

			/** Comment for <code>serialVersionUID</code> */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				closeButtonAction();
			}
		});

		dialog = new JDialog();
		dialog.addWindowListener(new WindowAdapter() {

			public void windowClosed(WindowEvent e) {
				closeButtonAction();
			}
		});

		GroupLayout layout = new GroupLayout(dialog.getContentPane());
		dialog.getContentPane().setLayout(layout);
		layout.setHorizontalGroup(createHorizontalGroup(layout));
		layout.setVerticalGroup(createVerticalGroup(layout));
		dialog.pack();
		dialog.setLocationRelativeTo(null);
	}

	private Group createHorizontalGroup(GroupLayout layout) {
		return layout
				.createParallelGroup(LEADING)
				//
				.addComponent(headerLabel, DEFAULT_SIZE, 494, Short.MAX_VALUE)
				//
				.addGroup(layout.createSequentialGroup()//
						// .addContainerGap()//
						// .addComponent( remindMeCheckBox,
						// javax.swing.GroupLayout.DEFAULT_SIZE, 470,
						// Short.MAX_VALUE )//
						.addContainerGap())
				//
				.addGroup(
						layout.createSequentialGroup()
								//
								.addContainerGap()
								//
								.addComponent(messageLabel, DEFAULT_SIZE, 470,
										Short.MAX_VALUE)//
								.addContainerGap())//
				.addGroup(TRAILING, layout.createSequentialGroup()//
						.addContainerGap(435, Short.MAX_VALUE)//
						.addComponent(closeButton).addContainerGap());
	}

	private Group createVerticalGroup(GroupLayout layout) {
		return layout
				.createParallelGroup(Alignment.LEADING)
				//
				.addGroup(
						layout.createSequentialGroup()
								//
								.addComponent(headerLabel, PREFERRED_SIZE, 17,
										PREFERRED_SIZE)//
								.addGap(18, 18, 18)//
								.addComponent(messageLabel, PREFERRED_SIZE, 57,
										PREFERRED_SIZE)//
								// .addGap( 18, 18, 18 )//
								// .addComponent( remindMeCheckBox )//
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)//
								.addComponent(closeButton)//
								.addContainerGap(DEFAULT_SIZE, Short.MAX_VALUE));
	}

	/**
	 * Action for the {@link #closeButton}.
	 */
	protected void closeButtonAction() {
		setVisible(false);
	}

	protected void initText(String baseName, Font font) {

		ResourceBundle bundle = ResourceBundle.getBundle(baseName);

		setFont(font, messageLabel,
		// remindMeCheckBox,
				closeButton);
		messageLabel.setText(getString("message.reminder.tray", bundle));
		// remindMeCheckBox.setText( getString( "message.reminder.again", bundle
		// ) );
		closeButton.setText(getString("common.ok", bundle));
		dialog.pack();
	}

	/**
	 * Shows or hides the {@link #dialog} dependent on the parameter.
	 * 
	 * @param visible
	 *            if <code>true</code>, makes the {@link #dialog} visible.
	 */
	public void setVisible(boolean visible) {
		this.dialog.setVisible(visible);
	}

	/**
	 * Sets the title of the Dialog.
	 * 
	 * @param title
	 *            the title displayed in the dialog's border; a null value
	 *            results in an empty title
	 */
	public void setTitle(String title) {
		this.dialog.setTitle(title);
	}

	/**
	 * Gets a string for the given key from a resource bundle.
	 * 
	 * @param key
	 *            the key for the desired string
	 * @param bundle
	 *            the bundle containing locale-specific resources.
	 * @return the string for the given key or 'MISSING:key' if the bundle does
	 *         not contain the key.
	 */
	protected static String getString(String key, ResourceBundle bundle) {
		if (bundle != null && bundle.containsKey(key)) {
			return bundle.getString(key);
		}
		return "MISSING:" + key;
	}

	/**
	 * Set component font.
	 * 
	 * @param font
	 *            the font to set.
	 * @param components
	 *            the component to set font on.
	 */
	protected static void setFont(Font font, JComponent... components) {
		for (JComponent component : components) {
			component.setFont(font);
		}
	}

	/** Label with TraceTracker logo. */
	private JLabel headerLabel;

	/** Info message shown. */
	private JLabel messageLabel;

	// /** Check if dialog should be shown next time. */
	// private JCheckBox remindMeCheckBox;

	/** Close the dialog. */
	private JButton closeButton;
}
