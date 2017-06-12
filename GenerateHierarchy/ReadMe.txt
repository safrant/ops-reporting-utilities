Last updated: 10/12/2012

GenerateHierarchy.exe
=====================

Generate a tabbed hierarcy and (if requested) an OWL file for an input file 
of the following format:

Concept ID <tab> Concept Display <tab> Parent ID
Concept ID <tab> Concept Display <tab> Parent ID
Concept ID <tab> Concept Display <tab> Parent ID
Concept ID <tab> Concept Display <tab> Parent ID
Concept ID <tab> Concept Display <tab> Parent ID
Concept ID <tab> Concept Display <tab> Parent ID
Concept ID <tab> Concept Display <tab> Parent ID
Concept ID <tab> Concept Display <tab> Parent ID

The identifiers in column A+C (first and third) must be of the same type
(both must be concept name, or both must be concept code).

To run by the command line or terminal:

1) On Windows, open a command window (Start->Run->'cmd').
   On a Mac, open a terminal window.

2) Change to the installed directory.

   On Windows this would be something like c:\> cd C:\GenerateHierarchy

3) The run command:

   To produce a text hierarchy and no OWL file:

   C:\GenerateHierarchy> GenerateHierarchy.exe -n [input.file] [root ID] [root label] [output.file]

   To produce a text hierarchy and an OWL file:

   C:\GenerateHierarchy> GenerateHierarchy.exe -o [input.file] [root ID] [root label] [output.file]

   You must use either -n or -o as the first argument.

The second command call demonstrates how to generate a simplistic/minimal OWL file based
on the input.  The OWL file will be named [root].owl.  File header.txt must exist in this
directory for the OWL to be written correctly.  This file may be added to and/or changed
over time by Operations.

Currently, generation assumes it is possible to travel every 
Concept ID via the parent association from the root 
(input is transitive).

Perl source code file printHierarchyPTWithOWL.pl has been included if 
running straight on the Mac is desired.

TODO: Add warnings for orphans, and other concepts that are unintentionally 
left out of hierarchy.  Also, fully test for cycles.