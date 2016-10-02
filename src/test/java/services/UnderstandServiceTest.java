package services;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by Marco on 01/10/16.
 */
public class UnderstandServiceTest extends TestCase {


    private UnderstandService us;


    @Test
    public void testAddDB() throws Exception
    {
        us = new UnderstandService();

        String v1path = ClassLoader.getSystemClassLoader().getResource("SimpleProject.udb").toURI().getPath();
        String v2path = ClassLoader.getSystemClassLoader().getResource("SimpleProjectV2.udb").toURI().getPath();

        us.addDB(v1path);
        us.addDB(v2path);

        assertFalse(us.getNumDB() == 0);
    }

    @Test
    public void testFindJavaGraph() throws Exception
    {
        us = new UnderstandService();

        String v1path = ClassLoader.getSystemClassLoader().getResource("SimpleProject.udb").toURI().getPath();
        String v2path =ClassLoader.getSystemClassLoader().getResource("SimpleProjectV2.udb").toURI().getPath();

        us.addDB(v1path);
        us.addDB(v2path);

        for(Object edge : us.findJavaGraph(v1path,"im").getGraph().edgeSet())
        {
            System.out.println(edge.toString());
        }


        assertNotNull(us.findJavaGraph(v1path,"i"));
        assertNotNull(us.findJavaGraph(v1path,"c"));
        assertNotNull(us.findJavaGraph(v1path,"cg"));
    }

}