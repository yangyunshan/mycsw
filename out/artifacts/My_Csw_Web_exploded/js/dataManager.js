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
                    document.getElementById("suggest_ul").style.display = "none";
                }
            }
        })
    })
}

function onlyShowWay1() {
    var obj1 = document.getElementById("way1");
    var obj2 = document.getElementById("way2");
    var obj3 = document.getElementById("update");
    var obj4 = document.getElementById("deleteById");
    obj1.style.display = "block";
    obj2.style.display = "none";
    obj3.style.display = "none";
    obj4.style.display = "none";
}

function onlyShowWay2() {
    var obj1 = document.getElementById("way1");
    var obj2 = document.getElementById("way2");
    var obj3 = document.getElementById("update");
    var obj4 = document.getElementById("deleteById");
    obj1.style.display = "none";
    obj2.style.display = "block";
    obj3.style.display = "none";
    obj4.style.display = "none";
}

function onlyShowUpdate() {
    var obj1 = document.getElementById("way1");
    var obj2 = document.getElementById("way2");
    var obj3 = document.getElementById("update");
    var obj4 = document.getElementById("deleteById");
    obj1.style.display = "none";
    obj2.style.display = "none";
    obj3.style.display = "block";
    obj4.style.display = "none";
}

function onlyShowDelete() {
    var obj1 = document.getElementById("way1");
    var obj2 = document.getElementById("way2");
    var obj3 = document.getElementById("update");
    var obj4 = document.getElementById("deleteById");
    obj1.style.display = "none";
    obj2.style.display = "none";
    obj3.style.display = "none";
    obj4.style.display = "block";
}

function getSuggestion(obj,suggestObj) {
    //如果文本框为空，不发送请求
    if ($(obj).val().length==0) {
        $(suggestObj).hide(0);
        return false;
    }
    var data = $(obj).val();
    $.ajax({
        url: "getSuggestInfo",
        type: "post",
        data: {"data":data},
        dataType: "json",
        success:function (data) {
            if (data!=null) {
                var list = "";
                for (var i=0;i<data.length;i++) {
                    var value = data[i]
                    var li = "<li>"+value+"</li>"
                    list += li;
                }
                $(suggestObj).html(list);
                $(suggestObj).show(0);
            }
        }
    })
    setInterval(function() {
        $("suggestObj li").hover(function () {
            $(this).css("background","#E86ED0");
        },function () {
            $(this).css("background","white");
        });
        $("#suggest_ul li").click(function () {
            $(obj).val($(this).html());
            $(suggestObj).hide(0);
        })
    },900);
}

function updateData() {
    $(document).ready(function () {
        var data = new FormData($("#updateData")[0]);
        $.ajax({
            url:"updateData",
            type:"post",
            data:data,
            dataType:"json",
            processData: false,
            contentType:false,
            success:function (data) {
                var count = data.updateCount;
                console.log("Count: "+count);
                if (parseInt(count)>0) {
                    alert("Success!");
                } else {
                    alert("Failed!");
                }
            }
        })
    })
}

function deleteDataById() {
    $(document).ready(function () {
        $.ajax({
            url:"deleteDataById",
            type:"post",
            data:$("#deleteById").serialize(),
            dataType:"json",
            success:function (data) {
                var count = data.deleteCount;
                console.log("Count: "+count);
                if (parseInt(count)>0) {
                    alert("Success!");
                } else {
                    alert("Failed!");
                }
            }
        })
    })
}

$(function () {
    $("#dataId1").keyup(function () {
        //如果文本框为空，不发送请求
        if ($("#dataId1").val().length==0) {
            $("#suggest_ul1").hide(0);
            return false;
        }
        var data = $("#dataId1").val();
        $.ajax({
            url: "getSuggestInfo",
            type: "post",
            data: {"data":data},
            dataType: "json",
            success:function (data) {
                if (data!=null) {
                    var list = "";
                    for (var i=0;i<data.length;i++) {
                        var value = data[i]
                        var li = "<li>"+value+"</li>"
                        list += li;
                    }
                    $("#suggest_ul1").html(list);
                    $("#suggest_ul1").show(0);
                }
            }
        })
    })
})

$(function () {
    $("#dataId1").keyup(function () {
        setInterval(function() {
            $("#suggest_ul1 li").hover(function () {
                $(this).css("background","#E86ED0");
            },function () {
                $(this).css("background","white");
            });
            $("#suggest_ul1 li").click(function () {
                $("#dataId1").val($(this).html());
                $("#suggest_ul1").hide(0);
            })
        },900);

    })
})

