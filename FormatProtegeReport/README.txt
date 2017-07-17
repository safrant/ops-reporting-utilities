RWW: This file was last updated on 7/17/17

FormatProtegeReport v1.0
========================
This program will take a Protege 5 report and format it to specifications
declared in a configuration file.  The input is the raw report from Protege
and the output is an Excel .xls file.

Requirements:
Java 1.8 or higher

Instruction of use
------------------
1. Export a report from Protege 5 based on any number of columns, specified
by a slot and optional qualifiers.  Currently, columns must be delimited by a tab, and values
must be delimited by the vertical bar.  Save this file anywhere on your file system. It is
suggested to save it to this folder for ease of command-line entry.

2. Create or modify a config file to your desired output specifications.  The default config
file (which will work with the example input TestReport1) is in the config folder.  The program
will default to using this config file if none is specified from the command-lin.

Each column of the desired output is configurable.  The parameters expected by each column include
the following, followed by an equals sign, and then the desired value.  All entries for a column
should begin with ColumnNumber.

ColumnNumber=<integer>
Property=<the property code in the raw file>
*QualifierName=<a qualifier name of a property value in the raw file>
*QualifierValue=<the qualifier value in the raw file>

* there may be zero or more Qualifiers specified for a column.  If none is
specified, all properties will be ouput (without JSON markup if qualifiers exist).

Any configuration file with errors in it will be reported by the program.  For example, if
there is a typo on QualifierName (e.g. QualferNAme) the program will abort letting you know
of the erroneous line.

3. Once you have your raw input file from Protege and configuration file set, the program is
ready to be run.

4. Open the command-line on Windows, or a terminal on Mac/Linux and change directory to this folder (a shortcut
   to the cmd prompt is included for convenience if using Windows that will open in this directory).

   - If on Windows, start by typing FormatProtegeReport.bat.
   - If on Mac/Linux, start by typing FormatProtegeReport.sh

   Follow this script by typing the optional and required parameters as such:

   - Windows:
   C:\FormatProtegeReport>FormatProtegeReport [OPTIONS] [RAW REPORT INPUT FILE] [EXCEL OUTPUT FILE]

   - Mac:
   $./FormatProtegeReport.sh [OPTIONS] [RAW REPORT INPUT FILE] [EXCEL OUTPUT FILE]

   Example from the included test input file:

   C:\>FormatProtegeReport TestInput1 TestReport.xls

   If you would like to create another config file and use it, the -C or --config option may be used with
   the location of the config file specified.

   C:\>FormatProtegeReport -C ".\config\mycustomconfig.txt" TestInput1 TestReportCustom.xls
   $./FormatProtegeReport.sh -C "./config/mycutomconfig.txt" TestInput1 TestReportCustom.xls



Known issues
------------
The ColumnNumber is currently meaningless.  The ordering of columns will be top to bottom
of the config file.

Bug reports and feature requests may be submitted to Rob for future releases.