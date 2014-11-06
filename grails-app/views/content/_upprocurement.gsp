<%@ page import="comonclass.UpProcType" %>
<script type="text/javascript">
    $(document).ready(function(){

        $("#proc_master_up").toggle();
        $("#proc_master").toggle();
        $("#proc_master_com").toggle();
        $("#proc_master_rfq").toggle();
        $("#proc_master_otm").toggle();
        $("#dir-proc-report").toggle();
        $("#item_details").toggle();

        $("#lai").toggle();
        $("#mrs").toggle();
        $("#ada").toggle();
        $("#uprfq").toggle();
        $("#openingsheet").toggle();
        $("#opningsheetdetails").toggle();
        $("#evaluation").toggle();
        $("#evaluationDetails").toggle();
        $("#workorder").toggle();
        $("#billpayment").toggle();
        $("#otm-tender").toggle();



        $("#otm-opening-sheet").toggle();
        $("#otm-schedule").toggle();
        $("#otm-te").toggle();
        $("#otm-work-order").toggle();
        $("#otm-bill-pay").toggle();
        $("#otm-invitation").toggle();




        $("#proc_up").click(function(){
            $("#proc_master_up").toggle();
        });
        $("#proc").click(function(){
            $("#proc_master").toggle();
            $("#dir-proc-report").toggle();
        });


        $("#cp").click(function(){
            $("#lai").toggle();
            $("#mrs").toggle();
            $("#ada").toggle();
            $("#proc_master_com").toggle();

        });


        $("#rfq").click(function(){
            $("#proc_master_rfq").toggle();
            $("#uprfq").toggle();
            $("#item_details").toggle();
            $("#openingsheet").toggle();
            $("#opningsheetdetails").toggle();
            $("#evaluation").toggle();
            $("#evaluationDetails").toggle();
            $("#workorder").toggle();
            $("#billpayment").toggle();
        });
        $("#otm-proc").click(function(){
            $("#proc_master_otm").toggle();
            $("#otm-invitation").toggle();
            $("#otm-opening-sheet").toggle();
            $("#otm-schedule").toggle();
            $("#otm-te").toggle();
            $("#otm-work-order").toggle();
            $("#otm-bill-pay").toggle();
            $("#otm-tender").toggle();
        });
    });
</script>

<div class="col-xs-3" style="float: left;">
    <div class="list-group">
        <a href="#" id="proc_up" class="list-group-item active" style="font-size:14px;"><span class="glyphicon glyphicon-log-out"></span> &nbsp &nbsp <strong>ক্রয় (ইউনিয়ন পরিষদ) </strong> </a><!-- this is header -->
        <a href="${g.createLink(controller: "Up_Proc_Master",action: "create",params:UpProcType.DIRECT_PROCUREMENT)}"  id="proc_master_up" class="list-group-item"><i class="fa fa-angle-double-right fa-col"></i>ইউনিয়ন পরিষদ ক্রয় তৈরী  করুন </a>


        <a href="#" id="proc" class="list-group-item active" style="font-size:14px;"><span class="glyphicon glyphicon-log-out"></span> &nbsp &nbsp <strong> সরাসরি ক্রয়- পদ্ধতি </strong> </a><!-- this is header -->
        <a href="${g.createLink(controller: "upDirectProcurement",action: "create")}"  id="proc_master" class="list-group-item"><i class="fa fa-angle-double-right fa-col"></i>আইটেম এর বর্ণনা</a>
        <a href="${g.createLink(controller: "upDirectProcurement",action: "create")}"  id="dir-proc-report" class="list-group-item"><i class="fa fa-angle-double-right fa-col"></i>রিপোর্ট </a>

        <a href="#" id="cp" class="list-group-item active" style="font-size:14px;"><span class="glyphicon glyphicon-log-out"></span> &nbsp &nbsp <strong>কমিউনিটি ক্রয়- পদ্ধতি </strong> </a><!-- this is header -->
    %{--<a href="${g.createLink(controller: "Up_Proc_Master")}"  id="proc_master_com" class="list-group-item"><i class="fa fa-angle-double-right fa-col"></i>মালামালের বিবরণ তৈরী  করুন</a>--}%
        <a href="${g.createLink(controller: "upCommuniProcurement",action: 'create')}"  id="proc_master_com" class="list-group-item"><i class="fa fa-angle-double-right fa-col"></i>মালামালের বিবরণ তৈরী  করুন</a>
        <a href="${g.createLink(controller: "labourAppoinmentInfo",action: "create")}" id="lai" class="list-group-item"><i class="fa fa-angle-double-right fa-col"></i>স্থানীয় শ্রমিক নিয়োগ</a>
        <a href="${g.createLink(controller: "MasterRoleSalary",action: "create")}" id="mrs" class="list-group-item"><i class="fa fa-angle-double-right fa-col"></i>মাস্টার রুল মুজরী </a>
        <a href="${g.createLink(controller: "AdvanceAdjustment",action: "create")}" id="ada" class="list-group-item"><i class="fa fa-angle-double-right fa-col"></i>অগ্রিম সমন্বয়</a>

        <a href="#" id="rfq" class="list-group-item active" style="font-size:14px;"><span class="glyphicon glyphicon-share-alt"></span> &nbsp &nbsp <strong>আর এফ কিউ পদ্ধতি </strong></a><!-- this is header -->
    %{--<a href="${g.createLink(controller: "Up_Proc_Master")}"  id="proc_master_rfq" class="list-group-item"><i class="fa fa-angle-double-right fa-col"></i>মালামালের বিবরণ তৈরী  করুন</a>--}%
    %{--<a href="${g.createLink(controller: "Up_Proc_Master")}" id="item_details" class="list-group-item"><i class="fa fa-angle-double-right fa-col"></i> Up RFQ Item Details</a>--}%
        <a href="${g.createLink(controller: "upRfqProcurement",action: 'create')}"  id="item_details" class="list-group-item"><i class="fa fa-angle-double-right fa-col"></i>মালামালের বিবরণ তৈরী  করুন</a>
        <a href="${g.createLink(controller: "upRfqOpeningSheet",action: "create")}" id="openingsheet" class="list-group-item"><i class="fa fa-angle-double-right fa-col"></i>দরপত্র খোলা</a>
        %{--<a href="UpRFQOpeningSheetDetails.html" id="opningsheetdetails" class="list-group-item"><i class="fa fa-angle-double-right fa-col"></i> Up RFQ Opening Sheet Details</a>--}%
        <a href="${g.createLink(controller: "upRfqEvaluation",action: "create")}" id="evaluation" class="list-group-item"><i class="fa fa-angle-double-right fa-col"></i> দরপত্র মূল্যায়ন</a>
        %{--<a href="${g.createLink(controller: "Up_Proc_Master")}" id="evaluationDetails" class="list-group-item"><i class="fa fa-angle-double-right fa-col"></i> UP RFQ Evaluation Sheet Details</a>--}%
        <a href="${g.createLink(controller: "upRFQProcWorkOrder",action: "create")}" id="workorder" class="list-group-item"><i class="fa fa-angle-double-right fa-col"></i>সরবরাহ আদেশ </a>
        <a href="${g.createLink(controller: "advanceAdjustment",action: "create")}" id="billpayment" class="list-group-item"><i class="fa fa-angle-double-right fa-col"></i> বিল পরিশোধ</a>

        <a href="#" id="otm-proc" class="list-group-item active" style="font-size:14px;"><span class="glyphicon glyphicon-upload"></span> &nbsp &nbsp <strong>উন্মুক্ত দরপত্র   পদ্ধতি </strong></a><!-- this is header -->
        <a href="${g.createLink(controller: "upOtmProcurement",action: 'create')}"  id="proc_master_otm" class="list-group-item"><i class="fa fa-angle-double-right fa-col"></i>মালামালের বিবরণ তৈরী  করুন</a>
        <a href="${g.createLink(controller: "IFT",action: "create")}" id="otm-invitation"  class="list-group-item"><i class="fa fa-angle-double-right fa-col"></i> দরপত্র আহবানের ছক</a>
        <a href="${g.createLink(controller: "UP_OTM_Tender_Document",action: "create")}"  id="otm-tender"  class="list-group-item"><i class="fa fa-angle-double-right fa-col"></i>দরপত্র </a>
        <a href="${g.createLink(controller: "UP_OTM_Tender_Schedule_Sample",action: "create")}" id="otm-schedule"  class="list-group-item"><i class="fa fa-angle-double-right fa-col"></i>দরপত্র দলিলের শিডিউল নমুনা </a>
        <a href="${g.createLink(controller: 'upOtmOpeningSheet',action: "create")}" id="otm-opening-sheet"  class="list-group-item"><i class="fa fa-angle-double-right fa-col"></i>দরপত্র খোলা </a>
        %{--<a href="UpOTMOpeningSheetDetails.html" id="otm-opening-sheet-details"  class="list-group-item"><i class="fa fa-angle-double-right fa-col"></i> Up OTM Opening Sheet Details</a>--}%
        <a href="${g.createLink(controller: 'upOtmEvaluation',action: "create")}" id="otm-te"  class="list-group-item"><i class="fa fa-angle-double-right fa-col"></i> দরপত্র মূল্যায়ন</a>
        <a href="${g.createLink(controller: "upRFQProcWorkOrder",action: "create")}" id="otm-work-order"  class="list-group-item"><i class="fa fa-angle-double-right fa-col"></i> সরবরাহ আদেশ </a>
        <a href="${g.createLink(controller: "advanceAdjustment",action: "create")}" id="otm-bill-pay"  class="list-group-item"><i class="fa fa-angle-double-right fa-col"></i>  বিল পরিশোধ</a>

    </div><!--list-group-->
</div><!--col-xs-3-->