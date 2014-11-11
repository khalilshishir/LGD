<%@ page import="hrms.HrEmployee" %>
<%--
  Created by IntelliJ IDEA.
  User: maruf
  Date: 6/12/13
  Time: 1:16 PM
  To change this template use File | Settings | File Templates.
--%>
%{--<html>
<body>--}%
%{--<div id="wrapper">--}%
<div style="width: 100%; background: #fff; padding: 5px;border: 1px solid #ccc; ">
    <table class="promint_block">
        <tr class="captionSpaceFirst">
            <td class="captionSpaceFirst">

                <g:img uri="/images/no-image.jpg" id="uploadPhotoFile" height="240px" width="200px"/>

            </td>
        </tr>
        <tr class="captionSpaceFirst">
            <td class="captionSpaceFirst">

                Photo: <input type="file" id="imagePath" name="imagePath" onchange="showPhoto(this)"/>

            </td>
        </tr>
    </table>
%{--    <div style="width: 100%; background: #fff; padding: 5px;border: 1px solid #ccc; float: left;">
        <g:img uri="/images/no-image.jpg" id="uploadPhotoFile" height="240px" width="200px"/>
    </div>
    <div style="width: 100%; background: #fff; padding: 5px;border: 1px solid #ccc; float: left;">
        Photo: <input type="file" id="imagePath" name="imagePath" onchange="showPhoto(this)"/>
    </div>--}%
</div>
%{--
</div>
</body>
</html>--}%
<script type="text/javascript">

    function showPhoto(imageFile) {
        var fileReader = new FileReader();
        var image = document.getElementById("uploadPhotoFile");
        fileReader.onload = function (e) {
            image.src = e.target.result;
        }
        fileReader.readAsDataURL(imageFile.files[0]);
    }
</script>