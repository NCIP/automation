package gov.nih.nci.cagrid.ncia.client;

/*
import gov.nih.nci.cagrid.cqlquery.CQLQuery;
import gov.nih.nci.cagrid.cqlresultset.CQLQueryResults;
import gov.nih.nci.cagrid.data.faults.MalformedQueryExceptionType;
import gov.nih.nci.cagrid.data.faults.QueryProcessingExceptionType;
import gov.nih.nci.cagrid.data.utilities.CQLQueryResultsIterator;
import gov.nih.nci.ivi.utils.ZipEntryInputStream;
*/

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
import java.rmi.RemoteException;
import java.util.zip.ZipInputStream;

import junit.framework.TestCase;
import org.cagrid.transfer.context.client.TransferServiceContextClient;
import org.cagrid.transfer.context.client.helper.TransferClientHelper;
import org.cagrid.transfer.descriptor.DataTransferDescriptor;


public class NCIACoreServiceClientTestCaseFunctional extends TestCase
{
	String gridServiceUrl = "http://localhost:21080/wsrf/services/cagrid/NCIACoreService";
	String clientDownLoadLocation ="NBIAGridClientDownLoad";

public void testRetrieveDicomDataBySeriesUID() throws Exception
{
	String seriesInstanceUID = "1.3.6.1.4.1.9328.50.1.8862";
	NBIAServiceClient client = new NBIAServiceClient(gridServiceUrl);
	InputStream istream = null;
	TransferServiceContextClient tclient = null;
	TransferServiceContextReference tscr = client.retrieveDicomDataBySeriesUID(seriesInstanceUID);
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
	int ii = 1;
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
		System.out.println(ii++ + " filenam = (bis.read(data, 0, data.length))) > 0)
		{
			bos.write(data, 0, bytesRead);
		}
		bos.flush();
		bos.close();
	}
	zis.close();
	tclient.destroy();
}

}
