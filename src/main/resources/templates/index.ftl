<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <script src="${request.contextPath}/js/jquery-3.2.1.min.js" type="text/javascript"></script>
    <script src="${request.contextPath}/js/ajaxfileupload.js" type="text/javascript"></script>
    <title>Document</title>
    <style>
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
        .header div{
        	margin:6% 25%;
        }
        .upload{
        	float: left;
        }
        .error{
        	color:red;
        	display: inline-block;
    		float: left;
        }
        .button {  
		    display: inline-block;  
		    zoom: 1; /* zoom and *display = ie7 hack for display:inline-block */  
		    *display: inline;  
		    vertical-align: baseline;  
		    margin: 0 2px;  
		    outline: none;  
		    cursor: pointer;  
		    text-align: center;  
		    text-decoration: none;  
		    font: 14px/100% Arial, Helvetica, sans-serif;  
		    padding: .5em 2em .55em;  
		    text-shadow: 0 1px 1px rgba(0,0,0,.3);  
		    -webkit-border-radius: .5em;   
		    -moz-border-radius: .5em;  
		    border-radius: .5em;  
		    -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.2);  
		    -moz-box-shadow: 0 1px 2px rgba(0,0,0,.2);  
		    box-shadow: 0 1px 2px rgba(0,0,0,.2);  
		    background-color: gainsboro;
		}  
		.button:hover {  
		    text-decoration: none;  
		}  
		.button:active {  
		    position: relative;  
		    top: 1px;  
		}  
		  
    </style>
</head>
<body>
    <div class="content">
        <div class="header">
            <div>
            	<label>
        	       <input type="file" name="file" value="" style="display:none;"  id="file"  size="10"  onchange="file_change()"/>
    	           <a class="select button" style="display:inline-block" >选择</a>
	               <span id='textfield_a'></span>
            	</label>
           	</div>
            <div>
            	<a type="button" id="upload" class="button upload" style="display:inline-block;"/>上传</a>
           		<span class="error"></span>
            </div>
        </div>
    </div>
</body>
<script>
	$("#upload").click(function () {
		$('.error').html("");
	
        $.ajaxFileUpload({
            url: '${request.contextPath}/qn/upload',
            secureuri: false,
            fileElementId:'file',
            dataType: 'json',
            success: function (data){
				$('.error').html(data.i);
            }
        });
    })
    
    function file_change(){
    	var file = $('#file').val();
        var strFileName=file.replace(/^.+?\\([^\\]+?)(\.[^\.\\]*?)?$/gi,"$1");  //正则表达式获取文件名，不带后缀
        var FileExt=file.replace(/.+\./,"");
        var a=strFileName+'.'+FileExt
        $('#textfield_a').html(a)
    }
    
</script>
</html>