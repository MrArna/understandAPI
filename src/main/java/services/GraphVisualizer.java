package services;

import entities.EntityGraph;
import org.jgrapht.GraphMapping;
import org.jgrapht.alg.isomorphism.VF2SubgraphIsomorphismInspector;
import org.jgrapht.alg.util.AlwaysEqualComparator;

import java.util.Iterator;

/**
 * Created by Marco on 30/09/16.
 */
public class GraphVisualizer
{

    public GraphVisualizer()
    {
        super();
    }



    public int consoleCoupleVisualizer(EntityGraph g)
    {
        System.out.println("\n** PROJECT CLASS DEPENDENCIES **\n");

        for(Object vertex : g.getGraph().vertexSet())
        {
            String v = (String) vertex;

            System.out.println("CLASS " + v + " COUPLED WITH:");
            for(Object neighbor : g.getNeighbor(v))
            {
                System.out.println("---> CLASS " + (String)neighbor);
            }
        }

        return 0;
    }

    public int consoleCallVisualizer(EntityGraph g)
    {
        System.out.println("\n** PROJECT CALL GRAPH DEPENDENCIES **\n");

        for(Object vertex : g.getGraph().vertexSet())
        {
            String v = (String) vertex;

            System.out.println("METHOD " + v + " CALL BY:");
            for(Object neighbor : g.getNeighbor(v))
            {
                System.out.println("---> METHOD " + (String)neighbor);
            }
        }

        return 0;
    }



    public int consoleInterfaceDependenciesVisualizer(EntityGraph g)
    {
        System.out.println("\n** PROJECT INTERFACE HIERARCHY **\n");

        for(Object vertex : g.getGraph().vertexSet())
        {
            String v = (String) vertex;

            System.out.println("INTERFACE " + v + " EXTENDED BY:");
            for(Object neighbor : g.getNeighbor(v))
            {
                System.out.println("---> INTERFACE " + (String)neighbor);
            }
        }

        return 0;
    }

    public int consoleInterfaceImplementationVisualizer(EntityGraph g)
    {
        System.out.println("\n** PROJECT INTERFACE IMPLEMENTATION **\n");

        for(Object vertex : g.getGraph().vertexSet())
        {
            String v = (String) vertex;

            if(g.getNeighbor(v).size() != 0)
                System.out.println("INTERFACE " + v + " IMPLEMENTED BY:");

            for(Object neighbor : g.getNeighbor(v))
            {
                System.out.println("---> CLASS " + (String)neighbor);
            }
        }

        return 0;
    }

    public int consoleClassDependenciesVisualizer(EntityGraph g)
    {
        System.out.println("\n** PROJECT CLASS HIERARCHY **\n");

        for(Object vertex : g.getGraph().vertexSet())
        {
            String v = (String) vertex;

            System.out.println("CLASS " + v + " EXTENDED BY:");
            for(Object neighbor : g.getNeighbor(v))
            {
                System.out.println("---> CLASS " + (String)neighbor);
            }
        }

        return 0;
    }


    public int computeIsomorphism(EntityGraph g1, EntityGraph g2)
    {
        VF2SubgraphIsomorphismInspector inspector =
                new VF2SubgraphIsomorphismInspector(
                        g1.getGraph(),
                        g2.getGraph(),
                        new AlwaysEqualComparator(),
                        new AlwaysEqualComparator()
                );

        //System.out.println(inspector.isomorphismExists());

        int i = 0;

        if(inspector.isomorphismExists())
        {
            Iterator iterator = inspector.getMappings();
            GraphMapping<String,String> gm = (GraphMapping<String,String>) iterator.next();
            for(Object vObj : g1.getGraph().vertexSet())
            {
                String v = (String) vObj;

                String correspondece = (String) gm.getVertexCorrespondence(v,true);
                if(correspondece == null)
                {
                    System.out.println("---> " + v + " not in subversion");
                    i++;
                }
            }
        }
        else
        {
            System.out.println("\n No Isomorphism found \n");
        }


        if(i == g1.getGraph().vertexSet().size())
        {
            System.out.println("\n No difference between two version \n");
        }
        return  0;
    }



    public int consoleVertexVisualize(EntityGraph g)
    {
        String s = "";
        for(Object vertex : g.getGraph().vertexSet())
        {
            s = s + " " + vertex.toString();
        }
        System.out.println(s);
        return 1;
    }


}
