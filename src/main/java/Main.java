/**
 * Created by Marco on 23/09/16.
 */
import com.scitools.understand.UnderstandException;
import entities.EntityGraph;
import org.apache.commons.cli.*;
import services.GraphVisualizer;
import services.UnderstandService;

import java.net.URISyntaxException;

public class Main {


    public static void main(String args[]) {
        CommandLineParser parser = new DefaultParser();


        Options options = new Options();
        options.addOption("v1", "database", true, "Path to the Understand udb file of version 1");
        options.addOption("v2", "almost-all", true, "Path to the Understand udb file of version 1");
        options.addOption("i", "interface", false, "Show interface graph");
        options.addOption("c", "class",false,"Show class inheritance");
        options.addOption("cg","call-graph",false,"Show call-graph");

        try {
            // parse the command line arguments
            CommandLine line = parser.parse(options, args);

            // validate that block-size has been set
            if (line.hasOption("v1") && line.hasOption("v2")) {
                // print the value of block-size
                System.out.println(line.getOptionValue("v1"));
                System.out.println(line.getOptionValue("v2"));
            }
        } catch (ParseException exp) {
            System.out.println("Unexpected exception:" + exp.getMessage());
        }

        UnderstandService us = new UnderstandService();
        GraphVisualizer gv = new GraphVisualizer();


        String path = "/Users/Marco/Google Drive/Università/CS474/cs474-HW2/src/main/resources/Maruora.udb";

        try {
            us.addDB(path);
        } catch (UnderstandException e) {
            System.out.println("Failed to access the specified DB");
            System.exit(0);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        EntityGraph g = us.findJavaGraph(path,"c");
        gv.visualizeInterface(g);

    }
}