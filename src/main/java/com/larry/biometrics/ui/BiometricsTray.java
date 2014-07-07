/*
 * Copyright 2010 TraceTracker AS, Norway.
 */

package com.larry.biometrics.ui;

import java.awt.AWTException;
import java.awt.Font;
import java.awt.SystemTray;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.util.Arrays;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

/**
 * 
 * Class for running Biometrics in SystemTray.
 * 
 * <br>
 * 
 */
public class BiometricsTray {

	/** The path to the icon to show in the System tray. */
	public static final String TRAY_LOGO = "/images/logo_64_x_64.png";

	/** Main Window. */
	private final MainWindow mainWindow;

	/** TrayIcon to use. */
	private JTrayIcon trayIcon;

	/** Private. */
	protected BiometricsTray(MainWindow mainWindow) {

		if (mainWindow == null) {
			throw new IllegalArgumentException("null mainWindow");
		}

		this.mainWindow = mainWindow;
		initComponents();
	}

	/**
	 * Initialize the GUI components.
	 */
	private void initComponents() {

		showHideWindowItem = createShowHideWindowItem();
		quitItem = createQuitItem();

		trayMenu = new JPopupMenu();
		trayMenu.setName("trayMenu");

		trayMenu.add(showHideWindowItem);
		trayMenu.addSeparator();
		trayMenu.add(quitItem);

	}

	/**
	 * 
	 * @return a MenuItem that will show or hide the main swing window.
	 */
	protected JMenuItem createShowHideWindowItem() {

		return createMenuItem("showHideWindowItem", "Show/Hide",
				new ActionListener() {

					public void actionPerformed(ActionEvent e) {

						mainWindow.setVisible(!mainWindow.isVisible());
					}
				});
	}

	/**
	 * 
	 * @return a MenuItem that will quit the application.
	 */
	protected JMenuItem createQuitItem() {

		return createMenuItem("quitItem", "Exit", new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainWindow.exitOperation();
			}
		});
	}

	/**
	 * Create a new MenuItem.
	 * 
	 * @param name
	 *            the name to assign to the MenuItem.
	 * 
	 * @param label
	 *            the label on the menu
	 * @param actionListener
	 *            the actionListener to assign to the MenuItem.
	 * @return a new MenuItem.
	 */
	protected JMenuItem createMenuItem(String name, String label,
			ActionListener actionListener) {
		JMenuItem item = new JMenuItem();
		item.setText(label);
		item.setName(name);
		item.addActionListener(actionListener);
		return item;
	}

	/**
	 * Start Biometrics in SystemTray
	 * 
	 * @param showTrayReminder
	 *            <code>true</code> if a dialog should be shown notifying the
	 *            user that the application is running in the system tray.
	 */
	public void showGUI(boolean showTrayReminder) {

		if (!SystemTray.isSupported()) {
			System.out
					.println("SystemTray is not supported by current platform, "
							+ "will show Biometrics without SystemTray support");
			mainWindow.setVisible(true); // will just show the swing window.
			return;
		}

		trayIcon = new JTrayIcon(new javax.swing.ImageIcon(getClass()
				.getResource(TRAY_LOGO)).getImage(), "Systech Biometrics") {

			public void displayMessage(String caption, String text,
					MessageType messageType) {

				String localizedMessage = null;
				/*
				 * TrayStatusAdapter will send text in the format of
				 * resourceKEY:::filename, if text match that, we will displayed
				 * a localized message. This was done because in the context of
				 * the TrayStatusAdapter no localization was possible.
				 */
				if (text != null && text.contains(":::")) {

					String[] split = text.split(":::");
					if (split.length == 2) {
						String i18nResource = split[0];
						String fileName = split[1];
						localizedMessage = String.format(
								getString("trayMessage." + i18nResource),
								fileName);
						System.out.println(localizedMessage);
					}

				}

				super.displayMessage(caption, localizedMessage == null ? text
						: localizedMessage, messageType);
			}

			private String getString(String string) {
				// TODO Auto-generated method stub
				return null;
			}

		};
		trayIcon.setJPopupMenu(trayMenu);
		trayIcon.setImageAutoSize(true);

		final SystemTray tray = SystemTray.getSystemTray();

		try {
			tray.add(trayIcon);

			if (showTrayReminder) {
				InfoDialog info = new InfoDialog("Biometrics",
						getApplicationFont());
				info.setTitle("Systech Biometrics");
				info.setVisible(true);
			}

		} catch (AWTException e) {
			System.out.println("TrayIcon could not be added" + e);
			mainWindow.setVisible(true);
			return; // will just show the swing window.
		}
	}

	private Font getApplicationFont() {
		return new Font("Times New Roman", Font.PLAIN, 11);
	}

	/**
	 * Start Biometrics in SystemTray.
	 * 
	 * @param args
	 *            arguments from console.
	 */
	public static void main(String[] args) {

		System.out.println("Started with arguments: " + Arrays.toString(args));

		try {
			launchIangInSystemTray(args);
		} catch (RuntimeException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Launch the Iang in SystemTray.
	 * 
	 * @param args
	 *            the command line arguments.
	 */
	private static void launchIangInSystemTray(String[] args) {

		final MainWindow mainWindow = new MainWindow();

		// final ImageIcon biometricsIcon = new javax.swing.ImageIcon("");
		// mainWindow.setIconImage(biometricsIcon.getImage());

		final BiometricsTray tray = new BiometricsTray(mainWindow);

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {

				tray.showGUI(true);
			}
		});
	}

	/** Menu shown when user click the SystemTray icon. */
	protected JPopupMenu trayMenu;

	/** MenuItem for exiting the application. */
	protected JMenuItem quitItem;

	/** MenuItem for showing or hiding MainWindow. */
	protected JMenuItem showHideWindowItem;

	/** Default Menu Item font. */
	private final Font defaultItemFont = new JMenuItem().getFont();

	/**
	 * Create a listener to update {@link #showHideWindowItem} label when
	 * closing the window.
	 * 
	 * @return a listener that will update {@link #showHideWindowItem} label.
	 */
	protected WindowAdapter createWindowClosingListener() {
		return new java.awt.event.WindowAdapter() {
			/**
			 * {@inheritDoc}
			 * 
			 * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
			 */
			public void windowClosing(java.awt.event.WindowEvent evt) {
				System.out.println("Hiding data input window in tray.");
			}
		};
	}

}
