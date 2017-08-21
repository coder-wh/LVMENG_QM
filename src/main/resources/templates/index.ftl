<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <script src="${request.contextPath}/js/jquery-3.2.1.min.js" type="text/javascript"></script>
    <script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="${request.contextPath}/js/ajaxfileupload.js" type="text/javascript"></script>
    <title>Document</title>
    <style>
        ul li{
            list-style: none;
            float: left;
            margin-right: 40px;
        }
        input{
            height: 28px;
            width:60px;
        }
        .content{
            border: 1px solid #cccccc;
            width:40%;
            height:200px;
            margin:auto;
            margin-top: 15%;
        }
        .header{
            height: 80px;
        }
        .header input{
            margin: 6% 46%;
        }
        .liList{
            height: auto;
        }
        .liList ul{
            margin:5% 21%;
        }
        .clear{
            clear: both;
            visibility: hidden;
        }
    </style>
</head>
<body>
    <div class="content">
        <div class="header">
            <label>
               <input type="file" name="file" style="display:none;"  id="file"  size="10"  />
               <a class="upload_a" style="display:inline-block" >选择</a>
               <span id='textfield_a'></span>
           	</label>
            <br>
           	<input type="button" id="upload" value="上传" class=" upload" style="display:inline-block;"/>
           	<input style="width:0.1px;border:0;" type="text" id="" name="upload" required value="">
       </div>
        <div class="clear"></div>
    </div>
</body>
<script>
	$("#upload").click(function () {
        $.ajaxFileUpload({
            url: '${request.contextPath}/qn/upload',
            secureuri: false,
            fileElementId:'file',
            dataType: 'json',
            success: function (data){
				console.log(data.c);
				console.log(data.i);
            }
        });
    })
    
    $('#file').change(function(){
        var file = $(this).val();
        var strFileName=file.replace(/^.+?\\([^\\]+?)(\.[^\.\\]*?)?$/gi,"$1");  //正则表达式获取文件名，不带后缀
        var FileExt=file.replace(/.+\./,"");
        var a=strFileName+'.'+FileExt
        $('#textfield_a').html(a)
    })
</script>
</html>