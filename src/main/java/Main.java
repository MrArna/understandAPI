/**
 * Created by Marco on 23/09/16.
 */
import com.scitools.understand.UnderstandException;
import entities.EntityGraph;
import org.apache.commons.cli.*;
import services.GraphVisualizer;
import services.UnderstandService;

import java.net.URISyntaxException;
import java.util.Scanner;

public class Main {

    private static String v1path = "";
    private static String v2path = "";
    private  static EntityGraph g1;
    private static EntityGraph g2;
    private static UnderstandService us;
    private static GraphVisualizer gv;
    private static Scanner reader;


    public static void main(String args[]) throws URISyntaxException {
        CommandLineParser parser = new DefaultParser();


        Options options = new Options();
        options.addOption("v1", "version1", true, "Path to the Understand udb file of version 1");
        options.addOption("v2", "version2", true, "Path to the Understand udb file of version 1");


        v1path = Main.class.getResource("SimpleProject.udb").toURI().getPath();
        v2path = Main.class.getResource("SimpleProjectV2.udb").toURI().getPath();

        try {
            // parse the command line arguments
            CommandLine line = parser.parse(options, args);

            // validate that block-size has been set
            if (line.hasOption("v1") && line.hasOption("v2")) {
                // print the value of block-size

                v1path = line.getOptionValue("v1");
                v2path = line.getOptionValue("v2");

            }
        } catch (ParseException exp) {
            System.out.println("Unexpected exception:" + exp.getMessage());
            System.exit(0);
        }

        us = new UnderstandService();
        gv = new GraphVisualizer();


        try {
            us.addDB(v1path);
            us.addDB(v2path);
        } catch (UnderstandException e) {
            System.out.println("Failed to access the specified DB: \n" + v1path + "\n" + v2path);
            System.exit(0);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        while (true) {
            System.out.println("\n------> VERSION ANALIZER <------\n");
            reader = new Scanner(System.in);
            System.out.println
                    (
                            "1. Show Interface hierarchy\n" +
                            "2. Show Interface Implementation\n" +
                            "3. Show Class hierarchy\n" +
                            "4. Show Class dependency\n" +
                            "5. Show Call Graph\n" +
                            "6. Show closure over type\n" +
                            "7. Show difference between versions\n" +
                            "8. Exit\n\n" +
                            "Your decision: "
                    );

            int n = reader.nextInt();
            while (n > 6 && n < 1) {
                System.out.println("Your decision: ");
                n = reader.nextInt();
            }


            int c = 0;

            switch (n) {
                case 1:
                    g1 = us.findJavaGraph(v1path, "i");
                    g2 = us.findJavaGraph(v2path, "i");
                    System.out.println("\n******** VERSION 1 ********\n");
                    gv.consoleInterfaceDependenciesVisualizer(g1);
                    System.out.println("\n******** VERSION 2 ********\n");
                    gv.consoleInterfaceDependenciesVisualizer(g2);

                    System.out.println("\n Applying closure? [1/0] ");
                    c = reader.nextInt();
                    if (c == 1) {
                        g1.closure();
                        g2.closure();
                        System.out.println("\n******** VERSION 1 WITH CLOSURE ********\n");
                        gv.consoleInterfaceDependenciesVisualizer(g1);
                        System.out.println("\n******** VERSION 2 WITH CLOSURE ********\n");
                        gv.consoleInterfaceDependenciesVisualizer(g2);
                    }

                    break;
                case 2:
                    g1 = us.findJavaGraph(v1path, "im");
                    g2 = us.findJavaGraph(v2path, "im");
                    System.out.println("\n******** VERSION 1 ********\n");
                    gv.consoleInterfaceImplementationVisualizer(g1);
                    System.out.println("\n******** VERSION 2 ********\n");
                    gv.consoleInterfaceImplementationVisualizer(g2);

                    System.out.println("\n Applying closure? [1/0] ");
                    c = reader.nextInt();
                    if (c == 1) {
                        g1.closure();
                        g2.closure();
                        System.out.println("\n******** VERSION 1 WITH CLOSURE ********\n");
                        gv.consoleInterfaceImplementationVisualizer(g1);
                        System.out.println("\n******** VERSION 2 WITH CLOSURE ********\n");
                        gv.consoleInterfaceImplementationVisualizer(g2);
                    }
                    break;
                case 3:
                    g1 = us.findJavaGraph(v1path, "c");
                    g2 = us.findJavaGraph(v2path, "c");
                    System.out.println("\n******** VERSION 1 ********\n");
                    gv.consoleClassDependenciesVisualizer(g1);
                    System.out.println("\n******** VERSION 2 ********\n");
                    gv.consoleClassDependenciesVisualizer(g2);

                    System.out.println("\n Applying closure? [1/0] ");
                    c = reader.nextInt();
                    if (c == 1) {
                        g1.closure();
                        g2.closure();
                        System.out.println("\n******** VERSION 1 WITH CLOSURE ********\n");
                        gv.consoleClassDependenciesVisualizer(g1);
                        System.out.println("\n******** VERSION 2 WITH CLOSURE ********\n");
                        gv.consoleClassDependenciesVisualizer(g2);
                    }
                    break;
                case 4:
                    g1 = us.findJavaGraph(v1path, "cd");
                    g2 = us.findJavaGraph(v2path, "cd");
                    System.out.println("\n******** VERSION 1 ********\n");
                    gv.consoleCoupleVisualizer(g1);
                    System.out.println("\n******** VERSION 2 ********\n");
                    gv.consoleCoupleVisualizer(g2);

                    System.out.println("\n Applying closure? [1/0] ");
                    c = reader.nextInt();
                    if (c == 1) {
                        g1.closure();
                        g2.closure();
                        System.out.println("\n******** VERSION 1 WITH CLOSURE ********\n");
                        gv.consoleCoupleVisualizer(g1);
                        System.out.println("\n******** VERSION 2 WITH CLOSURE ********\n");
                        gv.consoleCoupleVisualizer(g2);
                    }
                    break;
                case 5:
                    g1 = us.findJavaGraph(v1path, "mc");
                    g2 = us.findJavaGraph(v2path, "mc");
                    System.out.println("\n******** VERSION 1 ********\n");
                    gv.consoleCallVisualizer(g1);
                    System.out.println("\n******** VERSION 2 ********\n");
                    gv.consoleCallVisualizer(g2);

                    System.out.println("\n Applying closure? [1/0] ");
                    c = reader.nextInt();
                    if (c == 1) {
                        g1.closure();
                        g2.closure();
                        System.out.println("\n******** VERSION 1 WITH CLOSURE ********\n");
                        gv.consoleCallVisualizer(g1);
                        System.out.println("\n******** VERSION 2 WITH CLOSURE ********\n");
                        gv.consoleCallVisualizer(g2);
                    }
                    break;
                case 6:
                    int n3 = 0;
                    while (n3 != 4) {
                        System.out.println
                                (
                                        "\n Which kind of entity do you want to choose? \n\n" +
                                                "1. Interface\n" +
                                                "2. Class\n" +
                                                "3. Method\n" +
                                                "4. Go back\n\n"
                                );
                        System.out.println("Your decision: ");
                        n3 = reader.nextInt();
                        while (n3 > 4 && n3 < 1) {
                            System.out.println("Your decision: ");
                            n3 = reader.nextInt();
                        }
                        manageClosureDecision(n3);
                    }
                    break;
                case 7:
                    int n2 = 0;
                    while (n2 != 5) {
                        System.out.println
                                   (
                                           "\n Which difference do you want to visualize? \n\n" +
                                           "1. Interface\n" +
                                           "2. Interface implementation\n" +
                                           "3. Class\n" +
                                           "4. Call graph\n" +
                                           "5. Go back\n\n"
                                   );
                       System.out.println("Your decision: ");
                       n2 = reader.nextInt();
                       while (n2 > 5 && n2 < 1)
                       {
                           System.out.println("Your decision: ");
                           n2 = reader.nextInt();
                       }
                        manageDiffdecision(n2);
                    }
                    break;
                case 8:
                    System.out.println("Have a nice day :)");
                    System.exit(0);
                    break;

            }
        }

    }

    private static void manageClosureDecision(int decision)
    {
        String input;
        String path = "";

        if(decision != 4)
        {
            System.out.print("\nOn which version of the code? [1/2]");
            int v = 0;
            while (v != 1 && v != 2) {
                v = reader.nextInt();
            }
            path = v == 1 ? v1path : v2path;
            System.out.println("\n\nThese are the available type for the selected kind:\n");
        }
        switch(decision)
        {
            case 1:
                g1 = us.findJavaGraph(path,"i");
                gv.consoleVertexVisualize(g1);
                System.out.println("\nType your selected types separated by ',' e.g.[A,B,C]:");
                input = reader.next();
                g1.closure();
                gv.consoleInterfaceDependenciesVisualizer(g1.findSubgraph(input));
                break;
            case 2:
                g1 = us.findJavaGraph(path,"c");
                gv.consoleVertexVisualize(g1);
                System.out.println("\nType your selected types separated by ',' e.g.[A,B,C]:");
                input = reader.next();
                g1.closure();
                gv.consoleClassDependenciesVisualizer(g1.findSubgraph(input));
                break;
            case 3:
                g1 = us.findJavaGraph(path,"mc");
                gv.consoleVertexVisualize(g1);
                System.out.println("\nType your selected types separated by ',' e.g.[A,B,C]:");
                input = reader.next();
                g1.closure();
                gv.consoleCallVisualizer(g1.findSubgraph(input));
                break;
            case 4:
                return;
        }
    }


    private static void manageDiffdecision(int decision)
    {
        switch (decision)
        {
            case 1:
                g1 = us.findJavaGraph(v1path,"i");
                g2 = us.findJavaGraph(v2path,"i");
                break;
            case 2:
                g1 = us.findJavaGraph(v1path,"im");
                g2 = us.findJavaGraph(v2path,"im");
                break;
            case 3:
                g1 = us.findJavaGraph(v1path,"c");
                g2 = us.findJavaGraph(v2path,"c");
                break;
            case 4:
                g1 = us.findJavaGraph(v1path,"cg");
                g2 = us.findJavaGraph(v2path,"cg");
                break;
            case 5:
                return;
        }

        System.out.println("\n Applying closure before differentiating? [1/0] ");
        int c = reader.nextInt();
        if(c == 1)
        {
            g1.closure();
            g2.closure();
        }
        System.out.println("\n******** DIFFERENCE IN VERSION ********\n");
        gv.computeIsomorphism(g1,g2);
    }
}