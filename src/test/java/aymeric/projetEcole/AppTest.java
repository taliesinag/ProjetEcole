package aymeric.projetEcole;

import java.awt.EventQueue;
import java.sql.Date;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


import beans.Emprunt;
import controller.Controller;
import helpers.PDO;
import ihm.IhmMain;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
	public static void main(String[] args) {
		int table[] = {1,2,3,4,5,6,7,8,9};
		for(int i = 0, j = table.length; i<j; i++ ) {
			System.out.println(table[i]);
		};
	}
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
}
