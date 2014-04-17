/*
 * Copyright 2010 TraceTracker AS, Norway.
 */

package com.larry.biometrics.ui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.TrayIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JDialog;
import javax.swing.JPopupMenu;
import javax.swing.JWindow;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

/**
 * 
 * Add support for attaching a JPopupMenu to a TrayIcon.
 * 
 * <br>
 *  
 */
public class JTrayIcon extends TrayIcon {

	/** Popup menu to show in the tray. */
	private JPopupMenu jpopupMenu;

	/** Window to place the popup menu in. */
	private static Container window;

	/** Application started in a Windows OS. */
	static final boolean IS_WINDOWS = System.getProperty( "os.name" ).startsWith( "Windows" );

	static {
		// Hack, JWindow does not hide when loosing focus in Windows.
		// JDialog is shown in the taskbar when running in Linux.
		if( IS_WINDOWS ) {
			window = new JDialog( (Frame) null );
			((JDialog) window).setAlwaysOnTop( true );
			((JDialog) window).setUndecorated( true );
		} else {
			window = new JWindow( (Frame) null );
			((JWindow) window).setAlwaysOnTop( true );
		}
	}

	/**
	 * Creates a <code>TrayIcon</code> with the specified image and tooltip text.
	 * 
	 * @param image the <code>Image</code> to be used
	 * @param tooltip the string to be used as tooltip text; if the value is <code>null</code> no tooltip is shown
	 */
	public JTrayIcon( Image image, String tooltip ) {
		super( image, tooltip );
		addMouseListener( trayIconMouseListener );
	}

	/**
	 * Sets the popup menu for this <code>TrayIcon</code>. If <code>popup</code> is <code>null</code>, no popup menu will
	 * be associated with this <code>TrayIcon</code>.
	 * 
	 * @param popupMenu the popup menu to set.
	 */
	public void setJPopupMenu( JPopupMenu popupMenu ) {

		if( this.jpopupMenu != null ) {
			this.jpopupMenu.removePopupMenuListener( popupMenuListener );
		}

		this.jpopupMenu = popupMenu;
		this.jpopupMenu.addPopupMenuListener( popupMenuListener );
	}

	/**
	 * @return the popup menu set.
	 */
	public JPopupMenu getJPopupMenu() {
		return jpopupMenu;
	}

	/**
	 * Delegate mouse events from the TrayIcon to the popup menu.
	 */
	protected MouseListener trayIconMouseListener = new MouseAdapter() {

		public void mousePressed( MouseEvent e ) {
			showMenu( e );
		}

		public void mouseReleased( MouseEvent e ) {
			showMenu( e );
		}
	};

	/**
	 * Will show the popup menu if the event is {@link MouseEvent#isPopupTrigger()} and the {@link #jpopupMenu} is set.
	 * 
	 * @param e the mouse event.
	 */
	protected void showMenu( MouseEvent e ) {
		if( e.isPopupTrigger() && jpopupMenu != null ) {
			Dimension dim = jpopupMenu.getPreferredSize();
			showMenu( e.getX(), e.getY() - dim.height );
		}
	}

	/**
	 * Show the popup menu at given coordinates.
	 * 
	 * @param x the x coordinates for the menu.
	 * @param y the y coordinates for the menu.
	 */
	private void showMenu( int x, int y ) {
		window.setLocation( x, y );
		window.setVisible( true );
		jpopupMenu.show( window, 0, 0 );

		if( window instanceof JWindow ) {
			((JWindow) window).toFront();
		} else {
			((JDialog) window).toFront();
		}
	}

	/**
	 * Show / Hide the {@link #window}.
	 */
	private static PopupMenuListener popupMenuListener = new PopupMenuListener() {

		@Override
		public void popupMenuWillBecomeVisible( PopupMenuEvent e ) {
		}

		@Override
		public void popupMenuWillBecomeInvisible( PopupMenuEvent e ) {
			window.setVisible( true );
		}

		@Override
		public void popupMenuCanceled( PopupMenuEvent e ) {
			window.setVisible( false );
		}
	};

}
