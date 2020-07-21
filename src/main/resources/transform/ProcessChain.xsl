<?xml version="1.0" encoding="UTF-8"?>
<xsl:transform version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xlink="http://www.w3.org/1999/xlink"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:rim="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0"
	xmlns:gml="http://www.opengis.net/gml" xmlns:swe="http://www.opengis.net/swe/1.0"
	xmlns:wrs="http://www.opengis.net/cat/wrs/1.0" xmlns:sml="http://www.opengis.net/sensorML/1.0"
	xsi:schemaLocation="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0 http://docs.oasis-open.org/regrep/v3.0/schema/rim.xsd     http://www.opengis.net/cat/wrs/1.0 http://schemas.opengis.net/csw/2.0.2/profiles/ebrim/1.0/csw-ebrim.xsd">
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
				<xsl:element name="wrs:ExtrinsicObject" namespace="{$nswrs}">
					<xsl:attribute name="mimeType">
						<xsl:value-of select="'application/xml'"></xsl:value-of>
						</xsl:attribute>
					<xsl:attribute name="objectType">
						<xsl:value-of select="$fixedObjectTypeProcessChain"></xsl:value-of>
					</xsl:attribute>
					<xsl:attribute name="id">
						<xsl:value-of
						select="concat($fixedprocessChainPrefix, sml:ProcessChain/@gml:id)"></xsl:value-of>
					</xsl:attribute>
					<xsl:attribute name="lid">
						<xsl:value-of
						select="concat($fixedprocessChainPrefix, sml:ProcessChain/@gml:id)"></xsl:value-of>
					</xsl:attribute>

					<xsl:comment>
						#####################inputs########################
					</xsl:comment>
					<xsl:apply-templates select="sml:ProcessChain/sml:inputs" />

					<xsl:comment>
						###################outputs#######################
					</xsl:comment>
					<xsl:apply-templates select="sml:ProcessChain/sml:outputs" />

					<xsl:comment>
						####################parameters#######################
					</xsl:comment>
					<xsl:apply-templates select="sml:ProcessChain/sml:parameters" />

					<xsl:comment>
						####################解析components###################
					</xsl:comment>
					<xsl:apply-templates select="sml:PrcessChain/sml:components" />

				</xsl:element>

			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- /******************解析components*************/ -->
	<xsl:template match="sml:PrcessChain/sml:components">
		<xsl:element name="rim:Slot">
			<xsl:attribute name="name">
			<xsl:value-of select="concat($slotPrefix,'components')"></xsl:value-of>
			</xsl:attribute>
			<xsl:attribute name="slotType">
				<xsl:value-of select="concat($oasisDataTypePrefix,'String' )"></xsl:value-of>
			</xsl:attribute>
			<xsl:apply-templates select="sml:ComponentList/sml:component" />
		</xsl:element>
	</xsl:template>
	<!--*********************解析component对象 ********************* -->
	<xsl:template match="sml:ComponentList/sml:component">
	</xsl:template>
	<!-- /*******************解析inputs***************/ -->
	<xsl:template match="sml:inputs">
		<xsl:element name="rim:Slot">
			<xsl:attribute name="name">
			<xsl:value-of select="concat($slotPrefix,'inputs')"></xsl:value-of>
			</xsl:attribute>
			<xsl:attribute name="slotType">
				<xsl:value-of select="concat($oasisDataTypePrefix,'String' )"></xsl:value-of>
			</xsl:attribute>
			<xsl:apply-templates select="sml:InputList/sml:input" />
		</xsl:element>
	</xsl:template>
	<xsl:template match="sml:InputList/sml:input">
		<xsl:if test="@name">
			<xsl:element name="rim:Value">
				<xsl:value-of select="concat('name','|',@name)"></xsl:value-of>
			</xsl:element>
		</xsl:if>
		<xsl:if test="swe:Quantity">
			<xsl:if test="swe:Quantity/@referenceFrame">
				<xsl:element name="rim:Value">
					<xsl:value-of
						select="concat('swe:Quantity|referenceFrame','|',swe:Quantity/@referenceFrame)"></xsl:value-of>
				</xsl:element>
			</xsl:if>
			<xsl:if test="swe:Quantity/@gml:id">
				<xsl:element name="rim:Value">
					<xsl:value-of
						select="concat('swe:Quantity|gml:id','|',swe:Quantity/@gml:id)"></xsl:value-of>
				</xsl:element>
			</xsl:if>
			<xsl:if test="swe:Quantity/@axisID">
				<xsl:element name="rim:Value">
					<xsl:value-of
						select="concat('swe:Quantity|axisID','|',swe:Quantity/@axisID)"></xsl:value-of>
				</xsl:element>
			</xsl:if>
			<xsl:if test="swe:Quantity/@fixed">
				<xsl:element name="rim:Value">
					<xsl:value-of
						select="concat('swe:Quantity|fixed','|',swe:Quantity/@fixed)"></xsl:value-of>
				</xsl:element>
			</xsl:if>
			<xsl:if test="swe:Quantity/swe:value">
				<xsl:element name="rim:Value">
					<xsl:value-of
						select="concat('swe:Quantity|swe:value','|',swe:Quantity/swe:value )"></xsl:value-of>
				</xsl:element>
			</xsl:if>
		</xsl:if>
	</xsl:template>

	<!-- /*********************解析outputs***************************/ -->
	<xsl:template match="sml:outputs">
		<xsl:element name="rim:Slot">
			<xsl:attribute name="name">
			<xsl:value-of select="concat($slotPrefix,'outputs')"></xsl:value-of>
			</xsl:attribute>
			<xsl:attribute name="slotType">
				<xsl:value-of select="concat($oasisDataTypePrefix,'String' )"></xsl:value-of>
			</xsl:attribute>
			<xsl:apply-templates select="sml:OutputList/sml:output" />
		</xsl:element>
	</xsl:template>

	<xsl:template match="sml:OutputList/sml:output">
		<xsl:if test="@name">
			<xsl:element name="rim:Value">
				<xsl:value-of select="concat('name','|',@name)"></xsl:value-of>
			</xsl:element>
		</xsl:if>
		<xsl:if test="swe:Quantity">
			<xsl:if test="swe:Quantity/@referenceFrame">
				<xsl:element name="rim:Value">
					<xsl:value-of
						select="concat('swe:Quantity|referenceFrame','|',swe:Quantity/@referenceFrame)"></xsl:value-of>
				</xsl:element>
			</xsl:if>
			<xsl:if test="swe:Quantity/@gml:id">
				<xsl:element name="rim:Value">
					<xsl:value-of
						select="concat('swe:Quantity|gml:id','|',swe:Quantity/@gml:id)"></xsl:value-of>
				</xsl:element>
			</xsl:if>
			<xsl:if test="swe:Quantity/@axisID">
				<xsl:element name="rim:Value">
					<xsl:value-of
						select="concat('swe:Quantity|axisID','|',swe:Quantity/@axisID)"></xsl:value-of>
				</xsl:element>
			</xsl:if>
			<xsl:if test="swe:Quantity/@fixed">
				<xsl:element name="rim:Value">
					<xsl:value-of
						select="concat('swe:Quantity|fixed','|',swe:Quantity/@fixed)"></xsl:value-of>
				</xsl:element>
			</xsl:if>
			<xsl:if test="swe:Quantity/swe:value">
				<xsl:element name="rim:Value">
					<xsl:value-of
						select="concat('swe:Quantity|swe:value','|',swe:Quantity/swe:value )"></xsl:value-of>
				</xsl:element>
			</xsl:if>
		</xsl:if>
	</xsl:template>

	<!-- /*********************解析parameters************************/ -->
	<xsl:template match="sml:parameters">
		<xsl:element name="rim:Slot">
			<xsl:attribute name="name">
			<xsl:value-of select="concat($slotPrefix,'parameters')"></xsl:value-of>
			</xsl:attribute>
			<xsl:attribute name="slotType">
				<xsl:value-of select="concat($oasisDataTypePrefix,'String' )"></xsl:value-of>
			</xsl:attribute>
			<xsl:apply-templates select="sml:ParameterList/sml:parameter" />
		</xsl:element>
	</xsl:template>
	<xsl:template match="sml:ParameterList/sml:parameter">
		<xsl:if test="@name">
			<xsl:element name="rim:Value">
				<xsl:value-of select="concat('name','|',@name)"></xsl:value-of>
			</xsl:element>
		</xsl:if>
		<xsl:if test="swe:Quantity">
			<xsl:if test="swe:Quantity/@referenceFrame">
				<xsl:element name="rim:Value">
					<xsl:value-of
						select="concat('swe:Quantity|referenceFrame','|',swe:Quantity/@referenceFrame)"></xsl:value-of>
				</xsl:element>
			</xsl:if>
			<xsl:if test="swe:Quantity/@gml:id">
				<xsl:element name="rim:Value">
					<xsl:value-of
						select="concat('swe:Quantity|gml:id','|',swe:Quantity/@gml:id)"></xsl:value-of>
				</xsl:element>
			</xsl:if>
			<xsl:if test="swe:Quantity/@axisID">
				<xsl:element name="rim:Value">
					<xsl:value-of
						select="concat('swe:Quantity|axisID','|',swe:Quantity/@axisID)"></xsl:value-of>
				</xsl:element>
			</xsl:if>
			<xsl:if test="swe:Quantity/@fixed">
				<xsl:element name="rim:Value">
					<xsl:value-of
						select="concat('swe:Quantity|fixed','|',swe:Quantity/@fixed)"></xsl:value-of>
				</xsl:element>
			</xsl:if>
			<xsl:if test="swe:Quantity/swe:value">
				<xsl:element name="rim:Value">
					<xsl:value-of
						select="concat('swe:Quantity|swe:value','|',swe:Quantity/swe:value )"></xsl:value-of>
				</xsl:element>
			</xsl:if>
		</xsl:if>
		<xsl:if test="swe:Quantity/swe:Boolean">
			<xsl:element name="rim:Value">
				<xsl:value-of
					select="concat('swe:Quantity|swe:Boolean','|',swe:Quantity/swe:Boolean)"></xsl:value-of>
			</xsl:element>
		</xsl:if>
	</xsl:template>

	<!-- /*********************变量**********************************/ -->

	<xsl:variable name="fixedprocessChainPrefix" select="'urn:ogc:def:role:OGC:processChain'"></xsl:variable>
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
		select="'urn:ogc:def:objectType:OGC-CSW-ebRIM-Sensor::System'" />
	<xsl:variable name="fixedObjectTypeComponent"
		select="'urn:ogc:def:objectType:OGC-CSW-ebRIM-Sensor::Component'" />
	<!--
		下面的两个变量，fixedObjectTypeProcessModel和fixedObjectTypeProcessChain。这两个变量均是自己添加的，暂时放在这里
		yxl
	-->
	<xsl:variable name="fixedObjectTypeProcessModel"
		select="'urn:ogc:def:objectType:OGC-CSW-ebRIM-Sensor::ProcessModel'" />
	<xsl:variable name="fixedObjectTypeProcessChain"
		select="'urn:ogc:def:objectType:OGC-CSW-ebRIM-Sensor::ProcessChain'"></xsl:variable>



	<!-- 在这里可以增加的associationType类型  -->
	<!--
		在该处增加association类型例如 <xsl:variable name="ComposeAssociationType"
		select="urn:ogc:def:associationType::OGC-CSW-ebRIM-Sensor::Compose" />
	-->
	<xsl:variable name="ComposedOfAssociationType"
		select="'urn:ogc:def:associationType::OGC-CSW-ebRIM-Sensor::ComposedOf'" />
	<xsl:variable name="AccessibleThroughAssociationType"
		select="'urn:ogc:def:associationType::OGC-CSW-ebRIM-Sensor::AccessibleThrough'" />
	<!-- CLASSIFICATION SCHEMES -->

	<xsl:variable name="classificationSchemeId.SystemTypes"
		select="'urn:ogc:def:classificationScheme:OGC-CSW-ebRIM-Sensor::SystemTypes'" />

	<xsl:variable name="classificationSchemeId.ISO19119Services"
		select="'urn:ogc:def:ebRIM-ClassificationScheme:ISO-19119:2005:Services'" />
	<!--
		/**************************************************
		三种分类，platformType，stationLevel和IntendedAplication三种分类种类 自己定义的 yxl
		***********************************************/
	-->
	<xsl:variable name="classificationSchemeId.platformType"
		select="'urn:ogc:def:classificationScheme:OGC-CSW-ebRIM-Sensor::platformType'" />
	<xsl:variable name="classificationSchemeId.stationLevel"
		select="'urn:ogc:def:classificationScheme:OGC-CSW-ebRIM-Sensor::stationLevel'" />
	<xsl:variable name="classificationSchemeId.IntendedApplication"
		select="'urn:ogc:def:classificationScheme:OGC-CSW-ebRIM-Sensor::IntendedApplication'" />

	<!-- (ID) PREFIXES -->
	<xsl:variable name="systemIdentifier"
		select="//sml:System/sml:identification/sml:IdentifierList/sml:identifier/sml:Term[@definition='urn:ogc:def:identifier:OGC::uniqueID' or @definition='urn:ogc:def:identifier:OGC:1.0:uniqueID']/sml:value" />
	<!-- 判断前缀，就是idPrefix必须以urn：开头 yxl -->
	<xsl:variable name="idPrefix">
		<xsl:choose>
			<xsl:when test="starts-with($systemIdentifier, 'urn:')">
				<xsl:value-of select="concat($systemIdentifier, ':')" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="concat('urn:', $systemIdentifier, ':')" />
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
	<xsl:variable name="slotPrefix"
		select="'urn:ogc:def:slot:OGC-CSW-ebRIM-Sensor::'" />
	<xsl:variable name="oasisDataTypePrefix"
		select="'urn:oasis:names:tc:ebxml-regrep:DataType:'" />
	<xsl:variable name="iso19107DataTypePrefix"
		select="'urn:ogc:def:dataType:ISO-19107:2003:'" />
	<xsl:variable name="classificationNodeIdPrefix"
		select="'urn:ogc:def:objectType:OGC-CSW-ebRIM-Sensor::'" />
	<!-- GLOBAL ATTRIBUTE VALUES -->
	<xsl:variable name="fixedSystemAndComponentClassificationParent"
		select="'urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ExtrinsicObject'" />
	<xsl:variable name="defaultLocalizedStringLang" select="'en-US'" />
	<!-- MESSAGES -->
	<xsl:variable name="message.gmlError"
		select="'Element gml:description not found! Maybe I cannot match the gml namespace? Transformation requires http://www.opengis.net/gml'" />

</xsl:transform>