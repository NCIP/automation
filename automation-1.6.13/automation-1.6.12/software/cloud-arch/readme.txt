# need the -e -E
 ./xml2dcm -e -E -o ~/0000000a.dicom -x ~/0000000a.dicom
Created DICOM File /data/home/ssaksa/0000000a.dicom
 ./dcm2xml -C ~/0000000a.dicom > ~/0000000a.xml
 diff ~/0000000a.xml ~/0000000a-mod.xml


