package procurement.pmu

import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy

class ReportUpBuyProcessingController {

    TransactionAwareDataSourceProxy dataSource
    def jasperService

    def index() {}


    def generateReport() {
        Map parameters = new LinkedHashMap();
        parameters.put('schemeInfo', Long.parseLong(params.schemeInfo))

        def reportDef
        reportDef = new JasperReportDef(name: "scheme/Topsial_Bibaron_o_dararhar-2.jasper", parameters: parameters, fileFormat: JasperExportFormat.PDF_FORMAT)
        response.reset();

//        if (params.reportFormat == "XLS") {
//            response.setContentType("application/vnd.ms-excel");
//            response.setHeader("Content-disposition", "attachment; filename=\"" + report_name1 + ".xls\"");
//        }
//        else {
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename=\"" + "Topsial_Bibaron_o_dararhar-2" + ".pdf\"");
//        }

        response.outputStream << jasperService.generateReport(reportDef).toByteArray()
        response.outputStream.flush()

        return
    }
}
