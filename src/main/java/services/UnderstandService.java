package services;

import com.scitools.understand.*;
import entities.EntityGraph;
import entities.RelationshipEdge;
import org.jgraph.JGraph;
import org.jgrapht.ext.JGraphModelAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Marco on 24/09/16.
 */
public class UnderstandService
{
    private Map<String,Database> dbs;
    private JGraphModelAdapter m_jgAdapter;

    public UnderstandService()
    {
        if(dbs == null)
        {
            dbs = new HashMap<>();
        }
    }

    public void addDB(String name) throws UnderstandException
    {
        String path = this.getClass().getResource(name + ".udb").toString();
        System.out.println(path);
        dbs.put(name, Understand.open("/Users/Marco/Understand/Maruora.udb"));
    }


    public EntityGraph findJavaInterfaceGraph(String dbName)
    {
        EntityGraph g = new EntityGraph();

        Database db = dbs.get(dbName);
        // Get a list of all Java interfaces
        Entity[] funcs = db.ents("Java Interface Type");
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

        g.closure();
        return g;
    }





    public Database getDB(String name)
    {
        return  dbs.get(name);
    }


}
