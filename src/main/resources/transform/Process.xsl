<?xml version="1.0" encoding="UTF-8"?>

<xsl:transform xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:rim="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0" xmlns:gml="http://www.opengis.net/gml"
	xmlns:swe="http://www.opengis.net/swe/1.0.1" xmlns:wrs="http://www.opengis.net/cat/wrs/1.0"
	xmlns:sml="http://www.opengis.net/sensorML/1.0.1" version="2.0"
	xsi:schemaLocation="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0 http://docs.oasis-open.org/regrep/v3.0/schema/rim.xsd     http://www.opengis.net/cat/wrs/1.0 http://schemas.opengis.net/csw/2.0.2/profiles/ebrim/1.0/csw-ebrim.xsd">            <!-- imports -->
	<xsl:template match="text()|@*" />
	<xsl:strip-space elements="*" />
	<xsl:template match="sml:member">
		<xsl:if test="false()">
			<xsl:comment>
				<xsl:text>Processor:</xsl:text>
				<xsl:value-of select="system-property('xsl:vendor')" />
			</xsl:comment>
		</xsl:if>
		<xsl:element name="rim:RegistryPackage" namespace="{$nsrim}">
			<xsl:choose>
				<xsl:when test="false()">
					<xsl:attribute name="xsi:schemaLocation">
						<xsl:value-of
						select="concat($gmlSchemaLocation, ' ', $wrsSchemaLocation)" />
					</xsl:attribute>
				</xsl:when>
				<xsl:otherwise>
					<xsl:attribute name="xsi:schemaLocation">
						<xsl:value-of
						select="concat($sweSchemaLocation, ' ', $gmlSchemaLocation, ' ', $wrsSchemaLocation)" />
						</xsl:attribute>
				</xsl:otherwise>
			</xsl:choose>
			<xsl:attribute name="id">  
				 <xsl:value-of select="concat($idPrefix, $idSuffixRegPack)" />
			</xsl:attribute>
			<xsl:attribute name="xsi:schemaLocation">
				<xsl:value-of select="concat($wrsSchemaLocation, ' ', $gmlSchemaLocation)" />
				</xsl:attribute>

			<xsl:element name="rim:RegistryObjectList" namespace="{$nsrim}">
				<!-- 处理ProcessModel -->
				<xsl:apply-templates select="sml:ProcessModel" />
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<xsl:template match="sml:ProcessModel">
		<xsl:variable name="idvalue">
			<xsl:value-of
				select="concat('urn:ogc:object:feature:ProcessModel:',string(@gml:id) )"></xsl:value-of>
		</xsl:variable>
		<xsl:element name="wrs:ExtrinsicObject" namespace="{$nswrs}">
			<xsl:attribute name="id"> <xsl:value-of select="$idvalue"></xsl:value-of></xsl:attribute>
			<xsl:apply-templates select="sml:inputs" />
			<xsl:apply-templates select="sml:outputs" />
			<xsl:apply-templates select="sml:parameters" />
			<xsl:apply-templates select="sml:method" />
		</xsl:element>
	</xsl:template>
	<!--
		/********************************************************************
		Parse Method
		*********************************************************************/
	-->
	<xsl:template match="sml:method">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name">
			 	<xsl:value-of select="concat($slotPrefix,'Method')"></xsl:value-of>
			 </xsl:attribute>
			<xsl:attribute name="slotType">
				<xsl:value-of select="concat($oasisDataTypePrefix, 'String')"></xsl:value-of>
			</xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value">
					<xsl:value-of select="@xlink:href"></xsl:value-of>
				</xsl:element>
			</xsl:element>

		</xsl:element>
	</xsl:template>


	<!--
		/*****************************************************************
		Parse Parameters
		******************************************************************/
	-->
	<xsl:template match="sml:parameters">
		<xsl:apply-templates select="sml:ParameterList"></xsl:apply-templates>
	</xsl:template>
	<xsl:template match="sml:parameters/sml:ParameterList">

		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name">
				<xsl:value-of select="concat($slotPrefix, 'Parameters')"></xsl:value-of>
			</xsl:attribute>
			<xsl:attribute name="slotType">
				<xsl:value-of select="concat($oasisDataTypePrefix, 'String')"></xsl:value-of>
			</xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:apply-templates select="sml:parameter"></xsl:apply-templates>
			</xsl:element>
		</xsl:element>
	</xsl:template>

	<xsl:template match="sml:parameters/sml:ParameterList/sml:parameter">
		<xsl:element name="rim:Value" namespace="{$nsrim}">
			<xsl:value-of select="@name"></xsl:value-of>
			<!--
				<xsl:apply-templates select="swe:Quantity"></xsl:apply-templates>
			-->
		</xsl:element>
	</xsl:template>

	<xsl:template match="sml:ParameterList/sml:parameter/swe:Quantity">
		<!--
			<xsl:comment> <xsl:text> 存储的顺序为
			parameter:name|quantity:definition|uom:code </xsl:text>
			</xsl:comment>
		-->
		<xsl:variable name="quantityname">
			<xsl:value-of select="parent::sml:parameter/@name"></xsl:value-of>
		</xsl:variable>
		<xsl:variable name="definition">
			<xsl:value-of select="@definition"></xsl:value-of>
		</xsl:variable>
		<xsl:variable name="uom">
			<xsl:value-of select="swe:uom/@code"></xsl:value-of>
		</xsl:variable>
		<xsl:variable name="value">
			<xsl:value-of select="swe:value"></xsl:value-of>
		</xsl:variable>
		<xsl:element name="rim:Value">
			<xsl:value-of
				select="concat($quantityname,'|',$definition,'|',$uom ,'|',$value)"></xsl:value-of>
		</xsl:element>
	</xsl:template>
	<!--
		/***************************************************************************
		Parse Outputs
		****************************************************************************
	-->
	<xsl:template match="sml:ProcessModel/sml:outputs">
		<xsl:apply-templates select="sml:OutputList" />

	</xsl:template>

	<xsl:template match="sml:ProcessModel/sml:outputs/sml:OutputList">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name">
				<xsl:value-of select="concat($slotPrefix, 'Outputs')"></xsl:value-of>
			</xsl:attribute>
			<xsl:attribute name="slotType">
				<xsl:value-of select="concat($oasisDataTypePrefix, 'String')"></xsl:value-of>
			</xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:apply-templates select="sml:output/swe:Vector" />
				<xsl:apply-templates select="sml:output/swe:Quantity"/>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<xsl:template match="sml:output/swe:Quantity">
		<xsl:element name="rim:Value">
			<xsl:value-of select="parent::sml:output/@name"></xsl:value-of>
		</xsl:element>
	</xsl:template>
	<!-- 在这里可以添加其他的output的元素解析代码 -->
	<xsl:template match="sml:outputs/sml:OutputList/sml:output/swe:Vector">
		<xsl:variable name="inputvalue">
			<xsl:value-of select="parent::sml:output/@name"></xsl:value-of>
		</xsl:variable>
		<xsl:variable name="definitionvalue">
			<xsl:value-of select="@definition"></xsl:value-of>
		</xsl:variable>
		<xsl:variable name="referenceFrame">
			<xsl:value-of select="@referenceFrame"></xsl:value-of>
		</xsl:variable>
		<xsl:if test="contains($referenceFrame,'wgs84')">
			<xsl:variable name="quantityx.definition">
				<xsl:value-of select="swe:coordinate[@name='x']/swe:Quantity/@definition"></xsl:value-of>
			</xsl:variable>
			<xsl:variable name="quantityx.uom">
				<xsl:value-of select="swe:coordinate[@name='x']/swe:Quantity/swe:uom/@code"></xsl:value-of>
			</xsl:variable>
			<xsl:variable name="quantityx.value">
				<xsl:value-of select="swe:coordinate[@name='x']/swe:Quantity/swe:value" />
			</xsl:variable>

			<xsl:variable name="quantityy.definition">
				<xsl:value-of select="swe:coordinate[@name='y']/swe:Quantity/@definition"></xsl:value-of>
			</xsl:variable>
			<xsl:variable name="quantityy.uom">
				<xsl:value-of select="swe:coordinate[@name='y']/swe:Quantity/swe:uom/@code"></xsl:value-of>
			</xsl:variable>
			<xsl:variable name="quantityy.value">
				<xsl:value-of select="swe:coordinate[@name='y']/swe:Quantity/swe:value"></xsl:value-of>
			</xsl:variable>

			<xsl:variable name="quantityz.definition">
				<xsl:value-of select="swe:coordinate[@name='z']/swe:Quantity/@definition"></xsl:value-of>
			</xsl:variable>
			<xsl:variable name="quantityz.uom">
				<xsl:value-of select="swe:coordinate[@name='z']/swe:Quantity/swe:uom/@code"></xsl:value-of>
			</xsl:variable>
			<xsl:variable name="quantityz.value">
				<xsl:value-of select="swe:coordinate[@name='z']/swe:Quantity/swe:value"></xsl:value-of>
			</xsl:variable>

			<xsl:element name="rim:Value" namespace="{$nsrim}">
				<xsl:value-of
					select="concat($inputvalue,' |',$definitionvalue,' |',$referenceFrame,' |',$quantityx.definition,' |',$quantityx.uom,' |',$quantityx.value ,' |',$quantityy.definition,' |',$quantityy.uom,' |',$quantityy.value,' |',$quantityz.definition,' |',$quantityz.uom,' |',$quantityz.value,' ')"></xsl:value-of>
			</xsl:element>
		</xsl:if>
		<xsl:if test="contains($referenceFrame,'epsg4329')">
			<xsl:variable name="latitude.definition">
				<xsl:value-of
					select="swe:coordinate[@name='latitude']/swe:Quantity/@definition"></xsl:value-of>
			</xsl:variable>
			<xsl:variable name="latitude.uom">
				<xsl:value-of
					select="swe:coordinate[@name='latitude']/swe:Quantity/swe:uom/@code"></xsl:value-of>
			</xsl:variable>
			<xsl:variable name="latitude.value">
				<xsl:value-of
					select="swe:coordinate[@name='latitude']/swe:Quantity/swe:value"></xsl:value-of>
			</xsl:variable>

			<xsl:variable name="longitude.definition">
				<xsl:value-of
					select="swe:coordinate[@name='longitude']/swe:Quantity/@definition"></xsl:value-of>
			</xsl:variable>
			<xsl:variable name="longitude.uom">
				<xsl:value-of
					select="swe:coordinate[@name='longitude']/swe:Quantity/swe:uom/@code"></xsl:value-of>
			</xsl:variable>
			<xsl:variable name="longitude.value">
				<xsl:value-of
					select="swe:coordinate[@name='longitude']/swe:Quantity/swe:value"></xsl:value-of>
			</xsl:variable>

			<xsl:variable name="altitude.definition">
				<xsl:value-of
					select="swe:coordinate[@name='altitude']/swe:Quantity/@definition"></xsl:value-of>
			</xsl:variable>
			<xsl:variable name="altitude.uom">
				<xsl:value-of
					select="swe:coordinate[@name='altitude']/swe:Quantity/swe:uom/@code"></xsl:value-of>
			</xsl:variable>
			<xsl:variable name="altitude.value">
				<xsl:value-of
					select="swe:coordinate[@name='altitude']/swe:Quantity/swe:value"></xsl:value-of>
			</xsl:variable>

			<xsl:element name="rim:Value" namespace="{$nsrim}">
				<xsl:value-of
					select="concat($inputvalue,' |',$definitionvalue,' |',$referenceFrame,' |',$latitude.definition,' |',$latitude.uom,' |',$latitude.value ,' |',$longitude.definition,' |',$longitude.uom,' |',$longitude.value,' |',$altitude.definition,' |',$altitude.uom,' |',$altitude.value,' ')"></xsl:value-of>
			</xsl:element>
		</xsl:if>
	</xsl:template>
	<!--

		/**************************************************************************************
		Parse Inputs
		**************************************************************************/
	-->
	<!-- INPUTS -->
	<xsl:template match="sml:ProcessModel/sml:inputs">
		<xsl:apply-templates select="sml:InputList" />
	</xsl:template>
	<!-- INPUTS -->
	<xsl:template match="sml:ProcessModel/sml:inputs/sml:InputList">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix, 'Inputs')" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:apply-templates select="sml:input/swe:Time" />
				<xsl:apply-templates select="sml:input/swe:DataRecord" />
				<xsl:apply-templates select="sml:input/swe:Vector" />
				<xsl:apply-templates select="sml:input/swe:Quantity" />
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<xsl:template match="sml:input/swe:Quantity">
		<xsl:element name="rim:Value" namespace="{$nsrim}">
			<xsl:value-of select="parent::sml:input/@name"></xsl:value-of>
		</xsl:element>
	</xsl:template>
	<!-- 在这里可以添加其他的解析inputs代码 -->
	<!-- input time  -->
	<xsl:template
		match="sml:ProcessModel/sml:inputs/sml:InputList/sml:input/swe:Time">
		<!--
			<xsl:comment> <xsl:text>存储的顺序为
			input:name|time:definition|time:referenceTime|uom:code</xsl:text>
			</xsl:comment>
		-->
		<xsl:element name="rim:Value" namespace="{$nsrim}">
			<xsl:variable name="inputvalue">
				<xsl:value-of select="parent::sml:input/@name"></xsl:value-of>
			</xsl:variable>
			<xsl:variable name="definitionvalue">
				<xsl:value-of select="@definition"></xsl:value-of>
			</xsl:variable>
			<xsl:variable name="referenceTime">
				<xsl:value-of select="@referenceTime ">
				</xsl:value-of>
			</xsl:variable>
			<xsl:variable name="uomvalue">
				<xsl:value-of select="swe:uom/@code"></xsl:value-of>
			</xsl:variable>
			<xsl:value-of
				select="concat($inputvalue, '|',$definitionvalue,'|',$referenceTime,'|',$uomvalue)"></xsl:value-of>
		</xsl:element>
	</xsl:template>
	<!-- input vector -->
	<xsl:template match="sml:inputs/sml:InputList/sml:input/swe:Vector">
		<!--
			<xsl:comment> <xsl:text> 存储的顺序为
			input:name|vector:definition|vector:referenceFrame|coordinate:Quantity:definition|coordinate:Quantity:uom:code
			</xsl:text> </xsl:comment>
		-->
		<xsl:variable name="inputvalue">
			<xsl:value-of select="parent::sml:input/@name"></xsl:value-of>
		</xsl:variable>
		<xsl:variable name="definitionvalue">
			<xsl:value-of select="@definition"></xsl:value-of>
		</xsl:variable>
		<xsl:variable name="referenceFrame">
			<xsl:value-of select="@referenceFrame"></xsl:value-of>
		</xsl:variable>
		<xsl:if test="contains($referenceFrame,'wgs84')">
			<xsl:variable name="quantityx.definition">
				<xsl:value-of select="swe:coordinate[@name='x']/swe:Quantity/@definition"></xsl:value-of>
			</xsl:variable>
			<xsl:variable name="quantityx.uom">
				<xsl:value-of select="swe:coordinate[@name='x']/swe:Quantity/swe:uom/@code"></xsl:value-of>
			</xsl:variable>
			<xsl:variable name="quantityx.value">
				<xsl:value-of select="swe:coordinate[@name='x']/swe:Quantity/swe:value" />
			</xsl:variable>
			<xsl:variable name="quantityy.definition">
				<xsl:value-of select="swe:coordinate[@name='y']/swe:Quantity/@definition"></xsl:value-of>
			</xsl:variable>
			<xsl:variable name="quantityy.uom">
				<xsl:value-of select="swe:coordinate[@name='y']/swe:Quantity/swe:uom/@code"></xsl:value-of>
			</xsl:variable>
			<xsl:variable name="quantityy.value">
				<xsl:value-of select="swe:coordinate[@name='y']/swe:Quantity/swe:value"></xsl:value-of>
			</xsl:variable>
			<xsl:variable name="quantityz.definition">
				<xsl:value-of select="swe:coordinate[@name='z']/swe:Quantity/@definition"></xsl:value-of>
			</xsl:variable>
			<xsl:variable name="quantityz.uom">
				<xsl:value-of select="swe:coordinate[@name='z']/swe:Quantity/swe:uom/@code"></xsl:value-of>
			</xsl:variable>
			<xsl:variable name="quantityz.value">
				<xsl:value-of select="swe:coordinate[@name='z']/swe:Quantity/swe:value"></xsl:value-of>
			</xsl:variable>
			<xsl:element name="rim:Value" namespace="{$nsrim}">
				<xsl:value-of
					select="concat($inputvalue,' |',$definitionvalue,' |',$referenceFrame,' |',$quantityx.definition,' |',$quantityx.uom,' |',$quantityx.value ,' |',$quantityy.definition,' |',$quantityy.uom,' |',$quantityy.value,' |',$quantityz.definition,' |',$quantityz.uom,' |',$quantityz.value,' ')"></xsl:value-of>
			</xsl:element>
		</xsl:if>
		<xsl:if test="contains($referenceFrame,'epsg4329')">
			<xsl:variable name="latitude.definition">
				<xsl:value-of
					select="swe:coordinate[@name='latitude']/swe:Quantity/@definition"></xsl:value-of>
			</xsl:variable>
			<xsl:variable name="latitude.uom">
				<xsl:value-of
					select="swe:coordinate[@name='latitude']/swe:Quantity/swe:uom/@code"></xsl:value-of>
			</xsl:variable>
			<xsl:variable name="latitude.value">
				<xsl:value-of
					select="swe:coordinate[@name='latitude']/swe:Quantity/swe:value"></xsl:value-of>
			</xsl:variable>

			<xsl:variable name="longitude.definition">
				<xsl:value-of
					select="swe:coordinate[@name='longitude']/swe:Quantity/@definition"></xsl:value-of>
			</xsl:variable>
			<xsl:variable name="longitude.uom">
				<xsl:value-of
					select="swe:coordinate[@name='longitude']/swe:Quantity/swe:uom/@code"></xsl:value-of>
			</xsl:variable>
			<xsl:variable name="longitude.value">
				<xsl:value-of
					select="swe:coordinate[@name='longitude']/swe:Quantity/swe:value"></xsl:value-of>
			</xsl:variable>

			<xsl:variable name="altitude.definition">
				<xsl:value-of
					select="swe:coordinate[@name='altitude']/swe:Quantity/@definition"></xsl:value-of>
			</xsl:variable>
			<xsl:variable name="altitude.uom">
				<xsl:value-of
					select="swe:coordinate[@name='altitude']/swe:Quantity/swe:uom/@code"></xsl:value-of>
			</xsl:variable>
			<xsl:variable name="altitude.value">
				<xsl:value-of
					select="swe:coordinate[@name='altitude']/swe:Quantity/swe:value"></xsl:value-of>
			</xsl:variable>

			<xsl:element name="rim:Value" namespace="{$nsrim}">
				<xsl:value-of
					select="concat($inputvalue,' |',$definitionvalue,' |',$referenceFrame,' |',$latitude.definition,' |',$latitude.uom,' |',$latitude.value ,' |',$longitude.definition,' |',$longitude.uom,' |',$longitude.value,' |',$altitude.definition,' |',$altitude.uom,' |',$altitude.value,' ')"></xsl:value-of>
			</xsl:element>
		</xsl:if>
	</xsl:template>

	<xsl:variable name="slotPrefix"
		select="'urn:ogc:def:slot:OGC-CSW-ebRIM-Process::'" />
	<xsl:output method="xml" indent="no" encoding="UTF-8"
		version="1.0" />        <!-- DEBUGGING -->
	<xsl:param name="debugOn" select="false()" />
	<xsl:variable name="nsrim"
		select="'urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0'" />
	<xsl:variable name="nswrs" select="'http://www.opengis.net/cat/wrs/1.0'" />
	<xsl:variable name="nsgml" select="'http://www.opengis.net/gml'" />
	<xsl:variable name="gmlSchemaLocation"
		select="'http://www.opengis.net/gml http://schemas.opengis.net/gml/3.1.1/base/gml.xsd'" />
	<xsl:variable name="nsswe" select="'http://www.opengis.net/swe/1.0.1'" />
	<xsl:variable name="sweSchemaLocation"
		select="'http://www.opengis.net/swe/1.0.1 http://schemas.opengis.net/sweCommon/1.0.1/swe.xsd'" />
	<xsl:variable name="wrsSchemaLocation"
		select="'http://www.opengis.net/cat/wrs/1.0 http://schemas.opengis.net/csw/2.0.2/profiles/ebrim/1.0/csw-ebrim.xsd'" />
	<!-- true() or false() -->
	<xsl:variable name="noSWE" select="true()" />
	<!-- constant for testing agains valid swe:referenceFrame values -->
	<xsl:variable name="epsgUrnPrefix" select="'urn:ogc:def:crs:EPSG:'" />
	<!-- TYPES -->
	<xsl:variable name="fixedObjectTypeSystem"
		select="'urn:ogc:def:objectType:OGC-CSW-ebRIM-Sensor::Physical:System'" />
	<xsl:variable name="fixedObjectTypeComponent"
		select="'urn:ogc:def:objectType:OGC-CSW-ebRIM-Sensor::Physical:Component'" />
	<!--
		下面的两个变量，fixedObjectTypeProcessModel和fixedObjectTypeProcessChain。这两个变量均是自己添加的，暂时放在这里
		yxl
	-->
	<xsl:variable name="fixedObjectTypeProcessModel"
		select="'urn:ogc:def:objectType:OGC-CSW-ebRIM-Process::NonPhysical:ProcessModel'" />
	<xsl:variable name="fixedObjectTypeProcessChain"
		select="'urn:ogc:def:objectType:OGC-CSW-ebRIM-Process::NonProcessChain'"></xsl:variable>
	<xsl:variable name="idPrefix">
		<xsl:choose>
			<xsl:when
				test="starts-with($systemIdentifier, 'urn:ogc:def:identifier:OGC::')">
				<xsl:value-of select="concat($systemIdentifier,':')" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of
					select="concat('urn:ogc:def:identifier:OGC::', $systemIdentifier,':')" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	<xsl:variable name="idSuffixRegPack" select="'package'" />
	<xsl:variable name="idMiddlefixOrganization" select="'org:'" />
	<xsl:variable name="idMiddlefixPerson" select="'person:'" />
	<xsl:variable name="idMiddlefixAssociation" select="'association:'" />
	<xsl:variable name="idMiddlefixClassification" select="'classification:'" />
	<xsl:variable name="idMiddlefixService" select="'service:'" />
	<xsl:variable name="idMiddlefixGml" select="'gmlId:'" />
	<xsl:variable name="oasisDataTypePrefix"
		select="'urn:oasis:names:tc:ebxml-regrep:DataType:'" />
	<xsl:variable name="iso19107DataTypePrefix"
		select="'urn:ogc:def:dataType:ISO-19107:2003:'" />
	<xsl:variable name="classificationNodeIdPrefix"
		select="'urn:ogc:def:objectType:OGC-CSW-ebRIM-Process::'" />
	<!-- GLOBAL ATTRIBUTE VALUES -->
	<xsl:variable name="fixedSystemAndComponentClassificationParent"
		select="'urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ExtrinsicObject'" />
	<xsl:variable name="defaultLocalizedStringLang" select="'en-US'" />
	<!-- MESSAGES -->
	<xsl:variable name="message.gmlError"
		select="'Element gml:description not found! Maybe I cannot match the gml namespace? Transformation requires http://www.opengis.net/gml'" />

	<xsl:variable name="systemIdentifier"
		select="/sml:SensorML/sml:member/sml:ProcessModel/@gml:id" />


</xsl:transform>
	