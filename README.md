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
In detail, it's composed by 3 classes:

+ **main.Main**: this is the the core of the application, where the other classes are instantiated and used
+ **visitor.HalsteadVisitor**: this class extends the ASTVisitor class, in this way its possible to exploit polymorphism and use this custom class during the parsing by compilation unit. This class is in charge to visit the project structure, retrieve the files, parse them and count operands and operators. In order to achieve this last functionality several overloading versions of the method visit are provided. Each of those it's called when the AST node of the respective type is visited
+ **measure.HalsteadComplexity**: this class implements and provide all the measures being part of the Halstead Complexity

Further information about the methods and their behaviors can be found in the comment inside the code.

Functionalities
----------------

The application parse Java files up to JSL8 version. It identifies the basic 36 operators.

`=   >   <   !   ~   ?   :   -> 
 ==  >=  <=  !=  &&  ||  ++  --
 +   -   *   /   &   |   ^   %   <<   >>   >>>
 +=  -=  *=  /=  &=  |=  ^=  %=  <<=  >>=  >>>=`

And their operands, such as variables and functions.
As names are considered only variables and methods declared by the user.

Usage
----------------

To use the application, open the terminal and type as the following snippet of code, from the folder where the jar is located:

`java -jar cs474-hw1-HW1-ArnaboldiMarco-1.1.jar path/to/project/root`

It's important that the path doesn't contain white spaces.

Test
----------------

The tests and the application were developed in a OS X environment.

#### JUnit
All the tests were made automated by using JUnit. In particular a test suite for each of the *measure.HalsteadComplexity* and the *visitor.HalsteadVisitor* class were made. Each methods of both of them was tested except for the visitor methods since they were already safe. These tests are executed over a simple test case inside the resources folder. They ensure the correctness of the classes and their methods.

#### Other test
Another test, in order to validate the overall application, was made running it over a project retrieved from [OpenHub](https://www.openhub.net). The following application, a game developed in Java, was evaluated using the Halstead Complexity measures: [Marauroa](https://www.openhub.net/p/marauroa).
In order to run this test, proceed to the root of the project from the terminal an launch the following command:

`java -jar cs474-hw1-HW1-ArnaboldiMarco-1.1.jar marauroa-3.9.2/`

The result should be as follow:

~~~~
******* HALSTEAD COMPLEXITY MEASURES *******

Distinct operands = 1702
Distinct operators = 29
Total operands = 32546
Total Operators = 3099
Program Vocabulary = 1731.0
Program Length = 35645.0
Calculated program Length = 18408.473526368212
Volume = 278849.6756733894
Difficult = 90939.62068965517
Effort = 2.53584837351714E10
Time required = 1.4088046519539666E9 seconds
Bugs delivered = 92.9498918911298
*********************************************
~~~~

Acknowledgments
---------------
Some inspiration was taken by the [Eclipse JDT Tutorial](http://www.programcreek.com/2011/01/best-java-development-tooling-jdt-and-astparser-tutorials/). The code was rewritten and readapted in order to implement the described functionalities.