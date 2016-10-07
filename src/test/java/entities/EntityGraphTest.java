package entities;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Marco on 02/10/16.
 */
public class EntityGraphTest {


    @Test
    public void getNeighbor() throws Exception
    {
        EntityGraph eg = new EntityGraph();

        eg.addVertex("A");
        eg.addVertex("B");
        eg.addVertex("C");
        eg.addVertex("D");
        eg.addVertex("E");
        eg.addEdge("B","E");
        eg.addEdge("C","D");

        System.out.println(eg.getNeighbor("E").toString());

        assertEquals(0,eg.getNeighbor("E").size());
    }

    @Test
    public void subGraphTest() throws Exception
    {
        EntityGraph eg = new EntityGraph();

        eg.addVertex("A");
        eg.addVertex("B");
        eg.addVertex("C");
        eg.addVertex("D");
        eg.addVertex("E");
        eg.addEdge("B","E");
        eg.addEdge("C","D");
        eg.addEdge("E","C");

        eg.closure();
        System.out.println(eg.findSubgraph("B,C").getGraph().edgeSet());

        assertEquals(1,eg.findSubgraph("B,C").getGraph().edgeSet().size());
    }

}