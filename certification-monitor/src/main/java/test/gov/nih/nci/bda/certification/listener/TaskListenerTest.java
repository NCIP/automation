package test.gov.nih.nci.bda.certification.listener;

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
        target.buildStarted(getMockBuildEvent());

        target.writeProperty("x","y");
        target.writeProperty(name,value);
        target.writeProperty("z","abc");

        boolean found = false ;

        FileReader r = null ;
        try
        {
            r = new FileReader(target.getPropertyFilename());
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
        BufferedWriter w = target.getWriter();
        assertNotNull(w);
    }

    public void testGetPropertyFilename() {

        TaskListener target = new TaskListener();
        Project p = getMockBuildEvent().getProject() ;
        target.setProject (p);
        String fileName = target.getPropertyFilename();
        assertNotNull(fileName);

    }

    public void testBuildStartedSetsFilename() {
        TaskListener target = new TaskListener();

        target.buildStarted(getMockBuildEvent());
        String actual = target.getPropertyFilename();
        assertEquals(expected,actual);
    }

    public void testBuildInitializedSetsFilename() {
        TaskListener target = new TaskListener();

        Project p = getMockBuildEvent().getProject() ;
        target.setProject (p);

        String actual = target.getPropertyFilename();
        assertEquals(expected,actual);
    }

    public void testBuildStartedWithNoProperty() {
        TaskListener target = new TaskListener();

        Project p = new Project();
        target.setProject (p);

        assertNull(target.getPropertyFilename());
    }


}
