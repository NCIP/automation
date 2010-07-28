/**
 * Copyright (c) 2006, 2008 Bob Swift
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 * 			 notice, this list of conditions and the following disclaimer in the
 *   		 documentation and/or other materials provided with the distribution.
 *     * The names of contributors may not be used to endorse or promote products
 *           derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * Created on March 21, 2008 by Bob Swift
 */

package org.swift.confluence.cli;

import org.swift.common.cli.soap.AbstractSoapClient;

import com.dolby.atlassian.confluence.soap.model.confluence.*;

// see http://sourceforge.net/projects/jsap
import com.martiansoftware.jsap.JSAP;          // constants
import com.martiansoftware.jsap.FlaggedOption;
import com.martiansoftware.jsap.Switch; 

import java.rmi.RemoteException;
import java.util.Calendar;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.lang.Long;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

import javax.activation.MimetypesFileTypeMap;
import javax.xml.rpc.ServiceException;

/**
 * Soap Client
 * - parseArguments function for parameters, defaults, and documentation
 * - usage
 *          java -jar release/confluence-cli-x.x.x.jar --help
 *
 * - see the README.txt file for more information
 * - to reuse login token
 *          confluence --action login > token
 *          confluence --action xxxxx -x < token
 */
public class ConfluenceClient extends AbstractSoapClient {

    private ConfluenceSoapService service;
    
    protected static final String PAGE_PERMISSION_VIEW = "View";  // this is case sensitive
    protected static final String PAGE_PERMISSION_EDIT = "Edit";  // this is case sensitive

    /**
     * Command entry point - use as model for subclass implementation
     * @param args - command line arguments
     */	
    public static void main(String[] args) {
        if ((args == null) || (args.length == 0)) {
            args = new String[] {"--help"};
        }

    	ExitCode code = new ConfluenceClient().doWork(args);

    	System.exit(code.value());
    }

    /**
     * Get the name of the client that will appear in help text - command name
     * @return client name
     */
    protected String getClientName() {
    	return "confluence";
    }
    
    /**
     * Extension to server address that is the location of the service to use
     * - this is the default location that the user can override by parameter
     * @return - service url extention - example:  /api/rest/
     */
    protected String getDefaultServiceExtension() {
    	return "/rpc/soap-axis/confluenceservice-v1";    // Confluence soap service
    }
    
    /**
     * Setup for remote service
     * - this must be overridden by subclass 
     * - Example for Confluence:
     *     ConfluenceSoapServiceServiceLocator serviceLocator = new ConfluenceSoapServiceServiceLocator();
     *     serviceLocator.setConfluenceserviceV1EndpointAddress(address);
     *     service = serviceLocator.getConfluenceserviceV1();
     */
    protected void serviceSetup(String address) throws ClientException, RemoteException {
    	
    	try {
	        ConfluenceSoapServiceServiceLocator serviceLocator = new ConfluenceSoapServiceServiceLocator();
	        serviceLocator.setConfluenceserviceV1EndpointAddress(address);
	        // serviceLocator.setMaintainSession(true); // not sure this applies
	        service = serviceLocator.getConfluenceserviceV1();
    	} catch (ServiceException exception) {
    		throw new RemoteServiceException(exception.getMessage());
    	}
    }
    
    /**
     * Login to the remote service
     * - this must be overridden by subclass 
     * - Example for soap:
     *     String user = getString("user");
     *     String password = getString("password");
     *     token = service.login(user, password);
     *     if (verbose) {
     *         out.println("Successful login to: " + address + " by user: " + user);
     *     }
     */
    protected void serviceLogin() throws ClientException, RemoteException {
        String user = getString("user");
        String password = getString("password");
    	token = service.login(user, password);
        if (verbose) {
          	out.println("Successful login to: " + address + " by user: " + user);
        }
    }
    
    /**
     * Logout of  remote service
     * - this must be overridden by subclass 
     * - Example for soap:
     *     service.logout(token);
     */
    protected void serviceLogout() throws RemoteException {
        service.logout(token);
    }
    
    /**
     * Enumerate all the valid actions
     * - all command actions must be listed in this enumeration
     * - this needs to be overridden in a subclass 
     * - borrow switch technique from http://www.xefer.com/2006/12/switchonstring
     */
    protected enum Action {
    	LOGIN,
    	LOGOUT,
        
        ADDPAGE,
        STOREPAGE,
        REMOVEPAGE,
        COPYPAGE,
        RENAMEPAGE,
        GETSOURCE,
        GETPAGESOURCE,
        RENDER,
        RENDERPAGE,

        ADDATTACHMENT,
        GETATTACHMENT,
        REMOVEATTACHMENT,

        ADDCOMMENT,

        ADDLABELS,
        REMOVELABELS,

        ADDNEWS,
        ADDBLOG,

        STORENEWS,
        STOREBLOG,
        REMOVENEWS,
        REMOVEBLOG,
        GETNEWSSOURCE,
        GETBLOGSOURCE,
        RENDERNEWS,
        RENDERBLOG,

        ADDUSER,
        ADDUSERWITHFILE,
        REMOVEUSER,
        REMOVEUSERWITHFILE,
        ADDGROUP,
        REMOVEGROUP,
        ADDUSERTOGROUP,
        ADDUSERTOGROUPWITHFILE,
        REMOVEUSERFROMGROUP,
        REMOVEUSERFROMGROUPWITHFILE,

        ADDPERMISSIONS,
        REMOVEPERMISSIONS,
        COPYPERMISSIONS,
        REMOVEALLPERMISSIONSFORGROUP,

        ADDSPACE,
        REMOVESPACE,
        GETSPACE,

        GETSPACELIST,
        GETPAGELIST,
        GETNEWSLIST,
        GETLABELLIST,
        GETPERMISSIONLIST,
        GETUSERLIST,
        GETGROUPLIST,

        EXPORTSPACE,
        EXPORTSITE,

        LOADFILES,
    	
    	NOTFOUND;   // the last one

        private static Action toAction(String string) {
            try {
                return valueOf(string);
            } catch (Exception exception) {
                return NOTFOUND;
            }
        }
    }

    /**
     * Map the user request to implementing code
     * - this should be overridden by subclass for specific handling
     * @return - the output from the request that will be put on the out stream 
     */
    protected String handleRequest() throws ClientException, RemoteException, java.rmi.RemoteException {

    	String action = getString("action").toUpperCase();
    	
    	switch (Action.toAction(action)) {

    	    case LOGIN                            : return login(); 
    	    case LOGOUT                           : return logout();

    	    // Page actions
    	    case ADDPAGE                          : return storePage(jsapResult.userSpecified("replace"));
        	case STOREPAGE                        : return storePage(true);
            case REMOVEPAGE                       : return removePage();
            case COPYPAGE                         : return copyPage();
        	case RENAMEPAGE                       : return renamePage();
        	case GETSOURCE                        :
            case GETPAGESOURCE                    : return getSource(false);
            // Render actions
            case RENDER                           :
            case RENDERPAGE                       : return renderItem(false);

        	// Attachment actions
            case ADDATTACHMENT                    : return addAttachment();
        	case GETATTACHMENT                    : return getAttachment();
            case REMOVEATTACHMENT                 : return removeAttachment();
            // Comment actions
            case ADDCOMMENT                       : return addComment();
            // Label actions
    	    case ADDLABELS                        : return addLabels();
        	case REMOVELABELS                     : return removeLabels();
            // News (blog) actions
            case ADDNEWS                          :
        	case ADDBLOG                          : return storeNews(jsapResult.userSpecified("replace"));
            case STORENEWS                        :
            case STOREBLOG                        : return storeNews(true);
            case REMOVENEWS                       :
        	case REMOVEBLOG                       : return removeNews();
            case GETNEWSSOURCE                    :
            case GETBLOGSOURCE                    : return getSource(true);
            case RENDERNEWS                       :
        	case RENDERBLOG                       : return renderItem(true);
            // User and group management
        	case ADDUSER                          : return addUser();
            case ADDUSERWITHFILE                  : return addUserWithFile();
        	case REMOVEUSER                       : return removeUser();
            case REMOVEUSERWITHFILE               : return removeUserWithFile();
        	case ADDGROUP                         : return addGroup();
            case REMOVEGROUP                      : return removeGroup();
            case ADDUSERTOGROUP                   : return addUserToGroup();
        	case ADDUSERTOGROUPWITHFILE           : return addUserToGroupWithFile();
        	case REMOVEUSERFROMGROUP              : return removeUserFromGroup();
        	case REMOVEUSERFROMGROUPWITHFILE      : return removeUserFromGroupWithFile();
            // Permissions
            case ADDPERMISSIONS                   : return doPermissions(true);
        	case REMOVEPERMISSIONS                : return doPermissions(false);
            case COPYPERMISSIONS                  : return copyPermissions();
            case REMOVEALLPERMISSIONSFORGROUP     : return removeAllPermissionsForGroup();
            // Space actions
        	case ADDSPACE                         : return addSpace();
            case REMOVESPACE                      : return removeSpace();
        	case GETSPACE                         : return getSpace();
            // Lists
            case GETSPACELIST                     : return getSpaceList();
        	case GETPAGELIST                      : return getPageList();
            case GETNEWSLIST                      : return getNewsList();
        	case GETLABELLIST                     : return getLabelList();
            case GETPERMISSIONLIST                : return getPermissionList();
            case GETUSERLIST                      : return getUserList();
            case GETGROUPLIST                     : return getGroupList();
            // Export
            case EXPORTSPACE                      : return exportSpace(getString("user"), getString("password"));
            case EXPORTSITE                       : return exportSite(getString("user"), getString("password"));

            case LOADFILES                        : return loadFiles();

    	    case NOTFOUND                         :
	        default                               : throw new InvalidActionClientException("Invalid action specified: " + action + ".  Use --help for more information.");
    	}
    }

    /**
     * Map the action to the action help text 
     * - this should be overridden by subclass for specific handling
     * @action - action name
     * @return - the formatted help text for the action 
     */
    protected String getActionHelp(Action action) {   	
    	
    	switch (action) {
    	                                                   // name, help, required parameters, optional parameters
    		case LOGIN                            : return formatActionHelp("login", "Login to remote server. Returns login token.", "password", "user");
    		case LOGOUT                           : return formatActionHelp("logout", "Logout of remote server.", "", "");    	    
  
    	    // Page actions
    	    case ADDPAGE                          : return formatActionHelp("addPage", "Add a new page.", "space, title, content, file", "parent, labels, replace, findReplace");
        	case STOREPAGE                        : return formatActionHelp("storePage", "Create or update a page.", "space, title, content, file", "parent, labels, findReplace");
            case REMOVEPAGE                       : return formatActionHelp("removePage", "Remove a page and, optionally, all descendents.", "space, title", "descendents");
            case COPYPAGE                         : return formatActionHelp("copyPage", "Create or update a page from a source page.", "space, title, newSpace or newTitle", "parent, labels, replace, findReplace");
        	case RENAMEPAGE                       : return formatActionHelp("renamePage", "Rename or move a page.", "space, title, newTitle", "parent");
        	case GETSOURCE                        :
            case GETPAGESOURCE                    : return formatActionHelp("getSource", "Get page or news wiki text. Put to a file if specified.", "space, title", "file, news, dayOfMonth");
            // Render actions
            case RENDER                           :
            case RENDERPAGE                       : return formatActionHelp("render", "Render page or news.", "space, title", "file, news, dayOfMonth");

        	// Attachment actions
            case ADDATTACHMENT                    : return formatActionHelp("addAttachment", "Add an attachment.", "space, title, file or content and name", "mime, comment");
        	case GETATTACHMENT                    : return formatActionHelp("getAttachment", "Get an attachment and put to a file.", "space, title, name, file", "");
            case REMOVEATTACHMENT                 : return formatActionHelp("removeAttachment", "Remove an attachment.", "space, title, name", "");
            // Comment actions
            case ADDCOMMENT                       : return formatActionHelp("addComment", "Add a comment to a page or news.", "space, title, comment or content or file", "");
            // Label actions
    	    case ADDLABELS                        : return formatActionHelp("addLabels", "Add labels to a page, news or space.", "space, labels", "title, news, dayOfMonth");
        	case REMOVELABELS                     : return formatActionHelp("removeLabels", "Remove labels to a page, news or space.", "space, labels", "title, news, dayOfMonth");
            // News (blog) actions
            case ADDNEWS                          :
        	case ADDBLOG                          : return formatActionHelp("addNews", "Add a news (blog) entry.", "space, title, file or content", "dayOfMonth, labels, replace");
            case STORENEWS                        :
            case STOREBLOG                        : return formatActionHelp("storeNews", "Add or update a news (blog) entry.", "space, title, file or content", "dayOfMonth, labels, replace");
            case REMOVENEWS                       :
        	case REMOVEBLOG                       : return formatActionHelp("removeNews", "Remove a news (blog) entry.", "space, title", "dayOfMonth");
            case GETNEWSSOURCE                    :
            case GETBLOGSOURCE                    : return formatActionHelp("getNewsSource", "Get wiki text for a news (blog) entry. Put to a file if specified.", "space, title", "dayOfMonth, file");
            case RENDERNEWS                       :
        	case RENDERBLOG                       : return formatActionHelp("renderNews", "Render a news (blog) entry. Put to a file if specified.", "space, title", "dayOfMonth, file");
            // User and group management
        	case ADDUSER                          : return formatActionHelp("addUser", "Add a new user.", "userId", "userFullName, userEmail, userPassword");
            case ADDUSERWITHFILE                  : return formatActionHelp("addUserWithFile", "Add users from comma separated file.", "file", "");
        	case REMOVEUSER                       : return formatActionHelp("removeUser", "Add a new user.", "userId", "");
            case REMOVEUSERWITHFILE               : return formatActionHelp("removeUserWithFile", "Remove users from comma separate file.", "file", "");
        	case ADDGROUP                         : return formatActionHelp("addGroup", "Add a new group.", "group", "");
            case REMOVEGROUP                      : return formatActionHelp("removeGroup", "Remove a group.", "group", "");
            case ADDUSERTOGROUP                   : return formatActionHelp("addUserToGroup", "Add user to a group.", "userId, group", "");
        	case ADDUSERTOGROUPWITHFILE           : return formatActionHelp("addUserToGroupWithFile", "Add users to groups from comma separated file.", "file", "");
        	case REMOVEUSERFROMGROUP              : return formatActionHelp("removeUserFromGroup", "Remove user from a group.", "userId, group", "");
        	case REMOVEUSERFROMGROUPWITHFILE      : return formatActionHelp("removeUserFromGroupWithFile", "Remove users from groups from comma separated file.", "file", "");
            // Permissions
            case ADDPERMISSIONS                   : return formatActionHelp("addPermissions", "Add permissions to page or space.", "space, permissions, userId or group", "title, descendents");
        	case REMOVEPERMISSIONS                : return formatActionHelp("removePermissions", "Remove permissions from page or space.", "space, permissions, userId or group", "title, descendents");
            case COPYPERMISSIONS                  : return formatActionHelp("copyPermissions", "Copy page permissions from a page to another page.", "space, title", "newSpace, newTitle");
            case REMOVEALLPERMISSIONSFORGROUP     : return formatActionHelp("removeAllPermissionsForGroup", "Remove all permissions for a group.", "group", "");
            // Space actions
        	case ADDSPACE                         : return formatActionHelp("addSpace", "Add a new space.", "space or userId", "");
            case REMOVESPACE                      : return formatActionHelp("removeSpace", "Remove a space.", "space or userId", "");
        	case GETSPACE                         : return formatActionHelp("getSpace", "Get space information.", "space", "");
            // Lists
            case GETSPACELIST                     : return formatActionHelp("getSpaceList", "Get list of spaces. Put to a file if specified.", "space", "file");
        	case GETPAGELIST                      : return formatActionHelp("getPageList", "Get list of pages. Put to a file if specified.", "space", "title, ancestors, descendents, children, file");
            case GETNEWSLIST                      : return formatActionHelp("getNewsList", "Get list of news items. Put to a file if specified.", "space", "ancestors, descendents, children, file");
        	case GETLABELLIST                     : return formatActionHelp("getLabelList", "Get list of labels. Put to a file if specified.", "", "space, title, news, mostPopular, recentlyUpdated, file");
            case GETPERMISSIONLIST                : return formatActionHelp("getPermissionList", "Get list of user or page permissions. Put to a file if specified.", "space", "title, userId, file");
            case GETUSERLIST                      : return formatActionHelp("getUserList", "Get list of users. Put to a file if specified.", "", "file");
            case GETGROUPLIST                     : return formatActionHelp("getGroupList", "Get list of groups. Put to a file if specified.", "userId", "file");
            // Export
            case EXPORTSPACE                      : return formatActionHelp("exportSpace", "Export a space to a file", "space, exportType, file", ""); 
            case EXPORTSITE                       : return formatActionHelp("exportSite", "Export site backup to a file", "file", ""); 

            case LOADFILES                        : return formatActionHelp("loadFiles",
            		                                    "BETA. Load directory and files into a page hierarchy. HTM, HTML, and text files converted to pages. All other files added as attachments.",
            		                                    "space, file", "title, content, parent, userid, group, replace");           		

    	    case NOTFOUND                         : 
    	    default                               : return "";
    	}
    }

    /**
     * Append action help values to the string buffer
     * - this is used so that the Action enum for this class is used
     * @param string buffer to append the help text
     * @return general help for this client
     */
    protected void appendActionHelpValues(StringBuffer help) {
        for (Action action : Action.values()) {
        	help.append(getActionHelp(action));
        }
    }
    
    /**
     * Add parameters to the parameterList
     * - Options must have a value
     * - Switches do NOT have a value
     * - Note that the long version is expressed as: --xxx value, --xxx "value", or --xxx
     *   and that short flag version is expressed as: -x value, -x "value", or -x
     */ 
    protected void addParameters() {  
    	
    	super.addParameters();   // add all the standard parameters for superclass client

        // Options                          id              type                default          required?          short flag(-)      long flag (--) help text    
    	parameterList.add(new FlaggedOption("title",    JSAP.STRING_PARSER, JSAP.NO_DEFAULT,  JSAP.NOT_REQUIRED, 't',               "title",    "Page or news title."));
    	parameterList.add(new FlaggedOption("file",     JSAP.STRING_PARSER, JSAP.NO_DEFAULT,               JSAP.NOT_REQUIRED, 'f',               "file",     "Path to file based content for attachments, pages, etc..."));
    	parameterList.add(new FlaggedOption("content",  JSAP.STRING_PARSER, "",               JSAP.NOT_REQUIRED, 'c',               "content",  "Content for page, attachment or comment."));
    	parameterList.add(new FlaggedOption("comment",  JSAP.STRING_PARSER, "",               JSAP.NOT_REQUIRED, JSAP.NO_SHORTFLAG, "comment",  "Comment text for comment or attachment comment."));
    	parameterList.add(new FlaggedOption("parent",   JSAP.STRING_PARSER, JSAP.NO_DEFAULT,  JSAP.NOT_REQUIRED, JSAP.NO_SHORTFLAG, "parent",   "Parent page name." ));
    	parameterList.add(new FlaggedOption("name",     JSAP.STRING_PARSER, JSAP.NO_DEFAULT,  JSAP.NOT_REQUIRED, JSAP.NO_SHORTFLAG, "name",     "File name for attachment."));
    	parameterList.add(new FlaggedOption("labels",   JSAP.STRING_PARSER, JSAP.NO_DEFAULT,  JSAP.NOT_REQUIRED, JSAP.NO_SHORTFLAG, "labels",   "Comma separated list of labels."));
    	parameterList.add(new FlaggedOption("newTitle", JSAP.STRING_PARSER, JSAP.NO_DEFAULT,  JSAP.NOT_REQUIRED, JSAP.NO_SHORTFLAG, "newTitle", "New title of renamed page."));
    	parameterList.add(new FlaggedOption("space",    JSAP.STRING_PARSER, JSAP.NO_DEFAULT,  JSAP.NOT_REQUIRED, JSAP.NO_SHORTFLAG, "space",    "Space key."));
    	parameterList.add(new FlaggedOption("newSpace", JSAP.STRING_PARSER, JSAP.NO_DEFAULT,  JSAP.NOT_REQUIRED, JSAP.NO_SHORTFLAG, "newSpace", "New space key."));

    	parameterList.add(new FlaggedOption("userId",   JSAP.STRING_PARSER, JSAP.NO_DEFAULT,  JSAP.NOT_REQUIRED, JSAP.NO_SHORTFLAG, "userId",   "User id for user management and other actions"));
    	parameterList.add(new FlaggedOption("userFullName",JSAP.STRING_PARSER, JSAP.NO_DEFAULT,JSAP.NOT_REQUIRED, JSAP.NO_SHORTFLAG, "userFullName", "User name for user management actions"));
    	parameterList.add(new FlaggedOption("userEmail",JSAP.STRING_PARSER, JSAP.NO_DEFAULT,  JSAP.NOT_REQUIRED, JSAP.NO_SHORTFLAG, "userEmail", "User email for user management actions"));
    	parameterList.add(new FlaggedOption("userPassword",JSAP.STRING_PARSER, JSAP.NO_DEFAULT,JSAP.NOT_REQUIRED, JSAP.NO_SHORTFLAG, "userPassword", "User password for user management actions"));
    	parameterList.add(new FlaggedOption("group",    JSAP.STRING_PARSER, JSAP.NO_DEFAULT,  JSAP.NOT_REQUIRED, JSAP.NO_SHORTFLAG, "group",    "Group for user management actions"));
    	parameterList.add(new FlaggedOption("defaultGroup", JSAP.STRING_PARSER, JSAP.NO_DEFAULT,JSAP.NOT_REQUIRED, JSAP.NO_SHORTFLAG, "defaultGroup", "Default group to move users on removeGroup action."));

    	parameterList.add(new FlaggedOption("permissions",JSAP.STRING_PARSER, JSAP.NO_DEFAULT,JSAP.NOT_REQUIRED, JSAP.NO_SHORTFLAG, "permissions",
      			  "Comma separated list of permissions."
      			+ "\n\tPage permissions:"
      			+ "\n\t\tview, edit"
      			+ "\n\tSpace permissions:"
      			+ "\n\t\tviewsspace, editspace, comment, editblog, createattachment, "
      			+ "\n\t\tremovepage, removecomment, removeblog, removeattachment, removemail, "
      			+ "\n\t\tsetpagepermissions, setspacepermissions, "
      			+ "\n\t\texportpage, exportSpace"
                ));
    	parameterList.add(new FlaggedOption("dayOfMonth",JSAP.INTEGER_PARSER, "0",            JSAP.NOT_REQUIRED, JSAP.NO_SHORTFLAG, "dayOfMonth","Day of month for news entry. Use negative values for going back to previous months."));
    	parameterList.add(new FlaggedOption("count",    JSAP.INTEGER_PARSER, Integer.toString(Integer.MAX_VALUE),JSAP.NOT_REQUIRED, JSAP.NO_SHORTFLAG, "count",    "Maximum count of entries to return."));
    	parameterList.add(new FlaggedOption("exportType",JSAP.STRING_PARSER, "XML",           JSAP.NOT_REQUIRED, JSAP.NO_SHORTFLAG, "exportType","Export type (XML, HTML, PDF) for space export."));
    	parameterList.add(new FlaggedOption("mime",     JSAP.STRING_PARSER, JSAP.NO_DEFAULT,               JSAP.NOT_REQUIRED, JSAP.NO_SHORTFLAG, "mime",     "Attachment mime type if you want to override determination by file extension."));
    	parameterList.add(new FlaggedOption("findReplace", JSAP.STRING_PARSER, JSAP.NO_DEFAULT, JSAP.NOT_REQUIRED, JSAP.NO_SHORTFLAG, "findReplace", "Find and replace text."));

        // Switches - do NOT have a value
    	parameterList.add(new Switch       ("news",                   JSAP.NO_SHORTFLAG, "news",                   "Parameters represent a news item."));
    	parameterList.add(new Switch       ("exportAttachments",      JSAP.NO_SHORTFLAG, "exportAttachments",      "Export attachments for site export."));
    	parameterList.add(new Switch       ("mostPopular",            JSAP.NO_SHORTFLAG, "mostPopular",            "Request most popular labels."));
    	parameterList.add(new Switch       ("recentlyUsed",           JSAP.NO_SHORTFLAG, "recentlyUsed",           "Request recently used labels."));
    	parameterList.add(new Switch       ("ancestors",              JSAP.NO_SHORTFLAG, "ancestors",              "Ancestors for a page."));
    	parameterList.add(new Switch       ("descendents",            JSAP.NO_SHORTFLAG, "descendents",            "All descendents for a page."));
    	parameterList.add(new Switch       ("children",               JSAP.NO_SHORTFLAG, "children",               "Immediate children for a page."));
    	parameterList.add(new Switch       ("replace",                JSAP.NO_SHORTFLAG, "replace",                "Replace existing entity."));
    };
    
    
    /*
     * -------------------------------------------------------------------------------------------------------------------
     * Function implementations
     * -------------------------------------------------------------------------------------------------------------------
     */
      
    /*
     * Store a page.
     * - update the page if it already exists, otherwise create a new page
     * - BUG note: 2.5.2 seems to allow 2 pages with different parents
     *   - works correctly on 2.8, so probably just ignore for now
     */
    public String storePage(boolean allowReplace) throws java.rmi.RemoteException, ClientException {
        
    	String space = getRequiredString("space");
        String title = getRequiredString("title");
        String parent = getString("parent");
        RemotePage page = null;
        boolean createPage = false;
        try {
            page = getPage(title, space);
        }
        catch (ClientException exception) {   // page doesn't exist
            createPage = true;
        }
        if (!createPage && !allowReplace) {
        	throw new ClientException("Page '" + title + "' in space '" + space
        			                    + "' already exists. Specify --replace if you want to replace the page.");
        }
        
        String content = null;
        if (jsapResult.userSpecified("content")) {         // content specified by user
        	content = findReplace(jsapResult.getString("content"));
        } else if (jsapResult.userSpecified("file")) {     // file specified by user
            content = findReplace(getFileAsString(getString("file")));
        } else {                                           // neither content or file specified
             // then just use previous content or blank
        }
        
        page = storePage(page, title, space, parent, content, allowReplace);

        String labelsMessage = setLabels(page.getId());
        return "Page " + (createPage ? "created: " : "updated: ") + page.getUrl() + getChildMessage(parent) + labelsMessage + ".  Page has id: " + page.getId();
    }
    
    /*
     * Store a page - base helper function.
     * - update the page if it already exists, otherwise create a new page
     * - page is both parameter and output due to the need for the calling function to 
     *   retrieve the page if it already exists and handle appropriately - don't want to duplicate a remote call
     * - BUG note: 2.5.2 seems to allow 2 pages with different parents
     *   - works correctly on 2.8, so probably just ignore for now
     */
    public RemotePage storePage(RemotePage page, String title, String space, String parent, String content, boolean allowReplace) throws java.rmi.RemoteException, ClientException {

    	boolean createPage = false;
        if (page == null) {
            try {
                page = getPage(title, space);
            }
            catch (ClientException exception) {   // page doesn't exist
                page = new RemotePage();
                createPage = true;
            }
            if (!createPage && !allowReplace) {
            	throw new ClientException("Page '" + title + "' in space '" + space
        			                    + "' already exists. Specify --replace if you want to replace the page.");
            }
        }
        
        if (!space.equalsIgnoreCase(page.getSpace())) { // only reset if it is different other than is case
        	page.setSpace(space);
        }
        page.setTitle(title);

        //long id = page.getId();
        //int version = page.getVersion();
        //page.setId(id);
        //page.setVersion(version);

        Calendar calendar = Calendar.getInstance();
        page.setCreated(calendar);
        page.setModified(calendar);

        if (content != null) {
        	page.setContent(content);
        } 
        setParent(page, parent, space);
        page = service.storePage(token, page);

        return page;
    }

    /*
     * Set parent helper function
     * - return convenient message text
     */
    public void setParent(RemotePage page, String parent, String space) throws ClientException, InvalidSessionException {

        if ((parent != null) && (!"".equals(parent))) {
           	page.setParentId(getPage(parent, space).getId());
        }
        return;
    }
    
    /*
     * Set parent helper function
     * - return convenient message text
     */
    public String getChildMessage(String parent) {

    	String result = "";
        if ((parent != null) && (!"".equals(parent))) {
            result = " as child of '" + parent + "'";
        }
        return result;
    }

    /*
     * Set labels helper function
     * - return message text
     */
    public String setLabels(long contentId) throws ClientException {

        String result = "";
        if (jsapResult.userSpecified("labels")) {
        	String labels = getString("labels");
        	if (!"".equals(labels)) {
        		addLabels(labels, contentId);  // this may throw an exception
        		result = " with labels: " + labels;
        	}
        }
        return result;
    }

    /*
     * Set labels helper function
     * - return message text
     */
    public String getLabelsMessage() throws ClientException {

        String result = "";
        if (jsapResult.userSpecified("labels")) {
        	String labels = getString("labels");
        	if (!"".equals(labels)) {
        		result = " with labels: " + labels;
        	}
        }
        return result;
    }
    
    /*
     * Copy page
     */
    public String copyPage() throws java.rmi.RemoteException, ClientException {

        String space = getRequiredString("space");
        String title = getRequiredString("title");
        String parent = getString("parent");

        String newSpace = space;  // default to current space
        if (jsapResult.userSpecified("newSpace")) {
        	newSpace = getString("newSpace");
        } else if (!jsapResult.userSpecified("newTitle")) {
        	throw new ClientException("Specify either newSpace or newTitle parameters.");
        }

        String newTitle = title;
        if (jsapResult.userSpecified("newTitle")) {
        	newTitle = getString("newTitle");
        }

        if (newSpace.equalsIgnoreCase(space) && newTitle.equalsIgnoreCase(title)) {
        	throw new ClientException("Specify newTitle or newSpace parameters that differs from the source page.");
        }
        RemotePage page = copyPage(space, title, parent, newSpace, newTitle, jsapResult.userSpecified("replace"));

        boolean childCopied = false;  
        if (!newSpace.equalsIgnoreCase(space)) {          // if copy to new space, then consider copy of children
        	long sourceId = getContentId(title, space);
	        if (jsapResult.userSpecified("descendents")) {
				childCopied = copyChildren(sourceId, page.getTitle(), newSpace, true);
			} else if (jsapResult.userSpecified("children")) {
				childCopied = copyChildren(sourceId, page.getTitle(), newSpace, false);
			}
        }    
        return "Page " + title + (childCopied ? " with children" : "") + " copied to: " + page.getUrl() + getChildMessage(parent) + getLabelsMessage() + ".  Page has id: " + page.getId();
    }
    
    /*
     * Copy direct children
     */
    public boolean copyChildren(long sourceId, String parent, String newSpace, boolean descendents) throws java.rmi.RemoteException, ClientException {

    	boolean childCopied = false;  
        RemotePageSummary list[] = service.getChildren(token, sourceId);
        if (list.length > 0) {
        	for (RemotePageSummary summary : list) {
        		childCopied = true;
        		RemotePage page = copyPage(summary.getSpace(), summary.getTitle(), parent, newSpace, summary.getTitle(), jsapResult.userSpecified("replace"));
                if (descendents) {
                	copyChildren(getContentId(summary.getTitle(), summary.getSpace()), page.getTitle(), newSpace, true);
                }
        	}
        } 
        return childCopied;
    }    
        
    /*
     * Copy page helper
     */
    public RemotePage copyPage(String space, String title, String parent, String newSpace, String newTitle, boolean replace) throws java.rmi.RemoteException, ClientException {

        RemotePage page = null;
        RemotePage oldPage = getPage(title, space);

        boolean createPage = false;
        try {
            page = getPage(newTitle, newSpace);
        }
        catch (ClientException exception) {   // page doesn't exist
            createPage = true;
        }
        if (!createPage && !replace) {
        	throw new ClientException("Target page '" + newTitle + "' in space '" + newSpace
                    + "' already exists.  Specify --replace if you want to replace the page.");
        }

        page = storePage(page, newTitle, newSpace, parent, findReplace(oldPage.getContent()), true);

        setLabels(page.getId());
        return page;
    }

    /*
     * Remove a page from a space
     * - Ignore error if page not found.
     * - Let exception be generated for errors trying to remove page or descendents
     */
    public String removePage() throws java.rmi.RemoteException, ClientException {

        String space = getRequiredString("space");
        String title = getRequiredString("title");
        RemotePage page;
        int descendentCount = 0;
        try {
            page = getPage(title, space);
        }
        catch (ClientException exception) {
            log.debug("Ignore: " + exception.toString());
            return "Page '" + title + "' not found in space '" + space + "'. The request is ignored.";
        }  
        if (jsapResult.userSpecified("descendents")) {  // user request all descendents be deleted
        	RemotePageSummary pages[] = service.getDescendents(token, page.getId());
        	descendentCount = pages.length;
        	for (int i = 0; i < pages.length; i++) {
        		service.removePage(token, pages[i].getId());
        	}
        }
        service.removePage(token, page.getId());

        return "Removed page '" + title + "' "
               + (descendentCount == 0 ? "" : "and " + descendentCount + " descendents ")
        	   + "from space '" + space + "'.";
    }

    /*
     * Rename and/or move page
     * - issue error message if page not found
     */
    public String renamePage() throws java.rmi.RemoteException, ClientException {

        String space = getRequiredString("space");
        String title = getRequiredString("title");
        String parent = getString("parent");
        String newSpace = space;
        String newTitle = title;
        String spaceMessage = "";

        RemotePage page = getPage(title, space);

        /* API doesn't support this yet - http://jira.atlassian.com/browse/CONF-11350
        if (jsapResult.userSpecified("newSpace")) {
            newSpace = getString("newSpace");
            // API doesn't support changing space name, so remove and store.  This will loose history information.
            if (!newSpace.equalsIgnoreCase(space)) {
            	service.removePage(token, getContentId(title, space));
            }
        }
        */
        if (jsapResult.userSpecified("newTitle")) {
            newTitle = getString("newTitle");
        }

        // target page better not already exist!!
        try {
        	getContentId(newTitle, newSpace);
        	throw new ClientException("Page '" + title + "' already exists in space '" + space + "'");
        } catch (Exception discard) {
        }

        page.setTitle(newTitle);
        if (!newSpace.equalsIgnoreCase(space)) {
        	page.setSpace(newSpace);
        }

        // make sure the version is changed
        //int version = page.getVersion();
        //page.setVersion(version + 1);

        page = service.storePage(token, page);
        setParent(page, parent, newSpace);

        return "Page '" + title + "' in space '" + space + "'"
             + " changed to '" + newTitle + "' " + spaceMessage + getChildMessage(parent);
    }

    /*
     * Render page or news
     */
    public String renderItem(boolean newsOnly) throws java.rmi.RemoteException, ClientException {
        String space = getRequiredString("space");
        String title = getRequiredString("title");

    	long contentId = getContentId(title, space, newsOnly);
        String rendered = service.renderContent(token, space, contentId, null);
        if (jsapResult.userSpecified("file")) {
            writeToFile(rendered.getBytes(), getString("file"));
            return "Rendered data written to file: " + getString("file");
        }
        return "Rendered data: " + rendered;
    }

    /*
     * Get page or news source
     */
    public String getSource(boolean newsOnly) throws java.rmi.RemoteException, ClientException {
        String space = getRequiredString("space");
        String title = getRequiredString("title");

        String content;
        String message = "Page";
        if (newsOnly || jsapResult.userSpecified("news")) {
        	message = "News";

            Calendar calendar = getCalendar();
            RemoteBlogEntry news = getNews(title, space, calendar);
            content = news.getContent();
        } else {    // consider it a page
        	RemotePage page = getPage(title, space);
        	content = page.getContent();
        }
        if (jsapResult.userSpecified("file")) {
            writeToFile(content.getBytes(), getString("file"));
            return message + " source data written to file: " + getString("file");
        }
        return message + " source: " + content;
    }

    /*
     * Add a comment to a page
     * - if problems finding page, throws ClientException
     */
    public String addComment() throws java.rmi.RemoteException, ClientException {
        String space = getRequiredString("space");
        String title = getRequiredString("title");
        RemotePage page = getPage(title, space);
        RemoteComment comment = new RemoteComment();
        comment.setPageId(page.getId());
        String content = jsapResult.getString("comment");
        if (jsapResult.userSpecified("content")) {
            content = content + getFileAsString(jsapResult.getString("content"));
        }
        if (jsapResult.userSpecified("file")) {
            content = content + getFileAsString(getString("file"));
        }
        if ("".equals(content)) {
            throw new ClientException("A non-blank comment or file parameter is required.");
        }
        comment.setContent(content);
        comment.setCreated(Calendar.getInstance());
        comment = service.addComment(token, comment);
        return "Comment " + comment.getId() + " added: " + comment.getUrl();
    }

    /*
     * Add labels to a page or news item or space
     * - treat as space request unless title is specified
     * - treat as a page request unless user specified dayOfMonth
     * - if problems finding item, throws ClientException
     * - if there are no valid labels, just return with a warning message
     */
    public String addLabels() throws java.rmi.RemoteException, ClientException {

    	String message;
    	String labels = getString("labels");
    	if ("".equals(labels)) {
    		message = "No labels specified.";
    	} else {
	    	String space = getRequiredString("space");
	    	if (jsapResult.userSpecified("title")) {     // page or news request

		    	String title = getString("title");
		    	long contentId = getContentId(title, space);  // get page or news item

		    	String dayMessage = "";
		    	if (jsapResult.userSpecified("dayOfMonth")) {
		    		dayMessage = "for day of month: " + jsapResult.getInt("dayOfMonth");
		    	}
		    	addLabels(labels, contentId);
		    	message = "Labels: " + labels + " where added to item: '" + title + "' from space: '" + space + "' " + dayMessage;
	    	} else {                                      // space request
		    	addLabels(labels, space);
		    	message = "Labels: " + labels + " where added to space: '" + space + "'";
	    	}
    	}
    	return message;
    }

    /*
     * Remove labels from a page or news item or space
     * - treat as space request unless title is specified
     * - treat as a page request unless user specified dayOfMonth
     * - if problems finding item, throws ClientException
     * - if there are no valid labels, just return with a warning message
     */
    public String removeLabels() throws java.rmi.RemoteException, ClientException {

    	String message;
    	String goodLabels;
    	String labels = getString("labels");
    	String space = getRequiredString("space");
    	if (jsapResult.userSpecified("title")) {

	    	String title = getString("title");
	    	long contentId = getContentId(title, space);  // get page or news item

	    	String dayMessage = "";
	    	if (jsapResult.userSpecified("dayOfMonth")) {
	    		dayMessage = "for day of month: " + jsapResult.getInt("dayOfMonth");
	    	}
	    	goodLabels = removeLabels(labels, contentId);
	    	message = "item: '" + title + "' from space: '" + space + "' " + dayMessage;
    	} else {
	    	goodLabels = removeLabels(labels, space);
    		message = "space: '" + space + "'";
    	}
        if ("".equals(goodLabels.trim())) {
        	return "No labels removed from " + message;
        }
        return "Labels: " + goodLabels + " where removed from " + message;
    }

    /*
     * Add a attachment to a page
     * - if problems finding page, throws ClientException
     */
    public String addAttachment() throws java.rmi.RemoteException, ClientException {
        
        String space   = getRequiredString("space");
        String title   = getRequiredString("title");
        String name    = getString("name");           // name of the attachment
        String mime    = getString("mime");           // content type
        String comment = getString("comment");        // comment on attachment
        String file    = getString("file");
        String content = jsapResult.getString("content");

        RemoteAttachment attachment = addAttachment(title, space, name, mime, comment, file, content);

        return "Attachment '" + attachment.getFileName() + "' of type '" + attachment.getContentType() + "' added to page '" + title + "' in space '" + space + "' with url: " + attachment.getUrl();
    }
    
    /*
     * Add a attachment to a page based on parameters
     * - if problems finding page, throws ClientException
     */
    public RemoteAttachment addAttachment(String title, String space, String name, String mime, String comment, String fileName, String content) throws java.rmi.RemoteException, ClientException {
        RemoteAttachment attachment = new RemoteAttachment();

        byte[] data = null;
        if ((fileName != null) && !fileName.equals("")) {
            data = getFileAsBytes(fileName);
            File file = new File(fileName);
            attachment.setFileName(file.getName());  // use as default attachment name
        } else {
        	if (content != null) {
        		data = content.getBytes();
        	}
        }

        if ((data == null) || (data.length == 0)) {
        	out.println("Warning - there is no content!  You might have forgotten to provide the correct content or file parameters.");
        } 
        //out.println(data.length + " bytes written to attachment.");

        if ((name != null) && !name.equals("")) {
        	attachment.setFileName(name);
        }
        
        if (!attachment.getFileName().equals("")) {
            if ((mime != null) && !(mime.equals(""))) {
            	attachment.setContentType(mime);
            } else {
            	attachment.setContentType(new MimetypesFileTypeMap().getContentType(attachment.getFileName()));
                if ("".equals(attachment.getContentType())) {
                	attachment.setContentType("text/plain");    // make sure some non-blank mime type is set
                }
            }
            if (comment != null) {
            	attachment.setComment(comment);
            }
            attachment.setCreated(Calendar.getInstance());

            long id = getPage(title, space).getId();
            attachment = service.addAttachment(token, id, attachment, data);

            if (debug) {
                err.println("comment:           " + attachment.getComment());
                err.println("file name:         " + attachment.getFileName());
                err.println("mime type:         " + attachment.getContentType());
                err.println("content size (KB): " + attachment.getFileSize());
            }
        } else {
            throw new ClientException("Either the name or file parameter must be specified and non-blank.");
        }
        return attachment;
    }
    
    /*
     * Get attachment data
     */
    public String getAttachment() throws java.rmi.RemoteException, ClientException {
        String space = getRequiredString("space");
        String title = getRequiredString("title");
        String name  = getRequiredString("name");
        String file  = getRequiredString("file");

        RemotePage page = getPage(title, space);
        long id = page.getId();
        int versionNumber = 0;  // current version
        try {
       		writeToFile(service.getAttachmentData(token, id, name, versionNumber), getString("file"));
       		return "Attachment data written to file: " + file;
       	} catch (java.rmi.RemoteException exception) {
            throw new ClientException("Error trying to access attachment: " + name
                                         + ". It may not exist or you may not be authorized to it.");
        }
    }

    /*
     * Remove an attachment to a page
     * - if problems finding page, throws ClientException
     */
    public String removeAttachment() throws java.rmi.RemoteException, ClientException {
        String space = getRequiredString("space");
        String title = getRequiredString("title");
        String name  = getRequiredString("name");

        RemotePage page = getPage(title, space);
        long id = page.getId();
        try {
        	service.removeAttachment(token, id, name);
        	return "Attachment removed: " + name;
        }
        catch (java.rmi.RemoteException exception) {
            throw new ClientException("Error trying to access attachment: " + name
                                         + ". It may not exist or you may not be authorized to it.");
        }
    }

    /*
     * Store a news (blog) entry.  Updates an existing one if it exists or creates a new one.
     */
    public String storeNews(boolean allowReplace) throws java.rmi.RemoteException, ClientException {

        String space = getRequiredString("space");
        String title = getRequiredString("title");
    	boolean createNews = true;
        RemoteBlogEntry news;

        Calendar calendar = getCalendar();

        try {
            news = getNews(title, space, calendar);
            createNews = false;
        }
        catch (ClientException exception) {
            news = new RemoteBlogEntry();
        }
        if (!createNews && !allowReplace) {
        	throw new ClientException("News '" + title + "' in space '" + space
        			                    + "' already exists. Specify --replace if you want to replace.");
        }
        
        news.setSpace(space);
        news.setTitle(title);
        news.setPublishDate(calendar);

        if (jsapResult.userSpecified("content")) {         // content specified by user
        	news.setContent(findReplace(jsapResult.getString("content")));
        } else if (jsapResult.userSpecified("file")) {     // file specified by user
            news.setContent(findReplace(getFileAsString(getString("file"))));
        } else {                                           // neither content or file specified
             // then just use previous content
        }
        news = service.storeBlogEntry(token, news);

        String labelsMessage = "";
        if (jsapResult.userSpecified("labels")) {
        	String labels = getString("labels");
        	if (!"".equals(labels)) {
        		addLabels(labels, news.getId());  // this may throw an exception
        		labelsMessage = " with labels: " + labels;
        	}
        }
        return "News entry " + (createNews ? "created: " : "updated: ") + news.getUrl() + labelsMessage;
    }

    /*
     * Get news source
     */
    public String getNewsSource() throws java.rmi.RemoteException, ClientException {

        String space = getRequiredString("space");
        String title = getRequiredString("title");
        RemoteBlogEntry news = getNews(title, space, getCalendar());
        if (jsapResult.userSpecified("file")) {
            writeToFile(news.getContent().getBytes(), getString("file"));
            return "News source data written to file: " + getString("file");
        }
        return "News source: " + news.getContent();
    }

    /*
     * Remove news from a space
     */
    public String removeNews() throws java.rmi.RemoteException, ClientException {

    	String space = getRequiredString("space");
        String title = getRequiredString("title");
        RemoteBlogEntry news;
        try {
            news = getNews(title, space, getCalendar());
            service.removePage(token, news.getId());  // yes, you have to use remove page !!
        }
        catch (ClientException exception) {
            err.println("Ignore: " + exception.toString());
            return "";
        }
    	String dayMessage = "";
    	if (jsapResult.userSpecified("dayOfMonth")) {
    		dayMessage = "for day of month: " + jsapResult.getInt("dayOfMonth");
    	}
        return "Removed news item '" + title + "' from space '" + space + "' " + dayMessage;
    }

    /*
     * Adds a user. Just gives info message if it already exists.
     */
    public String addUser() throws java.rmi.RemoteException, ClientException {
        String userId = getRequiredString("userId");
        String userFullName = getString("userFullName");
        String userEmail = getString("userEmail");
        String userPassword = getString("userPassword");
        return addUserInternal(userId, userPassword, userEmail, userFullName);
    }

    /*
     * Adds a user. Just gives info message if it already exists.
     */
    public String addUserInternal(String userId, String userPassword, String userEmail, String userFullName)
                  throws java.rmi.RemoteException, ClientException {
        userId = userId.trim();
        if (userId == "") {
            throw new ClientException("A non-blank userId is required.");  // cover the from file case
        }

        if (userFullName.equals("")) {   // if blank full name, default to user id
            userFullName = userId;
        }
        if (userPassword.equals("")) {   // if blank password, generate a random password
            userPassword = Long.toString(Math.abs((new Random()).nextLong()), 36);
        }
        RemoteUser user = new RemoteUser();

        if (hasUser(userId)) {
            return "User: " + userId + " is already defined.";
        }

        user.setName(userId);
        user.setFullname(userFullName);
        user.setEmail(userEmail);
        user.setUrl("");

        service.addUser(token, user, userPassword);
        return "User: " + userId + " added with password: " + userPassword + ".  Full name is: "
                         + userFullName +".  Email is: " + userEmail + ".";
    }

    /*
     * Add user using a file.  File is a comma separated list of users with their user information
     * Each line is: userId, userPassword, userEmail, userFullName
     * Each line will be processed even if there are errors.
     * - if user already exists, line will be ignored
     * - if a group does not exist, it will be added
     */
    public String addUserWithFile() throws java.rmi.RemoteException, ClientException {
        String fileName = getRequiredString("file");
        BufferedReader in = null;
        int errorCount = 0;
        int goodCount = 0;
        int alreadyDefined = 0;
        try {
            in = new BufferedReader(new FileReader(fileName));

            String line;
            while((line = in.readLine()) != null) {    // while lines in file
                if (!line.trim().equals("")) {          // ignore blank lines
                	checkLogin();
                    String[] list = line.split(",");
                    String userId = list[0];
                    String userPassword = "";
                    String userEmail = "";
                    String userFullName = "";
                    if (list.length > 1) {
                        userPassword = list[1].trim();
                    }
                    if (list.length > 2) {
                        userEmail = list[2].trim();
                    }
                    if (list.length > 3) {
                        userFullName = list[3].trim();
                    }
                    try {
                        if (hasUser(userId)) {
                            alreadyDefined++;
                            out.println("User: " + userId + " already defined.");
                        } else {
                            String message = addUserInternal(userId, userPassword, userEmail, userFullName);
                            out.println(message);
                            for (int i=4; i < list.length; i++) {
                                String group = list[i].trim();
                                if (!group.equals("")) {     // ignore blank groups
                                    message = addUserToGroupInternal(userId, group, true);  // true to add groups if not defined
                                    out.println(message);
                                }
                            }
                            goodCount++;
                        }
                    }
                    catch (ClientException exception) {
                        out.println(exception.toString());
                        errorCount++;
                    }
                }
            }
        }
        catch (FileNotFoundException exception) {
            throw new ClientException("File not found: " + fileName);
        }
        catch (IOException exception) {
            if (log.isDebugEnabled()) {
                exception.printStackTrace(err);
            }
            throw new ClientException("Error reading file: " + fileName + "\n" + exception.toString());
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                }
                catch (Exception ignore) {
                }
            }
        }
        String message = "Successful adds: " + goodCount + "  errors: " + errorCount + "  already defined users: " + alreadyDefined;
        if ((errorCount > 0) || (alreadyDefined > 0)) {
            throw new ClientException(message);
        }
        return message;
    }

    /*
     * Removes a user. Info message if not found.
     */
    public String removeUser() throws java.rmi.RemoteException, ClientException {
        String userId = getRequiredString("userId");
        return removeUserInternal(userId);
    }

    /*
     * Removes a user. Info message if not found.
     */
    public String removeUserInternal(String userId) throws java.rmi.RemoteException, ClientException {
        userId = userId.trim();
        if (userId.equals("")) {
            throw new ClientException("A non-blank userId  is required.");
        }
        if (!hasUser(userId)) {
            return "User: " + userId + " is not defined.";
        }

        service.removeUser(token, userId);
        return "User: " + userId + " removed.";
    }

    /*
     * Remove user using a file.  File is a comma separated list of users
     * Each line is at least: userId (other information is ignored)
     * Each line will be processed even if there are errors.
     * - if user is not defined, line will be ignored
     */
    public String removeUserWithFile() throws java.rmi.RemoteException, ClientException {
        String fileName = getRequiredString("file");
        BufferedReader in = null;
        int errorCount = 0;
        int goodCount = 0;
        int notDefined = 0;
        try {
            in = new BufferedReader(new FileReader(fileName));

            String line;
            while((line = in.readLine()) != null) {    // while lines in file
                if (!line.trim().equals("")) {          // ignore blank lines
                	checkLogin();
                    String[] list = line.split(",");
                    String userId = list[0].trim();
                    try {
                        if (!hasUser(userId)) {
                            notDefined++;
                            out.println("User: " + userId + " not found.");
                        } else {
                            String message = removeUserInternal(userId);
                            out.println(message);
                            goodCount++;
                        }
                    }
                    catch (ClientException exception) {
                        out.println(exception.toString());
                        errorCount++;
                    }
                }
            }
        }
        catch (FileNotFoundException exception) {
            throw new ClientException("File not found: " + fileName);
        }
        catch (IOException exception) {
            if (verbose) {
                out.println(exception.toString());
                exception.printStackTrace(err);
            }
            throw new ClientException("Error reading file: " + fileName);
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                }
                catch (Exception ignore) {
                }
            }
        }
        String message = "Successful removes: " + goodCount + "  errors: " + errorCount + "  not defined users: " + notDefined;
        if ((errorCount > 0) || (notDefined > 0)) {
            throw new ClientException(message);
        }
        return message;
    }

    /*
     * Adds a group. Just gives info message if it already exists.
     */
    public String addGroup() throws java.rmi.RemoteException, ClientException {
        String group = getRequiredString("group");
        return addGroupInternal(group);
    }

    /*
     * Adds a group. Just gives info message if it already exists.
     */
    public String addGroupInternal(String group) throws java.rmi.RemoteException, ClientException {
        group = group.toLowerCase().trim();   // groups are not case sensitive, so lowercase them all
        if (group.equals("")) {
            throw new ClientException("A non-blank group is required.");
        }
        if (hasGroup(group)) {
            return "Group: " + group + " is already defined.";
        }

        service.addGroup(token, group);
        return "Group: " + group + " added.";
    }

    /*
     * Removes a group. If group has users and default group is specified, users added to default group.
     * Default group must exist or be blank.  Group names are converted to lowercase as that is what confluence does.
     */
    public String removeGroup() throws java.rmi.RemoteException, ClientException {
        String group = getRequiredString("group").toLowerCase();
        String defaultGroup = getString("defaultGroup").toLowerCase();
        String defaultGroupMessage = "";

        if (!hasGroup(group)) {
            return "Group: " + group + " is not defined.";
        }
        if (!defaultGroup.equals("")) {
            if (!hasGroup(defaultGroup)) {
                return "Default group: " + defaultGroup + " is not defined.";
            }
            defaultGroupMessage = "Users moved to default group: " + defaultGroup + ".";
        }

        service.removeGroup(token, group, defaultGroup);
        return "Group: " + group + " removed." + defaultGroupMessage;
    }

    /*
     * Add user to group.  Adds a user to an existing group.  Both user and group must be valid.
     */
    public String addUserToGroup() throws java.rmi.RemoteException, ClientException {
        String userId = getRequiredString("userId");
        String group = getRequiredString("group");

        return addUserToGroupInternal(userId, group, false);
    }

    /*
     * Add user to group using parameters. Adds a user to a group.  User must be valid.
     * Set addGroup=true if you need a non-existing group to be added.  Otherwise group must exist.
     */
    public String addUserToGroupInternal(String userId, String group, boolean addGroup)
                  throws java.rmi.RemoteException, ClientException {
        userId = userId.trim();
        group = group.trim();
        if (userId.equals("")) {
            throw new ClientException("A non-blank userId is required.");
        }
        if (group.equals("")) {
            throw new ClientException("A non-blank group is required.");
        }
        if (!hasUser(userId)) {
            throw new ClientException("'" + userId + "' is not a valid user.");
        }
        if (!hasGroup(group)) {
            if (addGroup) {
                String message = addGroupInternal(group);
                out.println(message);
            } else {
                throw new ClientException("'" + group + "' is not a valid group.");
            }
        }

        service.addUserToGroup(token, userId, group);
        return "User: " + userId + " added to group: " + group;
    }

    /*
     * Removes user from group.  Remove user from existing group. Both user and group must be valid.
     */
    public String removeUserFromGroup() throws java.rmi.RemoteException, ClientException {
        String userId = getRequiredString("userId");
        String group = getRequiredString("group");
        return removeUserFromGroupInternal(userId, group);
    }

    /*
     * Removes user from group.  Remove user from existing group. Both user and group must be valid.
     */
    public String removeUserFromGroupInternal(String userId, String group) throws java.rmi.RemoteException, ClientException {
        userId = userId.trim();
        group = group.trim();
        if (userId.equals("")) {
            throw new ClientException("A non-blank userId parameter is required.");
        }
        if (group.equals("")) {
            throw new ClientException("A non-blank group parameter is required.");
        }
        if (!hasUser(userId)) {
            throw new ClientException("'" + userId + "' is not a valid user.");
        }
        if (!hasGroup(group)) {
            throw new ClientException(group + " is not a valid group.");
        }

        service.removeUserFromGroup(token, userId, group);
        return "User: " + userId + " removed from group: " + group;
    }

    /*
     * Add user to group using a file.  File is a comma separated list of users and groups.
     * Each line is one user and one group separated by a comma
     * Each line will be processed even if there are errors.
     * - invalid user id will give an failure message
     * - if a group does not exist, it will be added
     */
    public String addUserToGroupWithFile() throws java.rmi.RemoteException, ClientException {
        return addOrRemoveUserToOrFromGroupWithFile(true);
    }

    /*
     * Remove user from group using a file.  File is a comma separated list of users and groups.
     * Each line is one user and one group separated by a comma
     * Each line will be processed even if there are errors.
     */
    public String removeUserFromGroupWithFile() throws java.rmi.RemoteException, ClientException {
        return addOrRemoveUserToOrFromGroupWithFile(false);
    }

    /*
     * Add/remove user to/from group using a file.  File is a comma separated list of users and groups.
     * Each line is one user and one group separated by a comma
     * Each line will be processed even if there are errors.
     * - for add:
     *   - invalid user id will give an failure message
     *   - if a group does not exist, it will be added
     */
    public String addOrRemoveUserToOrFromGroupWithFile(boolean add) throws java.rmi.RemoteException, ClientException {
        String fileName = getRequiredString("file");
        BufferedReader in = null;
        int errorCount = 0;
        int goodCount = 0;
        try {
            in = new BufferedReader(new FileReader(fileName));

            String line;
            while((line = in.readLine()) != null) {    // while lines in file
                if (!line.trim().equals("")) {          // ignore blank lines
                	checkLogin();
                    String[] list = line.split(",");
                    if (list.length > 1) {              // not 2 entries on a line
                        String userId = list[0];
                        String group  = list[1];
                        try {
                            String message;
                            if (add) {
                                message = addUserToGroupInternal(userId, group, true);
                            } else {
                                message = removeUserFromGroupInternal(userId, group);
                            }
                            out.println(message);
                            goodCount++;
                        }
                        catch (ClientException exception) {
                            out.println(exception.toString());
                            errorCount++;
                        }
                    }
                    else {
                        out.println("Line must have both userId and group separated by comma: " + line);
                        errorCount++;
                    }
                }
            }
        }
        catch (FileNotFoundException exception) {
            throw new ClientException("File not found: " + fileName);
        }
        catch (IOException exception) {
            if (log.isDebugEnabled()) {
                exception.printStackTrace(err);
            }
            throw new ClientException("Error reading file: " + fileName + "\n" + exception.toString());
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                }
                catch (Exception ignore) {
                }
            }
        }
        String message;
        if (add) {
            message = "Successful adds: " + goodCount + "  errors: " + errorCount;
        } else {
            message = "Successful removes: " + goodCount + "  errors: " + errorCount;
        }
        if (errorCount > 0) {
            throw new ClientException(message);
        }
        return message;
    }

    /*
     * Checks if userid is defined to Confluence.
     */
    public boolean hasUser(String userId) throws java.rmi.RemoteException {
        return service.hasUser(token, userId.trim());
    }

    /*
     * Checks if group is defined to Confluence.
     */
    public boolean hasGroup(String group) throws java.rmi.RemoteException {
        return service.hasGroup(token, group.trim());
    }

    /*
     * Remove all permissions for a group
     */
    public String removeAllPermissionsForGroup() throws java.rmi.RemoteException, ClientException {

    	String group = getRequiredString("group");
		service.removeAllPermissionsForGroup(token, group);
		return "All permissions removed for group '" + group + "'";
    }

    /*
     * Handles addPermissions and removePermissions for a space or page
     */
    public String doPermissions(boolean add) throws java.rmi.RemoteException, ClientException {

    	String space = getRequiredString("space");
    	String permissions = getRequiredString("permissions");
    	LinkedHashSet<String> goodList = new LinkedHashSet<String>();  // permissions successfully added
    	LinkedHashSet<String> badList  = new LinkedHashSet<String>();  // permissions that already exists and were therefore, not added - not considered an error
        String entity = null;
        String messageResult = "";
		//RemotePermission permission = null;
		long contentId = -1;
		boolean isPagePermission = jsapResult.userSpecified("title");
    	
		if (isPagePermission) {
			contentId = getContentId(getString("title"), space);
        }

        if (jsapResult.userSpecified("group")) {
    		entity = getString("group");
    	} else if (jsapResult.userSpecified("userId")){
    		entity = getString("userId");
    	} else {
    		throw new ClientException("Either group or userId must be provided.");
    	}

    	String list[] = permissions.toUpperCase().split(",");
    	boolean anonymous = "anonymous".equalsIgnoreCase(entity);
    	
		for (int permissionIndex = 0; permissionIndex < list.length; permissionIndex++) {
			String listEntry = list[permissionIndex].trim();
			if (listEntry.equalsIgnoreCase(PAGE_PERMISSION_VIEW)) {
				listEntry = PAGE_PERMISSION_VIEW;   // case seems to be important for page authorities
			} else if (listEntry.equalsIgnoreCase(PAGE_PERMISSION_EDIT)) {
				listEntry = PAGE_PERMISSION_EDIT;
			} else if (jsapResult.userSpecified("title")) {
				throw new ClientException("'" + listEntry + "' is invalid for a page permission.");
			}
			
			// add permission
			if (add) {
				if (isPagePermission) {   
					// do descendents first to cover the case of view permissions which can only be added if there
					// isn't already a view permission inherited from parent
			        if (jsapResult.userSpecified("descendents")) {  // user request all descendents be deleted
			        	RemotePageSummary pages[] = service.getDescendents(token, contentId);
			        	int descendentCount = 0;
			        	for (int i = 0; i < pages.length; i++) {
			        		if (addPagePermission(listEntry, pages[i].getId(), entity, jsapResult.userSpecified("group"))) {
			        			descendentCount++;
			        		}
			        	}
			    	    if (descendentCount > 0) {
			    	    	messageResult = messageResult + descendentCount + " descendent pages had " + listEntry + " permission " + (add ? "added." : "removed.") + "\n";
			    	    }
			        }
					if (addPagePermission(listEntry, contentId, entity, jsapResult.userSpecified("group"))) {
						goodList.add(listEntry);
					} else {
						badList.add(listEntry);
					}
				} else if (anonymous) {                                      // anonymous space permissions
					service.addAnonymousPermissionToSpace(token, listEntry, space);
					goodList.add(listEntry);
				} else {                                                     // space permissions
					service.addPermissionToSpace(token, listEntry, entity, space);
					goodList.add(listEntry);
				}
				
			// remove permissions
			} else {
				if (isPagePermission) {   
					
					if (removePagePermission(listEntry, contentId, entity, jsapResult.userSpecified("group"))) {
						goodList.add(listEntry);
					} else {
						badList.add(listEntry);
					}
			        if (jsapResult.userSpecified("descendents")) {  // user request all descendents be deleted
			        	RemotePageSummary pages[] = service.getDescendents(token, contentId);
			        	int descendentCount = 0;
			        	for (int i = 0; i < pages.length; i++) {
			        		if (removePagePermission(listEntry, pages[i].getId(), entity, jsapResult.userSpecified("group"))) {
			        			descendentCount++;
			        		}
			        	}
			    	    if (descendentCount > 0) {
			    	    	messageResult = messageResult + descendentCount + " descendent pages had permission " + listEntry + (add ? " added." : " removed.") + "\n";
			    	    }
			        }
				} else if (anonymous) {  // remove
					service.removeAnonymousPermissionFromSpace(token, listEntry, space);
					goodList.add(listEntry);
				} else {
					service.removePermissionFromSpace(token, listEntry, entity, space);
					goodList.add(listEntry);
				}
			}
    	}
    	String message = add ? " added to" : " removed from";
    	String messageForBad = add ? " permissions already exist for " : " permissions not found for ";
    	String messageForPage = isPagePermission ? " page: '" + getString("title") + "' in" : "";
    			
    	return messageResult
    	     + ((goodList.size() > 0) ? goodList.toString() : "No") + " permissions where" + message + messageForPage + " space: '" + space + "' for: '" + entity + "'"
    	     + ((badList.size()  > 0) ? "\n" + badList.toString() + messageForBad + "'" + entity + "'." : ".");
    }

    /*
     * Handles adding a page permission based on parameters
     * - permission must either be "View" or "Edit"
     */
    public boolean addPagePermission(String permission, long contentId, String entity, boolean isGroup) throws java.rmi.RemoteException, ClientException {

    	boolean result = false;
		RemoteContentPermissionSet permissionSet = service.getContentPermissionSet(token, contentId, permission);
		RemoteContentPermission contentPermissions[] = permissionSet.getContentPermissions();
    	
		RemoteContentPermission newContentPermission = new RemoteContentPermission();
		if (isGroup) {
			newContentPermission.setGroupName(entity);
		} else {
			newContentPermission.setUserName(entity);
		}
		newContentPermission.setType(permission);
		if (indexOfObjectInList(newContentPermission, contentPermissions) < 0) {   // not already there
			RemoteContentPermission newContentPermissions[] = new RemoteContentPermission[contentPermissions.length + 1];
			for(int j = 0; j < contentPermissions.length; j++) {   // copy the array
				newContentPermissions[j] = contentPermissions[j];
			}
			newContentPermissions[contentPermissions.length] = newContentPermission;
			service.setContentPermissions(token, contentId, permission, newContentPermissions);
		    result = true;
		} 
	    return result;
    }
    
    /*
     * Handles removing a page permission based on parameters
     * - permission must either be "View" or "Edit"
     */
    public boolean removePagePermission(String permission, long contentId, String entity, boolean isGroup) throws java.rmi.RemoteException, ClientException {

    	boolean result = false;
		RemoteContentPermissionSet permissionSet = service.getContentPermissionSet(token, contentId, permission);
		RemoteContentPermission contentPermissions[] = permissionSet.getContentPermissions();
    	
		RemoteContentPermission newContentPermission = new RemoteContentPermission();
		if (isGroup) {
			newContentPermission.setGroupName(entity);
		} else {
			newContentPermission.setUserName(entity);
		}
		newContentPermission.setType(permission);
		
		int foundIndex = indexOfObjectInList(newContentPermission, contentPermissions);
		if (foundIndex >= 0) { // permission is there to remove!  Ignore if permission not found
			RemoteContentPermission newContentPermissions[] = new RemoteContentPermission[contentPermissions.length - 1];
			int newIndex = 0;
			for(int j = 0; j < contentPermissions.length; j++) {   // copy the array
				if (j != foundIndex) {
					newContentPermissions[newIndex] = contentPermissions[j];
					newIndex++;
				}
			}
			service.setContentPermissions(token, contentId, permission, newContentPermissions);
		    result = true;
		} 
	    return result;
    }
    
    /*
     * Copy permissions for a page
     * - can't do spaces due to http://jira.atlassian.com/browse/CONF-9656
     */
    public String copyPermissions() throws java.rmi.RemoteException, ClientException {

    	String space = getRequiredString("space");
    	String title = getRequiredString("title");
    	//String list[] = service.getPermissions(token, space);
    	//String all[] = service.getSpaceLevelPermissions(token);

    	String newSpace = space;
    	String newTitle = title;
    	if (jsapResult.userSpecified("newSpace")) {
    		newSpace = getString("newSpace");
    	}
    	if (jsapResult.userSpecified("newTitle")) {
    		newTitle = getString("newTitle");
    	}

		RemotePermission list[] = service.getPagePermissions(token, getContentId(title, space));
		String lockType = null;

		for (int typeIndex = 0; typeIndex < 2; typeIndex++) {
			String type = (typeIndex == 0) ? "View" : "Edit";

			ArrayList<RemoteContentPermission> contentPermissions = new ArrayList<RemoteContentPermission>();

			//RemoteContentPermission contentPermissions[] = new RemoteContentPermission[list.length];
			for (int i = 0; i < list.length; i++) {
				RemotePermission listEntry = list[i];
                RemoteContentPermission newContentPermission = new RemoteContentPermission();
				lockType = listEntry.getLockType();
				if (lockType.equalsIgnoreCase(type)) {
					newContentPermission = new RemoteContentPermission();
					String entity = listEntry.getLockedBy();
					try {
						service.getUser(token, entity);            // check it is a user
						newContentPermission.setUserName(entity);
					} catch (RemoteException discard) {            // must be a group
						newContentPermission.setGroupName(entity);
					}
					newContentPermission.setType(lockType);
					contentPermissions.add(newContentPermission);
				}
			}
			service.setContentPermissions(token, getContentId(newTitle, newSpace), type, contentPermissions.toArray(new RemoteContentPermission[0]));
		}
    	return list.length + " page permissions copied from page '" + title + "' in space '" + space + "' to page '" + newTitle + "' in space '" + newSpace + "'";
    }

    /*
     * Helper function
     */
    public int indexOfObjectInList(Object object, Object list[]) {
        for (int i = 0; i < list.length; i++) {
        	if (object.equals(list[i])) {
        		return i;
        	}
        }
    	return -1;
    }

    /*
     * Add space.
     */
    public String addSpace() throws java.rmi.RemoteException, ClientException {

    	RemoteSpace space = new RemoteSpace();
        space.setDescription(getString("comment"));

    	if (jsapResult.userSpecified("userId")) {
    		RemoteUser user = service.getUser(token, getString("userId"));
    		space.setName(user.getFullname());
    		space = service.addPersonalSpace(token, space, user.getName());
    	} else {
    		space.setKey(getRequiredString("space"));
    		String name = getString("title");
    		if (name.equals("")) {
    			name = getString("space");
    		}
    		space.setName(name);
    		space.setDescription(getString("comment"));
    		space = service.addSpace(token, space);
    	}
        return "Space added with key: " + space.getKey() + " name: " + space.getName() + " and url: " + space.getUrl();
    }

    /*
     * Remove space.
     */
    public String removeSpace() throws java.rmi.RemoteException, ClientException {

    	String space = getRequiredString("space");
    	service.removeSpace(token, space);

        return "Space with key: " + space + " deleted";
    }

    /*
     * Get space information
     */
    public String getSpace() throws java.rmi.RemoteException, ClientException {
        String space = getRequiredString("space");
        RemoteSpace remoteSpace = service.getSpace(token, space);
        return "key: " + remoteSpace.getKey()
           + "; name: " + remoteSpace.getName()
           + "; url: " + remoteSpace.getUrl()
           + "; home: " + remoteSpace.getHomePage()
           + "; description: " + remoteSpace.getDescription()
           + ";";
    }

    /*
     * Get space list - list of spaces
     */
    public String getSpaceList() throws java.rmi.RemoteException, ClientException {
        RemoteSpaceSummary spaces[] = service.getSpaces(token);
        StringBuffer list = new StringBuffer();
        for (int i = 0; i < spaces.length; i++) {
        	list.append("key: " + spaces[i].getKey()
        			+ "; name: " + spaces[i].getName()
        			+ "; url: " + spaces[i].getUrl()
                    + "; type: " + spaces[i].getType() + ";")
        	    .append("\n");
        }
        if (jsapResult.userSpecified("file")) {
            writeToFile(list.toString().getBytes(), getString("file"));
            return spaces.length + " spaces in list written to file: " + getString("file");
        }
        return spaces.length + " spaces in list:\n" + list.toString();
    }

    /*
     * Get page list - list of pages in a space, perhaps with subsetting
     */
    public String getPageList() throws java.rmi.RemoteException, ClientException {

    	String space = getRequiredString("space");
    	RemotePageSummary list[] = null;

		if (jsapResult.userSpecified("title")) {
	    	String title = getString("title");
			long id = getContentId(title, space);
			if (jsapResult.userSpecified("ancestors")) {
				list = service.getAncestors(token, id);
			} else if (jsapResult.userSpecified("descendents")) {
				list = service.getDescendents(token, id);
			} else if (jsapResult.userSpecified("children")) {
				list = service.getChildren(token, id);
			}
		}
		if (list == null) { // list of pages in a space
			list = service.getPages(token, space);
		}

        StringBuffer output = new StringBuffer();
        for (int i = 0; i < list.length; i++) {
        	output.append(list[i].getTitle())
        	      .append("\n");
        }
        if (jsapResult.userSpecified("file")) {
            writeToFile(output.toString().getBytes(), getString("file"));
            return list.length + " pages in list written to file: " + getString("file");
        }
        return list.length + " pages in list:\n" + output.toString();
    }

    /*
     * Get news (blog) list - list of news in a space
     */
    public String getNewsList() throws java.rmi.RemoteException, ClientException {

    	String space = getRequiredString("space");
    	RemoteBlogEntrySummary list[] = null;

   		list = service.getBlogEntries(token, space);

        StringBuffer output = new StringBuffer();
        for (int i = 0; i < list.length; i++) {
        	output.append(list[i].getTitle())
        	      .append("\n");
        }
        if (jsapResult.userSpecified("file")) {
            writeToFile(output.toString().getBytes(), getString("file"));
            return list.length + " news entries in list written to file: " + getString("file");
        }
        return list.length + " news entries in list:\n" + output.toString();
    }

    /*
     * Get label list - list of labels
     */
    public String getLabelList() throws java.rmi.RemoteException, ClientException {

    	RemoteLabel list[] = null;
		String title = getString("title");
    	String space = getString("space");
    	String labels = getString("labels");
    	String label = null;
    	if (jsapResult.userSpecified("labels")) {
    		label = (labels.split(","))[0];  // only use the first label from the list !!!
    	}
    	int count = jsapResult.getInt("count");  // get maximum number specified by user or no max default

    	if (jsapResult.userSpecified("space")) {
        	if (jsapResult.userSpecified("mostPopular")) {
        		list = service.getMostPopularLabelsInSpace(token, space, count);
        	} else if (jsapResult.userSpecified("recentlyUsed")) {
        		list = service.getRecentlyUsedLabelsInSpace(token, space, count);
        	} else if (label != null) {
        		list = service.getRelatedLabelsInSpace(token, label, space, count);
        	} else if (jsapResult.userSpecified("title")) {
        		list = service.getLabelsById(token, getContentId(title, space));
        	}
    	} else if (jsapResult.userSpecified("mostPopular")) {
    		list = service.getMostPopularLabels(token, count);
        } else if (jsapResult.userSpecified("recentlyUsed")) {
        	list = service.getRecentlyUsedLabels(token, count);
        } else if (label != null) {
        	list = service.getRelatedLabels(token, label, count);
        }

    	if (list == null) {
            throw new ClientException("Invalid request for a list of labels.");
    	}
        StringBuffer output = new StringBuffer();
        for (int i = 0; (i < list.length) && (i < count); i++) {
        	output.append(list[i].getName())
        	      .append("\n");
        }
        if (list.length < count) {
        	count = list.length;
        }
        if (jsapResult.userSpecified("file")) {
            writeToFile(output.toString().getBytes(), getString("file"));
            return list.length + " labels written to file: " + getString("file");
        }
        return count + " labels in list:\n" + output.toString();
    }

    /*
     * Get group list
     */
    public String getGroupList() throws java.rmi.RemoteException, ClientException {

    	String list[] = null;

    	if (jsapResult.userSpecified("userId")) {
    		list = service.getUserGroups(token, getString("userId"));
    	} else {
    		list = service.getGroups(token);
    	}

        StringBuffer output = new StringBuffer();
        for (int i = 0; i < list.length; i++) {
        	output.append(list[i])
        	      .append("\n");
        }
        if (jsapResult.userSpecified("file")) {
            writeToFile(output.toString().getBytes(), getString("file"));
            return list.length + " groups in list written to file: " + getString("file");
        }
        return list.length + " groups in list:\n" + output.toString();
    }

    /*
     * Get user list
     */
    public String getUserList() throws java.rmi.RemoteException, ClientException {

    	String list[];
    	list = service.getActiveUsers(token, true); // true probably means all users

        StringBuffer output = new StringBuffer();
        for (int i = 0; i < list.length; i++) {
        	output.append(list[i])
        	      .append("\n");
        }
        if (jsapResult.userSpecified("file")) {
            writeToFile(output.toString().getBytes(), getString("file"));
            return list.length + " users in list written to file: " + getString("file");
        }
        return list.length + " users in list:\n" + output.toString();
    }

    /*
     * Get permission list
     * - if space is not provided, just get list of all permission possible
     * - if space is provided, list space permissions
     * - if space and title provided, list page permissions
     * - if userId specified, list permissions for specific user
     */
    public String getPermissionList() throws java.rmi.RemoteException, ClientException {

    	String list[] = null;
    	RemotePermission permissionList[] = null;

		String title = getString("title");
    	String space = getString("space");
    	String userId = getString("userId");
    	String userMessage = "";

    	if (jsapResult.userSpecified("space")) {
    		if (jsapResult.userSpecified("title")) {          // page permissions
    			permissionList = service.getPagePermissions(token, getContentId(title, space));
    		} else if (jsapResult.userSpecified("userId")) {  // user based space permissions
    			if (userId.equals("")) {  // blank means current user
    				list = service.getPermissions(token, space);
    				userId = jsapResult.getString("user");
    			} else {
    				list = service.getPermissionsForUser(token, space, userId);
    			}
    			userMessage = "for user '" + userId + "' ";
    		} else {                                          // all space permissions
    			list = service.getPermissions(token, space);
    		}
    	} else {                                              // all available permissions
    		list = service.getSpaceLevelPermissions(token);
    	}

        StringBuffer output = new StringBuffer();
        int count = 0;
        if (list != null) {
        	count = list.length;
        	for (int i = 0; i < list.length; i++) {
        		output.append(list[i])
        			  .append("\n");
        	}
        } else {
        	count = permissionList.length;
        	for (int i = 0; i < permissionList.length; i++) {
        		output.append(permissionList[i].getLockType() + "; " + permissionList[i].getLockedBy())
        			  .append("\n");
        	}
        }
        if (jsapResult.userSpecified("file")) {
            writeToFile(output.toString().getBytes(), getString("file"));
            return count + " permissions in list written to file: " + getString("file");
        }
        return count + " permissions " + userMessage + "in list:\n" + output.toString();
    }
    /*
     * Export space.  Not working... error getting the file that is produced.
     */
    public String exportSpace(String user, String password) throws java.rmi.RemoteException, ClientException {
        try {
	    	String space = getRequiredString("space");
	        String urlString = service.exportSpace(token, space, "TYPE_" + getString("exportType"));
	        urlString = urlString.replace('\\', '/');
	        String urlStringWithUser = urlString + "?os_username=" + user + "&os_password=" + password;
	
	        File file = copyUrlToFile(urlStringWithUser);
	        return "Space exported with url: " + urlString + " to file: " + file.getPath();
        } catch (IOException exception) {
        	throw new ClientException(exception.toString());
        }
    }

    /*
     * Export space.  Just shows URL.
     */
    public String exportSite(String user, String password) throws java.rmi.RemoteException, ClientException {
        try {
	    	long startTime = System.currentTimeMillis();
	        String urlString = service.exportSite(token, jsapResult.getBoolean("exportAttachments"));
	        urlString = urlString.replace('\\', '/');
	        String urlStringWithUser = urlString + "?os_username=" + user + "&os_password=" + password;
	
	        File file = copyUrlToFile(urlStringWithUser);
	    	String elapsedString = " with elapsed time: " + ((System.currentTimeMillis() - startTime)/60000) + " minutes";
	        return "Site exported with url:" + urlString + " to file: " + file.getPath() + elapsedString;
        } catch (IOException exception) {
        	throw new ClientException(exception.toString());
        }
    }

    /*
     * Load files
     */
    public String loadFiles() throws java.rmi.RemoteException, ClientException {

    	try {
	    	long startTime = System.currentTimeMillis();
	    	String space = getRequiredString("space");
	    	String file = getRequiredString("file");
	    	String parent = getString("parent");   // make the high level page a child of this page
	    	String title = getString("title");     // this will be name of the high level page
	    	String group = getString("group");
	    	String content = "{children} ";     // default content for directory pages
	    	boolean replace = jsapResult.userSpecified("replace");
	    	
	    	HashMap<String, String> nameMap = new HashMap<String, String>();
	    	HashMap<String, Long> pageIdMap = new HashMap<String, Long>();
	    	HashMap<String, String> attachmentMap = new HashMap<String, String>();
	    	
	    	if (jsapResult.userSpecified("content")) {
	    		content = jsapResult.getString("content");
	    	}
	    	
	    	File[] fileList;
	
	    	File directory = new File(file);
	    	if (!jsapResult.userSpecified("title")) {  // no title given
	    		title = directory.getName();
	    	}
	    	
	    	if (directory.isDirectory()) { 
	        	fileList = directory.listFiles();
	    	} else {
	    		fileList = new File[1];
	    		fileList[0] = directory;
	    	} 
	    	
	    	if (verbose) {
	    		out.println("Load start time: " + Calendar.getInstance().getTime());
	    	}
	    	// Create high level page
	    	RemotePage page;
	    	try {
	    		page = storePage(null, title, space, parent, content, replace);
	    		if (!"".equals(group)) {
	    			addPagePermission(PAGE_PERMISSION_EDIT, page.getId(), group, true);
	    		}
	    	} catch (ClientException ignore){
	    		// ignore page already exists - leave content as is
	    		page = getPage(title, space);  // resolve to the existing page
	    	}
	
			String relativePath = null;
			
		    int processedCount = processFilesPart1(fileList, title, space, content, replace, group, pageIdMap, nameMap, attachmentMap, relativePath, page.getId());
		    processedCount = processedCount 
		                     + processFilesPart2(fileList, title, space, content, replace, group, pageIdMap, nameMap, attachmentMap, relativePath);
			if (debug) {
				log.debug(attachmentMap.toString());
				log.debug(nameMap.toString());
			}
	    	if (verbose) {
	    		out.println("Load end time: " + Calendar.getInstance().getTime());
	    		out.println("Elapsed seconds = " + (System.currentTimeMillis() - startTime)/1000);
	    	}
	        return processedCount + " files/directories loaded from '" + file + "' to page '" + page.getTitle() + "' in space '" + space + "'";
        } catch (IOException exception) {
        	throw new ClientException(exception.toString());
        }
    }
    
    /*
     * Process files looking for all directories
     * - pageIdMap is critical to set up first before processing content files
     */
    public int processFilesPart1(File fileList[], String parentTitle, String space, String content, boolean replace, String group,
    		                 Map<String,Long> pageIdMap, Map<String, String> nameMap, Map<String, String> attachmentMap, String relativePath, long pageId) throws java.rmi.RemoteException, ClientException {

    	int processedCount = 0;	
	    
    	for (int i = 0; i < fileList.length; i++) {   // process all directories first - create pages for each one
			String fileName = fileList[i].getName();
			String newRelativePath = (relativePath == null) ? fileName : relativePath + "/" + fileName;
 
		    checkLogin();  // make sure don't time out on the login
			if (fileList[i].isDirectory()) { 
    			
				File newFileList[] = fileList[i].listFiles();
				
				if (newFileList.length > 0) {    // don't process empty directories at all
					String title = parentTitle + " - " + fileName; 
	    			RemotePage page = storePage(null, title, space, parentTitle, content, replace);
	    			if (!"".equals(group)) {
	    				addPagePermission(PAGE_PERMISSION_EDIT, page.getId(), group, true);
	    			}
	    			pageIdMap.put(newRelativePath, page.getId());
	    			nameMap.put(newRelativePath, title);
	    			log.debug("Map " + newRelativePath + " to " + parentTitle);
	    				
	            	processedCount = processedCount
	            	                 + processFilesPart1(newFileList, title, space, content, replace, group, pageIdMap, nameMap, attachmentMap, newRelativePath, page.getId());
	            	processedCount++;
				}
    		} else {             // now handle files
    			String mime = new MimetypesFileTypeMap().getContentType(fileList[i].getName());
				if (mime.equalsIgnoreCase("text/html") || mime.equalsIgnoreCase("text/plain")) {   // add as page content
					
        			String title = parentTitle + " - " + fileList[i].getName();
        			
        			RemotePage page = storePage(null, title, space, parentTitle, "", replace); // create page with no content, handle content in part2      			       			
        			nameMap.put(newRelativePath, title);  									
					pageIdMap.put(newRelativePath, page.getId());
        			log.debug("Map " + newRelativePath + " to " + title + " with page id " + page.getId());
        			
				} else {                                                                           // add as an attachment
			        RemoteAttachment attachment = addAttachment(parentTitle, space, null, mime, null, fileList[i].getPath(), null);  // null comment, null content
			        log.debug("Attachment '" + attachment.getFileName() + "' of type '" + attachment.getContentType() 
			        		    + "' added to page '" + parentTitle + "' in space '" + space + "' with url: " + attachment.getUrl());   
			        attachmentMap.put(newRelativePath, attachment.getFileName());  
			        pageIdMap.put(newRelativePath, attachment.getPageId());
			        nameMap.put(newRelativePath, parentTitle);
	    			log.debug("Map " + newRelativePath + " to " + parentTitle + " with file name " + attachment.getFileName());
				}  
    		}
    	}
    	log.debug(processedCount);
        return processedCount;
    }
 
    /*
     * Process files
     */
    public int processFilesPart2(File fileList[], String parentTitle, String space, String content, boolean replace, String group,
    		                 Map<String,Long> pageIdMap, Map<String, String> nameMap, Map<String, String> attachmentMap, String relativePath) throws java.rmi.RemoteException, ClientException {
 
    	int processedCount = 0;
	    
    	for (int i = 0; i < fileList.length; i++) {    // process files
    	    checkLogin();  // make sure don't time out on the login
			String fileName = fileList[i].getName();
			String newRelativePath = (relativePath == null) ? fileName : relativePath + "/" + fileName;
			log.debug("new relative path: " + newRelativePath);
			log.debug("Processing file: " + fileName);
			
			if (fileList[i].isDirectory()) { 
    			String pageName = nameMap.get(newRelativePath);
            	processedCount = processedCount
            	               + processFilesPart2(fileList[i].listFiles(), pageName, space, content, replace, group, pageIdMap, nameMap, attachmentMap, newRelativePath); 
    		} else {
    			String mime = new MimetypesFileTypeMap().getContentType(fileList[i].getName());
    			boolean isHtml = mime.equalsIgnoreCase("text/html");
    			boolean isText = mime.equalsIgnoreCase("text/plain");
    			if (isHtml || isText) {					
    				Long pageId = pageIdMap.get(newRelativePath);
    				if (pageId != null) {
						RemotePage page = getPage(pageId);  
    					if (isHtml) {
    						page.setContent(getHtmlContent(content, fileList[i].getPath(), pageIdMap, nameMap, attachmentMap)); 
    	        			if (!"".equals(group)) {
    	        				addPagePermission(PAGE_PERMISSION_EDIT, pageId, group, true);
    	        			}
    					} else {
    						page.setContent(content + getFileAsString(fileList[i].getPath()));
    					}
    					service.storePage(token, page); 
    				} else {
    					log.debug(newRelativePath + " not found in page id lookup.");
    				}
    			} 
    			processedCount++;
    		}
    	}
    	log.debug(processedCount);
        return processedCount;
    }
       
    /*
     * Get HTML content      
     */
    public String getHtmlContent(String content, String fileName, Map<String,Long> pageIdMap, Map<String, String> nameMap, Map<String, String> attachmentMap) throws ClientException {    
    	return content + " {html} " + processAsHtml(getFileAsString(fileName), pageIdMap, nameMap, attachmentMap) + " {html} ";
    }
    
    /*
     * processAsHtml
     * - handle various combinations and convert appropriately   
     * - use regex to find and replace links, ignore whitespace (/s*) in the find sequence
     *   - examples:
     *   -- src="file"
     *   -- src = 'file '
     *   -- href="\file"
     *   -- href="../../file"
     *   
     */
    public String processAsHtml(String content, Map<String,Long> pageIdMap, Map<String, String> nameMap, Map<String, String> attachmentMap) throws ClientException {
    	
    	if (debug) {
    		log.debug("---------------------------------------------------------");
    		int show = (content.length() > 800) ? 800 : content.length();
    		log.debug("Before: " + content.substring(0, show));
    	}
    	Iterator<String> iterator = nameMap.keySet().iterator();
    	while (iterator.hasNext()) {
    		String key = iterator.next();
    		String link;
			if (attachmentMap.get(key) != null) {
				link = "/download/attachments/" + pageIdMap.get(key) + "/" + attachmentMap.get(key);
			} else {
				link = "/pages/viewpage.action?pageId=" + pageIdMap.get(key);
			}		
			              // <--- $1 ---><- $2 >                                          <- $3 >    capture groups
			//String find = "(src=|href=)(\"|\')(?:(?:/)|(?:../)*)" + escapeRegex(key) + "(\"|\')";   
			
                         // <------ $1 ------>    <- $2 >                                              <- $3 >    capture groups			
	        String find = "((?:src|href)\\s*=)\\s*(\"|\')(?:(?:/)|(?:../)*)" + escapeRegex(key) + "\\s*(\"|\')";   		
	    	String replacement = "$1$2" + link + "$3";
    		
    		//if (debug) {
    			//log.debug("Replace " + find + " with " + replacement);
    		//}
    		content = content.replaceAll(find, replacement);  
    	}
    	if (debug) {
    		int show = (content.length() > 1000) ? 1000 : content.length();
    		log.debug("After:  " + content.substring(0, show));
        	log.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
    	}   	
    	return content;
    }
    
    /*
     * Escape regex characters in a string
     *  - use '\\' since '\\' is java for '\' which is the escape character for regex
     *  - use (c|c|c| ... |c) where c is one of the special regex charachters, and we are looking for any of these characters
     *  - use ([cccccccccc]) where c is any one of the special characters, (...) is for a capturing group so $1 is defined
     */
    public String escapeRegex(String string) {
    	
   	    //  char specialCharacters[] = {'/','.','*','+','?','|','(',')','[',']','{','}','\\'};
    	//String specialCharacters = "(\\/|\\.|\\*|\\+|\\?|\\||\\(|\\)|\\[|\\]|\\{|\\}|\\\\)"; // double escape all regex special characters
    	String specialCharacters = "([\\/\\.\\*\\+\\?\\|\\(\\)\\[\\]\\{\\}\\\\])"; // double escape all regex special characters
        return string.replaceAll(specialCharacters, "\\\\$1");  // double escape \ since it is a regex special character as well
    }
    
    /*
     * Copy URL content to file specified in file parameter.
     */
    public File copyUrlToFile(String urlString) throws java.rmi.RemoteException, IOException, MalformedURLException, ClientException {
        String fileName = getRequiredString("file");

        InputStream in = null;
        FileOutputStream out = null;
		try {
            URL url = new URL(urlString);
            in = url.openStream();

		    File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            out = new FileOutputStream(file);

			int ch;
			while((ch = in.read()) != -1) {
				out.write(ch);
			}
            return file;
		}
		finally {
			if (in != null) {
				in.close();
            }
			if (out != null) {
				out.close();
            }
		}
    }

    
    /*
     * -------------------------------------------------------------------------------------------------------------------
     * Help functions
     * -------------------------------------------------------------------------------------------------------------------
     */
        
    /*
     * Get page by name
     * - return page or throw exception
     */
    public RemotePage getPage(String pageTitle, String spaceKey) throws ClientException, InvalidSessionException {
        try {
            return service.getPage(token, spaceKey, pageTitle);
        }
        catch (InvalidSessionException exception) {
        	if (verbose) {
        		out.println("Session expired.");
        	}
        	throw exception;
        }
        catch (java.rmi.RemoteException exception) {
            String message = "Page '" + pageTitle + "' not found in space '" + spaceKey + "'";
            if (verbose) {
                exception.toString();
            }
            throw new ClientException(message);
        }
    }
    
    /*
     * Get page by id
     * - return page or throw exception
     */
    public RemotePage getPage(long pageId) throws ClientException {
        try {
            return service.getPage(token, pageId);
        }
        catch (java.rmi.RemoteException exception) {
            String message = "Page with id " + pageId + " was not found or you are not authorized to it.";
            if (verbose) {
                exception.toString();
            }
            throw new ClientException(message);
        }
    }

    /*
     * Get news item
     * - return news item or throw exception
     */
    public RemoteBlogEntry getNews(String title, String spaceKey, Calendar calendar) throws ClientException {
    	RemoteBlogEntry news;
    	String dayMessage = "";
    	if (jsapResult.userSpecified("dayOfMonth")) {
    		dayMessage = "for day of month: " + jsapResult.getInt("dayOfMonth");
    	}
	    try {
	    	//int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
	    	//log.debug(dayOfMonth);
	        news = service.getBlogEntryByDayAndTitle(token, spaceKey, calendar.get(Calendar.DAY_OF_MONTH), title);
	    }
        catch (java.rmi.RemoteException exception) {
            String message = "News '" + title + "' not found in space '" + spaceKey + "' " + dayMessage;
            if (verbose) {
                exception.printStackTrace(err);
            }
            throw new ClientException(message);
        }
	    return news;
    }

    /*
     * Get calendar for news item
     * - return calendar or throw exception for bad dayOfMonth parameter
     */
    public Calendar getCalendar() throws ClientException {

	    Calendar calendar = Calendar.getInstance();  // represents now (on client!!)
	    if (jsapResult.userSpecified("dayOfMonth")) {
            calendar.set(Calendar.DAY_OF_MONTH, jsapResult.getInt("dayOfMonth"));
	    }
	    return calendar;
    }

    /*
     * Add labels  - helper functions
     */
    public void addLabels(String labels, long id) throws ClientException {
    	addLabels(labels, id, "");
    }
    public void addLabels(String labels, String space) throws ClientException {
    	addLabels(labels, 0, space);
    }

    /*
     * Add labels to a page or blog with given id or space - helper function
     *  - note that the API seems to accept a blank separated list of labels as well
     */
    public void addLabels(String labels, long id, String space) throws ClientException {
    	String[] list = labels.split(",");
        try {
        	for (int i = 0; i < list.length; i++) {
        		if (id > 0) {
        			service.addLabelByName(token, list[i].trim(), id);
        		} else {
        			service.addLabelByNameToSpace(token, list[i].trim(), space);
        		}
        	}
        }
        catch (java.rmi.RemoteException exception) {
            String message = "Error adding labels.";
            if (verbose) {
                exception.printStackTrace(err);
            }
            throw new ClientException(message);
        }
    }

    /*
     * Remove labels  - helper functions
     */
    public String removeLabels(String labels, long id) throws ClientException {
    	return removeLabels(labels, id, "");
    }
    public String removeLabels(String labels, String space) throws ClientException {
    	return removeLabels(labels, 0, space);
    }

    /*
     * Remove labels from page, news, or space - helper function
     *  - ignore error removing any specific label
     *  - return the list of labels that were successfully removed
     */
    public String removeLabels(String labels, long id, String space) {
    	String goodLabels = "";
    	String[] list = labels.split(",");
       	for (int i = 0; i < list.length; i++) {
       		String label = list[i].trim();
            try {
        		if (id > 0) {
        			service.removeLabelByName(token, list[i].trim(), id);
        		} else {
        			service.removeLabelByNameFromSpace(token, list[i].trim(), space);
        		}
            	goodLabels = goodLabels + label + " ";
        	}
            catch (java.rmi.RemoteException exception) {
                err.println("Continue and ignore: " + exception.toString());
            	if (verbose) {
            		exception.printStackTrace(err);
            	}
            }
        }
       	return goodLabels;
    }

    /*
     * Get contentId for pages or news items
     * - treat as a page request unless user explicitly asks for news
     * - if problems finding item, throws ClientException
     */
    public long getContentId(String title, String space) throws java.rmi.RemoteException, ClientException {
    	return getContentId(title, space, false);
    }

    public long getContentId(String title, String space, boolean newsOnly) throws java.rmi.RemoteException, ClientException {
    	long contentId;
    	if (newsOnly || jsapResult.userSpecified("news") || jsapResult.userSpecified("dayOfMonth")) {
    		RemoteBlogEntry news = getNews(title, space, getCalendar());
    		contentId = news.getId();
    	} else {
    		RemotePage page = getPage(title, space);
    	    contentId = page.getId();
    	}
    	return contentId;
    }
    
    /*
     * Find and replace all text matching the replaceText map 
     */
    public String findReplace(String text) throws ClientException {

    	if (jsapResult.userSpecified("findReplace")) {    		
    		String[] list = splitCsvData(getString("findReplace"), ",", "'");
    		for (int i = 0; i < list.length; i++) {
	            String entry = list[i].trim();
	            if (!"".equals(entry)) {
	            	String[] colonList = splitCsvData(entry, ":", "'");
	            	String key   = (colonList.length > 0) ? stripQuotes(colonList[0].trim()) : "";
	            	String value = (colonList.length > 1) ? stripQuotes(colonList[1].trim()) : "";
	            	
	            	text = StringUtils.replace(text, key, value);
	            }
	        }
    	}
    	return text;
    }    
}