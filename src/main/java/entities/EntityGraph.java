package entities; /**
 * Created by Marco on 24/09/16.
 */
import com.scitools.understand.Entity;
import org.jgrapht.alg.TransitiveClosure;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import java.util.ArrayList;
import java.util.List;

public class EntityGraph {

    private SimpleDirectedGraph g;


    public EntityGraph()
    {
        g = new SimpleDirectedGraph( DefaultEdge.class );
    }


    public void addVertex(Entity name)
    {
        g.addVertex(name);
    }

    public void addEdge(Entity source, Entity destination, Object relation)
    {

            g.addEdge(source,destination,relation);

    }

    public SimpleDirectedGraph getGraph()
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


    public void closure()
    {
        TransitiveClosure.INSTANCE.closeSimpleDirectedGraph(g);
    }



}