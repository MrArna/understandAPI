package entities;

import org.junit.Test;

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

        assertEquals(eg.getNeighbor("E").size(),0);
    }

}