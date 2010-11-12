package gov.nih.nci.bda;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

public class DbValidationTag implements Tag {
    private PageContext pageContext;
    private Tag parent;

    public DbValidationTag() {
	super();
    }

    public int doStartTag() throws javax.servlet.jsp.JspTagException {
	return SKIP_BODY;
    }

    public int doEndTag() throws javax.servlet.jsp.JspTagException {
	try {
	    pageContext.getOut().write("Database Validation Successful!");
	} catch (java.io.IOException e) {
	    throw new JspTagException("IO Error: " + e.getMessage());
	}
	return EVAL_PAGE;
    }

    public void release() {
    }

    public Tag getParent() {
	return this.parent;
    }

    public void setParent(Tag arg0) {
	this.parent = arg0;
    }

    public PageContext getPageContext() {
	return pageContext;
    }

    public void setPageContext(PageContext arg0) {
	this.pageContext = arg0;
    }

}
