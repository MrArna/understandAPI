package services;

import com.scitools.understand.*;
import entities.EntityGraph;

import java.net.URISyntaxException;
import java.util.ArrayList;
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
                referenceType = "Java Extendby";
                entityType = "Java Interface Type";
                edgeLabel = "implemented by";
                break;
            case "im":
                referenceType = "Java Implementby";
                entityType = "Java Interface Type";
                edgeLabel = "implemented by";
                break;
            case "c":
                referenceType = "Java Extendby";
                entityType = "Java Class Member Type";
                edgeLabel = "extended by";
                break;
            case "cg":
                referenceType = "Java Couple";
                entityType = "Java Class Member Type";
                edgeLabel = "called by";
                break;
        }

        Entity[] entsArray = db.ents(entityType);

        ArrayList<Entity> entsList = new ArrayList<Entity>();


        for(Entity e : entsArray)
        {
            if(e.library().equals(""))
            {
                entsList.add(e);
            }
        }


        for(Entity e : entsList)
        {
            g.addVertex(e.name());
            if(option.equals("im"))
                entityType = "Java Class Member Type";

            Reference[] paramterRefs = e.refs(referenceType, entityType, true);
            for (Reference cRef : paramterRefs)
            {

                //System.out.println(cRef.toString());
                Entity cEnt = cRef.ent();
                if(cEnt.library().equals("") && !cEnt.name().equals(e.name()))
                {
                    g.addVertex(cEnt.name());
                    g.addEdge(e.name(),cEnt.name());
                }
            }
        }

        //System.out.println(g.getGraph().edgeSet().size());
        return g;
    }





    public Database getDB(String name)
    {
        return  dbs.get(name);
    }

    public int getNumDB()
    {
        return dbs.size();
    }


}
