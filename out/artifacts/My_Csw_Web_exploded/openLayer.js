// $(function () {
//     $(document).ready(function () {
//         //组成Popup的元素
//         var container = document.getElementById('popup');
//         var content = document.getElementById('pop-content');
//         var closer = document.getElementById('popup-closer');
//
//         /**
//          * Create an overlay to anchor the popup to the map.
//          */
//         var overlay = new ol.Overlay({
//             element: container,
//             autoPan: true,
//             autoPanAnimation: {
//                 duration: 250
//             }
//         });
//
//         /**
//          * Add a click handler to hide the popup.
//          * @return {boolean} Don't follow the href.
//          */
//         closer.onclick = function() {
//             overlay.setPosition(undefined);
//             closer.blur();
//             return false;
//         };
//
//
//         //加载鼠标位置信息
//         var mousePositionControl = new ol.control.MousePosition({
//             className:"custom-mouse-position",
//             coordinateFormat:ol.coordinate.createStringXY(4),
//             projection: 'EPSG:4326',
//             target:document.getElementById('mouse-position'),
//             undefinedHTML:'&nbsp;'
//         });
//
//         //加载点要素图层
//         var iconFeature = createPointFeature(110,39,'sss','test','2019-11-05','sensor');
//
//
//         var iconFeature2 = new ol.Feature(new ol.geom.Point(ol.proj.transform([110, 31], 'EPSG:4326',
//             'EPSG:3857')));
//         iconFeature2.set('style',createStyle('images/world.png',undefined));
//         iconFeature2.setId(2);
//
//         //加载地图图层（矢量图层和矢量注记图层）
//         var map = new ol.Map({
//             target: 'map',
//             layers: [
//                 crtLayerXYZ("vec_w","EPSG:3857",1),
//                 crtLayerXYZ("cva_w","EPSG:3857",1),
//                 new ol.layer.Vector({
//                     style: function (feature) {
//                         return feature.get('style')
//                     },
//                     source: new ol.source.Vector({features: [iconFeature,iconFeature2]})
//                 }),
//             ],
//             overlays:[overlay],
//             view: new ol.View({
//                 center: ol.proj.transform([110,39],"EPSG:4326","EPSG:3857"),
//                 zoom: 4
//             }),
//
//             //加载控件到地图容器中
//             controls: ol.control.defaults({//地图中默认控件
// //                attributionOptions: /** @type {ol.control.Attribution} */({
// //                    collapsible: true //地图数据源信息控件是否可展开,默认为true
// //                })
//             }).extend([mousePositionControl])//加载鼠标位置控件
//         });
//
//         //获得已选择元素并改变已选颜色的样式
//         var selectStyle = {};
//         var select = new ol.interaction.Select({
//             style: function(feature) {
//                 var image = feature.get('style').getImage().getImage();
//                 if (!selectStyle[image.src]) {
//                     var canvas = document.createElement('canvas');
//                     var context = canvas.getContext('2d');
//                     canvas.width = image.width;
//                     canvas.height = image.height;
//                     context.drawImage(image, 0, 0, image.width, image.height);
//                     var imageData = context.getImageData(0, 0, canvas.width, canvas.height);
//                     var data = imageData.data;
//                     for (var i = 0, ii = data.length; i < ii; i = i + (i % 4 == 2 ? 2 : 1)) {
//                         data[i] = 255 - data[i];
//                     }
//                     context.putImageData(imageData, 0, 0);
//                     selectStyle[image.src] = createStyle(undefined, canvas);
//                 }
//                 return selectStyle[image.src];
//             }
//         });
//         map.addInteraction(select);
//         select.on('select',function (e) {
//             var r1 = e.target.getFeatures();
//             var s = r1.item(0);
//             var aa = s.getProperties();
//             var geometry = s.getGeometry();
//             var ss = geometry.getCoordinates();
//             var detailLabel = '<a href="javascript:void(0)" onclick=toPropertiesSingle("'+ss+'")>Detail>></a>';
//             $('#popup-content').html(detailLabel);
//             overlay.setPosition(ss);
//         })
//     })
// })

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

///////////////

//加载地图图层（矢量图层和矢量注记图层）
var map = new ol.Map({
    target: 'map',
    layers: [
        crtLayerXYZ("vec_w","EPSG:3857",1),
        crtLayerXYZ("cva_w","EPSG:3857",1),
        // new ol.layer.Vector({
        //     style: function (feature) {
        //         return feature.get('style')
        //     },
        //     source: new ol.source.Vector({features: [iconFeature,iconFeature2]})
        // }),
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
    var r1 = e.target.getFeatures();
    var s = r1.item(0);
    var aa = s.getProperties();
    var geometry = s.getGeometry();
    var ss = geometry.getCoordinates();
    var detailLabel = '<a href="javascript:void(0)" onclick=toPropertiesSingle("'+ss+'")>Detail>></a>';
    $('#popup-content').html(detailLabel);
    overlay.setPosition(ss);
})

function removePointLayer() {
    var layers = map.getLayers();

    console.log("LayersCount: "+layers.getLength());

    map.removeLayer(layers.item(2));
}

function createLayer2Map() {
    //加载点要素图层
    var iconFeature = createPointFeature(110,39,'sss','test','2019-11-05','sensor');


    var iconFeature2 = new ol.Feature(new ol.geom.Point(ol.proj.transform([110, 31], 'EPSG:4326',
        'EPSG:3857')));
    iconFeature2.set('style',createStyle('images/world.png',undefined));
    iconFeature2.setId(2);

    var pointLayer = new ol.layer.Vector({
        style: function (feature) {
            return feature.get('style')
        },
        source: new ol.source.Vector({features: [iconFeature,iconFeature2]})
    });
    map.addLayer(pointLayer);
}

//创建点要素
function createPointFeature(longitude,latitude,featureId,name,time,type) {
    var point = new ol.geom.Point((new ol.proj.transform([longitude,latitude],'EPSG:4326','EPSG:3857')));
    var iconFeature = new ol.Feature(point);
    iconFeature.set('style',createStyle('images/world.png',undefined));
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