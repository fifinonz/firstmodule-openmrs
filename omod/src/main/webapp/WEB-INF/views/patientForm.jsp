<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="/template/localHeader.jsp"%>

<p>New Patient</p>
<form class="form" action="<c:url value='/module/firstmodule/submitForm.form' />" method="post">

    <label for="given_name">Given Name:</label>
    <input type="text" name="given_name" id="given_name" required>

    <label for="middle_name">Middle Name:</label>
    <input type="text" name="middle_name" id="middle_name" required>

    <label for="family_name">Family Name:</label>
    <input type="text" name="family_name" id="family_name" required>

    <label for="dob">Date of birth:</label>
    <input type="date" name="dob" id="dob" required>

    <label for="gender">Gender:</label>
    <select id="gender" name="gender" >
        <option value="M">Male</option>
        <option value="M">Female</option>
    </select>

    <label for="address">Address:</label>
    <input type="text" name="address" id="address" required>

    <label for="postal_code">Postal Code:</label>
    <input type="text" name="postal_code" id="postal_code" required>


    <button>Submit</button>

</form>

<%@ include file="/WEB-INF/template/footer.jsp"%>