#!/bin/bash

cd /maven/legacy-ivy-repo/ivy-repo
find . -name "*.jar" |sort  > file_list.txt
svn --username saksass commit -m "Updated automatically" file_list.txt

cd /maven/legacy-ivy-repo
find techstack* -type f |grep -v "\.svn" |sort > file_list.txt
svn --username saksass commit -m "Updated automatically" file_list.txt

cd /maven/cbiit-ivy-repo
find . -name "*.jar" |sort  > file-list.txt
svn --username bda_user commit -m "Updated automatically" file-list.txt

