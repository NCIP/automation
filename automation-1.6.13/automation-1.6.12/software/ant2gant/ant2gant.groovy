#! /usr/bin/env groovy
// -*- coding:utf-8 mode:groovy -*-

/*
 *  ant2gant -- a script for converting Ant XML files into Gantfiles.
 *
 *   Copyright © 2007-8 Russel Winder
 *
 *   Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 *   compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software distributed under the License is
 *   distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *   implied.  See the License for the specific language governing permissions and limitations under the
 *   License.
 */

import org.apache.tools.ant.BuildException
import org.apache.tools.ant.Project
import org.apache.tools.ant.ProjectHelper
import org.apache.tools.ant.RuntimeConfigurable
import org.apache.tools.ant.util.CollectionUtils

def versionNumber = '0.1.2'

def buildFileName = 'build.xml'
     
def cli = new CliBuilder ( usage : 'ant2gant [option]* [input-file] [output-file]' )
cli.V ( longOpt : 'version' , 'Print out the version number and exit.' )
cli.h ( longOpt : 'help' , 'Print out this message and exit.' )
def options = cli.parse ( args )
if ( options.V ) { println versionNumber ; return 0 }
if ( options.h ) { cli.usage ( ) ; return 0 }
def arguments = options.arguments ( )
def argumentsCount = arguments.size ( )
def outputFileName = null
if ( argumentsCount > 0 ) {
  buildFileName = arguments[0]
  if ( argumentsCount > 1 ) { outputFileName = arguments[1] }
  if ( argumentsCount > 2 ) { System.err.println ( 'Excess parameters ignored.' ) }
}

def file = new File ( buildFileName )
if ( ! file.canRead ( ) ) {
  println ( 'Cannot find file named ' + buildFileName )
  return 1
}

indentSpacer = '  '
resultString = ''

def processIndividualTask ( level , task ) {
  resultString += indentSpacer * level
  resultString += task.elementTag
  if ( task.attributeMap ) {
    def string = ' ('
    task.attributeMap.each { key , value -> string += " , '" + key + "' : '" + value + "'" }
    string += ' )'
    resultString += string.replaceFirst ( ' ,' , '' )
  }
  if ( task.children.class != CollectionUtils.EmptyEnumeration ) { 
    resultString += ' {\n'      
    processTasks ( level + 1 , task.children )
    resultString += indentSpacer * level + '}'
  }
  if ( task.text.toString ( ).trim ( ) != '' ) {
    resultString += ' {\n'
    resultString += "'''" + task.text + "'''\n"
    resultString += indentSpacer * level + '}'
  }
  resultString += '\n'
}  

def processTasks ( level , tasks ) {
  tasks.each { task ->
    assert task.class == RuntimeConfigurable , 'Got a ' + task.class
    processIndividualTask ( level , task )
  }
}

def project = new Project ( )
 project.init ( )
// Added by saksa
 ProjectHelper.configureProject(project, file)
try { ProjectHelper.getProjectHelper ( ).parse ( project , file ) }
catch ( BuildException be ) { System.err.println ( be ) ; return 2 }
project.targets.each { name , target ->
  if ( name != '' ) {
    resultString += "target ( '${name}' : '${target.description ?: ''}' ) {\n"
    if ( target.dependencies.class != CollectionUtils.EmptyEnumeration ) {
      resultString += '  depends ('
      def string = ''
      target.dependencies.each { dependency -> string += " , '" + dependency + "'" }
      resultString += string.replaceFirst ( ' ,' , '' )
      resultString += ' )\n'
    }
    def level = 0
    if ( target.if ) {
      ++level
      resultString += indentSpacer * level + 'if ( ' + target.if + ' ) {\n'
    }
    if ( target.unless ) {
      ++level
      resultString += indentSpacer * level + 'if ( ! ( ' + target.unless + ' ) ) {\n'
    }
    processTasks ( level + 1 , target.tasks.wrapper )
    while ( level > 0 ) { resultString += indentSpacer * level + '}\n' ; --level }
    resultString += '}\n'
  }
  else {
    target.tasks.wrapper.each { task ->
      assert task.class == RuntimeConfigurable , 'Got a ' + task.class
      resultString += 'ant.'
      processIndividualTask ( 1 , task )
    }
  }
}

if ( project.defaultTarget ) { resultString += "setDefaultTarget ( '${project.defaultTarget}' )\n" }

if ( outputFileName ) { ( new File ( outputFileName ) ).write ( resultString ) }
else { print ( resultString ) }

return 0
