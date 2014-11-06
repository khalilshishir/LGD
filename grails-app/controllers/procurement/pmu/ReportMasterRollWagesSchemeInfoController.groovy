package procurement.pmu


import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy

class ReportMasterRollWagesSchemeInfoController {

    TransactionAwareDataSourceProxy dataSource
//    SchemeService schemeService
    def jasperService
    def index() {}


    def generateReport() {
        Map parameters = new LinkedHashMap();
        parameters.put('schemeInfo', Long.parseLong(params.schemeInfo))

        def reportDef
        reportDef = new JasperReportDef(name: "scheme/Master_Role_Wages.jasper", parameters: parameters, fileFormat: JasperExportFormat.PDF_FORMAT)
        response.reset();

//        if (params.reportFormat == "XLS") {
//            response.setContentType("application/vnd.ms-excel");
//            response.setHeader("Content-disposition", "attachment; filename=\"" + report_name1 + ".xls\"");
//        }
//        else {
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename=\"" + "Master_Role_Wages" + ".pdf\"");
//      }

        response.outputStream << jasperService.generateReport(reportDef).toByteArray()
        response.outputStream.flush()

        return
    }
}


