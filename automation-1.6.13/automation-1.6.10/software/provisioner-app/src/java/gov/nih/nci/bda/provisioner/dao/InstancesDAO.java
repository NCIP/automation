package gov.nih.nci.bda.provisioner.dao;

import java.util.Iterator;
import java.util.List;

import gov.nih.nci.bda.provisioner.User;
import gov.nih.nci.bda.provisioner.util.HibernateUtil;
import gov.nih.nci.bda.provisioner.domain.Instances;

import org.hibernate.Query;
import org.hibernate.Session;


public class InstancesDAO {

	public void saveInstance(Instances instance)
	{
		//Session session = HibernateUtil.getSession();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

	    session.beginTransaction();
	    
	    session.save(instance);

	    session.getTransaction().commit();
	    //HibernateUtil.closeSession();
	}

	public List listInstancesByUserId(int userId)
	{
		//Session session = HibernateUtil.getSession();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		//Query query = session.createQuery("select instances.instanceId,instances.projectName,instances.instanceName,instances.instanceType from Instances as instances,USER as user where user.id = instances.userId and instances.userId='" + userId +"' order by instances.instanceId");
		Query query = session.createQuery(" from Instances as instances where instances.userId='" + userId +"' order by instances.instanceStatus");
		//and instances.instanceStatus != 'TERMINATED' 
		List<Instances> instances = query.list();
		System.out.println("COUNT::"+instances.size());
		Iterator ite = instances.iterator();
		while(ite.hasNext())
		{
			Instances is = (Instances) ite.next();

			System.out.println(is.getInstanceId());
			System.out.println(is.getInstanceName());
			System.out.println(is.getInstanceType());
			System.out.println(is.getProjectName());			
		}
		session.getTransaction().commit();
		//HibernateUtil.closeSession();
		return instances;	    
	}
	
	public void updateInstanceStatus(String instanceId)
	{
		//Session session = HibernateUtil.getSession();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	    session.beginTransaction();
		Query query = session.createQuery("update Instances set instanceStatus = 'TERMINATED' where instanceId = :instanceId" );
		query.setString("instanceId",instanceId);
	    int rowCount = query.executeUpdate();
	    System.out.println("Rows affected: " + rowCount);
	    session.getTransaction().commit();
	    //HibernateUtil.closeSession();
	}	
	
}
