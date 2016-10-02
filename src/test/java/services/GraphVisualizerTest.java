package services;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

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

        String v1path = this.getClass().getResource("/SimpleProject.udb").toURI().getPath();

        System.out.println(v1path);

        us.addDB(v1path);

        int res = gv.consoleCoupleVisualizer(us.findJavaGraph(v1path,"cg"));

        assertEquals(res,0);
    }


    @Test
    public void interfaceDependenciesViz() throws Exception
    {

        us = new UnderstandService();
        gv = new GraphVisualizer();

        String v1path = this.getClass().getResource("/SimpleProject.udb").toURI().getPath();

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

        String v1path = this.getClass().getResource("/SimpleProject.udb").toURI().getPath();

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

        String v1path = this.getClass().getResource("/SimpleProject.udb").toURI().getPath();

        System.out.println(v1path);

        us.addDB(v1path);

        int res = gv.consoleClassDependenciesVisualizer(us.findJavaGraph(v1path,"c"));

        assertEquals(res,0);
    }

}