package services;

import com.scitools.understand.Entity;
import entities.EntityGraph;
import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgrapht.ext.JGraphModelAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;
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
    private static final Dimension DEFAULT_SIZE = new Dimension( WIDTH*10, HEIGHT*10);

    private JFrame frame;


    private JGraphModelAdapter m_jgAdapter;


    public GraphVisualizer()
    {
        super();
        frame = new JFrame();
    }


    public void visualizeInterface(EntityGraph g)
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
            if(e.kind().check("Java Class Type"))
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
        jgraph.setSize(actualPosX + 500,actualPosY + 500);

        frame.setAutoRequestFocus(true);
        jgraph.setAutoResizeGraph(true);
        frame.getContentPane(  ).add( new JScrollPane(jgraph) );
        frame.setSize( DEFAULT_SIZE );
        frame.setVisible(true);
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
