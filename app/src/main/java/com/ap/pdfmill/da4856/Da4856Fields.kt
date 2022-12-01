package com.ap.pdfmill.da4856

enum class Da4856Field(val fieldName: String) {
    NAME("form1[0].Page1[0].Name[0]"),
    RANK_GRADE("form1[0].Page1[0].Rank_Grade[0]"),
    DATE_COUNSELING("form1[0].Page1[0].Date_Counseling[0]"),
    ORGANIZATION("form1[0].Page1[0].Organization[0]"),
    NAME_TITLE_COUNSELOR("form1[0].Page1[0].Name_Title_Counselor[0]"),
    PURPOSE_COUNSELING("form1[0].Page1[0].Purpose_Counseling[0]"),
    KEY_POINTS_DISCUSSION("form1[0].Page1[0].Key_Points_Disscussion[0]"),
    PLAN_OF_ACTION("form1[0].Page2[0].Plan_Action[0]"),
    INDIVIDUAL_COUNSELED_REMARKS("form1[0].Page2[0].Individual_Couseled_Remarks[0]"),
    LEADER_RESPONSIBILITIES("form1[0].Page2[0].Leader_Responsibilities[0]"),
    ASSESSMENT("form1[0].Page2[0].Assessment[0]"),
    INDIVIDUAL_COUNSELED_I_AGREE("form1[0].Page2[0].Individual_Counseled_I_Agree[0]"),
    INDIVIDUAL_COUNSELED_I_DISAGREE("form1[0].Page2[0].Individual_Counseled_I_Disagree[0]"),
    INDIVIDUAL_COUNSELED_DATE("form1[0].Page2[0].Individual_Counseled_Date[0]"),
    COUNSELOR_DATE("form1[0].Page2[0].Counselor_Date[0]"),
    DATE_ASSESSMENT("form1[0].Page2[0].Date_Assessment[0]"),
    SIGNATURE_INDIVIDUAL_COUNSELED("form1[0].Page2[0].Signature_Individual_Counseled[0]"),
    SIGNATURE_COUNSELOR("form1[0].Page2[0].Signature_Counselor[0]"),
    COUNSELOR("form1[0].Page2[0].Counselor[0]"),
    INDIVIDUAL_COUNSELED("form1[0].Page2[0].Individual_Counseled[0]");

    companion object {
        fun textFields() = listOf(
            NAME,
            RANK_GRADE,
            DATE_COUNSELING,
            ORGANIZATION,
            NAME_TITLE_COUNSELOR,
            PURPOSE_COUNSELING,
            KEY_POINTS_DISCUSSION,
            PLAN_OF_ACTION,
            INDIVIDUAL_COUNSELED_REMARKS,
            LEADER_RESPONSIBILITIES,
            ASSESSMENT,
            INDIVIDUAL_COUNSELED_DATE,
            COUNSELOR_DATE,
            DATE_ASSESSMENT,
        )
    }
}
