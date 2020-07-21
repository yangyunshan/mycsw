$(function () {
    $(document).ready(function () {
        // Create a WorldWindow for the canvas.
        var wwd = new WorldWind.WorldWindow("canvasOne");
        wwd.deepPicking = true;

        wwd.addLayer(new WorldWind.BMNGOneImageLayer());
        // wwd.addLayer(new WorldWind.BMNGLandsatLayer());

        // wwd.addLayer(new WorldWind.CompassLayer());
        wwd.addLayer(new WorldWind.CoordinatesDisplayLayer(wwd));
        // wwd.addLayer(new WorldWind.ViewControlsLayer(wwd));


        var annotationAttributes = new WorldWind.AnnotationAttributes(null);
        annotationAttributes.backgroundColor = WorldWind.Color.RED;
        annotationAttributes.cornerRadius = 5;
        annotationAttributes.drawLeader = true;
        annotationAttributes.opacity = 1;
        annotationAttributes.scale = 1;

        var position = new WorldWind.Position(113.0,-123.0,0.0)

        var annotation = new WorldWind.Annotation(position,annotationAttributes);
        annotation.text = "TEST ANNOTATION:\n" +
            "hfioafhwi:\n" +
            "bdiuwadi:\n"+"<h2>hdiuwahdi</h2>";
        annotation.displayName = "Metadata:";
        var annotationLayer = new WorldWind.RenderableLayer("annotation");
        annotationLayer.addRenderable(annotation);
        wwd.addLayer(annotationLayer);

        var polygonLayer = new WorldWind.RenderableLayer();
        wwd.addLayer(polygonLayer);

        var polygonAttributes = new WorldWind.ShapeAttributes(null);
        polygonAttributes.interiorColor = new WorldWind.Color(0, 1, 1, 0.75);
        polygonAttributes.outlineColor = WorldWind.Color.BLUE;
        polygonAttributes.drawOutline = true;
        polygonAttributes.applyLighting = true;

        var boundaries = [];
        boundaries.push(new WorldWind.Position(20.0, -75.0, 200000.0));
        boundaries.push(new WorldWind.Position(25.0, -85.0, 200000.0));
        boundaries.push(new WorldWind.Position(20.0, -95.0, 200000.0));

        var polygon = new WorldWind.Polygon(boundaries, polygonAttributes);
        polygon.extrude = true;
        polygonLayer.addRenderable(polygon);

        var highlightController = new WorldWind.HighlightController(wwd);
    })
})
