package test.gov.nih.nci.bda.certification.listener;

import gov.nih.nci.bda.certification.domain.TargetLookup;
import gov.nih.nci.bda.certification.listener.TaskListener;
import junit.framework.Test;
import junit.framework.TestSuite;
import junit.framework.TestCase;
import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.Project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;

/**
 * TaskListener Tester.
 *
 * @author <Authors name>
 * @since <pre>07/23/2010</pre>
 * @version 1.0
 */
public class TaskListenerTest extends TestCase {
    public TaskListenerTest(String name) {
        super(name);
    }

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    public static Test suite() {
        return new TestSuite(TaskListenerTest.class);
    }

    static String expected = "saved.properties" ;


    public static BuildEvent getMockBuildEvent() {
        Project p = new Project();
        p.setProperty("gov.nih.nci.bda.certification.listener.TaskListener.propertysavefile",expected);
        return new BuildEvent(p);

    }

    public void testWriteProperty() throws IOException {
        TaskListener target = new TaskListener();
        String name = "myproperty";
        String value = "myvalue" ;
        BuildEvent mock =   getMockBuildEvent();
        target.buildStarted(mock);

        target.writeProperty("x","y");
        target.writeProperty(name,value);
        target.writeProperty("z","abc");

        boolean found = false ;

        FileReader r = null ;
        String fileName = target.getPropertyFilename(mock.getProject());


        try
        {
            target.getWriter(mock.getProject()).flush();
            r = new FileReader(fileName);
            BufferedReader br = new BufferedReader(r);
            String line = br.readLine();

            while(line != null)
            {
                found = line.equals(name + "=" + value);
                if (found)
                {
                    break;
                }
                line = br.readLine();
            }


            
        }
        catch(Exception ex)
        {
            if (r != null)
            {
                r.close();
            }
            fail(ex.toString());
        }
        assertTrue(found);
    }

    // proves that the getWriter returns a BufferedWriter
    public void testGetFileWriter() {
        TaskListener target = new TaskListener();
        Project p = getMockBuildEvent().getProject() ;
        target.setProject (p);
        BufferedWriter w = target.getWriter(p);
        assertNotNull(w);
    }

    public void testGetPropertyFilename() {

        TaskListener target = new TaskListener();
        Project p = getMockBuildEvent().getProject() ;
        target.setProject (p);
        String fileName = target.getPropertyFilename(p);
        assertNotNull(fileName);

    }

    public void testBuildStartedSetsFilename() {
        TaskListener target = new TaskListener();
        BuildEvent mock =   getMockBuildEvent();

        target.buildStarted(mock);
        String actual = target.getPropertyFilename(mock.getProject());
        assertEquals(expected,actual);
    }

    public void testBuildInitializedSetsFilename() {
        TaskListener target = new TaskListener();

        Project p = getMockBuildEvent().getProject() ;
        target.setProject (p);

        String actual = target.getPropertyFilename(p);
        assertEquals(expected,actual);
    }

    public void testBuildStartedWithNoProperty() {
        TaskListener target = new TaskListener();

        Project p = new Project();
        target.setProject (p);

        assertNull(target.getPropertyFilename(p));
    }

    public void testAddPropertyValueThrowExceptionOnNullPropertyName() {
        TaskListener target = new TaskListener();
        Exception caught = null;

        try {
            target.addPropertyValue(null, "propertyvalue");
        }
        catch (Exception ex) {
            caught = ex;
        }

        assertNotNull(caught);

    }

    public void testAddPropertyValueThrowExceptionOnStringEmptyPropertyName() {
        TaskListener target = new TaskListener();
        Exception caught = null;

        try {
            target.addPropertyValue("", "propertyvalue");
        }
        catch (Exception ex) {
            caught = ex;
        }

        assertNotNull(caught);

    }

    public void testAddPropertyValueThrowExceptionOnNullValue() {
        TaskListener target = new TaskListener();
        Exception caught = null;

        try {
            target.addPropertyValue("propertyname", null);
        }
        catch (Exception ex) {
            caught = ex;
        }

        assertNotNull(caught);

    }

    public void testAddPropertyValueThrowExceptionOnStringEmptyValue() {
        TaskListener target = new TaskListener();
        Exception caught = null;

        try {
            target.addPropertyValue("propertyname", null);
        }
        catch (Exception ex) {
            caught = ex;
        }

        assertNotNull(caught);

    }

    public void testAddPropertyValueAddsAndRetrievesCorrectly() throws Exception {

        TaskListener target = new TaskListener();
        String key = "mykey";
        String value = "myvalue";
        target.addPropertyValue(key, value);
        assertEquals(1, target.getPropertyValues().size());
        String actual = target.getPropertyValues().get(key);
        assertEquals(value, actual);

    }

    public void testPropertyValuesNotNull() {

//        proves that propertyValues is not null, even though nothing has been added

        TaskListener target = new TaskListener();
        assertNotNull(target.getPropertyValues());

    }

    public void testSameKeyTwice() throws Exception {

        Exception caught = null;
        TaskListener target = new TaskListener();
        String key = "mykey";
        String value = "myvalue";
        target.addPropertyValue(key, value);
        try {
            target.addPropertyValue(key, value);
        }
        catch (Exception ex) {
            caught = ex;
        }

        assertNotNull(caught);


    }


    // this test tests the shouldSave method to check if it
    // both returns false and does not throw an exception if
    // there are no saveProperties
    public void testShouldSaveEmptyFalse() {

        TaskListener target = new TaskListener();
        TargetLookup lookup = new TargetLookup();
        assertFalse(target.ShouldSave("x"));

    }

    // this test tests the shouldSave method to check if it
    // returns true for a simple property name match when
    // there is only one in the collection of saveproperties
    public void testShouldSaveSimpleTrue() {

        TaskListener target = new TaskListener();
        target.setPropertiesToSaveExpression("x");
        assertTrue(target.ShouldSave("x"));

    }

    // this test tests the shouldSave method to check if it
    // returns true for a property name match
    // if there are more than one properties
    public void testShouldSaveMultiTrue() {

        TaskListener target = new TaskListener();
        target.setPropertiesToSaveExpression("y,x");
        assertTrue(target.ShouldSave("x"));
    }

    // this test tests the shouldSave method to check if it
    // returns true for a simple property name match when
    // there is only one in the collection of saveproperties
    public void testShouldSaveReqexTrue() {

        TaskListener target = new TaskListener();
        target.setPropertiesToSaveExpression(".*application\\.url");
        assertTrue(target.ShouldSave("application.url"));

    }

    // this test tests the shouldSave method to check if it
    // returns true for a simple property name match when
    // there is only one in the collection of saveproperties
    public void testShouldSaveReqexTrue2() {

        TaskListener target = new TaskListener();
        target.setPropertiesToSaveExpression(".*application\\.url");
        assertTrue(target.ShouldSave("jboss.application.url"));

    }

    public void testSavePropertiesNoException() throws Exception {
        TaskListener target = new TaskListener();
        Project p = new Project();
        target.saveProperties(p);

    }


    // proves that if the property 'x' is requested to be
    // saved that calling saveProperties saves it
    public void testSavePropertiesSaveSimple() throws Exception {

        TaskListener target = new TaskListener();
        assertEquals(0, target.getPropertyValues().size());
        String propertyName = "x" ;
        String propertyValue = "xyzxyzxyz" ;
        Project p = new Project();
        p.setProperty(propertyName,propertyValue);
        p.setProperty("gov.nih.nci.bda.certification.listener.TaskListener.propertysavefile","x");
        target.setPropertiesToSaveExpression(propertyName);
        target.saveProperties(p);
        assertEquals(1, target.getPropertyValues().size());
        assertTrue(target.getPropertyValues().containsKey(propertyName));
        assertEquals(propertyValue,target.getPropertyValues().get(propertyName));

    }

    // proves that if the property 'x' is requested to be
    // saved that calling saveProperties saves it
    public void testSavePropertiesSaveWildcard() throws Exception {

        TaskListener target = new TaskListener();
        assertEquals(0, target.getPropertyValues().size());
        String propertyName = ".*application.url" ;
        String propertyValue = "http://microsoft.com" ;
        Project p = new Project();
        target.setPropertiesToSaveExpression(propertyName);
        p.setProperty("jboss." + propertyName,propertyValue);
        assertTrue(target.ShouldSave(propertyName));

    }


    //proves that if the property ${gov.nih.nci.bda.certification.listener.TaskListener.properties.to.save}
    // is not set, no exception is thrown
    // when buildFinished is called.
    public void testBuildFinishedNoException() {

        TaskListener target = new TaskListener();

        target.buildFinished(getMockBuildEvent());


    }

}
