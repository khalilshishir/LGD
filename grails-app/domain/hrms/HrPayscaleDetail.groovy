package hrms

class HrPayscaleDetail {

    static mapping = {
        table 'HR_PAYSCALE_DETAIL'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        id column: 'ID'
        payscaleIdHrPayscale column: 'PAYSCALE_ID'
        sort stage: 'asc'
        //sort id: 'asc'

    }
    Long id
    Integer stage
    Float basic
    Float da
    Float houseRent
    Float conveyanceAllow
    Float washingAllow
    Float medicalAllow
    Float pfContribution
    Float festivalBonus
    Float annualBonus
    Float gratuity
    // Relation
    HrPayscale payscaleIdHrPayscale


    static constraints = {
        id(size: 0..19)
        stage(size: 0..15,nullable: false)
        basic(nullable: true)
        da(nullable: true)
        houseRent(nullable: true)
        conveyanceAllow(nullable: true)
        washingAllow(nullable: true)
        medicalAllow(nullable: true)
        pfContribution(nullable: true)
        festivalBonus(nullable: true)
        annualBonus(nullable: true)
        gratuity(nullable: true)
        payscaleIdHrPayscale(nullable: true)
    }

    String toString() {
        return "${stage}"
    }

}
