<head>
	<meta name="layout" content="main" />
	<title>User Registration</title>
	 <style>
		.right
		{
			position:absolute;
			right:0px;
			width:300px;
		}

	</style>	
</head>

<body>
	<div class="body">
		<div class='right'>
			<p>				
				<a class='home' href="${createLinkTo(dir:'')}">Home</a>
			<p>
		</div>
	
		<h1>User Registration</h1>
		<g:if test="${flash.message}">
		<div class="message">${flash.message}</div>
		</g:if>
		<g:hasErrors bean="${person}">
		<div class="errors">
			<g:renderErrors bean="${person}" as="list" />
		</div>
		</g:hasErrors>

		<g:form action="save">
		
		<div class="dialog">
		<table>
		<tbody>

			<tr class='prop'>
				<td valign='top' class='name'><label for='username'>Login Name:</label></td>
				<td valign='top' class='value ${hasErrors(bean:person,field:'username','errors')}'>
					<input type="text" name='username' value="${person?.username?.encodeAsHTML()}"/>
				</td>
			</tr>

			<tr class='prop'>
				<td valign='top' class='name'><label for='userRealName'>Full Name:</label></td>
				<td valign='top' class='value ${hasErrors(bean:person,field:'userRealName','errors')}'>
					<input type="text" name='userRealName' value="${person?.userRealName?.encodeAsHTML()}"/>
				</td>
			</tr>

			<tr class='prop'>
				<td valign='top' class='name'><label for='passwd'>Password:</label></td>
				<td valign='top' class='value ${hasErrors(bean:person,field:'passwd','errors')}'>
					<input type="password" name='passwd' value="${person?.passwd?.encodeAsHTML()}"/>
				</td>
			</tr>

			<tr class='prop'>
				<td valign='top' class='name'><label for='enabled'>Confirm Password:</label></td>
				<td valign='top' class='value ${hasErrors(bean:person,field:'passwd','errors')}'>
					<input type="password" name='repasswd' value="${person?.passwd?.encodeAsHTML()}"/>
				</td>
			</tr>

			<tr class='prop'>
				<td valign='top' class='name'><label for='email'>Email:</label></td>
				<td valign='top' class='value ${hasErrors(bean:person,field:'email','errors')}'>
					<input type="text" name='email' value="${person?.email?.encodeAsHTML()}"/>
				</td>
			</tr>

			<tr class='prop'>
				<td valign='top' class='name'><label for='accessId'>AccessId:</label></td>
				<td valign='top' class='value ${hasErrors(bean:person,field:'accessId','errors')}'>
					<input type="text" name='accessId' value="${person?.accessId?.encodeAsHTML()}"/>
				</td>
			</tr>

			<tr class='prop'>
				<td valign='top' class='name'><label for='secretKey'>SecretKey:</label></td>
				<td valign='top' class='value ${hasErrors(bean:person,field:'secretKey','errors')}'>
					<input type="text" name='secretKey' value="${person?.secretKey?.encodeAsHTML()}"/>
				</td>
			</tr>

			<tr class='prop'>
				<td valign='bottom' class='name'><label for='code'>Enter Code: </label></td>
				<td valign='top' class='name'>
					<input type="text" name="captcha" size="8"/>
					<img src="${createLink(controller:'captcha', action:'index')}" align="absmiddle"/>
				</td>
			</tr>

		</tbody>
		</table>
		</div>

		<div class="buttons">			
				<input type="submit" value="Create"></input>				
		</div>

		</g:form>
	</div>
</body>
