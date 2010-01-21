package gov.nih.nci.cagrid.ncia.client;

/*
import gov.nih.nci.cagrid.cqlquery.CQLQuery;
import gov.nih.nci.cagrid.cqlresultset.CQLQueryResults;
import gov.nih.nci.cagrid.data.faults.MalformedQueryExceptionType;
import gov.nih.nci.cagrid.data.faults.QueryProcessingExceptionType;
import gov.nih.nci.cagrid.data.utilities.CQLQueryResultsIterator;
*/

import gov.nih.nci.ivi.utils.ZipEntryInputStream;

/*
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
*/

import java.rmi.RemoteException;
import java.util.zip.ZipInputStream;

import java.io.*;
import junit.framework.TestCase;
import org.cagrid.transfer.context.client.TransferServiceContextClient;
import org.cagrid.transfer.context.client.helper.TransferClientHelper;
import org.cagrid.transfer.descriptor.DataTransferDescriptor;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.Date;
import java.text.SimpleDateFormat;


public class NCIACoreServiceClientTestCaseFunctional 
{
	String envPrefix;
	String patientID;
	String gridServiceUrl;
	String clientDownLoadLocation ="NBIAGridClientDownLoad";
	String currentDateTime;

	public NCIACoreServiceClientTestCaseFunctional()
	{
		Date curTime = new java.util.Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HHmmss");
		currentDateTime = formatter.format(curTime);

		Properties props = new Properties();
		try {
			props.load(new FileInputStream("nbia-perf.properties"));
			envPrefix = props.getProperty("env");
			patientID = props.getProperty("patient.id");
			gridServiceUrl = props.getProperty("grid.service.url");
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void main (String [] args) throws Exception
	{
		NCIACoreServiceClientTestCaseFunctional x = new NCIACoreServiceClientTestCaseFunctional();
		x.testRetrieveDicomDataByPersonID();
	}

public void testRetrieveDicomDataByPersonID() throws Exception
{
	long startq = System.currentTimeMillis();
	NCIACoreServiceClient client = new NCIACoreServiceClient(gridServiceUrl);
	InputStream istream = null;
	TransferServiceContextClient tclient = null;
	//TransferServiceContextReference tscr = client.retrieveDicomDataBySeriesUID(seriesInstanceUID);
	org.cagrid.transfer.context.stubs.types.TransferServiceContextReference tscr = client.retrieveDicomDataByPatientId(patientID);
	long endq = System.currentTimeMillis();
	long qtime=(endq - startq);
	System.out.println("Submit Query " + (endq - startq) + " milli seconds");
	long start = System.currentTimeMillis();
	tclient = new TransferServiceContextClient(tscr.getEndpointReference());
	istream = TransferClientHelper.getData(tclient.getDataTransferDescriptor());
	if(istream == null)
	{
		System.out.println("istrea is null");
		return;
	}
	ZipInputStream zis = new ZipInputStream(istream);
	ZipEntryInputStream zeis = null;
	BufferedInputStream bis = null;
	int zipCount = 1;
	while(true)
	{
		try
		{
			zeis = new ZipEntryInputStream(zis);
		}
		catch (EOFException e)
		{
			break;
		}
		String unzzipedFile = downloadLocation();
		System.out.println(zipCount++ + " filename: " + zeis.getName());
		              
		bis = new BufferedInputStream(zeis);
		              
		byte[] data = new byte[8192]; 
		int bytesRead = 0;
		try
		{             
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(unzzipedFile + File.separator + zeis.getName()));
			                                
			while ((bytesRead = (bis.read(data, 0, data.length))) > 0)
			{
				bos.write(data, 0, bytesRead);                  
			}                                 

			bos.flush();
			bos.close();
		}
		catch (IOException e)
		{
			System.out.println("IOException " + e);
		}

	}
	zis.close();
	tclient.destroy();
	long end = System.currentTimeMillis();
	long dtime=(end - start);
	System.out.println("Total time download images is " + (end - start) + " milli seconds");
	try{
		FileWriter fstream = new FileWriter("nbia-perf.csv",true);
		BufferedWriter out = new BufferedWriter(fstream);
		out.write(currentDateTime+","+envPrefix+","+(zipCount-1)+","+qtime+","+dtime+"\n");
		out.close();
	}catch (Exception e){//Catch exception if any 
		System.err.println("Error: " + e.getMessage());
	}   

}

private String downloadLocation()
{
	String localClient= System.getProperty("java.io.tmpdir") + File.separator + clientDownLoadLocation;
	if(!new File(localClient).exists())
	{
		new File(localClient).mkdir();
	}
	System.out.println("Local download location: "+localClient); 
	return localClient;
}

}
