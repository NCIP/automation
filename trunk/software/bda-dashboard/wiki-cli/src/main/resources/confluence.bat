@echo off

rem Comments
rem - Customize for your installation, for instance you might want to add default parameters like the following:
rem   java -jar release/${project.artifactId}-${project.version}.jar --server http://my-confluence-server --user automation --password automation %*

java -jar release/${project.artifactId}-${project.version}.jar %*