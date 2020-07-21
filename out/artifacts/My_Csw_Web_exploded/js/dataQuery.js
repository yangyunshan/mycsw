//组成Popup的元素
var container = document.getElementById('popup');
var content = document.getElementById('pop-content');
var closer = document.getElementById('popup-closer');

/**
 * Create an overlay to anchor the popup to the map.
 */
var overlay = new ol.Overlay({
    element: container,
    autoPan: true,
    autoPanAnimation: {
        duration: 250
    }
});

/**
 * Add a click handler to hide the popup.
 * @return {boolean} Don't follow the href.
 */
closer.onclick = function() {
    overlay.setPosition(undefined);
    closer.blur();
    return false;
};

//加载鼠标位置信息
var mousePositionControl = new ol.control.MousePosition({
    className:"custom-mouse-position",
    coordinateFormat:ol.coordinate.createStringXY(4),
    projection: 'EPSG:4326',
    target:document.getElementById('mouse-position'),
    undefinedHTML:'&nbsp;'
});

//加载地图图层（矢量图层和矢量注记图层）
var map = new ol.Map({
    target: 'map',
    layers: [
        crtLayerXYZ("vec_w","EPSG:3857",1),
        crtLayerXYZ("cva_w","EPSG:3857",1),
    ],
    overlays:[overlay],
    view: new ol.View({
        center: ol.proj.transform([110,39],"EPSG:4326","EPSG:3857"),
        zoom: 4
    }),

    //加载控件到地图容器中
    controls: ol.control.defaults({//地图中默认控件
//                attributionOptions: /** @type {ol.control.Attribution} */({
//                    collapsible: true //地图数据源信息控件是否可展开,默认为true
//                })
    }).extend([mousePositionControl])//加载鼠标位置控件
});

//获得已选择元素并改变已选颜色的样式
var selectStyle = {};
var select = new ol.interaction.Select({
    style: function(feature) {
        var image = feature.get('style').getImage().getImage();
        if (!selectStyle[image.src]) {
            var canvas = document.createElement('canvas');
            var context = canvas.getContext('2d');
            canvas.width = image.width;
            canvas.height = image.height;
            context.drawImage(image, 0, 0, image.width, image.height);
            var imageData = context.getImageData(0, 0, canvas.width, canvas.height);
            var data = imageData.data;
            for (var i = 0, ii = data.length; i < ii; i = i + (i % 4 == 2 ? 2 : 1)) {
                data[i] = 255 - data[i];
            }
            context.putImageData(imageData, 0, 0);
            selectStyle[image.src] = createStyle(undefined, canvas);
        }
        return selectStyle[image.src];
    }
});
map.addInteraction(select);
select.on('select',function (e) {
    var features = e.target.getFeatures();
    var feature = features.item(0);
    var properties = feature.getProperties();
    var geometry = feature.getGeometry();
    var coordinates = geometry.getCoordinates();
    var id = feature.getId();
    var info = '<Strong>Name: '+properties.name +'</Strong><br><span>'+'Time: '+properties.time+'<br>Type: '+properties.type+'</span><br>';
    var detailLabel = '<a href="javascript:void(0)" onclick=toPropertiesSingle("'+id+'")>Detail>></a>';
    $('#popup-content').html(info + detailLabel);
    $('#popup').css('display','block');
    overlay.setPosition(coordinates);
})

/*****************************************************/

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

function toDetail(obj) {
    var id = obj.innerHTML;
    console.log("id: "+id);
    if (id.toString().startsWith("ID:")) {
        id = id.substring(4);
    }
    window.open("properties-single.html?"+"id="+id);
}

function toPropertiesSingle(id) {
    window.open("properties-single.html?"+"id="+id.trim());
}

function getRecordById() {
    $(document).ready(function () {
        $.ajax({
            type:"get",
            url:"queryById",
            data:$("#getRecordById").serialize(),
            dataType:"json",
            success:function (data) {
                if (data!==null) {
                    showSpatialData(data);
                } else {
                    alert("没有符合查询要求的数据！");
                }
            },
        });
    })
}

function getRecordByIdNon() {
    $(document).ready(function () {
        $.ajax({
            type:"get",
            url:"queryByIdNon",
            data:$("#getRecordByIdNon").serialize(),
            dataType:"json",
            success:function (data) {
                if (data!==null) {
                    var count = data.length;
                    console.log("Count: "+count);
                    for (var j=9;j>count-1;j--) {
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
                        document.getElementById('generalResult').style.display = "block";
                    }
                } else {
                    alert("没有符合查询要求的数据！");
                }
            },
        });
    })
}

function getRecordsByName() {
    $(document).ready(function () {
        $.ajax({
            type: "GET",
            url: "queryByName",
            data: $("#getRecordsByName").serialize(),
            dataType: "json",
            success(data) {
                showSpatialData(data);
            }
        })
    })
}

function getRecordsByNameNon() {
    $(document).ready(function () {
        $.ajax({
            type: "GET",
            url: "queryByNameNon",
            data: $("#getRecordsByNameNon").serialize(),
            dataType: "json",
            success(data) {
                showByPage(data);
            }
        })
    })
}

function getRecordsByKeywords() {
    $(document).ready(function () {
        $.ajax({
            type: "GET",
            url: "queryByKeywords",
            data: $("#getRecordsByKeywords").serialize(),
            dataType: "json",
            success(data) {
                showSpatialData(data);
            }
        })
    })
}

function getRecordsByKeywordsNon() {
    $(document).ready(function () {
        $.ajax({
            type: "GET",
            url: "queryByKeywordsNon",
            data: $("#getRecordsByKeywordsNon").serialize(),
            dataType: "json",
            success(data) {
                showByPage(data);
            }
        })
    })
}

function getRecordsByOwner() {
    $(document).ready(function () {
        $.ajax({
            type: "GET",
            url: "queryByOwner",
            data: $("#getRecordsByOwner").serialize(),
            dataType: "json",
            success(data) {
                showSpatialData(data);
            }
        })
    })
}

function getRecordsByOwnerNon() {
    $(document).ready(function () {
        $.ajax({
            type: "GET",
            url: "queryByOwnerNon",
            data: $("#getRecordsByOwnerNon").serialize(),
            dataType: "json",
            success(data) {
                showByPage(data);
            }
        })
    })
}

function getRecordsByBoundingBox() {
    $(document).ready(function () {
        $.ajax({
            type: "GET",
            url: "queryByBoundingBox",
            data: $("#getRecordsByBoundingBox").serialize(),
            dataType: "json",
            success(data) {
                showSpatialData(data);
            }
        })
    })
}

function getRecordsByOrganization() {
    $(document).ready(function () {
        $.ajax({
            type: "GET",
            url: "queryByOrganization",
            data: $("#getRecordsByOrganization").serialize(),
            dataType: "json",
            success(data) {
                showSpatialData(data);
            }
        })
    })
}

function getRecordsByOrganizationNon() {
    $(document).ready(function () {
        $.ajax({
            type: "GET",
            url: "queryByOrganizationNon",
            data: $("#getRecordsByOrganizationNon").serialize(),
            dataType: "json",
            success(data) {
                showByPage(data);
            }
        })
    })
}

function getRecordsByRegistryDate() {
    $(document).ready(function () {
        $.ajax({
            type: "GET",
            url: "queryByRegistryDate",
            dataType: "json",
            data:{
                time:$("#registryTime").val()
            },
            success(data) {
                showSpatialData(data);
            }
        })
    })
}

function getRecordsByRegistryDateNon() {
    $(document).ready(function () {
        $.ajax({
            type: "GET",
            url: "queryByRegistryDateNon",
            dataType: "json",
            data:{
                time:$("#registryTimeNon").val()
            },
            success(data) {
                showByPage(data);
            }
        })
    })
}

function getRecordsByStartAndEndDate() {
    $(document).ready(function () {
        $.ajax({
            type: "GET",
            url: "queryByStartAndEndDate",
            dataType: "json",
            data:{
                startDate:$("#startDate").val(),
                endDate:$("#endDate").val()
            },
            success(data) {
                showSpatialData(data);
            }
        })
    })
}

function showByPage(data) {
    if (data!=null) {
        var ids = data.allIds;
        var recordNum = ids.length;
        var pageCount = Math.ceil(recordNum/10);
        $('#box').paging({
            initPageNo: 1, // 初始页码
            totalPages: pageCount, //总页数
            totalCount: '合计' + recordNum + '条数据', // 条目总数
            slideSpeed: 600, // 缓动速度。单位毫秒
            jump: true, //是否支持跳转
            callback: function(index) { // 回调函数
                $.ajax({
                    url:"queryResultByPage",
                    type:"get",
                    dataType:"json",
                    data:{
                        currentPage:index,
                        ids:ids.toString()
                    },
                    success:function (data) {
                        if (data!=null) {
                            var count = data.length;
                            console.log("Count: "+count);
                            for (var j=9;j>count-1;j--) {
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
                            document.getElementById('generalResult').style.display = "block";
                        }
                    }
                })
            }

        })
    }
}

$(function () {
    $('.dateselect').datetimepicker({
        format:'yyyy-mm-dd hh:ii:ss'
    });
})

function showSpatialData(data) {
    var layers = map.getLayers();
    var layerCount = layers.getLength();
    if (parseInt(layerCount)>2) {
        map.removeLayer(layers.item(2));
    }
    if (data!==null) {
        var count = data.length;
        console.log("Count: "+count);
        if (parseInt(count)<=0) {
            alert("没有符合查询要求的数据");
            return;
        }
        var features = [];
        for (var i=0;i<count;i++) {
            var longitude = (parseFloat(data[i].envelope.split(" ")[0])+parseFloat(data[i].envelope.split(" ")[2]))/2;
            var latitude = (parseFloat(data[i].envelope.split(" ")[1])+parseFloat(data[i].envelope.split(" ")[3]))/2;
            console.log("Longitude: "+longitude+", Latitude: "+latitude);
            var featureId = data[i].id;
            console.log("FeatureId: "+featureId);
            var name = data[i].name;
            var time = data[i].time;
            var type = data[i].type;

            var feature = createPointFeature(longitude,latitude,featureId,name,time,type);
            features.push(feature);
        }

        var resultLayer = new ol.layer.Vector({
            style: function (feature) {
                return feature.get('style')
            },
            source: new ol.source.Vector({features: features})
        });
        map.addLayer(resultLayer);

        // $('#spatialResult').css('display','block');
        // $('#generalResult').css('display','none');
    }
}


//创建点要素
function createPointFeature(longitude,latitude,featureId,name,time,type) {
    var point = new ol.geom.Point((new ol.proj.transform([longitude,latitude],'EPSG:4326','EPSG:3857')));
    var iconFeature = new ol.Feature(point);
    iconFeature.set('style',createStyle('../images/world.png',undefined));
    iconFeature.setId(featureId);
    iconFeature.setProperties({
        'name':name,
        'time':time,
        'type':type
    });
    return iconFeature;
}

//创建点图标样式
function createStyle(src, img) {
    return new ol.style.Style({
        image: new ol.style.Icon({
            anchor: [0.5, 0.96],
            crossOrigin: 'anonymous',
            src: src,
            img: img,
            imgSize: img ? [img.width, img.height] : undefined
        })
    });
}



//创建图层(WMTS方式)
function crtLayerWMTS(type, proj, opacity){
    var projection = ol.proj.get(proj);
    var projectionExtent = projection.getExtent();
    var size = ol.extent.getWidth(projectionExtent) / 256;
    var resolutions = new Array(19);
    var matrixIds = new Array(19);
    for (var z = 1; z < 19; ++z) {
        // generate resolutions and matrixIds arrays for this WMTS
        resolutions[z] = size / Math.pow(2, z);
        matrixIds[z] = z;
    }

    var layer = new ol.layer.Tile({
        opacity: opacity,
        source: new ol.source.WMTS({
            attributions: 'Tiles © <a href="http://www.tianditu.com/service/info.html?sid=5292&type=info">天地图</a>',
            url: 'http://t'+Math.round(Math.random()*7)+'.tianditu.com/'+type+'/wmts?tk=a4e0d4dc7dcc4fa01fb96c9949860252',
            layer: type.substr(0, 3),
            matrixSet: type.substring(4),
            format: 'tiles',
            projection: projection,
            tileGrid: new ol.tilegrid.WMTS({
                origin: ol.extent.getTopLeft(projectionExtent),
                resolutions: resolutions,
                matrixIds: matrixIds
            }),
            style: 'default',
            wrapX: true
        })
    });
    layer.id = type;
    return layer;
}


//创建图层(xyz方式)
function crtLayerXYZ(type, proj, opacity) {
    var layer = new ol.layer.Tile({
        source: new ol.source.XYZ({
            attributions: 'Tiles © <a href="http://www.tianditu.com/service/info.html?sid=5292&type=info">天地图</a>',
            url: 'http://t' + Math.round(Math.random() * 7) + '.tianditu.com/DataServer?T='+ type + '&x={x}&y={y}&l={z}&tk=a4e0d4dc7dcc4fa01fb96c9949860252',
            // projection: proj
        }),
        // opacity: opacity
    });
    layer.id = type;
    return layer;
}

function showSpatialResult() {
    $('#spatialResult').css('display','block');
    $('#generalResult').css('display','none');
}

function showGeneralResult() {
    $('#spatialResult').css('display','none');
    // $('#generalResult').css('display','block');
}


