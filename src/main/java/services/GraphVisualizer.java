package services;

import com.jgraph.layout.JGraphFacade;
import com.jgraph.layout.organic.JGraphFastOrganicLayout;
import com.jgraph.layout.organic.JGraphOrganicLayout;
import com.jgraph.layout.organic.JGraphSelfOrganizingOrganicLayout;
import com.scitools.understand.Entity;
import entities.EntityGraph;
import entities.RelationshipEdge;
import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultWeightedEdge;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Marco on 30/09/16.
 */
public class GraphVisualizer
{
    private static final Integer WIDTH = new Double(Toolkit.getDefaultToolkit().getScreenSize().getWidth()).intValue();
    private static final Integer HEIGHT = new Double(Toolkit.getDefaultToolkit().getScreenSize().getHeight()).intValue();
    private static final Integer BOX_WIDTH = WIDTH / 10;

    private static final Color     DEFAULT_BG_COLOR = Color.decode( "#FAFBFF" );
    private static final Dimension DEFAULT_SIZE = new Dimension( WIDTH, HEIGHT);

    private JFrame frame;


    private JGraphModelAdapter m_jgAdapter;


    public GraphVisualizer()
    {
        super();
        frame = new JFrame();
    }



    public int visualizeInterface(EntityGraph g)
    {
        // create a visualization using JGraph, via an adapter
        m_jgAdapter = new JGraphModelAdapter(g.getGraph());
        JGraph jgraph = new JGraph( m_jgAdapter );
        adjustDisplaySettings( jgraph );
        frame.getContentPane(  ).add( new JScrollPane(jgraph) );
        frame.setSize( DEFAULT_SIZE );

        frame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent we){
                System.exit(0);
            }
        });


        Integer actualPosX = 10;
        Integer actualPosY = 10;
        Integer relPosX = 0;

        for(Object vertex : g.getGraph().vertexSet())
        {
            Entity e = (Entity)vertex;
            positionVertexAt(vertex,actualPosX,actualPosY);

            for(Object neighborVertex : g.getNeighbor(e))
            {
                positionVertexAt(neighborVertex,actualPosX + relPosX,actualPosY + 100);
                relPosX += 100;
            }


            actualPosX += BOX_WIDTH;
            if(actualPosX >= WIDTH - 100)
            {
                actualPosX = 10;
                actualPosY += 150;
            }
            relPosX = 0;
        }

        jgraph.setSize(actualPosX + 500,actualPosY + 500);

        frame.setAutoRequestFocus(true);
        jgraph.setAutoResizeGraph(true);
        frame.getContentPane(  ).add( new JScrollPane(jgraph) );
        frame.setSize( DEFAULT_SIZE );
        frame.setVisible(true);

        return 0;
    }



    public int consoleCoupleVisualizer(EntityGraph g)
    {
        System.out.println("\n****** PROJECT CALL GRAPH DEPENDENCIES ******\n");

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


    public int consoleInterfaceDependenciesVisualizer(EntityGraph g)
    {
        System.out.println("\n****** PROJECT INTERFACE DEPENDENCIES ******\n");

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
        System.out.println("\n****** PROJECT INTERFACE IMPLEMENTATION ******\n");

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
        System.out.println("\n****** PROJECT CLASS DEPENDENCIES ******\n");

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

    public int visualizer2(EntityGraph g)
    {
        m_jgAdapter = new JGraphModelAdapter(g.getGraph());
        JGraph jgraph = new JGraph( m_jgAdapter );

        JPanel panel = new JPanel();
        panel.setPreferredSize(DEFAULT_SIZE);

        JScrollPane scrollpane = new JScrollPane(panel);
        scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        frame.getContentPane().add(scrollpane, BorderLayout.CENTER);

        frame.setTitle("Call Graph, " + g.getGraph().vertexSet().size() + "nodes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setPreferredSize(DEFAULT_SIZE);

        panel.add(jgraph);
        frame.setSize(DEFAULT_SIZE);

        // Let's see if we can lay it out
        JGraphFacade jgf = new JGraphFacade(jgraph);
        JGraphFastOrganicLayout layoutifier = new JGraphFastOrganicLayout();
        //layoutifier.setDeterministic(true);
        //layoutifier.setOptimizeEdgeCrossing(true);
        layoutifier.run(jgf);
        System.out.println("Layout complete");

        final Map nestedMap = jgf.createNestedMap(true, true);
        jgraph.getGraphLayoutCache().edit(nestedMap);

        jgraph.getGraphLayoutCache().update();
        jgraph.refresh();

        frame.setVisible(true);
        panel.setVisible(true);
        scrollpane.setVisible(true);


        return 1;
    }


    private void adjustDisplaySettings( JGraph jg ) {
        jg.setPreferredSize( DEFAULT_SIZE );

        Color c = DEFAULT_BG_COLOR;
        /*String colorStr = null;

        try {
            colorStr = frame.getParameter( "bgcolor" );
        }
        catch( Exception e ) {}

        if( colorStr != null ) {
            c = Color.decode( colorStr );
        }*/

        jg.setBackground( c );
        jg.setAutoResizeGraph(true);
        jg.setAutoscrolls(true);
    }


    private void positionVertexAt( Object vertex, int x, int y )
    {
        Entity e = (Entity) vertex;


        DefaultGraphCell cell = m_jgAdapter.getVertexCell( vertex );
        Map attr = cell.getAttributes(  );
        Rectangle2D b    = GraphConstants.getBounds( attr );

        //GraphConstants.setBounds( attr, new Rectangle( x, y, BOX_WIDTH , (int )b.getHeight()) );
        GraphConstants.setAutoSize(attr,true);
        GraphConstants.setValue(attr,e.name());
        GraphConstants.setLabelEnabled(attr,false);

        Map cellAttr = new HashMap(  );
        cellAttr.put( cell, attr );
        m_jgAdapter.edit( cellAttr, null, null, null);
    }


    private void setEdgeAttr(Object obj)
    {
        RelationshipEdge e = (RelationshipEdge) obj;


        DefaultGraphCell cell = m_jgAdapter.getEdgeCell( e );
        Map attr = cell.getAttributes(  );
        Rectangle2D b    = GraphConstants.getBounds( attr );

        //GraphConstants.setBounds( attr, new Rectangle( x, y, BOX_WIDTH , (int )b.getHeight()) );
        GraphConstants.setLabelAlongEdge(attr,false);

        Map cellAttr = new HashMap(  );
        cellAttr.put( cell, attr );
        m_jgAdapter.edit( cellAttr, null, null, null);
    }

}
