{\rtf1\ansi\ansicpg1252\cocoartf949\cocoasubrtf430
{\fonttbl\f0\fswiss\fcharset0 Helvetica;}
{\colortbl;\red255\green255\blue255;}
\margl1440\margr1440\vieww9600\viewh8400\viewkind0
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\ql\qnatural\pardirnatural

\f0\fs24 \cf0 This utility can branch/tag/merges any number of Subversion projects. The projects are specified as comma separated values for the property project.names in the project.properties. The repository details for the projects are specified in the project.properties. The property names should start with <project_name>. You will use the same project name as in the SVN repository. For example, for caGWAS, the name of the first project-specific property will be cagwas.tag.name (the name cagwas must match one of the comma-delimited values in project.names.\
\
The Utility can be checked out (via SVN) from:\
https://gforge.nci.nih.gov/svnroot/automation/trunk/software/svn-utilities\
\
To create Branches/Tags\
\
ant tag-branch:projects\
\
To Merge use: (Ensure your Subversion client is in the System path (e.g. svn.exe))\
\
ant merge:projects\
\
}