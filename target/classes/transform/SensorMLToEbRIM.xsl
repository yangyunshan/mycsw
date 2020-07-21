<?xml version="1.0" encoding="UTF-8"?>

<xsl:transform xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:rim="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0" xmlns:gml="http://www.opengis.net/gml"
	xmlns:swe="http://www.opengis.net/swe/1.0.1" xmlns:wrs="http://www.opengis.net/cat/wrs/1.0"
	xmlns:sml="http://www.opengis.net/sensorML/1.0.1" version="2.0"
	xsi:schemaLocation="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0 http://docs.oasis-open.org/regrep/v3.0/schema/rim.xsd     http://www.opengis.net/cat/wrs/1.0 http://schemas.opengis.net/csw/2.0.2/profiles/ebrim/1.0/csw-ebrim.xsd">            <!-- imports -->

	<!-- XSLT转换文件入口 ,所有的文字内容，属性和元素-->
	<xsl:template match="text()|@*" />
	<!-- 去除所有 元素的空格 -->
	<xsl:strip-space elements="*" />
	<!-- 根元素转换入口 -->
	<xsl:template match="sml:member">
		<!-- 如果$debugOn为真，则显示其中的内容，否则不显示  $debugOn默认为 false-->
		<xsl:if test="$debugOn">
			<xsl:comment>
				<xsl:text>Processor:</xsl:text>
				<xsl:value-of select="system-property('xsl:vendor')" />
			</xsl:comment>
		</xsl:if>
		<!-- 元素RegistryPackage入口 -->
		<xsl:element name="rim:RegistryPackage" namespace="{$nsrim}">
			<xsl:choose>
				<!-- 如果noSWE为真怎显示第一个xsl：when的内容，否则显示xsl:otherwise的内容  noSWE默认为true-->
				<xsl:when test="$noSWE">
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
				<xsl:apply-templates select="sml:System" mode="extrinsic-object" />
				<xsl:apply-templates select="sml:System"
					mode="classification-association" />
				<!--
					应用xsl:apply-templates select=sml:System/sml:contact 模板来转换
				-->
				<xsl:comment>
					<xsl:text>***********传感器管理人员联系方式************</xsl:text>
				</xsl:comment>
				<xsl:apply-templates select="sml:System/sml:contact" />

				<!-- <xsl:apply-templates select="sml:System/sml:components" /> -->
				<!-- YXL 新增connections节点 -->
				<!--<xsl:apply-templates select="sml:System/sml:connections" />
				-->
				<!--
					用于处理ProcessModel类型
				-->
				<xsl:apply-templates select="sml:ProcessModel"
					mode="extrinsic-object" />
				<xsl:apply-templates select="sml:ProcessModel"
					mode="classification-association" />

				<xsl:apply-templates select="sml:ProcessModel/sml:contact" />
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<xsl:template match="sml:ProcessModel" mode="extrinsic-object">
		<xsl:element name="wrs:ExtrinsicObject" namespace="{$nswrs}">
		</xsl:element>
	</xsl:template>
	<!-- 所有的元素都需去除不必要的空白
-->
	<xsl:strip-space elements="*" />

	<!--
		在这里也可以根据association生成其他的AssociationType类型 ，下面生成
		了composeOf-association和AccessibleThrough-associationType属性
	-->
	<!--
		生成 ComposedOf-association 关系
		对应的是sml:Component和sml:system中的identification部分
	-->
	<xsl:template match="sml:components/sml:ComponentList/sml:component"
		mode="ComposedOf-association">
		<!-- Source_ID 取UniqueId值 YXL -->
		<xsl:param name="SOURCE_ID"
			select="ancestor::sml:System/sml:identification/sml:IdentifierList/sml:identifier/sml:Term[@definition='urn:ogc:def:identifier:OGC::uniqueID' or @definition='urn:ogc:def:identifier:OGC:1.0:uniqueID']/sml:value" />
		<xsl:element name="rim:Association" namespace="{$nsrim}">
			<xsl:attribute name="id"><xsl:value-of
				select="concat($idPrefix, $idMiddlefixAssociation, generate-id())" /></xsl:attribute>
			<xsl:attribute name="associationType"><xsl:value-of
				select="$ComposedOfAssociationType" /></xsl:attribute>
			<xsl:attribute name="sourceObject"><xsl:value-of select="$SOURCE_ID" /></xsl:attribute>
			<!-- 这里取得是sml:Component下的uniqueId属性值  YXL -->
			<!--
				<xsl:attribute name="targetObject"><xsl:value-of
				select="sml:identification/sml:IdentifierList/sml:identifier/sml:Term[@definition='urn:ogc:def:identifier:OGC::uniqueID'
				or @definition='urn:ogc:def:identifier:OGC:1.0:uniqueID']/sml:value"
				/></xsl:attribute>
			-->
			<xsl:attribute name="targetObject">
		<xsl:value-of select="@xlink:href"></xsl:value-of>
	</xsl:attribute>
		</xsl:element>
	</xsl:template>

	<!-- 生成AccessibleThrough-association 关系对应的事sml:interface部分 YXL-->
	<xsl:template mode="AccessibleThrough-association"
		match="sml:interfaces/sml:InterfaceList/sml:interface/sml:InterfaceDefinition/sml:serviceLayer/swe:DataRecord[@definition='urn:ogc:def:interface:OGC::SWEServiceInterface' or @definition='urn:ogc:def:interface:OGC:1.0:SWEServiceInterface']">
		<xsl:param name="SOURCE_OBJECT_ID" />
		<xsl:variable name="SERVICE_ID"
			select="concat($idPrefix, $idMiddlefixService, generate-id())" />

		<!-- normalize-space:用于清除除元素的前后空格 -->

		<!-- URL的值为ServiceURL的值 YXL -->
		<xsl:variable name="URL"
			select="normalize-space(swe:field[@name='urn:ogc:def:interface:OGC::ServiceURL' or @name='urn:ogc:def:interface:OGC:1.0:ServiceURL']/swe:Text/swe:value)" />
		<xsl:variable name="TYPE"
			select="normalize-space(swe:field[@name='urn:ogc:def:interface:OGC::ServiceType' or @name='urn:ogc:def:interface:OGC:1.0:ServiceType']/swe:Text/swe:value)" />
		<xsl:variable name="SSID"
			select="normalize-space(swe:field[@name='urn:ogc:def:interface:OGC::ServiceSpecificSensorID' or @name='urn:ogc:def:interface:OGC:1.0:ServiceSpecificSensorID']/swe:Text/swe:value)" />

		<!--
			classification_node关系的生成，是调用deduceServiceType模板，传入参数type，将Type值传入 yxl
		-->
		<xsl:variable name="CLASSIFICATION_NODE">
			<xsl:call-template name="deduceServiceType">
				<xsl:with-param name="type">
					<xsl:value-of select="$TYPE" />
				</xsl:with-param>
			</xsl:call-template>
		</xsl:variable>
		<xsl:element name="rim:Association" namespace="{$nsrim}">
			<xsl:attribute name="id">
				<xsl:value-of
				select="concat($idPrefix,  $idMiddlefixAssociation, generate-id())" />
			</xsl:attribute>
			<xsl:attribute name="associationType">
				<xsl:value-of select="$AccessibleThroughAssociationType" />
			</xsl:attribute>
			<xsl:attribute name="sourceObject"><xsl:value-of select="$SOURCE_OBJECT_ID" /></xsl:attribute>
			<xsl:attribute name="targetObject"><xsl:value-of select="$SERVICE_ID" /></xsl:attribute>
			<xsl:element name="rim:Slot" namespace="{$nsrim}">
				<xsl:attribute name="name"><xsl:value-of
					select="concat($slotPrefix, 'ServiceSpecificSensorID')" /></xsl:attribute>
				<xsl:element name="rim:ValueList" namespace="{$nsrim}">
					<xsl:element name="rim:Value" namespace="{$nsrim}">
						<xsl:value-of select="$SSID" />
					</xsl:element>
				</xsl:element>
			</xsl:element>
		</xsl:element>

		<xsl:element name="rim:Service" namespace="{$nsrim}">
			<xsl:attribute name="id"><xsl:value-of select="$SERVICE_ID" /></xsl:attribute>
			<!-- 子元素 rim:Classification的表示 -->
			<xsl:element name="rim:Classification" namespace="{$nsrim}">
				<xsl:attribute name="id">      
					<xsl:value-of
					select="concat($idPrefix, $idMiddlefixClassification, generate-id())" />        
	            </xsl:attribute>
				<xsl:attribute name="classifiedObject"> 
					 <xsl:value-of select="$SERVICE_ID" />
				</xsl:attribute>
				<xsl:attribute name="classificationNode">
					<xsl:value-of select="$CLASSIFICATION_NODE" />
				</xsl:attribute>
			</xsl:element>
			<!--  -->
			<xsl:element name="rim:ServiceBinding" namespace="{$nsrim}">
				<!--
					generate-id(swe:field[@name='urn:ogc:def:inteface:OGC:ServiceURL'
					or @name='urn:ogc:def:interface:OGC:1.0:ServiceURL')不是很清楚 YXL
				-->
				<xsl:attribute name="id"><xsl:value-of
					select="concat($idPrefix, $idMiddlefixService, generate-id(swe:field[@name='urn:ogc:def:interface:OGC::ServiceURL' or @name='urn:ogc:def:interface:OGC:1.0:ServiceURL']/swe:Text/swe:value))" /></xsl:attribute>
				<xsl:attribute name="service"><xsl:value-of select="$SERVICE_ID" /></xsl:attribute>
				<xsl:attribute name="accessURI"><xsl:value-of select="$URL" /></xsl:attribute>
				<xsl:element name="rim:Name" namespace="{$nsrim}">
					<xsl:element name="rim:LocalizedString" namespace="{$nsrim}">
						<xsl:attribute name="xml:lang"><xsl:value-of
							select="$defaultLocalizedStringLang" /></xsl:attribute>
						<xsl:attribute name="value"><xsl:value-of
							select="ancestor::sml:interface/@name" /></xsl:attribute>
					</xsl:element>
				</xsl:element>
				<xsl:element name="rim:Description" namespace="{$nsrim}">
					<xsl:element name="rim:LocalizedString" namespace="{$nsrim}">
						<xsl:attribute name="xml:lang"><xsl:value-of
							select="$defaultLocalizedStringLang" /></xsl:attribute>
						<xsl:attribute name="value">TBD</xsl:attribute>
					</xsl:element>
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>



	<xsl:strip-space elements="*" />

	<!-- 利用模板生成classification节点 按照application分类YXL -->
	<xsl:template mode="classification"
		match="sml:classification/sml:ClassifierList/sml:classifier/sml:Term[@definition='urn:ogc:def:classifier:OGC:1.0:application' or @definition='urn:ogc:def:classifier:OGC::application']">
		<xsl:param name="CLASSIFIED_OBJECT_ID" />
		<xsl:element name="rim:Classification" namespace="{$nsrim}">
			<xsl:attribute name="id">
				<xsl:value-of
				select="concat($idPrefix, $idMiddlefixClassification, generate-id())" />                  
			 </xsl:attribute>
			<xsl:attribute name="classifiedObject">                             
			     <xsl:value-of select="$CLASSIFIED_OBJECT_ID" />
			</xsl:attribute>
			<xsl:attribute name="classificationNode"> 
				 <xsl:value-of
				select="concat( $idPrefix,$classificationNodeIdPrefix, generate-id(sml:value))" />                          
			</xsl:attribute>
		</xsl:element>
	</xsl:template>
	<!-- 利用模板生成classficationnode节点yxl -->
	<xsl:template mode="classificationNode"
		match="sml:classification/sml:ClassifierList/sml:classifier/sml:Term[@definition='urn:ogc:def:classifier:OGC:1.0:application'                  or @definition='urn:ogc:def:classifier:OGC::application']">
		<xsl:element name="rim:ClassificationNode" namespace="{$nsrim}">
			<xsl:attribute name="id">
				<xsl:value-of
				select="concat($idPrefix,$classificationNodeIdPrefix, generate-id(sml:value))" /> 
			</xsl:attribute>
			<xsl:attribute name="parent"><xsl:value-of
				select="$classificationSchemeId.IntendedApplication" />
			</xsl:attribute>
			<xsl:element name="rim:Name" namespace="{$nsrim}">
				<xsl:element name="rim:LocalizedString" namespace="{$nsrim}">
					<xsl:attribute name="xml:lang">
						<xsl:value-of select="$defaultLocalizedStringLang" /></xsl:attribute>
					<xsl:attribute name="value">
						<xsl:value-of select="normalize-space(sml:value)" />
					</xsl:attribute>
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 新增 urn:ogc:def:classifier:OGC:1.0:newAdded yxl -->
	<xsl:template mode="classification"
		match="sml:classification/sml:ClassifierList/sml:classifier/sml:Term[@definition='urn:ogc:def:classifier:OGC:1.0:newAdded' or @definition='urn:ogc:def:classifier:OGC::newAdded']">
		<xsl:param name="CLASSIFIED_OBJECT_ID" />
		<xsl:element name="rim:Classification" namespace="{$nsrim}">
			<xsl:attribute name="id">
				<xsl:value-of
				select="concat($idPrefix, $idMiddlefixClassification, generate-id())" />
			</xsl:attribute>
			<xsl:attribute name="classifiedObject">
				<xsl:value-of select="$CLASSIFIED_OBJECT_ID" />
			</xsl:attribute>
			<xsl:attribute name="classificationNode">
				<xsl:value-of
				select="concat($idPrefix,$classificationNodeIdPrefix, generate-id(sml:value))" />
			</xsl:attribute>
		</xsl:element>
	</xsl:template>
	<!-- 新增 urn:ogc:def:classifier:OGC:1.0:sensorType yxl -->
	<xsl:template mode="classification"
		match="sml:classification/sml:ClassifierList/sml:classifier/sml:Term[@definition='urn:ogc:def:classifier:OGC:1.0:sensorType' or @definition='urn:ogc:def:classifier:OGC::sensorType']">
		<xsl:param name="CLASSIFIED_OBJECT_ID" />
		<xsl:element name="rim:Classification" namespace="{$nsrim}">
			<xsl:attribute name="id">
				<xsl:value-of
				select="concat($idPrefix, $idMiddlefixClassification, generate-id())" />
			</xsl:attribute>
			<xsl:attribute name="classifiedObject">
				<xsl:value-of select="$CLASSIFIED_OBJECT_ID" />
			</xsl:attribute>
			<xsl:attribute name="classificationNode">
				<xsl:value-of
				select="concat($idPrefix,$classificationNodeIdPrefix, generate-id(sml:value))" />
			</xsl:attribute>
		</xsl:element>
	</xsl:template>
	<!-- 新增 urn:ogc:def:classifier:OGC:1.0:orbitType yxl -->
	<xsl:template mode="classification"
		match="sml:classification/sml:ClassifierList/sml:classifier/sml:Term[@definition='urn:ogc:def:classifier:OGC:1.0:orbitType' or @definition='urn:ogc:def:classifier:OGC::orbitType']">
		<xsl:param name="CLASSIFIED_OBJECT_ID" />
		<xsl:element name="rim:Classification" namespace="{$nsrim}">
			<xsl:attribute name="id">
				<xsl:value-of
				select="concat($idPrefix, $idMiddlefixClassification, generate-id())" />
			</xsl:attribute>
			<xsl:attribute name="classifiedObject">
				<xsl:value-of select="$CLASSIFIED_OBJECT_ID" />
			</xsl:attribute>
			<xsl:attribute name="classificationNode">
				<xsl:value-of
				select="concat($idPrefix,$classificationNodeIdPrefix, generate-id(sml:value))" />
			</xsl:attribute>
		</xsl:element>
	</xsl:template>
	<!-- 新增urn:ogc:def:classifier:OGC:1.0:sharingLevel yxl 2013-10-15  -->
	<xsl:template mode="classification"
		match="sml:classification/sml:ClassifierList/sml:classifier/sml:Term[@definition='urn:ogc:def:classifier:OGC:1.0:sharingLevel' or @definition='urn:ogc:def:classifier:OGC:1.0:sharingLevel']">
		<xsl:param name="CLASSIFIED_OBJECT_ID" />
		<xsl:element name="rim:Classification" namespace="{$nsrim}">
			<xsl:attribute name="id">
				<xsl:value-of
				select="concat($idPrefix, $idMiddlefixClassification, generate-id())" />
			</xsl:attribute>
			<xsl:attribute name="classifiedObject">
				<xsl:value-of select="$CLASSIFIED_OBJECT_ID" />
			</xsl:attribute>
			<xsl:attribute name="classificationNode">
				<xsl:value-of
				select="concat($idPrefix,$classificationNodeIdPrefix, generate-id(sml:value))" />
			</xsl:attribute>
		</xsl:element>
	</xsl:template>
	<!-- 新增urn:ogc:def:classifier:OGC:1.0:platformType yxl -->
	<xsl:template mode="classification"
		match="sml:classification/sml:ClassifierList/sml:classifier/sml:Term[@definition='urn:ogc:def:classifier:OGC:1.0:platformType' or @definition='urn:ogc:def:classifier:OGC::platformType']">
		<xsl:param name="CLASSIFIED_OBJECT_ID" />
		<xsl:element name="rim:Classification" namespace="{$nsrim}">
			<xsl:attribute name="id">
				<xsl:value-of
				select="concat($idPrefix, $idMiddlefixClassification, generate-id())" />
			</xsl:attribute>
			<xsl:attribute name="classifiedObject">
				<xsl:value-of select="$CLASSIFIED_OBJECT_ID" />
			</xsl:attribute>
			<xsl:attribute name="classificationNode">
				<xsl:value-of
				select="concat($idPrefix,$classificationNodeIdPrefix, generate-id(sml:value))" />
			</xsl:attribute>
		</xsl:element>
	</xsl:template>
	<!-- 新增urn:ogc:def:classifier:OGC:1.0:intendedApplication yxl -->
	<xsl:template mode="classification"
		match="sml:classification/sml:ClassifierList/sml:classifier/sml:Term[@definition='urn:ogc:def:classifier:OGC:1.0:intendedApplication' or @definition='urn:ogc:def:classifier:OGC::intendedApplication']">
		<xsl:param name="CLASSIFIED_OBJECT_ID" />
		<xsl:element name="rim:Classification" namespace="{$nsrim}">
			<xsl:attribute name="id">
				<xsl:value-of
				select="concat($idPrefix, $idMiddlefixClassification, generate-id())" />
			</xsl:attribute>
			<xsl:attribute name="classifiedObject">
				<xsl:value-of select="$CLASSIFIED_OBJECT_ID" />
			</xsl:attribute>
			<xsl:attribute name="classificationNode">
				<xsl:value-of
				select="concat($idPrefix,$classificationNodeIdPrefix, generate-id(sml:value))" />
			</xsl:attribute>
		</xsl:element>
	</xsl:template>

	<!-- 新增urn:ogc:def:classifier:OGC:1.0:radarType yxl -->
	<xsl:template mode="classification"
		match="sml:classification/sml:ClassifierList/sml:classifier/sml:Term[@definition='urn:ogc:def:classifier:OGC:1.0:radarType' or @definition='urn:ogc:def:classifier:OGC::radarType']">
		<xsl:param name="CLASSIFIED_OBJECT_ID" />
		<xsl:element name="rim:Classification" namespace="{$nsrim}">
			<xsl:attribute name="id">
				<xsl:value-of
				select="concat($idPrefix, $idMiddlefixClassification, generate-id())" />
			</xsl:attribute>
			<xsl:attribute name="classifiedObject">
				<xsl:value-of select="$CLASSIFIED_OBJECT_ID" />
			</xsl:attribute>
			<xsl:attribute name="classificationNode">
				<xsl:value-of
				select="concat($idPrefix,$classificationNodeIdPrefix, generate-id(sml:value))" />
			</xsl:attribute>
		</xsl:element>
	</xsl:template>
	<!-- 新增urn:ogc:def:classifier:OGC:1.0:scannerType yxl -->
	<xsl:template mode="classification"
		match="sml:classification/sml:ClassifierList/sml:classifier/sml:Term[@definition='urn:ogc:def:classifier:OGC:1.0:scannerType' or @definition='urn:ogc:def:classifier:OGC::scannerType']">
		<xsl:param name="CLASSIFIED_OBJECT_ID" />
		<xsl:element name="rim:Classification" namespace="{$nsrim}">
			<xsl:attribute name="id">
				<xsl:value-of
				select="concat($idPrefix, $idMiddlefixClassification, generate-id())" />
			</xsl:attribute>
			<xsl:attribute name="classifiedObject">
				<xsl:value-of select="$CLASSIFIED_OBJECT_ID" />
			</xsl:attribute>
			<xsl:attribute name="classificationNode">
				<xsl:value-of
				select="concat($idPrefix,$classificationNodeIdPrefix, generate-id(sml:value))" />
			</xsl:attribute>
		</xsl:element>
	</xsl:template>
	<!-- 新增urn:ogc:def:classifier:OGC:1.0:stationLevel yxl -->
	<xsl:template mode="classification"
		match="sml:classification/sml:ClassifierList/sml:classifier/sml:Term[@definition='urn:ogc:def:classifier:OGC:1.0:stationLevel' or @definition='urn:ogc:def:classifier:OGC::stationLevel']">
		<xsl:param name="CLASSIFIED_OBJECT_ID" />
		<xsl:element name="rim:Classification" namespace="{$nsrim}">
			<xsl:attribute name="id">
				<xsl:value-of
				select="concat($idPrefix, $idMiddlefixClassification, generate-id())" />
			</xsl:attribute>
			<xsl:attribute name="classifiedObject">
				<xsl:value-of select="$CLASSIFIED_OBJECT_ID" />
			</xsl:attribute>
			<xsl:attribute name="classificationNode">
				<xsl:value-of
				select="concat($idPrefix,$classificationNodeIdPrefix, generate-id(sml:value))" />
			</xsl:attribute>
		</xsl:element>
	</xsl:template>
	<!-- 新增urn:ogc:def:classifier:OGC:1.0:orbitType -->
	<xsl:template mode="classificationNode"
		match="sml:classification/sml:ClassifierList/sml:classifier/sml:Term[@definition='urn:ogc:def:classifier:OGC:1.0:orbitType' or @definition='urn:ogc:def:classifier:OGC:orbitType']">
		<xsl:element name="rim:ClassificationNode" namespace="{$nsrim}">
			<xsl:attribute name="id">                                 
			 <xsl:value-of
				select="concat($idPrefix,$classificationNodeIdPrefix, generate-id(sml:value))" />                          </xsl:attribute>
			<xsl:attribute name="parent"><xsl:value-of
				select="$classificationSchemeId.oritTypes" /></xsl:attribute>
			<xsl:element name="rim:Name" namespace="{$nsrim}">
				<xsl:element name="rim:LocalizedString" namespace="{$nsrim}">
					<xsl:attribute name="xml:lang"><xsl:value-of
						select="$defaultLocalizedStringLang" /></xsl:attribute>
					<xsl:attribute name="value">                                                  <xsl:value-of
						select="normalize-space(sml:value)" /></xsl:attribute>
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 新增urn:ogc:def:classifier:OGC:1.0:newAdded  -->
	<xsl:template mode="classificationNode"
		match="sml:classification/sml:ClassifierList/sml:classifier/sml:Term[@definition='urn:ogc:def:classifier:OGC:1.0:newAdded' or @definition='urn:ogc:def:classifier:OGC:newAdded']">
		<xsl:element name="rim:ClassificationNode" namespace="{$nsrim}">
			<xsl:attribute name="id">                                 
			 <xsl:value-of
				select="concat($idPrefix,$classificationNodeIdPrefix, generate-id(sml:value))" />                          </xsl:attribute>
			<xsl:attribute name="parent"><xsl:value-of
				select="$classificationSchemeId.newAddeds" /></xsl:attribute>
			<xsl:element name="rim:Name" namespace="{$nsrim}">
				<xsl:element name="rim:LocalizedString" namespace="{$nsrim}">
					<xsl:attribute name="xml:lang"><xsl:value-of
						select="$defaultLocalizedStringLang" /></xsl:attribute>
					<xsl:attribute name="value">                                                  <xsl:value-of
						select="normalize-space(sml:value)" /></xsl:attribute>
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 新增 urn:ogc:def:classifier:OGC:1.0:radarType -->
	<xsl:template mode="classificationNode"
		match="sml:classification/sml:ClassifierList/sml:classifier/sml:Term[@definition='urn:ogc:def:classifier:OGC:1.0:radarType' or @definition='urn:ogc:def:classifier:OGC::radarType']">
		<xsl:element name="rim:ClassificationNode" namespace="{$nsrim}">
			<xsl:attribute name="id">                                  <xsl:value-of
				select="concat($idPrefix,$classificationNodeIdPrefix, generate-id(sml:value))" />                          </xsl:attribute>
			<xsl:attribute name="parent"><xsl:value-of
				select="$classificationSchemeId.SystemTypes" /></xsl:attribute>
			<xsl:element name="rim:Name" namespace="{$nsrim}">
				<xsl:element name="rim:LocalizedString" namespace="{$nsrim}">
					<xsl:attribute name="xml:lang"><xsl:value-of
						select="$defaultLocalizedStringLang" /></xsl:attribute>
					<xsl:attribute name="value">                                                  <xsl:value-of
						select="normalize-space(sml:value)" /></xsl:attribute>
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 新增urn:ogc:def:classifier:OGC:1.0:scannerType 扫描类型-->
	<xsl:template mode="classificationNode"
		match="sml:classification/sml:ClassifierList/sml:classifier/sml:Term[@definition='urn:ogc:def:classifier:OGC:1.0:scannerType' or @definition='urn:ogc:def:classifier:OGC:scannerType']">
		<xsl:element name="rim:ClassificationNode" namespace="{$nsrim}">
			<xsl:attribute name="id">                                  <xsl:value-of
				select="concat($idPrefix,$classificationNodeIdPrefix, generate-id(sml:value))" /> </xsl:attribute>
			<xsl:attribute name="parent"><xsl:value-of
				select="$classificationSchemeId.scannerType" /></xsl:attribute>
			<xsl:element name="rim:Name" namespace="{$nsrim}">
				<xsl:element name="rim:LocalizedString" namespace="{$nsrim}">
					<xsl:attribute name="xml:lang"><xsl:value-of
						select="$defaultLocalizedStringLang" /></xsl:attribute>
					<xsl:attribute name="value">                                                  <xsl:value-of
						select="normalize-space(sml:value)" /></xsl:attribute>
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>

	<!-- 新增 urn:ogc:def:classifier:OGC:1.0:sensorType -->
	<xsl:template mode="classificationNode"
		match="sml:classification/sml:ClassifierList/sml:classifier/sml:Term[@definition='urn:ogc:def:classifier:OGC:1.0:sensorType' or @definition='urn:ogc:def:classifier:OGC::sensorType']">
		<xsl:element name="rim:ClassificationNode" namespace="{$nsrim}">
			<xsl:attribute name="id">                                  <xsl:value-of
				select="concat($idPrefix,$classificationNodeIdPrefix, generate-id(sml:value))" />                          </xsl:attribute>
			<xsl:attribute name="parent"><xsl:value-of
				select="$classificationSchemeId.sensorType" /></xsl:attribute>
			<xsl:element name="rim:Name" namespace="{$nsrim}">
				<xsl:element name="rim:LocalizedString" namespace="{$nsrim}">
					<xsl:attribute name="xml:lang"><xsl:value-of
						select="$defaultLocalizedStringLang" /></xsl:attribute>
					<xsl:attribute name="value">                                                  <xsl:value-of
						select="normalize-space(sml:value)" /></xsl:attribute>
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 新增urn:ogc:def:classifier:OGC:1.0:intendedApplication yxl -->
	<xsl:template mode="classificationNode"
		match="sml:classification/sml:ClassifierList/sml:classifier/sml:Term[@definition='urn:ogc:def:classifier:OGC:1.0:intendedApplication' or @definition='urn:ogc:def:classifier:OGC:1.0:intendedApplication']">
		<xsl:element name="rim:ClassificationNode" namespace="{$nsrim}">
			<xsl:attribute name="id">
				<xsl:value-of
				select="concat($idPrefix,$classificationNodeIdPrefix,generate-id(sml:value))"></xsl:value-of>
			</xsl:attribute>
			<xsl:attribute name="parent">
				<xsl:value-of select="$classificationSchemeId.IntendedApplication"></xsl:value-of>
			</xsl:attribute>
			<xsl:element name="rim:Name" namespace="{$nsrim}">
				<xsl:element name="rim:LocalizedString" namespace="{$nsrim}">
					<xsl:attribute name="xml:lang">
						<xsl:value-of select="$defaultLocalizedStringLang"></xsl:value-of>
					</xsl:attribute>
					<xsl:attribute name="value">
					<xsl:value-of select="normalize-space(sml:value)"></xsl:value-of>
					</xsl:attribute>
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 新增urn:ogc:def:classifier:OGC:1.0:sharingLevel yxl 2013-10-15 -->
	<xsl:template mode="classificationNode"
		match="sml:classification/sml:ClassifierList/sml:classifier/sml:Term[@definition='urn:ogc:def:classifier:OGC:sharingLevel' or @definition='urn:ogc:def:classifier:OGC:1.0:sharingLevel']">
		<xsl:element name="rim:ClassificationNode" namespace="{$nsrim}">
			<xsl:attribute name="id">
					<xsl:value-of
				select="concat($idPrefix,$classificationNodeIdPrefix,generate-id(sml:value) )">
					</xsl:value-of>
				</xsl:attribute>
			<xsl:attribute name="parent">
					<xsl:value-of select="$classificationSchemeId.sharingLevel"></xsl:value-of>
				</xsl:attribute>
			<xsl:element name="rim:Name" namespace="{$nsrim}">
				<xsl:element name="rim:LocalizedString" namespace="{$nsrim}">
					<xsl:attribute name="xml:lang">
						<xsl:value-of select="$defaultLocalizedStringLang"></xsl:value-of>
					</xsl:attribute>
					<xsl:attribute name="value">
						<xsl:value-of select="normalize-space(sml:value)"></xsl:value-of>
					</xsl:attribute>
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>

	<!-- 新增urn:ogc:def:classifier:OGC:1.0:stationLevel yxl -->
	<xsl:template mode="classificationNode"
		match="sml:classification/sml:ClassifierList/sml:classifier/sml:Term[@definition='urn:ogc:def:classifier:OGC:stationLevel' or @definition='urn:ogc:def:classifier:OGC:1.0:stationLevel']">
		<xsl:element name="rim:ClassificationNode" namespace="{$nsrim}">
			<xsl:attribute name="id">
					<xsl:value-of
				select="concat($idPrefix,$classificationNodeIdPrefix,generate-id(sml:value) )">
					</xsl:value-of>
				</xsl:attribute>
			<xsl:attribute name="parent">
					<xsl:value-of select="$classificationSchemeId.stationLevel"></xsl:value-of>
				</xsl:attribute>
			<xsl:element name="rim:Name" namespace="{$nsrim}">
				<xsl:element name="rim:LocalizedString" namespace="{$nsrim}">
					<xsl:attribute name="xml:lang">
						<xsl:value-of select="$defaultLocalizedStringLang"></xsl:value-of>
					</xsl:attribute>
					<xsl:attribute name="value">
						<xsl:value-of select="normalize-space(sml:value)"></xsl:value-of>
					</xsl:attribute>
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>

	<!-- 新增平台urn:ogc:def:classifier:OGC:1.0:platformType类别 yxl -->
	<xsl:template mode="classificationNode"
		match="sml:classification/sml:ClassifierList/sml:classifier/sml:Term[@definition='urn:ogc:def:classifier:OGC:platformType' or @definition='urn:ogc:def:classifier:OGC:1.0:platformType']">
		<xsl:element name="rim:ClassificationNode" namespace="{$nsrim}">
			<xsl:attribute name="id">
					<xsl:value-of
				select="concat($idPrefix,$classificationNodeIdPrefix,generate-id(sml:value) )">
					</xsl:value-of>
				</xsl:attribute>
			<xsl:attribute name="parent">
					<xsl:value-of select="$classificationSchemeId.platformType"></xsl:value-of>
				</xsl:attribute>
			<xsl:element name="rim:Name" namespace="$nsrim">
				<xsl:element name="rim:LocalizedString" namespace="{$nsrim}">
					<xsl:attribute name="xml:lang">
						<xsl:value-of select="$defaultLocalizedStringLang"></xsl:value-of>
					</xsl:attribute>
					<xsl:attribute name="value">
						<xsl:value-of select="normalize-space(sml:value)"></xsl:value-of>
					</xsl:attribute>
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 递归调用 -->
	<xsl:template name="normalized-substring-after-last-colon">
		<xsl:param name="string" />
		<xsl:variable name="delimiter" select="':'" />
		<xsl:choose>
			<xsl:when test="contains($string, $delimiter)">
				<xsl:call-template name="normalized-substring-after-last-colon">
					<!-- substring-after：取 一段字符串后面的子字符串-->
					<xsl:with-param name="string"
						select="substring-after($string, $delimiter)" />
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<!-- 用法translate(String:目标字符集合,char:目标替换字符,char:替换字符集合)
			 -->
				<xsl:value-of select="translate(normalize-space($string), ' ', '_')" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:strip-space elements="*" />
	<!-- 新增components-->
	<xsl:template match="sml:components/sml:ComponentList/sml:component">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,'components')" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:text>name|</xsl:text>
					<xsl:value-of select="@name" />
				</xsl:element>

				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:text>xlink:arcrole|</xsl:text>
					<xsl:value-of select="@xlink:arcrole" />
				</xsl:element>
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:text>xlink:href|</xsl:text>
					<xsl:value-of select="@xlink:href" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- Component -->
	<xsl:template match="sml:components/sml:ComponentList">

		<xsl:apply-templates select="sml:component"></xsl:apply-templates>
		<!--
			<xsl:apply-templates select="sml:component" mode="extrinsic-object"
			/>
		-->
		<xsl:comment>
			<xsl:text>
			******** classification nodes and accessible through associations
			********
			</xsl:text>
		</xsl:comment>
		<!--
			<xsl:apply-templates select="sml:component"
			mode="association-classification" />
		-->
		<!-- 在这里也可以增加其他的模板应用 yxl -->
	</xsl:template>
	<!-- 新增对interface的处理结果，这里就转换为ebrim中的service结果进行处理 -->
	<xsl:template match="sml:interfaces">
		<xsl:apply-templates select="sml:InterfaceList/sml:interface" />
	</xsl:template>
	<!-- 新增对interface的处理工作 -->

	<!-- 这是sml:component 与下面的sml:Component 相互是有区别的 -->
	<xsl:template match="sml:component" mode="extrinsic-object">
		<xsl:choose>
			<!-- 如果存在xlink：href则会调用下面的choose程序操作，否则直接跳过 -->
			<xsl:when test="@xlink:href">
				<xsl:choose>
					<!-- function-available() 函数返回一个布尔值，该值指示 XSLT 处理器是否支持指定的函数 -->
					<xsl:when test="function-available('doc-available')">
						<xsl:choose>
							<xsl:when test="not(doc-available(@xlink:href))">
								<xsl:comment>
									<xsl:text>
									A component is referenced but the ressource could not be
									accessed. The reference is:
									</xsl:text>
									<xsl:value-of select="@xlink:href" />
								</xsl:comment>
							</xsl:when>
							<xsl:otherwise>
								<!-- document() 函数用于访问外部 XML 文档中的节点。外部 XML 文档必须是合法且可解析的 -->
								<xsl:apply-templates select="document(@xlink:href)/*"
									mode="extrinsic-object" />
							</xsl:otherwise>
						</xsl:choose>
					</xsl:when>
					<xsl:otherwise>
						<xsl:apply-templates select="document(@xlink:href)/*"
							mode="extrinsic-object" />
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
			<xsl:otherwise>
				<xsl:apply-templates select="sml:Component"
					mode="extrinsic-object" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template match="sml:Component" mode="extrinsic-object">
		<xsl:variable name="UNIQUE_ID">
			<xsl:value-of
				select="sml:identification/sml:IdentifierList/sml:identifier/sml:Term[@definition='urn:ogc:def:identifier:OGC::uniqueID' or @definition='urn:ogc:def:identifier:OGC:1.0:uniqueID']/sml:value" />
		</xsl:variable>
		<xsl:element name="wrs:ExtrinsicObject" namespace="{$nswrs}">
			<xsl:attribute name="objectType"><xsl:value-of
				select="$fixedObjectTypeComponent" /></xsl:attribute>
			<xsl:attribute name="lid"><xsl:value-of select="$UNIQUE_ID" /></xsl:attribute>
			<xsl:attribute name="id"><xsl:value-of select="$UNIQUE_ID" /></xsl:attribute>
			<xsl:apply-templates select="sml:keywords" />
			<xsl:apply-templates select="sml:validTime" />
			<xsl:apply-templates select="sml:capabilities" />
			<xsl:apply-templates select="sml:position" />
			<xsl:apply-templates select="sml:inputs" />
			<xsl:apply-templates select="sml:outputs" />
			<xsl:apply-templates select="sml:identification" />
			<xsl:choose>
				<xsl:when test="count(gml:description) > 0">
					<xsl:apply-templates select="gml:description" />
				</xsl:when>
				<xsl:otherwise>
					<xsl:comment>
						<xsl:value-of select="$message.gmlError" />
					</xsl:comment>
				</xsl:otherwise>
			</xsl:choose>
			<xsl:apply-templates select="sml:classification"
				mode="classification">
				<xsl:with-param name="CLASSIFIED_OBJECT_ID">
					<xsl:value-of select="$UNIQUE_ID" />
				</xsl:with-param>
				<xsl:with-param name="CLASSIFIED_TYPE">
					COMPONENT
				</xsl:with-param>
			</xsl:apply-templates>
		</xsl:element>
	</xsl:template>
	<xsl:template match="sml:component" mode="association-classification">
		<xsl:choose>
			<xsl:when test="@xlink:href">
				<xsl:choose>
					<xsl:when test="function-available('doc-available')">
						<xsl:choose>
							<xsl:when test="not(doc-available(@xlink:href))">
								<xsl:comment>
									A component is referenced but the ressource could not be
									accessed. The reference is:
									<xsl:value-of select="@xlink:href" />
								</xsl:comment>
							</xsl:when>
							<xsl:otherwise>
								<xsl:apply-templates select="document(@xlink:href)/*"
									mode="association-classification" />
							</xsl:otherwise>
						</xsl:choose>
					</xsl:when>
					<xsl:otherwise>
						<xsl:apply-templates select="document(@xlink:href)/*"
							mode="association-classification" />
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
			<xsl:otherwise>
				<xsl:apply-templates select="sml:Component"
					mode="association-classification" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template match="sml:Component" mode="association-classification">
		<xsl:variable name="componentIdentifier"
			select="sml:identification/sml:IdentifierList/sml:identifier/sml:Term[@definition='urn:ogc:def:identifier:OGC::uniqueID' or @definition='urn:ogc:def:identifier:OGC:1.0:uniqueID']/sml:value" />
		<xsl:variable name="UNIQUE_ID">
			<!-- 处理好UNIQUE_ID,这个变量的值必须以urn:开头 -->
			<xsl:choose>
				<!-- starts-with(string,char)表示以char开头的string开头，则为true，否则为false -->
				<xsl:when test="starts-with($componentIdentifier, 'urn:')">
					<xsl:value-of select="$componentIdentifier" />
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="concat('urn:', $systemIdentifier)" />
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:apply-templates select="sml:classification"
			mode="classificationNode">
			<xsl:with-param name="CLASSIFIED_OBJECT_ID">
				<xsl:value-of select="$UNIQUE_ID" />
			</xsl:with-param>
		</xsl:apply-templates>
		<xsl:apply-templates select="sml:interfaces"
			mode="AccessibleThrough-association">
			<xsl:with-param name="SOURCE_OBJECT_ID">
				<xsl:value-of select="$UNIQUE_ID" />
			</xsl:with-param>
		</xsl:apply-templates>
	</xsl:template>
	<xsl:template match="sml:contact">
		<xsl:apply-templates select="sml:ResponsibleParty"
			mode="organization" />
		<xsl:if test="sml:ResponsibleParty/sml:individualName">
			<xsl:apply-templates select="sml:ResponsibleParty/sml:individualName"
				mode="primaryContact" />
		</xsl:if>
	</xsl:template>
	<xsl:template match="sml:ResponsibleParty" mode="organization">
		<xsl:element name="rim:Organization" namespace="{$nsrim}">
			<xsl:attribute name="id"><xsl:value-of
				select="concat($idPrefix, $idMiddlefixOrganization, generate-id(sml:organizationName))" />                          </xsl:attribute>
			<xsl:if test="count(sml:individualName)>0">
				<xsl:attribute name="primaryContact">                                          <xsl:value-of
					select="concat($idPrefix, $idMiddlefixOrganization, generate-id(sml:individualName))" />                                  </xsl:attribute>
			</xsl:if>
			<!-- 利用其他任何满足条件的 模板来生成-->
			<xsl:apply-templates />
		</xsl:element>
	</xsl:template>
	<xsl:template match="sml:individualName" mode="primaryContact">
		<xsl:element name="rim:Person" namespace="{$nsrim}">
			<xsl:attribute name="id">
				<xsl:value-of
				select="concat($idPrefix, $idMiddlefixPerson, generate-id(.))" />
			</xsl:attribute>
			<xsl:element name="rim:PersonName" namespace="{$nsrim}">
				<xsl:choose>
					<xsl:when test="contains(., ',')">
						<xsl:attribute name="lastName">                                                          <xsl:value-of
							select="normalize-space(translate(substring-before(., ','), ',', ' '))" />                                                  </xsl:attribute>
						<xsl:attribute name="firstName">                                                          <xsl:value-of
							select="normalize-space(substring-after(., ','))" />                                                  </xsl:attribute>
					</xsl:when>
					<xsl:when test="contains(., ' ')">
						<xsl:attribute name="lastName">                                                          <xsl:value-of
							select="normalize-space(substring-after(., ' '))" />                                                  </xsl:attribute>
						<xsl:attribute name="firstName">                                                          <xsl:value-of
							select="normalize-space(substring-before(., ' '))" />                                                  </xsl:attribute>
					</xsl:when>
					<xsl:otherwise>
						<xsl:attribute name="lastName">                                          <xsl:value-of
							select="normalize-space(.)" /></xsl:attribute>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<xsl:template match="sml:organizationName">
		<xsl:element name="rim:Name" namespace="{$nsrim}">
			<xsl:element name="rim:LocalizedString" namespace="{$nsrim}">
				<xsl:attribute name="value">                                  <xsl:value-of
					select="normalize-space(.)" />                          </xsl:attribute>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<xsl:template match="sml:contactInfo">
		<xsl:apply-templates mode="address" />
		<xsl:if test="sml:address/sml:electronicMailAddress">
			<xsl:apply-templates select="sml:address/sml:electronicMailAddress"
				mode="email" />
		</xsl:if>
	</xsl:template>
	<xsl:template match="sml:phone" mode="address">
		<!-- 没有做任何的修改，以后可以在这里增加，修改 yxl-->
	</xsl:template>
	<xsl:template match="sml:address" mode="address">
		<xsl:element name="rim:Address" namespace="{$nsrim}">
			<!-- 使用只要是存在match到该节点的模板就可以 yxl -->
			<xsl:apply-templates />
		</xsl:element>
	</xsl:template>
	<xsl:template match="sml:deliveryPoint">
		<xsl:attribute name="street">                          <xsl:value-of
			select="." />                  </xsl:attribute>
	</xsl:template>
	<xsl:template match="sml:city">
		<xsl:attribute name="city">                          <xsl:value-of
			select="." />                  </xsl:attribute>
	</xsl:template>
	<xsl:template match="sml:postalCode">
		<xsl:attribute name="postalCode">                          <xsl:value-of
			select="." />                  </xsl:attribute>
	</xsl:template>
	<xsl:template match="sml:country">
		<xsl:attribute name="country">                          <xsl:value-of
			select="." />                  </xsl:attribute>
	</xsl:template>
	<xsl:template match="sml:address/sml:electronicMailAddress"
		mode="email">
		<xsl:element name="rim:EmailAddress">
			<xsl:attribute name="address">
			<xsl:value-of select="." />
			</xsl:attribute>
		</xsl:element>
	</xsl:template>
	<xsl:template match="sml:identification/sml:IdentifierList">
		<!--针对 所属平台标识码的处理[雷达型传感器] -->
		<xsl:if
			test="sml:identifier/sml:Term[@definition='urn:ogc:def:identifier:OGC:1.0:parentSystemUniqueId' or @definition='urn:ogc:def:identifier:OGC:parentSystemUniqueId']">
			<xsl:apply-templates
				select="sml:identifier/sml:Term[@definition='urn:ogc:def:identifier:OGC::parentSystemUniqueId' or @definition='urn:ogc:def:identifier:OGC:1.0:parentSystemUniqueId']"></xsl:apply-templates>
		</xsl:if>
		<!--针对所属平台的简称的处理[雷达型传感器] -->
		<xsl:if
			test="sml:identifier/sml:Term[@definition='urn:ogc:def:identifier:OGC:1.0:parentSystemStandardName' or@definition='urn:ogc:def:identifier:OGC:parentSystemStandardName']">
			<xsl:apply-templates
				select="sml:identifier/sml:Term[@definition='urn:ogc:def:identifier:OGC::parentSystemStandardName' or @definition='urn:ogc:def:identifier:OGC:1.0:parentSystemStandardName']"></xsl:apply-templates>
		</xsl:if>
		<!-- 对于子元素部分的根元素就是父元素的match的节点 -->
		<xsl:apply-templates
			select="sml:identifier/sml:Term[@definition='urn:ogc:def:identifier:OGC::longName' or @definition='urn:ogc:def:identifier:OGC:1.0:longName']" />
		<xsl:apply-templates
			select="sml:identifier/sml:Term[@definition='urn:ogc:def:identifier:OGC::shortName' or @definition='urn:ogc:def:identifier:OGC:1.0:shortName']" />
		<!-- 搭载的传感器的名称  -->
		<xsl:apply-templates
			select="sml:identifier/sml:Term[@definition='urn:ogc:def:identifier:OGC::associatedSensorName' or @definition='urn:ogc:def:identifier:OGC:1.0:associatedSensorName']" />
		<!-- 搭载的传感器的标识符 -->
		<xsl:apply-templates
			select="sml:identifier/sml:Term[@definition='urn:ogc:def:identifier:OGC::associatedSensorUniqueID' or @definition='urn:ogc:def:identifier:OGC:1.0:associatedSensorUniqueID']" />
		<xsl:apply-templates
			select="sml:identifier/sml:Term[@definition='urn:ogc:def:identifier:OGC::modelNumber' or @definition='urn:ogc:def:identifier:OGC:1.0:modelNumber']" />
		<xsl:apply-templates
			select="sml:identifier/sml:Term[@definition='urn:ogc:def:identifier:OGC::manufacturer' or @definition='urn:ogc:def:identifier:OGC:1.0:manufacturer']" />
		<xsl:if
			test="sml:identifier/sml:Term[@name='urn:ogc:def:identifier:OGC:1.0:associatedSensorName' or@name='urn:ogc:def:identifier:OGC:associatedSensorName']">
			<xsl:element name="rim:Slot" namespace="{$nsrim}">
				<xsl:attribute name="name"><xsl:value-of
					select="concat($slotPrefix, 'associatedSensorName')" /></xsl:attribute>
				<xsl:attribute name="slotType"><xsl:value-of
					select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
				<xsl:element name="rim:ValueList" namespace="{$nsrim}">
					<xsl:apply-templates
						select="sml:identifier/sml:Term[@definition='urn:ogc:def:identifier:OGC:1.0:associatedSensorName' or @definition='urn:ogc:def:identifier:OGC:associatedSensorName']" />
				</xsl:element>
			</xsl:element>
		</xsl:if>
		<xsl:if
			test="sml:identifier/sml:Term[@name='urn:ogc:def:identifier:OGC:1.0:associatedSensorUniqueID' or@name='urn:ogc:def:identifier:OGC:associatedSensorUniqueID']">
			<xsl:element name="rim:Slot" namespace="{$nsrim}">
				<xsl:attribute name="name"><xsl:value-of
					select="concat($slotPrefix, 'associatedSensorUniqueID')" /></xsl:attribute>
				<xsl:attribute name="slotType"><xsl:value-of
					select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
				<xsl:element name="rim:ValueList" namespace="{$nsrim}">
					<xsl:apply-templates
						select="sml:identifier/sml:Term[@definition='urn:ogc:def:identifier:OGC:1.0:associatedSensorUniqueID' or @definition='urn:ogc:def:identifier:OGC:associatedSensorUniqueID']" />
				</xsl:element>
			</xsl:element>
		</xsl:if>
	</xsl:template>
	<!--增加对parentSystemUniqueId字段处理-->
	<xsl:template
		match="sml:identifier/sml:Term[@definition='urn:ogc:def:identifier:OGC:1.0:parentSystemUniqueId' or @definition='urn:ogc:def:identifier:OGC:parentSystemUniqueId']">
		<xsl:comment>
			<xsl:text>*************传感器搭载平台标识符***************</xsl:text>
		</xsl:comment>
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix, 'parentSystemUniqueId')" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:apply-templates></xsl:apply-templates>
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!--增加对parentSystemStandardName字段 处理  -->
	<xsl:template
		match="sml:identifier/sml:Term[@definition='urn:ogc:def:identifier:OGC:1.0:parentSystemStandardName' or @definition='urn:ogc:def:identifier:OGC:parentSystemStandardName']">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix, 'parentSystemStandardName')" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:apply-templates></xsl:apply-templates>
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 增加对 associatedSensorName字段 -->
	<xsl:template
		match="sml:identifier/sml:Term[@definition='urn:ogc:def:identifier:OGC:1.0:associatedSensorName' or @definition='urn:ogc:def:identifier:OGC:associatedSensorName']">
		<xsl:element name="rim:Value" namespace="{$nsrim}">
			<xsl:apply-templates />
		</xsl:element>
	</xsl:template>
	<!-- 增加对 associatedSensorUniqueID字段-->
	<xsl:template
		match="sml:identifier/sml:Term[@definition='urn:ogc:def:identifier:OGC:1.0:associatedSensorUniqueID' or @definition='urn:ogc:def:identifier:OGC:associatedSensorUniqueID']">
		<xsl:element name="rim:Value" namespace="{$nsrim}">
			<xsl:apply-templates />
		</xsl:element>
	</xsl:template>
	<xsl:template
		match="sml:identifier/sml:Term[@definition='urn:ogc:def:identifier:OGC::longName' or @definition='urn:ogc:def:identifier:OGC:1.0:longName']">
		<xsl:comment>
			<xsl:text>********传感器名称信息***********</xsl:text>
		</xsl:comment>
		<xsl:element name="rim:Name" namespace="{$nsrim}">
			<xsl:element name="rim:InternationalString" namespace="{$nsrim}">
				<xsl:element name="rim:LocalizedString" namespace="{$nsrim}">
					<xsl:attribute name="xml:lang"><xsl:value-of
						select="$defaultLocalizedStringLang" /></xsl:attribute>
					<xsl:attribute name="value">
				<xsl:apply-templates />
				</xsl:attribute>
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<xsl:template
		match="sml:identifier/sml:Term[@definition='urn:ogc:def:identifier:OGC::shortName' or @definition='urn:ogc:def:identifier:OGC:1.0:shortName']">
		<xsl:comment>
			<xsl:text>********传感器简称信息***********</xsl:text>
		</xsl:comment>
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix, 'ShortName')" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:apply-templates />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<xsl:template
		match="sml:identifier/sml:Term[@definition='urn:ogc:def:identifier:OGC::associatedSensorName' or @definition='urn:ogc:def:identifier:OGC:1.0:associatedSensorName']">
		<xsl:comment>
			<xsl:text>********平台搭载的传感器的名称***********</xsl:text>
		</xsl:comment>
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix, 'associatedSensorName')" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:apply-templates />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<xsl:template
		match="sml:identifier/sml:Term[@definition='urn:ogc:def:identifier:OGC::associatedSensorUniqueID' or @definition='urn:ogc:def:identifier:OGC:1.0:associatedSensorUniqueID']">
		<xsl:comment>
			<xsl:text>********平台搭载传感器标识码***********</xsl:text>
		</xsl:comment>
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix, 'associatedSensorUniqueID')" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:apply-templates />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<xsl:template
		match="sml:identifier/sml:Term[@definition='urn:ogc:def:identifier:OGC::modelNumber' or @definition='urn:ogc:def:identifier:OGC:1.0:modelNumber']">
		<xsl:comment>
			<xsl:text>********平台搭载的型号***********</xsl:text>
		</xsl:comment>
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix, 'modelNumber')" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:apply-templates />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<xsl:template
		match="sml:identifier/sml:Term[@definition='urn:ogc:def:identifier:OGC::manufacturer' or @definition='urn:ogc:def:identifier:OGC:1.0:manufacturer']">
		<xsl:comment>
			<xsl:text>********平台搭载的制造厂商***********</xsl:text>
		</xsl:comment>
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix, 'manufacturer')" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:apply-templates />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<xsl:template match="gml:description">
		<xsl:comment>
			<xsl:text>********传感器描述信息***********</xsl:text>
		</xsl:comment>
		<!-- 新增了对sml:ProcessModel的处理 -->
		<xsl:if test="parent::sml:System|parent::sml:Component">
			<xsl:element name="rim:Description" namespace="{$nsrim}">
				<xsl:element name="rim:LocalizedString" namespace="{$nsrim}">
					<xsl:attribute name="xml:lang"><xsl:value-of
						select="$defaultLocalizedStringLang" />
					</xsl:attribute>
					<xsl:attribute name="value">
						<xsl:value-of select="." /> 
					</xsl:attribute>
				</xsl:element>
			</xsl:element>
		</xsl:if>
	</xsl:template>
	<xsl:template match="sml:value">
		<xsl:value-of select="normalize-space(.)" />
	</xsl:template>
	<!-- capabilitie -->
	<!--
		<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:communicationCapability']"
		model="bbox"> <xsl:apply-templates
		select="swe:field[@name='observedBBOX']" /> </xsl:template>
	-->
	<xsl:template match="swe:field[@name='量程范围']/swe:Envelope">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix, 'BoundedBy')" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($iso19107DataTypePrefix, 'GM_Envelope')" /></xsl:attribute>
			<xsl:choose>
				<xsl:when test="$noSWE">
					<xsl:element name="wrs:ValueList" namespace="{$nswrs}">
						<xsl:element name="wrs:AnyValue" namespace="{$nswrs}">
							<xsl:element name="gml:Envelope" namespace="{$nsgml}">
								<xsl:attribute name="srsName"><xsl:value-of
									select="@referenceFrame" /></xsl:attribute>
								<xsl:apply-templates mode="buildGmlEnvelope" />
							</xsl:element>
						</xsl:element>
					</xsl:element>
				</xsl:when>
				<xsl:otherwise>
					<xsl:element name="wrs:ValueList" namespace="{$nswrs}">
						<xsl:element name="wrs:AnyValue" namespace="{$nswrs}">
							<xsl:element name="swe:Envelope" namespace="{$nsswe}">
								<xsl:attribute name="definition"><xsl:value-of
									select="@definition" /></xsl:attribute>
								<xsl:apply-templates mode="buildEnvelope" />
							</xsl:element>
						</xsl:element>
					</xsl:element>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:element>
	</xsl:template>
	<xsl:template match="swe:field[@name='observedBBOX']/swe:Envelope">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix, 'BoundedBy')" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($iso19107DataTypePrefix, 'GM_Envelope')" /></xsl:attribute>
			<xsl:choose>
				<xsl:when test="$noSWE">
					<xsl:element name="wrs:ValueList" namespace="{$nswrs}">
						<xsl:element name="wrs:AnyValue" namespace="{$nswrs}">
							<xsl:element name="gml:Envelope" namespace="{$nsgml}">
								<xsl:attribute name="srsName"><xsl:value-of
									select="@referenceFrame" /></xsl:attribute>
								<xsl:apply-templates mode="buildGmlEnvelope" />
							</xsl:element>
						</xsl:element>
					</xsl:element>
				</xsl:when>
				<xsl:otherwise>
					<xsl:element name="wrs:ValueList" namespace="{$nswrs}">
						<xsl:element name="wrs:AnyValue" namespace="{$nswrs}">
							<xsl:element name="swe:Envelope" namespace="{$nsswe}">
								<xsl:attribute name="definition"><xsl:value-of
									select="@definition" /></xsl:attribute>
								<xsl:apply-templates mode="buildEnvelope" />
							</xsl:element>
						</xsl:element>
					</xsl:element>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:element>
	</xsl:template>
	<xsl:template match="gml:description" mode="buildGmlEnvelope">
		<xsl:comment>
			<xsl:value-of select="."></xsl:value-of>
		</xsl:comment>
	</xsl:template>
	<xsl:template match="swe:lowerCorner" mode="buildGmlEnvelope">
		<xsl:element name="gml:lowerCorner" namespace="{$nsgml}">
			<xsl:apply-templates mode="buildGmlEnvelope" />
		</xsl:element>
	</xsl:template>
	<xsl:template match="swe:upperCorner" mode="buildGmlEnvelope">
		<xsl:element name="gml:upperCorner" namespace="{$nsgml}">
			<xsl:apply-templates mode="buildGmlEnvelope" />
		</xsl:element>
	</xsl:template>
	<xsl:template match="swe:Vector" mode="buildGmlEnvelope">
		<xsl:value-of
			select="concat(swe:coordinate/swe:Quantity[@axisID='y']/swe:value, ' ', swe:coordinate/swe:Quantity[@axisID='x']/swe:value)" />
	</xsl:template>
	<xsl:template match="swe:lowerCorner" mode="buildEnvelope">
		<xsl:element name="swe:lowerCorner" namespace="{$nsswe}">
			<xsl:apply-templates mode="buildEnvelope" />
		</xsl:element>
	</xsl:template>
	<xsl:template match="swe:upperCorner" mode="buildEnvelope">
		<xsl:element name="swe:upperCorner" namespace="{$nsswe}">
			<xsl:apply-templates mode="buildEnvelope" />
		</xsl:element>
	</xsl:template>
	<xsl:template match="swe:Vector" mode="buildEnvelope">
		<xsl:element name="swe:Vector" namespace="{$nsswe}">
			<xsl:apply-templates mode="buildEnvelope" />
		</xsl:element>
	</xsl:template>
	<xsl:template match="swe:coordinate" mode="buildEnvelope">
		<xsl:element name="swe:coordinate" namespace="{$nsswe}">
			<xsl:attribute name="name"><xsl:value-of select="@name" /></xsl:attribute>
			<xsl:apply-templates mode="buildEnvelope" />
		</xsl:element>
	</xsl:template>
	<xsl:template match="swe:Quantity" mode="buildEnvelope">
		<xsl:element name="swe:Quantity" namespace="{$nsswe}">
			<xsl:attribute name="axisID"><xsl:value-of select="@axisID" /></xsl:attribute>
			<xsl:apply-templates mode="buildEnvelope" />
		</xsl:element>
	</xsl:template>
	<xsl:template match="swe:uom" mode="buildEnvelope">
		<xsl:element name="swe:uom" namespace="{$nsswe}">
			<xsl:attribute name="code"><xsl:value-of select="@code" /></xsl:attribute>
			<xsl:apply-templates mode="buildEnvelope" />
		</xsl:element>
	</xsl:template>
	<xsl:template match="swe:value" mode="buildEnvelope">
		<xsl:element name="swe:value" namespace="{$nsswe}">
			<xsl:value-of select="." />
		</xsl:element>
	</xsl:template>

	<!-- 关键字的构建 -->
	<xsl:template match="sml:keywords/sml:KeywordList">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix, 'Keywords')" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:apply-templates select="sml:keyword" />
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<xsl:template match="sml:keyword">
		<xsl:element name="rim:Value" namespace="{$nsrim}">
			<xsl:value-of select="." />
		</xsl:element>
	</xsl:template>
	<!-- 新增对 sml:characteristics的物理属性的处理 -->
	<xsl:template match="sml:characteristics" mode="physicalProperties">
		<xsl:apply-templates
			select="swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:physicalProperties']/swe:field"></xsl:apply-templates>
	</xsl:template>
	<xsl:template
		match="swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:physicalProperties']/swe:field">
		<xsl:choose>
			<xsl:when test="swe:Quantity">
				<xsl:apply-templates match="swe:Quantity"></xsl:apply-templates>
			</xsl:when>
			<xsl:when test="swe:Category">
				<xsl:apply-templates match="swe:Category"></xsl:apply-templates>
			</xsl:when>
			<xsl:when test="swe:Text">
				<xsl:apply-templates match="swe:Text"></xsl:apply-templates>
			</xsl:when>
			<xsl:when test="swe:QuantityRange">
				<xsl:apply-templates match="swe:QuantityRange"></xsl:apply-templates>
			</xsl:when>
			<xsl:when test="swe:Time">
				<xsl:apply-templates match="swe:Time"></xsl:apply-templates>
			</xsl:when>
			<xsl:when test="swe:Boolean">
				<xsl:apply-templates match="swe:Boolean"></xsl:apply-templates>
			</xsl:when>
			<xsl:when test="swe:Count">
				<xsl:apply-templates match="swe:Count"></xsl:apply-templates>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
	<!-- 新增对雷达型传感器的measurementCapabilities的处理 -->
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']"
		mode="measurementCapabilities">
		<!-- 处理雷达传感器自身所设定的观测范围能力 -->
		<xsl:apply-templates select="swe:field[@name='observedBBOX']" />
		<xsl:apply-templates select="swe:field[@name='量程范围']" />
		<!-- 处理雷达传感器自身所设定的搭载波段 -->
		<xsl:apply-templates
			select="swe:field/swe:Category[@definition='urn:ogc:def:property:bandsCategory']" />
		<!-- 处理雷达传感器自身成像幅宽范围的能力 -->
		<xsl:apply-templates
			select="swe:field/swe:QuantityRange[@definition='urn:ogc:def:property:swathRange']" />
		<!-- 处理雷达传感器入射角度范围的能力 -->
		<xsl:apply-templates
			select="swe:field/swe:QuantityRange[@definition='urn:ogc:def:property:incidentAngle']" />
		<!--处理雷达传感器极化方式的能力  -->
		<xsl:apply-templates
			select="swe:field/swe:Category[@definition='urn:ogc:def:property:polarizationMode']" />
		<!-- 处理雷达传感器 波段频率的能力  -->
		<xsl:apply-templates
			select="swe:field/swe:Quantity[@definition='urn:ogc:def:property:bandFrequency']" />
		<!-- 处理雷达传感器光束模式的能力  -->
		<xsl:apply-templates
			select="swe:field/swe:Category[@definition='urn:ogc:def:property:lightBeamMode']" />
		<!-- 处理扫描传感器波段数 -->
		<xsl:apply-templates
			select="swe:field/swe:Count[@definition='urn:ogc:def:property:bandsNumber']" />
		<!-- 处理扫描传感器波谱范围 -->
		<xsl:apply-templates
			select="swe:field/swe:QuantityRange[@definition='urn:ogc:def:property:bandswidthRange']" />
		<!-- 处理扫描传感器的视场角 -->
		<xsl:apply-templates
			select="swe:field/swe:Quantity[@definition='urn:ogc:def:property:fov']" />
		<!-- 处理扫描传感器的侧视角 -->
		<xsl:apply-templates
			select="swe:field/swe:Quantity[@definition='urn:ogc:def:property:sideSwingAngle']" />
		<!-- 处理扫描传感器的波谱分布特征 -->
		<xsl:apply-templates select="swe:field[@name='波谱分布特征']/swe:DataRecord"></xsl:apply-templates>
		<!-- 处理地面分辨率 -->
		<xsl:apply-templates
			select="swe:field/swe:QuantityRange[@definition='urn:ogc:def:property:spatialResolutionRange']" />
		<!-- 处理星下点分辨率 -->
		<xsl:apply-templates
			select="swe:field/swe:Quantity[@definition='urn:ogc:def:property:nadirSpatialResolution']" />
		<!-- 处理瞬时视场角 -->
		<xsl:apply-templates
			select="swe:field/swe:Quantity[@definition='urn:ogc:def:property:IFOV']" />
		<!-- 处理量化等级 -->
		<xsl:apply-templates
			select="swe:field/swe:Quantity[@definition='urn:ogc:def:property:quantizationLevel']" />
		<!--处理光谱分辨率 -->
		<xsl:apply-templates
			select="swe:field/swe:Quantity[@definition='urn:ogc:def:property:spectralResolution']" />
		<!-- 处理探测精度 -->
		<xsl:apply-templates
			select="swe:field/swe:QuantityRange[@definition='urn:ogc:def:property:precisionRange']" />
		<!-- 处理重返周期 -->
		<xsl:apply-templates
			select="swe:field/swe:Quantity[@definition='urn:ogc:def:property:revisitingPeriod']" />
		<!-- 处理时间分辨率 -->
		<xsl:apply-templates
			select="swe:field/swe:QuantityRange[@definition='urn:ogc:def:property:temporalResolutionRange']" />
		<!-- 处理定位精度 -->
		<xsl:apply-templates
			select="swe:field/swe:Quantity[@definition='urn:ogc:def:property:positioningAccuracy']" />
		<!-- 处理辐射精度 -->
		<xsl:apply-templates
			select="swe:field/swe:Text[@definition='urn:ogc:def:property:radiometricAccuracy']" />
		<!-- 处理数据格式 -->
		<xsl:apply-templates
			select="swe:field/swe:Category[@definition='urn:ogc:def:property:dataFormat']" />
		<!--处理数据质量等级 -->
		<xsl:apply-templates
			select="swe:field/swe:Category[@definition='urn:ogc:def:property:dataQualityLevel']" />
		<!-- 处理数据获取等级 -->
		<xsl:apply-templates
			select="swe:field/swe:Category[@definition='urn:ogc:def:property:dataAccessLevel']" />
		<!-- 摄影型传感器处理 摄影比例尺 -->
		<xsl:apply-templates
			select="swe:field/swe:Quantity[@definition='urn:ogc:def:property:photographicScale']" />
		<!-- 摄影型传感器处理 焦距-->
		<xsl:apply-templates
			select="swe:field/swe:Quantity[@definition='urn:ogc:def:property:focalLength']" />
		<!-- 摄影型传感器处理 航高-->
		<xsl:apply-templates
			select="swe:field/swe:Quantity[@definition='urn:ogc:def:property:flightHeight']" />
		<!-- 摄影型传感器处理 灰阶-->
		<xsl:apply-templates
			select="swe:field/swe:Count[@definition='urn:ogc:def:property:grayScale']" />
		<!-- 摄影型传感器处理航向重叠度-->
		<xsl:apply-templates
			select="swe:field/swe:Quantity[@definition='urn:ogc:def:property:courseOverlapRate']" />
		<!-- 摄影型传感器处理旁向重叠度-->
		<xsl:apply-templates
			select="swe:field/swe:Quantity[@definition='urn:ogc:def:property:lateralOverlapRate']" />
		<!-- 摄影型传感器处理帧率-->
		<xsl:apply-templates
			select="swe:field/swe:Quantity[@definition='urn:ogc:def:property:frameRate']" />
		<!-- 摄影型传感器快门速度 -->
		<xsl:apply-templates
			select="swe:field/swe:Quantity[@definition='urn:ogc:def:property:shutterSpeed']" />
		<!-- 摄影型传感器几何畸变 -->
		<xsl:apply-templates
			select="swe:field/swe:Text[@definition='urn:ogc:def:property:geometricDistortion']" />
		<!-- 原位传感器观测相对误差处理 -->
		<xsl:apply-templates
			select="swe:field/swe:Quantity[@definition='urn:ogc:def:property:observationRelativeError']" />
		<!-- 原位传感器观测绝对误差 -->
		<xsl:apply-templates
			select="swe:field/swe:Quantity[@definition='urn:ogc:def:property:observationAbsError']" />
		<!-- 原位传感器观测频率 -->
		<xsl:apply-templates
			select="swe:field/swe:Quantity[@definition='urn:ogc:def:property:observingFrequency']" />
		<!-- 原位传感器观测分辨率 -->
		<xsl:apply-templates
			select="swe:field/swe:Quantity[@definition='urn:ogc:def:property:observationResolution']" />
		<!-- 原位传感器观测半径 -->
		<xsl:apply-templates
			select="swe:field/swe:Quantity[@definition='urn:ogc:def:property:observationRadius']" />
		<!-- 原位传感器观测范围-->
		<xsl:apply-templates
			select="swe:field/swe:QuantityRange[@definition='urn:ogc:def:property:observationRange']" />
		<!-- 原位传感器观测原理-->
		<xsl:apply-templates
			select="swe:field/swe:Text[@definition='urn:ogc:def:property:observationPrinciple']" />
		<!-- 处理波谱频段范围 -->
		<xsl:apply-templates
			select="swe:field/swe:QuantityRange[@definition='urn:ogc:def:property:spectrumResolutionRange']" />

		<!-- 新增的2013-08-06 -->
		<!-- 原位传感器的观测范围 -->
		<xsl:apply-templates
			select="swe:field/swe:Quantity[@definition='urn:ogc:def:property:measurementRange']"></xsl:apply-templates>
		<!-- 原位传感器的观测分辨率 -->
		<xsl:apply-templates
			select="swe:field/swe:Quantity[@definition='urn:ogc:def:property:measurementResolution']"></xsl:apply-templates>
		<!-- 原位传感器的观测精度 -->
		<xsl:apply-templates
			select="swe:field/swe:Quantity[@definition='urn:ogc:def:property:measurementRMS']"></xsl:apply-templates>
		<!--原位传感器的观测周期 -->
		<xsl:apply-templates
			select="swe:field/swe:Quantity[@definition='urn:ogc:def:property:measurementInterval']"></xsl:apply-templates>
		<!-- 原位传感器的响应时间 -->
		<xsl:apply-templates
			select="swe:field/swe:Quantity[@definition='urn:ogc:def:property:ResponseTime']"></xsl:apply-templates>
		<!-- //新增的2013-08-06 -->
	</xsl:template>
	<!-- 原位传感器的观测范围 -->
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field/swe:Quantity[@definition='urn:ogc:def:property:ResponseTime']">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('measurementCapabilities:','ResponseTime'))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>

	<!-- 原位传感器的观测范围 -->
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field/swe:Quantity[@definition='urn:ogc:def:property:measurementInterval']">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('measurementCapabilities:','measurementInterval'))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>

	<!-- 原位传感器的观测范围 -->
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field/swe:Quantity[@definition='urn:ogc:def:property:measurementRMS']">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('measurementCapabilities:','measurementRMS'))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>

	<!-- 原位传感器的观测范围 -->
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field/swe:Quantity[@definition='urn:ogc:def:property:measurementResolution']">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('measurementCapabilities:','measurementResolution'))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 原位传感器的观测范围 -->
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field/swe:Quantity[@definition='urn:ogc:def:property:measurementRange']">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('measurementCapabilities:','measurementRange'))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 处理波谱频段范围 -->
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field/swe:QuantityRange[@definition='urn:ogc:def:property:spectrumResolutionRange']">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('measurementCapabilities:','spectrumResolutionRange'))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 原位传感器观测原理-->
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field/swe:Text[@definition='urn:ogc:def:property:observationPrinciple']">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('measurementCapabilities:','observationPrinciple'))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 原位传感器观测范围-->
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field/swe:QuantityRange[@definition='urn:ogc:def:property:observationRange']">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('measurementCapabilities:','observationRange'))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field/swe:QuantityRange[@definition='urn:ogc:def:property:OGC:1.0:observedRange']">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('measurementCapabilities:','observationRange'))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 原位传感器观测半径 -->
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field/swe:Quantity[@definition='urn:ogc:def:property:observationRadius']">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('measurementCapabilities:','observationRadius'))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 原位传感器观测分辨率 -->
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field/swe:Quantity[@definition='urn:ogc:def:property:observationResolution']">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('measurementCapabilities:','observationResolution'))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 原位传感器观测频率 -->
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field/swe:Quantity[@definition='urn:ogc:def:property:observingFrequency']">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('measurementCapabilities:','observingFrequency'))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 原位传感器观测绝对误差 -->
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field/swe:Quantity[@definition='urn:ogc:def:property:observationAbsError']">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('measurementCapabilities:','observationAbsError'))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 原位传感器观测相对误差处理 -->
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field/swe:Quantity[@definition='urn:ogc:def:property:observationRelativeError']">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('measurementCapabilities:','observationRelativeError'))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 摄影型传感器快门速度 -->
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field/swe:Text[@definition='urn:ogc:def:property:geometricDistortion']">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('measurementCapabilities:','geometricDistortion'))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 摄影型传感器处理帧率-->
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field/swe:Quantity[@definition='urn:ogc:def:property:frameRate']">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('measurementCapabilities:','frameRate'))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 摄影型传感器处理旁向重叠度-->
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field/swe:Quantity[@definition='urn:ogc:def:property:lateralOverlapRate']">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('measurementCapabilities:','lateralOverlapRate'))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 摄影型传感器处理航向重叠度-->
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field/swe:Quantity[@definition='urn:ogc:def:property:courseOverlapRate']">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('measurementCapabilities:','courseOverlapRate'))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 摄影型传感器处理 灰阶-->
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field/swe:Count[@definition='urn:ogc:def:property:grayScale']">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('measurementCapabilities:','grayScale'))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 摄影型传感器处理 航高-->
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field/swe:Quantity[@definition='urn:ogc:def:property:flightHeight']">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('measurementCapabilities:','flightHeight'))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 摄影型传感器处理 焦距-->
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field/swe:Quantity[@definition='urn:ogc:def:property:focalLength']">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('measurementCapabilities:','focalLength'))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 摄影型传感器处理 摄影比例尺 -->
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field/swe:Quantity[@definition='urn:ogc:def:property:photographicScale']">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('measurementCapabilities:','photographicScale'))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 处理数据获取等级 -->
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field/swe:Category[@definition='urn:ogc:def:property:dataAccessLevel']">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('measurementCapabilities:','dataAccessLevel'))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 处理数据质量等级-->
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field/swe:Category[@definition='urn:ogc:def:property:dataQualityLevel']">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('measurementCapabilities:','dataQualityLevel'))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 处理数据格式 -->
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field/swe:Category[@definition='urn:ogc:def:property:dataFormat']">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('measurementCapabilities:','dataFormat'))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 处理辐射精度 -->
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field/swe:Text[@definition='urn:ogc:def:property:radiometricAccuracy']">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('measurementCapabilities:','radiometricAccuracy'))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 处理定位精度 -->
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field/swe:Quantity[@definition='urn:ogc:def:property:positioningAccuracy']">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('measurementCapabilities:','positioningAccuracy'))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 处理时间分辨率 -->
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field/swe:QuantityRange[@definition='urn:ogc:def:property:temporalResolutionRange']">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('measurementCapabilities:','temporalResolutionRange'))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 处理重返周期 -->
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field/swe:Quantity[@definition='urn:ogc:def:property:revisitingPeriod']">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('measurementCapabilities:','revisitingPeriod'))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 处理探测精度 -->
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field/swe:QuantityRange[@definition='urn:ogc:def:property:precisionRange']">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('measurementCapabilities:','precisionRange'))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!--处理光谱分辨率 -->
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field/swe:Quantity[@definition='urn:ogc:def:property:spectralResolution']">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('measurementCapabilities:','spectralResolution'))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 处理量化等级 -->
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field/swe:Quantity[@definition='urn:ogc:def:property:quantizationLevel']">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('measurementCapabilities:','quantizationLevel'))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 处理瞬时视场角 -->
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field/swe:Quantity[@definition='urn:ogc:def:property:IFOV']">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('measurementCapabilities:','IFOV'))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 处理星下点分辨率 -->
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field/swe:Quantity[@definition='urn:ogc:def:property:nadirSpatialResolution']">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('measurementCapabilities:','nadirSpatialResolution'))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 处理地面分辨率 -->
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field/swe:QuantityRange[@definition='urn:ogc:def:property:spatialResolutionRange']">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('measurementCapabilities:','spatialResolutionRange'))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 处理扫描传感器的波谱分布特征 ，这里坐下改动，将链接的改为每个slot的链接-->
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field[@name='波谱分布特征']/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']">
		<xsl:comment>
			<xsl:text>SpectrumDistributionFeature的处理</xsl:text>
		</xsl:comment>
		<xsl:apply-templates select="swe:field"></xsl:apply-templates>
	</xsl:template>
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field[@name='波谱分布特征']/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field">
		<!-- 波段名称 的处理 -->
		<xsl:if test="normalize-space(@name)!=''">
			<xsl:variable name="boduanname" select="@name" />
			<xsl:element name="rim:Slot" namespace="{$nsrim}">
				<xsl:attribute name="name"><xsl:value-of
					select="concat($slotPrefix,  concat('measurementCapabilities:SpectrumDistributionFeature:boundname:',$boduanname))" /></xsl:attribute>
				<xsl:attribute name="slotType"><xsl:value-of
					select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
				<xsl:element name="rim:ValueList" namespace="{$nsrim}">
					<xsl:element name="rim:Value" namespace="{$nsrim}">
						<xsl:value-of select="$boduanname" />
					</xsl:element>
				</xsl:element>
			</xsl:element>

			<xsl:if
				test="normalize-space(swe:DataRecord/swe:field[@name='波段类型']/swe:Category/swe:value)!=''">
				<!-- 处理波段类型 -->
				<xsl:variable name="bandCategory"
					select="swe:DataRecord/swe:field[@name='波段类型']/swe:Category/swe:value" />
				<xsl:element name="rim:Slot" namespace="{$nsrim}">
					<xsl:attribute name="name"><xsl:value-of
						select="concat($slotPrefix,  concat('measurementCapabilities:SpectrumDistributionFeature:bandCategory:',$boduanname))" /></xsl:attribute>
					<xsl:attribute name="slotType"><xsl:value-of
						select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
					<xsl:element name="rim:ValueList" namespace="{$nsrim}">
						<xsl:element name="rim:Value" namespace="{$nsrim}">
							<xsl:value-of select="$bandCategory" />
						</xsl:element>
					</xsl:element>
				</xsl:element>
			</xsl:if>
			<xsl:if
				test="normalize-space(swe:DataRecord/swe:field[@name='波段宽度']/swe:QuantityRange/swe:value)!=''">
				<!-- 处理波段宽度 -->
				<xsl:variable name="bandWidth"
					select="swe:DataRecord/swe:field[@name='波段宽度']/swe:QuantityRange/swe:value" />
				<xsl:element name="rim:Slot" namespace="{$nsrim}">
					<xsl:attribute name="name"><xsl:value-of
						select="concat($slotPrefix,  concat('measurementCapabilities:SpectrumDistributionFeature:bandWidth:',$boduanname))" /></xsl:attribute>
					<xsl:attribute name="slotType"><xsl:value-of
						select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
					<xsl:element name="rim:ValueList" namespace="{$nsrim}">
						<xsl:element name="rim:Value" namespace="{$nsrim}">
							<xsl:value-of select="$bandWidth" />
						</xsl:element>
					</xsl:element>
				</xsl:element>
			</xsl:if>
			<xsl:if
				test="normalize-space(swe:DataRecord/swe:field[@name='空间分辨率']/swe:Quantity/swe:value)!=''">
				<!-- 处理空间分辨率 -->
				<xsl:variable name="spatialResolution"
					select="swe:DataRecord/swe:field[@name='空间分辨率']/swe:Quantity/swe:value" />
				<xsl:element name="rim:Slot" namespace="{$nsrim}">
					<xsl:attribute name="name"><xsl:value-of
						select="concat($slotPrefix,  concat('measurementCapabilities:SpectrumDistributionFeature::spatialResolution',$boduanname))" /></xsl:attribute>
					<xsl:attribute name="slotType"><xsl:value-of
						select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
					<xsl:element name="rim:ValueList" namespace="{$nsrim}">
						<xsl:element name="rim:Value" namespace="{$nsrim}">
							<xsl:value-of select="$spatialResolution" />
						</xsl:element>
					</xsl:element>
				</xsl:element>
			</xsl:if>

			<xsl:if
				test="normalize-space(swe:DataRecord/swe:field[@name='辐射精度']/swe:Text/swe:value)!=''">
				<!-- 处理辐射经度 -->
				<xsl:variable name="bdfsjd"
					select="swe:DataRecord/swe:field[@name='辐射精度']/swe:Text/swe:value" />
				<xsl:element name="rim:Slot" namespace="{$nsrim}">
					<xsl:attribute name="name"><xsl:value-of
						select="concat($slotPrefix,  concat('measurementCapabilities:SpectrumDistributionFeature::radiationPrecision',$boduanname))" /></xsl:attribute>
					<xsl:attribute name="slotType"><xsl:value-of
						select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
					<xsl:element name="rim:ValueList" namespace="{$nsrim}">
						<xsl:element name="rim:Value" namespace="{$nsrim}">
							<xsl:value-of select="$bdfsjd" />
						</xsl:element>
					</xsl:element>
				</xsl:element>
			</xsl:if>
			<xsl:if
				test="normalize-space(swe:DataRecord/swe:field[@name='波段应用']/swe:Text/swe:value)!=''">
				<!-- 处理波段应用 -->
				<xsl:variable name="bandApplication"
					select="swe:DataRecord/swe:field[@name='波段应用']/swe:Text/swe:value" />
				<xsl:element name="rim:Slot" namespace="{$nsrim}">
					<xsl:attribute name="name"><xsl:value-of
						select="concat($slotPrefix,  concat('measurementCapabilities:SpectrumDistributionFeature::bandApplication',$boduanname))" /></xsl:attribute>
					<xsl:attribute name="slotType"><xsl:value-of
						select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
					<xsl:element name="rim:ValueList" namespace="{$nsrim}">
						<xsl:element name="rim:Value" namespace="{$nsrim}">
							<xsl:value-of select="$bandApplication" />
						</xsl:element>
					</xsl:element>
				</xsl:element>
			</xsl:if>
		</xsl:if>
	</xsl:template>
	<!-- 处理扫描传感器波段数 -->
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field/swe:Count[@definition='urn:ogc:def:property:bandsNumber']">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('measurementCapabilities:','bandsNumber'))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 处理扫描传感器波谱范围 -->
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field/swe:QuantityRange[@definition='urn:ogc:def:property:bandswidthRange']">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('measurementCapabilities:','bandswidthRange'))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 处理扫描传感器视场角 -->
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field/swe:Quantity[@definition='urn:ogc:def:property:fov']">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('measurementCapabilities:','fov'))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 处理扫描传感器的侧视角 -->
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field/swe:Quantity[@definition='urn:ogc:def:property:sideSwingAngle']">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('measurementCapabilities:','sideSwingAngle'))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>

	<!-- 处理雷达传感器光束模式的能力  -->
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field/swe:Category[@definition='urn:ogc:def:property:lightBeamMode']">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('measurementCapabilities:','lightBeamMode'))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>

	<!-- 处理雷达传感器波段频率的能力  -->
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field/swe:Quantity[@definition='urn:ogc:def:property:bandFrequency']">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('measurementCapabilities:','bandFrequency'))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>

	<!--处理雷达传感器极化方式的能力  -->
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field/swe:Category[@definition='urn:ogc:def:property:polarizationMode']">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('measurementCapabilities:','polarizationMode'))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 处理雷达传感器入射角度范围的能力 -->
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field/swe:QuantityRange[@definition='urn:ogc:def:property:incidentAngle']">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('measurementCapabilities:','incidentAngle'))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 处理雷达传感器自身成像幅宽范围的能力 -->
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field/swe:QuantityRange[@definition='urn:ogc:def:property:swathRange']">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('measurementCapabilities:','swathRange'))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 处理雷达传感器自身所设定的搭载波段 -->
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field/swe:Category[@definition='urn:ogc:def:property:bandsCategory']">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('measurementCapabilities:','bandsCategory'))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 处理雷达传感器其的自身所这顶的观测范围能力 -->
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field[@name='observedBBOX']">
		<xsl:apply-templates match="swe:field[@name='observedBBOX']/swe:Envelope"></xsl:apply-templates>
	</xsl:template>
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']/swe:field[@name='量程范围']">
		<xsl:apply-templates match="swe:field[@name='量程范围']/swe:Envelope"></xsl:apply-templates>
	</xsl:template>

	<!-- 新增的地理位置属性的处理 -->
	<xsl:template
		match="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:GeoPositionCapability']"
		mode="GeoPositionCapability">
		<xsl:apply-templates match="swe:field/swe:Text" />
	</xsl:template>
	<!-- 新增sml:capability的通信能力的处理 -->
	<xsl:template
		match="swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:communicationCapability']"
		mode="communicationCapability">
		<xsl:choose>
			<xsl:when test="swe:field/swe:Category">
				<xsl:apply-templates match="swe:field/swe:Category"></xsl:apply-templates>
			</xsl:when>
			<xsl:when test="swe:field/swe:Quantity">
				<xsl:apply-templates match="swe:field/swe:Quantity"></xsl:apply-templates>
			</xsl:when>
			<xsl:when test="swe:field/swe:Text">
				<xsl:apply-templates match="swe:field/swe:Text"></xsl:apply-templates>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
	<!-- 新增sml:capability的计算能力的处理 -->
	<xsl:template
		match="swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:computingCapability']"
		mode="computingCapability">
		<xsl:choose>
			<xsl:when test="swe:field/swe:Quantity">
				<xsl:apply-templates match="swe:field/swe:Quantity"></xsl:apply-templates>
			</xsl:when>
			<xsl:when test="swe:field/swe:Category">
				<xsl:apply-templates match="swe:field/swe:Category"></xsl:apply-templates>
			</xsl:when>
			<xsl:when test="swe:field/swe:QuantityRange">
				<xsl:apply-templates match="swe:field/swe:QuantityRange"></xsl:apply-templates>
			</xsl:when>
		</xsl:choose>
	</xsl:template>

	<xsl:template
		match="swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:communicationCapability']/swe:field/swe:Quantity">
		<xsl:variable name="tempnameStrs" select="@definition"></xsl:variable>
		<xsl:variable name="nameStrs"
			select="substring-after($tempnameStrs,'urn:ogc:def:property:')"></xsl:variable>
		<xsl:comment>
			<xsl:value-of select="$nameStrs"></xsl:value-of>
		</xsl:comment>
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('communicationCapabilities:',$nameStrs))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 解析communicationCapability的领域中的Category -->
	<xsl:template
		match="swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:communicationCapability']/swe:field/swe:Category">
		<xsl:variable name="tempnameStrs" select="@definition"></xsl:variable>
		<xsl:variable name="nameStrs"
			select="substring-after($tempnameStrs,'urn:ogc:def:property:')"></xsl:variable>
		<xsl:comment>
			<xsl:value-of select="$nameStrs"></xsl:value-of>
		</xsl:comment>
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('communicationCapabilities:',$nameStrs))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 解析communicationCapability领域中的Text部分  -->
	<xsl:template
		match="swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:communicationCapability']/swe:field/swe:Text">
		<xsl:variable name="tempnameStrs" select="@definition"></xsl:variable>
		<xsl:variable name="nameStrs"
			select="substring-after($tempnameStrs,'urn:ogc:def:property:')"></xsl:variable>
		<xsl:comment>
			<xsl:value-of select="$nameStrs"></xsl:value-of>
		</xsl:comment>
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('communicationCapabilities:',$nameStrs))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 传感器的计算能力属性信息的提取Quantity -->
	<xsl:template
		match="swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:computingCapability']/swe:field/swe:Quantity">
		<xsl:variable name="tempnameStrs" select="@definition"></xsl:variable>
		<xsl:variable name="nameStrs"
			select="substring-after($tempnameStrs,'urn:ogc:def:property:')"></xsl:variable>
		<xsl:comment>
			<xsl:value-of select="$nameStrs"></xsl:value-of>
		</xsl:comment>
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('computingCapability:',$nameStrs))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 传感器的计算能力属性信息的提取Category -->
	<xsl:template
		match="swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:computingCapability']/swe:field/swe:Category">
		<xsl:variable name="tempnameStrs" select="@definition"></xsl:variable>
		<xsl:variable name="nameStrs"
			select="substring-after($tempnameStrs,'urn:ogc:def:property:')"></xsl:variable>
		<xsl:comment>
			<xsl:value-of select="$nameStrs"></xsl:value-of>
		</xsl:comment>
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('computingCapability:',$nameStrs))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 传感器的计算能力属性信息的提取QuantityRange -->
	<xsl:template
		match="swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:computingCapability']/swe:field/swe:QuantityRange">
		<xsl:variable name="tempnameStrs" select="@definition"></xsl:variable>
		<xsl:variable name="nameStrs"
			select="substring-after($tempnameStrs,'urn:ogc:def:property:')"></xsl:variable>
		<xsl:comment>
			<xsl:value-of select="$nameStrs"></xsl:value-of>
		</xsl:comment>
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('computingCapability:',$nameStrs))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<xsl:template
		match="swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:GeoPositionCapability']/swe:field/swe:Text">
		<xsl:variable name="tempnameStrs" select="@definition"></xsl:variable>
		<xsl:variable name="nameStrs"
			select="substring-after($tempnameStrs,'urn:ogc:def:property:')"></xsl:variable>
		<xsl:comment>
			<xsl:value-of select="$nameStrs"></xsl:value-of>
		</xsl:comment>
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('GeoPositionCapabilities:property:',$nameStrs))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 处理物理特性中的类型为Boolean的性质 -->
	<xsl:template
		match="swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:physicalProperties']/swe:field/swe:Boolean">
		<xsl:variable name="tempnameStrs" select="@definition"></xsl:variable>
		<xsl:variable name="nameStrs"
			select="substring-after($tempnameStrs,'urn:ogc:def:property:')"></xsl:variable>
		<xsl:comment>
			<xsl:value-of select="$nameStrs"></xsl:value-of>
		</xsl:comment>
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('physicalProperties:property:',$nameStrs))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 处理物理特性中的类型为Time的性质 -->
	<xsl:template
		match="swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:physicalProperties']/swe:field/swe:Time">
		<xsl:variable name="tempnameStrs" select="@definition"></xsl:variable>
		<xsl:variable name="nameStrs"
			select="substring-after($tempnameStrs,'urn:ogc:def:property:')"></xsl:variable>
		<xsl:comment>
			<xsl:value-of select="$nameStrs"></xsl:value-of>
		</xsl:comment>
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('physicalProperties:property:',$nameStrs))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 处理物理特性中的类型为Quantity的性质 -->
	<xsl:template
		match="swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:physicalProperties']/swe:field/swe:Quantity">
		<xsl:variable name="tempnameStrs" select="@definition"></xsl:variable>
		<xsl:variable name="nameStrs"
			select="substring-after($tempnameStrs,'urn:ogc:def:property:')"></xsl:variable>
		<xsl:comment>
			<xsl:value-of select="$nameStrs"></xsl:value-of>
		</xsl:comment>
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('physicalProperties:property:',$nameStrs))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 处理物理特性中的类型为swe:Category的性质 -->
	<xsl:template
		match="swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:physicalProperties']/swe:field/swe:Category">
		<xsl:variable name="tempnameStrs" select="@definition"></xsl:variable>
		<xsl:variable name="nameStrs"
			select="substring-after($tempnameStrs,'urn:ogc:def:property:')"></xsl:variable>
		<xsl:comment>
			<xsl:value-of select="$nameStrs"></xsl:value-of>
		</xsl:comment>
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('physicalProperties:property:',$nameStrs))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 处理物理特性中的类型为swe:QuantityRange的性质 -->
	<xsl:template
		match="swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:physicalProperties']/swe:field/swe:QuantityRange">
		<xsl:variable name="tempnameStrs" select="@definition"></xsl:variable>
		<xsl:variable name="nameStrs"
			select="substring-after($tempnameStrs,'urn:ogc:def:property:')"></xsl:variable>
		<xsl:comment>
			<xsl:value-of select="$nameStrs"></xsl:value-of>
		</xsl:comment>
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('physicalProperties:property:',$nameStrs))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 处理物理特性中的类型为Text的性质 -->
	<xsl:template
		match="swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:physicalProperties']/swe:field/swe:Text">
		<xsl:variable name="tempnameStrs" select="@definition"></xsl:variable>
		<xsl:variable name="nameStrs"
			select="substring-after($tempnameStrs,'urn:ogc:def:property:')"></xsl:variable>
		<xsl:comment>
			<xsl:value-of select="$nameStrs"></xsl:value-of>
		</xsl:comment>
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('physicalProperties:property:',$nameStrs))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 处理物理属性中的类型为Count的性质 -->
	<xsl:template
		match="swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:physicalProperties']/swe:field/swe:Count">
		<xsl:variable name="tempnameStrs" select="@definition"></xsl:variable>
		<xsl:variable name="nameStrs"
			select="substring-after($tempnameStrs,'urn:ogc:def:property:')"></xsl:variable>
		<xsl:comment>
			<xsl:value-of select="$nameStrs"></xsl:value-of>
		</xsl:comment>
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix,  concat('physicalProperties:property:',$nameStrs))" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- INPUTS -->
	<xsl:template match="sml:inputs/sml:InputList">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix, 'Inputs')" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:apply-templates select="sml:input/swe:ObservableProperty" />
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<xsl:template match="swe:ObservableProperty">
		<xsl:element name="rim:Value" namespace="{$nsrim}">
			<xsl:value-of select="@definition" />
		</xsl:element>
	</xsl:template>
	<!-- sml:associatedSensorName -->
	<!-- OUTPUTS -->
	<xsl:template match="sml:outputs/sml:OutputList">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix, 'Outputs')" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:if test="sml:output/swe:Quantity">
					<xsl:apply-templates select="sml:output/swe:Quantity" />
				</xsl:if>
				<!-- <xsl:apply-templates select="sml:output"></xsl:apply-templates> -->
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<xsl:template match="sml:output">
		<xsl:if test="@definition">
			<xsl:element name="rim:Value" namespace="{$nsrim}">
				<xsl:value-of select="@definition" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@xlink:href">
			<xsl:element name="rim:Value" namespace="{$nsrim}">
				<xsl:value-of select="@xlink:href" />
			</xsl:element>
		</xsl:if>
	</xsl:template>
	<xsl:template match="sml:output/swe:Quantity">
		<xsl:element name="rim:Value" namespace="{$nsrim}">
			<xsl:value-of select="@definition" />
		</xsl:element>
		<xsl:element name="rim:Value" namespace="{$nsrim}">
			<xsl:value-of select="@xlink:href" />
		</xsl:element>
	</xsl:template>

	<!-- POSITION/LOCATION -->
	<xsl:template match="sml:position/swe:Position">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix, 'Location')" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($iso19107DataTypePrefix, 'GM_Point')" /></xsl:attribute>
			<xsl:element name="wrs:ValueList" namespace="{$nswrs}">
				<xsl:element name="wrs:AnyValue" namespace="{$nswrs}">
					<!--
						workaround for usage of <spatialReferenceFrame> in SensorML: If a
						Component refers to a locally defined spatial reference frame,
						then use the position of the enframing System. The test is done by
						checking for a valid EPSG code, i.e. if the attribute contains
						'EPSG:'.
					-->
					<!--
						xsl:message元素可向输出写一条消息。该元素主要用于报告错误可选。 "yes"：在消息写入输出后，终止处理。
						"no"：在消息写入输出后，继续进行处理。默认是 "no"。
					-->
					<xsl:if test="$debugOn">
						<xsl:message terminate="no">
							<xsl:text> Testing referenceFrame attribute: </xsl:text>
							<xsl:value-of select="@referenceFrame"></xsl:value-of>
							<xsl:text> -- </xsl:text>
							<xsl:value-of select="starts-with(@referenceFrame, $epsgUrnPrefix)"></xsl:value-of>
						</xsl:message>
					</xsl:if>
					<xsl:choose>
						<!--
							情况1：如果referenceFrame如果是以$epsgUrnPrefix开头，则就以第一种方式进行
						-->
						<xsl:when test="starts-with(@referenceFrame, $epsgUrnPrefix)">
							<xsl:element name="gml:Point" namespace="{$nsgml}">
								<xsl:attribute name="gml:id">                                                                          <xsl:value-of
									select="generate-id(swe:location)" />                                                                  </xsl:attribute>
								<xsl:attribute name="srsName">                                                                          <xsl:value-of
									select="@referenceFrame" />                                                                  </xsl:attribute>
								<xsl:call-template name="generateGmlPos">
									<xsl:with-param name="swePosition" select="." />
								</xsl:call-template>
							</xsl:element>
						</xsl:when>
						<!--
							情况2：如果referenceFrame不以$epsgUrnPrefxi开头，按照下面方式进行，即按照传感器安装的system的position来设置
						-->
						<xsl:otherwise>
							<xsl:comment>
								<xsl:text>Could not detect valid EPSG code in swe:Position/@referenceSystem, falling back to ancestor sml:System position definition.</xsl:text>
							</xsl:comment>
							<xsl:element name="gml:Point" namespace="{$nsgml}">
								<xsl:variable name="ancestorPosition"
									select="ancestor::sml:System/sml:position/swe:Position" />
								<xsl:attribute name="gml:id">                                                                          <xsl:value-of
									select="generate-id(swe:location)" />                                                                  </xsl:attribute>
								<xsl:attribute name="srsName">                                                                          <xsl:value-of
									select="$ancestorPosition/@referenceFrame" />                                                                  </xsl:attribute>
								<xsl:call-template name="generateGmlPos">
									<xsl:with-param name="swePosition" select="$ancestorPosition" />
								</xsl:call-template>
							</xsl:element>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<xsl:template name="generateGmlPos">
		<xsl:param name="swePosition" />
		<xsl:element name="gml:pos" namespace="{$nsgml}">
			<xsl:value-of
				select="concat($swePosition/swe:location/swe:Vector/swe:coordinate/swe:Quantity[@axisID='y']/swe:value, ' ', $swePosition/swe:location/swe:Vector/swe:coordinate/swe:Quantity[@axisID='x']/swe:value,' ',$swePosition/swe:location/swe:Vector/swe:coordinate/swe:Quantity[@axisID='z']/swe:value)" />
		</xsl:element>
		<!--
			<xsl:if
			test="$swePosition/swe:location/swe:Vector/swe:coordinate/swe:Quantity[@axisID='z']">
			按照注释的形式进行处理 <xsl:comment> <xsl:text>z-value '</xsl:text>
			<xsl:value-of
			select="$swePosition/swe:location/swe:Vector/swe:coordinate/swe:Quantity[@axisID='z']/swe:value"
			/> <xsl:text>' omitted!</xsl:text> </xsl:comment> </xsl:if>
		-->
	</xsl:template>

	<!-- VALID TIME 这里需要增加一个关于TimeInstant的处理（即时间点的处理）-->
	<xsl:template match="sml:validTime">
		<xsl:choose>
			<!--存在问题，并不能读取出gml:TimePeriod的属性值,这个可以改成TimePeroid试试 -->
			<xsl:when test="count(gml:TimePeriod) > 0">
				<xsl:apply-templates />
			</xsl:when>

			<!-- 自己增加的 gml:TimeInstant属性， yxl -->
			<xsl:when test="count(gml:TimeInstant)>0">
				<xsl:apply-templates />
			</xsl:when>
			<!-- 自己增加的gml:TimeInstant属性 ，end -->

			<xsl:otherwise>
				<xsl:comment>
					<xsl:text>
					"gml:TimePeriod" in sml:validTime not found! Maybe I cannot match
					the gml namespace?
					</xsl:text>
				</xsl:comment>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template match="gml:TimePeriod">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix, 'ValidTimeBegin')" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'DateTime')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="gml:beginPosition" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of
				select="concat($slotPrefix, 'ValidTimeEnd')" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'DateTime')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="gml:endPosition" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<xsl:strip-space elements="*" />
	<!-- System -->
	<xsl:template match="sml:System" mode="extrinsic-object">
		<xsl:variable name="UNIQUE_ID">
			<xsl:value-of
				select="sml:identification/sml:IdentifierList/sml:identifier/sml:Term[@definition='urn:ogc:def:identifier:OGC::uniqueID' or @definition='urn:ogc:def:identifier:OGC:1.0:uniqueID']/sml:value" />
		</xsl:variable>
		<xsl:element name="wrs:ExtrinsicObject" namespace="{$nswrs}">

			<!-- 设置多媒体的属性为application/xml，直接设置 -->
			<xsl:attribute name="mimeType">application/xml</xsl:attribute>
			<xsl:attribute name="objectType"><xsl:value-of
				select="$fixedObjectTypeSystem" /></xsl:attribute>
			<xsl:attribute name="lid"><xsl:value-of select="$UNIQUE_ID" /></xsl:attribute>
			<xsl:attribute name="id"><xsl:value-of select="$UNIQUE_ID" /></xsl:attribute>
			<xsl:comment>
				<xsl:text>
					*******************传感器关键字的转换***************************
					</xsl:text>
			</xsl:comment>
			<xsl:apply-templates select="sml:keywords" />
			<xsl:comment>
				<xsl:text>
					*******************传感器类型的转换***************************
					</xsl:text>
			</xsl:comment>
			<xsl:if test="@gml:id">
				<xsl:element name="rim:Slot" namespace="{$nsrim}">
					<xsl:attribute name="name"><xsl:value-of
						select="concat($slotPrefix,'SensorType')" /></xsl:attribute>
					<xsl:attribute name="slotType"><xsl:value-of
						select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
					<xsl:element name="rim:ValueList" namespace="{$nsrim}">
						<xsl:element name="rim:Value" namespace="{$nsrim}">
							<xsl:value-of select="@gml:id" />
						</xsl:element>
					</xsl:element>
				</xsl:element>
			</xsl:if>
			<xsl:comment>
				<xsl:text>
					*******************传感器物理工作时间的转换***************************
					</xsl:text>
			</xsl:comment>
			<xsl:apply-templates select="sml:validTime" />
			<xsl:comment>
				<xsl:text>
					*******************传感器物理属性的转换***************************
					</xsl:text>
			</xsl:comment>
			<xsl:apply-templates select="sml:characteristics"
				mode="physicalProperties" />
			<xsl:comment>
				<xsl:text>
					*******************传感器计算能力的转换***************************
					</xsl:text>
			</xsl:comment>
			<xsl:apply-templates
				select="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:computingCapability']"
				mode="computingCapability" />
			<xsl:comment>
				<xsl:text>
					*******************传感器通信能力的转换***************************
					</xsl:text>
			</xsl:comment>
			<xsl:apply-templates
				select="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:communicationCapability']"
				mode="communicationCapability" />

			<xsl:comment>
				<xsl:text>
					*******************传感器测量能力的转换***************************
					</xsl:text>
			</xsl:comment>
			<xsl:apply-templates
				select="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:measurementCapabilities']"
				mode="measurementCapabilities"></xsl:apply-templates>
			<xsl:comment>
				<xsl:text>
					*******************传感器获取目标区域范围的转换********************
					</xsl:text>
			</xsl:comment>
			<xsl:apply-templates
				select="sml:capabilities/swe:DataRecord[@definition='urn:ogc:def:property:Liesmars:GeoPositionCapability']"
				mode="GeoPositionCapability" />
			<xsl:comment>
				<xsl:text>
					*******************传感器位置信息的转换***************************
					</xsl:text>
			</xsl:comment>
			<xsl:apply-templates select="sml:position" />
			<xsl:comment>
				<xsl:text>
					*******************传感器输入参数的转换***************************
					</xsl:text>
			</xsl:comment>
			<xsl:apply-templates select="sml:inputs" />
			<xsl:comment>
				<xsl:text>
					*******************传感器输出参数的转换***************************
					</xsl:text>
			</xsl:comment>
			<xsl:apply-templates select="sml:outputs" />
			<xsl:comment>
				<xsl:text>
					*******************传感器输出所有标识符信息的转换***************************
					</xsl:text>
			</xsl:comment>
			<xsl:apply-templates select="sml:identification" />


			<!-- 新增interfaces的解析卫星平台 -->
			<xsl:comment>
				<xsl:text>
					****************解析传感器的服务接口部分******************************
					</xsl:text>
			</xsl:comment>
			<xsl:apply-templates select="sml:interfaces" />
			<xsl:comment>
				<xsl:text>
					****************解析传感器部件信息****************************
					</xsl:text>
			</xsl:comment>

			<xsl:apply-templates select="sml:components/sml:ComponentList/sml:component" />
			<!--
				<xsl:comment> <xsl:text>
				********************雷达传感器的服务信息******************** </xsl:text>
				</xsl:comment> 针对雷达传感器的转换 YXL <xsl:apply-templates
				select="sml:interfaces/sml:InterfaceList/sml:interface/sml:InterfaceDefinition/sml:serviceLayer/swe:DataRecord[@definition='urn:ogc:def:interface:OGC:1.0:SWEServiceInterface']"
				model="leidasensor" />
			-->
			<xsl:comment>
				<xsl:text>******** 传感器分类信息 ********</xsl:text>
			</xsl:comment>
			<xsl:apply-templates select="sml:classification"
				mode="classification">
				<xsl:with-param name="CLASSIFIED_OBJECT_ID">
					<xsl:value-of select="$UNIQUE_ID" />
				</xsl:with-param>
			</xsl:apply-templates>
			<xsl:choose>
				<xsl:when test="count(gml:description) > 0">
					<xsl:apply-templates select="gml:description" />
				</xsl:when>
				<xsl:otherwise>
					<xsl:comment>
						<xsl:value-of select="$message.gmlError" />
					</xsl:comment>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:element>
	</xsl:template>
	<xsl:template match="sml:connections/sml:ConnectionList/sml:connection">
		<xsl:element name="rim:Association" namespace="{$nsrim}">
			<xsl:variable name="slotvalue" select="./@name"></xsl:variable>
			<xsl:variable name="sourceoption" select="sml:Link/sml:source/@ref"></xsl:variable>
			<xsl:variable name="targetoption" select="sml:Link/sml:destination/@ref"></xsl:variable>
			<xsl:attribute name="id">
				<xsl:value-of
				select="concat($idPrefix,  $idMiddlefixAssociation, generate-id())" />
			</xsl:attribute>
			<xsl:choose>
				<xsl:when test="contains($sourceoption,'input')">
					<xsl:attribute name="associationType">
					<xsl:value-of select="$inputconnectionAssociationType" />
				</xsl:attribute>
				</xsl:when>
				<xsl:when test="contains($targetoption,'input')">
					<xsl:attribute name="associationType">
					<xsl:value-of select="$inputconnectionAssociationType" />
				</xsl:attribute>
				</xsl:when>
				<xsl:when test="contains($sourceoption,'output')">
					<xsl:attribute name="associationType">
					<xsl:value-of select="$outputconnectionAssociationType" />
				</xsl:attribute>
				</xsl:when>
				<xsl:when test="contains($sourceoption,'output')">
					<xsl:attribute name="associationType">
					<xsl:value-of select="$outputconnectionAssociationType" />
				</xsl:attribute>
				</xsl:when>
			</xsl:choose>
			<xsl:if test="contains($sourceoption,'this')">
				<xsl:variable name="targetcomponent"
					select="substring-before($targetoption,'/')"></xsl:variable>
				<xsl:attribute name="sourceObject">
					<xsl:value-of
					select="//sml:member/sml:System/sml:identification/sml:IdentifierList/sml:identifier/sml:Term[@definition='urn:ogc:def:identifier:OGC::uniqueID' or @definition='urn:ogc:def:identifier:OGC:1.0:uniqueID']/sml:value" />
				</xsl:attribute>
				<xsl:attribute name="targetObject">
					<xsl:value-of
					select="//sml:member/sml:System/sml:components/sml:ComponentList/sml:component[@name=$targetcomponent]/@xlink:href"></xsl:value-of>
				</xsl:attribute>
			</xsl:if>
			<xsl:if test="contains($targetoption,'this')">
				<xsl:variable name="sourcecomponent"
					select="substring-before($sourceoption,'/')"></xsl:variable>
				<xsl:attribute name="sourceObject">
					<xsl:value-of
					select="//sml:member/sml:System/sml:components/sml:ComponentList/sml:component[@name=$sourcecomponent]/@xlink:href"></xsl:value-of>
				</xsl:attribute>
				<xsl:attribute name="targetObject">
					<xsl:value-of
					select="//sml:member/sml:System/sml:identification/sml:IdentifierList/sml:identifier/sml:Term[@definition='urn:ogc:def:identifier:OGC::uniqueID' or @definition='urn:ogc:def:identifier:OGC:1.0:uniqueID']/sml:value" />
				</xsl:attribute>
			</xsl:if>
			<xsl:element name="rim:Slot" namespace="{$nsrim}">

				<xsl:if test="string-length(normalize-space($slotvalue))>0">
					<xsl:attribute name="name"><xsl:value-of
						select="$slotvalue" /></xsl:attribute>
				</xsl:if>
				<xsl:element name="rim:ValueList" namespace="{$nsrim}">
					<xsl:element name="rim:Value" namespace="{$nsrim}">
						<xsl:value-of select="sml:Link/sml:source/@ref" />
					</xsl:element>
					<xsl:element name="rim:Value" namespace="{$nsrim}">
						<xsl:value-of select="sml:Link/sml:destination/@ref" />
					</xsl:element>
				</xsl:element>
			</xsl:element>
		</xsl:element>

	</xsl:template>
	<xsl:template
		match="sml:interfaces/sml:InterfaceList/sml:interface/sml:InterfaceDefinition/sml:serviceLayer/swe:DataRecord[@definition='urn:ogc:def:interface:OGC:1.0:SWEServiceInterface']"
		model="leidasensor">
		<!--将其服务访问地址包装为一个slot -->
		<xsl:apply-templates
			select="swe:field[@name='urn:ogc:def:interface:OGC:1.0:ServiceURL']"
			model="leidasensor" />
		<!-- 将服务的类型包装为一个slot -->
		<xsl:apply-templates
			select="swe:field[@name='urn:ogc:def:interface:OGC:1.0:ServiceType']"
			model="leidasensor" />
		<!-- 将服务的ServiceSpecificSensorID包装为slot -->
		<xsl:apply-templates
			select="swe:field[@name='urn:ogc:def:interface:OGC:1.0:ServiceSpecificSensorID']"
			model="leidasensor" />
	</xsl:template>
	<!--将其服务访问地址包装为一个slot leidaisensor-->
	<xsl:template
		match="sml:interfaces/sml:InterfaceList/sml:interface/sml:InterfaceDefinition/sml:serviceLayer/swe:DataRecord[@definition='urn:ogc:def:interface:OGC:1.0:SWEServiceInterface']/swe:field[@name='urn:ogc:def:interface:OGC:1.0:ServiceURL']"
		model="leidasensor">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of select="@name" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:Text/swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 将服务的类型包装为一个slot -->
	<xsl:template
		match="sml:interfaces/sml:InterfaceList/sml:interface/sml:InterfaceDefinition/sml:serviceLayer/swe:DataRecord[@definition='urn:ogc:def:interface:OGC:1.0:SWEServiceInterface']/swe:field[@name='urn:ogc:def:interface:OGC:1.0:ServiceType']"
		model="leidasensor">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of select="@name" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:Text/swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 将服务的ServiceSpecificSensorID包装为slot -->
	<xsl:template
		match="sml:interfaces/sml:InterfaceList/sml:interface/sml:InterfaceDefinition/sml:serviceLayer/swe:DataRecord[@definition='urn:ogc:def:interface:OGC:1.0:SWEServiceInterface']/swe:field[@name='urn:ogc:def:interface:OGC:1.0:ServiceSpecificSensorID']"
		model="leidasensor">
		<xsl:element name="rim:Slot" namespace="{$nsrim}">
			<xsl:attribute name="name"><xsl:value-of select="@name" /></xsl:attribute>
			<xsl:attribute name="slotType"><xsl:value-of
				select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
			<xsl:element name="rim:ValueList" namespace="{$nsrim}">
				<xsl:element name="rim:Value" namespace="{$nsrim}">
					<xsl:value-of select="swe:Text/swe:value" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<xsl:template match="sml:System" mode="classification-association">
		<xsl:variable name="UNIQUE_ID">
			<xsl:value-of
				select="sml:identification/sml:IdentifierList/sml:identifier/sml:Term[@definition='urn:ogc:def:identifier:OGC::uniqueID' or @definition='urn:ogc:def:identifier:OGC:1.0:uniqueID']/sml:value" />
		</xsl:variable>
		<xsl:comment>
			*******************传感器分类节点信息***************************
		</xsl:comment>
		<xsl:apply-templates select="sml:classification"
			mode="classificationNode">
			<xsl:with-param name="CLASSIFIED_OBJECT_ID">
				<xsl:value-of select="$UNIQUE_ID" />
			</xsl:with-param>
		</xsl:apply-templates>
		<xsl:comment>
			<xsl:text>********传感器组件信息 ********</xsl:text>
		</xsl:comment>
		<xsl:apply-templates select="sml:components"
			mode="ComposedOf-association">
			<xsl:with-param name="SOURCE_OBJECT_ID">
				<xsl:value-of select="$UNIQUE_ID" />
			</xsl:with-param>
		</xsl:apply-templates>
		<!--增加对connection的处理 -->
		<xsl:comment>
			<xsl:text>
					************解析传感器的参数关联部分**************
					</xsl:text>
		</xsl:comment>
		<xsl:apply-templates select="sml:connections"></xsl:apply-templates>
		<xsl:comment>
			<xsl:text>********传感器服务接口信息 ********</xsl:text>
		</xsl:comment>
		<xsl:apply-templates select="sml:interfaces"
			mode="AccessibleThrough-association">
			<xsl:with-param name="SOURCE_OBJECT_ID">
				<xsl:value-of select="$UNIQUE_ID" />
			</xsl:with-param>
		</xsl:apply-templates>

	</xsl:template>

	<!--
		xsl:output 表示说明的是不依靠XSLT处理器直接对输出结果进行缩进，而是通过具体的代码实现，
		这里指定了xml格式为xml，版本是1.0，编码是UTF-8
	-->
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
	<xsl:variable name="inputconnectionAssociationType"
		select="'urn:ogc:def:associationType::OGC-CSW-ebRIM-Sensor::inputconnection'" />
	<xsl:variable name="outputconnectionAssociationType"
		select="'urn:ogc:def:associationType::OGC-CSW-ebRIM-Sensor::outputconnection'" />
	<!-- CLASSIFICATION SCHEMES -->

	<xsl:variable name="classificationSchemeId.SystemTypes"
		select="'urn:ogc:def:classificationScheme:OGC-CSW-ebRIM-Sensor::SystemTypes'" />
	<xsl:variable name="classificationSchemeId.oritTypes"
		select="'urn:ogc:def:classificationScheme:OGC-CSW-ebRIM-Sensor::OritTypes'"></xsl:variable>
	<xsl:variable name="classificationSchemeId.newAddeds"
		select="'urn:ogc:def:classificationScheme:OGC-CSW-ebRIM-Sensor::newAddeds'"></xsl:variable>
	<xsl:variable name="classificationSchemeId.ISO19119Services"
		select="'urn:ogc:def:ebRIM-ClassificationScheme:ISO-19119:2005:Services'" />
	<!--
		/************************************************** 三种分类，
		sensorType,cannerType,platformType，stationLevel和IntendedAplication三种分类种类
		自己定义的 yxl ***********************************************/
	-->
	<xsl:variable name="classificationSchemeId.sensorType"
		select="'urn:ogc:def:classificationScheme:OGC-CSW-ebRIM-Sensor::sensorType'" />
	<xsl:variable name="classificationSchemeId.scannerType"
		select="'urn:ogc:def:classificationScheme:OGC-CSW-ebRIM-Sensor::scannerType'" />
	<xsl:variable name="classificationSchemeId.platformType"
		select="'urn:ogc:def:classificationScheme:OGC-CSW-ebRIM-Sensor::platformType'" />
	<xsl:variable name="classificationSchemeId.stationLevel"
		select="'urn:ogc:def:classificationScheme:OGC-CSW-ebRIM-Sensor::stationLevel'" />
	<xsl:variable name="classificationSchemeId.sharingLevel"
		select="'urn:ogc:def:classificationScheme:OGC-CSW-ebRIM-Sensor::sharingLevel'" />
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
	<!-- SERVICE TYPES -->
	<xsl:template name="deduceServiceType">
		<xsl:param name="type" />
		<xsl:choose>
			<!--
				如果当前的前缀名为urn:ogc:serviceType的话，就选择当前节点的值就可以，从这里区分出SOS,SPS,WFS,WMS
				,SES,SAS,WNS,CAT数种服务
			-->
			<xsl:when test="starts-with($type, 'urn:ogc:serviceType:')">
				<xsl:value-of select="." />
			</xsl:when>
			<!-- Version number handling in urns ist NOT implemented! -->
			<xsl:when
				test="substring($type, (string-length($type) - string-length('SOS')) + 1) = 'SOS'">
				<xsl:value-of select="'urn:ogc:serviceType:SensorObservationService'" />
			</xsl:when>
			<xsl:when
				test="substring($type, (string-length($type) - string-length('SPS')) + 1) = 'SPS'">
				<xsl:value-of select="'urn:ogc:serviceType:SensorPlanningService'" />
			</xsl:when>
			<xsl:when
				test="substring($type, (string-length($type) - string-length('WFS')) + 1) = 'WFS'">
				<xsl:value-of select="'urn:ogc:serviceType:WebFeatureService'" />
			</xsl:when>
			<xsl:when
				test="substring($type, (string-length($type) - string-length('WMS')) + 1) = 'WMS'">
				<xsl:value-of select="'urn:ogc:serviceType:WebMapService'" />
			</xsl:when>
			<xsl:when
				test="substring($type, (string-length($type) - string-length('SES')) + 1) = 'SES'">
				<xsl:value-of select="'urn:ogc:serviceType:SensorAlertService'" />
			</xsl:when>
			<xsl:when
				test="substring($type, (string-length($type) - string-length('SAS')) + 1) = 'SAS'">
				<xsl:value-of select="'urn:ogc:serviceType:SensorAlertService'" />
			</xsl:when>
			<xsl:when
				test="substring($type, (string-length($type) - string-length('WNS')) + 1) = 'WNS'">
				<xsl:value-of select="'urn:ogc:serviceType:WebNotificationService'" />
			</xsl:when>
			<xsl:when
				test="substring($type, (string-length($type) - string-length('CAT')) + 1) = 'CAT'">
				<xsl:value-of select="'urn:ogc:serviceType:CatalogueService'" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="'Error: Unknown service type!'" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<!-- 在这里判断了各种服务所属的节点，这里是不是可以再新增一些判断服务所属的父节点的东西 -->
	<xsl:template name="deduceServiceParent">
		<xsl:param name="type" />
		<xsl:choose>
			<xsl:when test="not(starts-with($type, 'urn:ogc:serviceType:'))">
				<xsl:value-of select="'ERROR: Not given a service id!'" />
			</xsl:when>              <!-- Version number handling in urns ist NOT implemented! -->
			<xsl:when test="$type = 'urn:ogc:serviceType:SensorObservationService'">
				<xsl:value-of
					select="'urn:ogc:def:ebRIM-ClassificationScheme:ISO-19119:2005:Services:InfoManagement'" />
			</xsl:when>
			<xsl:when test="$type = 'urn:ogc:serviceType:SensorPlanningService'">
				<xsl:value-of
					select="'urn:ogc:def:ebRIM-ClassificationScheme:ISO-19119:2005:Services:SystemManagement'" />
			</xsl:when>
			<xsl:when test="$type = 'urn:ogc:serviceType:SensorAlertService'">
				<xsl:value-of
					select="'urn:ogc:def:ebRIM-ClassificationScheme:ISO-19119:2005:Services:Subscription'" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of
					select="'ERROR: No parent deduction rule found! Check for rules in file SensorML-to-ebRIM_variables.xsl.'" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:transform>  