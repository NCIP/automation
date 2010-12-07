
package gov.nih.nci.bda.calculator.validator;
/**
 *
 * @author Mahidhar Narra
 */

class CreateInstanceCommand {

   int averageAutomatedCodeCoveragePersentage
   int numberOfProjectsInPortfolio
   int averageNumberOfDeploymentsPerProjectPerYear
   int averageSizeOfProjects
   int numberOfTargetEnvironmentsPerProject
   int averageTechnicalArchitectureComplexity
   int averageEngineerHourlyRate            
         
   static constraints = {
           averageAutomatedCodeCoveragePersentage(blank:false)
           numberOfProjectsInPortfolio(blank:false)
           averageNumberOfDeploymentsPerProjectPerYear(blank:false)
           averageSizeOfProjects(blank:false)                      
           numberOfTargetEnvironmentsPerProject(blank:false)
           averageTechnicalArchitectureComplexity(blank:false)
           averageEngineerHourlyRate(blank:false)                      
   }
		
}
