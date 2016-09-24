/**
 * Created by Marco on 23/09/16.
 */
import com.scitools.understand.*;

public class Main {
    public static String projPath = "/Users/Marco/Understand/Maruora.udb";
    public static void main(String[] args) {

        UnderstandService us = new UnderstandService();

        Graph g = new Graph();

        try{
            //Open the Understand Database
            //Database db = Understand.open(projPath);
            us.addDB("Maruora");
            Database db = us.getDB("Maruora");
            // Get a list of all functions and methods
            Entity [] funcs = db.ents("Java Interface Type");
            for(Entity e : funcs){
                System.out.print(e.name()+"(");
                //Find all the parameters for the given method/function and build them into a string
                StringBuilder paramList = new StringBuilder();
                Reference [] paramterRefs = e.refs("Java Implementby Coupleby","",false);
                for( Reference pRef : paramterRefs){
                    Entity pEnt = pRef.ent();
                    paramList.append(pEnt.name());
                    paramList.append(",");
                }
                //Remove trailing comma from parameter list
                if(paramList.length() > 0){
                    paramList.setLength(paramList.length() - 1);
                }
                System.out.print(paramList+")\n");
            }
        }catch (UnderstandException e){
            System.out.println("Failed opening Database:" + e.getMessage());
            System.exit(0);
        }
    }
}