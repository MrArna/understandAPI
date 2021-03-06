package services;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Marco on 01/10/16.
 */
public class GraphVisualizerTest
{


    private UnderstandService us;
    private GraphVisualizer gv;


    @Test
    public void coupleViz() throws Exception
    {

        us = new UnderstandService();
        gv = new GraphVisualizer();

        String v1path = GraphVisualizer.class.getResource("/SimpleProject.udb").toURI().getPath();
        String v2path = GraphVisualizer.class.getResource("/SimpleProjectV2.udb").toURI().getPath();

        us.addDB(v1path);
        us.addDB(v2path);

        int res = gv.consoleCoupleVisualizer(us.findJavaGraph(v1path,"cg"));
        gv.consoleCoupleVisualizer(us.findJavaGraph(v2path,"cg"));

        assertEquals(res,0);
    }


    @Test
    public void interfaceDependenciesViz() throws Exception
    {

        us = new UnderstandService();
        gv = new GraphVisualizer();

        String v1path = GraphVisualizer.class.getResource("/SimpleProject.udb").toURI().getPath();

        System.out.println(v1path);

        us.addDB(v1path);

        int res = gv.consoleInterfaceDependenciesVisualizer(us.findJavaGraph(v1path,"i"));

        assertEquals(res,0);
    }

    @Test
    public void interfaceImplementationViz() throws Exception
    {

        us = new UnderstandService();
        gv = new GraphVisualizer();

        String v1path = GraphVisualizer.class.getResource("/SimpleProject.udb").toURI().getPath();

        System.out.println(v1path);

        us.addDB(v1path);

        int res = gv.consoleInterfaceImplementationVisualizer(us.findJavaGraph(v1path,"im"));

        assertEquals(res,0);
    }

    @Test
    public void classDependenciesViz() throws Exception
    {

        us = new UnderstandService();
        gv = new GraphVisualizer();

        String v1path = GraphVisualizer.class.getResource("/SimpleProject.udb").toURI().getPath();
        String v2path = GraphVisualizer.class.getResource("/SimpleProjectV2.udb").toURI().getPath();

        us.addDB(v1path);
        us.addDB(v2path);

        int res = gv.consoleClassDependenciesVisualizer(us.findJavaGraph(v1path,"c"));
        gv.consoleClassDependenciesVisualizer(us.findJavaGraph(v2path,"c"));

        assertEquals(res,0);
    }


    @Test
    public void isomorphismTest() throws Exception
    {
        us = new UnderstandService();
        gv = new GraphVisualizer();

        String v1path = GraphVisualizer.class.getResource("/SimpleProject.udb").toURI().getPath();
        String v2path = GraphVisualizer.class.getResource("/SimpleProjectV2.udb").toURI().getPath();

        us.addDB(v1path);
        us.addDB(v2path);

        gv.computeIsomorphism(us.findJavaGraph(v2path,"c"),us.findJavaGraph(v1path,"c"));
        assertTrue(true);
    }
}