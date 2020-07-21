package com.csw.getcapabilities.util;


import com.ebrim.model.csw.CapabilitiesDocument;
import com.ebrim.model.csw.CapabilitiesType;
import com.ebrim.model.ogc.*;
import com.ebrim.model.ogc.FilterCapabilitiesDocument.FilterCapabilities;
import com.ebrim.model.ogc.SpatialOperatorNameType.Enum;
import net.opengis.ows.*;
import net.opengis.ows.OperationsMetadataDocument.OperationsMetadata;
import net.opengis.ows.OperationsMetadataDocument;
import net.opengis.ows.OperationDocument.Operation;
import net.opengis.ows.DCPDocument.DCP;
import net.opengis.ows.HTTPDocument.HTTP;
import net.opengis.ows.ServiceIdentificationDocument.ServiceIdentification;
import net.opengis.ows.ServiceProviderDocument.ServiceProvider;

import javax.xml.namespace.QName;

public class CreateGetCapabilitiesDocumentUtil {

    /**
     * 生成ServiceIndentification文档，并返回ServiceIdentification文档
     * @param
     * @return
     */
    public ServiceIdentification getServiceIdentification() {
        ServiceIdentification serviceIdentification = ServiceIdentificationDocument.Factory.newInstance().addNewServiceIdentification();
        serviceIdentification.setTitle("Catalogue Service for Web");
        serviceIdentification.setAbstract("This Catalogue Service for Web provides facilities " +
                "for retrieving, storing and managing the geodata.");
        KeywordsType keywords =serviceIdentification.addNewKeywords();
        keywords.addKeyword("ebRIM");
        keywords.addKeyword("Registry");
        keywords.addKeyword("Discovery");
        keywords.addKeyword("Manager");
        CodeType codeType = serviceIdentification.addNewServiceType();
        codeType.setCodeSpace("http://www.opengis.net/cat/csw");
        codeType.setStringValue("CSW");
        serviceIdentification.addServiceTypeVersion("1.0");
        return serviceIdentification;
    }

    /**
     * 生成对应的ServiceProviderType的xml文档的内容，并返回 serviceProviderType内容
     * @param
     * @return 返回生成的serviceProviderType元素
     */
    public ServiceProvider getServiceProvider() {
        ServiceProvider serviceProvider = ServiceProviderDocument.Factory.newInstance().addNewServiceProvider();
        ResponsiblePartySubsetType responsiblePartySubsetType = serviceProvider.addNewServiceContact();
        responsiblePartySubsetType.setIndividualName("杨运山");
        ContactType contactType = responsiblePartySubsetType.addNewContactInfo();
        AddressType addressType = contactType.addNewAddress();
        addressType.setCity("武汉市");
        addressType.setCountry("中国");
        addressType.setPostalCode("430079");
        serviceProvider.setProviderName("中国地质大学(武汉)地理与信息工程学院");

        return serviceProvider;
    }

    /**
     * 生成对应的OperationMetadata的XML文档内容，并返回OperationMetadata内容
     * @param
     * @return
     */
    public OperationsMetadata getOperationsMetadata() {
        OperationsMetadata operationsMetadata = OperationsMetadataDocument.Factory.newInstance().addNewOperationsMetadata();
        String base = "http://localhost:8080/My_CSW/";
        String getRecordsPath =base + "getRecordsAction";
        String getRecordByIdPath = base + "getRecordByIdAction";
        String describeRecordPath = base + "describeRecordAction";
        String getDominPath = base + "getDominAction";
        String transactionPath = base + "transactionAction";
        String harvestPath = base + "havestAction";

        /*********GetRecords***********/
        Operation operation = operationsMetadata.addNewOperation();
        operation.setName("GetRecords");
        DCP dcp = operation.addNewDCP();
        HTTP http = dcp.addNewHTTP();
        RequestMethodType requestMethodType = http.addNewPost();
        requestMethodType.setHref(getRecordsPath);

        DomainType domainType1 = operation.addNewParameter();
        domainType1.setName("typeName");
        domainType1.addValue("rim:registrypackage");
        domainType1.addValue("csw:record");

        DomainType domainType2 = operation.addNewParameter();
        domainType2.setName("outputFormat");
        domainType2.addValue("text/xml");
        domainType2.addValue("application/xml");

        DomainType domainType3 = operation.addNewParameter();
        domainType3.setName("outputSchema");
        domainType3.addValue("http://www.opengis.net/cat/csw/2.0.2");
        domainType3.addValue("urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0");

        DomainType domainType4 = operation.addNewParameter();
        domainType4.setName("resultType");
        domainType4.addValue("hits");
        domainType4.addValue("results");
        domainType4.addValue("validate");

        DomainType domainType5 = operation.addNewParameter();
        domainType5.setName("ElementSetName");
        domainType5.addValue("brief");
        domainType5.addValue("summary");
        domainType5.addValue("full");

        DomainType domainType6 = operation.addNewParameter();
        domainType6.setName("CONSTRAINTLANGUAGE");
        domainType6.addValue("Filter");
        domainType6.addValue("CQL");

        DomainType domainType7 = operation.addNewConstraint();
        domainType7.setName("SupportedISOQueryables");
        String[] values = { "RevisionDate", "AlternateTitle", "CreationDate",
                "PublicationDate", "OrganisationName",
                "HasSecurityConstraints", "Language", "ResourceIdentifier",
                "ParentIdentifier", "KeywordType", "TopicCategory",
                "ResourceLanguage", "GeographicDescriptionCode",
                "DistanceValue", "DistanceUOM", "TempExtent_begin",
                "TempExtent_end", "ServiceType", "ServiceTypeVersion",
                "Operation", "CouplingType", "OperatesOn", "Denominator",
                "OperatesOnIdentifier", "OperatesOnWithOpName" };
        for (String value : values) {
            domainType7.addValue(value);
        }
        DomainType domainType8 = operation.addNewConstraint();
        domainType8.setName("AdditionalQueryables");
        domainType8.addValue("HierarchyLevelName");

        /*********GetRecordById***********/
        Operation getRecordByIdOperation = operationsMetadata.addNewOperation();
        getRecordByIdOperation.setName("GetRecordById");
        DCP dcpGetRecordById = getRecordByIdOperation.addNewDCP();
        dcpGetRecordById.addNewHTTP().addNewPost().setHref(getRecordByIdPath);
        DomainType dtGetRecordById = getRecordByIdOperation.addNewParameter();
        dtGetRecordById.setName("ElementSetName");
        dtGetRecordById.addValue("brief");
        dtGetRecordById.addValue("summary");
        dtGetRecordById.addValue("full");

        /*********DescribeRecord***********/
        Operation describeRecord = operationsMetadata.addNewOperation();
        describeRecord.setName("DescribeRecord");
        describeRecord.addNewDCP().addNewHTTP().addNewPost().setHref(describeRecordPath);

        /*********GetDomin***********/
        Operation getDomin = operationsMetadata.addNewOperation();
        getDomin.setName("GetDomin");
        getDomin.addNewDCP().addNewHTTP().addNewPost().setHref(getDominPath);

        /*********Transaction***********/
        Operation transaction = operationsMetadata.addNewOperation();
        transaction.setName("Transaction");
        transaction.addNewDCP().addNewHTTP().addNewPost().setHref(transactionPath);

        /*********Harvest***********/
        Operation harvest = operationsMetadata.addNewOperation();
        harvest.setName("Harvest");
        harvest.addNewDCP().addNewHTTP().addNewPost().setHref(harvestPath);

        return operationsMetadata;
    }

    /**
     * 生成FilterCapabilites的文档，并将FilterCapabilities对象返回
     * @param
     * @return
     */
    public FilterCapabilities getFilterCapabilities() {
        FilterCapabilities filterCapabilities = FilterCapabilitiesDocument.Factory.newInstance().addNewFilterCapabilities();
        IdCapabilitiesType idCapabilitiesType =filterCapabilities.addNewIdCapabilities();
        idCapabilitiesType.addNewEID();
        idCapabilitiesType.addNewFID();
        SpatialCapabilitiesType spatialCapabilitiesType = filterCapabilities.addNewSpatialCapabilities();
        SpatialOperatorsType spatialOperatorsType = spatialCapabilitiesType.addNewSpatialOperators();
        String[] spatialOperators = {"BBOX", "Beyond", "Contains", "Crosses", "Disjoint",
                "DWithin", "Equals", "Intersects", "Overlaps", "Touches", "Within"};
        for (String spatialOperator : spatialOperators) {
            spatialOperatorsType.addNewSpatialOperator().setName(Enum.forString(spatialOperator));
        }
        GeometryOperandsType geometryOperandsType = spatialCapabilitiesType.addNewGeometryOperands();
        String[] geometryOperandsValues = {"gml:Envelope", "gml:Point", "gml:LineString",
                "gml:Polygon"};
        for (String geometryOperandsValue : geometryOperandsValues) {
            geometryOperandsType.addGeometryOperand(new QName(geometryOperandsValue));
        }

        return filterCapabilities;
    }

    /**
     * 用于生成和获取GetCapabilitiesResponseDocument的文档的String信息
     * @param
     * @return 返回需要的GetCapabilitiesRsponseDocument的文档String信息</b>
     */
    public String createGetCapabilitiesResponseDocument() {
        CapabilitiesDocument capabilitiesDocument = CapabilitiesDocument.Factory.newInstance();
        CapabilitiesType capabilitiesType = capabilitiesDocument.addNewCapabilities();
        capabilitiesType.setVersion("2.0.2");

        ServiceIdentification serviceIdentification = getServiceIdentification();
        capabilitiesType.setServiceIdentification(serviceIdentification);

        ServiceProvider serviceProvider = getServiceProvider();
        capabilitiesType.setServiceProvider(serviceProvider);

        OperationsMetadata operationsMetadata = getOperationsMetadata();
        capabilitiesType.setOperationsMetadata(operationsMetadata);

        FilterCapabilities filterCapabilities = getFilterCapabilities();
        capabilitiesType.setFilterCapabilities(filterCapabilities);

        String getCapabilitiesDocument = capabilitiesDocument.xmlText();

        return getCapabilitiesDocument;
    }
}
