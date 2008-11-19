caGWAS Release Name: 1.0.1
Release Notes
=============
 
  #Product:#	caIntegrator-caGWAS
  #Version:#	1.0.1
  #Date:#	October 2008


Changes in Ver.1.0.1
---------------------------
Following domain objects & tables were updated in Ver 1.0.1

Domain Objects:
OddsRatio
SNPAssociationAnalysis

Tables:
odds_ratio
snp_analysis_lu
snp_association_analysis 

CSM schema is incorporated within caGWAS schema

  IMPORTANT NOTE: 
  A recent publication suggests that it is possible to determine whether
  a given genotype is a member of a cohort using summary statistics, such as
  allele frequency, in genome-wide association studies. 
  As a result, the NIH is recommending that, in addition to individual
genotypes,
  composite statistics in GWAS databases be privacy protected. To this effect, 
  the default installation of the caGWAS application has been altered to
install
  without publishing to the caGrid. In an upcoming release of caGWAS,
additional
  privacy controls will be added to improve grid security.
   
  Until this updated release where caGWAS implements a password protected grid
API,
  it is recommended that existing caGWAS databases storing human SNP
information be
  removed from the grid, and that new installations intending to study human
genomes 
  defer from publishing to the grid, per the default installation parameters .
It is 
  also recommended that PIs discuss these issues with their IRB prior to
importing 
  and sharing human data via caGWAS. For more information on this change, 
  please refer to the following:
	Article on GenomeWeb
(http://www.genomeweb.com/issues/news/149097-1.html). 
    Nils Homer, Szabolcs Szelinger, Margot Redman, David Duggan, Waibhav
Tembe,
    Jill Muehling, John V. Pearson, Dietrich A. Stephan, Stanley F. Nelson,
and David W. Craig. 
    Resolving individuals contributing trace amounts of DNA to highly complex
    mixtures using high-density SNP genotyping microarrays. 
    PLoS Genet. 2008 August; 4(8): e1000167. Published online 2008 August 29.
    doi: 10.1371/journal.pgen.1000167. PMCID: PMC2516199.
    (http://www.ncbi.nlm.nih.gov/sites/entrez?db=pmc&cmd=search&term=2516199
). 
    
    
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

* http://caintegrator-info.nci.nih.gov/bda-blueprints

Release History
------------------------
    * caGWAS  v1.0.1		-- October 2008

    * caGWAS  v1.0		    -- February 2008


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

---------------------------
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

https://gforge.nci.nih.gov/projects/bda-blueprints/

NCICB Web Pages
---------------

    * caIntegrator Home page http://caintegrator-info.nci.nih.gov/
    * The NCI Center for Bioinformatics, http://ncicb.nci.nih.gov/
    * NCICB Application Support, http://ncicb.nci.nih.gov/NCICB/support
    * NCICB Download Center, http://ncicb.nci.nih.gov/download/
