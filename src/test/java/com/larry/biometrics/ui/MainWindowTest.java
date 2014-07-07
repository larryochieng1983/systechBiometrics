/**
 * 
 */
package com.larry.biometrics.ui;

import org.jmock.cglib.MockObjectTestCase;

/**
 * @author Otieno Lawrence
 * 
 */
public class MainWindowTest extends MockObjectTestCase {

	private MainWindow dummyWindow;

	@Override
	public void setUp() throws Exception {
		dummyWindow = new MainWindow();
	}

	public void testNew() throws Exception {
		// test that the basic GUI was created.
		assertNotNull(dummyWindow.getConfig());
		assertNotNull(dummyWindow.getApplicationInfo());
		assertNotNull(dummyWindow.getBiometricsUtil());
	}

	public void testNothing() {
		// Have at least one test method, now that the others are disabled...
	}
}
