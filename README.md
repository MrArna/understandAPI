CS474 @ UIC: HOMEWORK2
====================
Developed by Marco Arnaboldi (marnab2@uic.edu)

#Description
--------------------
Your second homework assignment is to create a program for automatically analyzing the relationships among types in software applications. You will create and run your software application using the Application Programming Interface (API) of the tool called [Understand](https://scitools.com/non-commercial-license/), a static code analysis tool that supports many programming languages and it is used by many Fortune 500 companies. You should apply for a non-commercial license immediately, install the tool, and investigate its IDE and its API libraries. You can complete this homework using either Java or Scala or Python (I prefer that you use Java for this assignment). You will use SBT for building the project. You can use the latest community version of IntelliJ IDE for this assignment.
The goal of this homework is to gain hands-on experience analyzing the source code of object-oriented applications statically to obtain information about relationships among types in these applications. The input to your program is the source code of two consecutive versions of the same open-source application of your choice written in an object-oriented language, which is supported by Understand. Your application should contain more than 1,000 lines or code and more than a dozen of classes, interfaces, or traits. It should be created and entered in a popular open-source repository of your choice at least two years ago. If you have doubts, please contact the instructor or the TA for this course.
Using the Understand API calls, your program will process these versions and construct dependency and call graphs. Then, using [JGraphT](http://jgrapht.org/), an open-source graph library that provides mathematical graph-theory objects and algorithms, you will compute graph mappings between the two versions of the application. That is, you will consider the next version of the application as a subgraph of the first version and you will compute a subgraph isomorphic case, where the second one is assumed to be a subgraph of the first one. In addition, for selected input parameters in a method/types in your chosen open-source application, you will compute the transitive closure for the selected types. Finally, the output of your program is a list of differences between the versions of the application along with the subgraph that represents the transitive closure. You can vary the output to show the divergences between the graphs, use your imagination.

#Development & Design choices
-----------------
The application was developed with IntelliJ, with the use of SBT in order to manage the libraries and to create a fat jar. It has been designed in order to be as extendable as possible.
In detail, it's composed by 2 modules and different classes:

+ *Main*: this is the the core of the application, where the other classes are instantiated and used. It is in charge to expose a UI to the user and to call the right methods depending on the user choices.
+ **Entities**: this module contains the classes representing the graph and its edges
    + *RelationshipEdge*: this class represent an edge between two nodes and it relationship. The node type is parametric.
    + *EnityGraph*: this class represents the entity graph. It provides methods to populate and create the graph with edges and nodes. It also provide methods in order to apply the closure over the graph and retrieving a subgraph given a list of nodes.
+ **Services**: this module contains useful classes to visualize graph and to use Understand APIs.
    + *GraphVisualizer*: this class provide method to visualize graph in a graphical way via terminal. It also provides a method to find and represent isomorphism between two graphs.
    + *UnderstandService*: this class is in charge to manage the Understan APIs. In particular it provides methods to load ".udb" databases and to query over them. It also provides methods that integrate different API calls in order to retrive a graph of the specified relationship.

Further information about the methods and their behaviors can be found in the comment inside the code.

#Functionalities
----------------
The application analyze two ".udb" files of a two given Java application.
The application expose an UI via terminal, enabling the user to choose different relationship on which building a graph. In details:

~~~~
------> VERSION ANALIZER <------

1. Show Interface hierarchy
2. Show Interface Implementation
3. Show Class hierarchy
4. Show Class dependency
5. Show Call Graph
6. Show closure over type
7. Show difference between versions
8. Exit

Your decision: 
~~~~

The first 3 options and the 5th are self explanatory. The 4th allows to analyze how the classes are coupled, it is building over the "Couple" reference kind provided by Understand API user manual. The 6th allows the user to choose a set of types from a given list of types retrieved by the analyzed app and to compute and close the graph only over them, if a wrong type is passed, it is simply ignored. The 7th show difference between the two apps passed via command line parameters, it exploit isomorphism to find the difference. __Since in JGrapht the isomorphism is computed over the graph for all the possibile substructure, it requires a lot of time to complete. I suggest to not use this functionality over a huge project, it may cause exception raising.__

#Usage
----------------

######PREREQUISITES

Understand should be installed and configured in the hosting system. A tutorial for OS X could be found on Piazza.

######USE
To use the application, open the terminal and type as the following snippet of code, from the folder where the jar is located:

`java -jar understandAnalizer-Arnaboldi-assembly-final.jar [-v1 path/to/version/1/udbfile -v2 path/to/version/2/udbfile]`

It's important that the path doesn't contain white spaces. If the two version are not specified, the application runs in tutorial mode, two standard example are loaded from the resources in the fat jar.

#Test
----------------

The tests and the application were developed in a OS X environment.

#### JUnit
All the tests were made automated by using JUnit. In particular these test were used to ensure the correct behavior of the service and entities classes. To evaluate the service classes two UDBs extracted by the a two simple fake applications were used. They are located in the resources folder under the test. 

#### Other test
Another test, in order to validate the overall application, was made running it over a project retrieved from [OpenHub](https://www.openhub.net). The following application, a game developed in Java, was evaluated in two consecutive versions: [Marauroa](https://www.openhub.net/p/marauroa). The corresponding UDB files are located in the resources folder under "main.java". The UDB files for this project can be found in the root of the repository. This test showed the complexity of some operation, like closure and difference by isomorphism, since how the are implemented in *JGrapht* library -in particular for what concern isomorphism- makes the problem to find the isomorphic graph in a huge graph a NP-complete problem, hence takes a lot of time to compute the isomorphism given two huge problem. My suggestion is to test the isomorphism functionalities to significant smaller project, in term of graph dimension.

#Acknowledgments
---------------
Some inspiration was taken by the [Understand API user guide](http://scitools.com/documents/manuals/pdf/understand.pdf). The code was rewritten and readapted in order to implement the described functionalities.