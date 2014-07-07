/**
 * 
 */
package com.larry.biometrics.ui;

import junit.framework.TestCase;

/**
 * @author Otieno Lawrence
 *
 */
public class BiometricsTrayTest extends TestCase{
	private MainWindow dummyWindow;

	@Override
	public void setUp() {

		dummyWindow = new MainWindow();
	}
	
	
	/**
	 * Test create new instance. Should throw exception if Iang argument is <code>null</code>.
	 */
	public void testNewError() {

		try {
			new BiometricsTray( null );
			fail( "IllegalArgumentException was expected" );
		} catch( Throwable e ) {
			assertTrue( "Expected IllegalArgumentException got: " + e.getClass(),
					e instanceof IllegalArgumentException );
		}

	}

	public void testNew() {
		BiometricsTray biometricsTray = new BiometricsTray( dummyWindow );

		// test that the basic GUI was created.
		assertNotNull( biometricsTray.trayMenu);
		assertNotNull( biometricsTray.showHideWindowItem );
		assertNotNull( biometricsTray.quitItem );

	}
}
