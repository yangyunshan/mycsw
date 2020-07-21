function manualInsert() {
    $(document).ready(function () {
        var data = new FormData($("#manualInsertData")[0]);
        $.ajax({
            url:"manualInsert",
            type:"post",
            dataType:"json",
            data:data,
            processData:false,
            contentType:false,
            success:function (data) {
                var count = data.insertCount;
                console.log("Count: "+count);
                if (parseInt(count)>0) {
                    alert("Upload Success!");
                } else {
                    alert("Upload Failed!");
                }
            },
            error:function (data) {
                alert("Upload Failed");
            }
        })
    })
}

$(function () {
    $('.dateselect').datetimepicker({
        format:'yyyy-mm-dd hh:ii:ss'
    });
})

function getFileName(textId,fileObj) {
    var obj = fileObj;
    var length = obj.files.length;

    var fileName = "";
    for (var i=0;i<length;i++) {
        var temp = obj.files[i].name;
        fileName += temp+",";
    }
    $("#"+textId).val(fileName.substring(0,fileName.length-1));
}

function autoInsert() {
    $(document).ready(function () {
        var data = new FormData($("#autoInsertData")[0]);
        $.ajax({
            url: "autoInsert",
            type:"post",
            dataType: "json",
            data:data,
            processData: false,
            contentType:false,
            success:function (data) {
                var count = data.insertCount;
                console.log("Count: "+count);
                if (parseInt(count)>0) {
                    alert("Upload Success!");
                } else {
                    alert("Upload Failed!");
                }
            }
        })
    })
}