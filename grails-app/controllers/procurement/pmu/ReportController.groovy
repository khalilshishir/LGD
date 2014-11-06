package procurement.pmu

import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef

//import lgsp.SchemeService
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy

class ReportController {
    TransactionAwareDataSourceProxy dataSource
//    SchemeService schemeService
    def jasperService


//    private static final String REPORT_DIR = 'REPORT_DIR'
//    private static final String IMAGE_DIR = 'IMAGE_DIR'
//    private static final String SUBREPORT_DIR = 'SUBREPORT_DIR'

    def index() {}

//    def generateReport(){
////        List<GroovyRowResult> dataListReport = schemeService.getDataForSchemeReportJasper(params)
//        params._file = "scheme/Topsial_Bibaron_o_dararhar-2.jasper"
//        params.format = ".pdf"
//        chain(controller: 'jasper', action: 'index', params: params)
////        chain(controller: 'jasper', action: 'index', params: params)
//    }

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
