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
							<label for=" " class="col-sm-5 control-label requiredField">User Name<span class="asteriskField" style="color:red"> * </span></label>
							<div class="col-sm-7">
								 <form:input class="form-control disabled" path="userName" 
									id="userName" name="userName" pattern="^[a-zA-Z][a-zA-Z0-9/\s]+"  title="Username should contain only alphabets." /><span id="nameloc" style="color:red"></span><br/>   
							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label requiredField">Login Name<span class="asteriskField" style="color:red"> * </span></label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" path="loginName"
									id="loginName" name="loginName"/><span id="loginloc" style="color:red"></span>
							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label requiredField">Login
								Password<span class="asteriskField" style="color:red"> * </span></label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" path="loginPassword"
									id="loginPassword" name ="loginPassword" /><span id="passwordloc" style="color:red"></span><br/>  
							</div>
						</div>

						
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label requiredField">Email ID<span class="asteriskField" style="color:red"> * </span></label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" path="email"
									id="email" name="email" /><span id="emailloc" style="color:red"></span><br/>
							</div>
						</div>

						<%-- <div class="form-group">
							<label for=" " class="col-sm-5 control-label">Is Active</label>
							<div class="col-sm-7">
								  <form:checkbox class="form-control disabled" checked="true" path="isActive" /> 
								<!-- <input type="checkbox" checked data-toggle="toggle"
									data-on="Active" data-off="In Active" data-onstyle="success"
									data-offstyle="danger"> -->
							</div>
						</div> --%>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Is Active</label>
							<div class="col-sm-7">
								<form:checkbox class="form-control disabled" cheked="true"
									data-toggle="toggle" data-on="Active" data-off="In Active"
									data-onstyle="success" data-offstyle="danger" path="isActive" />
								<!-- <i class="fa fa-toggle-on" aria-hidden="true"></i> -->
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
function validateform(){  
	var username = document.myform.userName.value;
	var loginname = document.myform.loginName.value;
	var password = document.myform.loginPassword.value;  
	var x=document.myform.email.value;
	var atposition=x.indexOf("@");  
	var dotposition=x.lastIndexOf(".");  
	//var status=false;  
	  
	if (username== null || username =="" ) {
		alert("Error : Username can't be blank");
		document.getElementById("nameloc").innerHTML="Username is required";
		return false;
	}else if(loginname== null || loginname==""){
		alert("Login Name can't be blank");
		document.getElementById("loginloc").innerHTML="Loginname is required";
		return false;
	}else if (password==null || password==""){
		alert("Password can't be blank");
		document.getElementById("passwordloc").innerHTML="Password is required";
		return false;	
	}else if (password.length < 4) {
		alert("Password must be at least 4 characters long.");
		document.getElementById("passwordloc").innerHTML="LoginPassword must contain minimum 4 letters";
		return false;
	}else if(x==null || x==""){
		alert("EmailId can't be blank");
		document.getElementById("emailloc").innerHTML="Email Id is required";
		return false;
	}else if (atposition<1 || dotposition<atposition+2 || dotposition+2>x.length){  
		document.getElementById("emailloc").innerHTML=  
			" Please enter a valid E-mail address";  
			  return false;  
}
	}
		
</script>
<jsp:include page="footer.jsp" />