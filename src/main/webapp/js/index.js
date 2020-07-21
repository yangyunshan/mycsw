function load() {
    $(document).ready(function () {
        $.ajax({
            url:"header",
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
    $(document).ready(function () {
        $.ajax({
            url: "getNewestInfo",
            type: "get",
            dataType: "json",
            success:function (data) {
                var count = data.length;
                if (count>=8) {
                    $("#moreButton").css("display","block");
                }
                for (var i=0;i<count;i++) {
                    $("#id"+i+"").html(data[i].id);
                    $("#name"+i+"").html(data[i].name);
                    $("#time"+i+"").html(data[i].time);
                    $("#type"+i+"").html(data[i].type);
                }
                $("#newestInfo").css("display","block");
            }
        })
    })
})

function jump2More() {
    window.open("html/moreNewestInfo.html");
}

$(function () {
    $(document).ready(function () {
        $.ajax({
            url:"getInfoCount",
            type:"get",
            dataType:"json",
            success:function (data) {
                $("#allData").html(data.total);
                $("#geologicalHazardDataCount").html(data.geologicalHazardDataCount);
                $("#waterResourcesDataCount").html(data.waterResourcesDataCount);
                $("#ecologicalDataCount").html(data.ecologicalDataCount);
                $("#climateDataCount").html(data.climateDataCount);
                $("#environmentDataCount").html(data.environmentDataCount);
                $("#stratigraphicPaleontologyDataCount").html(data.stratigraphicPaleontologyDataCount);
                $("#mineralResourceDataCount").html(data.mineralResourceDataCount);
                $("#energyDataCount").html(data.energyDataCount);
                $("#literaturePictureDataCount").html(data.literaturePictureDataCount);
                $("#keyBandDataCount").html(data.keyBandDataCount);
                $("#countInfo").css("display","block");
            }
        })

    })
})

function toDetail(obj) {
    var id = obj.innerHTML;
    if (id.toString().startsWith("ID:")) {
        id = id.substring(4);
    }
    window.open("html/properties-single.html?"+"id="+id);
}




