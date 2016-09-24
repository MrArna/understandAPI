/**
 * Created by Marco on 23/09/16.
 */
import com.mxgraph.util.mxConstants;
import com.scitools.understand.*;
import com.sun.javafx.tk.*;
import org.jgraph.JGraph;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultEdge;

import javax.swing.*;
import java.awt.*;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

public class Main extends JApplet {
    public static String projPath = "/Users/Marco/Understand/Maruora.udb";

    private static final Integer WIDTH = new Double(Toolkit.getDefaultToolkit().getScreenSize().getWidth()).intValue();
    private static final Integer HEIGHT = new Double(Toolkit.getDefaultToolkit().getScreenSize().getHeight()).intValue();
    private static final Integer BOX_WIDTH = WIDTH / 10;

    private static final Color     DEFAULT_BG_COLOR = Color.decode( "#FAFBFF" );
    private static final Dimension DEFAULT_SIZE = new Dimension( WIDTH, HEIGHT );

    //
    private JGraphModelAdapter m_jgAdapter;

    /**
     * @see java.applet.Applet#init().
     */
    public void init(  ) {

        UnderstandService us = new UnderstandService();
        EntityGraph g = new EntityGraph();

        // create a visualization using JGraph, via an adapter
        m_jgAdapter = new JGraphModelAdapter( g.getGraph() );
        JGraph jgraph = new JGraph( m_jgAdapter );
        adjustDisplaySettings( jgraph );
        getContentPane(  ).add( jgraph );
        resize( DEFAULT_SIZE );

        try
        {
            us.addDB("Maruora");
            Database db = us.getDB("Maruora");
            // Get a list of all Java interfaces
            Entity [] funcs = db.ents("Java Interface Type");
            for(Entity e : funcs)
            {
                g.addVertex(e);
                //Find all the class that implements the given interface
                Reference[] paramterRefs = e.refs("Java Implementby Coupleby", "", false);
                for (Reference cRef : paramterRefs)
                {
                    Entity cEnt = cRef.ent();
                    g.addVertex(cEnt);
                    g.addEdge(e,cEnt, new RelationshipEdge<Entity>(e,cEnt,"implemented by"));
                }
            }
        } catch (UnderstandException e)
        {
            System.out.println("Failed opening Database:" + e.getMessage());
            System.exit(0);
        }


        Integer actualPosX = 10;
        Integer actualPosY = 10;
        Integer relPosX = 0;

        for(Object vertex : g.getGraph().vertexSet())
        {
            Entity e = (Entity)vertex;
            if(e.kind().check("Java Interface Type"))
            {
                positionVertexAt(vertex,actualPosX,actualPosY);
                for(Object neighborVertex : g.getNeighbor(e))
                {
                    positionVertexAt(neighborVertex,actualPosX + relPosX,actualPosY + 100);
                    relPosX += 100;
                }
            }
            actualPosX += BOX_WIDTH;
            if(actualPosX >= WIDTH - 100)
            {
                actualPosX = 10;
                actualPosY += 150;
            }
            relPosX = 0;
        }

        for(Object edge : g.getGraph().edgeSet())
        {
            setEdgeAttributes(edge);
        }
    }


    private void adjustDisplaySettings( JGraph jg ) {
        jg.setPreferredSize( DEFAULT_SIZE );

        Color c = DEFAULT_BG_COLOR;
        String colorStr = null;

        try {
            colorStr = getParameter( "bgcolor" );
        }
        catch( Exception e ) {}

        if( colorStr != null ) {
            c = Color.decode( colorStr );
        }

        jg.setBackground( c );
    }


    private void positionVertexAt( Object vertex, int x, int y )
    {
        Entity e = (Entity) vertex;


        DefaultGraphCell cell = m_jgAdapter.getVertexCell( vertex );
        Map attr = cell.getAttributes(  );
        Rectangle2D b    = GraphConstants.getBounds( attr );

        GraphConstants.setBounds( attr, new Rectangle( x, y, BOX_WIDTH , (int )b.getHeight()) );
        GraphConstants.setAutoSize(attr,true);
        GraphConstants.setValue(attr,e.name());
        GraphConstants.setLabelAlongEdge(attr,false);
        GraphConstants.setLabelEnabled(attr,false);

        if(e.kind().check("Java Interface Type"))
            GraphConstants.setBackground(attr,Color.BLUE);

        Map cellAttr = new HashMap(  );
        cellAttr.put( cell, attr );
        m_jgAdapter.edit( cellAttr, null, null, null);
    }

    private void setEdgeAttributes(Object o)
    {

    }


}