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
    public void addVertex(String name)
    {
        g.addVertex(name);
    }

    public void addEdge(Entity source, Entity destination, Object relation)
    {

            g.addEdge(source,destination,relation);

    }

    public void addEdge(String source, String destination)
    {

        g.addEdge(source,destination);

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

        neighbors.remove(e);
        return neighbors;
    }

    public List<String> getNeighbor(String e)
    {
        List<String> neighbors = new ArrayList<>();

        //for each edge of the given node, find the target of this edge different from itself
        for(Object edge : g.edgesOf(e))
        {
            String target = (String)g.getEdgeTarget(edge);
            if(!target.equals(e))
                neighbors.add(target);
        }

        return neighbors;
    }


    public void closure()
    {
        TransitiveClosure.INSTANCE.closeSimpleDirectedGraph(g);
    }

    public EntityGraph findSubgraph(String vertexList)
    {
        EntityGraph g = new EntityGraph();

        //retrieve the vertex
        for(String e : vertexList.split(","))
        {
            if(this.g.containsVertex(e))
            {
                g.addVertex(e);
            }
        }
        //for each vertex from the subgraph if there is an edge in the original graph,
        //add it to the subgraph
        for (Object vertex1 : g.getGraph().vertexSet())
        {
            String v1 = (String)vertex1;
            for(Object vertex2 : g.getGraph().vertexSet())
            {
                String v2 = (String)vertex2;
                if(this.g.containsEdge(v1,v2))
                {
                    g.addEdge(v1,v2);
                }
            }
        }

        return g;
    }

}