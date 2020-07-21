<?xml version="1.0" encoding="UTF-8"?>
<xsl:transform xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:rim="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0" xmlns:gml="http://www.opengis.net/gml"
	xmlns:swe="http://www.opengis.net/swe/1.0.1" xmlns:wrs="http://www.opengis.net/cat/wrs/1.0"
	xmlns:sml="http://www.opengis.net/sensorML/1.0.1" version="2.0"
	xsi:schemaLocation="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0 http://docs.oasis-open.org/regrep/v3.0/schema/rim.xsd     http://www.opengis.net/cat/wrs/1.0 http://schemas.opengis.net/csw/2.0.2/profiles/ebrim/1.0/csw-ebrim.xsd">            <!-- imports -->
	<xsl:template match="text()|@*" />
	<xsl:strip-space elements="*" />
	<xsl:template match="sml:ProcessMethod">
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
					<xsl:attribute name="id">
				<xsl:value-of
						select="sml:identification/sml:IdentifierList/sml:identifier/sml:Term[@definition='urn:ogc:def:property:OGC:processID']/sml:value"></xsl:value-of>
			</xsl:attribute>
					<xsl:attribute name="lid">
				<xsl:value-of
						select="sml:identification/sml:IdentifierList/sml:identifier/sml:Term[@definition='urn:ogc:def:property:OGC:processID']/sml:value"></xsl:value-of>
			</xsl:attribute>
					<xsl:attribute name="mimeType">application/xml</xsl:attribute>
					<xsl:attribute name="objectType">
				<xsl:value-of select="$fixedObjectTypeProcessMethod"></xsl:value-of>
			</xsl:attribute>
					<xsl:comment>
						<xsl:text>----------process type----------</xsl:text>
					</xsl:comment>
					<xsl:element name="rim:Slot" namespace="{$nsrim}">
						<xsl:attribute name="name"><xsl:value-of
							select="concat($slotPrefix,'SensorType')" /></xsl:attribute>
						<xsl:attribute name="slotType"><xsl:value-of
							select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
						<xsl:element name="rim:ValueList" namespace="{$nsrim}">
							<xsl:element name="rim:Value" namespace="{$nsrim}">
								processMethod
							</xsl:element>
						</xsl:element>
					</xsl:element>
					<xsl:apply-templates select="sml:documentation/sml:Document"></xsl:apply-templates>
					<xsl:apply-templates
						select="sml:classification/sml:ClassifierList/sml:classifier"
						mode="extrinsicObject">
					</xsl:apply-templates>
					<xsl:apply-templates select="sml:rules"></xsl:apply-templates>
					<xsl:apply-templates select="sml:algorithm"></xsl:apply-templates>
					<xsl:apply-templates select="sml:implementation"></xsl:apply-templates>
				</xsl:element>
				<xsl:apply-templates
					select="sml:classification/sml:ClassifierList/sml:classifier" mode="association-classification">
				</xsl:apply-templates>
				<xsl:comment>
					<xsl:text>---------contact---------</xsl:text>
				</xsl:comment>
				<xsl:apply-templates select="sml:contact"></xsl:apply-templates>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- 解析document -->
	<xsl:template match="sml:documentation/sml:Document">
		<xsl:comment>
			<xsl:text>----------document----------</xsl:text>
		</xsl:comment>
		<!-- 解析 document的描述资源-->
		<xsl:if test="normalize-space(gml:description)!=''">
			<xsl:element name="rim:Slot" namespace="{$nsrim}">
				<xsl:attribute name="name"><xsl:value-of
					select="concat($slotPrefix,'processMethod:DocumentDescription')" /></xsl:attribute>
				<xsl:attribute name="slotType"><xsl:value-of
					select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
				<xsl:element name="rim:ValueList" namespace="{$nsrim}">
					<xsl:element name="rim:Value" namespace="{$nsrim}">
						<xsl:value-of select="gml:description"></xsl:value-of>
					</xsl:element>
				</xsl:element>
			</xsl:element>
		</xsl:if>
		<!-- 解析document的在线资源 -->
		<xsl:if test="normalize-space(sml:onlineResource/@xlink:href)!=''">
			<xsl:element name="rim:Slot" namespace="{$nsrim}">
				<xsl:attribute name="name"><xsl:value-of
					select="concat($slotPrefix,'processMethod:DocumentOnlineResource')" /></xsl:attribute>
				<xsl:attribute name="slotType"><xsl:value-of
					select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
				<xsl:element name="rim:ValueList" namespace="{$nsrim}">
					<xsl:element name="rim:Value" namespace="{$nsrim}">
						<xsl:value-of select="sml:onlineResource/@xlink:href"></xsl:value-of>
					</xsl:element>
				</xsl:element>
			</xsl:element>
		</xsl:if>
	</xsl:template>

	<!--解析 sml:implementation部分  -->
	<xsl:template match="sml:implementation">
		<xsl:comment>
			<xsl:text>---------Implementations---------</xsl:text>
		</xsl:comment>
		<xsl:if test="normalize-space(sml:ImplementationCode/@language)!=''">
			<!-- 解析方法实现 的语言-->
			<xsl:element name="rim:Slot" namespace="{$nsrim}">
				<xsl:attribute name="name">
				<xsl:value-of
					select="concat($slotPrefix,'processmethod:implementation:impleLanguage')"></xsl:value-of>
			</xsl:attribute>
				<xsl:attribute name="slotType"><xsl:value-of
					select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
				<xsl:element name="rim:ValueList" namespace="{$nsrim}">
					<xsl:element name="rim:Value" namespace="{$nsrim}">
						<xsl:value-of select="sml:ImplementationCode/@language"></xsl:value-of>
					</xsl:element>
				</xsl:element>
			</xsl:element>
		</xsl:if>
		<xsl:if test="normalize-space(sml:ImplementationCode/@version)!=''">
			<!--解析方法实现的版本 -->
			<xsl:element name="rim:Slot" namespace="{$nsrim}">
				<xsl:attribute name="name">
				<xsl:value-of
					select="concat($slotPrefix,'processmethod:implementation:impleVersion')"></xsl:value-of>
			</xsl:attribute>
				<xsl:attribute name="slotType"><xsl:value-of
					select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
				<xsl:element name="rim:ValueList" namespace="{$nsrim}">
					<xsl:element name="rim:Value" namespace="{$nsrim}">
						<xsl:value-of select="sml:ImplementationCode/@version"></xsl:value-of>
					</xsl:element>
				</xsl:element>
			</xsl:element>
		</xsl:if>
		<xsl:if test="normalize-space(sml:ImplementationCode/gml:description)!=''">
			<!-- 解析方法的代码内容 -->
			<xsl:element name="rim:Slot" namespace="{$nsrim}">
				<xsl:attribute name="name">
				<xsl:value-of
					select="concat($slotPrefix,'processmethod:implementation:description')"></xsl:value-of>
			</xsl:attribute>
				<xsl:attribute name="slotType"><xsl:value-of
					select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
				<xsl:element name="rim:ValueList" namespace="{$nsrim}">
					<xsl:element name="rim:Value" namespace="{$nsrim}">
						<xsl:value-of select="sml:ImplementationCode/gml:description"></xsl:value-of>
					</xsl:element>
				</xsl:element>
			</xsl:element>
		</xsl:if>
	</xsl:template>

	<!-- 
		/************解析 sml:algorithm部分 ************/
	 -->
	<xsl:template match="sml:algorithm">
		<xsl:comment>
			<xsl:text>---------Algorithm---------</xsl:text>
		</xsl:comment>
		<!-- 算法的描述 -->
		<xsl:if test="normalize-space(sml:AlgorithmDefinition/gml:description)!=''">
			<xsl:element name="rim:Slot" namespace="{$nsrim}">
				<xsl:attribute name="name">
				<xsl:value-of
					select="concat($slotPrefix,'processmethod:algorithem:description' )"></xsl:value-of>
			</xsl:attribute>
				<xsl:attribute name="slotType"><xsl:value-of
					select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
				<xsl:element name="rim:ValueList" namespace="{$nsrim}">
					<xsl:element name="rim:Value">
						<xsl:value-of select="sml:AlgorithmDefinition/gml:description"></xsl:value-of>
					</xsl:element>
				</xsl:element>
			</xsl:element>
		</xsl:if>
		<!-- 关联到的算法的获取url -->
		<xsl:if test="normalize-space(sml:AlgorithmDefinition/sml:mathML)!=''">
			<xsl:element name="rim:Slot" namespace="{$nsrim}">
				<xsl:attribute name="name">
				<xsl:value-of
					select="concat($slotPrefix,'processmethod:algorithem:visitUrl' )"></xsl:value-of>
			</xsl:attribute>
				<xsl:attribute name="slotType"><xsl:value-of
					select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
				<xsl:element name="rim:ValueList" namespace="{$nsrim}">
					<xsl:element name="rim:Value">
						<xsl:value-of select="sml:AlgorithmDefinition/sml:mathML/@xlink:href"></xsl:value-of>
					</xsl:element>
				</xsl:element>
			</xsl:element>
		</xsl:if>
	</xsl:template>
	<!-- 
	/***************解析sml:Rules******************/
	 -->
	<xsl:template match="sml:rules">
		<xsl:comment>
			<xsl:text>---------Rules---------</xsl:text>
		</xsl:comment>
		<xsl:if test="normalize-space(sml:RulesDefinition/gml:description)!=''">
			<!-- 解析传感器规则的描述 -->
			<xsl:element name="rim:Slot" namespace="{$nsrim}">
				<xsl:attribute name="name">
				<xsl:value-of select="concat($slotPrefix,'rule:description')"></xsl:value-of>
			</xsl:attribute>
				<xsl:attribute name="slotType"><xsl:value-of
					select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
				<xsl:element name="rim:ValueList" namespace="{$nsrim}">
					<xsl:value-of select="sml:RulesDefinition/gml:description"></xsl:value-of>
				</xsl:element>
			</xsl:element>
		</xsl:if>
		<xsl:if test="normalize-space(sml:RulesDefinition/sml:schematron)!=''">
			<!-- 解析传感器的schematron信息-->
			<xsl:element name="rim:Slot" namespace="{$nsrim}">
				<xsl:attribute name="name">
				<xsl:value-of select="concat($slotPrefix,'rule:schematron')"></xsl:value-of>
			</xsl:attribute>
				<xsl:attribute name="slotType"><xsl:value-of
					select="concat($oasisDataTypePrefix, 'String')" /></xsl:attribute>
				<xsl:element name="rim:ValueList" namespace="{$nsrim}">
					<xsl:value-of select="sml:RulesDefinition/sml:schematron"></xsl:value-of>
				</xsl:element>
			</xsl:element>
		</xsl:if>
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
	<!-- /***************organization****************/ -->
	<xsl:template match="sml:contactmethod">
		<xsl:element name="rim:Organization" namespace="{$nsrim}">
			<xsl:attribute name="id">
				<xsl:value-of
				select="concat($systemIdentifier,':',$idMiddlefixOrganization, generate-id(sml:ResponsibleParty/sml:organizationName) )"></xsl:value-of>
			 </xsl:attribute>
			<xsl:attribute name="primaryContact">
			 	<xsl:value-of
				select="concat($systemIdentifier,':',$idMiddlefixOrganization, generate-id(sml:ResponsibleParty/sml:individualName) )"></xsl:value-of>
			 </xsl:attribute>
			<xsl:if
				test="normalize-space(sml:ResponsibleParty/sml:organizationName)!=''">
				<xsl:element name="rim:Name" namespace="{$nsrim}">
					<xsl:element name="rim:LocalzedString" namespace="{$nsrim}">
						<xsl:attribute name="xml:lang">
						<xsl:value-of select="$defaultLocalizedStringLang"></xsl:value-of>
					</xsl:attribute>
						<xsl:attribute name="value">
						<xsl:value-of select="sml:ResponsibleParty/sml:organizationName"></xsl:value-of>
					</xsl:attribute>
					</xsl:element>
				</xsl:element>
			</xsl:if>
			<xsl:element name="rim:Address" namespace="{$nsrim}">
				<xsl:apply-templates
					select="sml:ResponsibleParty/sml:contactInfo/sml:address" />
			</xsl:element>
			<xsl:element name="rim:EmailAddress">
				<xsl:attribute name="address">
					<xsl:value-of
					select="sml:ResponsibleParty/sml:contactInfo/sml:address/sml:electronicMailAddress"></xsl:value-of>
				</xsl:attribute>
			</xsl:element>
			<xsl:element name="rim:TelephoneNumber">
				<xsl:apply-templates
					select="sml:ResponsibleParty/sml:contactInfo/sml:phone" />
			</xsl:element>
		</xsl:element>
		<xsl:comment>
			<xsl:text>-----Person----</xsl:text>
		</xsl:comment>
		<xsl:apply-templates select="sml:ResponsibleParty/sml:individualName" />
	</xsl:template>
	<xsl:template match="sml:contactInfo/sml:phonemethod">
		<xsl:apply-templates select="sml:voice" />
	</xsl:template>
	<xsl:template match="sml:phone/sml:voicemethod">
		<xsl:variable name="phoneinfo" select="substring-after(.,'(' )"></xsl:variable>
		<!-- 256)961-7767 -->
		<xsl:choose>
			<xsl:when test="contains($phoneinfo, ')')">
				<xsl:attribute name="areaCode">
					 <xsl:value-of select="substring-before($phoneinfo,')' )"></xsl:value-of>
				</xsl:attribute>
				<xsl:attribute name="number">
					<xsl:value-of select="substring-after($phoneinfo,')' )"></xsl:value-of>
				</xsl:attribute>
			</xsl:when>
		</xsl:choose>

	</xsl:template>
	<!-- 解析individualName -->
	<xsl:template match="sml:individualNamemethod">
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
	<!-- 解析 contactInfo -->
	<xsl:template match="sml:ResponsibleParty/sml:contactInfo/sml:address">
		<xsl:if test="normalize-space(sml:deliveryPoint)!=''">
			<xsl:attribute name="street">
			<xsl:value-of select="sml:deliveryPoint"></xsl:value-of>
		</xsl:attribute>
		</xsl:if>
		<xsl:if test="normalize-space(sml:city)!=''">
			<xsl:attribute name="city">
			<xsl:value-of select="sml:city"></xsl:value-of>
		</xsl:attribute>
		</xsl:if>
		<xsl:if test="normalize-space(sml:postalCode)!=''">
			<xsl:attribute name="postalCode">
			<xsl:value-of select="sml:postalCode"></xsl:value-of>
			</xsl:attribute>
		</xsl:if>
		<xsl:if test="normalize-space(sml:country)!=''">
			<xsl:attribute name="country">
			<xsl:value-of select="sml:country"></xsl:value-of>
		</xsl:attribute>
		</xsl:if>
	</xsl:template>
	<!-- 解析Telephone -->
	<xsl:template match="sml:ResponsibleParty/sml:contactInfo/sml:phone">
		<xsl:attribute name="number">
		<xsl:value-of select="sml:voice"></xsl:value-of>
		</xsl:attribute>
	</xsl:template>
	<!--
		/********************classification*****************************/
	-->

	<xsl:template match="sml:classification/sml:ClassifierList/sml:classifier"
		mode="extrinsicObject">
		<xsl:comment>
			<xsl:text>----------classification---------</xsl:text>
		</xsl:comment>
		<xsl:element name="rim:Classification">
			<xsl:attribute name="id">
		<xsl:value-of
				select="concat($systemIdentifier,':classification:',generate-id(sml:Term/sml:value) )"></xsl:value-of>
	</xsl:attribute>
			<xsl:attribute name="classifiedObject">
				<xsl:value-of select="$systemIdentifier"></xsl:value-of>
			</xsl:attribute>
			<xsl:attribute name="classificationNode">
				<xsl:value-of
				select="concat($classificationNodeIdPrefix,generate-id(sml:Term/sml:value) )"></xsl:value-of>
			</xsl:attribute>
		</xsl:element>
	</xsl:template>

	<!--
		/********************************classificationNode********************************/
	-->
	<xsl:template match="sml:classification/sml:ClassifierList/sml:classifier"
		mode="association-classification">
		<xsl:comment>
			<xsl:text>---------classificationNode---------</xsl:text>
		</xsl:comment>
		<xsl:element name="rim:ClassificationNode">
			<xsl:attribute name="id">
		 	<xsl:value-of
				select="concat($classificationNodeIdPrefix,generate-id(sml:Term/sml:value) )"></xsl:value-of>
		 </xsl:attribute>
			<xsl:attribute name="parent">
		 	<xsl:value-of select="sml:Term/@definition"></xsl:value-of>
		 </xsl:attribute>
			<xsl:element name="name">
				<xsl:element name="rim:LocalzedString" namespace="{$nsrim}">
					<xsl:attribute name="xml:lang">
						<xsl:value-of select="$defaultLocalizedStringLang"></xsl:value-of>
					</xsl:attribute>
					<xsl:attribute name="value">
						<xsl:value-of select="sml:Term/sml:value"></xsl:value-of>
					</xsl:attribute>
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!--
		/**************************************************************************************
		各种变量
		**************************************************************************************/
	-->
	<xsl:variable name="implementationPrefix" select="'implementation'"></xsl:variable>
	<xsl:variable name="algorithmPrefix" select="'algorithem'"></xsl:variable>
	<xsl:variable name="rulesPrefix" select="'rules'"></xsl:variable>
	<xsl:variable name="slotPrefix"
		select="'urn:ogc:def:slot:OGC-CSW-ebRIM-Sensor::'" />
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
		YXL
	-->
	<xsl:variable name="fixedObjectTypeProcessModel"
		select="'urn:ogc:def:objectType:OGC-CSW-ebRIM-Sensor::NonPhysical:ProcessModel'" />
	<xsl:variable name="fixedObjectTypeProcessChain"
		select="'urn:ogc:def:objectType:OGC-CSW-ebRIM-Sensor::NonProcessChain'"></xsl:variable>
	<xsl:variable name="fixedObjectTypeProcessMethod"
		select="'urn:ogc:def:objectType:OGC-CSW-ebRIM-Sensor::ProcessMethod'">
	</xsl:variable>
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

	<xsl:variable name="systemIdentifier"
		select="//sml:ProcessMethod/sml:identification/sml:IdentifierList/sml:identifier/sml:Term[@definition='urn:ogc:def:property:OGC:processID']/sml:value" />

</xsl:transform>
	