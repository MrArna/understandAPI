package services;

import com.scitools.understand.*;
import entities.EntityGraph;
import entities.RelationshipEdge;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Marco on 24/09/16.
 */
public class UnderstandService
{
    private Map<String,Database> dbs;

    public UnderstandService()
    {
        if(dbs == null)
        {
            dbs = new HashMap<>();
        }
    }

    public void addDB(String path) throws UnderstandException, URISyntaxException
    {
        dbs.put(path, Understand.open(path));
    }


    public EntityGraph findJavaGraph(String dbName, String option)
    {
        EntityGraph g = new EntityGraph();

        Database db = dbs.get(dbName);
        String referenceType = "", entityType = "", edgeLabel = "";

        switch (option)
        {
            case "i":
                referenceType = "Java Implementby Coupleby";
                entityType = "Java Interface Type";
                edgeLabel = "implemented by";
                break;
            case "c":
                referenceType = "Java Extendby Coupleby";
                entityType = "Java Class Type";
                edgeLabel = "extended by";
                break;
            case "cg":
                referenceType = "Java Coupleby";
                entityType = "Java Class Type";
                edgeLabel = "called by";
                break;
        }

        Entity[] funcs = db.ents(entityType);
        for(Entity e : funcs)
        {
            g.addVertex(e);
            //Find all the class that implements the given interface
            Reference[] paramterRefs = e.refs(referenceType, "", false);
            for (Reference cRef : paramterRefs)
            {
                Entity cEnt = cRef.ent();
                g.addVertex(cEnt);
                g.addEdge(e,cEnt, new RelationshipEdge<Entity>(e,cEnt,edgeLabel));
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
