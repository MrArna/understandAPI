/**
 * Created by Marco on 24/09/16.
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JApplet;

import com.scitools.understand.Entity;
import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;

import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.ListenableDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

/**
 * A demo applet that shows how to use JGraph to visualize JGraphT graphs.
 *
 * @author Barak Naveh
 *
 * @since Aug 3, 2003
 */
public class EntityGraph {

    private ListenableDirectedGraph g;


    public EntityGraph()
    {
        g = new ListenableDirectedGraph( DefaultEdge.class );
    }


    public void addVertex(Entity name)
    {
        g.addVertex(name);
    }

    public void addEdge(Entity source, Entity destination, Object relation)
    {

            g.addEdge(source,destination,relation);

    }

    public ListenableDirectedGraph getGraph()
    {
        return g;
    }

    public Integer getNumVertex(String entityType)
    {
        if(entityType == null || entityType.equals(""))
            return g.vertexSet().size();

        Integer counter = 0;
        for (Object o : g.vertexSet())
        {
            Entity e = (Entity) o;
            if(e.kind().check(entityType))
                counter++;
        }
        return counter;
    }

    public List<Entity> getNeighbor(Entity e)
    {
        List<Entity> neighbors = new ArrayList<>();

        for(Object edge : g.edgesOf(e))
        {
            neighbors.add((Entity)g.getEdgeTarget(edge));
        }

        return neighbors;
    }


}