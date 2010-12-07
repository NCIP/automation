How to use ant2gant.groovy
__________________________

BUILD.XML
* copy relevant files to folder 
** bda-build-template/software/build/* ant2gant/build/
** bda-build-template/software/target/bda-utils/* ant2gant/target/bda-utils
** bda-build-template/software/target/lib/ivy-2.0.0.jar ant2gant/target/lib/
** bda-build-template/software/common/bda-download ant2gant/common/bda-download
* make a classpath file with ivy first, then ant libs, then bda-utils
* source the classpath
* run from build folder
* execute it "ant2gant.groovy  build.xml build.gant"

INSTALL.XML
* run from target dist exploded 
* make a classpath file with ivy first, then ant libs, then bda-utils
* source the classpath
* execute it "ant2gant.groovy  build.xml build.gant"

