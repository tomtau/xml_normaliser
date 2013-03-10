XML Normaliser
=============

XML Normalisation according to 'A Normal Form for XML Documents' (XNF) by Libkin and Arenas


Papers
-------
Marcelo Arenas. Normalization Theory for XML. SIGMOD Record, 35(4): 57-64, 2006.
[pdf](http://web.ing.puc.cl/~marenas/publications/sr06.pdf)

Marcelo Arenas and Leonid Libkin. A Normal Form for XML Documents. ACM Transactions on Databases Systems, 29(1):195-232, 2004. (Selected papers from SIGMOD/PODS'02). [pdf](http://web.ing.puc.cl/~marenas/publications/xnf_tods04.pdf)

Millist W. Vincent, Jixue Liu, and Mukesh Mohania. The implication problem for ’closest node’ functional dependencies in complete XML documents. J. Comput. Syst. Sci., 78(4):1045–1098, July 2012.

Building
-------
In the project directory, run:

    ant

Running
-------
* Make sure you have graphviz-2.4 installed
After building in the project directory, run:

    GUI: java -jar build.jar
    CLI: java -jar build.jar <input DTD file> [root node] <input XFD file> [output DTD file] [output XFD file]

Testing
-------
There are a few unit tests. In order to run them, you can use the ant 'test' target:

    ant test
