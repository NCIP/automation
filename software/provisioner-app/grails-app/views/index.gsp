<html>
	<head>
		<title>Application Provisioner
		</title>
		<meta name="layout" content="main"/>
		
		<g:javascript library="jquery" />
		 <style>
			 #login {
				margin:15px 0px; padding:0px;
				text-align:center;
			 }
			 #login .inner {
				width:260px;
				margin:0px auto;
				text-align:left;
				padding:10px;
				border-top:1px 
				border-bottom:1px 
			 }

			#login .inner .fheader {
				padding:4px;margin:3px 0px 3px 0;color:#2e3741;font-size:14px;font-weight:bold;
			}
			#login .inner .cssform p {
				clear: left;
				margin: 0;
				padding: 5px 0 8px 0;
				padding-left: 105px;
				border-top: 1px;
				margin-bottom: 10px;
				height: 1%;
			}
			#login .inner .cssform input[type='text'] {
				width: 120px;
			}
			#login .inner .cssform label {
				font-weight: bold;
				float: left;
				margin-left: -105px;
				width: 100px;
			}
			#login .inner .login_message {color:red;}
			#login .inner .text_ {width:120px;}
			#login .inner .chk {height:12px;}

		</style>
	</head>
	<body>
		<div id='login'>
			<div class='inner'>
				<g:if test='${flash.message}'>
					<div class='login_message'>${flash.message}</div>
				</g:if>
				<div class='fheader'>Application Provisioner Login (Beta)</div>
				<form action="${resource(file: 'j_spring_security_check')}" method="POST" id='loginForm' class='cssform'>
					<p>
						<label for='j_username'>Login ID</label>
						<input type='text' class='text_' name='j_username' id='j_username' value='${request.remoteUser}' />
					</p>
					<p>
						<label for='j_password'>Password</label>
						<input type='password' class='text_' name='j_password' id='j_password' />
					</p>
					<p>
						<input type='submit' value='Login' />
					</p>
				</form>
			</div>
		</div>
	</body>
</html>