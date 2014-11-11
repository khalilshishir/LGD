<%@ page import="hrms.HrEmployee" %>
<%--
  Created by IntelliJ IDEA.
  User: kcbarmon
  Date: 6/12/13
  Time: 1:16 PM
  To change this template use File | Settings | File Templates.
--%>
%{--<html>
<body>
<div id="wrapper">--}%

    <g:if test="${hrEmployeeInstance?.imageName}">
        <div id="prvImg" style="width: 100%; background: #fff; padding: 5px;border: 1px solid #ccc; display: block;">
            <g:img dir="employeeImage" file="${hrEmployeeInstance?.imageName}"   style="border: solid 1px; width: 30%; height: 10%; "/>
        </div>
        <div id="newImg" style="width: 100%; background: #fff; padding: 5px;border: 1px solid #ccc; display: none;">
            <g:img uri="/images/no-image.jpg" id="uploadPhotoFile" style="border: solid 1px; width: 30%; height: 10%; "/>
        </div>
    </g:if>
    <g:else>
        <div style="width: 100%; background: #fff; padding: 5px;border: 1px solid #ccc;">
            <g:img uri="/images/no-image.jpg" id="uploadPhotoFile" style="border: solid 1px; width: 30%; height: 10%; "/>
        </div>
    </g:else>
    <br/>
    <div style="width: 100%; background: #fff; padding: 5px;border: 1px solid #ccc;">
        Photo: <input type="file" id="imagePath" name="imagePath" onchange="showPhoto(this)"/>
    </div>

%{--
</div>
</body>
</html>--}%
<script type="text/javascript">

    function showPhoto(imageFile) {
        document.getElementById("prvImg").style.display='none';
        document.getElementById("newImg").style.display='block';
        var fileReader = new FileReader();
        var image = document.getElementById("uploadPhotoFile");
        fileReader.onload = function (e) {
            image.src = e.target.result;
        }
        fileReader.readAsDataURL(imageFile.files[0]);
    }
</script>