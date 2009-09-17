conditions
- database.type.list - update type 
- database.prefered - database.type and url set
- use.ldap
- use.jboss
- use.tomcat
- use.grid
  - install.properties
    - 
  - install.xml
    -
  - build.xml
    -
  - other
    - common/resources/grid
- use.gui-installer 
- use.maven

program flow
- read propertys
- download template archive from web
- extract template from web
- 
- build pattern lists
- pass 1 (build.xml, install.xml)
  - read file into string
  - remove all targets targets
    - set PATTERN.MULTILINE
    - use MATCHER.replaceAll(match,replace) to remove all targets
  - replaces patterns on string
  - write strings to file
- pass 2 (build.xml, install.xml)
  - read orig file into string
  - slurp out targets
  - build target add list
  - build target delete list
  - loop through add list 
    - clean up depends on delete lists
    - write to new file
- filter properties files (*properties *-properties.templates)
  - read file into string
  - replace patters on string (not multiline needed)
- copy gui-installer if needed
- copy common files
  - deleting resouce dirs as needed

 diff -c -w --ignore-all-space --ignore-blank-lines build.xml output/build.xml > t.diff
