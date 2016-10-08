package services;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Marco on 08/10/16.
 */
public class UnderstandServiceTest
{

    private UnderstandService us;


    @Test
    public void testAddDB() throws Exception
    {
        us = new UnderstandService();

        String v1path = UnderstandServiceTest.class.getResource("/SimpleProject.udb").toURI().getPath();
        String v2path = UnderstandServiceTest.class.getResource("/SimpleProjectV2.udb").toURI().getPath();

        us.addDB(v1path);
        us.addDB(v2path);

        assertFalse(us.getNumDB() == 0);
    }

    @Test
    public void testFindJavaGraph() throws Exception
    {
        us = new UnderstandService();

        String v1path = UnderstandServiceTest.class.getResource("/SimpleProject.udb").toURI().getPath();
        String v2path = UnderstandServiceTest.class.getResource("/SimpleProjectV2.udb").toURI().getPath();

        us.addDB(v1path);
        us.addDB(v2path);

        for(Object edge : us.findJavaGraph(v1path,"mc").getGraph().edgeSet())
        {
            System.out.println(edge.toString());
        }


        assertNotNull(us.findJavaGraph(v1path,"i"));
    }

}