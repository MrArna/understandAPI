/**
 * Created by Marco on 23/09/16.
 */
import com.scitools.understand.Entity;
import com.scitools.understand.UnderstandException;
import entities.EntityGraph;
import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgrapht.ext.JGraphModelAdapter;
import services.UnderstandService;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static String projPath = "/Users/Marco/Understand/Maruora.udb";

    private static final Integer WIDTH = new Double(Toolkit.getDefaultToolkit().getScreenSize().getWidth()).intValue();
    private static final Integer HEIGHT = new Double(Toolkit.getDefaultToolkit().getScreenSize().getHeight()).intValue();
    private static final Integer BOX_WIDTH = WIDTH / 10;

    private static final Color     DEFAULT_BG_COLOR = Color.decode( "#FAFBFF" );
    private static final Dimension DEFAULT_SIZE = new Dimension( WIDTH, HEIGHT );

    private JApplet myApplet = new JApplet();


    private JGraphModelAdapter m_jgAdapter;

    public void main(String args[])
    {

        UnderstandService us = new UnderstandService();
        try {
            us.addDB("Maruora");
        } catch (UnderstandException e) {
            System.out.println("Failed to access the specified DB");
            System.exit(0);
        }

        EntityGraph g = us.findJavaInterfaceGraph("Maruora");

        // create a visualization using JGraph, via an adapter
        JGraph jgraph = new JGraph( m_jgAdapter );
        adjustDisplaySettings( jgraph );
        myApplet.getContentPane(  ).add( jgraph );
        myApplet.resize( DEFAULT_SIZE );


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
            colorStr = myApplet.getParameter( "bgcolor" );
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