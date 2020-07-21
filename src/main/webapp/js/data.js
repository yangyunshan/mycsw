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

var recordNum;
function loadData(fileType,url) {
    $(document).ready(function () {
        $.ajax({
            url:"queryAllRecordsNum",
            type:"get",
            dataType:"json",
            data:{
                fileType:fileType
            },
            success:function (data) {
                recordNum = data.number;
                $.ajax({
                    url: url,
                    type: "get",
                    dataType: "json",
                    success:function (data) {
                        if (data!=null) {
                            var pageCount = Math.ceil(recordNum/12);
                            $('#box').paging({
                                initPageNo: 1, // 初始页码
                                totalPages: pageCount, //总页数
                                totalCount: '合计' + recordNum + '条数据', // 条目总数
                                slideSpeed: 600, // 缓动速度。单位毫秒
                                jump: true, //是否支持跳转
                                callback: function(index) { // 回调函数
                                    $.ajax({
                                        url:url,
                                        type:"get",
                                        dataType:"json",
                                        data:{
                                            currentPage:index,
                                        },
                                        success:function (data) {
                                            if (data!=null) {
                                                var count = data.length;
                                                console.log("COunt: "+count);
                                                for (var j=11;j>count-1;j--) {
                                                    var idTemp = "result"+j;
                                                    document.getElementById(idTemp).style.display = "none";
                                                }
                                                for (var i=0;i<count;i++) {
                                                    var id ="result"+i;
                                                    $("#id"+i+"").html(data[i].id);
                                                    $("#name"+i+"").html(data[i].name);
                                                    $("#type"+i+"").html("Type:"+data[i].type);
                                                    $("#time"+i+"").html("Time:"+data[i].time);
                                                    $("#"+id+"").css("display","inline");
                                                }
                                            }
                                        }
                                    })
                                }

                            })
                        }
                    }
                })
            }
        })

    })
}

function toDetail(obj) {
    var id = obj.innerHTML;
    if (id.toString().startsWith("ID:")) {
        id = id.substring(4);
    }
    window.open("properties-single.html?"+"id="+id);
}
