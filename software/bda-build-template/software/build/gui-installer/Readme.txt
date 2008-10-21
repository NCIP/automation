Release Name: caGWAS 1.0

Notes:
caGWAS Release Name: 1.0
Notes:
Release Notes
=============
 
  #Product:#	caIntegrator-caGWAS
  #Version:#	1.0
  #Date:#	February 2008

Contents
--------

   1. Introduction
   2. Release History
   3. Features
   4. Known Issues/Defects
   5. Bug Reports, Feature Requests, and Support
   6. Documentation and Files
   7. NCICB Web Pages

Introduction
---------------------------
 
Cancer Genome-Wide Association Studies (caGWAS) allows 
researchers to integrate, query, report, and analyze significant 
associations between genetic variations and disease, drug 
response or other clinical outcomes. New breakthroughs in SNP 
array technologies make it possible to genotype hundreds of 
thousands of single nucleotide polymorphisms (SNPs) 
simultaneously, enabling whole genome association studies.
Within the Clinical Genomic Object Model (CGOM), the caIntegrator 
team created a domain model for Whole Genome Association Study 
Analysis. CGOM-caGWAS is a semantically annotated domain model 
that captures associations between Study, Study Participant, 
Disease, SNP Association Analysis, SNP Population Frequency and 
SNP annotations.

* https://caintegrator.nci.nih.gov/cgems

Release History
------------------------
 
    * caGWAS  v1.0		-- February 2008


Features
------------------------

This release represents a new generation of the caIntegrator’s GWAS 
portal. It is build upon the CGEMS study use cases but has been 
generalized to be platform and disease agnostic 


Highlights of caGWAS 1.0 include:


Following the principals of caBIG, caGWAS APIs and web portal 
provide:
* A semantically annotated domain model, database schema with 
sample data, seasoned middleware, APIs, and web portal for GWAS 
data; 
* Platform and disease agnostic CGOM-caGWAS model and associated 
APIs;
* The opportunity for developers to customize the look and feel 
of their GWAS portal;
* A foundation of open source technologies;
* A well-tested and performance-enhanced platform, as the same 
software is being used to house the CGEMS data portal at 
https://caintegator.nci.nih.gov/cgems;
* Accelerated analysis of results from various biomedical 
studies; and
* A single application through which researchers and 
bioinformaticians can access and analyze clinical and 
experimental data from a variety of data types, as caGWAS objects 
are part of the CGOM, which includes microarray, genomic,
immunohistochemistry, imaging, and clinical data.


* Java API and Grid Service

The Java and caGRID APIs for caGWAS were generated using caCORE SDK 
version 3.2.1 and caGRID Introduce toolkit version 1.1


* Installation

caGWAS 1.0 provides a simple ant based installation script and has been 
tested on 64 bit, 32 bit and Windows XP platforms. The installation 
also includes a mySQL dump file with sample GWAS data to allow 
application validation.

Known Issues/Defects
------------------------
  
See the GForge tracker for the latest use cases, existing open defects, 
community requests, resolutions and feature requests. The following 
issues are highlighted. 


https://gforge.nci.nih.gov/tracker/?group_id=305

[#10743] If you select any item from “Population” only the query 
doesn’t bring any records.
[#11721] LSD - Exception/Error in server.log file for browse data query 
- results still displayed correctly

Bug Reports, Feature Requests, And Support
------------------------------------------

Send email to ncicb@pop.nci.nih.gov to request support or report a bug 
or request a new feature. Existing requests and resolution may be 
viewed at the caGWAS GForge URL:

    * https://gforge.nci.nih.gov/tracker/?group_id=379

Documentation And Files
-----------------------

Links to all documentation and files can be found at: 

https://gforge.nci.nih.gov/projects/cagwas/

NCICB Web Pages
---------------

    * caIntegrator Home page http://caintegrator-info.nci.nih.gov/
    * The NCI Center for Bioinformatics, http://ncicb.nci.nih.gov/
    * NCICB Application Support, http://ncicb.nci.nih.gov/NCICB/support
    * NCICB Download Center, http://ncicb.nci.nih.gov/download/



