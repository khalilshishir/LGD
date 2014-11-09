package fixedAsset
import org.codehaus.groovy.grails.plugins.jasper.JasperService
import grails.converters.JSON
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import groovy.sql.Sql
import util.Util

class FIXED_ASSET_RPTController {
    def jasperService
    def dataSource
    private static final String REPORT_FILE_FORMAT = 'pdf'
    private static final String REPORT_NAME_FIELD = 'REPORT_NAME'
    private static final String REPORT_FOLDER = 'storeInFromSite'
    private static final String SUB_REPORT_FOLDER = 'subReport'
    private static final String OUTPUT_FILE_NAME = 'roleReport'
    private static final String REPORT_TITLE = 'Company Name'
    private static final String REPORT_DIR = 'REPORT_DIR'
    private static final String IMAGE_DIR = 'IMAGE_DIR'
    private static final String SUBREPORT_DIR = 'SUBREPORT_DIR'
    private static final String JASPER_FILE = 'userRoleDetails.jasper'
    private static final String PDF_EXTENSION = ".pdf"
    private static final String REPORT = "report"
    def index() { }
    def generateReport() {
        Map parameters = new LinkedHashMap();
        def report_name=""
        def report_name1=""
        def reportNamel=params.reportName
        def locationId=params.LOCATION_ID
        def assetBookId=params.ASSET_BOOK_ID
        def majorCatId=params.ASSET_MAJOR_CATEGORY_ID
        def minorCatId=params.ASSET_MINOR_CATEGORY_ID
        def stakeholderId=params.STAKEHOLDER_ID
        def startDate=params.START_DATE
        def endDate=params.END_DATE
        if (reportNamel.equals("Fixed asset list"))
        {
            report_name="fixedAssetList.jasper"
            report_name1="fixedAssetList"

        }
        else if (reportNamel.equals("Category Wise Asset List"))
        {
            report_name="categoryWiseAssetList.jasper"
            report_name1="categoryWiseAssetList"
            
            if (params.ASSET_MAJOR_CATEGORY_ID.id!=""){
                parameters.put("MajorCategoryId",majorCatId.id.toInteger())
            }
            if (params.ASSET_MINOR_CATEGORY_ID.id!=""){
                parameters.put("MinorCategoryId",minorCatId.id.toInteger())
            }

        }
        else if (reportNamel.equals("Location wise Asset List"))
        {
            report_name="locationWiseAssetList.jasper"
            report_name1="locationWiseAssetList"
            parameters.put("LocationId",locationId.id)
//            parameters.put("p_occupation", fName)

        }
        else if (reportNamel.equals("Assigned Person wise Asset List"))
        {
            report_name="assignPersonWiseAssetList.jasper"
            report_name1="assignPersonWiseAssetList"
            parameters.put("StakeholderId",stakeholderId)
//            parameters.put("p_occupation", fName)

        }
        else if (reportNamel.equals("Stock Asset List"))
        {
            report_name="stockAssetList.jasper"
            report_name1="stockAssetList"
            parameters.put("Location",locationId.id)
            parameters.put("P_STDATE",startDate)
            parameters.put("P_ENDATE",endDate)
        }
        else if (reportNamel.equals("Warranty Period wise Asset List"))
        {
            report_name="warrantyPeriodWiseAssetList.jasper"
            report_name1="warrantyPeriodWiseAssetList"
            parameters.put("P_STDATE",startDate)
            parameters.put("P_ENDATE",endDate)
        }
        else if (reportNamel.equals("Asset Update History"))
        {
            report_name="assetUpdateHistory.jasper"
            report_name1="assetUpdateHistory"
            parameters.put("AssetBookId",assetBookId.id)
            parameters.put("P_ST_DATE",startDate)
            parameters.put("P_EN_DATE",endDate)
        }

        String subReportDir = Util.getReportDirectory()
        String reportDir = Util.getReportDirectory()
        String imageDir = Util.getImageDirectory()
        parameters.put(SUBREPORT_DIR,subReportDir)
        parameters.put(REPORT_DIR,reportDir)
        parameters.put(IMAGE_DIR,imageDir)
        println(parameters)

        def reportDef
        if (params.reportFormat == "XLS") {
            reportDef = new JasperReportDef(name: report_name, parameters: parameters, fileFormat: JasperExportFormat.XLS_FORMAT)
        }
        else {reportDef = new JasperReportDef(name: report_name, parameters: parameters, fileFormat: JasperExportFormat.PDF_FORMAT)}

        response.reset();

        if (params.reportFormat == "XLS") {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment; filename=\"" + report_name1 + ".xls\"");
        }
        else {
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "attachment; filename=\"" + report_name1 + ".pdf\"");
        }

        response.outputStream << jasperService.generateReport(reportDef).toByteArray()
        response.outputStream.flush()

        return
    }
}
