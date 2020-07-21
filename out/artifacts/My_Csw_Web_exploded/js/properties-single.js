function load() {
    $(document).ready(function () {
        $.ajax({
            url:"../header",
            type:"get",
            dataType:"text",
            success:function (data) {
                if (data!="null") {
                    document.getElementById("welcome").innerHTML = data+",欢迎您！";
                    document.getElementById("welcome").style.display = "inline";
                    document.getElementById("logout").style.display = "inline";
                    document.getElementById("login").style.display = "none";
                    document.getElementById("register").style.display = "none";
                }
            }
        })
    })
}

$(function () {
    var address = location.href;
    console.log("Address: "+address);
    var id = address.split("?")[1].substring(3);
    $.ajax(
        $(document).ready(function () {
            $.ajax({
                url:"getSingleProperties",
                type:"get",
                dataType:"json",
                data:{
                    id:id,
                },
                success:function (data) {
                   if (data!=null) {
                       $("#name").html(data.name);
                       $('#description').html(data.description);
                       $('#time').html(data.time);
                       $('#startAndEndTime').html(data.startAndEndTime);
                       $('#type').html(data.type);
                       $('#keywords').html(data.keywords);
                       $('#owner').html(data.owner);
                       $("#envelope").html(data.envelope);
                       $('#filePath').val(data.fileNames);
                   }
                }
            })
        })
    )
})

function downloadFile() {
    $("#fileForm").submit();
}