<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

   <form action="auth" method="POST">
    <input type="hidden" name="action" value="initiateRegister"/>

    <label>Phone:</label>
    <input type="text" name="phone" required />
    
    <label>Password:</label>
    <input type="password" name="password" required />
    
    <button type="submit">Send OTP & Register</button>
</form>

<p style="color:red;">${error}</p>

</body>
</html>