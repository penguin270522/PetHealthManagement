package com.example.pethealth.constant;

public class QueryConstant {
    public final static String WHERE_ONE_EQUAL_ONE = " where 1 = 1";
    public final static String SELECT_FROM_APPOINTMENT = "select * from Appointment AS a";

    public final static String SELECT_FROM_MEDICAL_REPORT = "select * from medical_report As a";

    public final static String SELECT_FROM_PET = "select * from pet As a";

    public final static String SELCET_FORM_PET_APPOINTMENT = "SELECT a.id AS pet_id, a.name AS pet_name, a.gender_pet AS pet_gender_pet, a.type_pet AS pet_type_pet, "
            + "a.weight AS pet_weight, a.birth_day AS pet_birth_day, a.color AS pet_color, a.size AS pet_size, "
            + "a.adoptive AS pet_adoptive, a.crystal AS pet_crystal, a.status_pet AS pet_status_pet, "
            + "mr.id AS appointment_id, mr.appointment_status AS appointment_status "
            + "FROM pet AS a";
    public final static String WHERE = " where ";
    public final static String JOIN_APPOINTMENT = " join appointment mr on a.id = mr.pet_id ";
    public final static String APPOINTMENT_STATUS = " mr.appointment_status = '%s'";
    public final static String LIMIT = " LIMIT ";
    public final static String OFFSET = " OFFSET ";
    public final static String IS_NOT_NULL = " is not null ";

    public final static String SELECT_FROM_QUESTION ="select * from question AS a";
}
