<html>
    <head>
        <title><g:layoutTitle default="Grails" /></title>
        <link rel="stylesheet" href="${createLinkTo(dir:'css',file:'main.css')}" />
        <link rel="shortcut icon" href="${createLinkTo(dir:'images',file:'nci-logo.gif')}" type="image/x-icon" />
        <g:layoutHead />
        <g:javascript library="application" />	
	 <style>
		#nciheader
		{ background:#a90101; height:37px }
		#ncilogo
		{ float:left; width:283px; height:37px; }
		#nihtag
		{ float:right; width:295px; height:37px; }
	</style>        
    </head>
    <body class="yui-skin-sam">
<div id="nciheader">
    <div id="ncilogo"><img src="${createLinkTo(dir:'images',file:'nci-logo.gif')}" alt="NCI" /></div>

    <div id="nihtag"><img src="${createLinkTo(dir:'images',file:'tagline.gif')}" width="295" height="33" alt="Logo: U.S. National Institutes of Health | www.cancer.gov" /></div>
</div>
        
        <g:layoutBody />		
    </body>	
</html>