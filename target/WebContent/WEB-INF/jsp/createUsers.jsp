<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />


<div id="page-wrapper">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header">Create New User</h3>
				<div class="col-md-6">
					<form:form class="form-horizontal" id="myform" name="myform"
						modelAttribute="UsersRequest" method="GET"
						onsubmit="return validateform()"
						action="${pageContext.request.contextPath}/Users/createOrUpdateUsers">

						<%-- <div class="form-group">
							<label for=" " class="col-sm-5 control-label">User ID</label>
							<div class="col-sm-7">

								<!-- <input type="text" id=""  placeholder="Employee Code" > -->
								<form:input type="hidden" class="form-control disabled"
									path="userId" />
							</div>
						</div> --%>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label requiredField">User Name<span class="asteriskField" style="color:red"> ** </span></label>
							<div class="col-sm-7">
								<%-- <form:input class="form-control disabled" path="userName" placeholder="userName"
									id="userName" name="userName" pattern="^[a-zA-Z][a-zA-Z0-9/\s]+" /><span id="nameloc" style="color:red"></span><br/>  --%>
									 <form:input class="form-control disabled" path="userName"
									id="userName" name="userName" pattern="^[a-zA-Z][a-zA-Z0-9/\s]+"  title="Username should only contain letters."/><span id="nameloc" style="color:red"></span><br/>    
							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label requiredField">Login Name<span class="asteriskField" style="color:red"> ** </span></label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" path="loginName"
									id="registration" />
							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label requiredField">Login
								Password<span class="asteriskField" style="color:red"> ** </span></label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" path="loginPassword"
									id="password" /><span id="passwordloc" style="color:red"></span><br/>  
							</div>
						</div>

						
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label requiredField">Email ID<span class="asteriskField" style="color:red"> ** </span></label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" path="email"
									id="registration" /><span id="emailloc" style="color:red"></span><br/>
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Is Active</label>
							<div class="col-sm-7">
								  <form:checkbox class="form-control disabled" checked="true" path="isActive" /> 
								<!-- <input type="checkbox" checked data-toggle="toggle"
									data-on="Active" data-off="In Active" data-onstyle="success"
									data-offstyle="danger"> -->
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-4 col-sm-8" style="margin-top: 10px;">
								<button type="submit" class="btn btn-primary">Create</button>
							</div>
						</div>
					</form:form>
				</div>
			</div>
		</div>

		<!-- ... Your content goes here ... -->

	</div>
</div>
<script>
	/* function validateform() {
		var name = document.myform.userName.value;
		var password = document.myform.password.value;

		if (name == null || name == "") {
			alert("Name can't be blank");
		document.myform.userName.focus();
			return false;
		} else if (password.length < 4) {
			alert("Password must be at least 4 characters long.");
			return false;
		}
	
		var x=document.myform.email.value;  
		var atposition=x.indexOf("@");  
		var dotposition=x.lastIndexOf(".");  
		if (atposition<1 || dotposition<atposition+2 || dotposition+2>=x.length){  
		  alert("Please enter a valid e-mail address \n atpostion:"+atposition+"\n dotposition:"+dotposition);  
		  return false;  
		  }  
		}    */
		
		function validateform(){  
			var name=document.myform.userName.value;  
			var password=document.myform.password.value;  
			var status=false;  
			  
			if(name.length<1){  
			document.getElementById("nameloc").innerHTML= 
			" <img src='unchecked.gif'/> Please enter your name";  
			return false;  
			}  
			  
			if(password.length<4){  
			document.getElementById("passwordloc").innerHTML=  
			" <img src='unchecked.gif'/> Password must be at least 4 char long";  
			return false;  
			} 
			 
		var x=document.myform.email.value;  
		var atposition=x.indexOf("@");  
		var dotposition=x.lastIndexOf(".");  
		if (atposition<1 || dotposition<atposition+2 || dotposition+2>x.length){  
			document.getElementById("emailloc").innerHTML=  
				" Please enter a valid e-mail address \n atpostion:"+atposition+"\n dotposition:"+dotposition;  
				  return false;  
	 
		}
		}
		 /*  var letters = /^[A-Za-z]+$/;  
	      if(userName.value.match(letters))  
	      {  
	    	  alert('Your registration number have accepted : you can try another');  
	    	  document.form1.text1.focus();  
	    	  return true;  
	    	  }  
	    	  else  
	    	  {  
	    	  alert('Please input alphanumeric characters only');  
	    	  return false;  
	    	  }   */
		/* var input = document.getElementById('userName');
	    	  input.oninvalid = function(event) {
	    		    event.target.setCustomValidity('Username should only contain lowercase letters. e.g. john');
	    		}
	      }   */
		
</script>
<jsp:include page="footer.jsp" />