package com.lovelyn.course_advizor.validation;

public class ValidationErrorCode {

    public final static String FIELD_INVALID = "FIELD_INVALID";

    public final static String FIELD_REQUIRED = "FIELD_REQUIRED";

    public final static String FIELD_PATTERN = "FIELD_PATTERN";

    public final static String FIELD_SIZE = "FIELD_SIZE";

    public final static String FIELD_EXISTS = "FIELD_EXISTS";

    public final static String FIELD_DO_NOT_EXIST = "FIELD_DO_NOT_EXIST";


    public final static String BODY_INVALID = "request_body / BODY_INVALID / Request body cannot be null";


    public final static String FIRST_NAME_REQUIRED = "first_name / "+FIELD_REQUIRED+" / First name is required";

    public final static String LAST_NAME_REQUIRED = "last_name / "+FIELD_REQUIRED+" / Last name is required";


    public final static String PIN_REQUIRED = "pin / "+FIELD_REQUIRED+" / Pin is required";

    public final static String PIN_SIZE =  "pin / "+FIELD_SIZE+" / Pin must be {max} characters long";


    public final static String PHONE_NUMBER_REQUIRED = "phone_number / "+FIELD_REQUIRED+" / Phone number is required";

    public final static String PHONE_NUMBER_SIZE =  "phone_number / "+FIELD_SIZE+" / Phone number must be {max} characters long";

    public final static String PHONE_NUMBER_EXISTS =  "phone_number / "+FIELD_EXISTS+" / Phone number already exists";


    public final static String MATRICULATION_NUMBER_REQUIRED = "matriculation_number / "+FIELD_REQUIRED+" / Matriculation number is required";

    public final static String MATRICULATION_NUMBER_SIZE =  "matriculation_number / "+FIELD_SIZE+" / Matriculation number must be {max} characters long";

    public final static String MATRICULATION_NUMBER_EXISTS =  "matriculation_number / "+FIELD_EXISTS+" / Matriculation number already exists";


    public final static String DEPARTMENT_ID_REQUIRED = "department_id / "+FIELD_REQUIRED+" / Department id is required";

    public final static String DEPARTMENT_ID_MIN = "department_id / "+FIELD_SIZE+" / Department id cannot be less than one";

    public final static String DEPARTMENT_ID_DO_NOT_EXIST = "department_id / "+FIELD_DO_NOT_EXIST+" / Department with id ${validatedValue} do not exist";


    public final static String SESSION_ID_REQUIRED = "session_id / "+FIELD_REQUIRED+" / Session id is required";

    public final static String SESSION_ID_MIN = "session_id / "+FIELD_SIZE+" / Session id cannot be less than one";

    public final static String SESSION_ID_DO_NOT_EXIST = "session_id / "+FIELD_DO_NOT_EXIST+" / Session with id ${validatedValue} do not exist";


    public final static String COURSE_ADVISER_ID_REQUIRED = "course_adviser_id / "+FIELD_REQUIRED+" / Course adviser id is required";

    public final static String COURSE_ADVISER_ID_MIN = "course_adviser_id / "+FIELD_SIZE+" / Course adviser id cannot be less than one";

    public final static String COURSE_ADVISER_ID_DO_NOT_EXIST = "course_adviser_id / "+FIELD_DO_NOT_EXIST+" / Course adviser with id ${validatedValue} do not exist";


    public final static String COURSE_CODE_BLANK = "course_code / "+FIELD_REQUIRED+" / Course code is required";

    public final static String COURSE_CODE_LENGTH = "course_code / "+FIELD_SIZE+" / Course code must be {max} characters long";

    public final static String COURSE_CODE_PATTERN = "course_code / "+FIELD_PATTERN+" / Course code is formatted wrongly";


    public final static String SEMESTER_REQUIRED = "semester / "+FIELD_REQUIRED+" / Semester is required";


    public final static String STUDENT_ID_REQUIRED = "student_id / "+FIELD_REQUIRED+" / Student id is required";

    public final static String STUDENT_ID_MIN = "student_id / "+FIELD_SIZE+" / Student id cannot be less than one";

    public final static String STUDENT_ID_DO_NOT_EXIST = "student_id / "+FIELD_DO_NOT_EXIST+" / Student with id ${validatedValue} do not exist";


    public final static String RESULT_ID_REQUIRED = "result_id / "+FIELD_REQUIRED+" / Result id is required";

    public final static String RESULT_ID_MIN = "result_id / "+FIELD_SIZE+" / Result id cannot be less than one";

    public final static String RESULT_ID_DO_NOT_EXIST = "result_id / "+FIELD_DO_NOT_EXIST+" / Result with id ${validatedValue} do not exist";


    public final static String STUDENT_RESULT_STUDENT_AND_RESULT_ID_EXISTS = "request_body / STUDENT_ID_AND_RESULT_ID_FIELD_EXISTS / Student result with student and result id already exists";


    public final static String GRADE_REQUIRED = "grade / "+FIELD_REQUIRED+" / Grade is required";


    public final static String STATUS_REQUIRED = "status / "+FIELD_REQUIRED+" / Status is required";


    public final static String STARTED_AT_REQUIRED = "started_at / "+FIELD_REQUIRED+" / Started at is required";

    public final static String STARTED_AT_PAST = "started_at / "+FIELD_INVALID+" / Started at must be a future date";


    public final static String REPLY_REQUIRED = "reply / "+FIELD_REQUIRED+" / Reply is required";

}
