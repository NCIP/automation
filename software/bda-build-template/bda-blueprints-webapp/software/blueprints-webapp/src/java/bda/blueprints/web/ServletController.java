package bda.blueprints.web;

import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bda.blueprints.business.domain.Study;
import bda.blueprints.business.service.StudyService;
import bda.blueprints.business.service.StudyServiceImpl;
import bda.blueprints.business.service.LoginService;
import bda.blueprints.business.service.LoginServiceImpl;
import bda.blueprints.business.service.UserException;

public class ServletController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String STUDY_PAGE = "study";

	private static final String LOGIN_PAGE = "index";

	private static final String EDIT_STUDY_PAGE = "editstudy";

	private static final String ERROR_PAGE = "error";

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		doPost(req, res);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		// getParameter is an example of getting a parameter from an HTML form
		String fromPage = req.getParameter("FROM_PAGE");
		String toPage = req.getParameter("TO_PAGE");
		String returnPage = null;
		System.out.println("In FROM_PAGE=" + fromPage);
		System.out.println("In TO_PAGE=" + toPage);
		processRequest(req);
		if (fromPage != null && fromPage.equalsIgnoreCase(LOGIN_PAGE)) {
			LoginService loginService = new LoginServiceImpl();
			try {
				loginService.login(req.getParameter("username"), req
						.getParameter("password"));
			} catch (UserException e) {
				toPage = ERROR_PAGE;
			}

			StudyService studyService = new StudyServiceImpl();
			StringBuffer studyBuffer = new StringBuffer("Index Study: ");
			Collection study = studyService.findAll();
			for (Iterator it = study.iterator(); it.hasNext();) {
				Study element = (Study) it.next();
				studyBuffer.append(element.getResearcher());
				studyBuffer.append("=" + element.getName());
				studyBuffer.append("</br>");
			}
			req.setAttribute("study", studyBuffer.toString());
			req.setAttribute("userMessage", "Logged in as "
					+ req.getParameter("username"));
		}

		if (fromPage != null && fromPage.equalsIgnoreCase(EDIT_STUDY_PAGE)) {
			Study study = new Study();
			study.setResearcher(req.getParameter("researcher"));
			study.setName(req.getParameter("study_name"));
			study.setDateReceived("2006-08-20");
			StudyService studyService = new StudyServiceImpl();
			studyService.create(study);

		}

		returnPage = toPage + ".jsp";
		System.out.println("returnPage=" + returnPage);
		RequestDispatcher rd = req.getRequestDispatcher(returnPage);
		rd.forward(req, res);
	}

	public Map processRequest(HttpServletRequest req) {
		Enumeration enumeration1 = null;
		Map attributeMap = new HashMap();
		enumeration1 = req.getParameterNames();
		do {
			if (!enumeration1.hasMoreElements())
				break;
			String s2 = (String) enumeration1.nextElement();
			if (s2 != null && (s2 instanceof String)) {
				String s3 = req.getParameter(s2);
				System.out.println("ELEMENTs2:" + s2);
				System.out.println("ELEMENTs3:" + s3);
				attributeMap.put(s2, s3);
			}
		} while (true);
		return attributeMap;
	}

}
