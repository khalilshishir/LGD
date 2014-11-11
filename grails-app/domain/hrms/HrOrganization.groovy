package hrms

class HrOrganization {

    static mapping = {
        table 'HR_ORGANIZATION'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        id column: 'ORGANIZATION_ID'
        emailId column: 'EMAIL'
        webAddress column: 'WEB'
        // test
    }

    Long id
    String orgName
    String address
    String phone
    String fax
    String emailId
    String webAddress
    //Integer logoId
    //byte[] logo
    String imagePath
    String imageName

    static constraints = {
        id(size:0..19)
        orgName(nullable: false)
        address(nullable: true)
        phone(nullable: true)
        fax(nullable: true)
        emailId(email:true)
        webAddress(nullable: true)
        //logoId(nullable: true)
        //logo(nullable: true,size: 0..5000000)
        imagePath(nullable: true)
        imageName(nullable: true)
    }

    String toString(){
        return "${orgName}"
    }
}
