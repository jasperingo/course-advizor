package com.lovelyn.course_advizor.validation;

public class ValidationErrorCode {

    public final static String FIELD_INVALID = "FIELD_INVALID";

    public final static String FIELD_REQUIRED = "FIELD_REQUIRED";

    public final static String FIELD_PATTERN = "FIELD_PATTERN";

    public final static String FIELD_SIZE = "FIELD_SIZE";

    public final static String FIELD_EXISTS = "FIELD_EXISTS";

    public final static String FIELD_DO_NOT_EXIST = "FIELD_DO_NOT_EXIST";

    public final static String ID_INVALID = "ID_INVALID";


    public final static String BODY_INVALID = "request_body / BODY_INVALID / Request body cannot be null";


    public final static String SESSION_ID_REQUIRED = "session_id / "+FIELD_REQUIRED+" / Session id is required";

    public final static String SESSION_ID_MIN = "session_id / "+FIELD_SIZE+" / Session id cannot be less than one";

    public final static String SESSION_ID_DO_NOT_EXIST = "session_id / "+FIELD_DO_NOT_EXIST+" / Session with id ${validatedValue} do not exist";


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


    public final static String GRADE_REQUIRED = "grade / "+FIELD_REQUIRED+" / Grade is required";


}
